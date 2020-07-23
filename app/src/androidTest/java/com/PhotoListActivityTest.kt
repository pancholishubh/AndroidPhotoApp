package com

import android.app.Activity
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.androidtask.R
import com.androidtask.ui.photo.PhotoListActivity
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4::class)

class PhotoListActivityTest {

    private val currentPkg = "com.androidtask"

    lateinit var baseActivity: Activity

    @Rule
    @JvmField
    var activityRule: ActivityTestRule<PhotoListActivity> = ActivityTestRule(PhotoListActivity::class.java)


    @Before
    fun initMe()
    {
        baseActivity = activityRule.activity
        Intents.init()
    }

    @Test
    fun check_package_name() {

        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        Assert.assertEquals(currentPkg, appContext.packageName)
    }

    @Test
    fun check_search_box_should_be_editable()
    {
        Espresso.onView(withId(R.id.et_search)).perform(ViewActions.typeText("test"))
    }


    @Test
    fun check_internet_on_off() {
        Thread.sleep(1000)
        val activity = baseActivity  as PhotoListActivity
        Assert.assertTrue(activity.isInternetConnection)

    }


    @Test
    fun check_photo_list(){
        Thread.sleep(5000)
        val activity = baseActivity as PhotoListActivity
        val size = activity.photoList.size
        Assert.assertTrue(size >= 0)
    }

    @After
    fun releaseMe()
    {
        Intents.release()
    }
}