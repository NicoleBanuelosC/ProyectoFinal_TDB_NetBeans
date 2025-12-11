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
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 
 * 
 * @author banue
 */

public class ReporteProducciones extends JFrame {

    private DefaultTableModel modelo;

    public ReporteProducciones() {
        setTitle("📊 Reporte: Producciones Detalladas");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(950, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        String[] columnas = {"ID", "Obra", "Autor", "Temporada", "Año", "Productor"};
        modelo = new DefaultTableModel(columnas, 0) {
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
        
        // exportar a pdf (pero no tengo sus libreraias)
        JButton btnExportar = new JButton("Exportar Reporte");
        btnExportar.setBackground(new Color(150, 180, 220));
        btnExportar.setForeground(Color.WHITE);
        btnExportar.setFocusPainted(false);
        btnExportar.addActionListener(e -> exportarReporte());
        panelBoton.add(btnExportar);
        
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.setBackground(new Color(200, 130, 150));
        btnCerrar.setForeground(Color.WHITE);
        btnCerrar.setFocusPainted(false);
        btnCerrar.addActionListener(e -> dispose());
        panelBoton.add(btnCerrar);
        add(panelBoton, BorderLayout.SOUTH);
    }//reporte

    private void exportarReporte() {
        JFileChooser chooser = new JFileChooser();
        chooser.setSelectedFile(new java.io.File("Reporte_Producciones.txt"));
        int result = chooser.showSaveDialog(this);
        if (result != JFileChooser.APPROVE_OPTION) return;

        String ruta = chooser.getSelectedFile().getAbsolutePath();
        if (!ruta.endsWith(".txt")) ruta += ".txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ruta))) {
            // Encabezado
            writer.write("TEATRO PLEASANTVILLE - REPORTE DE PRODUCCIONES");
            writer.newLine();
            writer.write("Generado el: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
            writer.newLine();
            writer.newLine();

            // Títulos
            String formato = "%-6s %-30s %-25s %-12s %-6s %-15s%n";
            writer.write(String.format(formato, "ID", "Obra", "Autor", "Temporada", "Año", "Productor"));
            writer.write("----------------------------------------------------------------------------------------------------");
            writer.newLine();

            // Datos
            for (int i = 0; i < modelo.getRowCount(); i++) {
                Object id = modelo.getValueAt(i, 0);
                Object obra = modelo.getValueAt(i, 1);
                Object autor = modelo.getValueAt(i, 2);
                Object temporada = modelo.getValueAt(i, 3);
                Object anio = modelo.getValueAt(i, 4);
                Object productor = modelo.getValueAt(i, 5);
                writer.write(String.format(formato,
                    id != null ? id.toString() : "",
                    obra != null ? obra.toString() : "",
                    autor != null ? autor.toString() : "",
                    temporada != null ? temporada.toString() : "",
                    anio != null ? anio.toString() : "",
                    productor != null ? productor.toString() : ""
                ));
            }

            JOptionPane.showMessageDialog(this, "✅ Reporte guardado:\n" + ruta, "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "❌ Error al guardar el reporte", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}//public class