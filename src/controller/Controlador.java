package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;   
import java.util.InputMismatchException;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import dao.ProductoDAO;
import dao.UsuarioDAO;
import domain.Producto;
import domain.Usuario;
import java.awt.Color;
import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import view.Vista;

public class Controlador implements ActionListener {

    // Atributos
    private Vista vista;
    private UsuarioDAO usuarioDAO;
    private ProductoDAO productoDAO;
    private ArrayList<Producto> listaProductos;
    private ArrayList<Usuario> listaUsuarios;
    private Usuario usuario_logueado; 
    private String nombreUsuario;
    private int contadorContrasena;
    private int contadorRespuesta;
    private final String SONIDO_ERROR = getClass().getResource("/sonidos/error.wav").getPath();
    private final String SONIDO_CORRECTO = getClass().getResource("/sonidos/correcto.wav").getPath();

    public Controlador() {
        vista = new Vista();
        usuarioDAO = new UsuarioDAO(); // Inicializo para no replicar en cada metodo y que se inicialice en el main
        productoDAO = new ProductoDAO(); // Inicializo para no replicar en cada metodo y que se inicialice en el main
        listaProductos = new ArrayList<Producto>(); 
        listaUsuarios = new ArrayList<Usuario>();
        contadorContrasena = 0; // ???
        contadorRespuesta = 0; // ???

        vista.btnLogin.addActionListener(this); // Inicializo porque son los botones a escuchar de la 1ªventana (login)
        vista.btnIrARegistro.addActionListener(this); // Inicializo porque son los botones a escuchar de la 1ªventana (login)
    }

    public void iniciarVista() { // Visibilizamos vista
        vista.setVisible(true);
    }

