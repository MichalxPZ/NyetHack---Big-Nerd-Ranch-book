package com.bignerdranch.nyerhack

open class Room(val name: String) {
    protected open val dangerLevel = 5
    open fun load() = "Nie ma tu nic ciekawego"
    var monster: Monster? = Goblin()
    fun description() = "Pomieszczenie: $name \npoziom zagrożenia: $dangerLevel \nPotwór: ${monster?.description}"
}