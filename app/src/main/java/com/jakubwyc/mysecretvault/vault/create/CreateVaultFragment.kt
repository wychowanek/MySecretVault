package com.jakubwyc.mysecretvault.vault.create

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.jakewharton.rxbinding.view.RxView
import com.jakewharton.rxbinding.widget.RxTextView
import com.jakubwyc.mysecretvault.R
import com.jakubwyc.mysecretvault.SystemContext
import com.jakubwyc.mysecretvault.main.MainActivity
import kotlinx.android.synthetic.main.fragment_create_vault.*

class CreateVaultFragment : Fragment(), CreateVaultView {
    private lateinit var presenter: CreateVaultPresenter

    private lateinit var listener: CreateVaultFragmentListener

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_create_vault, container, false)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        RxTextView.textChanges(loginEdit).subscribe { presenter.onLoginChanged(it) }
        RxTextView.textChanges(passwordEdit).subscribe { presenter.onPasswordChanged(it) }
        RxTextView.textChanges(passwordRepeatEdit).subscribe { presenter.onPasswordRepeatChanged(it) }
        RxView.clicks(createVaultButton).subscribe { presenter.createVault() }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        presenter = CreateVaultPresenter(activity.applicationContext as SystemContext)
        presenter.attachView(this)
        listener = context as? CreateVaultFragmentListener
                ?: throw RuntimeException(context.toString() + " must implement CreateVaultFragmentListener")
    }

    override fun onDetach() {
        super.onDetach()
        presenter.detachView()
    }

    override fun showLoginEmptyToast() {
        Toast.makeText(activity, R.string.login_empty, Toast.LENGTH_SHORT).show()
    }

    override fun showPasswordEmptyToast() {
        Toast.makeText(activity, R.string.password_empty, Toast.LENGTH_SHORT).show()
    }

    override fun showPasswordRepeatEmptyToast() {
        Toast.makeText(activity, R.string.password_repeat_empty, Toast.LENGTH_SHORT).show()
    }

    override fun showPasswordsNotEqualToast() {
        Toast.makeText(activity, R.string.passwords_not_equal, Toast.LENGTH_SHORT).show()
    }

    override fun showUserSavedToast() {
        Toast.makeText(activity, R.string.user_saved, Toast.LENGTH_SHORT).show()
    }

    override fun goToMainScreen() {
        listener.onFragmentChange(MainActivity.VaultScreen.OPEN)
    }

    interface CreateVaultFragmentListener {
        fun onFragmentChange(screen: MainActivity.VaultScreen)
    }
}
