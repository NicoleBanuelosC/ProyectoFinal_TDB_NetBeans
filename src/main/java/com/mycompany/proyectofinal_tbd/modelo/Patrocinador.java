/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinal_tbd.modelo;

/**
 *
 * @author banue
 */
public class Patrocinador {
    private Long idPatrocinador;
    private String nombre;
    private String tipo; 
    private String telefono;
    private String email;
    private String contactoPrincipal;
    private Long idDireccion;

    public Patrocinador() {}

    public Patrocinador(String nombre, String tipo) {
        this.nombre = nombre;
        this.tipo = tipo;
    }//public Patrocinador
    
    public Long getIdPatrocinador() { return idPatrocinador; }
    public void setIdPatrocinador(Long idPatrocinador) { this.idPatrocinador = idPatrocinador; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getContactoPrincipal() { return contactoPrincipal; }
    public void setContactoPrincipal(String contactoPrincipal) { this.contactoPrincipal = contactoPrincipal; }

    public Long getIdDireccion() { return idDireccion; }
    public void setIdDireccion(Long idDireccion) { this.idDireccion = idDireccion; }    

}//public class
