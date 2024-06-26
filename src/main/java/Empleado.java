import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Empleado {

    private Conexion conexion;

    public Empleado() {
        conexion = new Conexion();
    }

    public void guardarEmpleado(String nombre, String apellido, String telefono, String email) {
        try (Connection conn = conexion.establecerConexion()) {
            conn.setAutoCommit(false);

            // No es necesario un bloqueo explícito para un INSERT
            String sql = "INSERT INTO empleado (nombre, apellido, telefono, email) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pst = conn.prepareStatement(sql)) {
                pst.setString(1, nombre);
                pst.setString(2, apellido);
                pst.setString(3, telefono);
                pst.setString(4, email);
                pst.executeUpdate();
            }

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void modificarEmpleado(int id, String nombre, String apellido, String telefono, String email) {
        try (Connection conn = conexion.establecerConexion()) {
            conn.setAutoCommit(false);

            // Utilizar SELECT FOR UPDATE para bloquear la fila del empleado
            String lockSql = "SELECT * FROM empleado WHERE id = ? FOR UPDATE";
            try (PreparedStatement lockPst = conn.prepareStatement(lockSql)) {
                lockPst.setInt(1, id);
                lockPst.executeQuery();
            }

            String sql = "UPDATE empleado SET nombre = ?, apellido = ?, telefono = ?, email = ? WHERE id = ?";
            try (PreparedStatement pst = conn.prepareStatement(sql)) {
                pst.setString(1, nombre);
                pst.setString(2, apellido);
                pst.setString(3, telefono);
                pst.setString(4, email);
                pst.setInt(5, id);
                pst.executeUpdate();
            }

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarEmpleado(int id) {
        try (Connection conn = conexion.establecerConexion()) {
            conn.setAutoCommit(false);

            // Utilizar SELECT FOR UPDATE para bloquear la fila del empleado
            String lockSql = "SELECT * FROM empleado WHERE id = ? FOR UPDATE";
            try (PreparedStatement lockPst = conn.prepareStatement(lockSql)) {
                lockPst.setInt(1, id);
                lockPst.executeQuery();
            }

            String sql = "DELETE FROM empleado WHERE id = ?";
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
            String sql = "SELECT * FROM empleado";
            Statement st = conn.createStatement();
            rs = st.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }
}
