package game.model

/**
 * @author Luke
 * @time 2020/5/2 下午 9:53
 * @version 1.0.0
 * @description 显示的视图,定义显示规范
 */
interface View {
    //可以定义属性 让视线类去实现
    //位置
    val x: Int
    val y: Int

    //宽高
    val wight: Int
    val height: Int

    //显示
    fun draw()

    fun checkCollision(
        x1: Int, y1: Int, w1: Int, h1: Int,
        x2: Int, y2: Int, w2: Int, h2: Int
    ): Boolean {

        return when {
            y2 + h2 <= y1 -> {
                //如果阻挡物在运动物体的上方
                false
            }
            y1 + h1 <= y2 -> {
                //如果阻挡物在运动物体的上方
                false
            }
            x2 + w2 <= x1 -> {
                //如果阻挡物在运动物体的左方
                false
            }
            else -> x1 + wight > x2
        }
    }

/*    fun checkCollision(view: View): Boolean {
        return false
    }*/

}