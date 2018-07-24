package comp3350.bookworm.Acceptance;


import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.matcher.ViewMatchers;
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
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static comp3350.bookworm.util.TestUtils.childAtPosition;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class BookOrderingTest {

    @Rule
    public ActivityTestRule<HomePage> mActivityTestRule = new ActivityTestRule<>(HomePage.class);

    @Test
    public void bookOrderingTest() {
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.bookList_homepage))
                .atPosition(0)
                .onChildView(withId(R.id.btn_arrayAdapter_book))
                .perform(click());

        pressBack();
        pressBack();

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.home_account), withText("Account"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.container),
                                        2),
                                2),
                        isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.btn_login_account_page), withText("Login"),
                        childAtPosition(
                                allOf(withId(R.id.account_visitor),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                0)),
                                2),
                        isDisplayed()));
        appCompatButton3.perform(click());

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

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.btn_login), withText("Login"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.constraint.ConstraintLayout")),
                                        0),
                                2),
                        isDisplayed()));
        appCompatButton4.perform(click());

        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.bookList_homepage))
                .atPosition(3)
                .onChildView(withId(R.id.btn_arrayAdapter_book))
                .perform(click());

        ViewInteraction appCompatButton6 = onView(
                allOf(withId(R.id.btn_buy), withText("add to cart"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        appCompatButton6.perform(click());

        ViewInteraction appCompatButton7 = onView(
                allOf(withId(R.id.btn_back_to_list), withText("go back to book list"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.custom),
                                        0),
                                1),
                        isDisplayed()));
        appCompatButton7.perform(click());

        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.bookList_homepage))
                .atPosition(7)
                .onChildView(withId(R.id.btn_arrayAdapter_book))
                .perform(click());

        ViewInteraction appCompatButton9 = onView(
                allOf(withId(R.id.btn_buy), withText("add to cart"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        appCompatButton9.perform(click());

        ViewInteraction appCompatButton10 = onView(
                allOf(withId(R.id.btn_check_cart), withText("check my shopping cart"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.custom),
                                        0),
                                2),
                        isDisplayed()));
        appCompatButton10.perform(click());

        ViewInteraction appCompatButton11 = onView(
                allOf(withId(R.id.confirm_btn2), withText("Place Order"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatButton11.perform(click());

        ViewInteraction appCompatButton12 = onView(
                allOf(withId(R.id.confirm_btn), withText("Confirm"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                5),
                        isDisplayed()));
        appCompatButton12.perform(click());

//<<<<<<< HEAD
//        ViewInteraction appCompatButton13 = onView(
//                allOf(withId(R.id.home_account), withText("Account"),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(R.id.container),
//                                        2),
//                                2),
//                        isDisplayed()));
//        appCompatButton13.perform(click());
//
//        ViewInteraction appCompatButton14 = onView(
//                allOf(withId(R.id.btn_account_logout), withText("logout"),
//                        childAtPosition(
//                                allOf(withId(R.id.account_logined),
//                                        childAtPosition(
//                                                withClassName(is("android.widget.RelativeLayout")),
//                                                1)),
//                                4),
//                        isDisplayed()));
//        appCompatButton14.perform(click());

        onView(withId(R.id.home_account)).perform(click());
        onView(withId(R.id.btn_account_logout)).perform(click());
//=======
//        ViewInteraction appCompatButton13 = onView(
//                allOf(withId(R.id.home_account), withText("Account"),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(R.id.container),
//                                        2),
//                                2),
//                        isDisplayed()));
//        appCompatButton13.perform(click());
//
//        ViewInteraction appCompatButton14 = onView(
//                allOf(withId(R.id.btn_account_logout), withText("logout"),
//                        childAtPosition(
//                                allOf(withId(R.id.account_logined),
//                                        childAtPosition(
//                                                withClassName(is("android.widget.RelativeLayout")),
//                                                1)),
//                                5),
//                        isDisplayed()));
//        appCompatButton14.perform(click());
//>>>>>>> ad68c8e48c2aefb38873fa80a6567be18a447a30

    }

}