    public void limpiarCampos() { // Limpiamos campos
        vista.txtUsuarioNombre.setText("");
        vista.txtNombre.setText("");
        vista.txtPrecio.setText("");
        vista.txtCantidad.setText("");

        vista.rbCodigo.setSelected(true); // Activa por defecto selectCodigo
        vista.chkActualizarProducto.setSelected(false);

        vista.txtBuscar.setText("");
        vista.txtEliminar.setText("");
    }
    public void generarSonido(String archivo) {
        try {
                AudioInputStream audio = AudioSystem.getAudioInputStream(new File(archivo));
                Clip clip = AudioSystem.getClip();
                clip.open(audio);
                clip.start();
        }catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error ejecutando sonido: " + e.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) { // Al tener los lisener inicializados al realizar una accion con los botones se recogen como eventos
        // VENTANA-LOGIN
        if (e.getSource() == vista.btnLogin) { // verifica que el boton de logueo sea presionado

            if (!vista.txtUsuario.getText().equals("") && !vista.txtPassword.getPassword().equals("")) { //valida los campos de nombre y contrasena 
                String nombre = vista.txtUsuario.getText();
                String contrasena = vista.txtPassword.getText();

                try {
                    Usuario usuario = usuarioDAO.logueo(nombre, contrasena);  //intenta buscar esos datos en la tabla de usuarios ???

                    if (usuario != null) { //si encuentra el usuario procede a cambair de vista
                        contadorContrasena = 0;
                        contadorRespuesta = 0;
                        
                        if (usuario.getHabilitado() == 1) { // Comprobamos si está habil
                            usuario_logueado = usuario; // Para sacar la info si el usuario está logueado ?????

                            vista.limpiarVista();		// limpia la vista de logueo
                            vista.vistaProductos();		// carga la vista de de productos---
                            
                            // Validar si se muestra o no el boton de CRUD de usuarios
                            if (usuario_logueado.getRol().equals("admin")){
                                vista.btnCrudUsuarios.setVisible(true);
                                vista.contenedor.setBackground(Color.WHITE);
                                vista.rbCodigo.setBackground(Color.WHITE);
                                vista.rbNombre.setBackground(Color.WHITE);
                                vista.chkActualizarProducto.setBackground(Color.WHITE);
                                
                                Date fechaActual = new Date(System.currentTimeMillis());
                                SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
                                vista.lblFecha.setText("Fecha: " + formato.format(fechaActual));

                                LocalTime horaActual = LocalTime.now();
                                DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm");
                                vista.lblHora.setText("Hora: " + horaActual.format(formatoHora));
                            }else {
                                vista.btnCrudUsuarios.setVisible(false);
                            }

                            vista.btnAgregar.addActionListener(this); //se agrega el escuchador de los botones de la vista de productos
                            vista.btnCrudUsuarios.addActionListener(this);
                            vista.btnMostrarTodos.addActionListener(this);
                            vista.btnBuscar.addActionListener(this);
                            vista.btnEliminar.addActionListener(this);
                            vista.btnLogout.addActionListener(this);
                        }else{ 
                            generarSonido(SONIDO_ERROR);
                            JOptionPane.showMessageDialog(null, "El usuario se encuentra bloqueado. Contactar con el administrador !!");
                        }
                        
                    }else { // Si usuario no pone bien nombre y contrasenia
                        usuario_logueado = null;
                        contadorContrasena++;
                        
                        if (contadorContrasena < 3) { // Recuento de intentos.
                            generarSonido(SONIDO_ERROR);
                            JOptionPane.showMessageDialog(null, "Los datos son incorrectos. Te quedan: "+ (3 - contadorContrasena));
                        }else {
                            contadorContrasena = 0; 
                            
                            // Buscamos pregunta secreta
                            String pregunta = usuarioDAO.obtenerPregunta(nombre);
                            
                            if (!pregunta.equals("")) {
                                
                                nombreUsuario = nombre; // guardo el nombre usuario
                                
                                vista.limpiarVista();
                                vista.recuperarContrasena();
                                vista.lblRecuperarPregunta.setText(pregunta); // ???
                            
                                vista.btnRecuperarCambiar.addActionListener(this);
                            }else {
                                generarSonido(SONIDO_ERROR);
                                JOptionPane.showMessageDialog(null, "Nombre del usuario incorrecto.");
                            }      
                        }  
                    }
                }catch (SQLException ex) {
                    usuario_logueado = null;
                    generarSonido(SONIDO_ERROR);
                    JOptionPane.showMessageDialog(null, "Hubo un problema: " + ex.getMessage());
                }
            }else {
                usuario_logueado = null;
                generarSonido(SONIDO_ERROR);
                JOptionPane.showMessageDialog(null, "Los campos son obligatorios");
            }

        // IR A VENTANA-REGISTRO 
        }else if (e.getSource() == vista.btnIrARegistro) {
            vista.limpiarVista();
            vista.registro();

            vista.btnRegistro.addActionListener(this);
            vista.btnIrALogin.addActionListener(this);

        // VENTANA-REGISTRAR NUEVO USUARIO
        }else if (e.getSource() == vista.btnRegistro) {

            if (!vista.txtUsuarioRegistro.getText().equals("") && !vista.txtPasswordRegistro.getText().equals("")
                    && !vista.txtUsuarioRespuesta.getText().equals("")){
                try {
                    String nombre = vista.txtUsuarioRegistro.getText();
                    String contrasena = vista.txtPasswordRegistro.getText();
                    String pregunta = (String) vista.cmbPreguntas.getSelectedItem();
                    String respuesta = vista.txtUsuarioRespuesta.getText();
                    
                    String regex = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
                    
                    if(contrasena.matches(regex)){ // Matcheamos las condiciones de la contrasenia
                        Usuario usuario = new Usuario(nombre, contrasena, pregunta, respuesta, 1, "public");

                        int resp = usuarioDAO.registrar(usuario); // Ejecutamos metodo registrar

                        if (resp == 1) { // Verificacion
                            JOptionPane.showMessageDialog(null, "USUARIO AGREGADO!");
                            vista.txtUsuarioRegistro.setText("");
                            vista.txtPasswordRegistro.setText("");
                            vista.txtUsuarioRespuesta.setText("");
                        }else {
                            generarSonido(SONIDO_ERROR);
                            JOptionPane.showMessageDialog(null, "No se pudo agregar el usuario.");
                        }
                    }else{
                        generarSonido(SONIDO_ERROR);
                        JOptionPane.showMessageDialog(null, "Contrasenia Débil !!. (Debe tener 1 Mayusc, 1 num, 1 caracter especial. Num min de caracteres 8)");
                    }
                    
                }catch (SQLException ex) {
                    generarSonido(SONIDO_ERROR);
                    JOptionPane.showMessageDialog(null, "Hubo un problema: " + ex.getMessage());
                }
            }else { 
                generarSonido(SONIDO_ERROR);
                JOptionPane.showMessageDialog(null, "Hay campos obligatorios que estan vacios.");
            }

        // IR A LOGIN
        }else if (e.getSource() == vista.btnIrALogin) {
            vista.limpiarVista();
            vista.login();

            vista.btnLogin.addActionListener(this);
            vista.btnIrARegistro.addActionListener(this);
            
        // CRUD-DEL-PRODUCTO_________________________________________________________________________________________________:
        // BOTON-AGREGAR
        }else if (e.getSource() == vista.btnAgregar) {
            
            if (!vista.chkActualizarProducto.isSelected()) { // Si el check de actualizacion no está agregado
                
                if (!vista.txtCodigo.getText().equals("") && !vista.txtNombre.getText().equals("") // Si no hay campos vacios
                        && !vista.txtPrecio.getText().equals("") && !vista.txtCantidad.getText().equals("")) {
                    try {
                        String codigo = vista.txtCodigo.getText(); // Rellenamos datos del producto
                        String nombre = vista.txtNombre.getText();
                        Double precio = Double.parseDouble(vista.txtPrecio.getText());
                        int cantidad = Integer.parseInt(vista.txtCantidad.getText());

                        if (precio > 0 && cantidad > 0) {
                            Producto producto = new Producto(codigo, nombre, precio, cantidad, usuario_logueado.getId());

                            int resp = productoDAO.guardar(producto); // Ejecutamos metodo guardar

                            if (resp == 1) { // Verificacion
                                generarSonido(SONIDO_CORRECTO);
                                vista.lblMensaje.setText("PRODUCTO AGREGADO!");
                                limpiarCampos();
                            }else {
                                generarSonido(SONIDO_ERROR);
                                JOptionPane.showMessageDialog(null, "No se pudo agregar el producto.");
                            }
                        }else {
                            generarSonido(SONIDO_ERROR);
                            JOptionPane.showMessageDialog(null, "Precio y cantidad deben ser mayores a cero.");
                        }
                        
                    }catch (SQLException ex) {
                        generarSonido(SONIDO_ERROR);
                        JOptionPane.showMessageDialog(null, "Hubo un problema: " + ex.getMessage());
                        
                    }catch (InputMismatchException ex) {
                        generarSonido(SONIDO_ERROR);
                        JOptionPane.showMessageDialog(null, "Hubo un problema con los campos numericos: " + ex.getMessage());
                    }
                }else {
                    generarSonido(SONIDO_ERROR);
                    JOptionPane.showMessageDialog(null, "Hay campos obligatorios que estan vacios.");
                }
                
            // BOTÓN-ACTUALIZAR
            }else {
                if (!vista.txtCodigo.getText().equals("")) {
                    try {
                        String codigo = vista.txtCodigo.getText();
                        
                        String nombre = vista.txtNombre.getText();
                        String precio = vista.txtPrecio.getText();
                        String cantidad = vista.txtCantidad.getText();
                        
                        int resp = productoDAO.actualizar(codigo, nombre, precio, cantidad, usuario_logueado); // Ejecutamos metodo Actualizar

                        if (resp == 1) { // Verificacion
                            generarSonido(SONIDO_CORRECTO);
                            vista.lblMensaje.setText("PRODUCTO ACTUALIZADO!");
                            limpiarCampos();
                        }else {
                            generarSonido(SONIDO_ERROR);
                            JOptionPane.showMessageDialog(null, "No hay producto con ese codigo.");
                        }
                        
                    }catch (SQLException ex) {
                        generarSonido(SONIDO_ERROR);
                        JOptionPane.showMessageDialog(null, "Hubo un problema: " + ex.getMessage());
                        
                    }catch (InputMismatchException ex) {
                        generarSonido(SONIDO_ERROR);
                        JOptionPane.showMessageDialog(null, "Hubo un problema con el campo numerico: " + ex.getMessage());
                    }
                }else {
                    generarSonido(SONIDO_ERROR);
                    JOptionPane.showMessageDialog(null, "hay campos obligatorios que estan vacios.");
                }
            }
            
        // BOTON - MOSTRAR TODOS
        }else if (e.getSource() == vista.btnMostrarTodos) {
            try {
                listaProductos = (ArrayList<Producto>) productoDAO.listar(usuario_logueado); // Ejecutamos listar productos

                DefaultTableModel modelo = (DefaultTableModel) vista.tabla.getModel();
                modelo.setRowCount(0);  //limpiar la tabla
                SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy"); // Formato fecha

                for (Producto p : listaProductos) {
                    Object[] fila = {p.getId(), p.getCodigo(), p.getNombre(), p.getPrecio(), p.getCantidad(),formato.format(p.getFechaCreacion()), formato.format(p.getFechaActualizacion())};
                    modelo.addRow(fila);
                }
                
            }catch (SQLException ex) {
                generarSonido(SONIDO_ERROR);
                JOptionPane.showMessageDialog(null, "Hubo un problema: " + ex.getMessage());
            }
            
        // BOTÓN - BUSCAR
        }else if (e.getSource() == vista.btnBuscar) {
            
            if (!vista.txtBuscar.getText().equals("")) {
                String datoBusqueda = vista.txtBuscar.getText();

                if (vista.rbCodigo.isSelected()) { // BUSCAR POR CODIGO
                    try {
                        Producto p = productoDAO.buscarPorCodigo(datoBusqueda, usuario_logueado); // Ejecuto metodo buscar y lo guardo en obj

                        if (p != null) { 
                            DefaultTableModel modelo = (DefaultTableModel) vista.tabla.getModel(); // pinto tabla
                            modelo.setRowCount(0); // limpio filas

                            Object[] fila = {p.getId(), p.getCodigo(), p.getNombre(), p.getPrecio(), p.getCantidad()};
                            modelo.addRow(fila); // pinto la fila con los datos recogidos del objeto

                            limpiarCampos();// limpiamos campos del formulario
                        }else {
                            generarSonido(SONIDO_ERROR);
                            JOptionPane.showMessageDialog(null, "No existen productos con ese codigo.");
                        }
                    }catch (SQLException ex) {
                        generarSonido(SONIDO_ERROR);
                        JOptionPane.showMessageDialog(null, "Hubo un problema: " + ex.getMessage());
                    }
                // BUSCAR POR NOMBRE
                }else {
                    try { // Ejecuto metodo y guardo la lista en ArrayList
                        ArrayList<Producto> lista = (ArrayList<Producto>) productoDAO.buscarPorNombre(datoBusqueda, usuario_logueado);

                        if (lista.size() > 0) { // Si hay lista ejecutamos la logica anterior
                            DefaultTableModel modelo = (DefaultTableModel) vista.tabla.getModel();
                            modelo.setRowCount(0);

                            for (Producto p : lista) { // Al ser lista añadimos las filas con bucle
                                Object[] fila = {p.getId(), p.getCodigo(), p.getNombre(), p.getPrecio(), p.getCantidad()};
                                modelo.addRow(fila);
                            }
                            limpiarCampos();
                            
                        }else {
                            generarSonido(SONIDO_ERROR);
                            JOptionPane.showMessageDialog(null, "No hay productos que coincidan con ese nombre");
                        }
                    }catch (SQLException ex) {
                        generarSonido(SONIDO_ERROR);
                        JOptionPane.showMessageDialog(null, "Hubo un problema: " + ex.getMessage());
                    }
                }
            }else {
                generarSonido(SONIDO_ERROR);
                JOptionPane.showMessageDialog(null, "El campo de busqueda esta vacio.");
            }
            
        // BOTON-ELIMINAR
        }else if (e.getSource() == vista.btnEliminar) { 
            if (!vista.txtEliminar.getText().equals("")) { 
                try {
                    int id = Integer.parseInt(vista.txtEliminar.getText()); // Parseamos cadena en int

                    if (id > 0) {
                        int resp = productoDAO.eliminar(id, usuario_logueado); // Ejecutamos metodo eliminar

                        if (resp == 1) { // Verficiacion 
                            vista.lblMensaje.setText("PRODUCTO ELIMINADO!");
                            limpiarCampos(); // Limpiamos
                        }else {
                            generarSonido(SONIDO_ERROR);
                            JOptionPane.showMessageDialog(null, "No hay producto con ese id");
                        }
                    }else {
                        generarSonido(SONIDO_ERROR);
                        JOptionPane.showMessageDialog(null, "El campo ID debe ser mayor a cero.");
                    }
                }catch (SQLException ex) {
                    generarSonido(SONIDO_ERROR);
                    JOptionPane.showMessageDialog(null, "Hubo un problema: " + ex.getMessage());
                }catch (InputMismatchException ex) {
                    generarSonido(SONIDO_ERROR);
                    JOptionPane.showMessageDialog(null, "Hubo un problema con el campo numerico: " + ex.getMessage());
                }
            }else {
                generarSonido(SONIDO_ERROR);
                JOptionPane.showMessageDialog(null, "El campo de eliminar esta vacio.");
            }
            
        // IR A CRUD DE USUARIOS
        }else if (e.getSource() == vista.btnCrudUsuarios) {
            
            vista.limpiarVista();
            vista.vistaUsuarios();   

            vista.btnUsuarioAgregar.addActionListener(this); //se agrega el escuchador de los botones de la vista de productos
            vista.btnCrudProductos.addActionListener(this);
            vista.btnUsuarioMostrarTodos.addActionListener(this);
            vista.btnUsuarioHabilitar.addActionListener(this);
            vista.btnEliminar.addActionListener(this);
            
            vista.contenedor.setBackground(Color.WHITE); // Fondo
			
            Date fechaActual = new Date(System.currentTimeMillis());
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            vista.lblFecha.setText("Fecha: " + formato.format(fechaActual));

            LocalTime horaActual = LocalTime.now();
            DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm");
            vista.lblHora.setText("Hora: " + horaActual.format(formatoHora));
                
        // IR A CRUD DE PRODUCTOS
        }else if (e.getSource() == vista.btnCrudProductos){
            
            vista.limpiarVista();
            vista.vistaProductos(); 
            
            vista.btnAgregar.addActionListener(this); 
            vista.btnCrudUsuarios.addActionListener(this);
            vista.btnMostrarTodos.addActionListener(this);
            vista.btnBuscar.addActionListener(this);
            vista.btnEliminar.addActionListener(this);
            vista.btnLogout.addActionListener(this);
            
            vista.contenedor.setBackground(Color.WHITE);
            vista.rbCodigo.setBackground(Color.WHITE);
            vista.rbNombre.setBackground(Color.WHITE);
            vista.chkActualizarProducto.setBackground(Color.WHITE);

            Date fechaActual = new Date(System.currentTimeMillis());
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            vista.lblFecha.setText("Fecha: " + formato.format(fechaActual));

            LocalTime horaActual = LocalTime.now();
            DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm");
            vista.lblHora.setText("Hora: " + horaActual.format(formatoHora));

        // CRUD DE USUARIOS_________________________________________________________________________________________________________: 
        // AGREGAR (administrador)
        }else if (e.getSource() == vista.btnUsuarioAgregar){
             
            if (!vista.txtUsuarioNombre.getText().equals("") && !vista.txtUsuarioContrasena.getText().equals("")
                    && !vista.txtRespuestaUsuarios.getText().equals("")) {// Si no hay campos vacios
                try {
                    String nombre = vista.txtUsuarioNombre.getText(); // Rellenamos datos del producto
                    String contrasena = vista.txtUsuarioContrasena.getText();
                    String pregunta = (String) vista.cmbPreguntas.getSelectedItem();
                    String respuesta = vista.txtRespuestaUsuarios.getText();
                    
                    String regex = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
                    
                    if (contrasena.matches(regex)) {
                        Usuario usuario = new Usuario(nombre, contrasena, pregunta, respuesta, 1, "public");

                        int resp = usuarioDAO.guardar(usuario); // Ejecutamos metodo guardar

                        if (resp == 1) { // Verificacion
                            generarSonido(SONIDO_CORRECTO);
                            vista.lblUsuarioMensaje.setText("USUARIO AGREGADO!");
                            vista.txtUsuarioNombre.setText("");
                            vista.txtUsuarioContrasena.setText("");
                            vista.txtRespuestaUsuarios.setText("");
                        }else {
                            generarSonido(SONIDO_ERROR);
                            JOptionPane.showMessageDialog(null, "No se pudo agregar el usuario.");
                        }
                    }else {
                        generarSonido(SONIDO_ERROR);
                        JOptionPane.showMessageDialog(null, "Contrasenia debil !!. (Debe tener 1 Mayusc, 1 num, 1 caracter especial. Num min de caracteres 8)");
                    }
                }catch (SQLException ex) {
                    generarSonido(SONIDO_ERROR);
                    JOptionPane.showMessageDialog(null, "Hubo un problema: " + ex.getMessage());
                }
            }else {
                generarSonido(SONIDO_ERROR);
                JOptionPane.showMessageDialog(null, "Hay campos obligatorios que estan vacios.");
            }

        // MOSTRAR TODOS
        }else if (e.getSource() == vista.btnUsuarioMostrarTodos){
            
            try {
                listaUsuarios = (ArrayList<Usuario>) usuarioDAO.listar(); // Ejecutamos listar productos

                DefaultTableModel modelo = (DefaultTableModel) vista.tablaUsuarios.getModel();
                modelo.setRowCount(0);  // limpiar la tabla
                SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy"); // Formato fecha

                for (Usuario u : listaUsuarios) {
                    Object[] fila = {u.getId(), u.getNombre(), u.getHabilitado(), u.getRol(), formato.format(u.getFechaCreacion()), formato.format(u.getFechaActualizacion())};
                    modelo.addRow(fila);
                }
                
            }catch (SQLException ex) {
                generarSonido(SONIDO_ERROR);
                JOptionPane.showMessageDialog(null, "Hubo un problema: " + ex.getMessage());
            }
            
        // HABILITAR USUARIO
        }else if (e.getSource() == vista.btnUsuarioHabilitar){
            
            if (!vista.txtUsuarioHabilitar.getText().equals("")) { 
                try {
                    int id = Integer.parseInt(vista.txtUsuarioHabilitar.getText()); // Parseamos cadena en int

                    if (id > 0) {
                        int resp = usuarioDAO.habilitar(id); // Ejecutamos metodo eliminar

                        if (resp == 1) { // Verficiacion 
                            generarSonido(SONIDO_CORRECTO);
                            vista.lblUsuarioMensaje.setText("USUARIO HABILITADO !");
                            vista.txtUsuarioHabilitar.setText("");
                        }else {
                            generarSonido(SONIDO_ERROR);
                            JOptionPane.showMessageDialog(null, "No hay usuario con ese id");
                        }
                    }else {
                        generarSonido(SONIDO_ERROR);
                        JOptionPane.showMessageDialog(null, "El campo ID debe ser mayor a cero.");
                    }
                }catch (SQLException ex) {
                    generarSonido(SONIDO_ERROR);
                    JOptionPane.showMessageDialog(null, "Hubo un problema: " + ex.getMessage());

                }catch (InputMismatchException ex) {
                    generarSonido(SONIDO_ERROR);
                    JOptionPane.showMessageDialog(null, "Hubo un problema con el campo numerico: " + ex.getMessage());
                }
            }else {
                generarSonido(SONIDO_ERROR);
                JOptionPane.showMessageDialog(null, "El campo de habilitar está vacio.");
            }
        
        // ELIMINAR
        }else if (e.getSource() == vista.btnUsuarioEliminar){
            
            if (!vista.txtUsuarioEliminar.getText().equals("")) { 
                try {
                    int id = Integer.parseInt(vista.txtUsuarioEliminar.getText()); // Parseamos cadena en int

                    if (id > 0) {
                        int resp = usuarioDAO.eliminar(id); // Ejecutamos metodo eliminar

                        if (resp == 1) { // Verficiacion 
                            generarSonido(SONIDO_CORRECTO);
                            vista.lblUsuarioMensaje.setText("USUARIO ELIMINADO!");
                            vista.txtUsuarioEliminar.setText("");
                        }else {
                            generarSonido(SONIDO_ERROR);
                            JOptionPane.showMessageDialog(null, "No hay usuario con ese id");
                        }
                    }else {
                        generarSonido(SONIDO_ERROR);
                        JOptionPane.showMessageDialog(null, "El campo ID debe ser mayor a cero.");
                    }
                }catch (SQLException ex) {
                    generarSonido(SONIDO_ERROR);
                    JOptionPane.showMessageDialog(null, "Hubo un problema: " + ex.getMessage());
                    
                }catch (InputMismatchException ex) {
                    generarSonido(SONIDO_ERROR);
                    JOptionPane.showMessageDialog(null, "Hubo un problema con el campo numerico: " + ex.getMessage());
                }
            }else {
                generarSonido(SONIDO_ERROR);
                JOptionPane.showMessageDialog(null, "El campo de eliminar esta vacio.");
            }
        // RECUPERAR CONTRASENIA
        }else if (e.getSource() == vista.btnRecuperarCambiar){
            
            if (!vista.txtRecuperarRespuesta.getText().equals("") && !vista.txtRecuperarContrasena.getText().equals("")) {
                    
                String respuesta = vista.txtRecuperarRespuesta.getText();
                String nuevaContrasena = vista.txtRecuperarContrasena.getText();
                String nombre = nombreUsuario;
                
                String regex = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
                    
                if (nuevaContrasena.matches(regex)){
                    try {
                        int resp = usuarioDAO.actualizarContrasena(nombre, respuesta, nuevaContrasena);

                        if (resp == 1) {
                            contadorRespuesta = 0;
                            nombreUsuario = ""; // Limpiamos

                            JOptionPane.showMessageDialog(null, "La contrasenia a sido cambiada correctamente.");

                            vista.limpiarVista();
                            vista.login();

                            vista.btnLogin.addActionListener(this);
                            vista.btnIrARegistro.addActionListener(this);  
                        }else {
                            contadorRespuesta++;

                            if (contadorRespuesta <3) {
                                generarSonido(SONIDO_ERROR);
                                JOptionPane.showMessageDialog(null, "La respuesta no es correcta. Te quedan: "+ (3 - contadorRespuesta));

                            }else {
                                contadorRespuesta =0;
                                nombreUsuario = ""; // Limpiamos

                                int resp2 = usuarioDAO.deshabilitar(nombre); // DAO usuario para bloquear

                                if (resp2 == 1) {
                                    generarSonido(SONIDO_ERROR);
                                    JOptionPane.showMessageDialog(null, "El usuario ha sido bloqueado. Contactar con el Administrador !!");

                                    vista.limpiarVista();
                                    vista.login();

                                    vista.btnLogin.addActionListener(this);
                                    vista.btnIrARegistro.addActionListener(this); 
                                }
                            }     
                        }
                    }catch (SQLException ex) {
                        generarSonido(SONIDO_ERROR);
                        JOptionPane.showMessageDialog(null, "Hubo un problema: " + ex.getMessage());
                    }
                }else {
                    generarSonido(SONIDO_ERROR);
                    JOptionPane.showMessageDialog(null, "Contrasenia debil !!. (Debe tener 1 Mayusc, 1 num, 1 caracter especial. Num min de caracteres 8.)");
                }
            }else{
                generarSonido(SONIDO_ERROR);
                JOptionPane.showMessageDialog(null, "Los campos son obligatorios rellenarlos.");
            }
                  
        // BOTON-VOLVER A LOGIN
        }else if (e.getSource() == vista.btnLogout) {
            usuario_logueado = null;
            
            vista.limpiarVista();
            vista.login();// Ejecutamos de nuevo la ventana login

            vista.btnLogin.addActionListener(this);
            vista.btnIrARegistro.addActionListener(this);
        }
    }
}
