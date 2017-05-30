package com.jakubwyc.mysecretvault.vault.open

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.jakewharton.rxbinding.view.RxView
import com.jakubwyc.mysecretvault.R
import com.jakubwyc.mysecretvault.SystemContext
import com.jakubwyc.mysecretvault.main.MainActivity
import kotlinx.android.synthetic.main.fragment_open_vault.*
import rx.subscriptions.CompositeSubscription

class OpenVaultFragment : Fragment(), OpenVaultView {

    private lateinit var presenter: OpenVaultPresenter
    private lateinit var listener: OpenVaultFragmentListener
    private val subscriptions = CompositeSubscription()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_open_vault, container, false)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        subscriptions.add(RxView.clicks(createVaultButton).subscribe { showCreateVaultScreen() })
        subscriptions.add(RxView.clicks(openVaultButton).subscribe { tryToOpenVault() })
    }

    override fun onResume() {
        super.onResume()
        clearUserData()
    }

    override fun clearUserData() {
        loginEdit.text.clear()
        passwordEdit.text.clear()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        presenter = OpenVaultPresenter(activity.applicationContext as SystemContext)
        presenter.attachView(this)
        listener = context as? OpenVaultFragmentListener
                ?: throw RuntimeException(context.toString() + " must implement OpenVaultFragmentListener")
    }

    override fun onDetach() {
        super.onDetach()
        presenter.detachView()
        subscriptions.clear()
    }

    override fun showCreateVaultScreen() {
        listener.onFragmentChange(MainActivity.VaultScreen.CREATE)
    }

    fun tryToOpenVault() {
        presenter.verifyUserCredentials(loginEdit.text.toString(), passwordEdit.text.toString())
    }

    override fun showVaultScreen() {
        listener.onFragmentChange(MainActivity.VaultScreen.VAULT)
    }

    override fun showMessage(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    interface OpenVaultFragmentListener {
        fun onFragmentChange(screen: MainActivity.VaultScreen)
    }
}
