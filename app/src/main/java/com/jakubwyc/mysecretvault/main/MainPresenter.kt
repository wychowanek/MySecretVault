package com.jakubwyc.mysecretvault.main

import com.jakubwyc.mysecretvault.Presenter

class MainPresenter : Presenter<MainView> {

    private var view: MainView? = null

    override fun attachView(view: MainView) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }
}
