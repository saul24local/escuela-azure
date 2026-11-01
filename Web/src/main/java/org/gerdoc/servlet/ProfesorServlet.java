package org.gerdoc.servlet;

import org.gerdoc.model.Profesor;
import org.gerdoc.service.ProfesorService;
import org.gerdoc.service.impl.ProfesorServiceImpl;
import org.gerdoc.util.Flash;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serial;

@WebServlet("/ProfesorServlet")
public class ProfesorServlet extends HttpServlet
{
    @Serial
    private static final long serialVersionUID = 1L;
    private final ProfesorService profesorService = new ProfesorServiceImpl( );

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        req.setCharacterEncoding("UTF-8");
        String accion = req.getParameter("accion");
        try
        {
            if ("delete".equals(accion))
            {
                profesorService.deleteById(Long.parseLong(req.getParameter("id")));
                Flash.ok(req, "Profesor eliminado correctamente");
            }
            else
            {
                Profesor profesor = Profesor.builder( )
                        .nombre(req.getParameter("nombre"))
                        .especialidad(req.getParameter("especialidad"))
                        .build( );
                if ("update".equals(accion))
                {
                    profesor.setId(Long.parseLong(req.getParameter("id")));
                    profesorService.update(profesor);
                    Flash.ok(req, "Profesor actualizado correctamente");
                }
                else
                {
                    profesorService.save(profesor);
                    Flash.ok(req, "Profesor agregado correctamente");
                }
            }
        }
        catch (NumberFormatException e)
        {
            Flash.error(req, "Datos inválidos: revisa los campos numéricos");
        }
        catch (RuntimeException e)
        {
            Flash.error(req, e.getMessage( ));
        }
        resp.sendRedirect("Profesores.jsp");
    }
}
