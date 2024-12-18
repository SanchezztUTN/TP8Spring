package isi.deso.tp.grupo8;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

public class ClienteMemory implements ClienteDAO {
    private Connection connection;

    public ClienteMemory() throws SQLException {
        this.connection = ConexionDB.getConnection();
    }

    @Override
    public void crearCliente(Cliente cliente) {
        String sql = "INSERT INTO Cliente (cuit, email, direccion, id_coordenada, alias, cbu) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, cliente.getCuit());
            stmt.setString(2, cliente.getEmail());
            stmt.setString(3, cliente.getDireccion());
            stmt.setLong(4, cliente.getCoor().getId()); // Usar el ID de la coordenada
            stmt.setString(5, cliente.getAlias());
            stmt.setString(6, cliente.getCbu());
            stmt.executeUpdate();

            // Obtener el ID generado automáticamente para el cliente
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    cliente.setId(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Cliente buscarCliente(long id) {
        String sql = "SELECT c.id_cliente, c.cuit, c.email, c.direccion, c.id_coordenada, " +
                     "c.alias, c.cbu, co.latitud, co.longitud " +
                     "FROM Cliente c " +
                     "LEFT JOIN Coordenadas co ON c.id_coordenada = co.id_coordenada " +
                     "WHERE c.id_cliente = ? AND c.deleted = FALSE";
        Cliente cliente = null;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    cliente = mapearCliente(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar el cliente con ID: " + id);
            e.printStackTrace();
        }

        return cliente;
    }

    // Método auxiliar para mapear el ResultSet a un objeto Cliente
    private Cliente mapearCliente(ResultSet rs) throws SQLException {
        Coordenada coordenada = new Coordenada();
        coordenada.setId(rs.getLong("id_coordenada"));
        coordenada.setLatitud(rs.getDouble("latitud"));
        coordenada.setLongitud(rs.getDouble("longitud"));

        Cliente cliente = new Cliente(
            rs.getLong("id_cliente"),
            rs.getString("cuit"),
            rs.getString("email"),
            rs.getString("direccion"),
            coordenada,
            rs.getString("alias"),
            rs.getString("cbu")
        );

        return cliente;
    }

    @Override
    public boolean modificarCliente(Cliente cliente) {
        boolean clienteActualizado = false;
        boolean coordenadaActualizada = true; // Por defecto asumimos éxito

        try {
            connection.setAutoCommit(false);

            // Actualizar coordenada (si existe)
            if (cliente.getCoor() != null) {
                coordenadaActualizada = modificarCoordenada(cliente.getCoor());
            }

            // Actualizar cliente
            String sql = "UPDATE Cliente SET cuit = ?, email = ?, direccion = ?, alias = ?, cbu = ? WHERE id_cliente = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, cliente.getCuit());
                stmt.setString(2, cliente.getEmail());
                stmt.setString(3, cliente.getDireccion());
                stmt.setString(4, cliente.getAlias());
                stmt.setString(5, cliente.getCbu());
                stmt.setLong(6, cliente.getId());

                int filasActualizadas = stmt.executeUpdate();
                clienteActualizado = (filasActualizadas > 0);
            }

            // Confirmar transacción si todo fue exitoso
            if (clienteActualizado && coordenadaActualizada) {
                connection.commit();
            } else {
                connection.rollback();
            }

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException rollbackEx) {
                System.err.println("Error al hacer rollback");
                rollbackEx.printStackTrace();
            }
            System.err.println("Error al modificar el cliente con ID: " + cliente.getId());
            e.printStackTrace();
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return clienteActualizado && coordenadaActualizada;
    }

    public boolean modificarCoordenada(Coordenada coordenada) {
        if (coordenada == null) {
            throw new IllegalArgumentException("La coordenada no puede ser nula.");
        }

        if (Double.isNaN(coordenada.getLatitud()) || Double.isNaN(coordenada.getLongitud())) {
            throw new IllegalArgumentException("Latitud o longitud no son válidos.");
        }

        String sql = "UPDATE Coordenadas SET latitud = ?, longitud = ? WHERE id_coordenada = ?";
        boolean actualizada = false;

        try {
            if (connection == null || connection.isClosed()) {
                throw new IllegalStateException("La conexión a la base de datos no está disponible.");
            }

            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setDouble(1, coordenada.getLatitud());
                stmt.setDouble(2, coordenada.getLongitud());
                stmt.setLong(3, coordenada.getId());

                int filasActualizadas = stmt.executeUpdate();
                actualizada = (filasActualizadas > 0);

                if (!actualizada) {
                    System.err.println("No se encontró ninguna coordenada con ID: " + coordenada.getId());
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al modificar la coordenada con ID: " + coordenada.getId());
            e.printStackTrace();
        }

        return actualizada;
    }

    @Override
    public void eliminarCliente(long id) {
        String sqlMarkAsDeleted = "UPDATE cliente SET deleted = TRUE WHERE id_cliente = ?";
        try (PreparedStatement stmtMarkAsDeleted = connection.prepareStatement(sqlMarkAsDeleted)) {
            stmtMarkAsDeleted.setLong(1, id);
            stmtMarkAsDeleted.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Set<Cliente> listarClientes() {
        String sql = "SELECT c.id_cliente, c.cuit, c.email, c.direccion, c.id_coordenada, " +
                     "c.alias, c.cbu, co.latitud, co.longitud " +
                     "FROM Cliente c " +
                     "LEFT JOIN Coordenadas co ON c.id_coordenada = co.id_coordenada " +
                     "WHERE c.deleted = FALSE";
        Set<Cliente> clientes = new HashSet<>(); // Usamos HashSet para garantizar unicidad

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Cliente cliente = mapearCliente(rs);
                clientes.add(cliente); // HashSet se encarga de evitar duplicados
            }
        } catch (SQLException e) {
            System.err.println("Error al listar los clientes");
            e.printStackTrace();
        }

        return clientes;
    }
}
