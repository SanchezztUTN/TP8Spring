package isi.deso.tp.grupo8;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class ItemsMenuMemory implements ItemMenuDAO {
    private final Connection connection;

    public ItemsMenuMemory() throws SQLException {
        this.connection = ConexionDB.getConnection();
    }

    @Override
    public String crearItem(ItemMenu item, Categoria categoria) {
        String sqlItemMenu = "INSERT INTO itemmenu (nombre, descripcion, precio, id_categoria) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmtItemMenu = connection.prepareStatement(sqlItemMenu, PreparedStatement.RETURN_GENERATED_KEYS)) {
            connection.setAutoCommit(false); // Start transaction

            stmtItemMenu.setString(1, item.getNombre());
            stmtItemMenu.setString(2, item.getDesc());
            stmtItemMenu.setDouble(3, item.getPrecio());
            stmtItemMenu.setLong(4, categoria.getId_categoria());
            stmtItemMenu.executeUpdate();

            try (ResultSet generatedKeys = stmtItemMenu.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    long itemId = generatedKeys.getLong(1);
                    item.setId(itemId);
                    item.setCategoria(categoria); // Set the category

                    if (item instanceof Bebida) {
                        Bebida bebida = (Bebida) item;
                        String sqlBebida = "INSERT INTO bebida (nombre, descripcion, precio, id_categoria, graduacionAlcoholica, volumen, id_itemMenu) VALUES (?, ?, ?, ?, ?, ?, ?)";
                        try (PreparedStatement stmtBebida = connection.prepareStatement(sqlBebida)) {
                            stmtBebida.setString(1, bebida.getNombre());
                            stmtBebida.setString(2, bebida.getDesc());
                            stmtBebida.setDouble(3, bebida.getPrecio());
                            stmtBebida.setLong(4, categoria.getId_categoria());
                            stmtBebida.setDouble(5, bebida.getGradA());
                            stmtBebida.setDouble(6, bebida.getVol());
                            stmtBebida.setLong(7, itemId);
                            stmtBebida.executeUpdate();
                        }
                        connection.commit(); // Commit transaction
                        return "Bebida Creada";
                    } else if (item instanceof Plato) {
                        Plato plato = (Plato) item;
                        String sqlPlato = "INSERT INTO plato (nombre, descripcion, precio, id_categoria, calorias, aptoCeliaco, aptoVegetariano, peso, id_itemMenu) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                        try (PreparedStatement stmtPlato = connection.prepareStatement(sqlPlato)) {
                            stmtPlato.setString(1, plato.getNombre());
                            stmtPlato.setString(2, plato.getDesc());
                            stmtPlato.setDouble(3, plato.getPrecio());
                            stmtPlato.setLong(4, categoria.getId_categoria());
                            stmtPlato.setDouble(5, plato.getCalorias());
                            stmtPlato.setBoolean(6, plato.getAptoC());
                            stmtPlato.setBoolean(7, plato.getAptoV());
                            stmtPlato.setDouble(8, plato.getPeso());
                            stmtPlato.setLong(9, itemId);
                            stmtPlato.executeUpdate();
                        }
                        connection.commit(); // Commit transaction
                        return "Plato Creado";
                    }
                }
            }
        } catch (SQLException e) {
            try {
                connection.rollback(); // Rollback transaction on error
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                connection.setAutoCommit(true); // Reset auto-commit mode
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return "Error al crear el item";
    }

    @Override
    public ItemMenu buscarItem(long id) {
        String sqlItemMenu = "SELECT * FROM itemmenu WHERE id_itemMenu = ?";
        try (PreparedStatement stmtItemMenu = connection.prepareStatement(sqlItemMenu)) {
            stmtItemMenu.setLong(1, id);
            try (ResultSet rsItemMenu = stmtItemMenu.executeQuery()) {
                if (rsItemMenu.next()) {
                    String nombre = rsItemMenu.getString("nombre");
                    String descripcion = rsItemMenu.getString("descripcion");
                    double precio = rsItemMenu.getDouble("precio");
                    long idCategoria = rsItemMenu.getLong("id_categoria");

                    Categoria categoria = new Categoria(); // Assuming Categoria has a default constructor
                    categoria.setId_categoria(idCategoria);

                    String sqlBebida = "SELECT * FROM bebida WHERE id_itemMenu = ?";
                    try (PreparedStatement stmtBebida = connection.prepareStatement(sqlBebida)) {
                        stmtBebida.setLong(1, id);
                        try (ResultSet rsBebida = stmtBebida.executeQuery()) {
                            if (rsBebida.next()) {
                                Bebida bebida = new Bebida(rsBebida.getDouble("graduacionAlcoholica"), rsBebida.getDouble("volumen"), nombre, precio, descripcion);
                                bebida.setId(id);
                                bebida.setCategoria(categoria); // Set the category
                                return bebida;
                            }
                        }
                    }

                    String sqlPlato = "SELECT * FROM plato WHERE id_itemMenu = ?";
                    try (PreparedStatement stmtPlato = connection.prepareStatement(sqlPlato)) {
                        stmtPlato.setLong(1, id);
                        try (ResultSet rsPlato = stmtPlato.executeQuery()) {
                            if (rsPlato.next()) {
                                Plato plato = new Plato(rsPlato.getDouble("calorias"), rsPlato.getBoolean("aptoCeliaco"), rsPlato.getBoolean("aptoVegetariano"), nombre, precio, descripcion, rsPlato.getDouble("peso"));
                                plato.setId(id);
                                plato.setCategoria(categoria); // Set the category
                                return plato;
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public void actualizarItem(ItemMenu item, Categoria categoria) {
        String sqlItemMenu = "UPDATE itemmenu SET nombre = ?, descripcion = ?, precio = ?, id_categoria = ? WHERE id_itemMenu = ?";
        try (PreparedStatement stmtItemMenu = connection.prepareStatement(sqlItemMenu)) {
            connection.setAutoCommit(false); // Start transaction

            stmtItemMenu.setString(1, item.getNombre());
            stmtItemMenu.setString(2, item.getDesc());
            stmtItemMenu.setDouble(3, item.getPrecio());
            stmtItemMenu.setLong(4, categoria.getId_categoria());
            stmtItemMenu.setLong(5, item.getId());
            stmtItemMenu.executeUpdate();

            if (item instanceof Bebida) {
                Bebida bebida = (Bebida) item;
                String sqlBebida = "UPDATE bebida SET nombre = ?, descripcion = ?, precio = ?, id_categoria = ?, volumen = ?, graduacionAlcoholica = ? WHERE id_itemMenu = ?";
                try (PreparedStatement stmtBebida = connection.prepareStatement(sqlBebida)) {
                    stmtBebida.setString(1, bebida.getNombre());
                    stmtBebida.setString(2, bebida.getDesc());
                    stmtBebida.setDouble(3, bebida.getPrecio());
                    stmtBebida.setLong(4, categoria.getId_categoria());
                    stmtBebida.setDouble(5, bebida.getVol());
                    stmtBebida.setDouble(6, bebida.getGradA());
                    stmtBebida.setLong(7, item.getId());
                    stmtBebida.executeUpdate();
                }
            } else if (item instanceof Plato) {
                Plato plato = (Plato) item;
                String sqlPlato = "UPDATE plato SET nombre = ?, descripcion = ?, precio = ?, id_categoria = ?, calorias = ?, aptoCeliaco = ?, aptoVegetariano = ?, peso = ? WHERE id_itemMenu = ?";
                try (PreparedStatement stmtPlato = connection.prepareStatement(sqlPlato)) {
                    stmtPlato.setString(1, plato.getNombre());
                    stmtPlato.setString(2, plato.getDesc());
                    stmtPlato.setDouble(3, plato.getPrecio());
                    stmtPlato.setLong(4, categoria.getId_categoria());
                    stmtPlato.setDouble(5, plato.getCalorias());
                    stmtPlato.setBoolean(6, plato.getAptoC());
                    stmtPlato.setBoolean(7, plato.getAptoV());
                    stmtPlato.setDouble(8, plato.getPeso());
                    stmtPlato.setLong(9, item.getId());
                    stmtPlato.executeUpdate();
                }
            }

            connection.commit(); // Commit transaction
        } catch (SQLException e) {
            try {
                connection.rollback(); // Rollback transaction on error
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                connection.setAutoCommit(true); // Reset auto-commit mode
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
            
    @Override
public void eliminarItem(long id) {
    String sqlDeleteVendedorItemMenu = "DELETE FROM vendedor_itemmenu WHERE id_itemMenu = ?";
    String sqlDeletePedidoItemMenu = "DELETE FROM pedido_itemmenu WHERE id_itemMenu = ?";
    String sqlDeleteBebida = "DELETE FROM bebida WHERE id_itemMenu = ?";
    String sqlDeletePlato = "DELETE FROM plato WHERE id_itemMenu = ?";
    String sqlDeleteItemMenu = "DELETE FROM itemmenu WHERE id_itemMenu = ?";
    try (PreparedStatement stmtDeleteVendedorItemMenu = connection.prepareStatement(sqlDeleteVendedorItemMenu);
         PreparedStatement stmtDeletePedidoItemMenu = connection.prepareStatement(sqlDeletePedidoItemMenu);
         PreparedStatement stmtDeleteBebida = connection.prepareStatement(sqlDeleteBebida);
         PreparedStatement stmtDeletePlato = connection.prepareStatement(sqlDeletePlato);
         PreparedStatement stmtDeleteItemMenu = connection.prepareStatement(sqlDeleteItemMenu)) {
        connection.setAutoCommit(false); // Start transaction

        // Delete from vendedor_itemmenu
        stmtDeleteVendedorItemMenu.setLong(1, id);
        stmtDeleteVendedorItemMenu.executeUpdate();

        // Delete from pedido_itemmenu
        stmtDeletePedidoItemMenu.setLong(1, id);
        stmtDeletePedidoItemMenu.executeUpdate();

        // Delete from bebida
        stmtDeleteBebida.setLong(1, id);
        stmtDeleteBebida.executeUpdate();

        // Delete from plato
        stmtDeletePlato.setLong(1, id);
        stmtDeletePlato.executeUpdate();

        // Delete from itemmenu
        stmtDeleteItemMenu.setLong(1, id);
        stmtDeleteItemMenu.executeUpdate();

        connection.commit(); // Commit transaction
    } catch (SQLException e) {
        try {
            connection.rollback(); // Rollback transaction on error
        } catch (SQLException rollbackEx) {
            rollbackEx.printStackTrace();
        }
        e.printStackTrace();
    } finally {
        try {
            connection.setAutoCommit(true); // Reset auto-commit mode
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}

  @Override
public Set<ItemMenu> listarItems() {
    Set<ItemMenu> items = new HashSet<>();
    String sqlItemMenu = "SELECT * FROM itemmenu";
    try (PreparedStatement stmtItemMenu = connection.prepareStatement(sqlItemMenu);
         ResultSet rsItemMenu = stmtItemMenu.executeQuery()) {
        while (rsItemMenu.next()) {
            long id = rsItemMenu.getLong("id_itemMenu");
            String nombre = rsItemMenu.getString("nombre");
            String descripcion = rsItemMenu.getString("descripcion");
            double precio = rsItemMenu.getDouble("precio");
            long idCategoria = rsItemMenu.getLong("id_categoria");

            Categoria categoria = new Categoria(); // Assuming Categoria has a default constructor
            categoria.setId_categoria(idCategoria);

            // Fetch the description of the Categoria
            String sqlCategoria = "SELECT descripcion FROM categoria WHERE id_categoria = ?";
            try (PreparedStatement stmtCategoria = connection.prepareStatement(sqlCategoria)) {
                stmtCategoria.setLong(1, idCategoria);
                try (ResultSet rsCategoria = stmtCategoria.executeQuery()) {
                    if (rsCategoria.next()) {
                        categoria.setDescripcion(rsCategoria.getString("descripcion"));
                    }
                }
            }

            String sqlBebida = "SELECT * FROM bebida WHERE id_itemMenu = ?";
            try (PreparedStatement stmtBebida = connection.prepareStatement(sqlBebida)) {
                stmtBebida.setLong(1, id);
                try (ResultSet rsBebida = stmtBebida.executeQuery()) {
                    if (rsBebida.next()) {
                        Bebida bebida = new Bebida(rsBebida.getDouble("graduacionAlcoholica"), rsBebida.getDouble("volumen"), nombre, precio, descripcion);
                        bebida.setId(id);
                        bebida.setCategoria(categoria); // Set the category
                        items.add(bebida);
                        continue;
                    }
                }
            }

            String sqlPlato = "SELECT * FROM plato WHERE id_itemMenu = ?";
            try (PreparedStatement stmtPlato = connection.prepareStatement(sqlPlato)) {
                stmtPlato.setLong(1, id);
                try (ResultSet rsPlato = stmtPlato.executeQuery()) {
                    if (rsPlato.next()) {
                        Plato plato = new Plato(rsPlato.getDouble("calorias"), rsPlato.getBoolean("aptoCeliaco"), rsPlato.getBoolean("aptoVegetariano"), nombre, precio, descripcion, rsPlato.getDouble("peso"));
                        plato.setId(id);
                        plato.setCategoria(categoria); // Set the category
                        items.add(plato);
                        continue;
                    }
                }
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return items;
}
}