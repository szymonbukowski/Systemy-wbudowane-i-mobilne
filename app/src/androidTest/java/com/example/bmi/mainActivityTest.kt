package com.example.bmi


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class mainActivityTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun mainActivityTest() {
        val appCompatEditText = onView( withId(R.id.MassInputET) )
        appCompatEditText.perform(scrollTo(), replaceText("21"), closeSoftKeyboard())


        val appCompatEditText4 = onView(    withId(R.id.HeightInputET)    )

        appCompatEditText4.perform(scrollTo(), click())

        val appCompatEditText5 = onView(    withId(R.id.HeightInputET)    )
        appCompatEditText5.perform(scrollTo(), replaceText("132"), closeSoftKeyboard())

        val appCompatEditText6 = onView(
            allOf(
                withId(R.id.HeightInputET), withText("132"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.ScrollView")),
                        0
                    ),
                    4
                )
            )
        )
        appCompatEditText6.perform(pressImeActionButton())

        val appCompatButton = onView(
            allOf(
                withId(R.id.CountButton), withText("COUNT BMI"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.ScrollView")),
                        0
                    ),
                    5
                )
            )
        )
        appCompatButton.perform(scrollTo(), click())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(300)

        val overflowMenuButton = onView(
            allOf(
                withContentDescription("More options"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.action_bar),
                        1
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        overflowMenuButton.perform(click())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(250)

        val appCompatTextView = onView(
            allOf(
                withId(R.id.title), withText("switch units"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.content),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatTextView.perform(click())

        val appCompatEditText7 = onView(
            allOf(
                withId(R.id.MassInputET),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.ScrollView")),
                        0
                    ),
                    2
                )
            )
        )
        appCompatEditText7.perform(scrollTo(), replaceText("110"), closeSoftKeyboard())

        val appCompatEditText8 = onView(
            allOf(
                withId(R.id.MassInputET), withText("110"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.ScrollView")),
                        0
                    ),
                    2
                )
            )
        )
        appCompatEditText8.perform(pressImeActionButton())

        val appCompatEditText9 = onView(
            allOf(
                withId(R.id.HeightInputET),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.ScrollView")),
                        0
                    ),
                    4
                )
            )
        )
        appCompatEditText9.perform(scrollTo(), replaceText("71"), closeSoftKeyboard())

        val appCompatEditText10 = onView(
            allOf(
                withId(R.id.HeightInputET), withText("71"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.ScrollView")),
                        0
                    ),
                    4
                )
            )
        )
        appCompatEditText10.perform(pressImeActionButton())

        val appCompatButton2 = onView(
            allOf(
                withId(R.id.CountButton), withText("COUNT BMI"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.ScrollView")),
                        0
                    ),
                    5
                )
            )
        )
        appCompatButton2.perform(scrollTo(), click())

        val floatingActionButton = onView(
            allOf(
                withId(R.id.bmiInfoFAB),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.ScrollView")),
                        0
                    ),
                    10
                )
            )
        )
        floatingActionButton.perform(scrollTo(), click())

        pressBack()

        pressBack()
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
