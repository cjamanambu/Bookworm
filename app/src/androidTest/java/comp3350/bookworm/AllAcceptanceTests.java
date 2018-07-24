package comp3350.bookworm;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import comp3350.bookworm.Acceptance.AddToShoppingCartTest;
import comp3350.bookworm.Acceptance.BestSellerTest;
import comp3350.bookworm.Acceptance.BookOrderingTest;
import comp3350.bookworm.Acceptance.BookPriceTest;
import comp3350.bookworm.Acceptance.BookSuggestionTest;
import comp3350.bookworm.Acceptance.DescriptionTest;
import comp3350.bookworm.Acceptance.EditProfileTest;
import comp3350.bookworm.Acceptance.HalfPriceTest;
import comp3350.bookworm.Acceptance.OrderHistoryTest;
import comp3350.bookworm.Acceptance.RegistrationTest;
import comp3350.bookworm.Acceptance.SearchListTest;
import comp3350.bookworm.Acceptance.UserLoginTest;
import comp3350.bookworm.Acceptance.VisitorModeTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        DescriptionTest.class,
        UserLoginTest.class,
        AddToShoppingCartTest.class,
        HalfPriceTest.class,
        RegistrationTest.class,
        VisitorModeTest.class,
        BookPriceTest.class,
        OrderHistoryTest.class,
        SearchListTest.class,
        BestSellerTest.class,
        BookSuggestionTest.class,
        EditProfileTest.class,
        BookOrderingTest.class
})
public class AllAcceptanceTests {
}
