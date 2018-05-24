package com.regmoraes.wallet.persistence

import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import java.io.IOException


/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 */
@RunWith(AndroidJUnit4::class)
abstract class BaseAppDatabaseTests {

    protected lateinit var appDatabase: AppDatabase

    @Before
    open fun setUp() {

        val context = InstrumentationRegistry.getTargetContext()

        appDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        appDatabase.close()
    }
}