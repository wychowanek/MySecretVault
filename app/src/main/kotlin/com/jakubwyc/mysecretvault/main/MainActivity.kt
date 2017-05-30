package com.jakubwyc.mysecretvault.main

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity

import com.jakubwyc.mysecretvault.R
import com.jakubwyc.mysecretvault.vault.VaultActivity
import com.jakubwyc.mysecretvault.vault.create.CreateVaultFragment
import com.jakubwyc.mysecretvault.vault.open.OpenVaultFragment

class MainActivity : AppCompatActivity(), MainView, OpenVaultFragment.OpenVaultFragmentListener, CreateVaultFragment.CreateVaultFragmentListener {

    enum class VaultScreen {
        OPEN, CREATE, VAULT
    }

    private lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_container)

        presenter = MainPresenter()
        presenter.attachView(this)
        showOpenVaultScreen()
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }

    override fun showCreateVaultScreen() {
        replaceFragment(CreateVaultFragment(), true)
    }

    override fun showOpenVaultScreen() {
        replaceFragment(OpenVaultFragment(), false)
    }

    override fun showVaultScreen() {
        val intent = Intent(this, VaultActivity::class.java)
        startActivity(intent)
    }

    private fun replaceFragment(fragment: Fragment, addToBackStack: Boolean) {
        val fragmentTransaction = supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, fragment)
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(null)
        }
        fragmentTransaction.commit()
    }

    override fun onFragmentChange(screen: VaultScreen) {
        when (screen) {
            MainActivity.VaultScreen.CREATE -> showCreateVaultScreen()
            MainActivity.VaultScreen.OPEN -> showOpenVaultScreen()
            MainActivity.VaultScreen.VAULT -> showVaultScreen()
        }
    }
}
