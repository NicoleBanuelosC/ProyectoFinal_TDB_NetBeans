/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinal_tbd.dao;

import com.mycompany.proyectofinal_tbd.conexion.ConexionBD;
import com.mycompany.proyectofinal_tbd.modelo.Direccion;
import java.sql.*;

/*
 * @author banue
 */

    public class DireccionDAO {
        public Long insertar(Direccion d) throws SQLException {
        String sql = "INSERT INTO Direccion (calle, numero_ext, numero_int, colonia, ciudad, estado, codigo_postal, pais) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, d.getCalle());
            ps.setString(2, d.getNumeroExt());
            ps.setString(3, d.getNumeroInt());
            ps.setString(4, d.getColonia());
            ps.setString(5, d.getCiudad());
            ps.setString(6, d.getEstado());
            ps.setString(7, d.getCodigoPostal());
            ps.setString(8, d.getPais());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getLong(1);
                else throw new SQLException("No se generó ID para Dirección");
            }//try
        }//Try
    }//public

    public Direccion buscarPorId(Long id) throws SQLException {
        String sql = "SELECT * FROM Direccion WHERE id_direccion = ?";
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Direccion d = new Direccion();
                d.setIdDireccion(rs.getLong("id_direccion"));
                d.setCalle(rs.getString("calle"));
                d.setNumeroExt(rs.getString("numero_ext"));
                d.setNumeroInt(rs.getString("numero_int"));
                d.setColonia(rs.getString("colonia"));
                d.setCiudad(rs.getString("ciudad"));
                d.setEstado(rs.getString("estado"));
                d.setCodigoPostal(rs.getString("codigo_postal"));
                d.setPais(rs.getString("pais"));
                return d;
            }//if
        }//try
        return null;
    }//public

    public void actualizar(Direccion d) throws SQLException {
        String sql = "UPDATE Direccion SET calle=?, numero_ext=?, numero_int=?, colonia=?, ciudad=?, estado=?, codigo_postal=?, pais=? WHERE id_direccion=?";
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, d.getCalle());
            ps.setString(2, d.getNumeroExt());
            ps.setString(3, d.getNumeroInt());
            ps.setString(4, d.getColonia());
            ps.setString(5, d.getCiudad());
            ps.setString(6, d.getEstado());
            ps.setString(7, d.getCodigoPostal());
            ps.setString(8, d.getPais());
            ps.setLong(9, d.getIdDireccion());
            ps.executeUpdate();
        }//try
    }//public

    public void eliminar(Long id) throws SQLException {
        String sql = "DELETE FROM Direccion WHERE id_direccion = ?";
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        }//try
    }//public
    
}//DireccionDAO
