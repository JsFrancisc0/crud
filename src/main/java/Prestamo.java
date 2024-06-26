import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Prestamo {

    private Conexion conexion;

    public Prestamo() {
        conexion = new Conexion();
    }

    public void guardarPrestamo(int idEmpleado, int idVehiculo, Date fechaIni, Date fechaDev) {
        try (Connection conn = conexion.establecerConexion()) {
            conn.setAutoCommit(false);

            // Utilizar SELECT FOR UPDATE para bloquear la fila de vehículo
            String lockSql = "SELECT * FROM vehiculo WHERE id = ? FOR UPDATE";
            try (PreparedStatement lockPst = conn.prepareStatement(lockSql)) {
                lockPst.setInt(1, idVehiculo);
                lockPst.executeQuery();
            }

            String sql = "INSERT INTO prestamo (id_empleado, id_vehiculo, fecha_ini, fecha_dev) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pst = conn.prepareStatement(sql)) {
                pst.setInt(1, idEmpleado);
                pst.setInt(2, idVehiculo);
                pst.setDate(3, fechaIni);
                pst.setDate(4, fechaDev);
                pst.executeUpdate();
            }

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void modificarPrestamo(int id, int idEmpleado, int idVehiculo, Date fechaIni, Date fechaDev) {
        try (Connection conn = conexion.establecerConexion()) {
            conn.setAutoCommit(false);

            // Utilizar SELECT FOR UPDATE para bloquear la fila del préstamo
            String lockSql = "SELECT * FROM prestamo WHERE id = ? FOR UPDATE";
            try (PreparedStatement lockPst = conn.prepareStatement(lockSql)) {
                lockPst.setInt(1, id);
                lockPst.executeQuery();
            }

            String sql = "UPDATE prestamo SET id_empleado = ?, id_vehiculo = ?, fecha_ini = ?, fecha_dev = ? WHERE id = ?";
            try (PreparedStatement pst = conn.prepareStatement(sql)) {
                pst.setInt(1, idEmpleado);
                pst.setInt(2, idVehiculo);
                pst.setDate(3, fechaIni);
                pst.setDate(4, fechaDev);
                pst.setInt(5, id);
                pst.executeUpdate();
            }

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarPrestamo(int id) {
        try (Connection conn = conexion.establecerConexion()) {
            conn.setAutoCommit(false);

            // Utilizar SELECT FOR UPDATE para bloquear la fila del préstamo
            String lockSql = "SELECT * FROM prestamo WHERE id = ? FOR UPDATE";
            try (PreparedStatement lockPst = conn.prepareStatement(lockSql)) {
                lockPst.setInt(1, id);
                lockPst.executeQuery();
            }

            String sql = "DELETE FROM prestamo WHERE id = ?";
            try (PreparedStatement pst = conn.prepareStatement(sql)) {
                pst.setInt(1, id);
                pst.executeUpdate();
            }

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet cargarDatosTabla() {
        ResultSet rs = null;
        try (Connection conn = conexion.establecerConexion()) {
            String sql = "SELECT * FROM prestamo";
            Statement st = conn.createStatement();
            rs = st.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }
}


