package com.bignerdranch.nyerhack

abstract class Monster(
    val name: String,
    val description: String,
    override var healthPoints: Int)
    :Fightable{

    //Fightable fun
    override fun attack(opponent: com.bignerdranch.nyerhack.Fightable): kotlin.Int {
        val damageDealt = damageRoll
        opponent.healthPoints -= damageDealt
        return damageDealt
    }
}