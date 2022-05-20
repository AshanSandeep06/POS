package lk.ijse.pos.bo.custom.impl;

import lk.ijse.pos.bo.custom.PurchaseOrderBO;
import lk.ijse.pos.dao.DAOFactory;
import lk.ijse.pos.db.DBConnection;
import lk.ijse.pos.entity.Customer;
import lk.ijse.pos.entity.Item;
import lk.ijse.pos.entity.OrderDetail;
import lk.ijse.pos.entity.Orders;
import lk.ijse.pos.dao.custom.*;
import lk.ijse.pos.dto.CustomerDTO;
import lk.ijse.pos.dto.ItemDTO;
import lk.ijse.pos.dto.OrderDTO;
import lk.ijse.pos.dto.OrderDetailDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class PurchaseOrderBOImpl implements PurchaseOrderBO {

    // Exposed the object creation logic
    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER); // Hide the object creation logic using DAOFactory using factory design pattern
    ItemDAO itemDAO = (ItemDAO)DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);
    OrderDAO orderDAO = (OrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER);
    OrderDetailDAO orderDetailDAO = (OrderDetailDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDERDETAIL);
    JoinQueryDAO queryDAO = (JoinQueryDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.JOINQUERYDAO);

    @Override
    public boolean purchaseOrder(OrderDTO dto) throws SQLException, ClassNotFoundException {

        /*Transaction*/
        Connection connection = null;
        connection = DBConnection.getDbConnection().getConnection();
        boolean orderIsExists = orderDAO.IsExist(dto.getOrderId());
        if (orderIsExists) {

        }
        connection.setAutoCommit(false);

        Orders order = new Orders(dto.getOrderId(),dto.getOrderDate(),dto.getCustomerId());
        //
        System.out.println(dto.getOrderId());
        boolean orderIsSaved = orderDAO.save(order);

        if (!orderIsSaved) {
            connection.rollback();
            connection.setAutoCommit(true);
            return false;
        }

        for (OrderDetailDTO detail : dto.getOrderDetails()) {
            //
            System.out.println(detail.getOrderId()); //null
            boolean orderDetailsIsSaved = orderDetailDAO.save(new OrderDetail(detail.getOrderId(),detail.getItemCode(),detail.getQty(),detail.getUnitPrice()));

            if (!orderDetailsIsSaved) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }

            Item item = itemDAO.search(detail.getItemCode());
            item.setQtyOnHand(item.getQtyOnHand() - detail.getQty());

            boolean update = itemDAO.update(item);

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
        Customer customer = customerDAO.search(id);
        return new CustomerDTO(customer.getId(),customer.getName(),customer.getAddress());
    }

    @Override
    public ItemDTO searchItem(String code) throws SQLException, ClassNotFoundException {
        Item i = itemDAO.search(code);
        return new ItemDTO(i.getCode(),i.getDescription(),i.getUnitPrice(),i.getQtyOnHand());
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
        ArrayList<CustomerDTO> allCustomers = new ArrayList<>();
        for (Customer c1 : customerDAO.getAll()) {
            allCustomers.add(new CustomerDTO(c1.getId(),c1.getName(),c1.getAddress()));
        }
        return allCustomers;
    }

    @Override
    public ArrayList<ItemDTO> getAllItemCodes() throws SQLException, ClassNotFoundException {
        ArrayList<ItemDTO> allItems=new ArrayList<>();
        for (Item i : itemDAO.getAll()) {
            allItems.add(new ItemDTO(
                    i.getCode(),
                    i.getDescription(),
                    i.getUnitPrice(),
                    i.getQtyOnHand()
            ));
        }
        return allItems;
    }
}
