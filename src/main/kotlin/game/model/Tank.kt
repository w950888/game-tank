package game.model

import org.itheima.kotlin.game.core.Painter
import game.Config
import game.business.Attackable
import game.business.Blockable
import game.business.Movable
import game.business.Sufferable
import game.enums.Direction

/**
 * @author Luke
 * @time 2020/5/2 下午 11:41
 * @version 1.0.0
 * @description
 */
class Tank(
    override var x: Int,
    override var y: Int
) : Movable, Blockable, Sufferable {


    override var wight: Int = Config.block
    override var height: Int = Config.block

    //方向
    override var currentDirection: Direction = Direction.UP
    override val speed: Int = 8

    //坦克不可以走的方向
    private var badDirection: Direction? = null
    override var blood: Int = 20

    override fun draw() {
/*        when (currentDirection) {
            Direction.UP -> Painter.drawImage("img/tank_u.gif", x, y)
            Direction.DOWN -> Painter.drawImage("img/tank_d.gif", x, y)
            Direction.LEFT -> Painter.drawImage("img/tank_l.gif", x, y)
            Direction.RIGHT -> Painter.drawImage("img/tank_r.gif", x, y)
        }*/

        val imagePath: String = when (currentDirection) {
            Direction.UP -> "img/tank_u.gif"
            Direction.DOWN -> "img/tank_d.gif"
            Direction.LEFT -> "img/tank_l.gif"
            Direction.RIGHT -> "img/tank_r.gif"
        }
        Painter.drawImage(imagePath, x, y)
    }


    fun move(direction: Direction) {
        //判断是否要往要碰撞的方向走
        if (direction == badDirection) {
            //不往下执行
            return
        }

        //当前的方向和希望移动的发向不一致时,只做方向改变
        if (this.currentDirection != direction) {
            this.currentDirection = direction
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

/*    override fun willCollision(block: Blockable): Direction? {

        //未来的坐标
        var x: Int = this.x
        var y: Int = this.y

        when (currentDirection) {
            Direction.UP -> y -= speed
            Direction.DOWN -> y += speed
            Direction.LEFT -> x -= speed
            Direction.RIGHT -> x += speed
        }


        //检测下一步是否碰撞
        val collision: Boolean = checkCollision(
            block.x, block.y, block.wight, block.height,
            x, y, wight, height
        )

*//*        var collision = when {
            block.y + block.height <= y -> {
                //如果阻挡物在运动物体的上方
                false
            }
            y + height <= block.y -> {
                //如果阻挡物在运动物体的上方
                false
            }
            block.x + block.wight <= x -> {
                //如果阻挡物在运动物体的左方
                false
            }
            else -> x + wight > block.x
        }*//*

        return if (collision) currentDirection else null
    }*/

    override fun notifyCollision(direction: Direction?, block: Blockable?) {
        this.badDirection = direction
    }

    /**
     * 发射子弹
     */
    fun shot(): Bullet {

        // return Bullet(currentDirection, bulletX, bulletY)
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
        blood -= attackable.attackPower
        return arrayOf(Blast(x, y))
    }
}