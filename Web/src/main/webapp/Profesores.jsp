<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="org.gerdoc.model.Profesor" %>
<%@ page import="org.gerdoc.service.ProfesorService" %>
<%@ page import="org.gerdoc.service.impl.ProfesorServiceImpl" %>
<%@ page import="org.gerdoc.util.Html" %>
<% String titulo = "Profesores"; %>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<%
    ProfesorService profesorService = new ProfesorServiceImpl( );
    List<Profesor> profesores = profesorService.findAll( );
%>
<div class="d-flex justify-content-between align-items-center mb-3">
    <h1 class="mb-0">Profesores</h1>
    <button type="button" class="btn btn-success" data-bs-toggle="modal" data-bs-target="#formModal">
        Agregar profesor
    </button>
</div>
<table class="table table-striped align-middle">
    <thead>
        <tr>
            <th scope="col">#</th>
            <th scope="col">Nombre</th>
            <th scope="col">Especialidad</th>
            <th scope="col">Acción</th>
        </tr>
    </thead>
    <tbody>
<%
    int i = 0;
    for (Profesor fila : profesores)
    {
%>
        <tr>
            <th scope="row"><%= ++i %></th>
                    <td><a href="Profesor.jsp?id=<%= fila.getId( ) %>"><%= Html.esc(fila.getNombre( )) %></a></td>
                    <td><%= Html.esc(fila.getEspecialidad( )) %></td>
            <td>
                <button type="button" class="btn btn-warning btn-sm btn-editar" data-bs-toggle="modal" data-bs-target="#formModal"
                        data-id="<%= fila.getId( ) %>" data-nombre="<%= Html.esc(fila.getNombre( )) %>" data-especialidad="<%= Html.esc(fila.getEspecialidad( )) %>">
                    Editar
                </button>
                <form action="ProfesorServlet" method="post" style="display:inline;">
                    <input type="hidden" name="accion" value="delete">
                    <input type="hidden" name="id" value="<%= fila.getId( ) %>">
                    <button type="submit" class="btn btn-danger btn-sm"
                            onclick="return confirm('¿Seguro que deseas borrar este profesor?');">
                        Borrar
                    </button>
                </form>
            </td>
        </tr>
<%
    }
    if (profesores.isEmpty( ))
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
                <h5 class="modal-title" id="formModalLabel">Agregar profesor</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Cerrar"></button>
            </div>
            <form action="ProfesorServlet" method="post" id="form">
                <input type="hidden" id="accion" name="accion" value="save">
                <input type="hidden" id="id" name="id" value="0">
                <div class="modal-body">
                    <div class="mb-3">
                        <label for="nombre" class="form-label">Nombre</label>
                        <input type="text" class="form-control" id="nombre" name="nombre" required>
                    </div>
                    <div class="mb-3">
                        <label for="especialidad" class="form-label">Especialidad</label>
                        <input type="text" class="form-control" id="especialidad" name="especialidad" required>
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
            document.getElementById('formModalLabel').textContent = 'Editar profesor';
            document.getElementById('btnGuardar').textContent = 'Actualizar';
                document.getElementById('nombre').value = b.dataset.nombre;
                document.getElementById('especialidad').value = b.dataset.especialidad;
        } else {
            document.getElementById('id').value = '0';
            document.getElementById('accion').value = 'save';
            document.getElementById('formModalLabel').textContent = 'Agregar profesor';
            document.getElementById('btnGuardar').textContent = 'Guardar';
        }
    });
</script>
</body>
</html>
