package comp3350.bookworm;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import comp3350.bookworm.Business.AccountManagerTest;
import comp3350.bookworm.Business.BookManagerTest;
import comp3350.bookworm.Business.ReviewManagerTest;
import comp3350.bookworm.Business.ShoppingCartManagerTest;
import comp3350.bookworm.Business.Time.TimeProviderTest;
import comp3350.bookworm.Business.Validator.AccountValidatorTest;
import comp3350.bookworm.Business.Validator.BookValidatorTest;
import comp3350.bookworm.Business.Validator.PaymentValidatorTest;
import comp3350.bookworm.Business.Validator.ReviewValidatorTest;
import comp3350.bookworm.Objects.AccountTest;
import comp3350.bookworm.Objects.BookTest;
import comp3350.bookworm.Objects.ReviewTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AccountTest.class,
        AccountManagerTest.class,
        BookManagerTest.class,
        ShoppingCartManagerTest.class,
        BookTest.class,
        ReviewTest.class,
        ReviewManagerTest.class,
        AccountValidatorTest.class,
        BookValidatorTest.class,
        ReviewValidatorTest.class,
        PaymentValidatorTest.class,
        TimeProviderTest.class
})

public class AllUnitTests
{

}
