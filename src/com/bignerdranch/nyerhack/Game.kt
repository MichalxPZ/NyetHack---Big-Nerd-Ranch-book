package com.bignerdranch.nyerhack

import java.lang.Exception
import java.lang.IllegalStateException
import kotlin.system.exitProcess


fun main() {
    Game.play()
}

object Game {
    //main loop variable
    var run = true
    val player = Player(_name = "Mochal")
    var currentRoom: Room = TownSquare()
    init {
        println("Witaj, poszukiwaczu przygód!")
        player.castFireball(upojenieMagiczne=player.upojenieMagiczne)
    }
    private var worldMap = listOf(listOf(currentRoom, Room("Gospoda"), Room("Zaplecze Gospody")),
    listOf(Room("Długi korytarz"), Room("Pomiesczenie ogólne"))
    )

    //main LOOP
    fun play() {
        while (run){
            //Pokój
            println(currentRoom.description())
            println(currentRoom.load())
            //stan zdoriwa gracza
            printPlayerStatus(player)
            print("> Wpisz polecenie: ")
            GameInput(readLine()).processCommand()
            Thread.sleep(300)
        }
    }

    //move on map
    private fun move(directionInput: String){
        try {
            val direction = Direction.valueOf(directionInput.toUpperCase())
            val newPosition = direction.updateCoordinate(player.currentPosition)
            if (!newPosition.isInBounds){
                throw IllegalStateException("$direction poza zasięgiem mapy!")
            }
            val newRoom = worldMap[newPosition.y][newPosition.x]
            player.currentPosition = newPosition
            currentRoom = newRoom
            return println("Ok idziesz na $direction do ${newRoom.name}")
        } catch (e: Exception) {
            return println("Niewłaściwy kierunek: $directionInput")
        }
    }

    //mapa
    private fun showMap(){
        for (yIndex in 0..worldMap.lastIndex){
            for (xIndex in 0..worldMap[yIndex].lastIndex){
                if (currentRoom == worldMap[yIndex][xIndex]) {
                    print("X")
                } else {
                    print("O")
                }
            }
            println()
        }
    }

    //exit
    private fun close(){
        run = false
    }

    //pokaz status gracza
    private fun printPlayerStatus(
        player: Player
    ) {
        println("(Aura: ${player.auraColor}) " + "(Pobłogosławiony: ${if (player.isBlessed) "TAK" else "NIE"})")
        println("${player.name} ${player.formatHealthStatus()}")
    }

    private fun fight() = currentRoom.monster?.let {
        while (player.healthPoints > 0 && it.healthPoints > 0) {
            slay(it)
            Thread.sleep(1000)
        }
        "Walka została zakończona"
    } ?: "W tym pomieszczeniu nie ma z czym walczyć"

    //deal DMG
    private fun slay(monster: Monster) {
        println("${monster.name} zadał ${monster.attack(player)} obrażeń")
        println("${player.name} zadał ${player.attack(monster)} obrażeń")
        if (player.healthPoints <= 0) {
            println(">>>>>> Zostałeś pokonany! Dziękuję za grę. <<<<<<")
            exitProcess(0)
        }
        if (monster.healthPoints <= 0) {
            println(">>>>>> ${monster.name} został pokonany! <<<<<<")
            currentRoom.monster = null
        }
    }

    //Command handling
    private class GameInput(arg: String?) {
        private val input = arg ?: ""
        val command = input.split(" ")[0]
        val argument =input.split(" ").getOrElse(1, {" "})

        private fun commandNotFound() {
            println("Nie wiem co chcesz zrobić!")
        }

        fun processCommand(): Any{
            return when(command.toLowerCase()) {
                "walcz" -> fight()
                "idzna" -> move(argument)
                "mapa" -> showMap()
                "wyjdz" -> close()
                else -> commandNotFound()
            }
        }
    }
}