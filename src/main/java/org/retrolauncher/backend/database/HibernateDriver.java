package org.retrolauncher.backend.database;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.retrolauncher.backend.app.games.infrastructure.database.hibernate.models.GameModel;
import org.retrolauncher.backend.app.platforms.infrastructure.database.hibernate.models.PlatformModel;

import static org.hibernate.cfg.JdbcSettings.*;

public class HibernateDriver {
    private static SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        if (sessionFactory != null)
            return sessionFactory;

        sessionFactory = new Configuration()
                .addAnnotatedClass(GameModel.class)
                .addAnnotatedClass(PlatformModel.class)
                .setProperty(URL, "jdbc:h2:file:~/retro-launcher/database")
                .setProperty("hibernate.hbm2ddl.auto", "update")
                .buildSessionFactory();

        return sessionFactory;
    }
}
