package game.model

import org.itheima.kotlin.game.core.Composer
import org.itheima.kotlin.game.core.Painter
import game.Config
import game.business.Attackable
import game.business.Blockable
import game.business.Destroyable
import game.business.Sufferable

/**
 * @author Luke
 * @time 2020/5/2 下午 8:21
 * @version 1.0.0
 * @description
 */
class Wall(
    override var x: Int,
    override var y: Int
) : Blockable, Sufferable, Destroyable {

    //位置
    //override var x: Int = 100

    //override var y: Int = 100

    //宽高
    override var wight: Int = Config.block

    override var height: Int = Config.block
    override var blood: Int = 3

    //显示行为
    override fun draw() {
        //绘制砖墙
        Painter.drawImage("img/wall.gif", x, y)
    }


    override fun notifySuffer(attackable: Attackable): Array<View>? {
        //println("墙接收到挨打..")
        //砖墙被销毁
        blood -= attackable.attackPower

        //播放声音
        Composer.play("snd\\hit.wav")
        return arrayOf(Blast(x, y))
    }

    override fun isDestroyed(): Boolean = blood <= 0


}