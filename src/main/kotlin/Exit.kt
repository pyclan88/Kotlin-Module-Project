import menu.FileMenu
import menu.FolderMenu
import kotlin.system.exitProcess
import Exit.*

enum class Exit {
    FROM_FOLDER_MENU,
    FROM_FILE_MENU,
    FROM_NOTE_MENU;
}

object ExitRouter {
    internal var status = FROM_FOLDER_MENU

    internal fun executeExit() {
        when (status) {
            FROM_FOLDER_MENU -> exitProcess(0)
            FROM_FILE_MENU -> FolderMenu().showMenu()
            FROM_NOTE_MENU -> FileMenu().showMenu()
        }
    }
}