package com.guvyerhopkins.livefront.ui.detail

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.guvyerhopkins.livefront.R
import com.guvyerhopkins.livefront.core.network.Photo
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class ImageDetailActivityTest {

    @RelaxedMockK
    private lateinit var photo: Photo

    private lateinit var intent: Intent

    @get:Rule
    private lateinit var activityScenario: ActivityScenario<ImageDetailActivity>

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        intent =
            ImageDetailActivity.createIntent(ApplicationProvider.getApplicationContext(), photo)
        activityScenario = ActivityScenario.launch(intent)
    }

    @Test
    fun verifyPhotographerUrlShown() {
        activityScenario.onActivity {
            onView(withId(R.id.details_photographer_url_tv)).check(matches(withText(photo.photographerUrl)))
        }
    }

    @Test
    fun verifyPhotographerShown() {
        activityScenario.onActivity {
            onView(withId(R.id.details_photographer_tv)).check(matches(withText(photo.photographer)))
        }
    }

    @After
    fun tearDown() {
        activityScenario.close()
    }
}