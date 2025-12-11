package com.mycompany.proyectofinal_tbd.vista;

import com.mycompany.proyectofinal_tbd.dao.MiembroDAO;
import com.mycompany.proyectofinal_tbd.dao.MiembroDAOImpl;
import com.mycompany.proyectofinal_tbd.modelo.Miembro;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.Cursor;          
import java.time.LocalDate;     
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class ABCCMiembro extends JFrame {
    private MiembroDAO miembroDAO = new MiembroDAOImpl();
    private DefaultTableModel modeloTabla;
    private JTable tablaMiembros;

    private JTextField txtNombre, txtApellido, txtTelefono, txtEmail, txtFechaIngreso, txtAnioCuota;
    private JCheckBox chkCuotaPagada;
    // idDireccion no se pone por simplicidad

    private JButton btnGuardar, btnEditar, btnEliminar, btnLimpiar;
    private Miembro miembroSeleccionado = null;

    private final Color FONDO_VENTANA = new Color(255, 245, 248);
    private final Color FONDO_PANEL = new Color(250, 235, 240);
    private final Color COLOR_BOTON = new Color(180, 120, 140);
    private final Color COLOR_HEADER = new Color(160, 100, 120);

    public ABCCMiembro() {
        setTitle("👥 Gestión de Miembros");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(980, 720);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        getContentPane().setBackground(FONDO_VENTANA);

        Font fuenteBase = new Font("Segoe UI", Font.PLAIN, 15);
        Font fuenteBoton = new Font("Segoe UI", Font.BOLD, 16);

        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setBorder(BorderFactory.createTitledBorder("Datos del Miembro"));
        panelForm.setFont(fuenteBase);
        panelForm.setBackground(FONDO_PANEL);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        panelForm.add(new JLabel("Nombre *:"), gbc);
        gbc.gridx = 1;
        txtNombre = new JTextField(20);
        txtNombre.setFont(fuenteBase);
        panelForm.add(txtNombre, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panelForm.add(new JLabel("Apellido *:"), gbc);
        gbc.gridx = 1;
        txtApellido = new JTextField(20);
        txtApellido.setFont(fuenteBase);
        panelForm.add(txtApellido, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panelForm.add(new JLabel("Teléfono:"), gbc);
        gbc.gridx = 1;
        txtTelefono = new JTextField(15);
        txtTelefono.setFont(fuenteBase);
        panelForm.add(txtTelefono, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        panelForm.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        txtEmail = new JTextField(25);
        txtEmail.setFont(fuenteBase);
        panelForm.add(txtEmail, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        panelForm.add(new JLabel("Fecha Ingreso * (dd/MM/yyyy):"), gbc);
        gbc.gridx = 1;
        txtFechaIngreso = new JTextField(12);
        txtFechaIngreso.setFont(fuenteBase);
        panelForm.add(txtFechaIngreso, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        panelForm.add(new JLabel("Año Cuota *:"), gbc);
        gbc.gridx = 1;
        txtAnioCuota = new JTextField(6);
        txtAnioCuota.setFont(fuenteBase);
        panelForm.add(txtAnioCuota, gbc);

        gbc.gridx = 0; gbc.gridy = 6;
        panelForm.add(new JLabel("Cuota Pagada:"), gbc);
        gbc.gridx = 1;
        chkCuotaPagada = new JCheckBox("Sí");
        chkCuotaPagada.setFont(fuenteBase);
        panelForm.add(chkCuotaPagada, gbc);

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

        String[] columnas = {"ID", "Nombre", "Apellido", "Teléfono", "Email", "Ingreso", "Año Cuota", "Cuota Pagada"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tablaMiembros = new JTable(modeloTabla);
        tablaMiembros.setFont(fuenteBase);
        tablaMiembros.setRowHeight(26);
        tablaMiembros.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        tablaMiembros.setSelectionBackground(new Color(220, 190, 200));
        tablaMiembros.getTableHeader().setBackground(COLOR_HEADER);
        tablaMiembros.getTableHeader().setForeground(Color.WHITE);
        tablaMiembros.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));

        tablaMiembros.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int fila = tablaMiembros.getSelectedRow();
                if (fila >= 0) {
                    cargarMiembroSeleccionado(fila);
                }//if adentro
            }//if afuera
        });

        btnGuardar.addActionListener(e -> guardarMiembro());
        btnEditar.addActionListener(e -> editarMiembro());
        btnEliminar.addActionListener(e -> eliminarMiembro());
        btnLimpiar.addActionListener(e -> limpiarFormulario());

        add(panelForm, BorderLayout.NORTH);
        add(new JScrollPane(tablaMiembros), BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        cargarMiembros();
    }//ABCCMiembro

    private void estiloBoton(JButton btn, Font fuente, Color colorFondo) {
        btn.setFont(fuente);
        btn.setPreferredSize(new Dimension(120, 34));
        btn.setFocusable(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBackground(colorFondo);
        btn.setForeground(Color.WHITE);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
    }//estiloBoton

    private void guardarMiembro() {
        if (!validarCampos()) return;

        try {
            Miembro m = new Miembro();
            m.setNombre(txtNombre.getText().trim());
            m.setApellido(txtApellido.getText().trim());
            m.setTelefono(txtTelefono.getText().trim().isEmpty() ? null : txtTelefono.getText().trim());
            m.setEmail(txtEmail.getText().trim().isEmpty() ? null : txtEmail.getText().trim());
            m.setFechaIngreso(LocalDate.parse(txtFechaIngreso.getText().trim(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            m.setAnioCuota(Integer.parseInt(txtAnioCuota.getText().trim()));
            m.setCuotaPagada(chkCuotaPagada.isSelected());

            miembroDAO.insertar(m,
                () -> {
                    JOptionPane.showMessageDialog(this, "Miembro guardado con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    limpiarFormulario();
                    cargarMiembros();
                },
                () -> JOptionPane.showMessageDialog(this, "Error al guardar. Verifique los datos.", "Error", JOptionPane.ERROR_MESSAGE)
            );
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Formato de fecha inválido. Usa dd/MM/yyyy (ej: 15/11/2025)", "Error", JOptionPane.ERROR_MESSAGE);
            txtFechaIngreso.requestFocus();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El año de cuota debe ser un número entero.", "Error", JOptionPane.ERROR_MESSAGE);
            txtAnioCuota.requestFocus();
        }//catch
        
    }//guardarMiembro

    private void editarMiembro() {
        if (miembroSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un miembro de la tabla para editar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }//if

        if (!validarCampos()) return;

        try {
            miembroSeleccionado.setNombre(txtNombre.getText().trim());
            miembroSeleccionado.setApellido(txtApellido.getText().trim());
            miembroSeleccionado.setTelefono(txtTelefono.getText().trim().isEmpty() ? null : txtTelefono.getText().trim());
            miembroSeleccionado.setEmail(txtEmail.getText().trim().isEmpty() ? null : txtEmail.getText().trim());
            miembroSeleccionado.setFechaIngreso(LocalDate.parse(txtFechaIngreso.getText().trim(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            miembroSeleccionado.setAnioCuota(Integer.parseInt(txtAnioCuota.getText().trim()));
            miembroSeleccionado.setCuotaPagada(chkCuotaPagada.isSelected());

            miembroDAO.actualizar(miembroSeleccionado,
                () -> {
                    JOptionPane.showMessageDialog(this, "Miembro actualizado con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    limpiarFormulario();
                    cargarMiembros();
                },
                () -> JOptionPane.showMessageDialog(this, "Error al actualizar.", "Error", JOptionPane.ERROR_MESSAGE)
            );
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Formato de fecha inválido. Usa dd/MM/yyyy", "Error", JOptionPane.ERROR_MESSAGE);
            txtFechaIngreso.requestFocus();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El año de cuota debe ser un número entero.", "Error", JOptionPane.ERROR_MESSAGE);
            txtAnioCuota.requestFocus();
        }
    }//editarMiembro

    private void eliminarMiembro() {
        if (miembroSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un miembro para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }//if

        int confirm = JOptionPane.showConfirmDialog(this,
            "¿Eliminar a '" + miembroSeleccionado.getNombre() + " " + miembroSeleccionado.getApellido() + "'?",
            "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            miembroDAO.eliminar(miembroSeleccionado.getIdMiembro(),
                () -> {
                    JOptionPane.showMessageDialog(this, "Miembro eliminado con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    limpiarFormulario();
                    cargarMiembros();
                },
                () -> JOptionPane.showMessageDialog(this, "Error al eliminar. ¿Está asociado a una producción?", "Error", JOptionPane.ERROR_MESSAGE)
            );
        }//if
    }//eliminarMiembro

    private boolean validarCampos() {
        if (txtNombre.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre es obligatorio", "Error", JOptionPane.ERROR_MESSAGE);
            txtNombre.requestFocus();
            return false;
        }//if

        if (txtApellido.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El apellido es obligatorio", "Error", JOptionPane.ERROR_MESSAGE);
            txtApellido.requestFocus();
            return false;
        }//if

        if (txtFechaIngreso.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "La fecha de ingreso es obligatoria", "Error", JOptionPane.ERROR_MESSAGE);
            txtFechaIngreso.requestFocus();
            return false;
        }//if

        if (txtAnioCuota.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El año de cuota es obligatorio", "Error", JOptionPane.ERROR_MESSAGE);
            txtAnioCuota.requestFocus();
            return false;
        }//if

        try {
            int anio = Integer.parseInt(txtAnioCuota.getText().trim());
            int actual = LocalDate.now().getYear();
            if (anio < 2000 || anio > actual) {
                JOptionPane.showMessageDialog(this, "El año de cuota debe estar entre 2000 y " + actual, "Error", JOptionPane.ERROR_MESSAGE);
                txtAnioCuota.requestFocus();
                return false;
            }//if
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El año de cuota debe ser un número entero válido", "Error", JOptionPane.ERROR_MESSAGE);
            txtAnioCuota.requestFocus();
            return false;
        }//catch

        return true;
    }//validarCampos

    private void cargarMiembroSeleccionado(int fila) {
        Long id = (Long) modeloTabla.getValueAt(fila, 0);
        miembroSeleccionado = miembroDAO.buscarPorId(id);

        if (miembroSeleccionado != null) {
            txtNombre.setText(miembroSeleccionado.getNombre());
            txtApellido.setText(miembroSeleccionado.getApellido());
            txtTelefono.setText(miembroSeleccionado.getTelefono() == null ? "" : miembroSeleccionado.getTelefono());
            txtEmail.setText(miembroSeleccionado.getEmail() == null ? "" : miembroSeleccionado.getEmail());
            DateTimeFormatter f = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            txtFechaIngreso.setText(miembroSeleccionado.getFechaIngreso().format(f));
            txtAnioCuota.setText(String.valueOf(miembroSeleccionado.getAnioCuota()));
            chkCuotaPagada.setSelected(miembroSeleccionado.isCuotaPagada());
        }//if
    }//cargarMiembroSeleccionado

    private void limpiarFormulario() {
        txtNombre.setText("");
        txtApellido.setText("");
        txtTelefono.setText("");
        txtEmail.setText("");
        txtFechaIngreso.setText("");
        txtAnioCuota.setText("");
        chkCuotaPagada.setSelected(false);
        miembroSeleccionado = null;
        tablaMiembros.clearSelection();
    }//limpiarFormulario

    private void cargarMiembros() {
        modeloTabla.setRowCount(0);
        List<Miembro> miembros = miembroDAO.listarTodos();
        DateTimeFormatter f = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        for (Miembro m : miembros) {
            modeloTabla.addRow(new Object[]{
                m.getIdMiembro(),
                m.getNombre(),
                m.getApellido(),
                m.getTelefono() == null ? "" : m.getTelefono(),
                m.getEmail() == null ? "" : m.getEmail(),
                m.getFechaIngreso().format(f),
                m.getAnioCuota(),
                m.isCuotaPagada() ? "Sí" : "No"
            });
        }//for
    }//cargarMiembros

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }//catch

        SwingUtilities.invokeLater(() -> new ABCCMiembro().setVisible(true));
    }//main
}//ABCCMiembro