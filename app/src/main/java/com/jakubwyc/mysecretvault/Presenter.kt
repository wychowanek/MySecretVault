package com.jakubwyc.mysecretvault


interface Presenter<V> {

    fun attachView(view: V)

    fun detachView()

}
