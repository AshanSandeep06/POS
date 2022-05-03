package dao;

import db.DBConnection;
import model.ItemDTO;

import java.sql.*;
import java.util.ArrayList;

public interface ItemDAO {
    public ArrayList<ItemDTO> loadAllItems() throws SQLException, ClassNotFoundException;

    public boolean deleteItem(String code) throws SQLException, ClassNotFoundException;

    public boolean saveItem(ItemDTO dto) throws SQLException, ClassNotFoundException;

    public boolean updateItem(ItemDTO dto) throws SQLException, ClassNotFoundException;

    public boolean itemIsExists(String code) throws SQLException, ClassNotFoundException;

    public String generateItemCode() throws SQLException, ClassNotFoundException;

    public ItemDTO getItemDetails(String code) throws SQLException, ClassNotFoundException;

    public ItemDTO findItemDAO(String code) throws SQLException, ClassNotFoundException;
}
