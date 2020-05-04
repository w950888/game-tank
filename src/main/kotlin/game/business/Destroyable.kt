package game.business

import game.model.View

/**
 * @author Luke
 * @time 2020/5/3 下午 5:32
 * @version 1.0.0
 * @description 可销毁
 */
interface Destroyable : View {

    /**
     * 判断是否销毁了
     */
    fun isDestroyed(): Boolean

    /**
     * 爆炸功能
     */
    fun showDestroy(): Array<View>?{
        return null
    }

}