/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinal_tbd.modelo;

/**
 *
 * @author banue
 */
public class Asiento {
    private String fila; 
    private Integer numero; 

    public Asiento() {}

    public Asiento(String fila, Integer numero) {
        this.fila = fila;
        this.numero = numero;
    }//public Asiento

    public String getFila() { return fila; }
    public void setFila(String fila) { this.fila = fila; }

    public Integer getNumero() { return numero; }
    public void setNumero(Integer numero) { this.numero = numero; }   

}//public class
