package game.business

import game.enums.Direction
import game.model.View


/**
 * @author Luke
 * @time 2020/5/3 下午 5:00
 * @version 1.0.0
 * @description
 */
interface AutoMovable : View {

    val currentDirection: Direction
    val speed: Int

    fun autoMove()

}