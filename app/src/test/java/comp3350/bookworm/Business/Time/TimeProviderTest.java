package comp3350.bookworm.Business.Time;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;

import comp3350.bookworm.BusinessLogic.Time.Real.TimeProviderReal;
import comp3350.bookworm.BusinessLogic.Time.Stub.TimeProviderStub;
import comp3350.bookworm.BusinessLogic.Time.TimeProvider;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class TimeProviderTest {
    private TimeProvider timeProviderReal;
    private TimeProvider timeProviderStub;

    @Before
    public void setup() {
        timeProviderReal = new TimeProviderReal();
        timeProviderStub = new TimeProviderStub();
    }

    @After
    public void tearDown() {
        timeProviderReal = null;
        timeProviderStub = null;
    }

    @Test
    public void timeProviderRealTest() {
        System.out.println("\nStarting timeProviderRealTest");

        if(Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY)
            assertTrue(timeProviderReal.isHalfPriceDay());
        else
            assertFalse(timeProviderReal.isHalfPriceDay());
        System.out.println("Finishing timeProviderRealTest");
    }

    @Test
    public void timeProviderStubTest() {
        System.out.println("\nStarting timeProviderRealTest");
        assertTrue(timeProviderStub.isHalfPriceDay());
        System.out.println("Finishing timeProviderRealTest");
    }
}
