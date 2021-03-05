package com.bignerdranch.nyerhack

import com.bignerdranch.nyerhack.extensions.random as randomizer
import java.io.File

class Player(_name: String,
             override var healthPoints: Int,
             val isBlessed: Boolean,
             private val isImmortal: Boolean)
    : Fightable {

    val homeTown by lazy { selectHomeTown() }
    var name: String = _name
        get() ="${field.capitalize()} z ${homeTown.trim()}"

        private set(value) {
            field = value.trim()
        }
    var upojenieMagiczne = 0
    var currentPosition = Coordinate(0,0)

    init {
        require(healthPoints > 0, {("Wartość punktów życia musi być większa od zera")})
        require(name.isNotBlank(), {"Gracz musi mieć imię"})
    }

    constructor(_name: String): this(_name,
                                    healthPoints = 100,
                                    isBlessed = true,
                                    isImmortal = false)

    //fireball
    fun castFireball(numFireballs: Int = 2, upojenieMagiczne: Int = this.upojenieMagiczne) {
        if (upojenieMagiczne + numFireballs < 50) {
            println("Nagle pojawia się kula ognia! (x$numFireballs)")
        } else {
            println("Nie można rzucić czaru")
        }
        val statusUpojeniaMagicznego = statusUpojenia(upojenieMagiczne + numFireballs)
        println("Status upojenia: $statusUpojeniaMagicznego (${upojenieMagiczne + numFireballs})")
    }

    //status upojenia magicznego
    fun statusUpojenia(upojenieMagiczne: Int = this.upojenieMagiczne) = when(upojenieMagiczne) {
        in 1..10 -> "podchmielony"
        in 11..20 -> "zawiany"
        in 21..30 -> "zalany"
        in 31..40 -> "zalany w trupa"
        in 41..50 -> "nieprzytomny"
        else -> "ponad skalę"
    }
    //Aura
    private fun auraColor(): String =
        if (isBlessed && healthPoints > 50 || isImmortal) formatAuraColor((Math.pow(Math.random(), (110-healthPoints) / 100.0) * 20).toInt()) else "BRAK"

    //Aura
    val auraColor = auraColor()

    //status zdrowia gracza
    fun formatHealthStatus(): String {
        val healthStatus = when (healthPoints) {
            100 -> "cieszy się świetnym zdrowiem"
            in 90..99 -> "ma kilka draśnięć"
            in 75..89 -> if (isBlessed)
                "odniósł kilka pomniejszych ran, ale szybko dochodzi do zdrowia" else "odniósł kilka pomniejszych ran"
            in 15..75 -> "Jest poważnie ranny"
            else -> "Jest w opłakanym stanie"
        }
        return healthStatus
    }
    //kolor aury
    private fun formatAuraColor(karma: Int): String {
        val auraColor = when(karma){
            in 0..5 -> "czerwony"
            in 5..10 -> "Pomarańczowy"
            in 11..15 -> "purpurowy"
            in 16..20 -> "zielonkawy"
            else -> "Brak"
        }
        return auraColor
    }

    //random HomeTown
    private fun selectHomeTown() = File("data/towns.txt")
        .readText()
        .split("\n")
        .randomizer()

    //Fightable values
    override val diceCount = 3
    override val diceSides = 6

    //Fightable fun
    override fun attack(opponent: Fightable): Int {
        val damageDealt = if (isBlessed) {
            damageRoll * 2
        } else {
            damageRoll
        }
        opponent.healthPoints -= damageDealt
        return damageDealt
    }
}