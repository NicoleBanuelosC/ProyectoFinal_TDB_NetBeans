/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinal_tbd.modelo;

/**
 *
 * @author banue
 */
public class Obra {
    private Long idObra;
    private String titulo;
    private String autor;
    private String tipo; 
    private Integer numeroActos;
    
    public Obra() {}

    public Obra(String titulo, String autor, String tipo, Integer numeroActos) {
        this.titulo = titulo;
        this.autor = autor;
        this.tipo = tipo;
        this.numeroActos = numeroActos;
    }//public Obra
        
    public Long getIdObra() { return idObra; }
    public void setIdObra(Long idObra) { this.idObra = idObra; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public Integer getNumeroActos() { return numeroActos; }
    public void setNumeroActos(Integer numeroActos) { this.numeroActos = numeroActos; }
 
}//public class
