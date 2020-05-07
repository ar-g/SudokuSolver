package com.arg.sudokusolver.domain.model

sealed class Lce<out T> {
    class Loading<out T> : Lce<T>() {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false
            return true
        }

        override fun hashCode(): Int {
            return javaClass.hashCode()
        }
    }

    data class Content<out T>(val data: T) : Lce<T>()
    data class Error<out T>(val errorMsg: String) : Lce<T>()
}