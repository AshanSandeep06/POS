import dao.CrudDAO;
import dao.DAOFactory;
import dao.custom.CustomerDAO;
import dao.custom.ItemDAO;

import java.sql.SQLException;

public class Test {
    public static void main(String[] args) {

        //CrudDAO crudDAO = (CrudDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);

        CrudDAO queryDAO = (CrudDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.JOINQUERYDAO);
    }
}
