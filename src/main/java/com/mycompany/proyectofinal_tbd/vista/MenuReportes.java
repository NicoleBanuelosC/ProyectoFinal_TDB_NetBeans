/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinal_tbd.vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author banue
 */
public class MenuReportes extends JFrame{
    private JButton btnReporteProducciones;
    private JButton btnReporteGrafica;
    private JButton btnCerrar;


    private final Color FONDO_VENTANA = new Color(255, 245, 248);
    private final Color COLOR_BOTON = new Color(180, 120, 140);
    private final Color COLOR_BOTON_DARK = new Color(150, 90, 110);

public MenuReportes() {
    setTitle("📊 Menú de Reportes - Teatro Pleasantville");
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    setSize(400, 300);
    setLocationRelativeTo(null);
    setLayout(new BorderLayout());

    getContentPane().setBackground(FONDO_VENTANA);

    JLabel titulo = new JLabel("📈 Seleccione un reporte", JLabel.CENTER);
    titulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
    titulo.setForeground(new Color(160, 100, 120));
    add(titulo, BorderLayout.NORTH);

    JPanel panelBotones = new JPanel(new GridLayout(3, 1, 15, 15));
    panelBotones.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
    panelBotones.setBackground(FONDO_VENTANA);

    btnReporteProducciones = crearBoton("📋 Reporte: Producciones Detalladas", COLOR_BOTON);
    btnReporteGrafica = crearBoton("📊 Gráfica: Ingresos por Producción", COLOR_BOTON_DARK);
    btnCerrar = crearBoton("Cerrar", new Color(200, 130, 150));

    panelBotones.add(btnReporteProducciones);
    panelBotones.add(btnReporteGrafica);
    panelBotones.add(btnCerrar);

    add(panelBotones, BorderLayout.CENTER);

    // Listeners
    btnReporteProducciones.addActionListener(e -> abrirReporteProducciones());
    btnReporteGrafica.addActionListener(e -> abrirReporteGrafica());
    btnCerrar.addActionListener(e -> dispose());
}//MenuReportes

private JButton crearBoton(String texto, Color colorFondo) {
    JButton btn = new JButton(texto);
    btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
    btn.setForeground(Color.WHITE);
    btn.setBackground(colorFondo);
    btn.setFocusPainted(false);
    btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    btn.setBorderPainted(false);
    btn.setOpaque(true);
    return btn;
}//crearBoton

private void abrirReporteProducciones() {
    new com.mycompany.proyectofinal_tbd.reportes.ReporteProducciones().setVisible(true);
}//abrirReporteProducciones

private void abrirReporteGrafica() {
    new com.mycompany.proyectofinal_tbd.reportes.ReporteGrafica().setVisible(true);
}//abrirReporteGrafica

public static void main(String[] args) {
    try {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (Exception e) {
        e.printStackTrace();
    }//catch
    
    SwingUtilities.invokeLater(() -> new MenuReportes().setVisible(true));
}//main

}//menuReportes
