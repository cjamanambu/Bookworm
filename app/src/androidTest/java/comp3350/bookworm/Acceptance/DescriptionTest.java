package comp3350.bookworm.Acceptance;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.ListView;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import comp3350.bookworm.Presentation.HomePage;
import comp3350.bookworm.R;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;

//import java.io.File;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class DescriptionTest {

    @Rule
    public ActivityTestRule<HomePage> activityRule = new ActivityTestRule<>(HomePage.class);


    @Test
    public void testReadPreview() {
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.bookList_homepage))
        .atPosition(0)
        .onChildView(withId(R.id.btn_arrayAdapter_book))
        .perform(click());

        onView(withId(R.id.btn_preview)).perform(click());

        onView(withId(R.id.textView_previewContent)).check(matches(isDisplayed()));
    }

    @Test
    public void testWriteReview() {
        onData(anything()).inAdapterView(withId(R.id.bookList_homepage))
                .atPosition(1)
                .onChildView(withId(R.id.btn_arrayAdapter_book))
                .perform(click());

        onView(withId(R.id.editText_accName)).perform(typeText("codeX Manager"));
        onView(withId(R.id.editText_rating)).perform(typeText("5"));
        onView(withId(R.id.editText_content)).perform(typeText(
                "I personally like this book and would like to recommend this book to anyone for sure"));

        closeSoftKeyboard();
        onView(withId(R.id.btn_reviewSubmit)).perform(click());

        final int[] numAdapterItems = new int[1];

        onView(withId(R.id.list_reviews)).check(matches(new TypeSafeMatcher<View> () {
            @Override
            public boolean matchesSafely(View view) {
                ListView listView = (ListView) view;

                //here we assume the adapter has been fully loaded already
                numAdapterItems[0] = listView.getAdapter().getCount();

                return true;
            }

            @Override
            public void describeTo(Description description) {

            }
        }));

        onData(anything()).inAdapterView(withId(R.id.list_reviews))
                .atPosition(numAdapterItems[0] - 1)
                .onChildView(withId(R.id.text_content))
                .check(matches(withText("I personally like this book and would like to recommend this book to anyone for sure")));
    }
}
