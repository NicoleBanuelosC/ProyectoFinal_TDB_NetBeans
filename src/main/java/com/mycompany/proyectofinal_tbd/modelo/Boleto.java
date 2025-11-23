/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinal_tbd.modelo;

import java.time.LocalDateTime;

/**
 *
 * @author banue
 */
public class Boleto {
    private Long idBoleto;
    private Long idRepresentacion;
    private Long idPatrono;
    private String fila;
    private Integer numero;
    private Double precio;
    private LocalDateTime fechaCompra;
    private String tipoBoleto;
       
    public Boleto() {}

     public Boleto(Long idRepresentacion, Long idPatrono, String fila, Integer numero, Double precio) {
        this.idRepresentacion = idRepresentacion;
        this.idPatrono = idPatrono;
        this.fila = fila;
        this.numero = numero;
        this.precio = precio;
        this.tipoBoleto = "normal"; //por defecto
    }//public Boleto

    public Long getIdBoleto() { return idBoleto; }
    public void setIdBoleto(Long idBoleto) { this.idBoleto = idBoleto; }

    public Long getIdRepresentacion() { return idRepresentacion; }
    public void setIdRepresentacion(Long idRepresentacion) { this.idRepresentacion = idRepresentacion; }

    public Long getIdPatrono() { return idPatrono; }
    public void setIdPatrono(Long idPatrono) { this.idPatrono = idPatrono; }

    public String getFila() { return fila; }
    public void setFila(String fila) { this.fila = fila; }

    public Integer getNumero() { return numero; }
    public void setNumero(Integer numero) { this.numero = numero; }

    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }

    public LocalDateTime getFechaCompra() { return fechaCompra; }
    public void setFechaCompra(LocalDateTime fechaCompra) { this.fechaCompra = fechaCompra; }

    public String getTipoBoleto() { return tipoBoleto; }
    public void setTipoBoleto(String tipoBoleto) { this.tipoBoleto = tipoBoleto; }
    
}//public class
