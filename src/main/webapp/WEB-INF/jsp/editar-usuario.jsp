<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="org.iesvegademijas.model.Usuario"%>
<%@page import="java.util.Optional"%>
<!DOCTYPE html>
<html>
<%@ include file="/WEB-INF/jsp/head.jspf" %>
<body>

<%@ include file="header.jspf" %>
<%@ include file="nav.jspf" %>
<div id="contenedora" style="float:none; margin: 0 auto;width: 900px;" >
	<form action="/tienda_informatica/usuarios/editar/" method="post" >
		<input type="hidden" name="__method__" value="put" />
		<div class="clearfix">
			<div style="float: left; width: 50%">
				<h1>Editar Usuario</h1>
			</div>
			<div style="float: none;width: auto;overflow: hidden;min-height: 80px;position: relative;">
				
				<div style="position: absolute; left: 39%; top : 39%;">
							<input type="submit" value="Guardar" />						
				</div>
				
			</div>
		</div>
		
		<div class="clearfix">
			<hr/>
		</div>
		
		<% 	Optional<Usuario> optUsu = (Optional<Usuario>)request.getAttribute("usuario");
			if (optUsu.isPresent()) {
		%>
		
		<div style="margin-top: 6px;" class="clearfix">
			<div style="float: left;width: 25%">
				<label>Código</label>
			</div>
			<div style="float: none;width: auto;overflow: hidden;">
				<input name="codigo" value="<%= optUsu.get().getCodigo() %>" readonly="readonly"/>
			</div> 
		</div>
		<div style="margin-top: 6px;" class="clearfix">
			<div style="float: left;width: 25%">
				<label>Nombre</label>
			</div>
			<div style="float: none;width: auto;overflow: hidden;">
				<input name="nombre" value="<%= optUsu.get().getNombre() %>"/>
			</div> 
		</div>
		<div style="margin-top: 6px;" class="clearfix">
			<div style="float: left;width: 25%">
				<label>Contraseña</label>
			</div>
			<div style="float: none;width: auto;overflow: hidden;">
				<input name="contrasenia" value="<%= optUsu.get().getContraseña() %>"/>
			</div> 
		</div>
		<div style="margin-top: 6px;" class="clearfix">
			<div style="float: left;width: 25%">
				<label>Rol</label>
			</div>
			<div style="float: none;width: auto;overflow: hidden;">
				<input name="rol" value="<%= optUsu.get().getRol() %>"/>
			</div> 
		</div>
		
		
		<% 	} else { %>
			
				request.sendRedirect("usuarios/");
		
		<% 	} %>
	</form>
</div>

</body>
</html>