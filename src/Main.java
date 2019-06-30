import Manager.EmployeeManager;
import Manager.IEmployeeManager;

import java.io.IOException;
import java.text.ParseException;

public class Main {
    public static void main(String[] args) {
        try {
            IEmployeeManager employeeManager = new EmployeeManager();
            employeeManager.findTheLongestPartnershipPeriod();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}