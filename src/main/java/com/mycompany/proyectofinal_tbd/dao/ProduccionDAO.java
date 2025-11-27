/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinal_tbd.dao;

import com.mycompany.proyectofinal_tbd.modelo.Produccion;
import java.util.List;

/**
 *
 * @author banue
 */
public interface ProduccionDAO {
    void insertar(Produccion produccion, Runnable onSuccess, Runnable onError);
    void actualizar(Produccion produccion, Runnable onSuccess, Runnable onError);
    void eliminar(Long idProduccion, Runnable onSuccess, Runnable onError);
    Produccion buscarPorId(Long idProduccion);
    List<Produccion> listarTodas();
    List<Produccion> listarPorObra(Long idObra);
}//interface
