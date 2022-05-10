package dao.custom;

import dao.CrudDAO;
import model.ItemDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemDAO extends CrudDAO<ItemDTO,String> {
    ArrayList<ItemDTO> getAllItemsOnQtyOnHand(int qtyOnHand) throws SQLException, ClassNotFoundException;
}
