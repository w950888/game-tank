package game.model

import org.itheima.kotlin.game.core.Painter
import game.Config
import game.business.Destroyable

/**
 * @author Luke
 * @time 2020/5/3 下午 7:10
 * @version 1.0.0
 * @description
 */
class Blast(override val x: Int, override val y: Int) : Destroyable {


    override val wight: Int = Config.block

    override val height: Int = Config.block

    private val imagePath = arrayListOf<String>()
    private var index: Int = 0

    init {
        (1..32).forEach {
            imagePath.add("img/blast_${it}.png")
        }

    }

    override fun draw() {
        val i = index % imagePath.size
        Painter.drawImage(imagePath[i], x, y)
        index++

    }

    override fun isDestroyed(): Boolean {
        return index >= imagePath.size
    }

}