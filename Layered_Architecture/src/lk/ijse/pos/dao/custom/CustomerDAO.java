package lk.ijse.pos.dao.custom;

import lk.ijse.pos.dao.CrudDAO;
import lk.ijse.pos.entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;

// CustomerDAO interface - This interface is used for customer table unique methods
public interface CustomerDAO extends CrudDAO<Customer,String> {
    ArrayList<Customer> getAllCustomersByAddress(String address) throws SQLException,ClassNotFoundException;
}
