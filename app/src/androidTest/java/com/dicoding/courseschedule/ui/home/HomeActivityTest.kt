package com.dicoding.courseschedule.ui.home

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.junit.Test
import com.dicoding.courseschedule.R

class HomeActivityTest {
    @Test
    fun testAddCourse() {
        ActivityScenario.launch(HomeActivity::class.java)
        onView(withId(R.id.action_add)).perform(click())
        onView(withId(R.id.activity_add_course)).check(matches(isDisplayed()))
    }
}