/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinal_tbd.reportes;

import com.mycompany.proyectofinal_tbd.dao.ProduccionDAOImpl;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Map;
/**
 *
 * @author banue
 */
public class ReporteProducciones extends JFrame{

    public ReporteProducciones() {
        setTitle("📊 Reporte: Producciones Detalladas");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(950, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        String[] columnas = {"ID", "Obra", "Autor", "Temporada", "Año", "Productor", "Ingresos ($)"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };

        JTable tabla = new JTable(modelo);
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabla.setRowHeight(25);
        tabla.getTableHeader().setBackground(new Color(230, 180, 200));
        tabla.getTableHeader().setForeground(Color.WHITE);

        JScrollPane scroll = new JScrollPane(tabla);
        add(scroll, BorderLayout.CENTER);

        // aqui se cargar datos
        ProduccionDAOImpl dao = new ProduccionDAOImpl();
        var producciones = dao.listarProduccionesDetalladas();
        for (Map<String, Object> p : producciones) {
            Long id = (Long) p.get("id");
            String obra = (String) p.get("obra");
            String autor = (String) p.get("autor");
            String temporada = (String) p.get("temporada");
            Integer anio = (Integer) p.get("anio");
            String productor = (String) p.get("productor");
            double ingresos = dao.obtenerIngresosPorProduccion(id);
            modelo.addRow(new Object[]{
                id, obra, autor, temporada, anio, productor, String.format("%.2f", ingresos)
            });
        }//for

        //boton cerrar 
        JPanel panelBoton = new JPanel(new FlowLayout());
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.setBackground(new Color(200, 130, 150));
        btnCerrar.setForeground(Color.WHITE);
        btnCerrar.setFocusPainted(false);
        btnCerrar.addActionListener(e -> dispose());
        panelBoton.add(btnCerrar);
        add(panelBoton, BorderLayout.SOUTH);
    }//reporteProducciones
    
}//ReporteProducciones
