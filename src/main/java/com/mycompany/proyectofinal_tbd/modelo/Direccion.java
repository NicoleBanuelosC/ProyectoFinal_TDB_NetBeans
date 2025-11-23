/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinal_tbd.modelo;

/**
 *
 * @author banue
 */
public class Direccion {
    private Long idDireccion;
    private String calle;
    private String numeroExt;
    private String numeroInt;
    private String colonia;
    private String ciudad;
    private String estado;
    private String codigoPostal;
    private String pais;

    public Direccion(){ }

    public Direccion(String calle, String numeroExt, String colonia, String estado, String codigoPostal, String pais) {
        this.calle = calle;
        this.numeroExt = numeroExt;
        this.colonia = colonia;
        this.estado = estado;
        this.codigoPostal = codigoPostal;
        this.pais = pais;
        this.ciudad = "Pleasantville"; // Valor por defecto
    }//public Direccion

    public Long getIdDireccion() { return idDireccion; }
    public void setIdDireccion(Long idDireccion) { this.idDireccion = idDireccion; }

    public String getCalle() { return calle; }
    public void setCalle(String calle) { this.calle = calle; }

    public String getNumeroExt() { return numeroExt; }
    public void setNumeroExt(String numeroExt) { this.numeroExt = numeroExt; }

    public String getNumeroInt() { return numeroInt; }
    public void setNumeroInt(String numeroInt) { this.numeroInt = numeroInt; }

    public String getColonia() { return colonia; }
    public void setColonia(String colonia) { this.colonia = colonia; }

    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getCodigoPostal() { return codigoPostal; }
    public void setCodigoPostal(String codigoPostal) { this.codigoPostal = codigoPostal; }

    public String getPais() { return pais; }
    public void setPais(String pais) { this.pais = pais; }
    
}//public class
