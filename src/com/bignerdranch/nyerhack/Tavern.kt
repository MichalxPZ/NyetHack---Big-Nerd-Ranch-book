package com.bignerdranch.nyerhack

import java.io.File
import com.bignerdranch.nyerhack.extensions.random as randomizer

const val TAVERN_NAME = "Szynk Hipolit'a"
var drinkLeft = 5.0
val patronList = mutableListOf<String>("Ela", "Mordeczka", "Zocha")
val menuList = File("data/menu.txt").readText().split("\n")
val patronGold = mutableMapOf("Ela" to 10.5, "Mordeczka" to 8.0, "Zocha" to 5.5)

fun main(args: Array<String>) {
    patronList.forEachIndexed{ index, patron ->
        println("$patron! Dobry wieczór - jesteś ${index + 1}. w kolejce.")
        placeOrder(
            patron,
            menuList.randomizer()
        )
        println()
    }
}

//handle balance while purhasing
fun performPurchase(price: Double, patronName: String): Boolean {
    var bought = false
    displayGold(patronName)
    val totalPurse = patronGold.getOrDefault(
        patronName,
        0.0
    )
    if (totalPurse - price > 0) {
        patronGold[patronName] = totalPurse - price
        bought = true
    } else {
        println("Za mało złota")
    }
    displayGold(patronName)
    return bought
}

//displat gold of Player
private fun displayGold(patronName: String) {
    val money = patronGold.getOrDefault(
        patronName,
    0.0)
    println("W sakiewce: $money")
}

//display how much drink lefi in Tavern
private fun displayDrink() {
    println("W beczce zostało $drinkLeft garncy trunku")
}

//Order drink
fun placeOrder(patronName: String, menuData: String) {
    val indexOfApostrophe = TAVERN_NAME.indexOf('\'')
    val tavernMaser = TAVERN_NAME.substring(6 until indexOfApostrophe)
    val (type, name, price) = menuData.split(", ")
    println("$patronName i $tavernMaser rozmawiają o zamówieniu")
    if (drinkLeft >= 0.125) {
        println("$patronName kupuje $name ($type) za $price")
        val bought =
            performPurchase(price.toDoubleOrNull() ?: 0.0, patronName)
        if (bought) {
            drinkLeft -= 0.125
            if (name == "Oddech Smoka") {
                val phrase = "No... Ależ doskonały jest ten $name!"
                println("$patronName konstatuje z zachwytem: \n${toDragonSpeak(
                    phrase
                )}")
            } else {
                println("$patronName dziękuje za $name")
            }
        }
    } else {
        println("Nie ma już trunku")
    }
    displayDrink()
}

//translate speach
private fun toDragonSpeak(phrase: String): String{
    val phrase = phrase.replace(Regex("[aeiouAEIOU]")) {
        when (it.value) {
            "a" -> "4"
            "e" -> "3"
            "i" -> "1"
            "o" -> "0"
            "u" -> "|_|"
            "A" -> "4"
            "E" -> "3"
            "I" -> "1"
            "O" -> "0"
            "U" -> "|_|"
            else -> it.value
        }
    }
    return phrase
}