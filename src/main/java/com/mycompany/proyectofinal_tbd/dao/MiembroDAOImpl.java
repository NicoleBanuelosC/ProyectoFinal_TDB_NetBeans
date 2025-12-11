/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinal_tbd.dao;

import com.mycompany.proyectofinal_tbd.modelo.Miembro;
import com.mycompany.proyectofinal_tbd.conexion.ConexionBD;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author banue
 */
public class MiembroDAOImpl implements MiembroDAO{
    @Override
    public void insertar(Miembro miembro, Runnable onSuccess, Runnable onError) {
        String sql = "INSERT INTO Miembro (nombre, apellido, telefono, email, fecha_ingreso, cuota_pagada, anio_cuota, id_direccion) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexionBD.getConexion(); 
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, miembro.getNombre());
            stmt.setString(2, miembro.getApellido());
            stmt.setString(3, miembro.getTelefono());
            stmt.setString(4, miembro.getEmail());
            stmt.setDate(5, Date.valueOf(miembro.getFechaIngreso()));
            stmt.setInt(6, miembro.isCuotaPagada() ? 1 : 0);
            stmt.setInt(7, miembro.getAnioCuota());
            stmt.setObject(8, miembro.getIdDireccion());
            
            int filas = stmt.executeUpdate();
            if (filas > 0) {
                onSuccess.run();
            } else {
                onError.run();
            }//else
        } catch (SQLException e) {
            e.printStackTrace();
            onError.run();
        }//catch
    }//insertar

    @Override
    public void actualizar(Miembro miembro, Runnable onSuccess, Runnable onError) {
        String sql = "UPDATE Miembro SET nombre = ?, apellido = ?, telefono = ?, email = ?, fecha_ingreso = ?, cuota_pagada = ?, anio_cuota = ?, id_direccion = ? WHERE id_miembro = ?";
        try (Connection conn = ConexionBD.getConexion(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, miembro.getNombre());
            stmt.setString(2, miembro.getApellido());
            stmt.setString(3, miembro.getTelefono());
            stmt.setString(4, miembro.getEmail());
            stmt.setDate(5, Date.valueOf(miembro.getFechaIngreso()));
            stmt.setInt(6, miembro.isCuotaPagada() ? 1 : 0);
            stmt.setInt(7, miembro.getAnioCuota());
            stmt.setObject(8, miembro.getIdDireccion());
            stmt.setLong(9, miembro.getIdMiembro());
            
            int filas = stmt.executeUpdate();
            if (filas > 0) {
                onSuccess.run();
            } else {
                onError.run();
            }//else
        } catch (SQLException e) {
            e.printStackTrace();
            onError.run();
        }//catch
    }//actualizar

    @Override
    public void eliminar(Long idMiembro, Runnable onSuccess, Runnable onError) {
        String sql = "DELETE FROM Miembro WHERE id_miembro = ?";
        try (Connection conn = ConexionBD.getConexion(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, idMiembro);
            int filas = stmt.executeUpdate();
            if (filas > 0) {
                onSuccess.run();
            } else {
                onError.run();
            }//else
        } catch (SQLException e) {
            e.printStackTrace();
            onError.run();
        }//catch
    }//eliminar

    @Override
    public Miembro buscarPorId(Long idMiembro) {
        String sql = "SELECT * FROM Miembro WHERE id_miembro = ?";
        try (Connection conn = ConexionBD.getConexion(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, idMiembro);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Miembro m = new Miembro();
                m.setIdMiembro(rs.getLong("id_miembro"));
                m.setNombre(rs.getString("nombre"));
                m.setApellido(rs.getString("apellido"));
                m.setTelefono(rs.getString("telefono"));
                m.setEmail(rs.getString("email"));
                m.setFechaIngreso(rs.getDate("fecha_ingreso").toLocalDate());
                m.setCuotaPagada(rs.getInt("cuota_pagada") == 1);
                m.setAnioCuota(rs.getInt("anio_cuota"));
                m.setIdDireccion(rs.getObject("id_direccion") != null ? rs.getLong("id_direccion") : null);
                return m;
            }//if
        } catch (SQLException e) {
            e.printStackTrace();
        }//catch
        return null;
    }//buscarPorId

    @Override
    public List<Miembro> listarTodos() {
        List<Miembro> miembros = new ArrayList<>();
        String sql = "SELECT * FROM Miembro ORDER BY id_miembro";
        try (Connection conn = ConexionBD.getConexion(); 
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Miembro m = new Miembro();
                m.setIdMiembro(rs.getLong("id_miembro"));
                m.setNombre(rs.getString("nombre"));
                m.setApellido(rs.getString("apellido"));
                m.setTelefono(rs.getString("telefono"));
                m.setEmail(rs.getString("email"));
                m.setFechaIngreso(rs.getDate("fecha_ingreso").toLocalDate());
                m.setCuotaPagada(rs.getInt("cuota_pagada") == 1);
                m.setAnioCuota(rs.getInt("anio_cuota"));
                m.setIdDireccion(rs.getObject("id_direccion") != null ? rs.getLong("id_direccion") : null);
                miembros.add(m);
            }//while
            
        } catch (SQLException e) {
            e.printStackTrace();
        }//catch
        
        return miembros;
    }//listarTodos
}//MiembroDAOImpl