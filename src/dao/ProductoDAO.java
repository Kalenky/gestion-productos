package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import domain.Conexion;
import domain.Producto;
import domain.Usuario;
import java.sql.Date;

public class ProductoDAO {

    public int guardar(Producto p) throws SQLException { // MODIFICAR BD: INSERT INTO
        
        Connection conn = Conexion.getConexion(); // Conecta con la BD
        
        // Insercion en la BD
        String sql = "INSERT INTO productos (codigo, nombre, precio, cantidad, usuario_id, fecha_creacion, fecha_actualizacion) VALUES (?, ?, ?, ?, ?, ?, ?)";
        // Ejecuta sentencias  parametrizadas de MySQL
        PreparedStatement ps = conn.prepareStatement(sql);
        
        // Metemos en los par�metros de la sentencia los datos del los getters del obj.
        ps.setString(1, p.getCodigo());
        ps.setString(2, p.getNombre());
        ps.setDouble(3, p.getPrecio());
        ps.setInt(4, p.getCantidad());
        ps.setInt(5, p.getUsuarioId());
        ps.setDate(6, new Date(System.currentTimeMillis()));
        ps.setDate(7, new Date(System.currentTimeMillis()));
        
        // Al hacerse cambios en la BD ejecutamos este m�todo y si est� OK, devuelve un entero
        int resp = ps.executeUpdate();
        
        conn.close(); // Cerramos flujo
        
        return resp; 
    }
	
    public List<Producto> listar(Usuario usuario) throws SQLException { // EXTRAER DATOS: SELECT (Consulta)

        List<Producto> lista = new ArrayList<>(); // Creo lista para guardar varios productos de la consulta

        Connection conn = Conexion.getConexion(); 

        Statement st = conn.createStatement(); // Ejecuta sentecia SQL sin parametrizar
        ResultSet rs;
        
        if (usuario.getRol().equals("admin")) { // En caso de que sea administrador
            // Conjunto Set que recoge la lista de los productos con todos sus campos
            rs = st.executeQuery("SELECT * FROM productos"); // Como no hay par�metros meto aqui la consulta
        }else
            rs = st.executeQuery("SELECT * FROM productos WHERE usuario_id = " + usuario.getId()); 
        
        while(rs.next()) { // Al haber varios productos se utiliza while para crear 1 obj
            Producto p = new Producto(); // y guardar todos sus campos

            p.setId(rs.getInt("id"));
            p.setCodigo(rs.getString("codigo"));
            p.setNombre(rs.getString("nombre"));
            p.setPrecio(rs.getDouble("precio"));
            p.setCantidad(rs.getInt("cantidad"));
            p.setUsuarioId(rs.getInt("usuario_id"));
            p.setFechaCreacion(rs.getDate("fecha_creacion"));
            p.setFechaActualizacion(rs.getDate("fecha_actualizacion"));
            lista.add(p); // para aniadir todos los obj en una lista.
        }
        conn.close(); // Cerramos flujo

        return lista; // Devuelvo la lista con todos los productos
    }

    public Producto buscarPorCodigo(String codigo, Usuario usuario) throws SQLException { // EXTRAER DATO: SELECT (Consulta)

        Producto p = null; // Creo objeto para guardar campos del producto

        Connection conn = Conexion.getConexion();
        String sql;
        
        if (usuario.getRol().equals("admin")) { // Si es admin sacamos todos los productos
            sql = "SELECT * FROM productos WHERE codigo = ?"; // Saco los datos del objeto que busco por c�digo
        }else
            sql = "SELECT * FROM productos WHERE codigo = ? AND usuario_id = ?"; // Si es usuario sacamos solo los productos de su id.
        
        
        PreparedStatement ps = conn.prepareStatement(sql); // Como hay parametros meto la consulta aqu� mismo

        ps.setString(1, codigo);  // 1� parametro: indica el signo de interrogacion a sust. El 2� es el valor por el cual se sust.
        
        if (!usuario.getRol().equals("admin")) { // otra forma de hacerlo
            ps.setInt(2, usuario.getId());
        }

        ResultSet rs = ps.executeQuery(); // Conjunto que recoje el resultado.

        // Devuelve un boolean indicando si hay un proximo registro.
        if(rs.next()) {  // Al ser �nico el resultado se pone un if en vez de bucle
            p = new Producto(); // Guardo en el objeto el resultado de la busqueda

            p.setId(rs.getInt("id"));
            p.setCodigo(rs.getString("codigo"));    
            p.setNombre(rs.getString("nombre"));
            p.setPrecio(rs.getDouble("precio"));
            p.setCantidad(rs.getInt("cantidad"));
            p.setUsuarioId(rs.getInt("usuario_id"));
            p.setFechaCreacion(rs.getDate("fecha_creacion"));
            p.setFechaActualizacion(rs.getDate("fecha_actualizacion"));
            // Como solo hay un resultado no utilizo lista.
        }
        conn.close();
        
        return p;// Devuelvo el objeto con el producto
    }
	
