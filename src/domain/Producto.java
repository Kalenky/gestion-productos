package domain;

import java.time.LocalDateTime;
import java.util.Date;

public class Producto { // Modelo producto
    
    // Atributos
    private int id;
    private String codigo;
    private String nombre;
    private double precio;
    private int cantidad;
    private int usuario_id;
    private Date fecha_creacion;
    private Date fecha_actualizacion;

    // Constructor principal, será el que se base para ir rellenando los obj en la BD
    public Producto(int id, String codigo, String nombre, double precio, int cantidad, int usuario_id, Date fecha_creacion, Date fecha_actualizacion) {
        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio; 
        this.cantidad = cantidad;
        this.usuario_id = usuario_id;
        this.fecha_creacion = fecha_creacion;
        this.fecha_actualizacion = fecha_actualizacion;
    }
    
    // Constructor sobrecargado 1, se utiliza para rellenar en el formulario de la ventana
    public Producto(String codigo, String nombre, double precio, int cantidad, int usuario_id) {
        this(0, codigo, nombre, precio, cantidad, usuario_id, null, null);
    }
    
    // Constructor sobrecargado 2, se utiliza para crear obj producto por defecto sin data
    public Producto() {
        this(0, "", "", 0.0, 0, 0, null, null);
    }
    
    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    
    public int getUsuarioId() {
        return usuario_id;
    }

    public void setUsuarioId(int usuario_id) {
        this.usuario_id = usuario_id;
    }
    
    public Date getFechaCreacion() {  
        return fecha_creacion;
    }

    public void setFechaCreacion(Date fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }
    
    public Date getFechaActualizacion() {  
        return fecha_actualizacion;
    }

    public void setFechaActualizacion(Date fecha_actualizacion) {
        this.fecha_actualizacion = fecha_actualizacion;
    }

}
