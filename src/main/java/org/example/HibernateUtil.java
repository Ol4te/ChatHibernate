package org.example;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static SessionFactory sessionFactory;

    public static synchronized SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuracion = new Configuration().configure();

                // Si existen estas variables de entorno, tienen prioridad sobre hibernate.cfg.xml.
                // Así cada persona usa su propia base de datos sin tocar el código.
                String url = System.getenv("DB_URL");
                String usuario = System.getenv("DB_USER");
                String password = System.getenv("DB_PASSWORD");
                if (url != null) {
                    configuracion.setProperty("hibernate.connection.url", url);
                }
                if (usuario != null) {
                    configuracion.setProperty("hibernate.connection.username", usuario);
                }
                if (password != null) {
                    configuracion.setProperty("hibernate.connection.password", password);
                }

                sessionFactory = configuracion.buildSessionFactory();
            } catch (Exception ex) {
                throw new IllegalStateException(
                        "No se pudo conectar con la base de datos: " + ex.getMessage(), ex);
            }
        }
        return sessionFactory;
    }

    public static synchronized void cerrar() {
        if (sessionFactory != null && sessionFactory.isOpen()) {
            sessionFactory.close();
        }
    }
}