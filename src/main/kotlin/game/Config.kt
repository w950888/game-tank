package game

/**
 * @author Luke
 * @time 2020/5/2 下午 8:12
 * @version 1.0.0
 * @description object 单例
 */
object Config {

    /**
     * 方格像素
     */
    val block = 64

    val gameWidth: Int = block * 13
    val gameHeight: Int = block * 13

}