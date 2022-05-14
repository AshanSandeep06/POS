import dao.DAOFactory;
import dao.custom.CustomerDAO;
import dao.custom.ItemDAO;

import java.sql.SQLException;

public class Test {
    public static void main(String[] args) {

        ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);

        try {
            System.out.println(itemDAO.generateNewId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
