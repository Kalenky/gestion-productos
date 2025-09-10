package domain;

import java.time.LocalDateTime;
import java.util.Date;

public class Usuario {

    // Atributos
    private int id;
    private String nombre;
    private String contrasena;
    private String pregunta;
    private String respuesta;
    private int habilitado;
    private String rol;
    private Date fecha_creacion;
    private Date fecha_actualizacion;

    // Constructor principal
    public Usuario(int id, String nombre, String contrasena, String pregunta, String respuesta, int habilitado, String rol, Date fecha_creacion, Date fecha_actualizacion) {
        this.id = id;
        this.nombre = nombre;
        this.contrasena = contrasena;
        this.pregunta = pregunta;
        this.respuesta = respuesta;
        this.habilitado = habilitado;
        this.rol = rol; 
        this.fecha_creacion = fecha_creacion;
        this.fecha_actualizacion = fecha_actualizacion;
    }
    
    // Constructor sobrecargado 1
    public Usuario(String nombre, String contrasena, String pregunta, String respuesta, int habilitado, String rol) {
        this(0, nombre, contrasena, pregunta, respuesta, habilitado, rol, null, null);
    }
    
    // Constructor sobrecargado 2
    public Usuario() {
        this(0, "", "", "", "", 1, "", null, null);
    } 
    
    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
    
    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }
    
    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }
    
    public int getHabilitado() {
        return id;
    }

    public void setHabilitado(int id) {
        this.id = id;
    }

    
    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
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
