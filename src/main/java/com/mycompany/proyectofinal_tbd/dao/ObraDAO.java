/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinal_tbd.dao;

import com.mycompany.proyectofinal_tbd.modelo.Obra;
import java.util.List;

/**
 *
 * @author banue
 */

//El Runnable onSuccess/onError es para notificar a la interfaz desde hilos
public interface ObraDAO {
    void insertar(Obra obra, Runnable onSuccess, Runnable onError);
    void actualizar(Obra obra, Runnable onSuccess, Runnable onError);
    void eliminar(Long idObra, Runnable onSuccess, Runnable onError);
    boolean existeTitulo(String titulo);
    Obra buscarPorId(Long idObra);
    List<Obra> listarTodas();
}//public interface