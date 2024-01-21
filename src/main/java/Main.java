import org.hibernate.Session;
import util.SessionFactoryConfiguration;

public class Main {

    public static void main(String[] args) {

        Session session = SessionFactoryConfiguration.getInstance().getSession();

        Launcher.main(args);
    }
}
