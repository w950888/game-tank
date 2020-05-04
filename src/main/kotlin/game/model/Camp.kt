package game.model

import org.itheima.kotlin.game.core.Painter
import game.Config
import game.business.Attackable
import game.business.Blockable
import game.business.Destroyable
import game.business.Sufferable


/**
 * @author Luke
 * @time 2020/5/4 上午 2:19
 * @version 1.0.0
 * @description
 */
class Camp(
    override var x: Int,
    override var y: Int
) : Blockable, Sufferable, Destroyable {


    override var blood: Int = 12

    override var wight: Int = Config.block * 2

    override var height: Int = Config.block + 32


    override fun draw() {
        //血量低于6个时,画的是砖墙
        //血量低于三个时没有墙
        if (blood <= 3) {
            wight = Config.block
            height = Config.block
            x = (Config.gameWidth - Config.block) / 2
            y = Config.gameHeight - Config.block
            Painter.drawImage("img/camp.gif", x, y)
        } else if (blood <= 6) {
            //绘制外围的砖块
            Painter.drawImage("img/wall_small.gif", x, y)
            Painter.drawImage("img/wall_small.gif", x + 32, y)
            Painter.drawImage("img/wall_small.gif", x + 64, y)
            Painter.drawImage("img/wall_small.gif", x + 96, y)

            Painter.drawImage("img/wall_small.gif", x, y + 32)
            Painter.drawImage("img/wall_small.gif", x, y + 64)
            Painter.drawImage("img/wall_small.gif", x + 96, y + 32)
            Painter.drawImage("img/wall_small.gif", x + 96, y + 64)

            Painter.drawImage("img/camp.gif", x + 32, y + 32)
        } else {
            //绘制外围的砖块
            Painter.drawImage("img/steel_small.gif", x, y)
            Painter.drawImage("img/steel_small.gif", x + 32, y)
            Painter.drawImage("img/steel_small.gif", x + 64, y)
            Painter.drawImage("img/steel_small.gif", x + 96, y)

            Painter.drawImage("img/steel_small.gif", x, y + 32)
            Painter.drawImage("img/steel_small.gif", x, y + 64)
            Painter.drawImage("img/steel_small.gif", x + 96, y + 32)
            Painter.drawImage("img/steel_small.gif", x + 96, y + 64)

            Painter.drawImage("img/camp.gif", x + 32, y + 32)
        }


    }


    override fun notifySuffer(attackable: Attackable): Array<View>? {
        //被挨打
        blood -= attackable.attackPower
        if (blood == 3 || blood == 6) {
            val x = x - 32
            val y = y - 32
            return arrayOf(
                Blast(x, y),
                Blast(x + 32, y),
                Blast(x + Config.block, y),
                Blast(x + Config.block + 32, y),
                Blast(x + Config.block * 2, y),
                Blast(x, y + 32),
                Blast(x, y + Config.block),
                Blast(x, y + Config.block + 32),
                Blast(x + Config.block * 2, y + 32),
                Blast(x + Config.block * 2, y + Config.block),
                Blast(x + Config.block * 2, y + Config.block + 32)
            )

        }
        return null
    }

    override fun isDestroyed(): Boolean = blood <= 0
    override fun showDestroy(): Array<View>? {
        return arrayOf(
            Blast(x - 32, y - 32),
            Blast(x, y - 32),
            Blast(x + 32, y - 32),

            Blast(x - 32, y),
            Blast(x, y),
            Blast(x + 32, y),

            Blast(x - 32, y + 32),
            Blast(x, y + 32),
            Blast(x + 32, y + 32)
        )
    }
}