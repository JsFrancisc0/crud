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

public class VentanaEmpleado extends JFrame {

    private JPanel panelDatos, panelTabla;
    private JTextField txtId, txtNombre, txtApellido, txtTelefono, txtEmail;
    private JButton btnGuardar, btnModificar, btnEliminar, btnPrestamos, btnVehiculos, btnLimpiar;
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private Empleado empleado;

    public VentanaEmpleado() {
        // Configuración de la ventana
        setTitle("Gestión de Empleados");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Inicialización de la clase Empleado
        empleado = new Empleado();

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

        JLabel lblNombre = new JLabel("Nombre:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        panelDatos.add(lblNombre, gbc);
        txtNombre = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 1;
        panelDatos.add(txtNombre, gbc);

        JLabel lblApellido = new JLabel("Apellido:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        panelDatos.add(lblApellido, gbc);
        txtApellido = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 2;
        panelDatos.add(txtApellido, gbc);

        JLabel lblTelefono = new JLabel("Teléfono:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        panelDatos.add(lblTelefono, gbc);
        txtTelefono = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 3;
        panelDatos.add(txtTelefono, gbc);

        JLabel lblEmail = new JLabel("Email:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        panelDatos.add(lblEmail, gbc);
        txtEmail = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 4;
        panelDatos.add(txtEmail, gbc);

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

        btnPrestamos = new JButton("Prestamos");
        gbc.gridx = 0;
        gbc.gridy = 7;
        panelDatos.add(btnPrestamos, gbc);
        btnVehiculos = new JButton("Vehículos");
        gbc.gridx = 1;
        gbc.gridy = 7;
        panelDatos.add(btnVehiculos, gbc);

        // Panel de tabla
        panelTabla = new JPanel();
        panelTabla.setLayout(new BorderLayout());

        // Modelo de tabla y JTable
        modeloTabla = new DefaultTableModel(new String[]{"ID", "Nombre", "Apellido", "Teléfono", "Email"}, 0) {
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
                guardarEmpleado();
            }
        });

        btnModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modificarEmpleado();
            }
        });

        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarEmpleado();
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
                    txtNombre.setText(model.getValueAt(selectedRow, 1).toString());
                    txtApellido.setText(model.getValueAt(selectedRow, 2).toString());
                    txtTelefono.setText(model.getValueAt(selectedRow, 3).toString());
                    txtEmail.setText(model.getValueAt(selectedRow, 4).toString());
                }
            }
        });

        // Cargar datos en la tabla
        cargarDatosTabla();
    }

    private void guardarEmpleado() {
        String nombre = txtNombre.getText();
        String apellido = txtApellido.getText();
        String telefono = txtTelefono.getText();
        String email = txtEmail.getText();

        empleado.guardarEmpleado(nombre, apellido, telefono, email);
        cargarDatosTabla();
        limpiarCampos();
    }

    private void modificarEmpleado() {
        int id = Integer.parseInt(txtId.getText());
        String nombre = txtNombre.getText();
        String apellido = txtApellido.getText();
        String telefono = txtTelefono.getText();
        String email = txtEmail.getText();

        empleado.modificarEmpleado(id, nombre, apellido, telefono, email);
        cargarDatosTabla();
        limpiarCampos();
    }

    private void eliminarEmpleado() {
        int id = Integer.parseInt(txtId.getText());
        empleado.eliminarEmpleado(id);
        cargarDatosTabla();
        limpiarCampos();
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        txtTelefono.setText("");
        txtEmail.setText("");
    }

    private void cargarDatosTabla() {
        modeloTabla.setRowCount(0); // Limpiar la tabla
        try {
            ResultSet rs = empleado.cargarDatosTabla();
            while (rs != null && rs.next()) {
                modeloTabla.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("telefono"),
                        rs.getString("email")
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
                new VentanaEmpleado().setVisible(true);
            }
        });
    }
}
