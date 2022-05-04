package dao;

import db.DBConnection;
import model.CustomerDTO;
import model.ItemDTO;
import model.OrderDetailDTO;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

public class OrderDAOImpl implements OrderDAO{
    public String generateNewOrderId() throws SQLException, ClassNotFoundException {
        ResultSet result = SQLUtil.executeQuery("SELECT id FROM Orders ORDER BY id DESC LIMIT 1");
        if (result.next()) {
            return result.getString(1);
        } else {
            return null;
        }
    }

    public boolean saveOrder(String orderId, LocalDate orderDate, String customerId, List<OrderDetailDTO> orderDetails) throws SQLException, ClassNotFoundException {
        /*Transaction*/
        Connection connection = null;
        connection = DBConnection.getDbConnection().getConnection();
        ResultSet result = SQLUtil.executeQuery("SELECT id FROM Orders WHERE id=?",orderId);
        /*if order id already exist*/
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
    }
}
