/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinal_tbd.dao;

import com.mycompany.proyectofinal_tbd.modelo.Miembro;
import java.util.List;

/**
 *
 * @author banue
 */
public interface MiembroDAO {
    void insertar(Miembro miembro, Runnable onSuccess, Runnable onError);
    void actualizar(Miembro miembro, Runnable onSuccess, Runnable onError);
    void eliminar(Long idMiembro, Runnable onSuccess, Runnable onError);
    Miembro buscarPorId(Long idMiembro);
    List<Miembro> listarTodos();
}//MiembroDAO
