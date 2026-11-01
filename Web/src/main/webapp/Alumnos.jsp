<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="org.gerdoc.model.Alumno" %>
<%@ page import="org.gerdoc.service.AlumnoService" %>
<%@ page import="org.gerdoc.service.impl.AlumnoServiceImpl" %>
<%@ page import="org.gerdoc.util.Html" %>
<% String titulo = "Alumnos"; %>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<%
    AlumnoService alumnoService = new AlumnoServiceImpl( );
    List<Alumno> alumnos = alumnoService.findAll( );
%>
<div class="d-flex justify-content-between align-items-center mb-3">
    <h1 class="mb-0">Alumnos</h1>
    <button type="button" class="btn btn-success" data-bs-toggle="modal" data-bs-target="#formModal">
        Agregar alumno
    </button>
</div>
<table class="table table-striped align-middle">
    <thead>
        <tr>
            <th scope="col">#</th>
            <th scope="col">Nombre</th>
            <th scope="col">Edad</th>
            <th scope="col">Promedio</th>
            <th scope="col">Acción</th>
        </tr>
    </thead>
    <tbody>
<%
    int i = 0;
    for (Alumno fila : alumnos)
    {
%>
        <tr>
            <th scope="row"><%= ++i %></th>
                    <td><a href="Alumno.jsp?id=<%= fila.getId( ) %>"><%= Html.esc(fila.getNombre( )) %></a></td>
                    <td><%= Html.esc(fila.getEdad( )) %></td>
                    <td><%= Html.esc(fila.getPromedio( )) %></td>
            <td>
                <button type="button" class="btn btn-warning btn-sm btn-editar" data-bs-toggle="modal" data-bs-target="#formModal"
                        data-id="<%= fila.getId( ) %>" data-nombre="<%= Html.esc(fila.getNombre( )) %>" data-edad="<%= Html.esc(fila.getEdad( )) %>" data-promedio="<%= Html.esc(fila.getPromedio( )) %>">
                    Editar
                </button>
                <form action="AlumnoServlet" method="post" style="display:inline;">
                    <input type="hidden" name="accion" value="delete">
                    <input type="hidden" name="id" value="<%= fila.getId( ) %>">
                    <button type="submit" class="btn btn-danger btn-sm"
                            onclick="return confirm('¿Seguro que deseas borrar este alumno?');">
                        Borrar
                    </button>
                </form>
            </td>
        </tr>
<%
    }
    if (alumnos.isEmpty( ))
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
                <h5 class="modal-title" id="formModalLabel">Agregar alumno</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Cerrar"></button>
            </div>
            <form action="AlumnoServlet" method="post" id="form">
                <input type="hidden" id="accion" name="accion" value="save">
                <input type="hidden" id="id" name="id" value="0">
                <div class="modal-body">
                    <div class="mb-3">
                        <label for="nombre" class="form-label">Nombre</label>
                        <input type="text" class="form-control" id="nombre" name="nombre" required>
                    </div>
                    <div class="mb-3">
                        <label for="edad" class="form-label">Edad</label>
                        <input type="number" class="form-control" id="edad" name="edad" min="0" required>
                    </div>
                    <div class="mb-3">
                        <label for="promedio" class="form-label">Promedio</label>
                        <input type="number" class="form-control" id="promedio" name="promedio" step="0.01" min="0" max="10" required>
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
            document.getElementById('formModalLabel').textContent = 'Editar alumno';
            document.getElementById('btnGuardar').textContent = 'Actualizar';
                document.getElementById('nombre').value = b.dataset.nombre;
                document.getElementById('edad').value = b.dataset.edad;
                document.getElementById('promedio').value = b.dataset.promedio;
        } else {
            document.getElementById('id').value = '0';
            document.getElementById('accion').value = 'save';
            document.getElementById('formModalLabel').textContent = 'Agregar alumno';
            document.getElementById('btnGuardar').textContent = 'Guardar';
        }
    });
</script>
</body>
</html>
