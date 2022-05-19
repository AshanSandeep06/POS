import dao.CrudDAO;
import dao.custom.impl.CustomerDAOImpl;
import dto.CustomerDTO;

import java.sql.SQLException;

public class Test {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        CrudDAO c1 = new CustomerDAOImpl();
        c1.save("Ashan");


        CrudDAO<CustomerDTO,String> c2 = new CustomerDAOImpl();
        c2.save(new CustomerDTO());


    }
}
