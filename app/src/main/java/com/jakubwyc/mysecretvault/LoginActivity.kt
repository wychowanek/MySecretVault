package com.jakubwyc.mysecretvault

import android.app.Activity
import android.os.Bundle
import android.text.InputType.TYPE_CLASS_TEXT
import android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
import org.jetbrains.anko.*
import java.util.*

class LoginActivity : Activity(), AnkoLogger {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LoginActivityUi().setContentView(this)
    }

    fun login(password: String) {
        val passwordSha = defaultSharedPreferences.getString("password", "")
        if (Arrays.equals(sha512(password), passwordSha.hexToByteArray())) {
            toast("Equal!")
        } else {
            toast("Not equal!")
        }
    }
}

class LoginActivityUi : AnkoComponent<LoginActivity> {
    override fun createView(ui: AnkoContext<LoginActivity>) = with(ui) {
        verticalLayout() {
            textView("password")
            val passwordTextView = editText {
                inputType = TYPE_CLASS_TEXT or TYPE_TEXT_VARIATION_PASSWORD
            }
            button("enter").onClick { owner.login(passwordTextView.text.toString()) }
        }
    }

}