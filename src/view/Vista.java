package view;

import java.awt.Color;
import java.awt.Font;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class Vista extends JFrame {

    // Atributos
    public JPanel contenedor;

    // Login
    public JTextField txtUsuario;
    public JPasswordField txtPassword;
    public JButton btnLogin;
    public JButton btnIrARegistro;

    // Registro
    public JTextField txtUsuarioRegistro;
    public JPasswordField txtPasswordRegistro;
    public JComboBox cmbPreguntas;
    public JTextField txtUsuarioRespuesta;
    public JButton btnRegistro;
    public JButton btnIrALogin;

    // Productos
    public JLabel lblFecha;
    public JLabel lblHora;
    
    public JTextField txtCodigo;
    public JTextField txtNombre;
    public JTextField txtPrecio;
    public JTextField txtCantidad;

    public JButton btnAgregar;
    public JButton btnCrudUsuarios; 
    public JButton btnMostrarTodos;

    public JTable tabla;
    public JScrollPane scrollPane;

    public JRadioButton rbCodigo;
    public JRadioButton rbNombre;
    public ButtonGroup grupo;
    public JCheckBox chkActualizarProducto;

    public JTextField txtBuscar;
    public JButton btnBuscar;
    public JTextField txtEliminar;
    public JButton btnEliminar;

    public JButton btnLogout;
    public JLabel lblMensaje;
    
    // Usuarios
    public JTextField txtUsuarioNombre;
    public JPasswordField txtUsuarioContrasena;
    public JComboBox cmbUsuariosPreguntas;
    public JTextField txtRespuestaUsuarios;

    public JButton btnUsuarioAgregar;
    public JButton btnCrudProductos;
    public JButton btnUsuarioMostrarTodos;

    public JTable tablaUsuarios;
    public JScrollPane scrollPaneUsuarios;
    
    public JTextField txtUsuarioHabilitar;
    public JButton btnUsuarioHabilitar;
    public JTextField txtUsuarioEliminar;
    public JButton btnUsuarioEliminar;  

    public JLabel lblUsuarioMensaje;

    // Recuperar contrasena
    public JLabel lblRecuperarPregunta;
    public JTextField txtRecuperarRespuesta;
    public JPasswordField txtRecuperarContrasena;
    public JButton btnRecuperarCambiar;
    
    private final String ICONO = getClass().getResource("/image/icono.png").getPath();
	
    public Vista() { // CONSTRUCTOR 
        ImageIcon icono = new ImageIcon(ICONO);
        getClass().getResource("/sonidos/correcto.wav").getPath();
        setIconImage(icono.getImage());
                
        setTitle("Sistema de Productos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 650);  //ancho, alto
        setLocationRelativeTo(null);
        setLayout(null);

        login();
    }
	
    public void login() { // VENTANA-LOGIN________________________________________________________________
        
        contenedor = new JPanel(); // Usamos contenedor cuando queremos que cambie la vista de la ventana 
        contenedor.setBorder(new EmptyBorder(5,5,5,5));
        setContentPane(contenedor);
        contenedor.setLayout(null);
        
        // LABELS
        JLabel lblTitulo = new JLabel("LOGIN"); // Label-Titulo
        lblTitulo.setBounds(420, 100, 100, 25);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setForeground(Color.DARK_GRAY);
        contenedor.add(lblTitulo);

        JLabel lblUsuario = new JLabel("Nombre:"); // Label-Nombre user
        lblUsuario.setBounds(310, 210, 100, 25);  
        contenedor.add(lblUsuario);

        JLabel lblContrasena = new JLabel("Contrasena:"); // Label-Contrasenia
        lblContrasena.setBounds(310, 240, 100, 25);
        contenedor.add(lblContrasena);
        
        // CAMPOS
        txtUsuario = new JTextField(); // Campo-Usuario
        txtUsuario.setBounds(420, 210, 200, 25);
        contenedor.add(txtUsuario);

        txtPassword = new JPasswordField(); // Campo-Password
        txtPassword.setBounds(420, 240, 200, 25);
        contenedor.add(txtPassword);
        
        // BOTONES
        btnLogin = new JButton("Login"); // Boton-Login
        btnLogin.setBounds(310, 275, 310, 30);
        contenedor.add(btnLogin);

        btnIrARegistro = new JButton("Crear usuario"); // Boton-Usuario
        btnIrARegistro.setBounds(310, 315, 310, 30);
        contenedor.add(btnIrARegistro);
    }
	
    public void registro() { // VENTANA-REGISTRO________________________________
        
        contenedor = new JPanel(); // Contenedor
        contenedor.setBorder(new EmptyBorder(5,5,5,5));
        setContentPane(contenedor);
        contenedor.setLayout(null);
        
        // LABELS
        JLabel lblTitulo = new JLabel("REGISTRO"); // Label-Registro
        lblTitulo.setBounds(420, 100, 120, 25);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setForeground(Color.DARK_GRAY);
        contenedor.add(lblTitulo);

        JLabel lblUsuario = new JLabel("Nombre:"); // Label-Nombre user
        lblUsuario.setBounds(310, 210, 100, 25);  
        contenedor.add(lblUsuario);

        JLabel lblContrasena = new JLabel("Contrasena:"); // Label-Contrasenia
        lblContrasena.setBounds(310, 240, 100, 25);
        contenedor.add(lblContrasena);
        
        JLabel lblPregunta = new JLabel("Pregunta:"); // Lable-Pregunta
        lblPregunta.setBounds(310, 270, 100, 25);
        contenedor.add(lblPregunta);
		
        JLabel lblRespuesta = new JLabel("Respuesta:"); // Label-Respuesta
        lblRespuesta.setBounds(310, 300, 100, 25);
        contenedor.add(lblRespuesta);
        
        // CAMPOS
        txtUsuarioRegistro = new JTextField(); // Campo-usuario
        txtUsuarioRegistro.setBounds(420, 210, 200, 25);
        contenedor.add(txtUsuarioRegistro);

        txtPasswordRegistro = new JPasswordField(); // Campo-Contrasenia
        txtPasswordRegistro.setBounds(420, 240, 200, 25);
        contenedor.add(txtPasswordRegistro);
        
        // COMBO-BOX
        String[] opciones = {"�Cual es tu color favorito?", "�Cual es tu ciudad favorita?", "�Cual es tu animal favorito?"};
        cmbPreguntas = new JComboBox<>(opciones);
        cmbPreguntas.setBounds(420, 270, 200, 25);
        contenedor.add(cmbPreguntas);

        txtUsuarioRespuesta = new JTextField();
        txtUsuarioRespuesta.setBounds(420, 300, 200, 25);
        contenedor.add(txtUsuarioRespuesta);
		
        // BOTONES
        btnRegistro = new JButton("Registrar"); // Boton-Registrar
        btnRegistro.setBounds(310, 335, 310, 30);
        contenedor.add(btnRegistro);

        btnIrALogin = new JButton("Ir a Login"); // Boton-Login
        btnIrALogin.setBounds(310, 375, 310, 30);
        contenedor.add(btnIrALogin);
    }
	
    public void vistaProductos() { // VENTANA-PRODUCTOS_________________________
        
        contenedor = new JPanel(); // Contenedor
        contenedor.setBorder(new EmptyBorder(5,5,5,5));
        setContentPane(contenedor);
        contenedor.setLayout(null);

        // LABELS
        JLabel lblCodigo = new JLabel("Codigo:"); // Label-Codigo
        lblCodigo.setBounds(10, 10, 100, 25);  //posX, posY, ancho, alto
        contenedor.add(lblCodigo);

        JLabel lblNombre = new JLabel("Nombre:"); // Label-Nombre
        lblNombre.setBounds(10, 40, 100, 25);
        contenedor.add(lblNombre);

        JLabel lblPrecio = new JLabel("Precio:"); // Label-Precio
        lblPrecio.setBounds(10, 70, 100, 25);
        contenedor.add(lblPrecio);

        JLabel lblCantidad = new JLabel("Cantidad:"); // Label-Cantidad
        lblCantidad.setBounds(10, 100, 100, 25);
        contenedor.add(lblCantidad);

        // CAMPOS
        txtCodigo = new JTextField(); // Campo-Codigo
        txtCodigo.setBounds(120, 10, 200, 25);
        contenedor.add(txtCodigo);

        txtNombre = new JTextField(); // Campo-Nombre
        txtNombre.setBounds(120, 40, 200, 25);
        contenedor.add(txtNombre);

        txtPrecio = new JTextField(); // Campo-Precio
        txtPrecio.setBounds(120, 70, 200, 25);
        contenedor.add(txtPrecio);

        txtCantidad = new JTextField(); // Campo-Cantidad
        txtCantidad.setBounds(120, 100, 200, 25);
        contenedor.add(txtCantidad);
        
        //FECHA SISTEMA
        lblFecha = new JLabel("Fecha:");
        lblFecha.setBounds(730, 10, 140, 25); 
        lblFecha.setFont(new Font("Arial", Font.BOLD, 14));
        contenedor.add(lblFecha);

        lblHora = new JLabel("Hora:");
        lblHora.setBounds(730, 30, 140, 25); 
        lblHora.setFont(new Font("Arial", Font.BOLD, 14));
        contenedor.add(lblHora);

        // BOTONES AGREGAR Y MOSTRAR
        btnAgregar = new JButton("Agregar"); // Boton-Agregar
        btnAgregar.setBounds(10, 140, 310, 30);
        contenedor.add(btnAgregar);
        
        btnCrudUsuarios = new JButton("CRUD USUARIOS"); // Boton-Usuarios
        btnCrudUsuarios.setBounds(330, 100, 310, 30);
        contenedor.add(btnCrudUsuarios);

        btnMostrarTodos = new JButton("Mostrar Todos"); // Boton-Mostrar
        btnMostrarTodos.setBounds(330, 140, 310, 30);
        contenedor.add(btnMostrarTodos);

        // TABLA
        String[] columnas = {"Id", "Codigo", "Nombre", "Precio", "Cantidad", "Fecha creacion", "Fecha actualizacion"};
        tabla = new JTable(new DefaultTableModel(columnas, 0));
        scrollPane = new JScrollPane(tabla);
        scrollPane.setBounds(10, 180, 860, 300);
        contenedor.add(scrollPane);

        // OPCIONES BUSQUEDA Y ELIMINACION
        rbCodigo = new JRadioButton("Por codigo"); // RadioButton-Codigo
        rbCodigo.setBounds(10, 490, 100, 25);
        rbCodigo.setSelected(true);
        contenedor.add(rbCodigo);

        rbNombre = new JRadioButton("Por nombre"); // RadioButton-Nombre
        rbNombre.setBounds(120, 490, 100, 25);
        contenedor.add(rbNombre);

        grupo = new ButtonGroup(); // Agruparlos para que no esten los dos seleccionados
        grupo.add(rbCodigo);
        grupo.add(rbNombre);
        
        // CHECK-BOX
        chkActualizarProducto = new JCheckBox("Actualizar Producto");
        chkActualizarProducto.setBounds(250, 490, 160, 25);
        contenedor.add(chkActualizarProducto);
        
        JLabel lblBuscar = new JLabel("Buscar producto: "); // Label-Buscar prod.
        lblBuscar.setBounds(10, 520, 120, 25);
        contenedor.add(lblBuscar);

        txtBuscar = new JTextField(); // Campo-Buscar
        txtBuscar.setBounds(130, 520, 200, 25);
        contenedor.add(txtBuscar);

        btnBuscar = new JButton("Buscar"); // Boton-Buscar
        btnBuscar.setBounds(340, 520, 120, 25);
        contenedor.add(btnBuscar);

        JLabel lblEliminar = new JLabel("Eliminar ID: "); // Label-Eliminar
        lblEliminar.setBounds(10, 555, 120, 25);
        contenedor.add(lblEliminar);

        txtEliminar = new JTextField(); // Campo-Eliminar
        txtEliminar.setBounds(130, 555, 200, 25);
        contenedor.add(txtEliminar);

        btnEliminar = new JButton("Eliminar"); // Bot�n-Eliminar
        btnEliminar.setBounds(340, 555, 120, 25);
        contenedor.add(btnEliminar);

        btnLogout = new JButton("Logout"); // Bot�n-Eliminar
        btnLogout.setBounds(470, 555, 120, 25);
        contenedor.add(btnLogout);

        lblMensaje = new JLabel(""); // Label- de mensajes de verificacion ??
        lblMensaje.setBounds(540, 520, 500, 25);
        lblMensaje.setForeground(Color.GREEN);
        lblMensaje.setFont(new Font("Arial", Font.BOLD, 18));
        contenedor.add(lblMensaje);
    }
    
    
    public void vistaUsuarios() { // VENTANA-USUARIOS___________________________
        contenedor = new JPanel(); // Contenedor
        contenedor.setBorder(new EmptyBorder(5,5,5,5));
        setContentPane(contenedor);
        contenedor.setLayout(null);

        // LABELS
        JLabel lblUsuarioNombre = new JLabel("Nombre:"); // Label-Codigo
        lblUsuarioNombre.setBounds(10, 10, 100, 25);  //posX, posY, ancho, alto
        contenedor.add(lblUsuarioNombre);

        JLabel lblUsuarioContrasena = new JLabel("Contrasena"); // Label-Nombre
        lblUsuarioContrasena.setBounds(10, 40, 100, 25);
        contenedor.add(lblUsuarioContrasena);
        
        JLabel lblPregunta = new JLabel("Pregunta:"); //Label-Pregunta
        lblPregunta.setBounds(10, 70, 100, 25);
        contenedor.add(lblPregunta);
		
        JLabel lblRespuesta = new JLabel("Respuesta:"); // Lable-Respuesta
        lblRespuesta.setBounds(10, 100, 100, 25);
        contenedor.add(lblRespuesta);
        
        //FECHA SISTEMA
        lblFecha = new JLabel("Fecha:");
        lblFecha.setBounds(730, 10, 140, 25); 
        lblFecha.setFont(new Font("Arial", Font.BOLD, 14));
        contenedor.add(lblFecha);

        lblHora = new JLabel("Hora:");
        lblHora.setBounds(730, 30, 140, 25); 
        lblHora.setFont(new Font("Arial", Font.BOLD, 14));
        contenedor.add(lblHora);

        // CAMPOS
        txtUsuarioNombre = new JTextField(); // Campo-Codigo
        txtUsuarioNombre.setBounds(120, 10, 200, 25);
        contenedor.add(txtUsuarioNombre);

        txtUsuarioContrasena = new JPasswordField();
        txtUsuarioContrasena.setBounds(120, 40, 200, 25);
        contenedor.add(txtUsuarioContrasena);

        String[] opciones = {"�Cual es tu color favorito?", "�Cual es tu ciudad favorita?", "�Cual es tu animal favorito?"};
        cmbUsuariosPreguntas = new JComboBox<>(opciones);
        cmbUsuariosPreguntas.setBounds(120, 70, 200, 25);
        contenedor.add(cmbUsuariosPreguntas);

        txtRespuestaUsuarios = new JTextField();
        txtRespuestaUsuarios.setBounds(120, 100, 200, 25);
        contenedor.add(txtRespuestaUsuarios);

        // BOTONES AGREGAR Y MOSTRAR
        btnUsuarioAgregar = new JButton("Agregar");
        btnUsuarioAgregar.setBounds(10, 140, 310, 30);
        contenedor.add(btnUsuarioAgregar);

        btnCrudProductos = new JButton("CRUD PRODUCTOS");
        btnCrudProductos.setBounds(330, 100, 310, 30);
        contenedor.add(btnCrudProductos);

        btnUsuarioMostrarTodos = new JButton("Mostrar Todos");
        btnUsuarioMostrarTodos.setBounds(330, 140, 310, 30);
        contenedor.add(btnUsuarioMostrarTodos);

        // TABLA
        String[] columnas = {"Id", "Nombre", "Habilitado", "Rol", "Fecha creacion", "Fecha actualizacion"};
        tablaUsuarios = new JTable(new DefaultTableModel(columnas, 0));
        scrollPaneUsuarios = new JScrollPane(tablaUsuarios);
        scrollPaneUsuarios.setBounds(10, 180, 860, 300);
        contenedor.add(scrollPaneUsuarios);

        // OPCIONES DE ELIMINACION
        JLabel lblUsuarioHabilitar = new JLabel("Habilitar ID: ");
        lblUsuarioHabilitar.setBounds(10, 525, 120, 25);
        contenedor.add(lblUsuarioHabilitar);
        
        JLabel lblUsuarioEliminar = new JLabel("Eliminar ID: ");
        lblUsuarioEliminar.setBounds(10, 555, 120, 25);
        contenedor.add(lblUsuarioEliminar);

        txtUsuarioHabilitar = new JTextField();
        txtUsuarioHabilitar.setBounds(130, 525, 200, 25);
        contenedor.add(txtUsuarioHabilitar);
        
        txtUsuarioEliminar = new JTextField();
        txtUsuarioEliminar.setBounds(130, 555, 200, 25);
        contenedor.add(txtUsuarioEliminar);
        
        btnUsuarioHabilitar = new JButton("Habilitar");
        btnUsuarioHabilitar.setBounds(340, 525, 120, 25);
        contenedor.add(btnUsuarioHabilitar);

        btnUsuarioEliminar = new JButton("Eliminar");
        btnUsuarioEliminar.setBounds(340, 555, 120, 25);
        contenedor.add(btnUsuarioEliminar);

        lblUsuarioMensaje = new JLabel("");
        lblUsuarioMensaje.setBounds(540, 520, 500, 25);
        lblUsuarioMensaje.setForeground(Color.GREEN);
        lblUsuarioMensaje.setFont(new Font("Arial", Font.BOLD, 18));
        contenedor.add(lblUsuarioMensaje);
    }
    
    public void recuperarContrasena() {
        contenedor = new JPanel();
        contenedor.setBorder(new EmptyBorder(5,5,5,5));
        setContentPane(contenedor);
        contenedor.setLayout(null);

        JLabel lblTitulo = new JLabel("RECUPERAR CONTRASENA");
        lblTitulo.setBounds(310, 180, 300, 25);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        contenedor.add(lblTitulo);

        JLabel lblPregunta = new JLabel("Pregunta:");
        lblPregunta.setBounds(310, 210, 100, 25);  
        contenedor.add(lblPregunta);

        JLabel lblRespuesta = new JLabel("Respuesta:");
        lblRespuesta.setBounds(310, 240, 100, 25);
        contenedor.add(lblRespuesta);

        JLabel lblNuevaContrasena = new JLabel("Contrasena (N):");
        lblNuevaContrasena.setBounds(310, 270, 100, 25);
        contenedor.add(lblNuevaContrasena);

        lblRecuperarPregunta = new JLabel("");
        lblRecuperarPregunta.setBounds(420, 210, 200, 25);  
        contenedor.add(lblRecuperarPregunta);

        txtRecuperarRespuesta = new JTextField();
        txtRecuperarRespuesta.setBounds(420, 240, 200, 25);
        contenedor.add(txtRecuperarRespuesta);

        txtRecuperarContrasena = new JPasswordField();
        txtRecuperarContrasena.setBounds(420, 270, 200, 25);
        contenedor.add(txtRecuperarContrasena);

        btnRecuperarCambiar = new JButton("Cambiar");
        btnRecuperarCambiar.setBounds(310, 335, 310, 30);
        contenedor.add(btnRecuperarCambiar);
    }
    
    
    public void limpiarVista() {
        contenedor.removeAll();   //remueve los elementos del contenedor
        contenedor.updateUI();   // Actualiza info de interfaz de usuario
    }
}
