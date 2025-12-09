/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinal_tbd.reportes;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.ChartFrame;
import javax.swing.*;
import java.awt.*;

public class PruebaJFreeChart extends JFrame{
    public static void main(String[] args) {
        // Crea un dataset simple
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(100, "Ingresos", "Obra A");
        dataset.addValue(200, "Ingresos", "Obra B");

        // Crea la gráfica
        JFreeChart chart = ChartFactory.createBarChart(
            "Prueba de JFreeChart",
            "Obra",
            "Monto ($)",
            dataset,
            PlotOrientation.VERTICAL,
            false, true, false
        );

        // Muestra en ventana
        ChartFrame frame = new ChartFrame("Mi Prueba", chart);
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}