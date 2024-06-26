import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Vehiculo {

    private Conexion conexion;

    public Vehiculo() {
        conexion = new Conexion();
    }

    public void guardarVehiculo(String tipo, String matricula, String modelo, String fabricante, String fechaAdquisicion) {
        try (Connection conn = conexion.establecerConexion()) {
            String sql = "INSERT INTO vehiculo (tipo, matricula, modelo, fabricante, fecha_ad) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, tipo);
            pst.setString(2, matricula);
            pst.setString(3, modelo);
            pst.setString(4, fabricante);
            pst.setDate(5, java.sql.Date.valueOf(fechaAdquisicion));
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void modificarVehiculo(int id, String tipo, String matricula, String modelo, String fabricante, String fechaAdquisicion) {
        try (Connection conn = conexion.establecerConexion()) {
            String sql = "UPDATE vehiculo SET tipo = ?, matricula = ?, modelo = ?, fabricante = ?, fecha_ad = ? WHERE id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, tipo);
            pst.setString(2, matricula);
            pst.setString(3, modelo);
            pst.setString(4, fabricante);
            pst.setDate(5, java.sql.Date.valueOf(fechaAdquisicion));
            pst.setInt(6, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarVehiculo(int id) {
        try (Connection conn = conexion.establecerConexion()) {
            String sql = "DELETE FROM vehiculo WHERE id = ?";
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
            String sql = "SELECT * FROM vehiculo";
            Statement st = conn.createStatement();
            rs = st.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }
}
