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

public class MainScreen extends JFrame {
    
    private String rolActual;
    private JLabel lblBienvenida;

    public MainScreen(String rol) {
        this.rolActual = rol;
        configurarVentana();
        componentesIniciales();
    }//MainScreen

    private void configurarVentana() {
        setTitle("Teatro Pleasantville - Sistema Administrativo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null); // centrar en pantalla
        setLayout(new BorderLayout());
    }//configurarVnetana

    private void componentesIniciales() {
        JPanel sidebar = crearSidebar();
        add(sidebar, BorderLayout.WEST);

        JPanel panelCentral = new JPanel(new BorderLayout());
        panelCentral.setBackground(new Color(250, 245, 235));

        lblBienvenida = new JLabel("Bienvenido al Sistema del Teatro Pleasantville ", JLabel.CENTER);
        lblBienvenida.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblBienvenida.setForeground(new Color(80, 50, 20));
        lblBienvenida.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        panelCentral.add(lblBienvenida, BorderLayout.CENTER);

        JLabel lblFooter = new JLabel("© 2025 Teatro Pleasantville - Todos los derechos reservados ", JLabel.CENTER);
        lblFooter.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblFooter.setForeground(new Color(120, 100, 80));
        panelCentral.add(lblFooter, BorderLayout.SOUTH);

        add(panelCentral, BorderLayout.CENTER);

        actualizarMensajeBienvenida();
    }//componentesIniciales

    private JPanel crearSidebar() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(30, 40, 50));
        panel.setPreferredSize(new Dimension(220, getHeight()));

        JLabel lblLogo = new JLabel("TEATRO PLEASANTVILLE");
        lblLogo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblLogo.setForeground(Color.WHITE);
        lblLogo.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblLogo.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));
        panel.add(lblLogo);

        JLabel lblRol = new JLabel("Rol: " + minmay(rolActual));
        lblRol.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblRol.setForeground(new Color(180, 180, 180));
        lblRol.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblRol.setBorder(BorderFactory.createEmptyBorder(0, 15, 15, 15));
        panel.add(lblRol);

        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(70, 80, 90));
        panel.add(sep);

        agregarBoton(panel, "Inicio", e -> mostrarInicio());
        
        //para que abra estas al darle click
        //las dos tablas relacionadas
        agregarBoton(panel, "Obras", e -> new ABCCObra().setVisible(true));
        agregarBoton(panel, "Producciones", e -> new ABCCProduccion().setVisible(true));

        if ("administrador".equals(rolActual) || "oficial".equals(rolActual)) {
            agregarBoton(panel, "Miembros", e -> abrirABCC("Miembro"));
            agregarBoton(panel, "Oficiales", e -> abrirABCC("Oficial"));
            agregarBoton(panel, "Patronos", e -> abrirABCC("Patrono"));
            agregarBoton(panel, "Boletos", e -> abrirABCC("Boleto"));
        }//if

        agregarBoton(panel, "Cerrar Sesión", e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                "¿Desea cerrar sesión?",
                "Confirmar salida",
                JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                dispose();
                new LoginJava().setVisible(true);
            }//if
        });

        return panel;
    }//crearSideBar

    private void agregarBoton(JPanel panel, String texto, ActionListener listener) {
        JButton btn = new JButton(texto);
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setMaximumSize(new Dimension(220, 40));
        btn.setBackground(new Color(50, 60, 70));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addActionListener(listener);
        panel.add(btn);
    }//agregarBoton

    private void actualizarMensajeBienvenida() {
        String mensaje = "";
        switch (rolActual) {
            case "administrador":
                mensaje = "¡Bienvenido, Administrador! Gestiona todo el sistema ";
                break;
                
            case "oficial":
                mensaje = "¡Bienvenido, Oficial! Administra obras y miembros ";
                break;
                
            case "productor":
                mensaje = "¡Bienvenido, Productor! Gestiona tus producciones ";
                break;
                
            case "miembro":
                mensaje = "¡Bienvenido, Miembro! Consulta obras y eventos ";
                break;
                
            default:
                mensaje = "Bienvenido al Sistema del Teatro Pleasantville ";
        }//switcj
        
        lblBienvenida.setText(mensaje);
    }//ActualizarMsj

    private void mostrarInicio() {
        lblBienvenida.setText("Panel de Inicio - Rol: " + minmay(rolActual));
    }//mostrarInicio

    private void abrirABCC(String entidad) {
        JOptionPane.showMessageDialog(this,
            "Módulo de " + entidad + " aún en desarrollo.\n Pronto estará conectado a Oracle ",
            "En construcción",
            JOptionPane.INFORMATION_MESSAGE);
    }//Abrir abcc

    //primera mayuscula, lo demas minuscula
    private String minmay(String str) {
        if (str == null || str.isEmpty()) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }//minmay

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }//Catthc
        
        SwingUtilities.invokeLater(() -> new MainScreen("administrador").setVisible(true));
    }//void main
    
}//MainScreen
