package comp3350.bookworm;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import comp3350.bookworm.Business.AccountManagerIT;
import comp3350.bookworm.Business.BookManagerIT;
import comp3350.bookworm.Business.ReviewManagerIT;
import comp3350.bookworm.Business.ShoppingCartManagerIT;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AccountManagerIT.class,
        ReviewManagerIT.class,
        ShoppingCartManagerIT.class,
        BookManagerIT.class
})
public class AllIntegrationTests {
}
