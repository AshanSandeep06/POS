package dao.custom;

import dao.CrudDAO;
import model.CustomerDTO;

import java.sql.SQLException;
import java.util.ArrayList;

// CustomerDAO interface - This interface is used for customer table unique methods
public interface CustomerDAO extends CrudDAO<CustomerDTO,String> {
    ArrayList<CustomerDTO> getAllCustomersByAddress(String address) throws SQLException,ClassNotFoundException;
}
