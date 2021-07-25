package Services;

import Models.CashFlow;
import Models.Category;
import Models.Registro;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CashFlowServices {

	String url = "jdbc:mysql://localhost:3306/ventas?zeroDateTimeBehavior=convertToNull&serverTimezone=UTC";
	String user = "newuser";
	String password = "yael2712";
    private Connection conn;

    public static String Clasificacion1 = "Entrada";
    public static String Clasificacion2 = "Salida";

    public ObservableList<String> getRoles() {
        ObservableList<String> roles = FXCollections.observableArrayList();
        roles.add("Administrador");
        roles.add("Director de Finanzas");
        return roles;
    }

    public ObservableList<String> getCategoriesClasification() {
        ObservableList<String> clasifications = FXCollections.observableArrayList();
        clasifications.add("Entrada");
        clasifications.add("Salida");
        return clasifications;
    }

    public void createCategory(Category categoria) {
        String query = "INSERT INTO categoria" + " (clasificacion, categoria, sub_categoria) VALUES" + " (?, ?, ?);";
        try {
            conn = DriverManager.getConnection(this.url, this.user, this.password);
            PreparedStatement req = conn.prepareStatement(query);
            req.setString(1, categoria.getClasificacion());
            req.setString(2, categoria.getCategoria());
            req.setString(3, categoria.getSub_categoria());
            req.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    public ObservableList<Category> getCategories() {

        ObservableList<Category> categorias = FXCollections.observableArrayList();
        String query = "SELECT * FROM categoria ORDER BY id ASC;";
        Category newCat;
        try {
            conn = getConnection();
            PreparedStatement request = conn.prepareStatement(query);
            ResultSet response = request.executeQuery();
            while (response.next()) {
                int id = response.getInt("id");
                String clasificacion = response.getString("clasificacion");
                String categoria = response.getString("categoria");
                String subcat = response.getString("sub_categoria");
                newCat = new Category(id, clasificacion, categoria, subcat);
                categorias.add(newCat);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return categorias;
    }

    public Category validateCategory(String cat, String sub_cat){
        String query = "Select * From categoria WHERE categoria ='" + cat + "' AND sub_categoria='" + sub_cat + "';";
        Category current = new Category();
        try {
            conn = getConnection();
            PreparedStatement request = conn.prepareStatement(query);
            ResultSet response = request.executeQuery();
            while (response.next()) {
                int id = response.getInt("id");
                String clasificacion = response.getString("clasificacion");
                String categoria = response.getString("categoria");
                String subcat = response.getString("sub_categoria");
                current = new Category(id, clasificacion, categoria, subcat);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return current;
    }
    
    
    
    public void patchClasif(Category categoria) {
        String query = "UPDATE categoria set clasificacion = '" + categoria.getClasificacion() + ".' WHERE id = " + categoria.getiD() + ";";
        try {
            conn = getConnection();
            PreparedStatement req = conn.prepareStatement(query);
            req.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    public void patchCat(Category categoria) {
        String query = "UPDATE categoria set categoria = '" + categoria.getCategoria() + "' WHERE id = " + categoria.getiD() + ";";
        try {
            conn = getConnection();
            System.out.println(query);
            PreparedStatement req = conn.prepareStatement(query);
            req.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    public void patchSubCat(Category categoria) {
        String query = "UPDATE categoria set sub_categoria = '" + categoria.getSub_categoria() + "' WHERE id = " + categoria.getiD() + ";";
        try {
            conn = getConnection();
            PreparedStatement req = conn.prepareStatement(query);
            req.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    
    
    //----------------------------------CashFlow Stage Services--------------------------------------
    
    public ObservableList<String> getCategoriesCatalog(String filter){
        ObservableList<String> catalogo = FXCollections.observableArrayList();
        String query = "SELECT categoria, sub_categoria FROM categoria WHERE clasificacion = '" + filter + "' ORDER BY categoria ASC;";
        try {
            conn = getConnection();
            PreparedStatement request = conn.prepareStatement(query);
            ResultSet response = request.executeQuery();
            while (response.next()) {
                String categoria = response.getString("categoria");
                String subcat = response.getString("sub_categoria");
                String item = categoria + " - " + subcat;
                catalogo.add(item);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return catalogo;
    }
    
    public void createCashFlow(CashFlow item){
        String query = "INSERT INTO flujo" + " (fecha, descripcion, monto, categoria) VALUES" + " (?, ?, ?, ?);";
        try {
            conn = getConnection();
            PreparedStatement req = conn.prepareStatement(query);
            req.setString(1, item.getFecha());
            req.setString(2, item.getDescripcion());
            req.setDouble(3, item.getMonto());
            req.setString(4, item.getCategoria());
            req.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }
    
    public ObservableList<CashFlow> getFlows(){        
        ObservableList<CashFlow> flujos = FXCollections.observableArrayList();
        String query = "SELECT * FROM flujo ORDER BY id ASC;";
        CashFlow current;
        try {
            conn = getConnection();
            PreparedStatement request = conn.prepareStatement(query);
            ResultSet response = request.executeQuery();
            while (response.next()) {
                int id = response.getInt("id");
                String fecha = response.getString("fecha");
                String descripcion = response.getString("descripcion");
                int monto = response.getInt("monto");
                String motivo = response.getString("categoria");
                current = new CashFlow(id, fecha, descripcion, monto, motivo);
                flujos.add(current);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return flujos;
    }
    
    
    //*---------------------------Money Index Services------------------------------**
    public void createRegister(Registro nuevo) {
        String query = "INSERT INTO registro" + " (numsemana, mes, razon, monto, tipo) VALUES" + " (?, ?, ?, ?, ?);";
        try {
            conn = getConnection();
            PreparedStatement req = conn.prepareStatement(query);
            req.setInt(1, nuevo.getNoSemana());
            req.setString(2, nuevo.getMes());
            req.setString(3, nuevo.getRazon());
            req.setDouble(4, nuevo.getMonto());
            req.setString(5, nuevo.getTipo());
            req.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }
    
    public ObservableList<Integer> getweeks() {
        ObservableList<Integer> semanas = FXCollections.observableArrayList();
        semanas.add(1);
        semanas.add(2);
        semanas.add(3);
        semanas.add(4);
        semanas.add(5);
        return semanas;
    }
   
    public Connection getConnection() throws SQLException {
        return conn = DriverManager.getConnection(this.url, this.user, this.password);
    }
    
    public static void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}
