/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinal_tbd.modelo;

/**
 *
 * @author banue
 */
public class Produccion {
    private Long idProduccion;
    private Long idObra;
    private String temporada; 
    private Integer anio;
    private Long idProductor; 

    public Produccion() {}

    public Produccion(Long idObra, String temporada, Integer anio, Long idProductor) {
        this.idObra = idObra;
        this.temporada = temporada;
        this.anio = anio;
        this.idProductor = idProductor;
    }//public Produccion
    
    public Long getIdProduccion() { return idProduccion; }
    public void setIdProduccion(Long idProduccion) { this.idProduccion = idProduccion; }

    public Long getIdObra() { return idObra; }
    public void setIdObra(Long idObra) { this.idObra = idObra; }

    public String getTemporada() { return temporada; }
    public void setTemporada(String temporada) { this.temporada = temporada; }

    public Integer getAnio() { return anio; }
    public void setAnio(Integer anio) { this.anio = anio; }

    public Long getIdProductor() { return idProductor; }
    public void setIdProductor(Long idProductor) { this.idProductor = idProductor; }
    
}//public class
