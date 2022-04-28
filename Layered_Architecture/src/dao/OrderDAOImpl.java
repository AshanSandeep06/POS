package dao;

import db.DBConnection;
import model.CustomerDTO;
import model.ItemDTO;
import model.OrderDetailDTO;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

public class OrderDAOImpl {
    public String generateOrderId() throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery("SELECT id FROM Orders ORDER BY id DESC LIMIT 1;");

        return rst.next() ? String.format("OID-%03d", (Integer.parseInt(rst.getString("id").replace("OID-", "")) + 1)) : "OID-001";
    }

    public boolean placeOrder(String orderId, LocalDate orderDate, String customerId, List<OrderDetailDTO> orderDetails) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement stm = connection.prepareStatement("SELECT id FROM Orders WHERE id=?");
        stm.setString(1, orderId);
        /*if order id already exist*/
        if (stm.executeQuery().next()) {

        }

        connection.setAutoCommit(false);
        stm = connection.prepareStatement("INSERT INTO Orders (id, date, customerId) VALUES (?,?,?)");
        stm.setString(1, orderId);
        stm.setDate(2, Date.valueOf(orderDate));
        stm.setString(3, customerId);

        if (stm.executeUpdate() != 1) {
            connection.rollback();
            connection.setAutoCommit(true);
            return false;
        }

        stm = connection.prepareStatement("INSERT INTO orderdetail (orderId, itemCode, unitPrice, qty) VALUES (?,?,?,?)");

        for (OrderDetailDTO detail : orderDetails) {
            stm.setString(1, orderId);
            stm.setString(2, detail.getItemCode());
            stm.setBigDecimal(3, detail.getUnitPrice());
            stm.setInt(4, detail.getQty());

            if (stm.executeUpdate() != 1) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }

//                //Search & Update Item
            ItemDTO item = new ItemDAOImpl().findItemDAO(detail.getItemCode());
            item.setQtyOnHand(item.getQtyOnHand() - detail.getQty());

            PreparedStatement pstm = connection.prepareStatement("UPDATE Item SET description=?, unitPrice=?, qtyOnHand=? WHERE code=?");
            pstm.setString(1, item.getDescription());
            pstm.setBigDecimal(2, item.getUnitPrice());
            pstm.setInt(3, item.getQtyOnHand());
            pstm.setString(4, item.getCode());

            if (!(pstm.executeUpdate() > 0)) {
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
