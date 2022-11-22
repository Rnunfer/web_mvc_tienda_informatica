<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="org.iesvegademijas.model.Producto"%>
<%@page import="java.util.List"%>

<!DOCTYPE html>
<html>
<%@ include file="/WEB-INF/jsp/head.jspf" %>
<body>

	<div id="contenedora" style="float:none; margin: 0 auto;width: 900px;" >
	<%@ include file="header.jspf" %>
	<%@ include file="nav.jspf" %>
	<%@ include file="main.jspf" %>
		<div class="clearfix">
		</div>
		<form method="get" action="/tienda_informatica/productos/">
			<input type="text" name="filtrar-por-nombre" <% if(request.getParameter("filtrar-por-nombre") != null) { %> value="<% request.getParameter("filtrar-por-nombre");%>"<% }; %>><input type="submit" value="Buscar">
		</form>
		<div class="clearfix">
			<hr/>
		</div>
		<div class="clearfix">
			<div style="float: left;width: 10%">Código</div>
			<div style="float: left;width: 30%">Nombre</div>
			<div style="float: left;width: 20%">Precio</div>
			<div style="float: left;width: 10%">Código del fabricante</div>
			<div style="float: none;width: auto;overflow: hidden;">Acción</div>
		</div>
		<div class="clearfix">
			<hr/>
		</div>
	<% 
        if (request.getAttribute("listaProductos") != null) {
            List<Producto> listaProductos = (List<Producto>)request.getAttribute("listaProductos");
            
            for (Producto producto : listaProductos) {
    %>

		<div style="margin-top: 6px;" class="clearfix">
			<div style="float: left;width: 10%"><%= producto.getCodigo()%></div>
			<div style="float: left;width: 30%"><%= producto.getNombre()%></div>
			<div style="float: left;width: 20%"><%= producto.getPrecio()%></div>
			<div style="float: left;width: 10%"><%= producto.getCodigoFabricante()%></div>
			<div style="float: none;width: auto;overflow: hidden;">
				<form action="/tienda_informatica/productos/<%= producto.getCodigo()%>" style="display: inline;">
    				<input type="submit" value="Ver Detalle" />
				</form>
				<form action="/tienda_informatica/productos/editar/<%= producto.getCodigo()%>" style="display: inline;">
    				<input type="submit" value="Editar" />
				</form>
				<form action="/tienda_informatica/productos/borrar/" method="post" style="display: inline;">
					<input type="hidden" name="__method__" value="delete"/>
					<input type="hidden" name="codigo" value="<%= producto.getCodigo()%>"/>
    				<input type="submit" value="Eliminar" />
				</form>
			</div>
		</div>

	<% 
            }
        } else { 
    %>
		No hay registros de fabricante
	<% } %>
	</div>
	<%@ include file ="/WEB-INF/jsp/footer.jspf"%>
</body>
</html>