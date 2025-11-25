/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinal_tbd.conexion;

/**
 *
 * @author banue
 */
public class PruebaConexion {
    
    public static void main(String[] args) {
        
        ConexionBD.getConexion();
        
        ConexionBD.cerrar();
        
        System.out.println("--- Prueba de conexión finalizada ---");
        
    }//void main
    
}//public class