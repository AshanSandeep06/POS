package dao;

import model.CustomerDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerDAO {
    ArrayList<CustomerDTO> getCustomersUsingAddress() throws SQLException, ClassNotFoundException;
}
