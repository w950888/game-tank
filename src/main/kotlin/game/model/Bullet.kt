package game.model

import org.itheima.kotlin.game.core.Painter
import game.Config
import game.business.Attackable
import game.business.AutoMovable
import game.business.Destroyable
import game.business.Sufferable
import game.enums.Direction
import game.ext.checkCollision

/**
 * @author Luke
 * @time 2020/5/3 下午 3:21
 * @version 1.0.0
 * @description 子弹
 * create()函数返回两个值,方向由坦克来决定
 */
class Bullet(
    override var owner: View,
    override val currentDirection: Direction,
    create: (width: Int, height: Int) -> Pair<Int, Int>
) : AutoMovable, Destroyable, Attackable, Sufferable {


    //给子弹一个方向,和坦克方向一致
    override val wight: Int
    override val height: Int
    override val blood: Int = 1


    override var x: Int = 0
    override var y: Int = 0

    override val speed: Int = 8

    private var isDestroyable = false

    override val attackPower: Int = 1

    private val imagePath: String = when (currentDirection) {
        Direction.UP -> "/img/shot_top.gif"
        Direction.DOWN -> "/img/shot_bottom.gif"
        Direction.LEFT -> "/img/shot_left.gif"
        Direction.RIGHT -> "/img/shot_right.gif"
    }

    init {
        //先计算宽度和高度
        val size = Painter.size(imagePath)
        wight = size[0]
        height = size[1]

        val pair = create.invoke(wight, height)
        x = pair.first
        y = pair.second
    }


    override fun draw() {
/*        val imagePath = when (direction) {
            Direction.UP -> "/img/shot_top.gif"
            Direction.DOWN -> "/img/shot_bottom.gif"
            Direction.LEFT -> "/img/shot_left.gif"
            Direction.RIGHT -> "/img/shot_right.gif"
        }*/
        Painter.drawImage(imagePath, x, y)
    }

    override fun autoMove() {
        //根据自己的方向,来改变自己的x和y
        when (currentDirection) {
            Direction.UP -> y -= speed
            Direction.DOWN -> y += speed
            Direction.LEFT -> x -= speed
            Direction.RIGHT -> x += speed

        }
    }

    override fun isDestroyed(): Boolean {
        if (isDestroyable) {
            return true
        }
        //子弹在脱离了屏幕后,需要被销毁
        if (x < -wight) return true
        if (x > Config.gameWidth) return true
        if (y < -height) return true
        if (y > Config.gameHeight) return true
        return false
    }


    override fun isCollision(sufferable: Sufferable): Boolean {
        return checkCollision(sufferable)
    }

    override fun notifyAttack(sufferable: Sufferable) {
        //println("子弹接收到碰撞..")
        //子弹打到墙后要销毁
        isDestroyable = true
    }

    override fun notifySuffer(attackable: Attackable): Array<View>? {
        return arrayOf(Blast(x, y))
    }


}