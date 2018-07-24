package comp3350.bookworm.Acceptance;

import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import comp3350.bookworm.Presentation.HomePage;
import comp3350.bookworm.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static comp3350.bookworm.util.TestUtils.childAtPosition;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)
@android.support.test.filters.LargeTest
public class OrderHistoryTest {
//    private String username = "test";
//    private String password = "test";

    @Rule
    public ActivityTestRule<HomePage> mActivityTestRule = new ActivityTestRule<>(HomePage.class);

//    @Before
//    public void setup() {
//        Service.getAccountPersistence().deleteAccount(new Account(username, password));
//    }
//
//    @After
//    public void tearDown() {
//        Service.getAccountPersistence().deleteAccount(new Account(username, password));
//    }

    @Test
    public void testOrderHistory() {
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.home_account), withText("Account"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.container),
                                        2),
                                2),
                        isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.btn_login_account_page), withText("Login"),
                        childAtPosition(
                                allOf(withId(R.id.account_visitor),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                0)),
                                2),
                        isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.login_username),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.design.widget.TextInputLayout")),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("admin"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.login_password),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.design.widget.TextInputLayout")),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("admin"), closeSoftKeyboard());

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.btn_login), withText("Login"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.constraint.ConstraintLayout")),
                                        0),
                                2),
                        isDisplayed()));
        appCompatButton3.perform(click());

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.home_account), withText("Account"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.container),
                                        2),
                                2),
                        isDisplayed()));
        appCompatButton4.perform(click());

        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.btn_Purchase_History), withText("Purchase History"),
                        childAtPosition(
                                allOf(withId(R.id.account_logined),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                1)),
                                1),
                        isDisplayed()));
        appCompatButton5.perform(click());

        ViewInteraction listView = onView(
                allOf(withId(R.id.bookList_orderhistory),
                        childAtPosition(
                                allOf(withId(R.id.My_History),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                0),
                        isDisplayed()));
        listView.check(matches(isDisplayed()));

        pressBack();


//
        onView(withId(R.id.btn_account_logout)).perform(click());
//=======
//        ViewInteraction appCompatButton6 = onView(
//                allOf(withId(R.id.btn_account_logout), withText("logout"),
//                        childAtPosition(
//                                allOf(withId(R.id.account_logined),
//                                        childAtPosition(
//                                                withClassName(is("android.widget.RelativeLayout")),
//                                                1)),
//                                5),
//                        isDisplayed()));
//        appCompatButton6.perform(click());
//>>>>>>> ad68c8e48c2aefb38873fa80a6567be18a447a30

    }
}
