/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinal_tbd.modelo;

/**
 *
 * @author banue
 */
public class Participacion {
    private Long idParticipacion;
    private Long idMiembro;
    private Long idProduccion;
    private String rol; 
    private String personaje;
    private String creditosPrograma;

    public Participacion() {}

    public Participacion(Long idMiembro, Long idProduccion, String rol) {
        this.idMiembro = idMiembro;
        this.idProduccion = idProduccion;
        this.rol = rol;
    }//public Participacion
        
    public Long getIdParticipacion() { return idParticipacion; }
    public void setIdParticipacion(Long idParticipacion) { this.idParticipacion = idParticipacion; }

    public Long getIdMiembro() { return idMiembro; }
    public void setIdMiembro(Long idMiembro) { this.idMiembro = idMiembro; }

    public Long getIdProduccion() { return idProduccion; }
    public void setIdProduccion(Long idProduccion) { this.idProduccion = idProduccion; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    public String getPersonaje() { return personaje; }
    public void setPersonaje(String personaje) { this.personaje = personaje; }

    public String getCreditosPrograma() { return creditosPrograma; }
    public void setCreditosPrograma(String creditosPrograma) { this.creditosPrograma = creditosPrograma; }

}//public class
