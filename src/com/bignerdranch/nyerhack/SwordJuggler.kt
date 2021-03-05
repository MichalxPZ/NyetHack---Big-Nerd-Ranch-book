package com.bignerdranch.nyerhack

import java.lang.Exception
import java.lang.IllegalStateException

fun main(args: Array<String>) {
    var swordsJuggling: Int? = null
    val isJugglingProficient = (1..3).shuffled().last() == 3
    if (isJugglingProficient) {
        swordsJuggling = 2
    }
    try {
        proficiencyCheck(swordsJuggling)
        swordsJuggling = swordsJuggling!!.plus(1)
    } catch (e: Exception) {
        println(e)
    }
    println("Liczba mieczy do żonglowania: $swordsJuggling!")
}

fun proficiencyCheck(swordsJuggling: Int?) {
    checkNotNull(swordsJuggling, {"Postać nie potrafi żonglować mieczami!"})
}

class UnskilledSwordJugglerException() : IllegalStateException("Postać nie potrafi żonglować mieczami")