package lk.ijse.pos.dao.custom;

import lk.ijse.pos.dao.CrudDAO;
import lk.ijse.pos.entity.Item;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemDAO extends CrudDAO<Item,String> {
    ArrayList<Item> getAllItemsOnQtyOnHand(int qtyOnHand) throws SQLException, ClassNotFoundException;
}
