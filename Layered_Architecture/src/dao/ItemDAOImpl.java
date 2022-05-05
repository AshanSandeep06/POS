package dao;

import db.DBConnection;
import model.CustomerDTO;
import model.ItemDTO;

import java.sql.*;
import java.util.ArrayList;

public class ItemDAOImpl implements CrudDAO<ItemDTO,String>{
    @Override
    public ArrayList<ItemDTO> getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.executeQuery("SELECT * FROM Item");
        ArrayList<ItemDTO> allItems = new ArrayList<>();

        while(resultSet.next()){
            allItems.add(new ItemDTO(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getBigDecimal(3),
                    resultSet.getInt(4)
            ));
        }
        return allItems;
    }

    @Override
    public boolean save(ItemDTO dto) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("INSERT INTO Item (code, description, unitPrice, qtyOnHand) VALUES (?,?,?,?)",dto.getCode(),dto.getDescription(),dto.getUnitPrice(),dto.getQtyOnHand());
    }

    @Override
    public boolean update(ItemDTO dto) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("UPDATE Item SET description=?, unitPrice=?, qtyOnHand=? WHERE code=?",dto.getDescription(),dto.getUnitPrice(),dto.getQtyOnHand(),dto.getCode());
    }

    @Override
    public boolean delete(String code) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("DELETE FROM Item WHERE code=?",code);
    }

    @Override
    public String generateNewId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.executeQuery("SELECT code FROM Item ORDER BY code DESC LIMIT 1;");
        if (resultSet.next()) {
            String id = resultSet.getString("code");
            int newItemId = Integer.parseInt(id.replace("I00-", "")) + 1;
            return String.format("I00-%03d", newItemId);
        } else {
            return "I00-001";
        }
    }

    @Override
    public boolean IsExist(String code) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeQuery("SELECT code FROM Item WHERE code=?",code).next();
    }

    @Override
    public ItemDTO search(String code) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.executeQuery("SELECT * FROM Item WHERE code=?", code);
        if(resultSet.next()){
            return new ItemDTO(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getBigDecimal(3),
                    resultSet.getInt(4)
            );
        }
        return null;
    }

    /*public ArrayList<ItemDTO> loadAllItems() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.executeQuery("SELECT * FROM Item");
        ArrayList<ItemDTO> allItems = new ArrayList<>();

        while(resultSet.next()){
            allItems.add(new ItemDTO(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getBigDecimal(3),
                    resultSet.getInt(4)
            ));
        }
        return allItems;
    }

    public boolean deleteItem(String code) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("DELETE FROM Item WHERE code=?",code);
    }

    public boolean saveItem(ItemDTO dto) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("INSERT INTO Item (code, description, unitPrice, qtyOnHand) VALUES (?,?,?,?)",dto.getCode(),dto.getDescription(),dto.getUnitPrice(),dto.getQtyOnHand());
    }

    public boolean updateItem(ItemDTO dto) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("UPDATE Item SET description=?, unitPrice=?, qtyOnHand=? WHERE code=?",dto.getDescription(),dto.getUnitPrice(),dto.getQtyOnHand(),dto.getCode());
    }

    public boolean itemIsExists(String code) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeQuery("SELECT code FROM Item WHERE code=?",code).next();
    }

    public String generateItemCode() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.executeQuery("SELECT code FROM Item ORDER BY code DESC LIMIT 1;");
        if (resultSet.next()) {
            String id = resultSet.getString("code");
            int newItemId = Integer.parseInt(id.replace("I00-", "")) + 1;
            return String.format("I00-%03d", newItemId);
        } else {
            return "I00-001";
        }
    }

    public ItemDTO getItemDetails(String code) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.executeQuery("SELECT * FROM Item WHERE code=?", code);
        if(resultSet.next()){
            return new ItemDTO(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getBigDecimal(3),
                    resultSet.getInt(4)
            );
        }
        return null;
    }

    public ItemDTO findItem(String code) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.executeQuery("SELECT * FROM Item WHERE code=?", code);
        if(resultSet.next()){
            return new ItemDTO(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getBigDecimal(3),
                    resultSet.getInt(4)
            );
        }
        return null;
    }*/
}
