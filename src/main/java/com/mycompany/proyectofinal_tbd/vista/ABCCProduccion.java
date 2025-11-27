/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinal_tbd.vista;

import com.mycompany.proyectofinal_tbd.dao.ProduccionDAO;
import com.mycompany.proyectofinal_tbd.dao.ProduccionDAOImpl;
import com.mycompany.proyectofinal_tbd.modelo.Produccion;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Year;

/**
 *
 * @author banue
 */

public class ABCCProduccion extends JFrame{
    private ProduccionDAO produccionDAO = new ProduccionDAOImpl();
    private DefaultTableModel modeloTabla;
    private JTable tablaProducciones;

    private JComboBox<Long> comboObra;
    private JComboBox<String> comboTemporada;
    private JTextField txtAnio;
    private JComboBox<Long> comboProductor;

    private JButton btnGuardar, btnEditar, btnEliminar, btnLimpiar;
    private Produccion produccionSeleccionada = null;

    public ABCCProduccion() {
        setTitle("Gestión de Producciones");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        Font fuenteBase = new Font("Segoe UI", Font.PLAIN, 16);
        Font fuenteBoton = new Font("Segoe UI", Font.BOLD, 16);

        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setBorder(BorderFactory.createTitledBorder("Datos de la Producción"));
        panelForm.setFont(fuenteBase);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        panelForm.add(new JLabel("Obra *:"), gbc);
        gbc.gridx = 1;
        comboObra = new JComboBox<>();
        comboObra.setFont(fuenteBase);
        panelForm.add(comboObra, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panelForm.add(new JLabel("Temporada *:"), gbc);
        gbc.gridx = 1;
        comboTemporada = new JComboBox<>(new String[]{"otono", "primavera"});
        comboTemporada.setFont(fuenteBase);
        panelForm.add(comboTemporada, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panelForm.add(new JLabel("Año *:"), gbc);
        gbc.gridx = 1;
        txtAnio = new JTextField(10);
        txtAnio.setFont(fuenteBase);
        panelForm.add(txtAnio, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        panelForm.add(new JLabel("Productor *:"), gbc);
        gbc.gridx = 1;
        comboProductor = new JComboBox<>();
        comboProductor.setFont(fuenteBase);
        panelForm.add(comboProductor, gbc);

        //botnotes
        JPanel panelBotones = new JPanel(new FlowLayout());
        btnGuardar = new JButton("Guardar");
        btnEditar = new JButton("Editar");
        btnEliminar = new JButton("Eliminar");
        btnLimpiar = new JButton("Limpiar");

        estiloBoton(btnGuardar, fuenteBoton);
        estiloBoton(btnEditar, fuenteBoton);
        estiloBoton(btnEliminar, fuenteBoton);
        estiloBoton(btnLimpiar, fuenteBoton);

        panelBotones.add(btnGuardar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);

        // tabkita
        String[] columnas = {"ID", "Obra", "Temporada", "Año", "Productor"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        
        tablaProducciones = new JTable(modeloTabla);
        tablaProducciones.setFont(fuenteBase);
        tablaProducciones.setRowHeight(25);
        tablaProducciones.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        tablaProducciones.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int fila = tablaProducciones.getSelectedRow();
                if (fila >= 0) {
                    cargarProduccionSeleccionada(fila);
                }//if adentro
            }//if afuera
        });

        // actionlisteners
        btnGuardar.addActionListener(e -> guardarProduccion());
        btnEditar.addActionListener(e -> editarProduccion());
        btnEliminar.addActionListener(e -> eliminarProduccion());
        btnLimpiar.addActionListener(e -> limpiarFormulario());

        add(panelForm, BorderLayout.NORTH);
        add(new JScrollPane(tablaProducciones), BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        //cargar desde la bd
        cargarListasDesplegables();
        cargarProducciones();
    }//public abbcProduccion

    private void estiloBoton(JButton btn, Font fuente) {
        btn.setFont(fuente);
        btn.setPreferredSize(new Dimension(120, 32));
        btn.setFocusable(true);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBackground(new Color(139, 115, 85));
        btn.setForeground(Color.WHITE);
    }//EstiloBoton

    private void guardarProduccion() {
        if (!validarCampos()) return;

        Produccion p = new Produccion();
        p.setIdObra((Long) comboObra.getSelectedItem());
        p.setTemporada((String) comboTemporada.getSelectedItem());
        p.setAnio(Integer.parseInt(txtAnio.getText().trim()));
        p.setIdProductor((Long) comboProductor.getSelectedItem());

        produccionDAO.insertar(p,() -> {
                JOptionPane.showMessageDialog(this, "Producción guardada", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarFormulario();
                cargarProducciones();
            },
            () -> JOptionPane.showMessageDialog(this, "Error al guardar", "Error", JOptionPane.ERROR_MESSAGE)
        );
    }//guardarProduccion

    private void editarProduccion() {
        if (produccionSeleccionada == null) {
            JOptionPane.showMessageDialog(this, "Seleccione una producción", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }//if
        
        if (!validarCampos()) return;

        produccionSeleccionada.setIdObra((Long) comboObra.getSelectedItem());
        produccionSeleccionada.setTemporada((String) comboTemporada.getSelectedItem());
        produccionSeleccionada.setAnio(Integer.parseInt(txtAnio.getText().trim()));
        produccionSeleccionada.setIdProductor((Long) comboProductor.getSelectedItem());

        produccionDAO.actualizar(produccionSeleccionada,
            () -> {
                JOptionPane.showMessageDialog(this, "Producción actualizada", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarFormulario();
                cargarProducciones();
            },
            () -> JOptionPane.showMessageDialog(this, "Error al actualizar", "Error", JOptionPane.ERROR_MESSAGE)
        );
    }//Ediatrproduccion

    private void eliminarProduccion() {
        if (produccionSeleccionada == null) {
            JOptionPane.showMessageDialog(this, "Seleccione una producción", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }//if

        int confirm = JOptionPane.showConfirmDialog(this,
            "¿Eliminar esta producción?",
            "Confirmar",
            JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            produccionDAO.eliminar(produccionSeleccionada.getIdProduccion(),
                () -> {
                    JOptionPane.showMessageDialog(this, "Producción eliminada", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    limpiarFormulario();
                    cargarProducciones();
                },
                () -> JOptionPane.showMessageDialog(this, "Error al eliminar", "Error", JOptionPane.ERROR_MESSAGE)
            );
        }//if
    }//eliminarProduccion

    private boolean validarCampos() {
        if (txtAnio.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El año es obligatorio", "Error", JOptionPane.ERROR_MESSAGE);
            txtAnio.requestFocus();
            return false;
        }//if
        
        try {
            int anio = Integer.parseInt(txtAnio.getText().trim());
            int anioActual = java.time.Year.now().getValue();
            if (anio < 2000 || anio > anioActual + 1) {
                JOptionPane.showMessageDialog(this, "Año inválido (2000 - " + (anioActual + 1) + ")", "Error", JOptionPane.ERROR_MESSAGE);
                txtAnio.requestFocus();
                return false;
            }//if
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El año debe ser un número válido ", "Error", JOptionPane.ERROR_MESSAGE);
            txtAnio.requestFocus();
            return false;
        }//Catch
        
        return true;
    }//validarCampos

    private void cargarProduccionSeleccionada(int fila) {
        JOptionPane.showMessageDialog(this, "Funcionalidad de edición en desarrollo", "Info", JOptionPane.INFORMATION_MESSAGE);
    }//CargarProduccion

    private void limpiarFormulario() {
        if (comboObra.getItemCount() > 0) comboObra.setSelectedIndex(0);
        comboTemporada.setSelectedIndex(0);
        txtAnio.setText("");
        
        if (comboProductor.getItemCount() > 0) comboProductor.setSelectedIndex(0);
        produccionSeleccionada = null;
    }//limpiar

    private void cargarListasDesplegables() {
        //llamadas al doa
        comboObra.addItem(1L);
        comboObra.addItem(2L);
        comboProductor.addItem(1L);
        comboProductor.addItem(2L);
    }//CargarListasDesplegables

    private void cargarProducciones() {
        modeloTabla.setRowCount(0);
        //actualizar despues con el dao
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }//cathc
        
        SwingUtilities.invokeLater(() -> new ABCCProduccion().setVisible(true));
        
    }//vois main
    
}//public class
