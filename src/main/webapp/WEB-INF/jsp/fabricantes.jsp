<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="org.iesvegademijas.dto.FabricanteDTO"%>
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
				<h1>Fabricantes</h1>
			</div>
			<div style="float: none;width: auto;overflow: hidden;min-height: 80px;position: relative;">
				
				<div style="position: absolute; left: 39%; top : 39%;">
					
						<form action="/tienda_informatica/fabricantes/crear">
							<input type="submit" value="Crear">
						</form>
					</div>
				
			</div>
		</div>
		<form method="get" action="/tienda_informatica/fabricantes/">
			<select name="ordenar-por">
				<option value="nombre" <% if (request.getParameter("ordenar-por") != null && request.getParameter("ordenar-por").equals("nombre")) {%> selected <% } %>>Nombre</option>
				<option value="codigo" <% if (request.getParameter("ordenar-por") != null && request.getParameter("ordenar-por").equals("codigo")) {%> selected <% } %>>Código</option>
			</select>
			<select name="modo-ordenar">
				<option value="asc"<% if (request.getParameter("modo-ordenar") != null && request.getParameter("modo-ordenar").equals("asc")) {%> selected <% } %>>Ascendente</option>
				<option value="desc"<% if (request.getParameter("modo-ordenar") != null && request.getParameter("modo-ordenar").equals("desc")) {%> selected <% } %>>Descendente</option>
			</select>
			<input type="submit" value="Ordenar">
		</form>
		<div class="clearfix">
			<hr/>
		</div>
		<div class="clearfix">
			<div style="float: left;width: 20%">Código</div>
			<div style="float: left;width: 30%">Nombre</div>
			<div style="float: left;width: 20%">Número de productos</div>
			<div style="float: none;width: auto;overflow: hidden;">Acción</div>
		</div>
		<div class="clearfix">
			<hr/>
		</div>
	<% 
		String ordenarPor = (String)request.getParameter("ordenar-por");
		String modoOrdenar = (String)request.getParameter("modo-ordenar");
	
        if (request.getAttribute("listaFabricantes") != null) {
            List<FabricanteDTO> listaFabricante = (List<FabricanteDTO>)request.getAttribute("listaFabricantes");
            
            /*listaFabricante = listaFabricante.stream()
            		.filter(f -> f.getNumProductos() > 1)
            		//.sorted(comparing(FabricanteDTO::getNombre))
            		.collect(toList());*/
            for (FabricanteDTO fabricante : listaFabricante) {
    %>

		<div style="margin-top: 6px;" class="clearfix">
			<div style="float: left;width: 20%"><%= fabricante.getCodigo()%></div>
			<div style="float: left;width: 30%"><%= fabricante.getNombre()%></div>
			<div style="float: left;width: 20%"><%= fabricante.getNumProductos().get()%></div>
			<div style="float: none;width: auto;overflow: hidden;">
				<form action="/tienda_informatica/fabricantes/<%= fabricante.getCodigo()%>" style="display: inline;">
    				<input type="submit" value="Ver Detalle" />
				</form>
				<form action="/tienda_informatica/fabricantes/editar/<%= fabricante.getCodigo()%>" style="display: inline;">
    				<input type="submit" value="Editar" />
				</form>
				<form action="/tienda_informatica/fabricantes/borrar/" method="post" style="display: inline;">
					<input type="hidden" name="__method__" value="delete"/>
					<input type="hidden" name="codigo" value="<%= fabricante.getCodigo()%>"/>
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
	<%@ include file ="footer.jspf"%>
</body>
</html>