package com.jakubwyc.mysecretvault

import android.app.Activity
import android.os.Bundle
import android.text.InputType.TYPE_CLASS_TEXT
import android.view.inputmethod.EditorInfo.TYPE_TEXT_VARIATION_PASSWORD
import org.jetbrains.anko.*
import java.util.*
import kotlin.text.Charsets.UTF_8

class OpenActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        OpenActivityUi().setContentView(this)
    }

    fun checkPasswords(pass1: String, pass2: String) {
        val pass1sha = sha512(pass1)
        val pass2sha = sha512(pass2)

        if (Arrays.equals(pass1sha, pass2sha)) {
            toast("Equal!")
            defaultSharedPreferences.edit().apply {
                putString("password", pass1sha.toHexString())
                putBoolean("vaulted", true)
            }.apply()
            startActivity<MainActivity>()
            finish()
        } else {
            toast("Not equal!")
        }
    }
}

class OpenActivityUi : AnkoComponent<OpenActivity> {
    override fun createView(ui: AnkoContext<OpenActivity>) = with(ui) {
        verticalLayout() {
            textView("Password:")
            val password1EditText = editText {
                inputType = TYPE_CLASS_TEXT or TYPE_TEXT_VARIATION_PASSWORD
            }
            textView("Repeat password:")
            val password2EditText = editText {
                inputType = TYPE_CLASS_TEXT or TYPE_TEXT_VARIATION_PASSWORD
            }
            button("Enter").onClick {
                owner.checkPasswords(password1EditText.text.toString(), password2EditText.text.toString())
            }
        }
    }

}