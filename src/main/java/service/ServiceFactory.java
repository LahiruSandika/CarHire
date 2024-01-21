package service;

import service.custom.impl.*;

public class ServiceFactory {

    private static ServiceFactory serviceFactory;

    private ServiceFactory() {}

    public static ServiceFactory getInstance() {
        return serviceFactory == null ?
                serviceFactory = new ServiceFactory()
                : serviceFactory;
    }

    public enum ServiceTypes {
        CAR, BRAND, CATEGORY, MODEL, CUSTOMER, RENT, USER
    }

    public SuperService getService(ServiceTypes type) {
        switch (type) {
            case CAR:
                return new CarServiceImpl();
            case BRAND:
                return new BrandServiceImpl();
            case CATEGORY:
                return new CategoryServiceImpl();
            case MODEL:
                return new ModelServiceImpl();
            case CUSTOMER:
                return new CustomerServiceImpl();
            case RENT:
                return  new RentServiceImpl();
            case USER:
                return new UserServiceImpl();
            default:
                return null;
        }
    }
}
