package com.jakubwyc.mysecretvault

import android.app.Activity
import android.graphics.Color.LTGRAY
import android.os.Bundle
import android.view.Gravity.CENTER
import android.view.Gravity.CENTER_VERTICAL
import android.view.View.TEXT_ALIGNMENT_CENTER
import org.jetbrains.anko.*

/**
 * Created by Jakub Wychowaniec
 */
class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MainActivityUi().setContentView(this)
    }

    fun startLoginActivity() {
        startActivity<LoginActivity>()
    }

    fun startOpenActivity() {
        startActivity<OpenActivity>()
    }
}

class MainActivityUi : AnkoComponent<MainActivity> {

    override fun createView(ui: AnkoContext<MainActivity>) = with(ui) {
        val vaulted = defaultSharedPreferences.getBoolean("vaulted", false)
        verticalLayout {
            textView {
                textResource = R.string.welcome_text
                gravity = CENTER_VERTICAL
                horizontalPadding = ui.dip(20)
                verticalPadding = ui.dip(10)
                backgroundColor = 0x99CCCCCC.toInt()
                textSize = 25f
                textAlignment = TEXT_ALIGNMENT_CENTER
            }.lparams { margin = 10 }

            if (vaulted) customButton("Open Vault", { owner.startLoginActivity() })
            else customButton("Create Vault", { owner.startOpenActivity() })

            customButton("Srothing LOLZ", { toast("Button pressed") })
        }
    }

    private fun _LinearLayout.customButton(text: String, function: () -> Unit = {}) {
        button(text) {
        }.lparams(width = 600) {
            margin = 10
            gravity = CENTER
        }.style {
            it.backgroundColor = LTGRAY
            it.onClick { function() }
        }
    }
}
