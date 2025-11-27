/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinal_tbd.dao;

import com.mycompany.proyectofinal_tbd.conexion.ConexionBD;
import com.mycompany.proyectofinal_tbd.modelo.Produccion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author banue
 */

public class ProduccionDAOImpl implements ProduccionDAO {
    private static final String INSERT_PRODUCCION = 
        "INSERT INTO Produccion (id_obra, temporada, anio, id_productor) VALUES (?, ?, ?, ?)";

    private static final String UPDATE_PRODUCCION = 
        "UPDATE Produccion SET id_obra = ?, temporada = ?, anio = ?, id_productor = ? WHERE id_produccion = ?";

    private static final String DELETE_PRODUCCION = 
        "DELETE FROM Produccion WHERE id_produccion = ?";

    private static final String SELECT_POR_ID = 
        "SELECT id_produccion, id_obra, temporada, anio, id_productor FROM Produccion WHERE id_produccion = ?";

    private static final String SELECT_TODAS = 
        "SELECT id_produccion, id_obra, temporada, anio, id_productor FROM Produccion ORDER BY anio DESC, temporada";

    private static final String SELECT_POR_OBRA = 
        "SELECT id_produccion, id_obra, temporada, anio, id_productor FROM Produccion WHERE id_obra = ?";

    @Override
    public void insertar(Produccion produccion, Runnable onSuccess, Runnable onError) {
        new Thread(() -> {
            Connection conn = null;
            PreparedStatement stmt = null;
            try {
                conn = ConexionBD.getConexion();
                if (conn == null) {
                    throw new SQLException("No se pudo establecer conexión con la base de datos ");
                }//if
                
                conn.setAutoCommit(false);

                if (!existeObra(conn, produccion.getIdObra())) {
                    throw new SQLException("La obra con ID " + produccion.getIdObra() + " no existe ");
                }//if
                
                if (!existeMiembro(conn, produccion.getIdProductor())) {
                    throw new SQLException("El productor (miembro) con ID " + produccion.getIdProductor() + " no existe ");
                }//if

                stmt = conn.prepareStatement(INSERT_PRODUCCION);
                stmt.setLong(1, produccion.getIdObra());
                stmt.setString(2, produccion.getTemporada().toLowerCase());
                stmt.setInt(3, produccion.getAnio());
                stmt.setLong(4, produccion.getIdProductor());
                stmt.executeUpdate();

                conn.commit();
                onSuccess.run();
            } catch (SQLException e) {
                try {
                    if (conn != null) conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }//catch
                
                e.printStackTrace();
                onError.run();
                
            } finally {
                try {
                    if (stmt != null) stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }//try
            }//finally
        }).start();
    }//insertar

    @Override
    public void actualizar(Produccion produccion, Runnable onSuccess, Runnable onError) {
        new Thread(() -> {
            Connection conn = null;
            PreparedStatement stmt = null;
            try {
                conn = ConexionBD.getConexion();
                if (conn == null) {
                    throw new SQLException("No se pudo establecer conexión con la base de datos ");
                }//if
                
                conn.setAutoCommit(false);

                if (!existeObra(conn, produccion.getIdObra())) {
                    throw new SQLException("La obra con ID " + produccion.getIdObra() + " no existe ");
                }//if
                
                if (!existeMiembro(conn, produccion.getIdProductor())) {
                    throw new SQLException("El productor con ID " + produccion.getIdProductor() + " no existe ");
                }//if

                stmt = conn.prepareStatement(UPDATE_PRODUCCION);
                stmt.setLong(1, produccion.getIdObra());
                stmt.setString(2, produccion.getTemporada().toLowerCase());
                stmt.setInt(3, produccion.getAnio());
                stmt.setLong(4, produccion.getIdProductor());
                stmt.setLong(5, produccion.getIdProduccion());

                int filas = stmt.executeUpdate();
                if (filas == 0) {
                    throw new SQLException("No se encontró la producción con ID: " + produccion.getIdProduccion());
                }//if

                conn.commit();
                onSuccess.run();
            } catch (SQLException e) {
                try {
                    if (conn != null) conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }//catch
                
                e.printStackTrace();
                onError.run();
                
            } finally {
                try {
                    if (stmt != null) stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }//Catch
            }//finally
            
        }).start();
    }//actualizar

