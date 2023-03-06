import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.denchic45.kiro.common.App


fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
