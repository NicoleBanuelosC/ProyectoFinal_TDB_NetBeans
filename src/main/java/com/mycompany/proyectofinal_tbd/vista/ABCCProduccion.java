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
    private JTextField txtBuscarId; 

    private JButton btnGuardar, btnEditar, btnEliminar, btnLimpiar;
    private Produccion produccionSeleccionada = null;

    // colores rosado suave y beige
    private final Color FONDO_VENTANA = new Color(255, 245, 248);
    private final Color FONDO_PANEL = new Color(250, 235, 240);
    private final Color COLOR_BOTON = new Color(180, 120, 140);
    private final Color COLOR_HEADER = new Color(160, 100, 120);

    public ABCCProduccion() {
        setTitle("Gestión de Producciones");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Aplicar fondo
        getContentPane().setBackground(FONDO_VENTANA);

        Font fuenteBase = new Font("Segoe UI", Font.PLAIN, 16);
        Font fuenteBoton = new Font("Segoe UI", Font.BOLD, 16);

        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setBorder(BorderFactory.createTitledBorder("Datos de la Producción"));
        panelForm.setFont(fuenteBase);
        panelForm.setBackground(FONDO_PANEL); // fondo panel

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12); // más espacio
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; 
        gbc.gridy = 0;
        panelForm.add(new JLabel("Obra *:"), gbc);
        gbc.gridx = 1;
        comboObra = new JComboBox<>();
        comboObra.setFont(fuenteBase);
        panelForm.add(comboObra, gbc);

        gbc.gridx = 0; 
        gbc.gridy = 1;
        panelForm.add(new JLabel("Temporada *:"), gbc);
        gbc.gridx = 1;
        comboTemporada = new JComboBox<>(new String[]{"otono", "primavera"});
        comboTemporada.setFont(fuenteBase);
        panelForm.add(comboTemporada, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panelForm.add(new JLabel("Año *:"), gbc);
        gbc.gridx = 1;
        txtAnio = new JTextField(10);
        txtAnio.setFont(fuenteBase);
        panelForm.add(txtAnio, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panelForm.add(new JLabel("Productor *:"), gbc);
        gbc.gridx = 1;
        comboProductor = new JComboBox<>();
        comboProductor.setFont(fuenteBase);
        panelForm.add(comboProductor, gbc);

        //botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.setBackground(FONDO_VENTANA); // mismo fondo
        btnGuardar = new JButton("Guardar");
        btnEditar = new JButton("Editar");
        btnEliminar = new JButton("Eliminar");
        btnLimpiar = new JButton("Limpiar");

        estiloBoton(btnGuardar, fuenteBoton, COLOR_BOTON);
        estiloBoton(btnEditar, fuenteBoton, new Color(130, 90, 110));
        estiloBoton(btnEliminar, fuenteBoton, new Color(190, 80, 100));
        estiloBoton(btnLimpiar, fuenteBoton, new Color(170, 140, 150));

        panelBotones.add(btnGuardar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);

        // tabla
        String[] columnas = {"ID", "Obra", "Temporada", "Año", "Productor"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        
        tablaProducciones = new JTable(modeloTabla);
        tablaProducciones.setFont(fuenteBase);
        tablaProducciones.setRowHeight(28); // ✨ más alto
        tablaProducciones.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // estilo de tabla
        tablaProducciones.setSelectionBackground(new Color(220, 190, 200));
        tablaProducciones.getTableHeader().setBackground(COLOR_HEADER);
        tablaProducciones.getTableHeader().setForeground(Color.WHITE);
        tablaProducciones.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));

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

        // Panel de búsqueda por ID
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBusqueda.setBackground(FONDO_VENTANA);
        JLabel lblBuscar = new JLabel("🔍 Buscar Producción por ID:");
        lblBuscar.setFont(fuenteBase);
        txtBuscarId = new JTextField(12);
        txtBuscarId.setFont(fuenteBase);
        JButton btnBuscar = new JButton("Buscar");
        estiloBoton(btnBuscar, fuenteBoton, new Color(100, 140, 180));
        btnBuscar.addActionListener(e -> buscarProduccionPorId());
        panelBusqueda.add(lblBuscar);
        panelBusqueda.add(txtBuscarId);
        panelBusqueda.add(btnBuscar);

        // Panel contenedor para botones y búsqueda
        JPanel panelInferior = new JPanel(new BorderLayout());
        panelInferior.setBackground(FONDO_VENTANA);
        panelInferior.add(panelBotones, BorderLayout.NORTH);
        panelInferior.add(panelBusqueda, BorderLayout.SOUTH);

        add(panelInferior, BorderLayout.SOUTH);

        //cargar desde la bd
        cargarListasDesplegables();
        cargarProducciones();
    }//public abbcProduccion

    private void estiloBoton(JButton btn, Font fuente, Color colorFondo) {
        btn.setFont(fuente);
        btn.setPreferredSize(new Dimension(120, 36)); // tamaño ligeramente mayor
        btn.setFocusable(false); 
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBackground(colorFondo);
        btn.setForeground(Color.WHITE);
        btn.setBorderPainted(false); // sin borde feo
        btn.setOpaque(true);
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
    }//EditarProduccion

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
            int anioActual = Year.now().getValue();
            if (anio < 2000 || anio > anioActual + 1) {
                JOptionPane.showMessageDialog(this, "Año inválido (2000 - " + (anioActual + 1) + ")", "Error", JOptionPane.ERROR_MESSAGE);
                txtAnio.requestFocus();
                return false;
            }//if
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El año debe ser un número válido", "Error", JOptionPane.ERROR_MESSAGE);
            txtAnio.requestFocus();
            return false;
        }//catch
        
        return true;
    }//validarCampos

    private void cargarProduccionSeleccionada(int fila) {
        Long idProduccion = (Long) modeloTabla.getValueAt(fila, 0);
        produccionSeleccionada = produccionDAO.buscarPorId(idProduccion);

        if (produccionSeleccionada != null) {
            comboObra.setSelectedItem(produccionSeleccionada.getIdObra());
            comboTemporada.setSelectedItem(produccionSeleccionada.getTemporada());
            txtAnio.setText(String.valueOf(produccionSeleccionada.getAnio()));
            comboProductor.setSelectedItem(produccionSeleccionada.getIdProductor());
        }//if
    }//CargarProduccion

    private void limpiarFormulario() {
        if (comboObra.getItemCount() > 0) comboObra.setSelectedIndex(0);
        comboTemporada.setSelectedIndex(0);
        txtAnio.setText("");
        
        if (comboProductor.getItemCount() > 0) comboProductor.setSelectedIndex(0);
        produccionSeleccionada = null;
    }//limpiar

    private void cargarListasDesplegables() {
        // cargar obras reales desde la base de datos
        comboObra.removeAllItems();
        java.util.List<com.mycompany.proyectofinal_tbd.modelo.Obra> obras = new com.mycompany.proyectofinal_tbd.dao.ObraDAOImpl().listarTodas();
        for (com.mycompany.proyectofinal_tbd.modelo.Obra o : obras) {
            comboObra.addItem(o.getIdObra());
        }//fir

        // Cargar productores (miembros) reales desde la base de datos
        comboProductor.removeAllItems();
        comboProductor.addItem(1L);
        comboProductor.addItem(2L);
        comboProductor.addItem(3L);
    }//CargarListasDesplegables

    private void cargarProducciones() {
        modeloTabla.setRowCount(0);
        java.util.List<Produccion> producciones = produccionDAO.listarTodas();
        for (Produccion p : producciones) {
            modeloTabla.addRow(new Object[]{
                p.getIdProduccion(),
                p.getIdObra(),
                p.getTemporada(),
                p.getAnio(),
                p.getIdProductor()
            });
        }//for
    }//cargarProducciones

    private void buscarProduccionPorId() {
        String idStr = txtBuscarId.getText().trim();
        if (idStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese un ID para buscar.", "Campo vacío", JOptionPane.WARNING_MESSAGE);
            return;
        }//if
        try {
            Long id = Long.parseLong(idStr);
            Produccion produccion = produccionDAO.buscarPorId(id);
            if (produccion != null) {
                // Mostrar solo esa producción en la tabla
                DefaultTableModel modeloTemp = new DefaultTableModel(new String[]{"ID", "Obra", "Temporada", "Año", "Productor"}, 0) {
                    @Override public boolean isCellEditable(int r, int c) { return false; }
                };
                modeloTemp.addRow(new Object[]{
                    produccion.getIdProduccion(),
                    produccion.getIdObra(),
                    produccion.getTemporada(),
                    produccion.getAnio(),
                    produccion.getIdProductor()
                });
                tablaProducciones.setModel(modeloTemp);
                // Cargar en el formulario
                comboObra.setSelectedItem(produccion.getIdObra());
                comboTemporada.setSelectedItem(produccion.getTemporada());
                txtAnio.setText(String.valueOf(produccion.getAnio()));
                comboProductor.setSelectedItem(produccion.getIdProductor());
                produccionSeleccionada = produccion;
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró producción con ID: " + id, "No encontrado", JOptionPane.INFORMATION_MESSAGE);
                // Volver a cargar la tabla completa
                cargarProducciones();
                limpiarFormulario();
            }//if
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID inválido. Ingrese un número entero.", "Error", JOptionPane.ERROR_MESSAGE);
            txtBuscarId.selectAll();
            txtBuscarId.requestFocus();
        }//catch
    }//buscarProduccionPorId

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }//catch
        
        SwingUtilities.invokeLater(() -> new ABCCProduccion().setVisible(true));
        
    }//void main
    
}//public class