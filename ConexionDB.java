package conexiondb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConexionDB {
    static Connection conexion;
    
    public static void main(String[] args) {
        establecerConexion();
        //recuperarInfo();
        //escribir(); 
        //leerDatos();
        escribirDatos();
    }

    private static void establecerConexion() {
        String url = "jdbc:mysql://localhost:3306/ejemplojava?useSSL=false";
        String user = "root";
        String pass = "12345";
        
        try {
            conexion = DriverManager.getConnection(url, user, pass);
            if(conexion!=null){
                System.out.println("conexión satisfactoria");
            } else {
                System.out.println("conexión fallida");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConexionDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static void leerDatos(){
        ResultSet resultado;        
        String query = "SELECT * FROM Inventario";
        
        try {
            Statement sentenciador = conexion.createStatement();
            resultado = sentenciador.executeQuery(query);
            int acumulado = 0, cont=0;
            while(resultado.next()){
                String version = resultado.getString("nombre");                
                System.out.println("Si devolvió datos!: " + version);  
                acumulado += resultado.getInt("precio");
                cont++;
            }
            System.out.println("El acumulado es: " + acumulado  + "; promedio es: " + acumulado/cont);
        } catch (SQLException ex) {
            Logger.getLogger(ConexionDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static void recuperarInfo(){
        ResultSet rs;
        try {
            Statement st = conexion.createStatement();
            String query = ("SELECT * FROM repetition;");
            
            rs = st.executeQuery(query);
            
            int prob = 0;
            while(rs.next()) {
               String version = rs.getString("version");
               String number = rs.getString("number");
               System.out.println("Campos: " + version + " y " + number);
               prob += Integer.parseInt(rs.getString("probability"));
            }
            System.out.println("Probabilidad acumulada: " + prob);
            //rs.getArray("number");
        } catch (Exception e) {
           e.printStackTrace();
        }
    }
    
   
    private static void escribirDatos() {
        try {
            String insertar = "INSERT INTO Inventario (nombre, precio, descr) VALUES ('VolksWagen', 'sfgsdfg', 'Probando ando');";
            Statement sentenciador = conexion.createStatement();
            if(sentenciador.executeUpdate(insertar)==1)
                System.out.println("Insercción correcta");
            else System.out.println("Insercción fallida");
        } catch (SQLException ex) {
            Logger.getLogger(   ConexionDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
