package menu

import Exit
import ExitRouter
import File

class NoteMenu(var file: File) {

    fun showMenu() {
        println("\nАрхив \"${FileMenu().folderName}\"\nЗаметка \"${file.name}\":")
        println(file.note)
        println("\n0. Выход")
        inputAndCheckCommand()
        ExitRouter.status = Exit.FROM_NOTE_MENU
        ExitRouter.executeExit()
    }

    private fun inputAndCheckCommand() {
        var restriction = true
        while (restriction) {
            print("> ")
            val input = readln()
            if (input.toIntOrNull() != 0) {
                println("Такого пункта нет\nВведите 0 для выхода")
            } else restriction = false
        }
    }

}