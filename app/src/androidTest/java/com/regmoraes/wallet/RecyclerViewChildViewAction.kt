package com.regmoraes.wallet

import android.support.test.espresso.UiController
import android.support.test.espresso.ViewAction
import android.view.View
import org.hamcrest.Matcher

object RecyclerViewChildViewAction {

    fun clickChildViewWithId(id: Int): ViewAction {

        return object : ViewAction {

            override fun getConstraints(): Matcher<View>? = null

            override fun getDescription(): String = "Click on a child view with specified id."

            override fun perform(uiController: UiController, view: View) {
                view.findViewById<View>(id).performClick()
            }
        }
    }

}