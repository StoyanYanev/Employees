package PeriodCalculation;

import Employees.IEmployee;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class PeriodCalculator implements IPeriodCalculator {

    /**
     * Calculate period between two employees and checks for overlapping
     * Used formula for overlapping: max(min(EndDate1, EndDate2) - max(StartDate1, StartDate2), 0)
     *
     * @param firstEmployee
     * @param secondEmployee
     * @return period in days
     */
    @Override
    public Long getPeriod(IEmployee firstEmployee, IEmployee secondEmployee) {
        Date minEndDate = this.getMinDateTo(firstEmployee.getDateTo(), secondEmployee.getDateTo());
        Date maxStartDate = this.getMaxDateFrom(firstEmployee.getDateFrom(), secondEmployee.getDateFrom());
        long days = minEndDate.getTime() - maxStartDate.getTime() + 1;
        if (days > 0) {
            return TimeUnit.DAYS.convert(days, TimeUnit.MILLISECONDS);
        }
        return 0L;
    }

    private Date getMinDateTo(Date firstEndPeriod, Date secondEndPeriod) {
        if (firstEndPeriod.before(secondEndPeriod)) {
            return firstEndPeriod;
        }
        return secondEndPeriod;
    }

    private Date getMaxDateFrom(Date firstStartPeriod, Date secondStartPeriod) {
        if (firstStartPeriod.after(secondStartPeriod)) {
            return firstStartPeriod;
        }
        return secondStartPeriod;
    }
}