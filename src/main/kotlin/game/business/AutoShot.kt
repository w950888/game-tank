package game.business

import game.model.View

/**
 * @author Luke
 * @time 2020/5/4 上午 1:45
 * @version 1.0.0
 * @description 自动射击
 */
interface AutoShot {
    /**
     * 自动射击
     */
    fun autoShot(): View?
}