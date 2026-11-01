<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="org.gerdoc.model.Alumno" %>
<%@ page import="org.gerdoc.model.Materia" %>
<%@ page import="org.gerdoc.model.AlumnoMateria" %>
<%@ page import="org.gerdoc.service.AlumnoService" %>
<%@ page import="org.gerdoc.service.MateriaService" %>
<%@ page import="org.gerdoc.service.AlumnoMateriaService" %>
<%@ page import="org.gerdoc.service.impl.AlumnoServiceImpl" %>
<%@ page import="org.gerdoc.service.impl.MateriaServiceImpl" %>
<%@ page import="org.gerdoc.service.impl.AlumnoMateriaServiceImpl" %>
<%@ page import="org.gerdoc.util.Html" %>
<% String titulo = "Alumno - Materia"; %>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<%
    List<AlumnoMateria> inscripciones = new AlumnoMateriaServiceImpl( ).findAll( );
    List<Alumno> alumnos = new AlumnoServiceImpl( ).findAll( );
    List<Materia> materias = new MateriaServiceImpl( ).findAll( );
%>
<div class="d-flex justify-content-between align-items-center mb-3">
    <h1 class="mb-0">Alumno - Materia</h1>
    <button type="button" class="btn btn-success" data-bs-toggle="modal" data-bs-target="#formModal">
        Inscribir alumno en materia
    </button>
</div>
<table class="table table-striped align-middle">
    <thead>
        <tr>
            <th scope="col">#</th>
            <th scope="col">Alumno</th>
            <th scope="col">Materia</th>
            <th scope="col">Calificación</th>
            <th scope="col">Acción</th>
        </tr>
    </thead>
    <tbody>
<%
    int i = 0;
    for (AlumnoMateria fila : inscripciones)
    {
%>
        <tr>
            <th scope="row"><%= ++i %></th>
                    <td><%= Html.esc(fila.getAlumno( ).getNombre( )) %></td>
                    <td><%= Html.esc(fila.getMateria( ).getNombre( )) %></td>
                    <td><%= fila.getCalificacion( ) == null ? "-" : Html.esc(fila.getCalificacion( )) %></td>
            <td>
                <button type="button" class="btn btn-warning btn-sm btn-editar" data-bs-toggle="modal" data-bs-target="#formModal"
                        data-id="<%= fila.getId( ) %>" data-alumno-id="<%= fila.getAlumno( ).getId( ) %>" data-materia-id="<%= fila.getMateria( ).getId( ) %>" data-calificacion="<%= Html.esc(fila.getCalificacion( )) %>">
                    Editar
                </button>
                <form action="AlumnoMateriaServlet" method="post" style="display:inline;">
                    <input type="hidden" name="accion" value="delete">
                    <input type="hidden" name="id" value="<%= fila.getId( ) %>">
                    <button type="submit" class="btn btn-danger btn-sm"
                            onclick="return confirm('¿Seguro que deseas borrar esta inscripción?');">
                        Borrar
                    </button>
                </form>
            </td>
        </tr>
<%
    }
    if (inscripciones.isEmpty( ))
    {
%>
        <tr><td colspan="5" class="text-center text-muted">No hay registros</td></tr>
<%
    }
%>
    </tbody>
</table>
<a href="index.jsp" class="btn btn-secondary">Inicio</a>

<!-- Ventana con el formulario de alta / cambio -->
<div class="modal fade" id="formModal" tabindex="-1" aria-labelledby="formModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="formModalLabel">Inscribir alumno en materia</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Cerrar"></button>
            </div>
            <form action="AlumnoMateriaServlet" method="post" id="form">
                <input type="hidden" id="accion" name="accion" value="save">
                <input type="hidden" id="id" name="id" value="0">
                <div class="modal-body">
                    <div class="mb-3">
                        <label for="alumnoId" class="form-label">Alumno</label>
                        <select class="form-select" id="alumnoId" name="alumnoId" required>
                            <option value="">Selecciona un alumno</option>
                            <%
                                for (Alumno o : alumnos)
                                {
                            %>
                            <option value="<%= o.getId( ) %>"><%= Html.esc(o.getNombre( )) %></option>
                            <%
                                }
                            %>
                        </select>
                    </div>
                    <div class="mb-3">
                        <label for="materiaId" class="form-label">Materia</label>
                        <select class="form-select" id="materiaId" name="materiaId" required>
                            <option value="">Selecciona una materia</option>
                            <%
                                for (Materia o : materias)
                                {
                            %>
                            <option value="<%= o.getId( ) %>"><%= Html.esc(o.getNombre( )) %></option>
                            <%
                                }
                            %>
                        </select>
                    </div>
                    <div class="mb-3">
                        <label for="calificacion" class="form-label">Calificación (opcional)</label>
                        <input type="number" class="form-control" id="calificacion" name="calificacion" step="0.01" min="0" max="10">
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                    <button type="submit" class="btn btn-primary" id="btnGuardar">Guardar</button>
                </div>
            </form>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
<script>
    document.getElementById('formModal').addEventListener('show.bs.modal', function (event) {
        const b = event.relatedTarget;
        document.getElementById('form').reset();
        if (b && b.classList.contains('btn-editar')) {
            document.getElementById('id').value = b.dataset.id;
            document.getElementById('accion').value = 'update';
            document.getElementById('formModalLabel').textContent = 'Editar inscripción';
            document.getElementById('btnGuardar').textContent = 'Actualizar';
                document.getElementById('alumnoId').value = b.dataset.alumnoId;
                document.getElementById('materiaId').value = b.dataset.materiaId;
                document.getElementById('calificacion').value = b.dataset.calificacion;
        } else {
            document.getElementById('id').value = '0';
            document.getElementById('accion').value = 'save';
            document.getElementById('formModalLabel').textContent = 'Inscribir alumno en materia';
            document.getElementById('btnGuardar').textContent = 'Guardar';
        }
    });
</script>
</body>
</html>
