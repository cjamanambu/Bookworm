package comp3350.bookworm.Acceptance;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import comp3350.bookworm.Presentation.HomePage;
import comp3350.bookworm.R;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class BookPriceTest {
    @Rule
    public ActivityTestRule<HomePage> activityRule = new ActivityTestRule<>(HomePage.class);


    @Test
    public void bookPriceTest() {
        onData(anything()).inAdapterView(withId(R.id.bookList_homepage))
                .atPosition(0)
                .onChildView(withId(R.id.btn_arrayAdapter_book))
                .perform(click());

        onView(withId(R.id.textView_price)).check(matches(not(withText(""))));
    }
}
