package com.regmoraes.wallet.presentation.market

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.regmoraes.wallet.R
import com.regmoraes.wallet.databinding.ActivityPricesBinding
import com.regmoraes.wallet.presentation.transactions.TransactionsHistoryFragment


class MarketActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityPricesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = DataBindingUtil.setContentView(this, R.layout.activity_prices)

        setSupportActionBar(viewBinding.toolbar)

        setUpFragments()
    }

    private fun setUpFragments() {

        viewBinding.bottomNavigation.setOnNavigationItemSelectedListener { item ->

            var selectedFragment: Fragment? = null

            when (item.itemId) {
                R.id.action_transactions -> selectedFragment = TransactionsHistoryFragment.newInstance()
                R.id.action_market -> selectedFragment = MarketFragment.newInstance()
                //R.id.action_wallet -> selectedFragment = ItemTwoFragment.newInstance()
            }

            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.content, selectedFragment)
            transaction.commit()
            true
        }

        //Manually displaying the first fragment - one time only
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.content, MarketFragment.newInstance())
        transaction.commit()

        //Used to select an item programmatically
        //bottomNavigationView.getMenu().getItem(2).setChecked(true);
    }




}
