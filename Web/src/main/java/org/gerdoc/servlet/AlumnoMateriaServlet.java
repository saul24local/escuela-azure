package org.gerdoc.servlet;

import org.gerdoc.model.Alumno;
import org.gerdoc.model.AlumnoMateria;
import org.gerdoc.model.Materia;
import org.gerdoc.service.AlumnoMateriaService;
import org.gerdoc.service.AlumnoService;
import org.gerdoc.service.MateriaService;
import org.gerdoc.service.impl.AlumnoMateriaServiceImpl;
import org.gerdoc.service.impl.AlumnoServiceImpl;
import org.gerdoc.service.impl.MateriaServiceImpl;
import org.gerdoc.util.Flash;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serial;

@WebServlet("/AlumnoMateriaServlet")
public class AlumnoMateriaServlet extends HttpServlet
{
    @Serial
    private static final long serialVersionUID = 1L;
    private final AlumnoMateriaService alumnoMateriaService = new AlumnoMateriaServiceImpl( );
    private final AlumnoService alumnoService = new AlumnoServiceImpl( );
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
                alumnoMateriaService.deleteById(Long.parseLong(req.getParameter("id")));
                Flash.ok(req, "Inscripción eliminada correctamente");
            }
            else
            {
                Alumno alumno = alumnoService.findById(Long.parseLong(req.getParameter("alumnoId")));
                Materia materia = materiaService.findById(Long.parseLong(req.getParameter("materiaId")));
                String cal = req.getParameter("calificacion");
                Double calificacion = (cal == null || cal.trim( ).isEmpty( )) ? null : Double.valueOf(cal);
                if (alumno == null || materia == null)
                {
                    Flash.error(req, "El alumno o la materia seleccionados ya no existen");
                }
                else
                {
                    AlumnoMateria alumnoMateria = AlumnoMateria.builder( )
                            .alumno(alumno)
                            .materia(materia)
                            .calificacion(calificacion)
                            .build( );
                    if ("update".equals(accion))
                    {
                        alumnoMateria.setId(Long.parseLong(req.getParameter("id")));
                        alumnoMateriaService.update(alumnoMateria);
                        Flash.ok(req, "Inscripción actualizada correctamente");
                    }
                    else
                    {
                        alumnoMateriaService.save(alumnoMateria);
                        Flash.ok(req, "Inscripción agregada correctamente");
                    }
                }
            }
        }
        catch (NumberFormatException e)
        {
            Flash.error(req, "Datos inválidos: revisa alumno, materia y calificación");
        }
        catch (RuntimeException e)
        {
            Flash.error(req, e.getMessage( ));
        }
        resp.sendRedirect("AlumnoMaterias.jsp");
    }
}
