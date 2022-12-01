<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="org.iesvegademijas.model.Producto"%>
<%@page import="org.iesvegademijas.model.Fabricante"%>
<%@page import="java.util.Optional"%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html>
<%@ include file="/WEB-INF/jsp/head.jspf" %>
<body>
	<%@ include file="header.jspf" %>
	<%@ include file="nav.jspf" %>
<div id="contenedora" style="float:none; margin: 0 auto;width: 900px;" >
	
	<form action="/tienda_informatica/productos/editar/" method="post" >
		<input type="hidden" name="__method__" value="put" />
		<div class="clearfix">
			<div style="float: left; width: 50%">
				<h1>Editar Producto</h1>
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
		
		<% 	Optional<Producto> optProd = (Optional<Producto>)request.getAttribute("producto");
			if (optProd.isPresent()) {
		%>
		
		<div style="margin-top: 6px;" class="clearfix">
			<div style="float: left;width: 25%">
				<label>Código</label>
			</div>
			<div style="float: none;width: auto;overflow: hidden;">
				<input name="codigo" value="<%= optProd.get().getCodigo() %>" readonly="readonly"/>
			</div> 
		</div>
		<div style="margin-top: 6px;" class="clearfix">
			<div style="float: left;width: 25%">
				<label>Nombre</label>
			</div>
			<div style="float: none;width: auto;overflow: hidden;">
				<input name="nombre" value="<%= optProd.get().getNombre() %>"/>
			</div> 
		</div>
		<div style="margin-top: 6px;" class="clearfix">
			<div style="float: left;width: 25%">
				<label>Precio</label>
			</div>
			<div style="float: none;width: auto;overflow: hidden;">
				<input name="precio" value="<%= optProd.get().getPrecio() %>"/>
			</div> 
		</div>
		<div style="margin-top: 6px;" class="clearfix">
			<div style="float: left;width: 50%">
				Código del fabricante
			</div>
			<select name="codigo_fabricante">
			<% 
       		if (request.getAttribute("listaFabricantes") != null) {
            List<Fabricante> listaFab = (List<Fabricante>)request.getAttribute("listaFabricantes");
				for (Fabricante Fab: listaFab) { %>
				<option value="<%= Fab.getCodigo() %>" <% if(optProd.get().getCodigoFabricante() == Fab.getCodigo()) { %> selected <% }; %>><%= Fab.getNombre() %></option>
				<% }
			} %>
			</select>
		</div>
		
		
		<% 	} else { %>
			
				request.sendRedirect("productos/");
		
		<% 	} %>
	</form>
</div>

</body>
</html>