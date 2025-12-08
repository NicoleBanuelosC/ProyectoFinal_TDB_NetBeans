/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinal_tbd.controlador;

import com.mycompany.proyectofinal_tbd.dao.ObraDAO;
import com.mycompany.proyectofinal_tbd.dao.ObraDAOImpl;
import com.mycompany.proyectofinal_tbd.dao.ProduccionDAO;
import com.mycompany.proyectofinal_tbd.dao.ProduccionDAOImpl;
import com.mycompany.proyectofinal_tbd.modelo.Obra;
import com.mycompany.proyectofinal_tbd.modelo.Produccion;
/**
 *
 * @author banue
 */

//Patron de diseño FACADE (estructural)
//este proporciona una interfaz unificada para acceder a multiples DAOs
//simplifica el uso de la capa de datos desde las vistas

public class GestorTeatro {
    //DAOs
    private ObraDAO obraDAO;
    private ProduccionDAO produccionDAO;
    
    //constructor donde se inicializan los subsistemas
    public GestorTeatro() {
        this.obraDAO = new ObraDAOImpl();
        this.produccionDAO = new ProduccionDAOImpl();
    }//gestorTeatro

   // METODOS O B R A 
    public void guardarObra(Obra obra, Runnable onSuccess, Runnable onError) {
        obraDAO.insertar(obra, onSuccess, onError);
    }//guardarObra

    public void actualizarObra(Obra obra, Runnable onSuccess, Runnable onError) {
        obraDAO.actualizar(obra, onSuccess, onError);
    }//actualizarObra

    public void eliminarObra(Long idObra, Runnable onSuccess, Runnable onError) {
        obraDAO.eliminar(idObra, onSuccess, onError);
    }//eliminarObra

    public Obra buscarObraPorId(Long idObra) {
        return obraDAO.buscarPorId(idObra);
    }//buscraPorId
    
    //METODOS P R O D U C C I O N 
    public void registrarProduccion(Produccion produccion, Runnable onSuccess, Runnable onError) {
        produccionDAO.insertar(produccion, onSuccess, onError);
    }//registrarProduccion

    public void venderBoleto(
        Long idRepresentacion, Long idPatrono,
        String fila, int numero,
        double precio, String tipoBoleto,
        Runnable onSuccess, Runnable onError
    ) {
        ((ProduccionDAOImpl) produccionDAO).venderBoleto(
            idRepresentacion, idPatrono, fila, numero, precio, tipoBoleto, onSuccess, onError
        );
    }//venderBoleto

    public double obtenerIngresosPorProduccion(Long idProduccion) {
        return ((ProduccionDAOImpl) produccionDAO).obtenerIngresosPorProduccion(idProduccion);
    }//obtenerIngresos

    public java.util.List<java.util.Map<String, Object>> obtenerProduccionesDetalladas() {
        return ((ProduccionDAOImpl) produccionDAO).listarProduccionesDetalladas();
    }//obtenerProduccionesDetalladas
    
}//GestorTeatro
