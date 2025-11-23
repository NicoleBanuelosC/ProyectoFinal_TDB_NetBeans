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
public class Representacion {
    private Long idRepresentacion;
    private Long idProduccion;
    private LocalDate fecha;
    private String hora; 

    public Representacion() {}

    public Representacion(Long idProduccion, LocalDate fecha, String hora) {
        this.idProduccion = idProduccion;
        this.fecha = fecha;
        this.hora = hora;
    }//public Representacion
    
    public Long getIdRepresentacion() { return idRepresentacion; }
    public void setIdRepresentacion(Long idRepresentacion) { this.idRepresentacion = idRepresentacion; }

    public Long getIdProduccion() { return idProduccion; }
    public void setIdProduccion(Long idProduccion) { this.idProduccion = idProduccion; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public String getHora() { return hora; }
    public void setHora(String hora) { this.hora = hora; }
    
}//public class
