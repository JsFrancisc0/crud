import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VentanaVehiculo extends JFrame {

    private JPanel panelDatos, panelTabla;
    private JTextField txtId, txtTipo, txtMatricula, txtModelo, txtFabricante, txtFechaAdquisicion;
    private JButton btnGuardar, btnModificar, btnEliminar, btnPrestamos, btnEmpleados, btnLimpiar;
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private Vehiculo vehiculo;

    public VentanaVehiculo() {
        // Configuración de la ventana
        setTitle("Gestión de Vehículos");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Inicialización de la clase Vehiculo
        vehiculo = new Vehiculo();

        // Panel de datos
        panelDatos = new JPanel();
        panelDatos.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Campos de texto y etiquetas
        JLabel lblId = new JLabel("ID:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelDatos.add(lblId, gbc);
        txtId = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 0;
        panelDatos.add(txtId, gbc);

        JLabel lblTipo = new JLabel("Tipo:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        panelDatos.add(lblTipo, gbc);
        txtTipo = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 1;
        panelDatos.add(txtTipo, gbc);

        JLabel lblMatricula = new JLabel("Matricula:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        panelDatos.add(lblMatricula, gbc);
        txtMatricula = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 2;
        panelDatos.add(txtMatricula, gbc);

        JLabel lblModelo = new JLabel("Modelo:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        panelDatos.add(lblModelo, gbc);
        txtModelo = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 3;
        panelDatos.add(txtModelo, gbc);

        JLabel lblFabricante = new JLabel("Fabricante:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        panelDatos.add(lblFabricante, gbc);
        txtFabricante = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 4;
        panelDatos.add(txtFabricante, gbc);

        JLabel lblFechaAdquisicion = new JLabel("Fecha Adquisición:");
        gbc.gridx = 0;
        gbc.gridy = 5;
        panelDatos.add(lblFechaAdquisicion, gbc);
        txtFechaAdquisicion = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 5;
        panelDatos.add(txtFechaAdquisicion, gbc);

        // Botones
        btnGuardar = new JButton("Guardar");
        gbc.gridx = 0;
        gbc.gridy = 6;
        panelDatos.add(btnGuardar, gbc);
        btnModificar = new JButton("Modificar");
        gbc.gridx = 1;
        gbc.gridy = 6;
        panelDatos.add(btnModificar, gbc);
        btnEliminar = new JButton("Eliminar");
        gbc.gridx = 0;
        gbc.gridy = 7;
        panelDatos.add(btnEliminar, gbc);
        btnLimpiar = new JButton("Limpiar");
        gbc.gridx = 1;
        gbc.gridy = 7;
        panelDatos.add(btnLimpiar, gbc);

        btnPrestamos = new JButton("Prestamos");
        gbc.gridx = 0;
        gbc.gridy = 8;
        panelDatos.add(btnPrestamos, gbc);
        btnEmpleados = new JButton("Empleados");
        gbc.gridx = 1;
        gbc.gridy = 8;
        panelDatos.add(btnEmpleados, gbc);

        // Panel de tabla
        panelTabla = new JPanel();
        panelTabla.setLayout(new BorderLayout());

        // Modelo de tabla y JTable
        modeloTabla = new DefaultTableModel(new String[]{"ID", "Tipo", "Matricula", "Modelo", "Fabricante", "Fecha Adquisición"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Deshabilitar edición de celdas
            }
        };
        tabla = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tabla);
        panelTabla.add(scrollPane, BorderLayout.CENTER);

        // Añadir paneles a la ventana
        add(panelDatos, BorderLayout.WEST);
        add(panelTabla, BorderLayout.CENTER);

        // Agregar listeners a los botones
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarVehiculo();
            }
        });

        btnModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modificarVehiculo();
            }
        });

        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarVehiculo();
            }
        });

        btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarCampos();
            }
        });

        btnPrestamos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Abrir ventana de préstamos y cerrar la actual
                new VentanaPrestamo().setVisible(true);
                dispose();
            }
        });

        btnEmpleados.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Abrir ventana de empleados y cerrar la actual
                new VentanaEmpleado().setVisible(true);
                dispose();
            }
        });

        // Agregar listener para la tabla
        tabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = tabla.getSelectedRow();
                if (selectedRow != -1) {
                    TableModel model = tabla.getModel();
                    txtId.setText(model.getValueAt(selectedRow, 0).toString());
                    txtTipo.setText(model.getValueAt(selectedRow, 1).toString());
                    txtMatricula.setText(model.getValueAt(selectedRow, 2).toString());
                    txtModelo.setText(model.getValueAt(selectedRow, 3).toString());
                    txtFabricante.setText(model.getValueAt(selectedRow, 4).toString());
                    txtFechaAdquisicion.setText(model.getValueAt(selectedRow, 5).toString());
                }
            }
        });

        // Cargar datos iniciales en la tabla
        cargarDatosTabla();
    }

    private void guardarVehiculo() {
        String tipo = txtTipo.getText();
        String matricula = txtMatricula.getText();
        String modelo = txtModelo.getText();
        String fabricante = txtFabricante.getText();
        String fechaAdquisicion = txtFechaAdquisicion.getText();
        vehiculo.guardarVehiculo(tipo, matricula, modelo, fabricante, fechaAdquisicion);
        cargarDatosTabla();
        limpiarCampos();
    }

    private void modificarVehiculo() {
        int id = Integer.parseInt(txtId.getText());
        String tipo = txtTipo.getText();
        String matricula = txtMatricula.getText();
        String modelo = txtModelo.getText();
        String fabricante = txtFabricante.getText();
        String fechaAdquisicion = txtFechaAdquisicion.getText();
        vehiculo.modificarVehiculo(id, tipo, matricula, modelo, fabricante, fechaAdquisicion);
        cargarDatosTabla();
        limpiarCampos();
    }

    private void eliminarVehiculo() {
        int id = Integer.parseInt(txtId.getText());
        vehiculo.eliminarVehiculo(id);
        cargarDatosTabla();
        limpiarCampos();
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtTipo.setText("");
        txtMatricula.setText("");
        txtModelo.setText("");
        txtFabricante.setText("");
        txtFechaAdquisicion.setText("");
    }

    private void cargarDatosTabla() {
        modeloTabla.setRowCount(0); // Limpiar la tabla
        try {
            ResultSet rs = vehiculo.cargarDatosTabla();
            while (rs != null && rs.next()) {
                modeloTabla.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("tipo"),
                        rs.getString("matricula"),
                        rs.getString("modelo"),
                        rs.getString("fabricante"),
                        rs.getDate("fecha_ad")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new VentanaVehiculo().setVisible(true);
            }
        });
    }
}

