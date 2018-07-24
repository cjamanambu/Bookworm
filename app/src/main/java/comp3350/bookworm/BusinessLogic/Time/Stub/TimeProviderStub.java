package comp3350.bookworm.BusinessLogic.Time.Stub;

import comp3350.bookworm.BusinessLogic.Time.TimeProvider;

public class TimeProviderStub implements TimeProvider {
    @Override
    public boolean isHalfPriceDay() {
        return true;
    }
}
