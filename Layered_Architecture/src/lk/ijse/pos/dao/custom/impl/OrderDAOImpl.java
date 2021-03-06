package lk.ijse.pos.dao.custom.impl;

import lk.ijse.pos.dao.SQLUtil;
import lk.ijse.pos.dao.custom.OrderDAO;
import lk.ijse.pos.entity.Orders;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDAOImpl implements OrderDAO {
    @Override
    public ArrayList<Orders> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(Orders entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("INSERT INTO Orders (id, date, customerId) VALUES (?,?,?)", entity.getId(),entity.getDate(),entity.getCustomerId());
    }

    @Override
    public boolean update(Orders entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String generateNewId() throws SQLException, ClassNotFoundException {
        ResultSet result = SQLUtil.executeQuery("SELECT id FROM Orders ORDER BY id DESC LIMIT 1");
        if (result.next()) {
            return result.getString(1);
        } else {
            return null;
        }
    }

    @Override
    public boolean IsExist(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeQuery("SELECT id FROM Orders WHERE id=?",id).next();
    }

    @Override
    public Orders search(String s) throws SQLException, ClassNotFoundException {
        return null;
    }



    /*public String generateNewOrderId() throws SQLException, ClassNotFoundException {
        ResultSet result = SQLUtil.executeQuery("SELECT id FROM Orders ORDER BY id DESC LIMIT 1");
        if (result.next()) {
            return result.getString(1);
        } else {
            return null;
        }
    }

    public boolean saveOrder(String orderId, LocalDate orderDate, String customerId, List<OrderDetailDTO> orderDetails) throws SQLException, ClassNotFoundException {
        *//*Transaction*//*
        Connection connection = null;
        connection = DBConnection.getDbConnection().getConnection();
        ResultSet result = SQLUtil.executeQuery("SELECT id FROM Orders WHERE id=?",orderId);
        *//*if order id already exist*//*
        if (result.next()) {

        }

        connection.setAutoCommit(false);
        boolean b1 = false;
        if (!(b1 = SQLUtil.executeUpdate("INSERT INTO Orders (id, date, customerId) VALUES (?,?,?)",orderId, Date.valueOf(orderDate),customerId))) {
            connection.rollback();
            connection.setAutoCommit(true);
            System.out.println("Orders");
            return false;
        }

        for (OrderDetailDTO detail : orderDetails) {
            boolean b=false;
            if (!(b = SQLUtil.executeUpdate("INSERT INTO orderdetail (orderId, itemCode, unitPrice, qty) VALUES (?,?,?,?)",orderId,detail.getItemCode(),detail.getUnitPrice(),detail.getQty()))) {
                connection.rollback();
                connection.setAutoCommit(true);
                System.out.println();
                return false;
            }

            //Search & Update Item
            ItemDAOImpl itemDAO = new ItemDAOImpl();
            ItemDTO item = itemDAO.findItem(detail.getItemCode());
            item.setQtyOnHand(item.getQtyOnHand() - detail.getQty());

            boolean qtyIsUpdated = false;
            if (!(qtyIsUpdated = SQLUtil.executeUpdate("UPDATE Item SET description=?, unitPrice=?, qtyOnHand=? WHERE code=?",item.getDescription(),item.getUnitPrice(),item.getQtyOnHand(),item.getCode()))) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }
        }

        connection.commit();
        connection.setAutoCommit(true);
        return true;
    }*/
}
