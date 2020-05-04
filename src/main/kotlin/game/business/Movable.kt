package game.business

import game.Config
import game.enums.Direction
import game.model.View


/**
 * @author Luke
 * @time 2020/5/3 下午 1:38
 * @version 1.0.0
 * @description
 */
interface Movable : View {
    /**
     * 可移动的物体方向
     */
    val currentDirection: Direction

    /**
     * 移动的物体的速度
     */
    val speed: Int

    /**
     * 判断移动的物体是否和阻塞的物体发生碰撞
     * @return 要碰撞的方向,如果为null,说明没有碰撞
     */
    fun willCollision(block: Blockable): Direction?{
        //未来的坐标
        var x: Int = this.x
        var y: Int = this.y

        when (currentDirection) {
            Direction.UP -> y -= speed
            Direction.DOWN -> y += speed
            Direction.LEFT -> x -= speed
            Direction.RIGHT -> x += speed
        }

        ///和边界进行检测
        //越界判断
        if (x < 0) return Direction.LEFT
        if (x > Config.gameWidth - wight) return Direction.RIGHT
        if (y < 0) return Direction.UP
        if (y > Config.gameHeight - height) return Direction.DOWN


        //检测下一步是否碰撞
        val collision: Boolean = checkCollision(
            block.x, block.y, block.wight, block.height,
            x, y, wight, height
        )

        return if (collision) currentDirection else null
    }

    /**
     * 通知碰撞
     */
    fun notifyCollision(direction: Direction?, block: Blockable?)
}