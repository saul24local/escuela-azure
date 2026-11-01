package org.gerdoc.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil
{
    private static final SessionFactory sessionFactory;
    static
    {
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Configuration configuration = new Configuration( ).configure("hibernate.cfg.xml");

            // La contraseña NO se guarda en el repositorio: viene de la variable de entorno DB_PASSWORD
            String password = valor("DB_PASSWORD");
            if (password == null)
            {
                throw new IllegalStateException(
                        "Falta la variable de entorno DB_PASSWORD (contraseña de MySQL)");
            }
            configuration.setProperty("hibernate.connection.password", password);

            String url = valor("DB_URL");
            if (url != null)
            {
                configuration.setProperty("hibernate.connection.url", url);
            }
            String user = valor("DB_USER");
            if (user != null)
            {
                configuration.setProperty("hibernate.connection.username", user);
            }
            sessionFactory = configuration.buildSessionFactory( );
        }
        catch (Throwable e)
        {
            System.err.println( "Error al iniciar Hibernate: " + e.getMessage( ) );
            throw new ExceptionInInitializerError( e );
        }
    }

    private static String valor(String nombre)
    {
        String v = System.getenv(nombre);
        return v != null ? v : System.getProperty(nombre);
    }

    public static SessionFactory getSessionFactory()
    {
        return sessionFactory;
    }
}
