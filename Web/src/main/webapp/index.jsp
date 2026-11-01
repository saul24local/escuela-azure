<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="org.gerdoc.util.Html" %>
<% String titulo = "Inicio"; %>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<h1 class="mb-4">Sistema Escolar</h1>
<p class="text-muted">Selecciona un catálogo para consultar, agregar, modificar o eliminar registros.</p>
<div class="row g-3">
    <div class="col-md-4">
        <div class="card h-100"><div class="card-body">
            <h5 class="card-title">Alumnos</h5>
            <p class="card-text">Catálogo de alumnos.</p>
            <a href="Alumnos.jsp" class="btn btn-primary">Abrir</a>
        </div></div>
    </div>
    <div class="col-md-4">
        <div class="card h-100"><div class="card-body">
            <h5 class="card-title">Profesores</h5>
            <p class="card-text">Catálogo de profesores.</p>
            <a href="Profesores.jsp" class="btn btn-primary">Abrir</a>
        </div></div>
    </div>
    <div class="col-md-4">
        <div class="card h-100"><div class="card-body">
            <h5 class="card-title">Materias</h5>
            <p class="card-text">Catálogo de materias.</p>
            <a href="Materias.jsp" class="btn btn-primary">Abrir</a>
        </div></div>
    </div>
    <div class="col-md-6">
        <div class="card h-100"><div class="card-body">
            <h5 class="card-title">Alumno - Materia</h5>
            <p class="card-text">Inscripciones de alumnos a materias y sus calificaciones.</p>
            <a href="AlumnoMaterias.jsp" class="btn btn-primary">Abrir</a>
        </div></div>
    </div>
    <div class="col-md-6">
        <div class="card h-100"><div class="card-body">
            <h5 class="card-title">Profesor - Materia</h5>
            <p class="card-text">Materias que imparte cada profesor.</p>
            <a href="ProfesorMaterias.jsp" class="btn btn-primary">Abrir</a>
        </div></div>
    </div>
</div>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
</body>
</html>
