/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinal_tbd.modelo;

/**
 *
 * @author banue
 */
public class Oficial {
    private Long idOficial;
    private Long idMiembro;
    private String cargo; 
    private Integer anioElecto;
    
    public Oficial() {}

    public Oficial(Long idMiembro, String cargo, Integer anioElecto) {
        this.idMiembro = idMiembro;
        this.cargo = cargo;
        this.anioElecto = anioElecto;
    }//public Oficial
   
    public Long getIdOficial() { return idOficial; }
    public void setIdOficial(Long idOficial) { this.idOficial = idOficial; }

    public Long getIdMiembro() { return idMiembro; }
    public void setIdMiembro(Long idMiembro) { this.idMiembro = idMiembro; }

    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }

    public Integer getAnioElecto() { return anioElecto; }
    public void setAnioElecto(Integer anioElecto) { this.anioElecto = anioElecto; }
    
}//public class
