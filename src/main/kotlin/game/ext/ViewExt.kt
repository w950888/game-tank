package game.ext

import game.model.View

/**
 * @author Luke
 * @time 2020/5/3 下午 6:31
 * @version 1.0.0
 * @description
 */
fun View.checkCollision(view: View): Boolean {
    return checkCollision(
        x, y, wight, height,
        view.x, view.y, view.wight, view.height
    )

}