    public List<Producto> buscarPorNombre(String nombre, Usuario usuario) throws SQLException { // EXTRAER DATOS: SELECT (Consulta)

        List<Producto> lista = new ArrayList<>(); // Creo lista para guardar varios productos de la consulta

        Connection conn = Conexion.getConexion(); 

        Statement st = conn.createStatement(); // Saco los datos del objeto que busco por c�digo. Al no haber "?" no hay Prepared
        ResultSet rs;
        
        if (usuario.getRol().equals("admin")) { // Si es admin
            // Recojo el resultado y en la consulta pongo "like" para que me busque por la parte de texto que coincida.
            rs = st.executeQuery("SELECT * FROM productos WHERE nombre LIKE '%" + nombre + "%'"); 
        }else
            rs = st.executeQuery("SELECT * FROM productos WHERE usuario_id = "+usuario.getId() +"AND nombre LIKE '%" + nombre + "%'"); 
        

        while(rs.next()) { // Devuelve boolean y al haber varios productos se utiliza while para crear 1 obj
            Producto p = new Producto(); // y guardar todos sus campos

            p.setId(rs.getInt("id"));
            p.setCodigo(rs.getString("codigo"));
            p.setNombre(rs.getString("nombre"));
            p.setPrecio(rs.getDouble("precio"));
            p.setCantidad(rs.getInt("cantidad"));
            p.setUsuarioId(rs.getInt("usuario_id"));
            p.setFechaCreacion(rs.getDate("fecha_creacion"));
            p.setFechaActualizacion(rs.getDate("fecha_actualizacion"));

            lista.add(p); // para a�adir todos los obj en una lista.
        }
        conn.close();

        return lista; // Devuelvo la listo con todos los productos
    }
	
    public int actualizar(String codigo, String nombre, String precio, String cantidad, Usuario usuario) throws SQLException { // MODIFICAR BD: UPDATE
        
        Connection conn = Conexion.getConexion();// Genero sentencia para actualizar datos
        String sql = "UPDATE productos SET ";
        
        if (!nombre.equals("")) // Se concatena la consulta sql para permutar diferentes opciones de cambio
            sql += "nombre = '" + nombre + "', ";
        
        if (!precio.equals("")) // precio
            sql += "precio = " + precio + ", ";
        
        if (!cantidad.equals("")) // cantidad
            sql += "cantidad = " + cantidad + ", ";
        
        sql += "fecha_actualizacion = ? WHERE codigo = ?"; // resto de la consulta
        
        if (!usuario.getRol().equals("admin"))  // Si es admin
            sql += " AND usuario_id = " + usuario.getId(); // ???
        
        PreparedStatement ps = conn.prepareStatement(sql); // Si hay par�metros meto la consulta aqu�

        ps.setDate(1, new Date(System.currentTimeMillis()));
        ps.setString(2, codigo); // Metemos en los par�metros de la sentencia los datos del los getters del obj.
        
        int resp = ps.executeUpdate(); // Al hacerse cambios en la BD siempre (executeUpdate)

        conn.close(); // se cierra flujo
        
        return resp;
    }
	
    public int eliminar(int id, Usuario usuario) throws SQLException { // MODIFICAR BD: DELETE
        
        Connection conn = Conexion.getConexion();
        String sql;
        
        if (usuario.getRol().equals("admin")) { // Borra en modo admin
            sql = "DELETE FROM productos WHERE id = ?"; // Setencia de borrado
        }else
            sql = "DELETE FROM productos WHERE id = ? AND usuario_id = " + usuario.getId(); // Setencia de borrado user

        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setInt(1, id);

        int resp = ps.executeUpdate();

        conn.close();
        
        return resp;
    }
}
