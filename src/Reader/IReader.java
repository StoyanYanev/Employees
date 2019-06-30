package Reader;

import Employees.IEmployee;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface IReader {

    List<IEmployee> readEmployees() throws IOException, ParseException;

}
