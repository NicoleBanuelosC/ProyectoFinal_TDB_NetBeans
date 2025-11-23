/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinal_tbd.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author banue
 */

public class PruebaConexion {
    public static void main(String[] args) {
        try {
            // Cargar manualmente el driver (por seguridad)
            Class.forName("oracle.jdbc.OracleDriver");

            Connection conn = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521/XEPDB1",
                "teatro_pleasantville",
                "teatro123"
            );
            System.out.println("✅ ¡CONEXIÓN EXITOSA A ORACLE!");
            conn.close();
        } catch (Exception e) {
            System.err.println("❌ ERROR AL CONECTAR: " + e.getMessage());
            e.printStackTrace();
        }
    }
}