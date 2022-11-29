<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="org.iesvegademijas.model.Usuario"%>
<%@page import="java.util.List"%>

<!DOCTYPE html>
<html>
<%@ include file="/WEB-INF/jsp/head.jspf" %>
<body>

<%@ include file="header.jspf" %>
<%@ include file="nav.jspf" %>
	<div id="contenedora" style="float:none; margin: 0 auto;width: 900px;" >
	
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
			<div style="float: left;width: 15%">Nombre</div>
			<div style="float: left;width: 25%">Contraseña</div>
			<div style="float: left;width: 20%">Rol</div>
			<div style="float: none;width: auto;overflow: hidden;">Acción</div>
		</div>
		<div class="clearfix">
			<hr/>
		</div>
	<% 
	
        if (request.getAttribute("listaUsuarios") != null) {
            List<Usuario> listaUsuarios = (List<Usuario>)request.getAttribute("listaUsuarios");
            
            for (Usuario usu : listaUsuarios) {
    %>

		<div style="margin-top: 6px;" class="clearfix">
			<div style="float: left;width: 10%"><%= usu.getCodigo()%></div>
			<div style="float: left;width: 15%"><%= usu.getNombre()%></div>
			<textarea  rows="1" readonly="readonly" style="float: left;width: 25%; margin-right: 5px"><%= usu.getContraseña()%></textarea>
			<div style="float: left;width: 20%"><%= usu.getRol()%></div>
			<div style="float: none;width: auto;overflow: hidden;">
				<form action="/tienda_informatica/usuarios/<%= usu.getCodigo()%>" style="display: inline;">
    				<input type="submit" value="Ver Detalle" />
				</form>
				<form action="/tienda_informatica/usuarios/editar/<%= usu.getCodigo()%>" style="display: inline;">
    				<input type="submit" value="Editar" />
				</form>
				<form action="/tienda_informatica/usuarios/borrar/" method="post" style="display: inline;">
					<input type="hidden" name="__method__" value="delete"/>
					<input type="hidden" name="codigo" value="<%= usu.getCodigo()%>"/>
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