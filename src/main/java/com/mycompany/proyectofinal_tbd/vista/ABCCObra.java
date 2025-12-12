/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinal_tbd.vista;

import com.mycompany.proyectofinal_tbd.dao.ObraDAO;
import com.mycompany.proyectofinal_tbd.dao.ObraDAOImpl;
import com.mycompany.proyectofinal_tbd.modelo.Obra;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.Cursor;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 *
 * @author banue
 */
public class ABCCObra extends JFrame{
    private ObraDAO obraDAO = new ObraDAOImpl();
    private DefaultTableModel modeloTabla;
    private JTable tablaObras;

    private JTextField txtTitulo, txtAutor, txtNumeroActos, txtBuscarId; 
    private JComboBox<String> comboTipo;

    private JButton btnGuardar, btnEditar, btnEliminar, btnLimpiar;
    private Obra obraSeleccionada = null;

    private final Color FONDO_VENTANA = new Color(255, 245, 248);
    private final Color FONDO_PANEL = new Color(250, 235, 240);
    private final Color COLOR_BOTON = new Color(180, 120, 140);
    private final Color COLOR_HEADER = new Color(160, 100, 120);

    private String modo; //aqui defino en que modo lo quiero
    //tipo, altas, bajas, cambios y consultas

    //recibir el nuevo parametro
    public ABCCObra(String modo) {
        this.modo = modo;
        initGUI();
    }//public

    public ABCCObra() {
        this.modo = "COMPLETO";
        initGUI();
    }//public

