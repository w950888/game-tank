package game.model

import org.itheima.kotlin.game.core.Painter
import game.Config

/**
 * @author Luke
 * @time 2020/5/2 下午 9:51
 * @version 1.0.0
 * @description
 */
class Grass(override var x: Int, override var y: Int) : View {
    //位置
    //override var x: Int = 100

    //override var y: Int = 100

    //宽高
    override var wight: Int = Config.block

    override var height: Int = Config.block

    //显示行为
    override fun draw() {
        //绘制草坪
        Painter.drawImage("img/grass.gif", x, y)
    }
}