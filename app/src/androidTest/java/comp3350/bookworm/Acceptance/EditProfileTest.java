package comp3350.bookworm.Acceptance;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import comp3350.bookworm.Application.Service;
import comp3350.bookworm.Objects.Account;
import comp3350.bookworm.Objects.Exceptions.DuplicateUsernameException;
import comp3350.bookworm.Presentation.HomePage;
import comp3350.bookworm.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static comp3350.bookworm.util.TestUtils.childAtPosition;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)
@android.support.test.filters.LargeTest
public class EditProfileTest {
    private String beforeEditUsername = "before";
    private String beforeEditEmail = "before@bookworm.ca";
    private String beforeEditPassword = "before";
    private String afterEditUsername = "after";
    private String afterEditEmail = "after@bookworm.ca";
    private String afterEditPassword = "after";

    @Rule
    public ActivityTestRule<HomePage> mActivityTestRule = new ActivityTestRule<>(HomePage.class);

    @Before
    public void setup() {
        Service.getAccountPersistence().deleteAccount(new Account(beforeEditUsername, beforeEditPassword));
        Service.getAccountPersistence().deleteAccount(new Account(afterEditUsername, afterEditPassword));

        try {
            Service.getAccountPersistence().insertAccount(new Account(beforeEditUsername, beforeEditPassword, beforeEditEmail));
        } catch (DuplicateUsernameException e) {
            // this line shouldn't be executed.
        }
    }

    @After
    public void tearDown() {
        Service.getAccountPersistence().deleteAccount(new Account(beforeEditUsername, beforeEditPassword));
        Service.getAccountPersistence().deleteAccount(new Account(afterEditUsername, afterEditPassword));
    }

    @Test
    public void homePageTest() {
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
        appCompatEditText.perform(replaceText(beforeEditUsername), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.login_password),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.design.widget.TextInputLayout")),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText(beforeEditPassword), closeSoftKeyboard());

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

//<<<<<<< HEAD
//        ViewInteraction appCompatButton5 = onView(
//                allOf(withId(R.id.btn_Edit_Profie), withText("Edit profie"),
//                        childAtPosition(
//                                allOf(withId(R.id.account_logined),
//                                        childAtPosition(
//                                                withClassName(is("android.widget.RelativeLayout")),
//                                                1)),
//                                3),
//                        isDisplayed()));
//        appCompatButton5.perform(click());
        onView(withId(R.id.btn_Edit_Profie)).perform(click());
//=======
//        ViewInteraction appCompatButton5 = onView(
//                allOf(withId(R.id.btn_Edit_Profie), withText("Edit profile"),
//                        childAtPosition(
//                                allOf(withId(R.id.account_logined),
//                                        childAtPosition(
//                                                withClassName(is("android.widget.RelativeLayout")),
//                                                1)),
//                                3),
//                        isDisplayed()));
//        appCompatButton5.perform(click());
//>>>>>>> ad68c8e48c2aefb38873fa80a6567be18a447a30

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.new_name),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText(afterEditUsername), closeSoftKeyboard());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.new_password),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        1),
                                1),
                        isDisplayed()));
        appCompatEditText4.perform(replaceText(afterEditPassword), closeSoftKeyboard());

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.new_email_addr),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        2),
                                1),
                        isDisplayed()));
        appCompatEditText5.perform(replaceText(afterEditEmail), closeSoftKeyboard());

        ViewInteraction appCompatButton6 = onView(
                allOf(withId(R.id.confirm_edit_btn), withText("Confirm"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        4),
                                0),
                        isDisplayed()));
        appCompatButton6.perform(click());

//        ViewInteraction textView = onView(
//                allOf(withId(R.id.account_uername), withText("Hi there, NEW. Welcome back."),
//                        childAtPosition(
//                                allOf(withId(R.id.account_logined),
//                                        childAtPosition(
//                                                IsInstanceOf.<View>instanceOf(android.widget.RelativeLayout.class),
//                                                0)),
//                                0),
//                        isDisplayed()));
//        textView.check(matches(withText("Hi there, NEW. Welcome back.")));

//<<<<<<< HEAD
//        ViewInteraction appCompatButton7 = onView(
//                allOf(withId(R.id.btn_account_logout), withText("logout"),
//                        childAtPosition(
//                                allOf(withId(R.id.account_logined),
//                                        childAtPosition(
//                                                withClassName(is("android.widget.RelativeLayout")),
//                                                1)),
//                                4),
//                        isDisplayed()));
//        appCompatButton7.perform(click());
//=======
//        ViewInteraction appCompatButton7 = onView(
//                allOf(withId(R.id.btn_account_logout), withText("logout"),
//                        childAtPosition(
//                                allOf(withId(R.id.account_logined),
//                                        childAtPosition(
//                                                withClassName(is("android.widget.RelativeLayout")),
//                                                1)),
//                                5),
//                        isDisplayed()));
//        appCompatButton7.perform(click());
//>>>>>>> ad68c8e48c2aefb38873fa80a6567be18a447a30

//        onView(withId(R.id.home_account)).perform(click());
        onView(withId(R.id.btn_account_logout)).perform(click());

    }


}
