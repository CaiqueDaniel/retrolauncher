package fixtures;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.retrolauncher.backend.app.games.infrastructure.database.hibernate.models.GameModel;
import org.retrolauncher.backend.app.platforms.infrastructure.database.hibernate.models.PlatformModel;

import static org.hibernate.cfg.JdbcSettings.*;

public class TestHibernateDriver {
    private static SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        if (sessionFactory != null)
            return sessionFactory;

        sessionFactory = new Configuration()
                .addAnnotatedClass(GameModel.class)
                .addAnnotatedClass(PlatformModel.class)
                .setProperty(URL, "jdbc:h2:mem:db1")
                .setProperty(USER, "sa")
                .setProperty(PASS, "")
                .setProperty("hibernate.agroal.maxSize", 20)
                .setProperty(SHOW_SQL, true)
                .setProperty(FORMAT_SQL, true)
                .setProperty(HIGHLIGHT_SQL, true)
                .buildSessionFactory();
        sessionFactory.getSchemaManager().exportMappedObjects(true);

        return sessionFactory;
    }
}
