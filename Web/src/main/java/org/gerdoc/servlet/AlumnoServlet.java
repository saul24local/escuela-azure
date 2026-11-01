package org.gerdoc.servlet;

import org.gerdoc.model.Alumno;
import org.gerdoc.service.AlumnoService;
import org.gerdoc.service.impl.AlumnoServiceImpl;
import org.gerdoc.util.Flash;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serial;

@WebServlet("/AlumnoServlet")
public class AlumnoServlet extends HttpServlet
{
    @Serial
    private static final long serialVersionUID = 1L;
    private final AlumnoService alumnoService = new AlumnoServiceImpl( );

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        req.setCharacterEncoding("UTF-8");
        String accion = req.getParameter("accion");
        try
        {
            if ("delete".equals(accion))
            {
                alumnoService.deleteById(Long.parseLong(req.getParameter("id")));
                Flash.ok(req, "Alumno eliminado correctamente");
            }
            else
            {
                Alumno alumno = Alumno.builder( )
                        .nombre(req.getParameter("nombre"))
                        .edad(Integer.parseInt(req.getParameter("edad")))
                        .promedio(Double.parseDouble(req.getParameter("promedio")))
                        .build( );
                if ("update".equals(accion))
                {
                    alumno.setId(Long.parseLong(req.getParameter("id")));
                    alumnoService.update(alumno);
                    Flash.ok(req, "Alumno actualizado correctamente");
                }
                else
                {
                    alumnoService.save(alumno);
                    Flash.ok(req, "Alumno agregado correctamente");
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
        resp.sendRedirect("Alumnos.jsp");
    }
}
