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
	
	<form action="/tienda_informatica/usuarios/login/" method="post">
	<input type="hidden" name="__method__" value="login" />
		<div class="clearfix">
			<div style="float: left; width: 100%">
				<h1>Iniciar Sesión</h1>
			</div>
		</div>		
		<div class="clearfix">
			<hr/>
		</div>
		<div style="margin-top: 6px;" class="clearfix">
			<div style="float: left;width: 30%">
				Nombre
			</div>
			<div style="float: none;width: auto;overflow: hidden;">
				<input name="nombre" />
			</div> 
		</div>
		<div style="margin-top: 6px;" class="clearfix">
			<div style="float: left;width: 30%">
				Contraseña
			</div>
			<div style="float: none;width: auto;overflow: hidden;">
				<input name="contrasenia" />
			</div> 
		</div>
		<div style="width: 29.5%;display: inline-block;"> </div>
		<div style="margin-top: 20px; display:inline-block;" class="clearfix">
			<input type="submit" value="Iniciar sesión">
		</div>

	</form>
</div>

</body>
</html>