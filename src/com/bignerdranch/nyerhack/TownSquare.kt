package com.bignerdranch.nyerhack

class TownSquare: Room("Skwer miejski") {
    private val bellSound = "DZYNN"
    override val dangerLevel
        get() = super.dangerLevel - 3
    final override fun load() = "Kiedy wszedłeś, mieszkańcy uśmiechają się i witają \n${ringBell()}"

    private fun ringBell() = "Dzwon na wieży obwieszcza Twoje przybycie! $bellSound"
}