<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="org.iesvegademijas.model.Usuario"%>
<%@page import="java.util.List"%>

<!DOCTYPE html>
<html>
<%@ include file="/WEB-INF/jsp/head.jspf" %>
<body>

	<div id="contenedora" style="float:none; margin: 0 auto;width: 900px;" >
	<%@ include file="header.jspf" %>
	<%@ include file="nav.jspf" %>
	<div class="clearfix">
			<div style="float: left; width: 50%">
				<h1>Usuarios</h1>
			</div>
			<div style="float: none;width: auto;overflow: hidden;min-height: 80px;position: relative;">
				
				<div style="position: absolute; left: 39%; top : 39%;">
					
						<form action="/tienda_informatica/usuarios/crear">
							<input type="submit" value="Crear">
						</form>
					</div>
				
			</div>
		</div>
		<div class="clearfix">
			<hr/>
		</div>
		<div class="clearfix">
			<div style="float: left;width: 10%">Código</div>
			<div style="float: left;width: 20%">Nombre</div>
			<div style="float: left;width: 20%">Contraseña</div>
			<div style="float: left;width: 20%">Rol</div>
			<div style="float: none;width: auto;overflow: hidden;">Acción</div>
		</div>
		<div class="clearfix">
			<hr/>
		</div>
	<% 
	
        if (request.getAttribute("listaUsuarios") != null) {
            List<Usuario> listaUsuarios = (List<Usuario>)request.getAttribute("listaUsuarios");
            
            for (Usuario usuario : listaUsuarios) {
    %>

		<div style="margin-top: 6px;" class="clearfix">
			<div style="float: left;width: 10%"><%= usuario.getCodigo()%></div>
			<div style="float: left;width: 20%"><%= usuario.getNombre()%></div>
			<div style="float: left;width: 20%"><%= usuario.getContraseña()%></div>
			<div style="float: left;width: 20%"><%= usuario.getRol()%></div>
			<div style="float: none;width: auto;overflow: hidden;">
				<form action="/tienda_informatica/usuarios/<%= usuario.getCodigo()%>" style="display: inline;">
    				<input type="submit" value="Ver Detalle" />
				</form>
				<form action="/tienda_informatica/usuarios/editar/<%= usuario.getCodigo()%>" style="display: inline;">
    				<input type="submit" value="Editar" />
				</form>
				<form action="/tienda_informatica/usuarios/borrar/" method="post" style="display: inline;">
					<input type="hidden" name="__method__" value="delete"/>
					<input type="hidden" name="codigo" value="<%= usuario.getCodigo()%>"/>
    				<input type="submit" value="Eliminar" />
				</form>
			</div>
		</div>

	<% 
            }
        } else { 
    %>
		No hay registros de usuario
	<% } %>
	</div>
	<%@ include file ="footer.jspf"%>
</body>
</html>