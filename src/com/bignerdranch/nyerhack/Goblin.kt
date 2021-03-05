package com.bignerdranch.nyerhack

class Goblin(
    name: String = "Goblin",
    description: String = "Wyjątkowo paskudny goblin",
    healthPoints: Int = 50)
    : Monster(name, description, healthPoints) {

    //Fightable values init
    override val diceCount: Int = 2
    override val diceSides: Int = 8
}