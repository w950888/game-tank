package game.business

import game.model.View

/**
 * @author Luke
 * @time 2020/5/3 下午 5:47
 * @version 1.0.0
 * @description
 */
interface Attackable : View {

    /**
     * 所有者
     */
    var owner: View

    /**
     * 攻击力
     */
    val attackPower: Int

    //判断是否碰撞
    fun isCollision(sufferable: Sufferable): Boolean
    fun notifyAttack(sufferable: Sufferable)

}