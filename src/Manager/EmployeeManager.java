package Manager;

import Employees.IEmployee;
import PeriodCalculation.IPeriodCalculator;
import PeriodCalculation.PeriodCalculator;
import Reader.IReader;
import Reader.Reader;
import javafx.util.Pair;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;

public class EmployeeManager implements IEmployeeManager {

    private IReader employeeReader;
    private IPeriodCalculator periodCalculator;

    public EmployeeManager() {
        this.employeeReader = new Reader();
        this.periodCalculator = new PeriodCalculator();
    }

    /**
     * Get list of employees from file and calculate the period of partnership
     *
     * @throws IOException
     * @throws ParseException
     */
    @Override
    public void findTheLongestPartnershipPeriod() throws IOException, ParseException {
        Map<Pair<String, String>, Long> employeesWorkingTogether = new HashMap<>();
        // get list of employees
        List<IEmployee> employees = this.employeeReader.readEmployees();

        this.calculatePartnershipPeriod(employees, employeesWorkingTogether);

        long maxPeriod = Long.MIN_VALUE;
        Pair<String, String> partnership = new Pair<>(null, null);
        long period;
        for (Pair<String, String> pair : employeesWorkingTogether.keySet()) {
            period = employeesWorkingTogether.get(pair);
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
     *
     * @param employees                from file
     * @param employeesWorkingTogether
     */
    private void calculatePartnershipPeriod(List<IEmployee> employees, Map<Pair<String, String>, Long> employeesWorkingTogether) {
        IEmployee firstEmployee;
        IEmployee secondEmployee;
        boolean isSameEmployee;
        boolean isSameProject;
        for (int i = 0; i < employees.size(); i++) {
            firstEmployee = employees.get(i);
            for (int j = i + 1; j < employees.size(); j++) {
                secondEmployee = employees.get(j);
                isSameEmployee = firstEmployee.getEmployeeID().equals(secondEmployee.getEmployeeID());
                isSameProject = firstEmployee.getProjectID().equals(secondEmployee.getProjectID());
                if (!isSameEmployee && isSameProject) {
                    this.savePair(firstEmployee, secondEmployee, employeesWorkingTogether);
                }
            }
        }
    }

    /**
     * Make pair of employees and if it is already existing update the period of the partnership
     *
     * @param firstEmployee
     * @param secondEmployee
     * @param employeesWorkingTogether
     */
    private void savePair(IEmployee firstEmployee, IEmployee secondEmployee, Map<Pair<String, String>, Long> employeesWorkingTogether) {
        long period = this.periodCalculator.getPeriod(firstEmployee, secondEmployee);
        Pair<String, String> pair = new Pair<>(firstEmployee.getEmployeeID(), secondEmployee.getEmployeeID());
        Pair<String, String> reversedPair = new Pair<>(secondEmployee.getEmployeeID(), firstEmployee.getEmployeeID());

        if (employeesWorkingTogether.containsKey(pair)) {
            long newPeriod = employeesWorkingTogether.get(pair) + period;
            employeesWorkingTogether.put(pair, newPeriod);
            return;
        } else if (employeesWorkingTogether.containsKey(reversedPair)) {
            long newPeriod = employeesWorkingTogether.get(reversedPair) + period;
            employeesWorkingTogether.put(reversedPair, newPeriod);
            return;
        }
        employeesWorkingTogether.put(pair, period);
    }
}