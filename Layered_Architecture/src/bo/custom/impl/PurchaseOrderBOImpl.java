package bo.custom.impl;

import bo.custom.PurchaseOrderBO;
import dao.CrudDAO;
import dao.DAOFactory;
import dao.SuperDAO;
import dao.custom.*;
import dao.custom.impl.*;
import db.DBConnection;
import model.CustomerDTO;
import model.ItemDTO;
import model.OrderDTO;
import model.OrderDetailDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PurchaseOrderBOImpl implements PurchaseOrderBO {

    // Exposed the object creation logic
    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER); // Hide the object creation logic using DAOFactory using factory design pattern
    ItemDAO itemDAO = (ItemDAO)DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);
    OrderDAO orderDAO = (OrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER);
    OrderDetailDAO orderDetailDAO = (OrderDetailDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDERDETAIL);
    JoinQueryDAO queryDAO = (JoinQueryDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.JOINQUERYDAO);

    @Override
    public boolean purchaseOrder(String orderId, LocalDate orderDate, String customerId, List<OrderDetailDTO> orderDetails) throws SQLException, ClassNotFoundException {

        /*Transaction*/
        Connection connection = null;
        connection = DBConnection.getDbConnection().getConnection();
        boolean orderIsExists = orderDAO.IsExist(orderId);
        if (orderIsExists) {

        }
        connection.setAutoCommit(false);

        OrderDTO order = new OrderDTO(orderId, orderDate, customerId);
        boolean orderIsSaved = orderDAO.save(order);

        if (!orderIsSaved) {
            connection.rollback();
            connection.setAutoCommit(true);
            return false;
        }

        for (OrderDetailDTO detail : orderDetails) {
            boolean orderDetailsIsSaved = orderDetailDAO.save(new OrderDetailDTO(orderId, detail.getItemCode(), detail.getUnitPrice(), detail.getQty()));

            if (!orderDetailsIsSaved) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }

            ItemDTO item = itemDAO.search(detail.getItemCode());
            item.setQtyOnHand(item.getQtyOnHand() - detail.getQty());

            boolean update = itemDAO.update(new ItemDTO(item.getCode(), item.getDescription(), item.getUnitPrice(), item.getQtyOnHand()));

            if (!update) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }
        }

        connection.commit();
        connection.setAutoCommit(true);
        return true;
    }

    @Override
    public CustomerDTO searchCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.search(id);
    }

    @Override
    public ItemDTO searchItem(String code) throws SQLException, ClassNotFoundException {
        return itemDAO.search(code);
    }

    @Override
    public boolean itemIsExists(String code) throws SQLException, ClassNotFoundException {
        return itemDAO.IsExist(code);
    }

    @Override
    public boolean customerIsExists(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.IsExist(id);
    }

    @Override
    public String generateNewOrderId() throws SQLException, ClassNotFoundException {
        return orderDAO.generateNewId();
    }

    @Override
    public ArrayList<CustomerDTO> getAllCustomerIds() throws SQLException, ClassNotFoundException {
        return customerDAO.getAll();
    }

    @Override
    public ArrayList<ItemDTO> getAllItemCodes() throws SQLException, ClassNotFoundException {
        return itemDAO.getAll();
    }
}