    private void initGUI() {
        setTitle("Gestión de Obras - " + modo);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        getContentPane().setBackground(FONDO_VENTANA);

        Font fuenteBase = new Font("Segoe UI", Font.PLAIN, 15);
        Font fuenteBoton = new Font("Segoe UI", Font.BOLD, 15);
        Font fuenteTitulo = new Font("Segoe UI", Font.BOLD, 17);

        // regresar
        JPanel panelRegresar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelRegresar.setBackground(FONDO_VENTANA);
        JButton btnRegresar = new JButton("← Regresar");
        btnRegresar.setFont(fuenteBoton);
        btnRegresar.setFocusable(false);
        btnRegresar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRegresar.setBackground(new Color(100, 140, 180));
        btnRegresar.setForeground(Color.WHITE);
        btnRegresar.setBorderPainted(false);
        btnRegresar.setOpaque(true);
        btnRegresar.addActionListener(e -> {
            this.dispose();
            new SelectorVistas().setVisible(true);
        });
        panelRegresar.add(btnRegresar);
        add(panelRegresar, BorderLayout.NORTH);

        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setBorder(BorderFactory.createTitledBorder("Datos de la Obra"));
        panelForm.setFont(fuenteBase);
        panelForm.setBackground(FONDO_PANEL);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        if ("CONSULTA".equals(modo)) {
            //consulta mood
            JLabel lblCriterio = new JLabel("Buscar por:");
            lblCriterio.setFont(fuenteTitulo);
            gbc.gridx = 0; gbc.gridy = 0;
            panelForm.add(lblCriterio, gbc);

            ButtonGroup grupoCriterio = new ButtonGroup();
            JRadioButton radioId = new JRadioButton("ID");
            JRadioButton radioTitulo = new JRadioButton("Título");
            JRadioButton radioAutor = new JRadioButton("Autor");

            radioId.setFont(fuenteBase);
            radioTitulo.setFont(fuenteBase);
            radioAutor.setFont(fuenteBase);

            grupoCriterio.add(radioId);
            grupoCriterio.add(radioTitulo);
            grupoCriterio.add(radioAutor);
            radioId.setSelected(true);

            gbc.gridx = 1; gbc.gridy = 0;
            panelForm.add(radioId, gbc);
            gbc.gridx = 2; panelForm.add(radioTitulo, gbc);
            gbc.gridx = 3; panelForm.add(radioAutor, gbc);

            gbc.gridx = 0; gbc.gridy = 1;
            panelForm.add(new JLabel("Valor:"), gbc);
            JTextField txtValor = new JTextField(20);
            txtValor.setFont(fuenteBase);
            gbc.gridx = 1;
            gbc.gridwidth = 3;
            panelForm.add(txtValor, gbc);
            gbc.gridwidth = 1;

            JButton btnBuscar = new JButton("🔍 Buscar");
            estiloBoton(btnBuscar, fuenteBoton, new Color(100, 140, 180));
            gbc.gridx = 1; gbc.gridy = 2;
            panelForm.add(btnBuscar, gbc);

            JButton btnExportar = new JButton("💾 Guardar consulta como...");
            btnExportar.setEnabled(false);
            estiloBoton(btnExportar, fuenteBoton, new Color(150, 180, 220));
            gbc.gridx = 2; gbc.gridy = 2;
            panelForm.add(btnExportar, gbc);

            btnBuscar.addActionListener(e -> {
                String valor = txtValor.getText().trim();
                if (valor.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Por favor ingrese un valor para buscar", "Campo vacío", JOptionPane.WARNING_MESSAGE);
                    btnExportar.setEnabled(false);
                    return;
                }//if
                try {
                    java.util.List<Obra> resultados = new ArrayList<>();
                    if (radioId.isSelected()) {
                        Long id = Long.parseLong(valor);
                        Obra obra = obraDAO.buscarPorId(id);
                        if (obra != null) resultados.add(obra);
                    } else if (radioTitulo.isSelected()) {
                        resultados = obraDAO.listarTodas().stream()
                            .filter(o -> o.getTitulo().toLowerCase().contains(valor.toLowerCase()))
                            .collect(Collectors.toList());
                    } else if (radioAutor.isSelected()) {
                        resultados = obraDAO.listarTodas().stream()
                            .filter(o -> o.getAutor().toLowerCase().contains(valor.toLowerCase()))
                            .collect(Collectors.toList());
                    }//if

                    modeloTabla.setRowCount(0);
                    if (resultados.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "No se encontraron obras con ese criterio", "No encontrado", JOptionPane.INFORMATION_MESSAGE);
                        limpiarFormulario();
                        btnExportar.setEnabled(false);
                    } else {
                        for (Obra o : resultados) {
                            modeloTabla.addRow(new Object[]{
                                o.getIdObra(),
                                o.getTitulo(),
                                o.getAutor(),
                                o.getTipo(),
                                o.getNumeroActos()
                            });
                        }//for
                        Obra primera = resultados.get(0);
                        txtTitulo.setText(primera.getTitulo());
                        txtAutor.setText(primera.getAutor());
                        comboTipo.setSelectedItem(primera.getTipo());
                        txtNumeroActos.setText(String.valueOf(primera.getNumeroActos()));
                        obraSeleccionada = primera;
                        btnExportar.setEnabled(true);
                    }//if
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "ID inválido. Ingrese un número entero.", "Error", JOptionPane.ERROR_MESSAGE);
                    btnExportar.setEnabled(false);
                }//catch
            });

            btnExportar.addActionListener(e -> {
                if (obraSeleccionada == null) {
                    JOptionPane.showMessageDialog(this, "No hay obra seleccionada para exportar", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    return;
                }//if
                guardarConsultaComo(obraSeleccionada);
            });

        } else {
            // Modos ALTA, BAJA, CAMBIO: formulario normal + tabla con TODOS los registros
            gbc.gridx = 0; gbc.gridy = 0;
            panelForm.add(new JLabel("Título *:"), gbc);
            gbc.gridx = 1;
            txtTitulo = new JTextField(25);
            txtTitulo.setFont(fuenteBase);
            txtTitulo.setEditable(!"CONSULTA".equals(modo));
            panelForm.add(txtTitulo, gbc);

            gbc.gridx = 0; gbc.gridy = 1;
            panelForm.add(new JLabel("Autor *:"), gbc);
            gbc.gridx = 1;
            txtAutor = new JTextField(25);
            txtAutor.setFont(fuenteBase);
            txtAutor.setEditable(!"CONSULTA".equals(modo));
            panelForm.add(txtAutor, gbc);

            gbc.gridx = 0; gbc.gridy = 2;
            panelForm.add(new JLabel("Tipo *:"), gbc);
            gbc.gridx = 1;
            comboTipo = new JComboBox<>(new String[]{"drama", "comedia", "musical", "infantil", "otro"});
            comboTipo.setFont(fuenteBase);
            comboTipo.setEnabled(!"CONSULTA".equals(modo));
            panelForm.add(comboTipo, gbc);

            gbc.gridx = 0; gbc.gridy = 3;
            panelForm.add(new JLabel("Número de Actos *:"), gbc);
            gbc.gridx = 1;
            txtNumeroActos = new JTextField(10);
            txtNumeroActos.setFont(fuenteBase);
            txtNumeroActos.setEditable(!"CONSULTA".equals(modo));
            panelForm.add(txtNumeroActos, gbc);
        }

