package org.gerdoc.util;

import javax.servlet.http.HttpServletRequest;

/** Mensajes de una sola vez (se muestran en la siguiente página y se borran). */
public final class Flash
{
    public static final String MSG = "flashMsg";
    public static final String TIPO = "flashTipo";

    private Flash( )
    {
    }

    public static void ok(HttpServletRequest req, String mensaje)
    {
        req.getSession( ).setAttribute(MSG, mensaje);
        req.getSession( ).setAttribute(TIPO, "success");
    }

    public static void error(HttpServletRequest req, String mensaje)
    {
        req.getSession( ).setAttribute(MSG, mensaje);
        req.getSession( ).setAttribute(TIPO, "danger");
    }
}
