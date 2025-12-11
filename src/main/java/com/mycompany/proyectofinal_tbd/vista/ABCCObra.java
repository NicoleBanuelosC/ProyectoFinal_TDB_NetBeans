package com.mycompany.proyectofinal_tbd.vista;

import com.mycompany.proyectofinal_tbd.dao.ObraDAO;
import com.mycompany.proyectofinal_tbd.dao.ObraDAOImpl;
import com.mycompany.proyectofinal_tbd.modelo.Obra;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.Cursor;

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

    public ABCCObra() {
        setTitle("Gestión de Obras");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        getContentPane().setBackground(FONDO_VENTANA);

        Font fuenteBase = new Font("Segoe UI", Font.PLAIN, 16);
        Font fuenteBoton = new Font("Segoe UI", Font.BOLD, 16);

        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setBorder(BorderFactory.createTitledBorder("Datos de la Obra"));
        panelForm.setFont(fuenteBase);
        panelForm.setBackground(FONDO_PANEL);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; 
        gbc.gridy = 0;
        panelForm.add(new JLabel("Título *:"), gbc);
        gbc.gridx = 1;
        txtTitulo = new JTextField(20);
        txtTitulo.setFont(fuenteBase);
        panelForm.add(txtTitulo, gbc);

        gbc.gridx = 0; 
        gbc.gridy = 1;
        panelForm.add(new JLabel("Autor *:"), gbc);
        gbc.gridx = 1;
        txtAutor = new JTextField(20);
        txtAutor.setFont(fuenteBase);
        panelForm.add(txtAutor, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panelForm.add(new JLabel("Tipo *:"), gbc);
        gbc.gridx = 1;
        comboTipo = new JComboBox<>(new String[]{"drama", "comedia", "musical", "infantil", "otro"});
        comboTipo.setFont(fuenteBase);
        panelForm.add(comboTipo, gbc);

        gbc.gridx = 0; 
        gbc.gridy = 3;
        panelForm.add(new JLabel("Número de Actos *:"), gbc);
        gbc.gridx = 1;
        txtNumeroActos = new JTextField(10);
        txtNumeroActos.setFont(fuenteBase);
        panelForm.add(txtNumeroActos, gbc);

        // Panel de botones principales (Alta, Editar, Baja, Limpiar)
        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.setBackground(FONDO_VENTANA);
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

        //tabla
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
        tablaObras.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));

        tablaObras.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int fila = tablaObras.getSelectedRow();
                if (fila >= 0) {
                    cargarObraSeleccionada(fila);
                }//if adentro
            }//if afeura
        });

        btnGuardar.addActionListener(e -> guardarObra());
        btnEditar.addActionListener(e -> editarObra());
        btnEliminar.addActionListener(e -> eliminarObra());
        btnLimpiar.addActionListener(e -> limpiarFormulario());

        add(panelForm, BorderLayout.NORTH);
        add(new JScrollPane(tablaObras), BorderLayout.CENTER);

        // Panel de búsqueda por ID
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBusqueda.setBackground(FONDO_VENTANA);
        JLabel lblBuscar = new JLabel("🔍 Buscar Obra por ID:");
        lblBuscar.setFont(fuenteBase);
        txtBuscarId = new JTextField(12);
        txtBuscarId.setFont(fuenteBase);
        JButton btnBuscar = new JButton("Buscar");
        estiloBoton(btnBuscar, fuenteBoton, new Color(100, 140, 180));
        btnBuscar.addActionListener(e -> buscarObraPorId());
        panelBusqueda.add(lblBuscar);
        panelBusqueda.add(txtBuscarId);
        panelBusqueda.add(btnBuscar);

        // Panel contenedor para incluir ambos: botones y búsqueda
        JPanel panelInferior = new JPanel(new BorderLayout());
        panelInferior.setBackground(FONDO_VENTANA);
        panelInferior.add(panelBotones, BorderLayout.NORTH);
        panelInferior.add(panelBusqueda, BorderLayout.SOUTH);

        add(panelInferior, BorderLayout.SOUTH);

        cargarObras();
    }//puiblic abbc obra

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
        if (txtAutor.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El autor es obligatorio", "Error", JOptionPane.ERROR_MESSAGE);
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

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
            
        }//catch
        
        SwingUtilities.invokeLater(() -> new ABCCObra().setVisible(true));
    }//void main
    
}//public class