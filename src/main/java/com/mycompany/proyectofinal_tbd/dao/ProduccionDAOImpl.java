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
import java.util.Map;
import java.util.HashMap;

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

    //las transacciones ya se encuentran en los metodos
    //los trigger se activan al usar tablas como miembro
    
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
                
                conn.setAutoCommit(false); //inicia transaccion

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

                conn.commit(); //confirma transaccion
                onSuccess.run();
            } catch (SQLException e) {
                try {
                    if (conn != null) conn.rollback(); //revierte si hay error
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
                
                conn.setAutoCommit(false); //inicia transaccion

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

                conn.commit(); //confirma transaccion
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
                conn.setAutoCommit(false); //inicia transaccion

                stmt = conn.prepareStatement(DELETE_PRODUCCION);
                stmt.setLong(1, idProduccion);
                int filas = stmt.executeUpdate();
                if (filas == 0) {
                    throw new SQLException("No se encontró la producción con ID: " + idProduccion);
                }//if

                conn.commit(); //confirma
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

    //estos metodos no usan transacciones porque solo hacen SELECT (no modifican datos)
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

    //aqui mando llamar al procedimiento almacenado vender_boleto
    public void venderBoleto(
        Long idRepresentacion, Long idPatrono,
        String fila, int numero,
        double precio, String tipoBoleto,
        Runnable onSuccess, Runnable onError
    ) {
        new Thread(() -> {
            try (Connection conn = ConexionBD.getConexion()) {
                String sql = "{CALL vender_boleto(?, ?, ?, ?, ?, ?)}";
                try (CallableStatement cs = conn.prepareCall(sql)) {
                    cs.setLong(1, idRepresentacion);
                    cs.setLong(2, idPatrono);
                    cs.setString(3, fila);
                    cs.setInt(4, numero);
                    cs.setDouble(5, precio);
                    cs.setString(6, tipoBoleto);
                    cs.execute();
                    onSuccess.run();
                }//try
            } catch (SQLException e) {
                onError.run();
                e.printStackTrace();
            }//catch
        }).start();
    }//venderBoleto

    //aqui mando llamar mi funcion
    public double obtenerIngresosPorProduccion(Long idProduccion) {
        try (Connection conn = ConexionBD.getConexion()) {
            String sql = "{ ? = call ingresos_por_produccion(?) }";
            try (CallableStatement cs = conn.prepareCall(sql)) {
                cs.registerOutParameter(1, Types.NUMERIC);
                cs.setLong(2, idProduccion);
                cs.execute();
                return cs.getDouble(1);
            }//try
        } catch (SQLException e) {
            e.printStackTrace();
            return 0.0;
        }//catch
    }//obtenerIngresosPorProduccion

    //mando llamar otra funcion
    public int obtenerTotalBoletosVendidos(Long idRepresentacion) {
        try (Connection conn = ConexionBD.getConexion()) {
            String sql = "{ ? = call total_boletos_vendidos(?) }";
            try (CallableStatement cs = conn.prepareCall(sql)) {
                cs.registerOutParameter(1, Types.INTEGER);
                cs.setLong(2, idRepresentacion);
                cs.execute();
                return cs.getInt(1);
            }//try
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }//catch
    }//obtenerTotalBoletosVendidos

    //uso de vista para generar listados de consulta multitablas con joins
    public java.util.List<java.util.Map<String, Object>> listarProduccionesDetalladas() {
        java.util.List<java.util.Map<String, Object>> lista = new java.util.ArrayList<>();
        java.sql.Connection conn = null;
        java.sql.PreparedStatement stmt = null;
        java.sql.ResultSet rs = null;
        try {
            conn = ConexionBD.getConexion();
            if (conn == null) return lista;

            // consulta a la VISTA (JOIN de Produccion + Obra + Miembro)
            String sql = "SELECT * FROM vista_producciones_detalles ORDER BY anio DESC";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                java.util.Map<String, Object> fila = new java.util.HashMap<>();
                fila.put("id", rs.getLong("id_produccion"));
                fila.put("obra", rs.getString("obra_titulo"));
                fila.put("autor", rs.getString("obra_autor"));
                fila.put("temporada", rs.getString("temporada"));
                fila.put("anio", rs.getInt("anio"));
                fila.put("productor", rs.getString("productor_nombre"));
                lista.add(fila);
            }//while
            
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
            }//catch
        }//finally
        return lista;
    }//listarProduccionesDetalladas
    
}//public class