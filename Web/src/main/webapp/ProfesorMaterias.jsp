<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="org.gerdoc.model.Profesor" %>
<%@ page import="org.gerdoc.model.Materia" %>
<%@ page import="org.gerdoc.model.ProfesorMateria" %>
<%@ page import="org.gerdoc.service.ProfesorService" %>
<%@ page import="org.gerdoc.service.MateriaService" %>
<%@ page import="org.gerdoc.service.ProfesorMateriaService" %>
<%@ page import="org.gerdoc.service.impl.ProfesorServiceImpl" %>
<%@ page import="org.gerdoc.service.impl.MateriaServiceImpl" %>
<%@ page import="org.gerdoc.service.impl.ProfesorMateriaServiceImpl" %>
<%@ page import="org.gerdoc.util.Html" %>
<% String titulo = "Profesor - Materia"; %>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<%
    List<ProfesorMateria> asignaciones = new ProfesorMateriaServiceImpl( ).findAll( );
    List<Profesor> profesores = new ProfesorServiceImpl( ).findAll( );
    List<Materia> materias = new MateriaServiceImpl( ).findAll( );
%>
<div class="d-flex justify-content-between align-items-center mb-3">
    <h1 class="mb-0">Profesor - Materia</h1>
    <button type="button" class="btn btn-success" data-bs-toggle="modal" data-bs-target="#formModal">
        Asignar materia a profesor
    </button>
</div>
<table class="table table-striped align-middle">
    <thead>
        <tr>
            <th scope="col">#</th>
            <th scope="col">Profesor</th>
            <th scope="col">Materia</th>
            <th scope="col">Acción</th>
        </tr>
    </thead>
    <tbody>
<%
    int i = 0;
    for (ProfesorMateria fila : asignaciones)
    {
%>
        <tr>
            <th scope="row"><%= ++i %></th>
                    <td><a href="ProfesorMateria.jsp?id=<%= fila.getId( ) %>"><%= Html.esc(fila.getProfesor( ).getNombre( )) %></a></td>
                    <td><%= Html.esc(fila.getMateria( ).getNombre( )) %></td>
            <td>
                <button type="button" class="btn btn-warning btn-sm btn-editar" data-bs-toggle="modal" data-bs-target="#formModal"
                        data-id="<%= fila.getId( ) %>" data-profesor-id="<%= fila.getProfesor( ).getId( ) %>" data-materia-id="<%= fila.getMateria( ).getId( ) %>">
                    Editar
                </button>
                <form action="ProfesorMateriaServlet" method="post" style="display:inline;">
                    <input type="hidden" name="accion" value="delete">
                    <input type="hidden" name="id" value="<%= fila.getId( ) %>">
                    <button type="submit" class="btn btn-danger btn-sm"
                            onclick="return confirm('¿Seguro que deseas borrar esta asignación?');">
                        Borrar
                    </button>
                </form>
            </td>
        </tr>
<%
    }
    if (asignaciones.isEmpty( ))
    {
%>
        <tr><td colspan="4" class="text-center text-muted">No hay registros</td></tr>
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
                <h5 class="modal-title" id="formModalLabel">Asignar materia a profesor</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Cerrar"></button>
            </div>
            <form action="ProfesorMateriaServlet" method="post" id="form">
                <input type="hidden" id="accion" name="accion" value="save">
                <input type="hidden" id="id" name="id" value="0">
                <div class="modal-body">
                    <div class="mb-3">
                        <label for="profesorId" class="form-label">Profesor</label>
                        <select class="form-select" id="profesorId" name="profesorId" required>
                            <option value="">Selecciona un profesor</option>
                            <%
                                for (Profesor o : profesores)
                                {
                            %>
                            <option value="<%= o.getId( ) %>"><%= Html.esc(o.getNombre( ) + " - " + o.getEspecialidad( )) %></option>
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
            document.getElementById('formModalLabel').textContent = 'Editar asignación';
            document.getElementById('btnGuardar').textContent = 'Actualizar';
                document.getElementById('profesorId').value = b.dataset.profesorId;
                document.getElementById('materiaId').value = b.dataset.materiaId;
        } else {
            document.getElementById('id').value = '0';
            document.getElementById('accion').value = 'save';
            document.getElementById('formModalLabel').textContent = 'Asignar materia a profesor';
            document.getElementById('btnGuardar').textContent = 'Guardar';
        }
    });
</script>
</body>
</html>
