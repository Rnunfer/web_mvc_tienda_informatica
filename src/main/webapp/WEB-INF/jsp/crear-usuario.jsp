<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="org.iesvegademijas.model.Usuario"%>
<%@page import="java.util.Optional"%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html>
<%@ include file="/WEB-INF/jsp/head.jspf" %>
<body>
	<%@ include file="header.jspf" %>
	<%@ include file="nav.jspf" %>
<div id="contenedora" style="float:none; margin: 0 auto;width: 900px;" >
	
	<form action="/tienda_informatica/usuarios/crear/" method="post">
		<div class="clearfix">
			<div style="float: left; width: 50%">
				<h1>Crear Usuario</h1>
			</div>
			<div style="float: none;width: auto;overflow: hidden;min-height: 80px;position: relative;">
				
				<div style="position: absolute; left: 39%; top : 39%;">								
					<input type="submit" value="Crear"/>					
				</div>
				
			</div>
			
		</div>
		
		<div class="clearfix">
			<hr/>
		</div>
		
		<div style="margin-top: 6px;" class="clearfix">
			<div style="float: left;width: 50%">
				Nombre
			</div>
			<div style="float: none;width: auto;overflow: hidden;">
				<input name="nombre" />
			</div> 
		</div>
		<div style="margin-top: 6px;" class="clearfix">
			<div style="float: left;width: 50%">
				Contraseña
			</div>
			<div style="float: none;width: auto;overflow: hidden;">
				<input name="contrasenia" />
			</div> 
		</div>
		<div style="margin-top: 6px;" class="clearfix">
			<div style="float: left;width: 50%">
				Rol
			</div>
			<select name="rol">
				<option value="Administrador">Administrador</option>
				<option value="Cliente">Cliente</option>
				<option value="Vendedor">Vendedor</option>
			</select>
		</div>

	</form>
</div>
<%@ include file ="footer.jspf"%>
</body>
</html>