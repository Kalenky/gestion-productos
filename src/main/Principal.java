package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import controller.Controlador;
import domain.Conexion;

public class Principal {

    public static void main(String[] args) {

        //se crean las tablas en caso de no existir
        try {
            Connection conn = Conexion.getConexion();
            Statement stmt = conn.createStatement();

            String sql = loadResourceAsString("/resources/estructuradb.sql");

            String[] sentencias = sql.split(";"); // Separa las sentencias sql por (;)

            for (String sentencia : sentencias) 
                if (!sentencia.trim().isEmpty()) 
                    stmt.execute(sentencia.trim()); // Ejecuta la sentencia una por una

            // Inicializo con el objeto controlador, ya que, desde su constructor inicializo
            // la vista y las clases del modelo una vez ejecutadas todas las sentencias sql
            Controlador controlador = new Controlador();
            // inicializo para que arranque el entorno gráfico
            controlador.iniciarVista();
        }catch (SQLException e) {
            System.out.println("Problemas conectando a la base de datos: " + e.getMessage());		
        } 
    }

    public static String loadResourceAsString(String resourceName) { // Método para sacar el código del arch. sql
        StringBuilder salida = new StringBuilder();

        InputStream is = Principal.class.getResourceAsStream(resourceName);

        if (is != null) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                String linea;

                while ((linea = br.readLine()) != null) 
                    salida.append(linea);

            }catch (IOException ex) {
                System.err.printf("Problema leyendo el recurso como cadena: %S\n ", resourceName);
            } 
        }
        return salida.toString();
    }
}

        
   
