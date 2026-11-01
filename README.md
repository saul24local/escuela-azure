# Sistema Escolar – Azure (Tomcat + Spring Boot + MySQL)

Dos aplicaciones sobre la base de datos `ESCUELA`, con los 5 catálogos
(ALUMNO, PROFESOR, MATERIA, ALUMNO_MATERIA, PROFESOR_MATERIA):

| Carpeta | Tecnología | Puerto | URL (detrás de Apache) |
|---|---|---|---|
| `Web/` | JSP + Servlets + Hibernate (WAR en Tomcat 9) | 8080 | `/Web/` |
| `Escuela-SpringBoot/` | Spring Boot 4 + JPA + Swagger | 9080 | `/swagger-ui.html` |
| `sql/Escuela.sql` | Script de la base de datos con datos de ejemplo | 3306 | – |

## Requisitos
Java 17, Maven, MySQL 8, Tomcat 9.

## Contraseña de MySQL (no se guarda en el repositorio)
Ambas aplicaciones leen la contraseña de la variable de entorno `DB_PASSWORD`
(opcionales: `DB_USER`, `DB_URL`).

```bash
export DB_PASSWORD='tu_contraseña'
```

## Crear la base de datos
```bash
mysql -u root -p < sql/Escuela.sql
```
(Borra y vuelve a crear `ESCUELA` con datos de ejemplo.)

## Aplicación web (Tomcat)
```bash
cd Web
mvn clean package
cp target/Web.war $TOMCAT_HOME/webapps/
$TOMCAT_HOME/bin/startup.sh
```
Abrir `http://<servidor>:8080/Web/` (o `http://<dominio>/Web/` con Apache como proxy).

## Spring Boot
```bash
cd Escuela-SpringBoot
mvn clean package
mvn spring-boot:run
```
Swagger: `http://<servidor>:9080/swagger-ui.html`

Endpoints REST (GET, POST, PUT, DELETE en cada uno):
`/api/alumnos`, `/api/profesores`, `/api/materias`,
`/api/alumno-materias`, `/api/profesor-materias`.
Los borrados que violan integridad referencial responden `409 Conflict`.

## Apache como proxy
```apache
<VirtualHost *:80>
    ServerName <tu-dominio>
    ProxyPreserveHost On
    ProxyRequests Off
    ProxyPass /Web/ http://127.0.0.1:8080/Web/
    ProxyPass / http://127.0.0.1:9080/
    ProxyPassReverse /Web/ http://127.0.0.1:8080/Web/
    ProxyPassReverse / http://127.0.0.1:9080/
</VirtualHost>
```
