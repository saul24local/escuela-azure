package org.gerdoc.servlet;

import org.gerdoc.model.Materia;
import org.gerdoc.model.Profesor;
import org.gerdoc.model.ProfesorMateria;
import org.gerdoc.service.MateriaService;
import org.gerdoc.service.ProfesorMateriaService;
import org.gerdoc.service.ProfesorService;
import org.gerdoc.service.impl.MateriaServiceImpl;
import org.gerdoc.service.impl.ProfesorMateriaServiceImpl;
import org.gerdoc.service.impl.ProfesorServiceImpl;
import org.gerdoc.util.Flash;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serial;

@WebServlet("/ProfesorMateriaServlet")
public class ProfesorMateriaServlet extends HttpServlet
{
    @Serial
    private static final long serialVersionUID = 1L;
    private final ProfesorMateriaService profesorMateriaService = new ProfesorMateriaServiceImpl( );
    private final ProfesorService profesorService = new ProfesorServiceImpl( );
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
                profesorMateriaService.deleteById(Long.parseLong(req.getParameter("id")));
                Flash.ok(req, "Asignación eliminada correctamente");
            }
            else
            {
                Profesor profesor = profesorService.findById(Long.parseLong(req.getParameter("profesorId")));
                Materia materia = materiaService.findById(Long.parseLong(req.getParameter("materiaId")));
                if (profesor == null || materia == null)
                {
                    Flash.error(req, "El profesor o la materia seleccionados ya no existen");
                }
                else
                {
                    ProfesorMateria profesorMateria = ProfesorMateria.builder( )
                            .profesor(profesor)
                            .materia(materia)
                            .build( );
                    if ("update".equals(accion))
                    {
                        profesorMateria.setId(Long.parseLong(req.getParameter("id")));
                        profesorMateriaService.update(profesorMateria);
                        Flash.ok(req, "Asignación actualizada correctamente");
                    }
                    else
                    {
                        profesorMateriaService.save(profesorMateria);
                        Flash.ok(req, "Asignación agregada correctamente");
                    }
                }
            }
        }
        catch (NumberFormatException e)
        {
            Flash.error(req, "Datos inválidos: selecciona un profesor y una materia");
        }
        catch (RuntimeException e)
        {
            Flash.error(req, e.getMessage( ));
        }
        resp.sendRedirect("ProfesorMaterias.jsp");
    }
}
