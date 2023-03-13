import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import com.denchic45.kiro.di.appComponent
import com.denchic45.kiro.ui.auth.AuthScreen
import com.denchic45.kiro.ui.main.MainScreen
import com.denchic45.kiro.ui.theme.DesktopTheme
import java.awt.Toolkit


private val desktopAppComponent = appComponent

fun main() = application {

    val isAuth by desktopAppComponent.authService.isAuthed.collectAsState()

    if (isAuth) {
        val size = Toolkit.getDefaultToolkit().screenSize.run {
            DpSize((width / 100 * 80).dp, (height / 100 * 80).dp)
        }

        Window(
            onCloseRequest = ::exitApplication,
            state = WindowState(size = size)
        ) {
            DesktopTheme() {
                MainScreen(desktopAppComponent.mainComponent)
            }
        }
    } else {
        Window(
            onCloseRequest = ::exitApplication,
            state = WindowState(size = DpSize(400.dp,460.dp)),
            resizable = false,
            title = "Авторизация"
        ) {
            DesktopTheme() {
                AuthScreen(desktopAppComponent.authComponent(desktopAppComponent.componentContext))
            }
        }
    }
}
