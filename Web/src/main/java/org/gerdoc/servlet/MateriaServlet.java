package org.gerdoc.servlet;

import org.gerdoc.model.Materia;
import org.gerdoc.service.MateriaService;
import org.gerdoc.service.impl.MateriaServiceImpl;
import org.gerdoc.util.Flash;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serial;

@WebServlet("/MateriaServlet")
public class MateriaServlet extends HttpServlet
{
    @Serial
    private static final long serialVersionUID = 1L;
    private final MateriaService materiaService = new MateriaServiceImpl( );

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        req.setCharacterEncoding("UTF-8");
        String accion = req.getParameter("accion");
        try
        {
            if ("delete".equals(accion))
            {
                materiaService.deleteById(Long.parseLong(req.getParameter("id")));
                Flash.ok(req, "Materia eliminada correctamente");
            }
            else
            {
                Materia materia = Materia.builder( )
                        .nombre(req.getParameter("nombre"))
                        .creditos(Integer.parseInt(req.getParameter("creditos")))
                        .build( );
                if ("update".equals(accion))
                {
                    materia.setId(Long.parseLong(req.getParameter("id")));
                    materiaService.update(materia);
                    Flash.ok(req, "Materia actualizada correctamente");
                }
                else
                {
                    materiaService.save(materia);
                    Flash.ok(req, "Materia agregada correctamente");
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
        resp.sendRedirect("Materias.jsp");
    }
}
