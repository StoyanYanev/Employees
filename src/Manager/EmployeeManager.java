package Manager;

import Employees.IEmployee;
import Reader.IReader;
import Reader.Reader;
import javafx.util.Pair;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class EmployeeManager implements IEmployeeManager {

    private IReader employeeReader;
    private Map<Pair<String, String>, Long> employeesWorkingTogether;
    private List<IEmployee> employees;

    public EmployeeManager() {
        this.employeeReader = new Reader();
        this.employeesWorkingTogether = new HashMap<>();
    }

    /**
     * Get list of employees from file and calculate the period of partnership
     *
     * @throws IOException
     * @throws ParseException
     */
    @Override
    public void findTheLongestPartnershipPeriod() throws IOException, ParseException {
        // get list of employees
        this.employees = this.employeeReader.readEmployees();

        this.calculatePartnershipPeriod();

        long maxPeriod = Long.MIN_VALUE;
        Pair<String, String> partnership = new Pair<>(null, null);
        long period;
        for (Pair<String, String> pair : this.employeesWorkingTogether.keySet()) {
            period = this.employeesWorkingTogether.get(pair);
            if (period > maxPeriod) {
                maxPeriod = period;
                partnership = pair;
            }
        }
        if (partnership == null || maxPeriod == 0) {
            System.out.println("No pair with longest period!");
            return;
        }
        System.out.printf("ID of the employees: %s %s working together %d days.", partnership.getKey(), partnership.getValue(), maxPeriod);
    }

    /**
     * Helper function for calculating the period
     */
    private void calculatePartnershipPeriod() {
        IEmployee firstEmployee;
        IEmployee secondEmployee;
        boolean isSameEmployee;
        boolean isSameProject;
        for (int i = 0; i < this.employees.size(); i++) {
            firstEmployee = this.employees.get(i);
            for (int j = i + 1; j < this.employees.size(); j++) {
                secondEmployee = this.employees.get(j);
                isSameEmployee = firstEmployee.getEmployeeID().equals(secondEmployee.getEmployeeID());
                isSameProject = firstEmployee.getProjectID().equals(secondEmployee.getProjectID());
                if (!isSameEmployee && isSameProject) {
                    this.savePair(firstEmployee, secondEmployee);
                }
            }
        }
    }

    /**
     * Make pair of employees and if it is already existing update the period of the partnership
     *
     * @param firstEmployee
     * @param secondEmployee
     */
    private void savePair(IEmployee firstEmployee, IEmployee secondEmployee) {
        long period = this.getPeriod(firstEmployee, secondEmployee);
        Pair<String, String> pair = new Pair<>(firstEmployee.getEmployeeID(), secondEmployee.getEmployeeID());
        Pair<String, String> reversedPair = new Pair<>(secondEmployee.getEmployeeID(), firstEmployee.getEmployeeID());

        if (this.employeesWorkingTogether.containsKey(pair)) {
            long newPeriod = this.employeesWorkingTogether.get(pair) + period;
            this.employeesWorkingTogether.put(pair, newPeriod);
            return;
        } else if (this.employeesWorkingTogether.containsKey(reversedPair)) {
            long newPeriod = this.employeesWorkingTogether.get(reversedPair) + period;
            this.employeesWorkingTogether.put(reversedPair, newPeriod);
            return;
        }
        this.employeesWorkingTogether.put(pair, period);
    }

    /**
     * Calculate period between two employees and checks for overlapping
     * Used formula for overlapping: max(min(EndDate1, EndDate2) - max(StartDate1, StartDate2), 0)
     *
     * @param firstEmployee
     * @param secondEmployee
     * @return period in days
     */
    private Long getPeriod(IEmployee firstEmployee, IEmployee secondEmployee) {
        Date minEndDate = this.getMinDateTo(firstEmployee.getDateTo(), secondEmployee.getDateTo());
        Date maxStartDate = this.getMaxDateFrom(firstEmployee.getDateFrom(), secondEmployee.getDateFrom());
        long days = minEndDate.getTime() - maxStartDate.getTime();
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