import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {

    Connection conectar = null;

    String user = "postgres";
    String pass = "123098";
    String db = "garaje";
    String ip = "localhost";
    String port = "5432";

    String cadena = "jdbc:postgresql://"+ip+":"+port+"/"+db;

    public Connection establecerConexion(){
        try {
            Class.forName("org.postgresql.Driver");

            conectar = DriverManager.getConnection(cadena,user,pass);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,e.toString(),"ERROR",JOptionPane.ERROR_MESSAGE);
        }

        return conectar;
    }
}
