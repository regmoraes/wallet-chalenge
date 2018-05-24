package com.regmoraes.wallet.presentation.home

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.regmoraes.wallet.R
import com.regmoraes.wallet.databinding.ActivityPricesBinding
import com.regmoraes.wallet.presentation.market.MarketFragment
import com.regmoraes.wallet.presentation.transactions.TransactionsHistoryFragment
import com.regmoraes.wallet.presentation.wallet.WalletFragment


class HomeActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityPricesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = DataBindingUtil.setContentView(this, R.layout.activity_prices)

        setSupportActionBar(viewBinding.toolbar)

        setUpFragments()
    }

    private fun setUpFragments() {

        val marketFragment =
            MarketFragment.newInstance()
        val walletFragment = WalletFragment.newInstance()
        val transactionsHistoryFragment = TransactionsHistoryFragment.newInstance()

        viewBinding.bottomNavigation.selectedItemId = R.id.action_market

        viewBinding.bottomNavigation.setOnNavigationItemSelectedListener { item ->

            var selectedFragment: Fragment? = null

            when (item.itemId) {
                R.id.action_transactions -> selectedFragment = transactionsHistoryFragment
                R.id.action_market -> selectedFragment = marketFragment
                R.id.action_wallet -> selectedFragment = walletFragment
            }

            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.container, selectedFragment)
            transaction.commit()
            true
        }

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, marketFragment)
        transaction.commit()
    }
}
