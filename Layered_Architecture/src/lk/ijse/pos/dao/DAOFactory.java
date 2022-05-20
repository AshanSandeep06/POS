package lk.ijse.pos.dao;

import lk.ijse.pos.dao.custom.impl.*;

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
                return new CustomerDAOImpl();  // SuperDAO superDao=new CustomerDAOImpl();  -----> return superDao;
            case ITEM:
                return new ItemDAOImpl();      // SuperDAO superDao=new ItemDAOImpl();  -----> return superDao;
            case ORDER:
                return new OrderDAOImpl();    // SuperDAO superDao=new OrderDAOImpl();  -----> return superDao;
            case ORDERDETAIL:
                return new OrderDetailDAOImpl(); // SuperDAO superDao=new OrderDetailDAOImpl();  -----> return superDao;
            case JOINQUERYDAO:
                return new JoinQueryDAOImpl();  // SuperDAO superDao=new JoinQueryDAOImpl();  -----> return superDao;
            default:
                return null;
        }
    }

}
