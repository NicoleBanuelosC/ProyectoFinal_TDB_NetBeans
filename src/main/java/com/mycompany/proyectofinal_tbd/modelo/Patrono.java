/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinal_tbd.modelo;

/**
 *
 * @author banue
 */
public class Patrono {
    private Long idPatrono;
    private String nombre;
    private String apellido;
    private String telefono;
    private String email;
    private boolean esSuscriptor;
    private Long idDireccion;

    public Patrono() {}

    public Patrono(String nombre, String apellido, String email) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
    }//public Patrono
      
    public Long getIdPatrono() { return idPatrono; }
    public void setIdPatrono(Long idPatrono) { this.idPatrono = idPatrono; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public boolean isEsSuscriptor() { return esSuscriptor; }
    public void setEsSuscriptor(boolean esSuscriptor) { this.esSuscriptor = esSuscriptor; }

    public Long getIdDireccion() { return idDireccion; }
    public void setIdDireccion(Long idDireccion) { this.idDireccion = idDireccion; }
    
}//public class
