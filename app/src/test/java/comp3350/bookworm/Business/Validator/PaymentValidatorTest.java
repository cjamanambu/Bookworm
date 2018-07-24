package comp3350.bookworm.Business.Validator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import comp3350.bookworm.BusinessLogic.Validator.PaymentValidator;
import comp3350.bookworm.Objects.Payment;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class PaymentValidatorTest {
    private PaymentValidator paymentValidator;

    @Before
    public void setup() {
        paymentValidator = new PaymentValidator();
    }

    @After
    public void tearDown() {
        paymentValidator = null;
    }

    @Test
    public void testPaymentValidator() {
        System.out.println("\nStarting testReviewValidator");
        
        assertFalse(paymentValidator.isValidPmtOption("SOMEPLACE", "3412", "sdas", "3423"));
        assertTrue(paymentValidator.isValidPmtOption("RBC", "1234567812345678", "123", "12/2019"));

        System.out.println("Finishing testReviewValidator");
    }

    @Test
    public void testPaymentValidator2() {
        System.out.println("\nStarting testReviewValidator");

        Payment invalidPayment = new Payment("SOMEPLACE", "3412", "sdas", "3423");
        Payment validPayment = new Payment("RBC", "1234567812345678", "123", "12/2019");

        assertFalse(paymentValidator.isValidPmtOption(invalidPayment));
        assertTrue(paymentValidator.isValidPmtOption(validPayment));

        System.out.println("Finishing testReviewValidator");
    }

    @Test
    public void testPaymentValidator3() {
        System.out.println("\nStarting testReviewValidator");

        assertFalse(paymentValidator.isValidCardNumber("123"));
        assertTrue(paymentValidator.isValidCardNumber("12345"));
        assertFalse(paymentValidator.isValidCvv("12"));
        assertTrue(paymentValidator.isValidCvv("123"));
        assertFalse(paymentValidator.isValidExpiry("132000"));
        assertTrue(paymentValidator.isValidExpiry("12/2018"));

        System.out.println("Finishing testReviewValidator");
    }
}
