package org.gerdoc.dao.impl;

import org.gerdoc.exception.DaoException;
import org.gerdoc.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.function.Function;

/**
 * CRUD genérico. Cada operación abre su propia sesión y la cierra al terminar,
 * así no se acumulan conexiones abiertas.
 */
public abstract class GenericDao<T>
{
    private final Class<T> type;

    protected GenericDao(Class<T> type)
    {
        this.type = type;
    }

    public List<T> findAll( )
    {
        try (Session session = HibernateUtil.getSessionFactory( ).openSession( ))
        {
            return session
                    .createQuery("select e from " + type.getName( ) + " e order by e.id", type)
                    .getResultList( );
        }
        catch (RuntimeException e)
        {
            throw traducir(e);
        }
    }

    public T findById(Long id)
    {
        try (Session session = HibernateUtil.getSessionFactory( ).openSession( ))
        {
            return session.get(type, id);
        }
        catch (RuntimeException e)
        {
            throw traducir(e);
        }
    }

    public T save(T entidad)
    {
        return enTransaccion(session -> {
            session.save(entidad);
            return entidad;
        });
    }

    @SuppressWarnings("unchecked")
    public T update(T entidad)
    {
        return enTransaccion(session -> (T) session.merge(entidad));
    }

    public void deleteById(Long id)
    {
        enTransaccion(session -> {
            T entidad = session.get(type, id);
            if (entidad != null)
            {
                session.delete(entidad);
            }
            return null;
        });
    }

    private <R> R enTransaccion(Function<Session, R> trabajo)
    {
        try (Session session = HibernateUtil.getSessionFactory( ).openSession( ))
        {
            Transaction tx = session.beginTransaction( );
            try
            {
                R resultado = trabajo.apply(session);
                tx.commit( );
                return resultado;
            }
            catch (RuntimeException e)
            {
                if (tx.isActive( ))
                {
                    tx.rollback( );
                }
                throw e;
            }
        }
        catch (RuntimeException e)
        {
            throw traducir(e);
        }
    }

    private DaoException traducir(RuntimeException e)
    {
        if (e instanceof DaoException)
        {
            return (DaoException) e;
        }
        for (Throwable t = e; t != null; t = t.getCause( ))
        {
            if (t instanceof ConstraintViolationException || t instanceof SQLIntegrityConstraintViolationException)
            {
                return new DaoException(
                        "Operación rechazada por integridad referencial: el registro tiene "
                      + "relaciones con otros catálogos o ya existe.", e);
            }
        }
        return new DaoException("Error en la base de datos: " + e.getMessage( ), e);
    }
}
