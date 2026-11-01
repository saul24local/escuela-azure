<%@ page import="org.gerdoc.service.MateriaService" %>
<%@ page import="org.gerdoc.service.impl.MateriaServiceImpl" %>
<%@ page import="org.gerdoc.model.Materia" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Bootstrap demo</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
</head>
<body>
<div class="container">
    <h1>Proyecto funcionando</h1>
    <%
        String materiaId = request.getParameter( "id" );

        MateriaService materiaService = null;
        Long id = null;
        Materia materia = null;
        if( materiaId != null )
        {
            id = Long.parseLong( materiaId );
            materiaService = new MateriaServiceImpl( );
            materia = materiaService.findById( id );
        }
    %>
    <table class="table">
        <thead>
        <tr class="table-dark" align="center">
            <th scope="col" colspan="2" >Información</th>
        </tr>
        </thead>
        <%
        if( materia != null )
        {
        %>
            <tbody>
                <tr>
                    <td>Nombre</td>
                    <td><%= materia.getNombre( )%></td>
                </tr>
                <tr>
                    <td>Edad</td>
                    <td><%= materia.getCreditos( )%></td>
                </tr>
            </tbody>
        <%
        }
        %>
    </table>
    <a href="Materias.jsp" class="btn btn-secondary">
        Regresar
    </a>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js" integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI" crossorigin="anonymous"></script>
</body>
</html>