package com.bignerdranch.nyerhack

enum class Direction(private val coordinate: Coordinate) {
    POLNOC(Coordinate(0, -1)),
    WSCHOD(Coordinate(1,0)),
    POLUDNIE(Coordinate(0,1)),
    ZACHOD(Coordinate(-1,0));
    fun updateCoordinate(playerCoordinate: Coordinate): Coordinate{
        return coordinate + playerCoordinate
    }
}

data class Coordinate(val x: Int, val y: Int) {
    val isInBounds = x >= 0 && y >= 0
    operator fun plus(other: Coordinate) = Coordinate(x + other.x, y + other.y)
}