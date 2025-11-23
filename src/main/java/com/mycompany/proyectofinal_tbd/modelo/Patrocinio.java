/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinal_tbd.modelo;

/**
 *
 * @author banue
 */
public class Patrocinio {
    private Long idPatrocinio;
    private Long idPatrocinador;
    private Long idProduccion;
    private Double monto;
    private String tipoDonacion; 
    private String descripcion;

    public Patrocinio() {}

    public Patrocinio(Long idPatrocinador, Long idProduccion, String tipoDonacion) {
        this.idPatrocinador = idPatrocinador;
        this.idProduccion = idProduccion;
        this.tipoDonacion = tipoDonacion;
    }//public Patrocinio
    
    public Long getIdPatrocinio() { return idPatrocinio; }
    public void setIdPatrocinio(Long idPatrocinio) { this.idPatrocinio = idPatrocinio; }

    public Long getIdPatrocinador() { return idPatrocinador; }
    public void setIdPatrocinador(Long idPatrocinador) { this.idPatrocinador = idPatrocinador; }

    public Long getIdProduccion() { return idProduccion; }
    public void setIdProduccion(Long idProduccion) { this.idProduccion = idProduccion; }

    public Double getMonto() { return monto; }
    public void setMonto(Double monto) { this.monto = monto; }

    public String getTipoDonacion() { return tipoDonacion; }
    public void setTipoDonacion(String tipoDonacion) { this.tipoDonacion = tipoDonacion; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    
}//public class
