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
public class TransaccionFinanciera {
    private Long idTransaccion;
    private LocalDate fecha;
    private String tipo; 
    private Double monto;
    private String categoria;
    private String descripcion;
    private Long idProduccion;
    private Long idPatrono;
    private Long idPatrocinador;
   
    public TransaccionFinanciera() {}

    public TransaccionFinanciera(String tipo, Double monto, String categoria, LocalDate fecha) {
        this.tipo = tipo;
        this.monto = monto;
        this.categoria = categoria;
        this.fecha = fecha;
    }//public Transaccion
    
    public Long getIdTransaccion() { return idTransaccion; }
    public void setIdTransaccion(Long idTransaccion) { this.idTransaccion = idTransaccion; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public Double getMonto() { return monto; }
    public void setMonto(Double monto) { this.monto = monto; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Long getIdProduccion() { return idProduccion; }
    public void setIdProduccion(Long idProduccion) { this.idProduccion = idProduccion; }

    public Long getIdPatrono() { return idPatrono; }
    public void setIdPatrono(Long idPatrono) { this.idPatrono = idPatrono; }

    public Long getIdPatrocinador() { return idPatrocinador; }
    public void setIdPatrocinador(Long idPatrocinador) { this.idPatrocinador = idPatrocinador; } 
    
}//public class
