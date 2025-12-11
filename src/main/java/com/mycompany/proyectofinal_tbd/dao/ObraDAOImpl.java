/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.proyectofinal_tbd.dao;

import com.mycompany.proyectofinal_tbd.conexion.ConexionBD;
import com.mycompany.proyectofinal_tbd.modelo.Obra;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 
 * @author banue
 */

public class ObraDAOImpl implements ObraDAO {

    private static final String INSERT_OBRA = 
        "INSERT INTO Obra (titulo, autor, tipo, numero_actos) VALUES (?, ?, ?, ?)";

    private static final String UPDATE_OBRA = 
        "UPDATE Obra SET titulo = ?, autor = ?, tipo = ?, numero_actos = ? WHERE id_obra = ?";

    private static final String DELETE_OBRA = 
        "DELETE FROM Obra WHERE id_obra = ?";

    private static final String SELECT_POR_ID = 
        "SELECT id_obra, titulo, autor, tipo, numero_actos FROM Obra WHERE id_obra = ?";

    private static final String SELECT_TODAS = 
        "SELECT id_obra, titulo, autor, tipo, numero_actos FROM Obra ORDER BY titulo";

    @Override
    public void insertar(Obra obra, Runnable onSuccess, Runnable onError) {
        new Thread(() -> {
            Connection conn = null;
            PreparedStatement stmt = null;
            try {
                conn = ConexionBD.getConexion();
                if (conn == null) {
                    throw new SQLException("No se pudo establecer conexión con la base de datos");
                }//if
                conn.setAutoCommit(false);

                stmt = conn.prepareStatement(INSERT_OBRA);
                stmt.setString(1, obra.getTitulo().trim());
                stmt.setString(2, obra.getAutor().trim());
                stmt.setString(3, obra.getTipo().toLowerCase());
                stmt.setInt(4, obra.getNumeroActos());
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
                    // nO cerramos la conexión aquí porque usamos Singleton
                } catch (SQLException e) {
                    e.printStackTrace();
                    
                }//catch
                
            }//finally
        }).start();
        
    }//insertar

    @Override
    public void actualizar(Obra obra, Runnable onSuccess, Runnable onError) {
        new Thread(() -> {
            Connection conn = null;
            PreparedStatement stmt = null;
            try {
                conn = ConexionBD.getConexion();
                if (conn == null) {
                    throw new SQLException("No se pudo establecer conexión con la base de datos");
                }//if
                conn.setAutoCommit(false);

                stmt = conn.prepareStatement(UPDATE_OBRA);
                stmt.setString(1, obra.getTitulo().trim());
                stmt.setString(2, obra.getAutor().trim());
                stmt.setString(3, obra.getTipo().toLowerCase());
                stmt.setInt(4, obra.getNumeroActos());
                stmt.setLong(5, obra.getIdObra());

                int filas = stmt.executeUpdate();
                if (filas == 0) {
                    throw new SQLException("No se encontró la obra con ID: " + obra.getIdObra());
                }//if

                conn.commit();
                onSuccess.run();
            } catch (SQLException e) {
                try {
                    if (conn != null) conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }//Catcch
                e.printStackTrace();
                onError.run();
            } finally {
                try {
                    if (stmt != null) stmt.close();
                    // NO cerramos conexión (Singleton)
                } catch (SQLException e) {
                    e.printStackTrace();
                    
                }//Cacth
                
            }//finally
            
        }).start();
    }//actualizar

    @Override
    public void eliminar(Long idObra, Runnable onSuccess, Runnable onError) {
        new Thread(() -> {
            Connection conn = null;
            PreparedStatement stmt = null;
            try {
                conn = ConexionBD.getConexion();
                if (conn == null) {
                    throw new SQLException("No se pudo establecer conexión con la base de datos");
                }//if
                conn.setAutoCommit(false);

                //no se elimina si hay producciones, osea, integridad refderencials
                try (PreparedStatement check = conn.prepareStatement(
                        "SELECT COUNT(*) FROM Produccion WHERE id_obra = ?")) {
                    check.setLong(1, idObra);
                    ResultSet rs = check.executeQuery();
                    if (rs.next() && rs.getInt(1) > 0) {
                        throw new SQLException("No se puede eliminar la obra ya que tiene producciones asociadas");
                    }//if
                }//try

                stmt = conn.prepareStatement(DELETE_OBRA);
                stmt.setLong(1, idObra);
                int filas = stmt.executeUpdate();
                if (filas == 0) {
                    throw new SQLException("No se encontró la obra con ID: " + idObra);
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
                    // NO cerramos conexión
                } catch (SQLException e) {
                    e.printStackTrace();
                    
                }//Cacth
                
            }//finally
            
        }).start();
        
    }//elimminar

    @Override
    public Obra buscarPorId(Long idObra) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = ConexionBD.getConexion();
            if (conn == null) {
                return null;
            }//Try

            stmt = conn.prepareStatement(SELECT_POR_ID);
            stmt.setLong(1, idObra);
            rs = stmt.executeQuery();
            if (rs.next()) {
                Obra obra = new Obra();
                obra.setIdObra(rs.getLong("id_obra"));
                obra.setTitulo(rs.getString("titulo"));
                obra.setAutor(rs.getString("autor"));
                obra.setTipo(rs.getString("tipo"));
                obra.setNumeroActos(rs.getInt("numero_actos"));
                return obra;
            }//if
        } catch (SQLException e) {
            e.printStackTrace();
            
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                // NO cerramos conexión
            } catch (SQLException e) {
                e.printStackTrace();
                
            }//Catch
            
        }//finally
        
        return null;
    }//buscarPorId

    @Override
    public List<Obra> listarTodas() {
        List<Obra> obras = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = ConexionBD.getConexion();
            if (conn == null) {
                return obras;
            }//if

            stmt = conn.prepareStatement(SELECT_TODAS);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Obra obra = new Obra();
                obra.setIdObra(rs.getLong("id_obra"));
                obra.setTitulo(rs.getString("titulo"));
                obra.setAutor(rs.getString("autor"));
                obra.setTipo(rs.getString("tipo"));
                obra.setNumeroActos(rs.getInt("numero_actos"));
                obras.add(obra);
            }//while
        } catch (SQLException e) {
            e.printStackTrace();
            
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                // NO cerramos conexión
            } catch (SQLException e) {
                e.printStackTrace();
            }//catch
        }//finally
        
        return obras;
        
    }//listar
    
    public boolean existeTitulo(String titulo) {
        String sql = "SELECT COUNT(*) FROM Obra WHERE LOWER(titulo) = LOWER(?)";
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, titulo.trim());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }//IF
        } catch (SQLException e) {
            e.printStackTrace();
        }//caths
        return false;
    }//existeTitulo
    
}//public class