package game.model

import org.itheima.kotlin.game.core.Painter
import game.Config
import game.business.*
import game.enums.Direction
import java.util.*

/**
 * @author Luke
 * @time 2020/5/3 下午 11:32
 * @version 1.0.0
 * @description 地方坦克
 */
class Enemy(
    override var x: Int,
    override var y: Int
) : Movable, AutoMovable, Blockable, AutoShot, Sufferable, Destroyable {


    override var currentDirection: Direction = Direction.DOWN
    override val speed: Int = 8


    override val wight: Int = Config.block
    override val height: Int = Config.block

    //坦克不可以走的方向
    private var badDirection: Direction? = null

    private var lastShotTime = 0L
    private var shotFrequency = 800

    private var lastMoveTime = 0L
    private var moveFrequency = 50

    override var blood: Int = 2

    override fun draw() {

        val imagePath: String = when (currentDirection) {
            Direction.UP -> "img/enemy_1_u.gif"
            Direction.DOWN -> "img/enemy_1_d.gif"
            Direction.LEFT -> "img/enemy_1_l.gif"
            Direction.RIGHT -> "img/enemy_1_r.gif"
        }
        Painter.drawImage(imagePath, x, y)
    }

/*    override fun willCollision(block: Blockable): Direction? {
        return null
    }*/

    override fun notifyCollision(direction: Direction?, block: Blockable?) {
//
        badDirection = direction
    }

    override fun autoMove() {
        val current = System.currentTimeMillis()
        if (current - lastMoveTime < moveFrequency) return
        lastMoveTime = current

        if (currentDirection == badDirection) {
            //要网错误方向走,不允许的
            //改变自己的方向
            currentDirection = rdmDirection(badDirection)
            return
        }
        //坦克坐标需要发生变化
        //根据不同的方向,改变对应的坐标
        when (currentDirection) {
            Direction.UP -> y -= speed
            Direction.DOWN -> y += speed
            Direction.LEFT -> x -= speed
            Direction.RIGHT -> x += speed
        }

        //越界判断
        if (x < 0) x = 0
        if (x > Config.gameWidth - wight) x = Config.gameWidth - wight
        if (y < 0) y = 0
        if (y > Config.gameHeight - height) y = Config.gameHeight - height
    }

    private fun rdmDirection(bad: Direction?): Direction {
        val i = Random().nextInt(4)
        val direction = when (i) {
            0 -> Direction.UP
            1 -> Direction.DOWN
            2 -> Direction.LEFT
            3 -> Direction.RIGHT
            else -> Direction.UP
        }
        //判断不能要错误方向
        if (direction == bad) {
            return rdmDirection(bad)
        }
        return direction
    }

    override fun autoShot(): View? {

        val current = System.currentTimeMillis()
        if (current - lastShotTime < shotFrequency) return null
        lastShotTime = current


        return Bullet(this, currentDirection) { bulletWidth, bulletHight ->
            //计算子弹的真实坐标

            val tanX = x
            val tanY = y
            val tanWith = wight
            val tanHeight = height


            var bulletX = 0
            var bulletY = 0

            //如果坦克是向上的,计算子弹的位置
            when (currentDirection) {
                Direction.UP -> {
                    bulletX = tanX + (tanWith - bulletWidth) / 2
                    bulletY = tanY - bulletHight / 2
                }
                Direction.DOWN -> {
                    bulletX = tanX + (tanWith - bulletWidth) / 2
                    bulletY = tanY + tanHeight - bulletHight / 2
                }
                Direction.LEFT -> {
                    bulletX = tanX - bulletWidth / 2
                    bulletY = tanY + (tanHeight - bulletHight) / 2
                }
                Direction.RIGHT -> {
                    bulletX = tanX + tanWith - bulletWidth / 2
                    bulletY = tanY + (tanHeight - bulletHight) / 2
                }
            }
            Pair(bulletX, bulletY)
        }
    }

    override fun notifySuffer(attackable: Attackable): Array<View>? {
        if (attackable.owner is Enemy) {
            //挨打,不掉血
            return null
        }

        blood -= attackable.attackPower
        return arrayOf(Blast(x, y))
    }

    override fun isDestroyed(): Boolean = blood <= 0


}