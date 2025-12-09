/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinal_tbd.reportes;

import com.mycompany.proyectofinal_tbd.dao.ProduccionDAOImpl;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

/**
 *
 * @author banue
 */

public class ReporteGrafica  extends JFrame{
        public ReporteGrafica() {
        setTitle("📈 Gráfica: Ingresos por Producción");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(850, 600);
        setLocationRelativeTo(null);

        // Dataset
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        ProduccionDAOImpl dao = new ProduccionDAOImpl();
        var producciones = dao.listarProduccionesDetalladas();

        for (Map<String, Object> p : producciones) {
            Long id = (Long) p.get("id");
            String obra = (String) p.get("obra");
            double ingresos = dao.obtenerIngresosPorProduccion(id);
            if (ingresos > 0) {
                dataset.addValue(ingresos, "Ingresos", obra.length() > 20 ? obra.substring(0, 20) + "..." : obra);
            }
        }

        // Crear gráfica
        JFreeChart chart = ChartFactory.createBarChart(
            "Ingresos por Producción (USD)",
            "Obra",
            "Monto ($)",
            dataset,
            PlotOrientation.VERTICAL,
            false, true, false
        );
        chart.getTitle().setFont(new Font("Segoe UI", Font.BOLD, 16));

        // Mostrar
        ChartPanel panel = new ChartPanel(chart);
        panel.setPreferredSize(new Dimension(800, 500));
        add(panel, BorderLayout.CENTER);

        // Botón cerrar
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.setBackground(new Color(200, 130, 150));
        btnCerrar.setForeground(Color.WHITE);
        btnCerrar.setFocusPainted(false);
        btnCerrar.addActionListener(e -> dispose());
        add(btnCerrar, BorderLayout.SOUTH);
    }
        
}//ReporteGrafica
