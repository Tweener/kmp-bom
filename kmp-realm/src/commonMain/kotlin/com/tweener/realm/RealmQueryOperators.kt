package com.tweener.realm

/**
 * @author Vivien Mahe
 * @since 01/09/2024
 */

infix fun <T : Any> String.equalTo(that: T) = RealmQueryConstraint.EqualTo(this, that)

infix fun <T : Any> String.greaterThan(that: T) = RealmQueryConstraint.GreaterThan(this, that)

infix fun <T : Any> String.greaterThanOrEqualTo(that: T) = RealmQueryConstraint.GreaterThanOrEqualTo(this, that)

infix fun <T : Any> String.lessThan(that: T) = RealmQueryConstraint.LessThan(this, that)

infix fun <T : Any> String.lessThanOrEqualTo(that: T) = RealmQueryConstraint.LessThanOrEqualTo(this, that)
