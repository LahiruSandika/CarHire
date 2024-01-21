package dao;

import dao.custom.impl.*;

public class DaoFactory {
    private static DaoFactory daoFactory;

    private DaoFactory() {}

    public static DaoFactory getInstance() {
        return daoFactory == null ?
                daoFactory = new DaoFactory() :
                daoFactory;
    }

    public enum DaoTypes {
        CAR, BRAND, CATEGORY, MODEL, CUSTOMER, RENT, USER
    }

    public SuperDao getDao(DaoTypes type) {
        switch (type) {
            case CAR :
                return new CarDaoImpl();
            case BRAND :
                return new BrandDaoImpl();
            case CATEGORY :
                return new CategoryDaoImpl();
            case MODEL :
                return new ModelDaoImpl();
            case CUSTOMER :
                return new CustomerDaoImpl();
            case RENT :
                return new RentDaoImpl();
            case USER :
                return new UserDaoImpl();
            default :
                return null;
        }
    }
}
