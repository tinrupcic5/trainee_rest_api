package com.bitroot.trainee.restapi.logging

abstract class AbstractStringId(val value: String) {
    override fun equals(other: Any?): Boolean = other != null && this::class == other::class && (other as AbstractStringId).value == value

    override fun hashCode(): Int = value.hashCode()

    override fun toString(): String = "${this::class.simpleName}($value)"
}
