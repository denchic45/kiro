import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import com.denchic45.kiro.di.appComponent
import com.denchic45.kiro.ui.main.MainScreen
import com.denchic45.kiro.ui.theme.DesktopTheme
import java.awt.Toolkit


fun main() = application {
    val size = Toolkit.getDefaultToolkit().screenSize.run {
        DpSize((width / 100 * 80).dp, (height / 100 * 80).dp)
    }
    Window(
        onCloseRequest = ::exitApplication,
        state = WindowState(size = size)
    ) {
        DesktopTheme() {
            MainScreen(appComponent.mainComponent)
        }
    }
}