    @Override
    public void eliminar(Long idProduccion, Runnable onSuccess, Runnable onError) {
        new Thread(() -> {
            Connection conn = null;
            PreparedStatement stmt = null;
            try {
                conn = ConexionBD.getConexion();
                if (conn == null) {
                    throw new SQLException("No se pudo establecer conexión con la base de datos ");
                }//if
                conn.setAutoCommit(false);

                stmt = conn.prepareStatement(DELETE_PRODUCCION);
                stmt.setLong(1, idProduccion);
                int filas = stmt.executeUpdate();
                if (filas == 0) {
                    throw new SQLException("No se encontró la producción con ID: " + idProduccion);
                }//if

                conn.commit();
                onSuccess.run();
                
            } catch (SQLException e) {
                try {
                    if (conn != null) conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }//Catch
                
                e.printStackTrace();
                onError.run();
            } finally {
                try {
                    if (stmt != null) stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }//try
            }//finally
        }).start();
    }//eliminar

    @Override
    public Produccion buscarPorId(Long idProduccion) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = ConexionBD.getConexion();
            if (conn == null) return null;

            stmt = conn.prepareStatement(SELECT_POR_ID);
            stmt.setLong(1, idProduccion);
            rs = stmt.executeQuery();
            if (rs.next()) {
                Produccion p = new Produccion();
                p.setIdProduccion(rs.getLong("id_produccion"));
                p.setIdObra(rs.getLong("id_obra"));
                p.setTemporada(rs.getString("temporada"));
                p.setAnio(rs.getInt("anio"));
                p.setIdProductor(rs.getLong("id_productor"));
                return p;
            }//if
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }//catch
        }//finally
        
        return null;
    }//bucasrId

    @Override
    public List<Produccion> listarTodas() {
        List<Produccion> producciones = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = ConexionBD.getConexion();
            if (conn == null) return producciones;

            stmt = conn.prepareStatement(SELECT_TODAS);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Produccion p = new Produccion();
                p.setIdProduccion(rs.getLong("id_produccion"));
                p.setIdObra(rs.getLong("id_obra"));
                p.setTemporada(rs.getString("temporada"));
                p.setAnio(rs.getInt("anio"));
                p.setIdProductor(rs.getLong("id_productor"));
                producciones.add(p);
            }//while
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }//try
        }//finally
        
        return producciones;
    }//listarTodas

    @Override
    public List<Produccion> listarPorObra(Long idObra) {
        List<Produccion> producciones = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = ConexionBD.getConexion();
            if (conn == null) return producciones;

            stmt = conn.prepareStatement(SELECT_POR_OBRA);
            stmt.setLong(1, idObra);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Produccion p = new Produccion();
                p.setIdProduccion(rs.getLong("id_produccion"));
                p.setIdObra(rs.getLong("id_obra"));
                p.setTemporada(rs.getString("temporada"));
                p.setAnio(rs.getInt("anio"));
                p.setIdProductor(rs.getLong("id_productor"));
                producciones.add(p);
            }//while
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }//Catch
        }//finally
        return producciones;
        
    }//listarPorobra

    private boolean existeObra(Connection conn, Long idObra) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement("SELECT 1 FROM Obra WHERE id_obra = ?")) {
            stmt.setLong(1, idObra);
            return stmt.executeQuery().next();
        }//try
    }//existeObra

    private boolean existeMiembro(Connection conn, Long idMiembro) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement("SELECT 1 FROM Miembro WHERE id_miembro = ?")) {
            stmt.setLong(1, idMiembro);
            return stmt.executeQuery().next();
        }//try
        
    }//existeMiembro
    
}//public class
