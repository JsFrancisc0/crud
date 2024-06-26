import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VentanaPrestamo extends JFrame {

    private JPanel panelDatos, panelTabla;
    private JTextField txtId, txtIdEmpleado, txtIdVehiculo, txtFechaIni, txtFechaDev;
    private JButton btnGuardar, btnModificar, btnEliminar, btnEmpleados, btnVehiculos, btnLimpiar;
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private Prestamo prestamo;

    public VentanaPrestamo() {
        // Configuración de la ventana
        setTitle("Gestión de Préstamos");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Inicialización de la clase Prestamo
        prestamo = new Prestamo();

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

        JLabel lblIdEmpleado = new JLabel("ID Empleado:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        panelDatos.add(lblIdEmpleado, gbc);
        txtIdEmpleado = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 1;
        panelDatos.add(txtIdEmpleado, gbc);

        JLabel lblIdVehiculo = new JLabel("ID Vehículo:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        panelDatos.add(lblIdVehiculo, gbc);
        txtIdVehiculo = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 2;
        panelDatos.add(txtIdVehiculo, gbc);

        JLabel lblFechaIni = new JLabel("Fecha Inicio:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        panelDatos.add(lblFechaIni, gbc);
        txtFechaIni = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 3;
        panelDatos.add(txtFechaIni, gbc);

        JLabel lblFechaDev = new JLabel("Fecha Devolución:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        panelDatos.add(lblFechaDev, gbc);
        txtFechaDev = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 4;
        panelDatos.add(txtFechaDev, gbc);

        // Botones
        btnGuardar = new JButton("Guardar");
        gbc.gridx = 0;
        gbc.gridy = 5;
        panelDatos.add(btnGuardar, gbc);
        btnModificar = new JButton("Modificar");
        gbc.gridx = 1;
        gbc.gridy = 5;
        panelDatos.add(btnModificar, gbc);
        btnEliminar = new JButton("Eliminar");
        gbc.gridx = 0;
        gbc.gridy = 6;
        panelDatos.add(btnEliminar, gbc);
        btnLimpiar = new JButton("Limpiar");
        gbc.gridx = 1;
        gbc.gridy = 6;
        panelDatos.add(btnLimpiar, gbc);

        btnEmpleados = new JButton("Empleados");
        gbc.gridx = 0;
        gbc.gridy = 7;
        panelDatos.add(btnEmpleados, gbc);
        btnVehiculos = new JButton("Vehículos");
        gbc.gridx = 1;
        gbc.gridy = 7;
        panelDatos.add(btnVehiculos, gbc);

        // Panel de tabla
        panelTabla = new JPanel();
        panelTabla.setLayout(new BorderLayout());

        // Modelo de tabla y JTable
        modeloTabla = new DefaultTableModel(new String[]{"ID", "ID Empleado", "ID Vehículo", "Fecha Inicio", "Fecha Devolución"}, 0) {
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
                guardarPrestamo();
            }
        });

        btnModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modificarPrestamo();
            }
        });

        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarPrestamo();
            }
        });

        btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarCampos();
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

        btnVehiculos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Abrir ventana de vehículos y cerrar la actual
                new VentanaVehiculo().setVisible(true);
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
                    txtIdEmpleado.setText(model.getValueAt(selectedRow, 1).toString());
                    txtIdVehiculo.setText(model.getValueAt(selectedRow, 2).toString());
                    txtFechaIni.setText(model.getValueAt(selectedRow, 3).toString());
                    txtFechaDev.setText(model.getValueAt(selectedRow, 4) != null ? model.getValueAt(selectedRow, 4).toString() : "");
                }
            }
        });

        // Cargar datos en la tabla
        cargarDatosTabla();
    }

    private void guardarPrestamo() {
        int idEmpleado = Integer.parseInt(txtIdEmpleado.getText());
        int idVehiculo = Integer.parseInt(txtIdVehiculo.getText());
        Date fechaIni = Date.valueOf(txtFechaIni.getText());
        Date fechaDev = txtFechaDev.getText().isEmpty() ? null : Date.valueOf(txtFechaDev.getText());

        prestamo.guardarPrestamo(idEmpleado, idVehiculo, fechaIni, fechaDev);
        cargarDatosTabla();
        limpiarCampos();
    }

    private void modificarPrestamo() {
        int id = Integer.parseInt(txtId.getText());
        int idEmpleado = Integer.parseInt(txtIdEmpleado.getText());
        int idVehiculo = Integer.parseInt(txtIdVehiculo.getText());
        Date fechaIni = Date.valueOf(txtFechaIni.getText());
        Date fechaDev = txtFechaDev.getText().isEmpty() ? null : Date.valueOf(txtFechaDev.getText());

        prestamo.modificarPrestamo(id, idEmpleado, idVehiculo, fechaIni, fechaDev);
        cargarDatosTabla();
        limpiarCampos();
    }

    private void eliminarPrestamo() {
        int id = Integer.parseInt(txtId.getText());

        prestamo.eliminarPrestamo(id);
        cargarDatosTabla();
        limpiarCampos();
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtIdEmpleado.setText("");
        txtIdVehiculo.setText("");
        txtFechaIni.setText("");
        txtFechaDev.setText("");
    }

    private void cargarDatosTabla() {
        ResultSet rs = prestamo.cargarDatosTabla();
        modeloTabla.setRowCount(0); // Limpiar la tabla
        try {
            while (rs.next()) {
                modeloTabla.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getInt("id_empleado"),
                        rs.getInt("id_vehiculo"),
                        rs.getDate("fecha_ini"),
                        rs.getDate("fecha_dev")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new VentanaPrestamo().setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
