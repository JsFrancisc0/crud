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
            String sql = "INSERT INTO prestamo (id_empleado, id_vehiculo, fecha_ini, fecha_dev) VALUES (?, ?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, idEmpleado);
            pst.setInt(2, idVehiculo);
            pst.setDate(3, fechaIni);
            pst.setDate(4, fechaDev);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void modificarPrestamo(int id, int idEmpleado, int idVehiculo, Date fechaIni, Date fechaDev) {
        try (Connection conn = conexion.establecerConexion()) {
            String sql = "UPDATE prestamo SET id_empleado = ?, id_vehiculo = ?, fecha_ini = ?, fecha_dev = ? WHERE id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, idEmpleado);
            pst.setInt(2, idVehiculo);
            pst.setDate(3, fechaIni);
            pst.setDate(4, fechaDev);
            pst.setInt(5, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarPrestamo(int id) {
        try (Connection conn = conexion.establecerConexion()) {
            String sql = "DELETE FROM prestamo WHERE id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, id);
            pst.executeUpdate();
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

