import javafx.application.Application
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.scene.paint.Color
import org.itheima.kotlin.game.core.Composer
import org.itheima.kotlin.game.core.Painter
import org.itheima.kotlin.game.core.Window

/**
 * @author Luke
 * @time 2020/5/2 下午 7:18
 * @version 1.0.0
 * @description 窗体 继承游戏引擎的窗体
 */
class MyWindow : Window() {
    override fun onCreate() {
        //println("创建窗体")
    }

    override fun onDisplay() {
        //println("onDisplay")
        //窗体渲染时的回调 不停地执行

        Painter.drawImage("img/steel.gif", 0, 0)
        Painter.drawColor(Color.WHITE, 200, 200, 20, 20)

        Painter.drawText("haha",44,44,Color.WHEAT)
    }

    override fun onKeyPressed(event: KeyEvent) {
        //println("onKeyPressed")
        //按键响应
        when (event.code) {
            KeyCode.ENTER -> println("点击了enter")
            KeyCode.L -> Composer.play("snd/bg.wav")
        }
    }

    override fun onRefresh() {
        //println("onRefresh")
        //刷新 做业务逻辑的, 做耗时的操作
    }

}


fun main(args: Array<String>) {
    Application.launch(MyWindow::class.java)
}