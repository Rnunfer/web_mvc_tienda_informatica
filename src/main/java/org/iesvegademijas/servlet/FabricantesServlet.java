package org.iesvegademijas.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.List;
import java.util.*;
import static java.util.stream.Collectors.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.iesvegademijas.dao.FabricanteDAO;
import org.iesvegademijas.dao.FabricanteDAOImpl;
import org.iesvegademijas.dto.FabricanteDTO;
import org.iesvegademijas.model.Fabricante;

public class FabricantesServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	/**
	 * HTTP Method: GET
	 * Paths: 
	 * 		/fabricantes/
	 * 		/fabricantes/{id}
	 * 		/fabricantes/editar/{id}
	 * 		/fabricantes/crear
	 * 		/fabricantes/borrar
	 */		
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		RequestDispatcher dispatcher;
				
		String pathInfo = request.getPathInfo(); //
			
		if (pathInfo == null || "/".equals(pathInfo)) {
			
			FabricanteDAO fabDAO = new FabricanteDAOImpl();
			
			String ordenarPor = (String)request.getParameter("ordenar-por");
			String modoOrdenar = (String)request.getParameter("modo-ordenar");
			
			List<FabricanteDTO> listaOrdenada;
			if (ordenarPor != null && modoOrdenar != null) {
				listaOrdenada = fabDAO.getAllDTOPlusCountProductos(ordenarPor, modoOrdenar);
			} else {
				listaOrdenada = fabDAO.getAllDTOPlusCountProductos();
			}
			
			/*if (ordenarPor != null && modoOrdenar != null) {
				if (ordenarPor.equals("nombre") && modoOrdenar.equals("asc")) {
					listaOrdenada = listaOrdenada.stream()
							.sorted(Comparator.comparing(FabricanteDTO::getNombre))
							.collect(toList());
					
				} else if (ordenarPor.equals("nombre") && modoOrdenar.equals("desc")) {
					listaOrdenada = listaOrdenada.stream()
							.sorted(Comparator.comparing(FabricanteDTO::getNombre).reversed())
		            		.collect(toList());
		
				} else if (ordenarPor.equals("codigo") && modoOrdenar.equals("asc")) {
					listaOrdenada = listaOrdenada.stream()
							.sorted(Comparator.comparing(FabricanteDTO::getCodigo))
		            		.collect(toList());
		
				} else if (ordenarPor.equals("codigo") && modoOrdenar.equals("desc")) {
					listaOrdenada = listaOrdenada.stream()
							.sorted(Comparator.comparing(FabricanteDTO::getCodigo).reversed())
		            		.collect(toList());
		
				}
			}*/
				
			//GET 
			//	/fabricantes/
			//	/fabricantes
			
			/*var lista = fabDAO.getAll().stream().map( f -> {
				
				FabricanteDTO fDTO = new FabricanteDTO(f);
				fDTO.setNumProductos(fabDAO.getCountProductos(f.getCodigo()));
				
				return fDTO;
			}).collect(toList());*/
			
			request.setAttribute("listaFabricantes", listaOrdenada);		
			dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/fabricantes.jsp");
			        		       
		} else {
			// GET
			// 		/fabricantes/{id}
			// 		/fabricantes/{id}/
			// 		/fabricantes/edit/{id}
			// 		/fabricantes/edit/{id}/
			// 		/fabricantes/create
			// 		/fabricantes/create/
			
			pathInfo = pathInfo.replaceAll("/$", "");
			String[] pathParts = pathInfo.split("/");
			
			if (pathParts.length == 2 && "crear".equals(pathParts[1])) {
				
				// GET
				// /fabricantes/create									
				dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/crear-fabricante.jsp");
        												
			
			} else if (pathParts.length == 2) {
				FabricanteDAO fabDAO = new FabricanteDAOImpl();
				// GET
				// /fabricantes/{id}
				try {
					request.setAttribute("fabricante",fabDAO.find(Integer.parseInt(pathParts[1])));
					dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/detalle-fabricante.jsp");
					        								
				} catch (NumberFormatException nfe) {
					nfe.printStackTrace();
					dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/fabricantes.jsp");
				}
				
			} else if (pathParts.length == 3 && "editar".equals(pathParts[1]) ) {
				FabricanteDAO fabDAO = new FabricanteDAOImpl();
				
				// GET
				// /fabricantes/edit/{id}
				try {
					request.setAttribute("fabricante",fabDAO.find(Integer.parseInt(pathParts[2])));
					dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/editar-fabricante.jsp");
					        								
				} catch (NumberFormatException nfe) {
					nfe.printStackTrace();
					dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/fabricantes.jsp");
				}
				
				
			} else {
				
				System.out.println("Opción POST no soportada.");
				dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/fabricantes.jsp");
			
			}
			
		}
		
		dispatcher.forward(request, response);
			 
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		RequestDispatcher dispatcher;
		String __method__ = request.getParameter("__method__");
		
		if (__method__ == null) {
			// Crear uno nuevo
			FabricanteDAO fabDAO = new FabricanteDAOImpl();
			
			String nombre = request.getParameter("nombre");
			Fabricante nuevoFab = new Fabricante();
			nuevoFab.setNombre(nombre);
			fabDAO.create(nuevoFab);			
			
		} else if (__method__ != null && "put".equalsIgnoreCase(__method__)) {			
			// Actualizar uno existente
			//Dado que los forms de html sólo soportan method GET y POST utilizo parámetro oculto para indicar la operación de actulización PUT.
			doPut(request, response);
			
		
		} else if (__method__ != null && "delete".equalsIgnoreCase(__method__)) {			
			// Actualizar uno existente
			//Dado que los forms de html sólo soportan method GET y POST utilizo parámetro oculto para indicar la operación de actulización DELETE.
			doDelete(request, response);
			
			
			
		} else {
			
			System.out.println("Opción POST no soportada.");
			
		}
		
		response.sendRedirect("/tienda_informatica/fabricantes");
		//response.sendRedirect("/tienda_informatica/fabricantes");
		
		
	}
	
	
	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		FabricanteDAO fabDAO = new FabricanteDAOImpl();
		String codigo = request.getParameter("codigo");
		String nombre = request.getParameter("nombre");
		Fabricante fab = new Fabricante();
		
		try {
			
			int id = Integer.parseInt(codigo);
			fab.setCodigo(id);
			fab.setNombre(nombre);
			fabDAO.update(fab);
			
		} catch (NumberFormatException nfe) {
			nfe.printStackTrace();
		}
		
	}
	
	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		RequestDispatcher dispatcher;
		FabricanteDAO fabDAO = new FabricanteDAOImpl();
		String codigo = request.getParameter("codigo");
		
		try {
			
			int id = Integer.parseInt(codigo);
		
		fabDAO.delete(id);
			
		} catch (NumberFormatException nfe) {
			nfe.printStackTrace();
		}
		
	}
	
}
