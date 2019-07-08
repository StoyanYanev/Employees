package Reader;

import Employees.Employee;
import Employees.IEmployee;
import Exceptions.InvalidFileFormatException;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Reader implements IReader {

    private static final String DELIMITER = ",";
    private static final int MAX_COLUMN = 4;
    private static final int POSITION_OF_THE_EMPLOYEE_ID = 0;
    private static final int POSITION_OF_THE_PROJECT_ID = 1;
    private static final int POSITION_OF_THE_DATE_FROM = 2;
    private static final int POSITION_OF_THE_DATE_TO = 3;

    private static final String PATTERN = "yyyy-MM-dd";

    /**
     * Reads from file and make list of employees
     *
     * @return list of employees
     * @throws IOException
     * @throws InvalidFileFormatException
     */
    @Override
    public List<IEmployee> readEmployees() throws IOException, ParseException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Please, enter the absolute file's path: ");
        String filePath = bufferedReader.readLine();

        File file = new File(filePath);
        Scanner scanner = new Scanner(file);
        String fileContent;
        String[] splitContent;
        List<IEmployee> employees = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat(PATTERN);
        while (scanner.hasNextLine()) {
            fileContent = scanner.nextLine();
            splitContent = fileContent.split(DELIMITER);
            if (splitContent.length < MAX_COLUMN) {
                throw new InvalidFileFormatException("Invalid file format!");
            }
            IEmployee newEmployee = this.parseData(splitContent, formatter);
            employees.add(newEmployee);
        }
        bufferedReader.close();
        return employees;
    }

    /**
     * Creates employee with data from the file
     *
     * @param splitContent contains information for employee
     * @param formatter    of date
     * @return created employee
     * @throws ParseException
     */
    private IEmployee parseData(String[] splitContent, SimpleDateFormat formatter) throws ParseException {
        String employeeID = splitContent[POSITION_OF_THE_EMPLOYEE_ID].trim();
        String projectID = splitContent[POSITION_OF_THE_PROJECT_ID].trim();
        String dateFrom = splitContent[POSITION_OF_THE_DATE_FROM].trim();
        String dateTo = splitContent[POSITION_OF_THE_DATE_TO].trim();
        Date from = null;
        Date to = null;

        from = formatter.parse(dateFrom);
        // if dateTo is NULL then in the constructor of the Employee will be set the current date
        if (!dateTo.toUpperCase().equals("NULL")) {
            to = formatter.parse(splitContent[POSITION_OF_THE_DATE_TO]);
        }

        return new Employee(employeeID, projectID, from, to);
    }
}