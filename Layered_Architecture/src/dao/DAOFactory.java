package dao;

import dao.custom.CustomerDAO;
import dao.custom.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory(){

    }

    // Singleton design pattern
    public static DAOFactory getDaoFactory(){
        return daoFactory==null ? daoFactory=new DAOFactory() : daoFactory;
    }

    // public static final Integer values
    public enum DAOTypes{
        CUSTOMER, ITEM, ORDER, ORDERDETAIL, JOINQUERYDAO;
    }

    // This Method for hide the object creation logic and return what user wants
    public SuperDAO getDAO(DAOTypes types){
        switch (types){
            case CUSTOMER:
                return new CustomerDAOImpl();
            case ITEM:
                return new ItemDAOImpl();
            case ORDER:
                return new OrderDAOImpl();
            case ORDERDETAIL:
                return new OrderDetailDAOImpl();
            case JOINQUERYDAO:
                return new JoinQueryDAOImpl();
            default:
                return null;
        }
    }

}
