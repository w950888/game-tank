package game.business

import game.model.View

/**
 * @author Luke
 * @time 2020/5/3 下午 5:48
 * @version 1.0.0
 * @description
 */
interface Sufferable : View {


    val blood: Int

    fun notifySuffer(attackable: Attackable): Array<View>?
}