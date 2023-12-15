package SistemaInventario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {
    private Connection conexion;

    public ProductoDAO() {
        try {
            this.conexion = ConexionDB.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void agregarProducto(Producto producto) {
        String query = "INSERT INTO productos (nombre, cantidad, precio) VALUES (?, ?, ?)";

        try (PreparedStatement pstmt = conexion.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, producto.getNombre());
            pstmt.setInt(2, producto.getCantidad());
            pstmt.setDouble(3, producto.getPrecio());

            int filasAfectadas = pstmt.executeUpdate();

            if (filasAfectadas > 0) {
                ResultSet generatedKeys = pstmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int idGenerado = generatedKeys.getInt(1);
                    producto.setId(idGenerado);
                    System.out.println("Producto agregado a la base de datos con ID: " + idGenerado);
                }
            } else {
                System.err.println("No se pudo agregar el producto a la base de datos.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actualizarProducto(Producto producto) {
        String query = "UPDATE productos SET nombre = ?, cantidad = ?, precio = ? WHERE id = ?";

        try (PreparedStatement pstmt = conexion.prepareStatement(query)) {
            pstmt.setString(1, producto.getNombre());
            pstmt.setInt(2, producto.getCantidad());
            pstmt.setDouble(3, producto.getPrecio());
            pstmt.setInt(4, producto.getId());

            int filasAfectadas = pstmt.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("Producto actualizado en la base de datos.");
            } else {
                System.err.println("No se pudo actualizar el producto en la base de datos.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarProducto(int id) {
        String query = "DELETE FROM productos WHERE id = ?";

        try (PreparedStatement pstmt = conexion.prepareStatement(query)) {
            pstmt.setInt(1, id);

            int filasAfectadas = pstmt.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("Producto eliminado de la base de datos.");
            } else {
                System.err.println("No se pudo eliminar el producto de la base de datos.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Producto> obtenerTodosLosProductos() {
        List<Producto> productos = new ArrayList<>();
        String query = "SELECT * FROM productos";

        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Producto producto = new Producto();
                producto.setId(rs.getInt("id"));
                producto.setNombre(rs.getString("nombre"));
                producto.setCantidad(rs.getInt("cantidad"));
                producto.setPrecio(rs.getDouble("precio"));

                productos.add(producto);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productos;
    }

    public Producto obtenerProductoPorId(int id) {
        String query = "SELECT * FROM productos WHERE id = ?";
        Producto producto = null;

        try (PreparedStatement pstmt = conexion.prepareStatement(query)) {
            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    producto = new Producto();
                    producto.setId(rs.getInt("id"));
                    producto.setNombre(rs.getString("nombre"));
                    producto.setCantidad(rs.getInt("cantidad"));
                    producto.setPrecio(rs.getDouble("precio"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return producto;
    }
}
