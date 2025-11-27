/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.proyectofinal_tbd.vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

/**
 *
 * @author banue
 */

public class Login extends JFrame {

    private JTextField txtUsuario;
    private JPasswordField txtContrasena;
    private JButton btnIniciarSesion;

    public Login() {
        setTitle("INICIAR SESIÓN");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600); 
        setLocationRelativeTo(null); // Centrar
        setLayout(new BorderLayout());

        // panel principal con dos columnas
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.LIGHT_GRAY);

        // izquierdo - imagen
        JPanel panelImagen = new JPanel(new BorderLayout());
        panelImagen.setPreferredSize(new Dimension(400, 0));
        panelImagen.setBackground(Color.WHITE);

        // cargar imagen
        try {
            URL imageUrl = getClass().getResource("/com/mycompany/proyectofinal_tbd/imagenes/teatro.jpg");
            if (imageUrl != null) {
                BufferedImage img = ImageIO.read(imageUrl);
                JLabel labelImagen = new JLabel(new ImageIcon(img));
                labelImagen.setHorizontalAlignment(JLabel.CENTER);
                panelImagen.add(labelImagen, BorderLayout.CENTER);
                
            } else {
                JLabel labelError = new JLabel("Imagen no encontrada", JLabel.CENTER);
                labelError.setForeground(Color.RED);
                panelImagen.add(labelError, BorderLayout.CENTER);
                
            }//Else
            
        } catch (IOException e) {
            e.printStackTrace();
            JLabel labelError = new JLabel("Error al cargar imagen", JLabel.CENTER);
            labelError.setForeground(Color.RED);
            panelImagen.add(labelError, BorderLayout.CENTER);
        }//acthc

        // derecho - login
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelFormulario.setBackground(Color.WHITE);

        Font fuenteBase = new Font("Segoe UI", Font.PLAIN, 18);
        Font fuenteTitulo = new Font("Segoe UI", Font.BOLD, 28);
        Font fuenteBoton = new Font("Segoe UI", Font.BOLD, 20);

        JLabel lblTitulo = new JLabel("INICIAR SESIÓN");
        lblTitulo.setFont(fuenteTitulo);
        lblTitulo.setForeground(new Color(80, 50, 20));

        JLabel lblUsuario = new JLabel("Ingrese Usuario");
        lblUsuario.setFont(fuenteBase);
        lblUsuario.setForeground(new Color(80, 50, 20));

        JLabel lblContrasena = new JLabel("Ingrese Contraseña");
        lblContrasena.setFont(fuenteBase);
        lblContrasena.setForeground(new Color(80, 50, 20));

        txtUsuario = new JTextField(15);
        txtUsuario.setFont(fuenteBase);
        txtUsuario.setPreferredSize(new Dimension(250, 40));
        txtUsuario.setFocusable(true);
        txtUsuario.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 150, 100), 2),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        txtContrasena = new JPasswordField(15);
        txtContrasena.setFont(fuenteBase);
        txtContrasena.setPreferredSize(new Dimension(250, 40));
        txtContrasena.setFocusable(true);
        txtContrasena.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 150, 100), 2),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        btnIniciarSesion = new JButton(" INGRESAR ");
        btnIniciarSesion.setFont(fuenteBoton);
        btnIniciarSesion.setPreferredSize(new Dimension(200, 50));
        btnIniciarSesion.setBackground(new Color(60, 120, 255));
        btnIniciarSesion.setForeground(Color.WHITE);
        btnIniciarSesion.setFocusable(true);
        btnIniciarSesion.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnIniciarSesion.setBorder(BorderFactory.createEmptyBorder());

        btnIniciarSesion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                autenticar();
            }//actionPerformed
        });

        txtUsuario.addActionListener(e -> autenticar());
        txtContrasena.addActionListener(e -> autenticar());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panelFormulario.add(lblTitulo, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1;
        panelFormulario.add(lblUsuario, gbc);
        gbc.gridx = 1;
        panelFormulario.add(txtUsuario, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panelFormulario.add(lblContrasena, gbc);
        gbc.gridx = 1;
        panelFormulario.add(txtContrasena, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        panelFormulario.add(btnIniciarSesion, gbc);

        mainPanel.add(panelImagen, BorderLayout.WEST);
        mainPanel.add(panelFormulario, BorderLayout.CENTER);

        add(mainPanel);

        SwingUtilities.invokeLater(() -> txtUsuario.requestFocusInWindow());
    }//public Login

    private void autenticar() {
        String usuario = txtUsuario.getText().trim();
        String contrasena = new String(txtContrasena.getPassword());

        if ("productor@teatro.com".equals(usuario) && "prd123".equals(contrasena)) {
            JOptionPane.showMessageDialog(this,
                "¡Bienvenido, Productor!",
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE);
            dispose();
            new MainScreen("productor").setVisible(true);
        }//if
        
        else if ("miembro@teatro.com".equals(usuario) && "miem123".equals(contrasena)) {
            JOptionPane.showMessageDialog(this,
                "¡Bienvenido, Miembro!",
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE);
            dispose();
            new MainScreen("miembro").setVisible(true);
        }//else if
        
        else if ("oficial@teatro.com".equals(usuario) && "ofc123".equals(contrasena)) {
            JOptionPane.showMessageDialog(this,
                "¡Bienvenido, Oficial!",
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE);
            dispose();
            new MainScreen("oficial").setVisible(true);
        }//Else if
        
        else if ("admin@teatro.com".equals(usuario) && "adm123".equals(contrasena)) {
            JOptionPane.showMessageDialog(this,
                "¡Bienvenido, Administrador!",
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE);
            dispose();
            new MainScreen("administrador").setVisible(true);
        }//Else if
        
        else {
            JOptionPane.showMessageDialog(this,
                "Usuario o contraseña incorrectos.\n Cada rol tiene una contraseña fija asignada ",
                "Acceso denegado",
                JOptionPane.ERROR_MESSAGE);
            txtContrasena.selectAll();
            txtUsuario.requestFocus();
        }//else
        
    }//autenticar

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace(); 
        }//Try catch

        SwingUtilities.invokeLater(() -> new Login().setVisible(true));
        
    }//void main
    
}//public class