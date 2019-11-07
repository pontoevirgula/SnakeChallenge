package com.chsltutorials.snakechallenge

data class Coordinates(
    var x : Int,
    var y : Int
){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        val that = other as Coordinates

        if (x != that.x) return false

        return y == that.y
    }



}


