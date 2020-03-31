package com.cosmo.additionGame;

import kotlin.random.Random

class Operation {

    enum class Operator(sign: String) {
        Add("+"), Remove("-");

        internal val sign: String = sign
            get() = field

        fun getResult(firstValue: Int, secondValue: Int): Int {

            if (this == Add) return firstValue.plus(secondValue)
            if (this == Remove) return firstValue.minus(secondValue)

            throw Exception("Not implemented");
        }
    }

    internal val firstValue: Int = Random.nextInt(0, MAX_NUMBER)
    internal val secondValue: Int = Random.nextInt(0, MAX_NUMBER)

    internal val operator: Operator = if (Random.nextBoolean()) Operator.Add else Operator.Remove
    internal val resultValue: Int = operator.getResult(firstValue, secondValue)

    companion object {
        val MAX_NUMBER: Int = 15
    }
}
