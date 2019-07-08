package PeriodCalculation;

import Employees.IEmployee;

public interface IPeriodCalculator {

    Long getPeriod(IEmployee firstEmployee, IEmployee secondEmployee);

}