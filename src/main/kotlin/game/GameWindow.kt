package game

import game.business.*
import game.enums.Direction
import game.model.*
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import org.itheima.kotlin.game.core.Window
import java.io.BufferedReader
import java.io.File
import java.io.InputStream
import java.io.InputStreamReader
import java.util.concurrent.CopyOnWriteArrayList

/**
 * @author Luke
 * @time 2020/5/2 下午 7:58
 * @version 1.0.0
 * @description
 */
class GameWindow : Window(
    title = "坦克大战 v1.0",
    icon = "img\\tank_u.gif",
    width = Config.gameWidth,
    height = Config.gameHeight
) {

    //private val views: ArrayList<View> = arrayListOf<View>()
    private val views = CopyOnWriteArrayList<View>()

    //晚点创建
    private lateinit var tank: Tank
    //var wall = Wall()
    //var grass = Grass()

    //游戏是否结束
    private var gameOwer: Boolean = false

    //敌方坦克数量
    private var enemyTotalSize = 60

    //敌方坦克在界面上最多显示几个
    private var enemyActiveSize = 6

    private val enemyBornLoaction = arrayListOf<Pair<Int, Int>>()

    //出生地点下标
    private var bornIndex = 0

    override fun onCreate() {
        //地图
        //通过读文件的方式创建地图
        //val file = File(javaClass.getResource("/map/1.map").path)
        val resourceAsStream = javaClass.getResourceAsStream("/map/1.map")
        val reader = BufferedReader(InputStreamReader(resourceAsStream, "utf-8"))

        //读取文件中的行
        val lines: List<String> = reader.readLines()
        //循环遍历
        var lineNum = 0

        lines.forEach { lines ->
            run {
                var columnNum = 0
                lines.toCharArray().forEach { column ->
                    run {
                        when (column) {
                            'Z' -> views.add(Wall(columnNum * Config.block, lineNum * Config.block))
                            'C' -> views.add(Grass(columnNum * Config.block, lineNum * Config.block))
                            'T' -> views.add(Steel(columnNum * Config.block, lineNum * Config.block))
                            'S' -> views.add(Water(columnNum * Config.block, lineNum * Config.block))
                            'D' -> enemyBornLoaction.add(Pair(columnNum * Config.block, lineNum * Config.block))
                        }
                        columnNum++
                    }
                }
            }
            lineNum++
        }

        //添加我方的坦克
        this.tank = Tank(Config.block * 10, Config.block * 12)
        views.add(tank)

        //添加大本营
        views.add(Camp(Config.gameWidth / 2 - Config.block, Config.gameHeight - 96))
    }

    override fun onDisplay() {
        //绘制地图中的元素

        views.forEach {
            it.draw()
        }

        //println("v:" + views.size)
        //绘制砖墙
        //wall.draw()
        //grass.draw()

        // Painter.drawImage("img/grass.gif", 200, 100)
        // Painter.drawImage("/img/shit.png", 700, 100)
    }

    override fun onKeyPressed(event: KeyEvent) {
        if (!gameOwer) {
            when (event.code) {
                KeyCode.W -> {
                    this.tank.move(Direction.UP)
                }
                KeyCode.S -> {
                    this.tank.move(Direction.DOWN)
                }
                KeyCode.A -> {
                    this.tank.move(Direction.LEFT)
                }
                KeyCode.D -> {
                    this.tank.move(Direction.RIGHT)
                }
                KeyCode.ENTER -> {
                    //发射子弹
                    val bullet = tank.shot()
                    //交给views管理
                    views.add(bullet)
                }
            }
        }

    }

    override fun onRefresh() {
        //业务逻辑
        //检测自动销毁
        views.filter { it is Destroyable }.forEach {
            //判断具备销毁能力的
            if ((it as Destroyable).isDestroyed()) {
                views.remove(it)

                if (it is Enemy) {
                    enemyTotalSize--
                }

                val destroy = it.showDestroy()
                destroy?.let {
                    views.addAll(destroy)
                }
            }
        }

        if (gameOwer) return

        //判断运动的物体和阻塞的物体是否发生碰撞
        //1)找到运动/阻塞的物体
        //2)
        //3)
        views.filter { it is Movable }.forEach { move ->
            move as Movable
            var badDirection: Direction? = null
            var badBlock: Blockable? = null
            run {
                views.filter { (it is Blockable) and (move != it) }.forEach blockTag@{ block ->

                    block as Blockable
                    //获得碰撞方向
                    val direction = move.willCollision(block)
                    //加入? 不为空的时候执行
                    direction?.let {
                        //移动的发现碰撞, 跳出当前循环
                        badDirection = direction
                        badBlock = block
                        return@blockTag
                    }
                }
                //找到和move碰撞的block 找到会碰撞的方向
                //通知可以移动的物体,会在哪个方向和哪个物体碰撞
                move.notifyCollision(badDirection, badBlock)

            }
        }

        //检测自动移动能力的物体,让他们自己动起来
        views.filter {
            it is AutoMovable
        }.forEach {
            (it as AutoMovable).autoMove()
        }


        //检测 具备攻击能力的和被攻击能力的物体实付产生碰撞
        //1)过滤 具备攻击能力的
        views.filter { it is Attackable }.forEach { attack ->
            attack as Attackable
            //2)受攻击能力的(攻击方的源不可以是发射方)
            //攻击方如果也是受攻击方时是不可以打自己的
            views.filter { (it is Sufferable) and (attack.owner != it) and (attack != it) }
                .forEach sufferTag@{ suffer ->
                    suffer as Sufferable
                    //2)判断碰撞
                    if (attack.isCollision(suffer)) {
                        //产生了碰撞,找到碰撞者
                        //通知攻击者 产生碰撞
                        attack.notifyAttack(suffer)
                        //通知被攻击者 产生碰撞
                        val sufferView = suffer.notifySuffer(attack)
                        sufferView?.let {
                            //显示挨打的效果
                            views.addAll(sufferView)
                        }
                        return@sufferTag
                    }
                }
        }

        //检测自动射击
        views.filter { it is AutoShot }.forEach {
            it as AutoShot
            val shot = it.autoShot()
            shot?.let {
                views.add(shot)
            }
        }

        //检测游戏是否结束
        if ((views.filter {
                it is Camp
            }.isEmpty()) or (enemyTotalSize <= 0)) {
            gameOwer = true
        }

        //检测敌方出生
        //判断当前页面上敌方的数量,小于激活数量
        if ((enemyTotalSize > 0) and (views.filter { it is Enemy }.size < enemyActiveSize)) {
            val index = bornIndex % enemyBornLoaction.size
            val pair = enemyBornLoaction[index]
            views.add(Enemy(pair.first, pair.second))

            bornIndex++
        }
    }
}

