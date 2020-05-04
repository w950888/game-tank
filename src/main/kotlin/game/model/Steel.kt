package game.model

import org.itheima.kotlin.game.core.Painter
import game.Config
import game.business.Attackable
import game.business.Blockable
import game.business.Sufferable

/**
 * @author Luke
 * @time 2020/5/2 下午 10:01
 * @version 1.0.0
 * @description
 */
class Steel(
    override var x: Int,
    override var y: Int
) : Blockable, Sufferable {
    override val blood: Int = 1

    override fun notifySuffer(attackable: Attackable): Array<View>? {
        return null
    }
    //位置
    //override var x: Int = 100

    //override var y: Int = 100

    //宽高
    override var wight: Int = Config.block

    override var height: Int = Config.block

    //显示行为
    override fun draw() {
        Painter.drawImage("img/steel.gif", x, y)
    }
}