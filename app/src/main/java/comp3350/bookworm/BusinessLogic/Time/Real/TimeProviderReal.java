package comp3350.bookworm.BusinessLogic.Time.Real;

import java.util.Calendar;

import comp3350.bookworm.BusinessLogic.Time.TimeProvider;

public class TimeProviderReal implements TimeProvider{
    @Override
    public boolean isHalfPriceDay() {
        return Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY;
    }
}
