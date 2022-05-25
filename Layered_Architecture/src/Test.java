import java.math.BigDecimal;
import java.sql.SQLException;

public class Test {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        BigDecimal unitPrice=new BigDecimal(200.00).setScale(2);
        System.out.println(unitPrice);

    }
}