        // Panel de botones (solo en modos no CONSULTA)
        if (!"CONSULTA".equals(modo)) {
            JPanel panelBotones = new JPanel(new FlowLayout());
            panelBotones.setBackground(FONDO_VENTANA);
            btnGuardar = new JButton("✅ Guardar");
            btnEditar = new JButton("✏️ Editar");
            btnEliminar = new JButton("🗑️ Eliminar");
            btnLimpiar = new JButton("🧹 Limpiar");

            estiloBoton(btnGuardar, fuenteBoton, COLOR_BOTON);
            estiloBoton(btnEditar, fuenteBoton, new Color(130, 90, 110));
            estiloBoton(btnEliminar, fuenteBoton, new Color(190, 80, 100));
            estiloBoton(btnLimpiar, fuenteBoton, new Color(170, 140, 150));

            if ("ALTA".equals(modo)) {
                panelBotones.add(btnGuardar);
                panelBotones.add(btnLimpiar);
            } else if ("BAJA".equals(modo)) {
                panelBotones.add(btnEliminar);
                panelBotones.add(btnLimpiar);
            } else if ("CAMBIO".equals(modo)) {
                panelBotones.add(btnEditar);
                panelBotones.add(btnLimpiar);
            } else {
                panelBotones.add(btnGuardar);
                panelBotones.add(btnEditar);
                panelBotones.add(btnEliminar);
                panelBotones.add(btnLimpiar);
            }

            btnGuardar.addActionListener(e -> guardarObra());
            btnEditar.addActionListener(e -> editarObra());
            btnEliminar.addActionListener(e -> eliminarObra());
            btnLimpiar.addActionListener(e -> limpiarFormulario());

            add(panelBotones, BorderLayout.SOUTH);
        }

