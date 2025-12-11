/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinal_tbd.reportes;

import com.mycompany.proyectofinal_tbd.dao.ProduccionDAOImpl;
import com.mycompany.proyectofinal_tbd.modelo.Produccion;
import com.mycompany.proyectofinal_tbd.dao.ObraDAOImpl;
import com.mycompany.proyectofinal_tbd.modelo.Obra;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReporteGrafica extends JFrame {

    public ReporteGrafica() {
        setTitle("📈 Gráfica: Producciones por Obra");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(850, 600);
        setLocationRelativeTo(null);

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        ProduccionDAOImpl produccionDAO = new ProduccionDAOImpl();
        ObraDAOImpl obraDAO = new ObraDAOImpl();

        Map<Long, Integer> conteo = new HashMap<>();
        List<Produccion> producciones = produccionDAO.listarTodas();
        for (Produccion p : producciones) {
            conteo.put(p.getIdObra(), conteo.getOrDefault(p.getIdObra(), 0) + 1);
        }//for

        for (Map.Entry<Long, Integer> entry : conteo.entrySet()) {
            Obra obra = obraDAO.buscarPorId(entry.getKey());
            String nombre = obra != null ? obra.getTitulo() : "Obra ID: " + entry.getKey();
            if (nombre.length() > 20) nombre = nombre.substring(0, 17) + "...";
            dataset.addValue(entry.getValue(), "Producciones", nombre);
        }//For

        JFreeChart chart = ChartFactory.createBarChart(
            "Número de Producciones por Obra",
            "Obra",
            "Cantidad",
            dataset,
            PlotOrientation.VERTICAL,
            false, true, false
        );
        chart.getTitle().setFont(new Font("Segoe UI", Font.BOLD, 16));

        ChartPanel panel = new ChartPanel(chart);
        add(panel, BorderLayout.CENTER);

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.setBackground(new Color(200, 130, 150));
        btnCerrar.setForeground(Color.WHITE);
        btnCerrar.setFocusPainted(false);
        btnCerrar.addActionListener(e -> dispose());
        add(btnCerrar, BorderLayout.SOUTH);
    }//public
}//public class