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
public class SelectorVistas extends JFrame {

    private final Color FONDO_VENTANA = new Color(255, 245, 248);
    private final Color COLOR_BOTON = new Color(180, 120, 140);

    public SelectorVistas() {
        setTitle("🎭 Gestión de Obras - Seleccione Operación");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(500, 300);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        getContentPane().setBackground(FONDO_VENTANA);

        JLabel lblTitulo = new JLabel("¿Qué operación desea realizar?");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setHorizontalAlignment(JLabel.CENTER);
        add(lblTitulo, BorderLayout.NORTH);

        JPanel panelBotones = new JPanel(new GridLayout(2, 2, 15, 15));
        panelBotones.setBackground(FONDO_VENTANA);
        panelBotones.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JButton btnAlta = crearBoton("Altas");
        JButton btnBaja = crearBoton("Bajas");
        JButton btnCambio = crearBoton("Cambios");
        JButton btnConsulta = crearBoton("Consultas");

        panelBotones.add(btnAlta);
        panelBotones.add(btnBaja);
        panelBotones.add(btnCambio);
        panelBotones.add(btnConsulta);

        add(panelBotones, BorderLayout.CENTER);

        // Acciones
        btnAlta.addActionListener(e -> abrirABCC("ALTA"));
        btnBaja.addActionListener(e -> abrirABCC("BAJA"));
        btnCambio.addActionListener(e -> abrirABCC("CAMBIO"));
        btnConsulta.addActionListener(e -> abrirABCC("CONSULTA"));
    }//SelectorABCCObra

    private JButton crearBoton(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btn.setPreferredSize(new Dimension(150, 50));
        btn.setFocusable(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBackground(COLOR_BOTON);
        btn.setForeground(Color.WHITE);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        return btn;
    }//crearBoton

    private void abrirABCC(String modo) {
        ABCCObra ventana = new ABCCObra(modo);
        ventana.setVisible(true);
        this.dispose();
    }//abrirABCC

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }//catch

        SwingUtilities.invokeLater(() -> new SelectorVistas().setVisible(true));
    }//void main
}//public class