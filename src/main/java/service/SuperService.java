package service;

import org.hibernate.Session;
import util.SessionFactoryConfiguration;

public interface SuperService {
    Session session = SessionFactoryConfiguration.getInstance().getSession();
}
