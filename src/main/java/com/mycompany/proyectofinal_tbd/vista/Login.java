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

public class Login extends JFrame {

    private JTextField txtUsuario;
    private JPasswordField txtContrasena;
    private JButton btnIniciarSesion;

    public Login() {
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        GradientPanel panel = new GradientPanel();
        panel.setLayout(new GridBagLayout());

        Font fuenteBase = new Font("Segoe UI", Font.PLAIN, 20);
        Font fuenteTitulo = new Font("Segoe UI", Font.BOLD, 36);
        Font fuenteBoton = new Font("Segoe UI", Font.BOLD, 22);

        JLabel lblTitulo = new JLabel("INICIO DE SESIÓN");
        lblTitulo.setFont(fuenteTitulo);
        lblTitulo.setForeground(new Color(80, 50, 20));

        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setFont(fuenteBase);
        lblUsuario.setForeground(new Color(80, 50, 20));

        JLabel lblContrasena = new JLabel("Contraseña:");
        lblContrasena.setFont(fuenteBase);
        lblContrasena.setForeground(new Color(80, 50, 20));

        txtUsuario = new JTextField(15);
        txtUsuario.setFont(fuenteBase);
        txtUsuario.setPreferredSize(new Dimension(350, 50));
        txtUsuario.setFocusable(true);
        txtUsuario.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 150, 100), 2),
            BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));

        txtContrasena = new JPasswordField(15);
        txtContrasena.setFont(fuenteBase);
        txtContrasena.setPreferredSize(new Dimension(350, 50));
        txtContrasena.setFocusable(true);
        txtContrasena.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 150, 100), 2),
            BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));

        btnIniciarSesion = new JButton("Iniciar Sesión");
        btnIniciarSesion.setFont(fuenteBoton);
        btnIniciarSesion.setPreferredSize(new Dimension(280, 60));
        btnIniciarSesion.setBackground(new Color(139, 115, 85));
        btnIniciarSesion.setForeground(Color.WHITE);
        btnIniciarSesion.setFocusable(true);
        btnIniciarSesion.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnIniciarSesion.setBorder(BorderFactory.createEmptyBorder());

        btnIniciarSesion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                autenticar();
            }
        });

        txtUsuario.addActionListener(e -> autenticar());
        txtContrasena.addActionListener(e -> autenticar());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);

        gbc.gridy = 0; panel.add(lblTitulo, gbc);
        gbc.gridy = 1; panel.add(lblUsuario, gbc);
        gbc.gridy = 2; panel.add(txtUsuario, gbc);
        gbc.gridy = 3; panel.add(lblContrasena, gbc);
        gbc.gridy = 4; panel.add(txtContrasena, gbc);
        gbc.gridy = 5; panel.add(btnIniciarSesion, gbc);

        add(panel);

        SwingUtilities.invokeLater(() -> txtUsuario.requestFocusInWindow());
    }//public Login

    private void autenticar() {
        String usuario = txtUsuario.getText().trim();
        String contrasena = new String(txtContrasena.getPassword());

        if ("nicole".equals(usuario) && "1234".equals(contrasena)) {
            JOptionPane.showMessageDialog(this,
                "¡Bienvenida, " + usuario + "!",
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                "Usuario o contraseña incorrectos.",
                "Error de autenticación",
                JOptionPane.ERROR_MESSAGE);
            txtContrasena.selectAll();
            txtContrasena.requestFocusInWindow();
        }//else
    }//autenticar

    private static class GradientPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

            int w = getWidth();
            int h = getHeight();

            Color c1 = new Color(250, 240, 220); // Beige muy claro
            Color c2 = new Color(240, 225, 190); // Beige cálido

            GradientPaint gradient = new GradientPaint(0, 0, c1, 0, h, c2);
            g2d.setPaint(gradient);
            g2d.fillRect(0, 0, w, h);
            g2d.dispose();
        }//public pintar
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace(); 
        }//Try catch

        SwingUtilities.invokeLater(() -> new Login().setVisible(true));
        
    }//void main
    
}//public class