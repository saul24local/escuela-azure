package org.gerdoc.exception;

/** Error de acceso a datos con un mensaje que se puede mostrar al usuario. */
public class DaoException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    public DaoException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
