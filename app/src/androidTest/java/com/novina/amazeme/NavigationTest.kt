package com.novina.amazeme

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import com.novina.amazeme.ui.MainActivity
import com.novina.amazeme.ui.viewholder.ShowListItemViewHolder
import com.novina.amazeme.utils.waitFor
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class NavigationTest {

    @get: Rule
    val activityTestRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun test_isRecyclerViewVisible_onAppLaunch() {
        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()))
    }

    @Test
    fun test_isProgressbarViewVisible_onAppLaunch() {
        // Note this would fail if device is offline
        onView(withId(R.id.progressBar)).check(matches(isDisplayed()))
    }

    @Test
    fun test_selectListItem_isDetailFragmentVisible() {
        // Make sure the view has been fully populated
        getInstrumentation().waitForIdleSync()
        onView(isRoot()).perform(waitFor(2000))

        onView(withId(R.id.recyclerView)).perform(
            actionOnItemAtPosition<ShowListItemViewHolder>(
                1,
                click()
            )
        )
        onView(withId(R.id.toolbar_layout)).check(matches(isDisplayed()))
    }

    @Test
    fun test_backNavigation_toShowListFragment() {
        // Make sure the view has been fully populated
        getInstrumentation().waitForIdleSync()
        onView(isRoot()).perform(waitFor(2000))

        onView(withId(R.id.recyclerView)).perform(
            actionOnItemAtPosition<ShowListItemViewHolder>(
                1,
                click()
            )
        )
        onView(withId(R.id.toolbar_layout)).check(matches(isDisplayed()))

        pressBack()
        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()))
    }
}