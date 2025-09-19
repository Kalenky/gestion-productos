package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

import domain.Conexion;
import domain.Usuario;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    public Usuario logueo(String nombre, String pass) throws SQLException { // EXTRAER DATOS: SELECT (Consulta)
        Usuario user = null;

        Connection conn = Conexion.getConexion();

        String sql = "SELECT * FROM usuarios WHERE nombre = ? AND contrasena = ?";
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, nombre);
        ps.setString(2, pass);

        ResultSet rs = ps.executeQuery();

        if(rs.next()) {
            user = new Usuario();

            user.setId(rs.getInt("id"));
            user.setNombre(rs.getString("nombre"));
            user.setContrasena(rs.getString("contrasena"));
            user.setPregunta(rs.getString("pregunta"));
            user.setRespuesta(rs.getString("respuesta"));
            user.setHabilitado(rs.getInt("habilitado"));
            user.setRol(rs.getString("rol"));
            user.setFechaCreacion(rs.getDate("fecha_creacion"));
            user.setFechaActualizacion(rs.getDate("fecha_actualizacion"));
        }
        conn.close();
        
        return user;		
    }
	
    public int registrar(Usuario u) throws SQLException {
        
        Connection conn = Conexion.getConexion();

        String sql = "INSERT INTO usuarios (nombre, contrasena, pregunta, respuesta, habilitado, rol, fecha_creacion, fecha_actualizacion) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, u.getNombre());
        ps.setString(2, u.getContrasena());
        ps.setString(3, u.getPregunta());
        ps.setString(4, u.getRespuesta());
        ps.setInt(5, 1);
        ps.setString(6,"public");
        ps.setDate(7, new Date(System.currentTimeMillis()));
        ps.setDate(8, new Date(System.currentTimeMillis()));
        

        int resp = ps.executeUpdate();
        conn.close();
        
        return resp;
    }
    
    public int guardar(Usuario u) throws SQLException { // MODIFICAR BD: INSERT INTO
        
        Connection conn = Conexion.getConexion(); // Conecta con la BD
        
        // Insercion en la BD
        String sql = "INSERT INTO usuarios (nombre, contrasena, pregunta, respuesta, habilitado, rol, fecha_creacion, fecha_actualizacion) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        // Ejecuta sentencias  parametrizadas de MySQL
        PreparedStatement ps = conn.prepareStatement(sql);
        
        // Metemos en los parámetros de la sentencia los datos del los getters del obj.
        ps.setString(1, u.getNombre());
        ps.setString(2, u.getContrasena());
        ps.setString(3, u.getPregunta());
        ps.setString(4, u.getRespuesta());
        ps.setInt(5, 1);
        ps.setString(6,"public");
        ps.setDate(7, new Date(System.currentTimeMillis()));
        ps.setDate(8, new Date(System.currentTimeMillis()));

        // Al hacerse cambios en la BD ejecutamos este método y si está OK, devuelve un entero
        int resp = ps.executeUpdate();
        
        conn.close(); // Cerramos flujo
        
        return resp; 
    }
    
    public List<Usuario> listar() throws SQLException { // EXTRAER DATOS: SELECT (Consulta)

        List<Usuario> lista = new ArrayList<>(); // Creo lista para guardar varios productos de la consulta

        Connection conn = Conexion.getConexion(); 

        Statement st = conn.createStatement(); // Ejecuta sentecia SQL sin parametrizar
        ResultSet rs = st.executeQuery("SELECT * FROM usuarios");
            
        
        while(rs.next()) { // Al haber varios productos se utiliza while para crear 1 obj
            Usuario u = new Usuario(); // y guardar todos sus campos

            u.setId(rs.getInt("id"));
            u.setNombre(rs.getString("nombre"));
            u.setContrasena(rs.getString("contrasena"));
            u.setPregunta(rs.getString("pregunta"));
            u.setRespuesta(rs.getString("respuesta"));
            u.setHabilitado(rs.getInt("habilitado"));
            u.setRol(rs.getString("rol"));
            u.setFechaCreacion(rs.getDate("fecha_creacion"));
            u.setFechaActualizacion(rs.getDate("fecha_actualizacion"));

            lista.add(u); // para añadir todos los obj en una lista.
        }
        conn.close(); // Cerramos flujo

        return lista; // Devuelvo la listo con todos los productos
    }
    
    public int eliminar(int id) throws SQLException { // MODIFICAR BD: DETELE
         
        Connection conn = Conexion.getConexion();
        
        String sql = "DELETE FROM usuarios WHERE id = ?";
       
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setInt(1, id);

        int resp = ps.executeUpdate();
        conn.close();
        
        return resp;
    }
    
    public String obtenerPregunta (String nombre) throws SQLException {
        
        String pregunta = "";

        Connection conn = Conexion.getConexion();
        String sql = "SELECT * FROM usuarios WHERE nombre = ?";
        
        
        PreparedStatement ps = conn.prepareStatement(sql); // Como hay parametros meto la consulta aquí mismo

        ps.setString(1, nombre);  // 1º parametro: indica el signo de interrogacion a sust. El 2º es el valor por el cual se sust.

        ResultSet rs = ps.executeQuery(); // Conjunto que recoje el resultado.

        // Devuelve un boolean indicando si hay un proximo registro.
        if(rs.next()) {  // Al ser único el resultado se pone un if en vez de bucle
            pregunta = rs.getString("pregunta");
        }
        conn.close();
        
        return pregunta;// Devuelvo el objeto con el producto 
    }
    
     public int actualizarContrasena(String nombre, String respuesta, String nuevaContrasena) throws SQLException { // MODIFICAR BD: UPDATE
        
        Connection conn = Conexion.getConexion();// Genero sentencia para actualizar datos
        String sql = "UPDATE usuarios SET contrasena = ?, fecha_actualizacion = ? WHERE nombre = ? AND respuesta = ?";
        
        PreparedStatement ps = conn.prepareStatement(sql); // Si hay parámetros meto la consulta aquí

        ps.setString(1, nuevaContrasena);
        ps.setDate(2, new Date(System.currentTimeMillis()));
        ps.setString(3, nombre);
        ps.setString(4, respuesta);
        
        int resp = ps.executeUpdate(); // Al hacerse cambios en la BD siempre (executeUpdate), 1 si es OK
        conn.close();
        
        return resp;
    }
     
    public int deshabilitar(String nombre) throws SQLException {
         
        Connection conn = Conexion.getConexion();
        String sql = "UPDATE usuarios SET habilitado = ?, fecha_actualizacion = ? WHERE nombre = ?";
        
        PreparedStatement ps = conn.prepareStatement(sql); // Si hay parámetros meto la consulta aquí

        ps.setInt(1, 0);   
        ps.setDate(2, new Date(System.currentTimeMillis()));
        ps.setString(3, nombre  );
        int resp = ps.executeUpdate(); // Al hacerse cambios en la BD siempre (executeUpdate), 1 si es OK
        conn.close();
        
        return resp;
    }
    
    public int habilitar(int id) throws SQLException {
        
        Connection conn = Conexion.getConexion();
        String sql = "UPDATE usuarios SET habilitado = 1, fecha_actualizacion = ? WHERE id = ?";
        
        PreparedStatement ps = conn.prepareStatement(sql); // Si hay parámetros meto la consulta aquí

        ps.setDate(1, new Date(System.currentTimeMillis()));
        ps.setInt(2, id);
        
        int resp = ps.executeUpdate(); // Al hacerse cambios en la BD siempre (executeUpdate), 1 si es OK
        conn.close();
        
        return resp;
    } 
}
