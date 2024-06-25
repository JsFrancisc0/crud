import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VentanaPrestamo extends JFrame {

    private JPanel panelDatos, panelTabla;
    private JTextField txtId, txtIdEmpleado, txtIdVehiculo, txtFechaIni, txtFechaDev;
    private JButton btnGuardar, btnModificar, btnEliminar, btnEmpleados, btnVehiculos;
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private Prestamo prestamo;

    public VentanaPrestamo() {
        // Configuración de la ventana
        setTitle("Gestión de Préstamos");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Inicialización de la clase Prestamo
        prestamo = new Prestamo();

        // Panel de datos
        panelDatos = new JPanel();
        panelDatos.setLayout(new GridLayout(7, 2, 5, 5));

        // Campos de texto y etiquetas
        panelDatos.add(new JLabel("ID:"));
        txtId = new JTextField();
        panelDatos.add(txtId);

        panelDatos.add(new JLabel("ID Empleado:"));
        txtIdEmpleado = new JTextField();
        panelDatos.add(txtIdEmpleado);

        panelDatos.add(new JLabel("ID Vehículo:"));
        txtIdVehiculo = new JTextField();
        panelDatos.add(txtIdVehiculo);

        panelDatos.add(new JLabel("Fecha Inicio:"));
        txtFechaIni = new JTextField();
        panelDatos.add(txtFechaIni);

        panelDatos.add(new JLabel("Fecha Devolución:"));
        txtFechaDev = new JTextField();
        panelDatos.add(txtFechaDev);

        // Botones
        btnGuardar = new JButton("Guardar");
        btnModificar = new JButton("Modificar");
        btnEliminar = new JButton("Eliminar");

        panelDatos.add(btnGuardar);
        panelDatos.add(btnModificar);
        panelDatos.add(btnEliminar);

        btnEmpleados = new JButton("Empleados");
        btnVehiculos = new JButton("Vehículos");

        panelDatos.add(btnEmpleados);
        panelDatos.add(btnVehiculos);

        // Panel de tabla
        panelTabla = new JPanel();
        panelTabla.setLayout(new BorderLayout());

        // Modelo de tabla y JTable
        modeloTabla = new DefaultTableModel(new String[]{"ID", "ID Empleado", "ID Vehículo", "Fecha Inicio", "Fecha Devolución"}, 0);
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

        btnEmpleados.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Abrir ventana de empleados
                //new VentanaEmpleado().setVisible(true);
            }
        });

        btnVehiculos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Abrir ventana de vehículos
                //new VentanaVehiculo().setVisible(true);
            }
        });

        // Cargar datos en la tabla
        cargarDatosTabla();
    }

    private void guardarPrestamo() {
        int idEmpleado = Integer.parseInt(txtIdEmpleado.getText());
        int idVehiculo = Integer.parseInt(txtIdVehiculo.getText());
        Date fechaIni = Date.valueOf(txtFechaIni.getText());
        Date fechaDev = Date.valueOf(txtFechaDev.getText());

        prestamo.guardarPrestamo(idEmpleado, idVehiculo, fechaIni, fechaDev);
        cargarDatosTabla();
    }

    private void modificarPrestamo() {
        int id = Integer.parseInt(txtId.getText());
        int idEmpleado = Integer.parseInt(txtIdEmpleado.getText());
        int idVehiculo = Integer.parseInt(txtIdVehiculo.getText());
        Date fechaIni = Date.valueOf(txtFechaIni.getText());
        Date fechaDev = Date.valueOf(txtFechaDev.getText());

        prestamo.modificarPrestamo(id, idEmpleado, idVehiculo, fechaIni, fechaDev);
        cargarDatosTabla();
    }

    private void eliminarPrestamo() {
        int id = Integer.parseInt(txtId.getText());

        prestamo.eliminarPrestamo(id);
        cargarDatosTabla();
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
}

