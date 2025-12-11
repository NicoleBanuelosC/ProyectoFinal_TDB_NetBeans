/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinal_tbd.reportes;

import com.mycompany.proyectofinal_tbd.dao.ProduccionDAOImpl;
import com.mycompany.proyectofinal_tbd.modelo.Produccion;
import com.mycompany.proyectofinal_tbd.dao.ObraDAOImpl;
import com.mycompany.proyectofinal_tbd.modelo.Obra;
import com.mycompany.proyectofinal_tbd.dao.MiembroDAOImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * 
 * 
 * @author banue
 */

public class ReporteProducciones extends JFrame {

    public ReporteProducciones() {
        setTitle("📊 Reporte: Producciones Detalladas");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(950, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        String[] columnas = {"ID", "Obra", "Autor", "Temporada", "Año", "Productor"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };

        JTable tabla = new JTable(modelo);
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabla.setRowHeight(25);
        tabla.getTableHeader().setBackground(new Color(230, 180, 200));
        tabla.getTableHeader().setForeground(Color.WHITE);

        //carga lo datos
        ProduccionDAOImpl produccionDAO = new ProduccionDAOImpl();
        ObraDAOImpl obraDAO = new ObraDAOImpl();
        MiembroDAOImpl miembroDAO = new MiembroDAOImpl();

        List<Produccion> producciones = produccionDAO.listarTodas();
        for (Produccion p : producciones) {
            Obra obra = obraDAO.buscarPorId(p.getIdObra());
            String productor = "ID: " + p.getIdProductor(); 
            modelo.addRow(new Object[]{
                p.getIdProduccion(),
                obra != null ? obra.getTitulo() : "Obra no encontrada",
                obra != null ? obra.getAutor() : "—",
                p.getTemporada(),
                p.getAnio(),
                productor
            });
        }//for

        JScrollPane scroll = new JScrollPane(tabla);
        add(scroll, BorderLayout.CENTER);

        JPanel panelBoton = new JPanel(new FlowLayout());
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.setBackground(new Color(200, 130, 150));
        btnCerrar.setForeground(Color.WHITE);
        btnCerrar.setFocusPainted(false);
        btnCerrar.addActionListener(e -> dispose());
        panelBoton.add(btnCerrar);
        add(panelBoton, BorderLayout.SOUTH);
    }//reporte
}//public class