package menu

import Exit
import ExitRouter
import Folder

class FolderMenu : AbstractMenu() {

    companion object {
        internal var folderName = ""
    }

    override fun inputAndCheckCommand(): String {
        var input = ""
        var restriction = true
        while (restriction) {
            print("> ")
            input = readln()
            if (input.toIntOrNull() !in 0..FolderRepository.archive.lastIndex + 2) {
                println("Такого пункта нет\nВведите число от 0 до ${FolderRepository.archive.lastIndex + 2}")
            } else restriction = false
        }
        return input.trim()
    }

    override fun createNewOne() {
        println("\nВведите название архива:")
        val newFolderName = checkName()
        val newArchive = Folder(newFolderName, mutableListOf())
        FolderRepository.archive.add(newArchive)
        showMenu()
    }

    override fun showMenu() {
        println("folderName = ${FileMenu().folderName}")
        println("\nСписок архивов:")
        val menuList = mutableMapOf("0. Создать архив" to { createNewOne() })
        for (i in FolderRepository.archive.indices) {
            menuList["${i + 1}. ${FolderRepository.archive[i].name}"] = {
                ExitRouter.status = Exit.FROM_FILE_MENU
                folderName = FolderRepository.archive[i].name
                FileMenu().showMenu()
            }
        }
        menuList["${menuList.size}. Выход"] = {
            ExitRouter.status = Exit.FROM_FOLDER_MENU
            ExitRouter.executeExit()
        }
        menuList.forEach { println(it.key) }
        println("\nВведите число от 0 до ${FolderRepository.archive.lastIndex + 2}")
        val input = inputAndCheckCommand()
        val selectedOption = menuList.keys.find { it.startsWith(input) }
        menuList[selectedOption]?.invoke()
    }

    override fun checkName(): String {
        var newFolderName = ""
        var restriction = true
        while (restriction) {
            print("> ")
            newFolderName = readln()
            val isEmpty = newFolderName.trim().isEmpty()
            val isUnique = checkIfNameUnique(newFolderName)
            if (isEmpty) {
                println(
                    "Название должно содержать минимум один символ\n" +
                            "Введите название архива:"
                )
            } else if (isUnique) {
                println(
                    "Архив с таким названием уже существует\n" +
                            "Введите другое название архива:"
                )
            } else restriction = false
        }
        return newFolderName
    }

    override fun checkIfNameUnique(newName: String): Boolean {
        var notUnique = false
        for (i in FolderRepository.archive) {
            if (i.name == newName) notUnique = true
        }
        return notUnique
    }
}