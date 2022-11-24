package org.iesvegademijas.dao;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.iesvegademijas.model.Producto;
import org.iesvegademijas.model.Usuario;

public class UsuarioDAOImpl extends AbstractDAOImpl implements UsuarioDAO{

	/**
	 * Inserta en base de datos el nuevo fabricante, actualizando el id en el bean producto.
	 */
	@Override	
	public synchronized void create(Usuario usuario) {
		
		Connection conn = null;
		PreparedStatement ps = null;
        ResultSet rs = null;
        ResultSet rsGenKeys = null;

        try {
        	conn = connectDB();


        	//1 alternativas comentadas:       
        	//ps = conn.prepareStatement("INSERT INTO fabricante (nombre) VALUES (?)", new String[] {"codigo"});        	
        	//Ver también, AbstractDAOImpl.executeInsert ...
        	//Columna producto.codigo es clave primaria auto_increment, por ese motivo se omite de la sentencia SQL INSERT siguiente. 
        	ps = conn.prepareStatement("INSERT INTO usuario (nombre, contrasenia, rol) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            
            int idx = 1;
            try {
            	ps.setString(idx++, usuario.getNombre());
				ps.setString(idx++, hashPassword(usuario.getContraseña()));
				ps.setString(idx, usuario.getRol());
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
            
            int rows = ps.executeUpdate();
            if (rows == 0) 
            	System.out.println("INSERT de producto con 0 filas insertadas.");
            
            rsGenKeys = ps.getGeneratedKeys();
            if (rsGenKeys.next()) 
            	usuario.setCodigo(rsGenKeys.getInt(1));
                      
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
            closeDb(conn, ps, rs);
        }
        
	}

	/**
	 * Devuelve lista con todos los productos.
	 */
	@Override
	public List<Usuario> getAll() {
		
		Connection conn = null;
		Statement s = null;
        ResultSet rs = null;
        
        List<Usuario> listUsu = new ArrayList<>(); 
        
        try {
        	conn = connectDB();

        	// Se utiliza un objeto Statement dado que no hay parámetros en la consulta.
        	s = conn.createStatement();
            		
        	rs = s.executeQuery("SELECT * FROM usuario");          
            while (rs.next()) {
            	Usuario usu = new Usuario();
            	usu.setCodigo(rs.getInt("codigo"));
            	usu.setNombre(rs.getString("nombre"));
            	usu.setContraseña(rs.getString("contrasenia"));
            	usu.setRol(rs.getString("rol"));
            	listUsu.add(usu);
            }
          
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
            closeDb(conn, s, rs);
        }
        return listUsu;
        
	}

	/**
	 * Devuelve Optional de producto con el ID dado.
	 */
	@Override
	public Optional<Usuario> find(int id) {
		
		Connection conn = null;
		PreparedStatement ps = null;
        ResultSet rs = null;

        try {
        	conn = connectDB();
        	
        	ps = conn.prepareStatement("SELECT * FROM usuario WHERE codigo = ?");
        	
        	int idx =  1;
        	ps.setInt(idx, id);
        	
        	rs = ps.executeQuery();
        	
        	if (rs.next()) {
        		Usuario usu = new Usuario();
        		usu.setCodigo(rs.getInt("codigo"));
        		usu.setNombre(rs.getString("nombre"));
        		usu.setContraseña(rs.getString("contrasenia"));
        		usu.setRol(rs.getString("rol"));
        		
        		return Optional.of(usu);
        	}
        	
        } catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
            closeDb(conn, ps, rs);
        }
        
        return Optional.empty();
        
	}
	/**
	 * Actualiza fabricante con campos del bean producto según ID del mismo.
	 */
	@Override
	public void update(Usuario usuario) {
		
		Connection conn = null;
		PreparedStatement ps = null;
        ResultSet rs = null;

        try {
        	conn = connectDB();
        	
        	ps = conn.prepareStatement("UPDATE usuario SET nombre = ?, contrasenia = ?, rol = ?  WHERE codigo = ?");
        	int idx = 1;
        	ps.setString(idx++, usuario.getNombre());
        	ps.setString(idx++, usuario.getContraseña());
        	ps.setString(idx++, usuario.getRol());
        	ps.setInt(idx, usuario.getCodigo());
        	
        	int rows = ps.executeUpdate();
        	
        	if (rows == 0) 
        		System.out.println("Update de usuario con 0 registros actualizados.");
        	
        } catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
            closeDb(conn, ps, rs);
        }
    
	}

	/**
	 * Borra producto con ID proporcionado.
	 */
	@Override
	public void delete(int id) {
		
		Connection conn = null;
		PreparedStatement ps = null;
        ResultSet rs = null;

        try {
        	conn = connectDB();
        	
        	ps = conn.prepareStatement("DELETE FROM usuario WHERE codigo = ?");
        	int idx = 1;        	
        	ps.setInt(idx, id);
        	
        	int rows = ps.executeUpdate();
        	
        	if (rows == 0) 
        		System.out.println("Delete de usuario con 0 registros eliminados.");
        	
        } catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
            closeDb(conn, ps, rs);
        }
		
	}
	
	@Override
	public Usuario loginUsuario(String nombre, String contraseña) {
		
		Connection conn = null;
		PreparedStatement ps = null;
        ResultSet rs = null;
        
        Usuario usuarioLogin = new Usuario();
        try {
			contraseña = hashPassword(contraseña);
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        try {
        	conn = connectDB();

        	// Se utiliza un objeto Statement dado que no hay parámetros en la consulta.

        	ps = conn.prepareStatement("SELECT * FROM usuario WHERE MATCH(nombre) AGAINST (?);");
            
        	ps.setString(1, nombre);
        	rs = ps.executeQuery();
        	
            if (rs.next()) {
            	
            	ps.setString(1, contraseña);
            	rs = ps.executeQuery();
            	
            	if (rs.next()) {
            		usuarioLogin.setCodigo(rs.getInt("codigo"));
            		usuarioLogin.setNombre(rs.getString("nombre"));
            		usuarioLogin.setContraseña(rs.getString("contrasenia"));
            		usuarioLogin.setRol(rs.getString("rol"));
            	}
            	
            }
          
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
            closeDb(conn, ps, rs);
        }
		return usuarioLogin;
	}
	
	public static String hashPassword(String password ) throws NoSuchAlgorithmException {
		MessageDigest digest;
		
		digest = MessageDigest.getInstance("SHA-256");
		byte[] encodedhash = digest.digest(
				password.getBytes(StandardCharsets.UTF_8));
		
		return bytesToHex(encodedhash);					
		
	}
	
	private static String bytesToHex(byte[] byteHash) {
		
	    StringBuilder hexString = new StringBuilder(2 * byteHash.length);	  	
	    for (int i = 0; i < byteHash.length; i++) {
	        String hex = Integer.toHexString(0xff & byteHash[i]);
	        if(hex.length() == 1) {
	            hexString.append('0');
	        }
	        hexString.append(hex);
	    }
	    
	    return hexString.toString();
	    
	}

	

}

