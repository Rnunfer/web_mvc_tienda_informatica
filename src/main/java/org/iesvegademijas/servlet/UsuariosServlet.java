package org.iesvegademijas.servlet;

import java.io.IOException;
import java.util.*;
import static java.util.stream.Collectors.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.iesvegademijas.dao.UsuarioDAO;
import org.iesvegademijas.dao.UsuarioDAOImpl;
import org.iesvegademijas.model.Usuario;

/**
 * Servlet implementation class UsuariosServlet
 */
@WebServlet("/usuarios/*")
public class UsuariosServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     * HTTP Method: GET
     * Paths: 
	 * 		/usuarios/(index) 		-- muestra listado principal con operadones CRUD
	 * 		/usuarios/{id} 		-- ver detalles de producto con {id}
	 * 		/usuarios/editar/{id}	-- accede al formulario para editar usuario con {id}
	 * 		/usuarios/crear		-- accede al formulario para crear un usuario nuevo
	 * 		/usuarios/borrar		-- accede al formulario para borrar un usuario con {id}
	 * 		/usuarios/login     -- accede al formulario para logearse como un usuario
     */
    public UsuariosServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		RequestDispatcher dispatcher = null;;
		
		String pathInfo = request.getPathInfo(); //
			
		if (pathInfo == null || "/".equals(pathInfo)) {
			UsuarioDAO usuDAO = new UsuarioDAOImpl();
			
			//GET 
			//	/usuarios/
			//	/usuarios
						
			request.setAttribute("listaUsuarios", usuDAO.getAll());		
			dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/usuarios.jsp");
			        		       
		} else {
			// GET
			// 		/usuarios/{id}
			// 		/usuarios/{id}/
			// 		/usuarios/edit/{id}
			// 		/usuarios/edit/{id}/
			// 		/usuarios/create
			// 		/usuarios/create/
			//		/usaurios/login
			//		/usaurios/login/
			
			pathInfo = pathInfo.replaceAll("/$", "");
			String[] pathParts = pathInfo.split("/");
			
			if (pathParts.length == 2 && "crear".equals(pathParts[1])) {
				
				UsuarioDAO usuDAO = new UsuarioDAOImpl();
				
				// GET
				// /usuarios/create
				request.setAttribute("listaUsuarios", usuDAO.getAll());
				dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/crear-usuario.jsp");
        												
			} else if (pathParts.length == 2 && "login".equals(pathParts[1]) ) {
					UsuarioDAO usuDAO = new UsuarioDAOImpl();
					
					// GET
					// /usuarios/login
					request.setAttribute("listaUsuarios", usuDAO.getAll());
					dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
					
			} else if (pathParts.length == 2) {
				UsuarioDAO usuDAO = new UsuarioDAOImpl();
				// GET
				// /usuario/{id}
				try {
					request.setAttribute("usuario",usuDAO.find(Integer.parseInt(pathParts[1])));
					dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/detalle-usuario.jsp");
					        								
				} catch (NumberFormatException nfe) {
					nfe.printStackTrace();
					dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/usuarios.jsp");
				}
				
			} else if (pathParts.length == 3 && "editar".equals(pathParts[1]) ) {
				UsuarioDAO usuDAO = new UsuarioDAOImpl();
				
				// GET
				// /usuarios/edit/{id}
				try {
					request.setAttribute("usuario",usuDAO.find(Integer.parseInt(pathParts[2])));
					dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/editar-usuario.jsp");
					        								
				} catch (NumberFormatException nfe) {
					nfe.printStackTrace();
					dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/usuarios.jsp");
				}
				
			} else {
				
				System.out.println("Opción POST no soportada.");
				dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/usuarios.jsp");
			
			}
			
		}
		
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		RequestDispatcher dispatcher;
		String __method__ = request.getParameter("__method__");
		
		if (__method__ == null) {
			// Crear uno nuevo
			UsuarioDAO usuDAO = new UsuarioDAOImpl();
			
			String nombre = request.getParameter("nombre");
			String contraseña = request.getParameter("contrasenia");
			String rol = request.getParameter("rol");
			Usuario nuevoUsu = new Usuario();
			
			nuevoUsu.setNombre(nombre);
			nuevoUsu.setContraseña(contraseña);
			nuevoUsu.setRol(rol);
			usuDAO.create(nuevoUsu);			
			
		} else if (__method__ != null && "put".equalsIgnoreCase(__method__)) {			
			// Actualizar uno existente
			//Dado que los forms de html sólo soportan method GET y POST utilizo parámetro oculto para indicar la operación de actualización PUT.
			doPut(request, response);
			
		
		} else if (__method__ != null && "delete".equalsIgnoreCase(__method__)) {			
			// Actualizar uno existente
			//Dado que los forms de html sólo soportan method GET y POST utilizo parámetro oculto para indicar la operación de actualización DELETE.
			doDelete(request, response);
			
			
		} else if (__method__ != null && "login".equalsIgnoreCase(__method__)) {
			// Logearse a una cuenta
			//Dado que los forms de html sólo soportan method GET y POST utilizo parámetro oculto para indicar la operación de actualización LOGIN.
			 doLogin(request, response);
		
		} else if (__method__ != null && "logout".equalsIgnoreCase(__method__)) {
			doLogout(request, response);
			
		} else {
			
			System.out.println("Opción POST no soportada.");
			
		}
		
		if (__method__ != null && ("login".equalsIgnoreCase(__method__) || "logout".equalsIgnoreCase(__method__))) {
			response.sendRedirect("/tienda_informatica/");
		} else {
			response.sendRedirect("/tienda_informatica/usuarios");
		}
		
		//response.sendRedirect("/tienda_informatica/usuarios");
	}
	
	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		UsuarioDAO usuDAO = new UsuarioDAOImpl();
		
		String codigo = request.getParameter("codigo");
		String nombre = request.getParameter("nombre");
		String contraseña = request.getParameter("contrasenia");
		String rol = request.getParameter("rol");
		Usuario usu = new Usuario();
		
		try {
			
			int id = Integer.parseInt(codigo);
			usu.setCodigo(id);
			usu.setNombre(nombre);
			usu.setContraseña(contraseña);
			usu.setRol(rol);
			usuDAO.update(usu);
			
		} catch (NumberFormatException nfe) {
			nfe.printStackTrace();
		}
		
	}
	
	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		RequestDispatcher dispatcher;
		UsuarioDAO usuDAO = new UsuarioDAOImpl();
		String codigo = request.getParameter("codigo");
		
		try {
			
			int id = Integer.parseInt(codigo);
		
			usuDAO.delete(id);
			
		} catch (NumberFormatException nfe) {
			nfe.printStackTrace();
		}
		
	}
	
	protected void doLogin(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		UsuarioDAO usuDAO = new UsuarioDAOImpl();
		
		String nombre = request.getParameter("nombre");
		String contraseña = request.getParameter("contrasenia");
		Usuario usuario = usuDAO.loginUsuario(nombre, contraseña);
		if (usuario.getCodigo() != 0) {
			HttpSession session=request.getSession(true);  
			session.setAttribute("usuario-logeado", usuario); 
		}

		 
	}
	
	protected void doLogout(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		HttpSession session=request.getSession();
		session.invalidate();
	}
}