        // Tabla: siempre se muestra, pero en CONSULTA empieza vacía
        String[] columnas = {"ID", "Título", "Autor", "Tipo", "Actos"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        tablaObras = new JTable(modeloTabla);
        tablaObras.setFont(fuenteBase);
        tablaObras.setRowHeight(28);
        tablaObras.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaObras.setSelectionBackground(new Color(220, 190, 200));
        tablaObras.getTableHeader().setBackground(COLOR_HEADER);
        tablaObras.getTableHeader().setForeground(Color.WHITE);
        tablaObras.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 15));

        tablaObras.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int fila = tablaObras.getSelectedRow();
                if (fila >= 0) {
                    cargarObraSeleccionada(fila);
                }//if adentro
            }//if afeura
        });

        add(panelForm, BorderLayout.NORTH);
        add(new JScrollPane(tablaObras), BorderLayout.CENTER);

        // Cargar todos los registros en modos no CONSULTA
        if ("CONSULTA".equals(modo)) {
            modeloTabla.setRowCount(0); // tabla vacía al inicio
        } else {
            cargarObras(); // ¡todos los registros!
        }
    }//initGUI

    private void estiloBoton(JButton btn, Font fuente, Color colorFondo) {
        btn.setFont(fuente);
        btn.setPreferredSize(new Dimension(120, 36));
        btn.setFocusable(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBackground(colorFondo);
        btn.setForeground(Color.WHITE);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
    }//EstiloBotno

    private void guardarObra() {
        if (!validarCampos()) return;
        Obra obra = new Obra();
        obra.setTitulo(txtTitulo.getText().trim());
        obra.setAutor(txtAutor.getText().trim());
        obra.setTipo((String) comboTipo.getSelectedItem());
        obra.setNumeroActos(Integer.parseInt(txtNumeroActos.getText().trim()));
        
        obraDAO.insertar(obra, () -> {
                JOptionPane.showMessageDialog(this, "Obra guardada", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarFormulario();
                cargarObras();
            },
            () -> JOptionPane.showMessageDialog(this, "Error al guardar", "Error", JOptionPane.ERROR_MESSAGE)
        );
    }//guardarObra

    private void editarObra() {
        if (obraSeleccionada == null) {
            JOptionPane.showMessageDialog(this, "Seleccione una obra", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }//if
        
        if (!validarCampos()) return;
        obraSeleccionada.setTitulo(txtTitulo.getText().trim());
        obraSeleccionada.setAutor(txtAutor.getText().trim());
        obraSeleccionada.setTipo((String) comboTipo.getSelectedItem());
        obraSeleccionada.setNumeroActos(Integer.parseInt(txtNumeroActos.getText().trim()));
        obraDAO.actualizar(obraSeleccionada,() -> {
                JOptionPane.showMessageDialog(this, "Obra actualizada", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarFormulario();
                cargarObras();
            },
            () -> JOptionPane.showMessageDialog(this, "Error al actualizar", "Error", JOptionPane.ERROR_MESSAGE)
        );
    }//editarObra

    private void eliminarObra() {
        if (obraSeleccionada == null) {
            JOptionPane.showMessageDialog(this, "Seleccione una obra", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }//if
        
        int confirm = JOptionPane.showConfirmDialog(this,
            "¿Eliminar la obra '" + obraSeleccionada.getTitulo() + "'?\n(Se eliminarán sus producciones si existen)",
            "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            obraDAO.eliminar(obraSeleccionada.getIdObra(), () -> {
                    JOptionPane.showMessageDialog(this, "Obra eliminada", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    limpiarFormulario();
                    cargarObras();
                },
                () -> JOptionPane.showMessageDialog(this, "Error al eliminar", "Error", JOptionPane.ERROR_MESSAGE)
            );
        }//if
    }//eliminarObra

    private boolean validarCampos() {
        if (txtTitulo.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El título es obligatorio", "Error", JOptionPane.ERROR_MESSAGE);
            txtTitulo.requestFocus();
            return false;
        }//if
        
        // validación: título solo letras, espacios, guiones etc
        String titulo = txtTitulo.getText().trim();
        if (!titulo.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\-' ]+")) {
            JOptionPane.showMessageDialog(this, "El título solo puede contener letras, espacios, guiones (-) y apóstrofes (')", "Error", JOptionPane.ERROR_MESSAGE);
            txtTitulo.requestFocus();
            return false;
        }//if

        // validacion de que solo haya una obra con ese unico titulo
        if (obraSeleccionada == null && obraDAO.existeTitulo(titulo)) {
            JOptionPane.showMessageDialog(this, "Ya existe una obra con el título: \"" + titulo + "\"", "Error", JOptionPane.ERROR_MESSAGE);
            txtTitulo.requestFocus();
            return false;
        }//if

        if (txtAutor.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El autor es obligatorio", "Error", JOptionPane.ERROR_MESSAGE);
            txtAutor.requestFocus();
            return false;
        }//if
        
        // validación: autor solo letras y espacios (sin numeros ni simbolos)
        String autor = txtAutor.getText().trim();
        if (!autor.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
            JOptionPane.showMessageDialog(this, "El autor solo puede contener letras y espacios", "Error", JOptionPane.ERROR_MESSAGE);
            txtAutor.requestFocus();
            return false;
        }//if

        try {
            int actos = Integer.parseInt(txtNumeroActos.getText().trim());
            if (actos <= 0 || actos > 10) {
                JOptionPane.showMessageDialog(this, "Número de actos debe estar entre 1 y 10", "Error", JOptionPane.ERROR_MESSAGE);
                txtNumeroActos.requestFocus();
                return false;
            }//if
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Número de actos debe ser un entero válido", "Error", JOptionPane.ERROR_MESSAGE);
            txtNumeroActos.requestFocus();
            return false;
        }//catch
        return true;
    }//validarCampos

    private void cargarObraSeleccionada(int fila) {
        Long idObra = (Long) modeloTabla.getValueAt(fila, 0);
        obraSeleccionada = obraDAO.buscarPorId(idObra);
        if (obraSeleccionada != null) {
            txtTitulo.setText(obraSeleccionada.getTitulo());
            txtAutor.setText(obraSeleccionada.getAutor());
            comboTipo.setSelectedItem(obraSeleccionada.getTipo());
            txtNumeroActos.setText(String.valueOf(obraSeleccionada.getNumeroActos()));
        }//If
    }//cargarObraSeleccionada

    private void limpiarFormulario() {
        txtTitulo.setText("");
        txtAutor.setText("");
        txtNumeroActos.setText("");
        comboTipo.setSelectedIndex(0);
        obraSeleccionada = null;
    }//limpiarFormulario

    private void cargarObras() {
        modeloTabla.setRowCount(0);
        java.util.List<Obra> obras = obraDAO.listarTodas();
        for (Obra o : obras) {
            modeloTabla.addRow(new Object[]{
                o.getIdObra(),
                o.getTitulo(),
                o.getAutor(),
                o.getTipo(),
                o.getNumeroActos()
            });
        }//for
    }//cargarObras

    private void buscarObraPorId() {
        String idStr = txtBuscarId.getText().trim();
        if (idStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese un ID para buscar.", "Campo vacío", JOptionPane.WARNING_MESSAGE);
            return;
        }//if
        try {
            Long id = Long.parseLong(idStr);
            Obra obra = obraDAO.buscarPorId(id);
            if (obra != null) {
                // Mostrar solo esa obra en la tabla (sin borrar LAS FALTANTES)
                DefaultTableModel modeloTemp = new DefaultTableModel(new String[]{"ID", "Título", "Autor", "Tipo", "Actos"}, 0) {
                    @Override public boolean isCellEditable(int r, int c) { return false; }
                };
                modeloTemp.addRow(new Object[]{
                    obra.getIdObra(),
                    obra.getTitulo(),
                    obra.getAutor(),
                    obra.getTipo(),
                    obra.getNumeroActos()
                });
                tablaObras.setModel(modeloTemp);
                // cargar en formulario
                txtTitulo.setText(obra.getTitulo());
                txtAutor.setText(obra.getAutor());
                comboTipo.setSelectedItem(obra.getTipo());
                txtNumeroActos.setText(String.valueOf(obra.getNumeroActos()));
                obraSeleccionada = obra;
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró obra con ID: " + id, "No encontrado", JOptionPane.INFORMATION_MESSAGE);
                // volver a cargar la tabla completa
                cargarObras();
                limpiarFormulario();
            }//if
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID inválido. Ingrese un número entero.", "Error", JOptionPane.ERROR_MESSAGE);
            txtBuscarId.selectAll();
            txtBuscarId.requestFocus();
        }//catch
    }//buscarObraPorId

    private void guardarConsultaComo(Obra obra) {
        JFileChooser chooser = new JFileChooser();
        chooser.setSelectedFile(new java.io.File("Consulta_Obra_" + obra.getIdObra() + ".txt"));
        int result = chooser.showSaveDialog(this);
        if (result != JFileChooser.APPROVE_OPTION) return;

        String ruta = chooser.getSelectedFile().getAbsolutePath();
        if (!ruta.endsWith(".txt")) ruta += ".txt";

        try (java.io.BufferedWriter writer = new java.io.BufferedWriter(new java.io.FileWriter(ruta))) {
            String fechaHora = java.time.LocalDateTime.now()
                .format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));

            writer.write("Se consultó la obra con ID: " + obra.getIdObra());
            writer.newLine();
            writer.write("Título: " + obra.getTitulo());
            writer.newLine();
            writer.write("Autor: " + obra.getAutor());
            writer.newLine();
            writer.write("Tipo: " + obra.getTipo());
            writer.newLine();
            writer.write("Número de Actos: " + obra.getNumeroActos());
            writer.newLine();
            writer.newLine();
            writer.write("Fecha y hora de la consulta: " + fechaHora);

            JOptionPane.showMessageDialog(this, "✅ Consulta guardada:\n" + ruta, "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (java.io.IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "❌ Error al guardar el archivo", "Error", JOptionPane.ERROR_MESSAGE);
        }//catch
    }//guardarConsultaComo

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
            
        }//catch
        
        SwingUtilities.invokeLater(() -> new ABCCObra().setVisible(true));
    }//void main
    
}//public class