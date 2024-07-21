package org.retrolauncher.backend.database;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.retrolauncher.backend.app.games.infrastructure.database.hibernate.models.GameModel;
import org.retrolauncher.backend.app.platforms.infrastructure.database.hibernate.models.PlatformModel;

import java.io.IOException;

import static org.hibernate.cfg.JdbcSettings.*;

public class HibernateDriver {
    private static SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() throws IOException {
        if (sessionFactory != null)
            return sessionFactory;

        sessionFactory = new Configuration()
                .addAnnotatedClass(GameModel.class)
                .addAnnotatedClass(PlatformModel.class)
                .setProperty(URL, "jdbc:h2:file:~/retro-launcher/database")
                .setProperty("hibernate.agroal.maxSize", 20)
                .buildSessionFactory();
        sessionFactory.getSchemaManager().exportMappedObjects(true);

        return sessionFactory;
    }
}
