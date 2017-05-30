package com.jakubwyc.mysecretvault.model.mapper


interface Mapper<in F, out T> {

    fun map(data: F): T

}
