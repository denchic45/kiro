import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.denchic45.kiro.di.appComponent
import com.denchic45.kiro.ui.main.MainScreen
import com.denchic45.kiro.ui.theme.DesktopTheme


fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        DesktopTheme() {
            MainScreen(appComponent.mainComponent)
        }
    }
}
