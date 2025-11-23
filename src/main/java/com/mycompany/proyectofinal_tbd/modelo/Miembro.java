/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinal_tbd.modelo;

import java.time.LocalDate;

/**
 *
 * @author banue
 */
public class Miembro {
    private Long idMiembro;
    private String nombre;
    private String apellido;
    private String telefono;
    private String email;
    private LocalDate fechaIngreso;
    private boolean cuotaPagada;
    private Integer anioCuota;
    private Long idDireccion;
    
    public Miembro() {}

    public Miembro(String nombre, String apellido, LocalDate fechaIngreso, Integer anioCuota) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaIngreso = fechaIngreso;
        this.anioCuota = anioCuota;
        this.cuotaPagada = false; // por defecto no pagada
    }//public Miembro
    
    public Long getIdMiembro() { return idMiembro; }
    public void setIdMiembro(Long idMiembro) { this.idMiembro = idMiembro; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public LocalDate getFechaIngreso() { return fechaIngreso; }
    public void setFechaIngreso(LocalDate fechaIngreso) { this.fechaIngreso = fechaIngreso; }

    public boolean isCuotaPagada() { return cuotaPagada; }
    public void setCuotaPagada(boolean cuotaPagada) { this.cuotaPagada = cuotaPagada; }

    public Integer getAnioCuota() { return anioCuota; }
    public void setAnioCuota(Integer anioCuota) { this.anioCuota = anioCuota; }

    public Long getIdDireccion() { return idDireccion; }
    public void setIdDireccion(Long idDireccion) { this.idDireccion = idDireccion; }
    
}//public class
