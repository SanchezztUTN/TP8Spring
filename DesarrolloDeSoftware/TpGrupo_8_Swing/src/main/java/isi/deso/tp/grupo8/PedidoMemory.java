package isi.deso.tp.grupo8;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

public class PedidoMemory implements PedidoDAO {
    private final Connection connection;

    public PedidoMemory() throws SQLException {
        this.connection = ConexionDB.getConnection();
    }

    @Override
    public void crearPedido(Pedido pedido) {
        try {
            connection.setAutoCommit(false);

            // Create and save the payment first
            crearPago(pedido.getPago());

            // Create the order
            String sql = "INSERT INTO pedido (id_cliente, id_vendedor, estado, id_pago, metodo_pago) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setLong(1, pedido.getCliente().getId());
                stmt.setLong(2, pedido.getVendedor().getId());
                stmt.setString(3, pedido.getEstado().toString());
                stmt.setLong(4, pedido.getPago().getId());
                stmt.setString(5, pedido.getMetodoDePago());
                stmt.executeUpdate();
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        pedido.setId(generatedKeys.getLong(1));
                    }
                }
            }

            // Add items to the order
            for (ItemPedido itemPedido : pedido.getItemsPedidoMemory().getLista()) {
                ItemMenu itemMenu = itemPedido.getItemPedido();
                agregarItemAPedido(pedido.getId(), itemMenu.getId());
            }

            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public Pedido buscarPedido(long id) {
        String sql = "SELECT * FROM pedido WHERE id_pedido = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Pedido pedido = new Pedido();
                    pedido.setId(rs.getLong("id_pedido"));
                    pedido.setCliente(new ClienteMemory().buscarCliente(rs.getLong("id_cliente")));
                    pedido.setVendedor(new VendedorMemory().buscarVendedor(rs.getLong("id_vendedor")));
                    pedido.setEstado(EstadoPedido.valueOf(rs.getString("estado")));
                    pedido.setItemsPedidoMemory(obtenerItemsPedido(id));
                    Pago pago = buscarPago(rs.getLong("id_pago"));
                    pedido.setPago(pago);
                    pedido.setMetodoDePago(pago instanceof PagoPorMP ? "MP" : "Por Transferencia");
                    return pedido;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void actualizarPedido(Pedido pedido) {
        
        try {
            connection.setAutoCommit(false);

            // Update the payment first
            actualizarPago(pedido.getPago());

            // Update the order
            String sql = "UPDATE pedido SET estado = ?, id_cliente = ?, id_vendedor = ?, id_pago = ? WHERE id_pedido = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, pedido.getEstado().toString());
                stmt.setLong(2, pedido.getCliente().getId());
                stmt.setLong(3, pedido.getVendedor().getId());
                stmt.setLong(4, pedido.getPago().getId());
                stmt.setLong(5, pedido.getId());
                stmt.executeUpdate();
            }

            // Update the items in the order
            String sqlDeleteItems = "DELETE FROM pedido_itemmenu WHERE id_pedido = ?";
            try (PreparedStatement stmtDeleteItems = connection.prepareStatement(sqlDeleteItems)) {
                stmtDeleteItems.setLong(1, pedido.getId());
                stmtDeleteItems.executeUpdate();
            }

            for (ItemPedido itemPedido : pedido.getItemsPedidoMemory().getLista()) {
                ItemMenu itemMenu = itemPedido.getItemPedido();
                agregarItemAPedido(pedido.getId(), itemMenu.getId());
            }

            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        
    }

    private void actualizarPago(Pago pago) throws SQLException {
        String sqlPago = "UPDATE pago SET monto = ?, fecha = ? WHERE id_pago = ?";
        try (PreparedStatement stmtPago = connection.prepareStatement(sqlPago)) {
            stmtPago.setDouble(1, pago.getMonto());
            stmtPago.setDate(2, java.sql.Date.valueOf(pago.getFecha()));
            stmtPago.setLong(3, pago.getId());
            stmtPago.executeUpdate();
        }

        if (pago instanceof PagoPorMP) {
            String sqlMP = "UPDATE pagopormp SET alias = ?, recargo = ? WHERE id_pago = ?";
            try (PreparedStatement stmtMP = connection.prepareStatement(sqlMP)) {
                stmtMP.setString(1, ((PagoPorMP) pago).getAlias());
                stmtMP.setDouble(2, ((PagoPorMP) pago).getRecargo());
                stmtMP.setLong(3, pago.getId());
                stmtMP.executeUpdate();
            }
        } else if (pago instanceof PagoPorTransferencia) {
            String sqlTransf = "UPDATE pagoportransferencia SET cbu = ?, cuit = ? WHERE id_pago = ?";
            try (PreparedStatement stmtTransf = connection.prepareStatement(sqlTransf)) {
                stmtTransf.setString(1, ((PagoPorTransferencia) pago).getCbu());
                stmtTransf.setString(2, ((PagoPorTransferencia) pago).getCuit());
                stmtTransf.setLong(3, pago.getId());
                stmtTransf.executeUpdate();
            }
        }
    }

    @Override
    public void eliminarPedido(long id) {
        try {
            connection.setAutoCommit(false);

            // Delete related records from pedido_itemmenu
            String sqlDeleteItems = "DELETE FROM pedido_itemmenu WHERE id_pedido = ?";
            try (PreparedStatement stmtDeleteItems = connection.prepareStatement(sqlDeleteItems)) {
                stmtDeleteItems.setLong(1, id);
                stmtDeleteItems.executeUpdate();
            }

            // Get the payment ID associated with the order
            long idPago = 0;
            String sqlGetPago = "SELECT id_pago FROM pedido WHERE id_pedido = ?";
            try (PreparedStatement stmtGetPago = connection.prepareStatement(sqlGetPago)) {
                stmtGetPago.setLong(1, id);
                try (ResultSet rs = stmtGetPago.executeQuery()) {
                    if (rs.next()) {
                        idPago = rs.getLong("id_pago");
                    }
                }
            }

            // Delete the order record
            String sqlDeletePedido = "DELETE FROM pedido WHERE id_pedido = ?";
            try (PreparedStatement stmtDeletePedido = connection.prepareStatement(sqlDeletePedido)) {
                stmtDeletePedido.setLong(1, id);
                stmtDeletePedido.executeUpdate();
            }

            // Delete specific payment type records
            if (idPago != 0) {
                String sqlDeletePagoMP = "DELETE FROM pagopormp WHERE id_pago = ?";
                try (PreparedStatement stmtDeletePagoMP = connection.prepareStatement(sqlDeletePagoMP)) {
                    stmtDeletePagoMP.setLong(1, idPago);
                    stmtDeletePagoMP.executeUpdate();
                }

                String sqlDeletePagoTransf = "DELETE FROM pagoportransferencia WHERE id_pago = ?";
                try (PreparedStatement stmtDeletePagoTransf = connection.prepareStatement(sqlDeletePagoTransf)) {
                    stmtDeletePagoTransf.setLong(1, idPago);
                    stmtDeletePagoTransf.executeUpdate();
                }

                // Delete the payment record
                String sqlDeletePago = "DELETE FROM pago WHERE id_pago = ?";
                try (PreparedStatement stmtDeletePago = connection.prepareStatement(sqlDeletePago)) {
                    stmtDeletePago.setLong(1, idPago);
                    stmtDeletePago.executeUpdate();
                }
            }

            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public Set<Pedido> listarPedidos() {
        Set<Pedido> pedidos = new HashSet<>();
        String sql = "SELECT * FROM pedido";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Pedido pedido = new Pedido();
                pedido.setId(rs.getLong("id_pedido"));
                pedido.setCliente(new ClienteMemory().buscarCliente(rs.getLong("id_cliente")));
                pedido.setVendedor(new VendedorMemory().buscarVendedor(rs.getLong("id_vendedor")));
                pedido.setEstado(EstadoPedido.valueOf(rs.getString("estado")));
                pedido.setItemsPedidoMemory(obtenerItemsPedido(pedido.getId()));
                Pago pago = buscarPago(rs.getLong("id_pago"));
                pedido.setPago(pago);
                pedido.setMetodoDePago(pago instanceof PagoPorMP ? "MP" : "Por Transferencia");
                pedidos.add(pedido);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pedidos;
    }

    private ItemsPedidoMemory obtenerItemsPedido(long idPedido) {
        ItemsPedidoMemory itemsPedidoMemory = new ItemsPedidoMemory();
        String sql = "SELECT * FROM pedido_itemmenu WHERE id_pedido = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, idPedido);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ItemMenu item = new ItemsMenuMemory().buscarItem(rs.getLong("id_itemmenu"));
                    if (item != null) {
                        itemsPedidoMemory.agregarItem(item);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return itemsPedidoMemory;
    }

    private void agregarItemAPedido(long idPedido, long idItemMenu) {
        String sql = "INSERT INTO pedido_itemmenu (id_pedido, id_itemmenu) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, idPedido);
            stmt.setLong(2, idItemMenu);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void crearPago(Pago pago) {
        if (pago == null || pago.getMonto() == 0 || pago.getFecha() == null) {
            throw new IllegalArgumentException("Pago data is incomplete or invalid.");
        }

        String sqlPago = "INSERT INTO pago (monto, fecha) VALUES (?, ?)";
        try (PreparedStatement stmtPago = connection.prepareStatement(sqlPago, Statement.RETURN_GENERATED_KEYS)) {
            stmtPago.setDouble(1, pago.getMonto());
            stmtPago.setDate(2, java.sql.Date.valueOf(pago.getFecha()));
            stmtPago.executeUpdate();
            try (ResultSet generatedKeys = stmtPago.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    long idPago = generatedKeys.getLong(1);
                    pago.setId(idPago);

                    if (pago instanceof PagoPorMP) {
                        PagoPorMP pagoMP = (PagoPorMP) pago;
                        String sqlMP = "INSERT INTO pagopormp (id_pago, alias, recargo, monto, fecha) VALUES (?, ?, ?, ?, ?)";
                        try (PreparedStatement stmtMP = connection.prepareStatement(sqlMP)) {
                            stmtMP.setLong(1, idPago);
                            stmtMP.setString(2, pagoMP.getAlias());
                            stmtMP.setDouble(3, pagoMP.getRecargo());
                            stmtMP.setDouble(4, pagoMP.getMonto());
                            stmtMP.setDate(5, java.sql.Date.valueOf(pagoMP.getFecha()));
                            stmtMP.executeUpdate();
                        }
                    } else if (pago instanceof PagoPorTransferencia) {
                        PagoPorTransferencia pagoTransf = (PagoPorTransferencia) pago;
                        String sqlTransf = "INSERT INTO pagoportransferencia (id_pago, cbu, cuit, recargo, monto, fecha) VALUES (?, ?, ?, ?, ?, ?)";
                        try (PreparedStatement stmtTransf = connection.prepareStatement(sqlTransf)) {
                            stmtTransf.setLong(1, idPago);
                            stmtTransf.setString(2, pagoTransf.getCbu());
                            stmtTransf.setString(3, pagoTransf.getCuit());
                            stmtTransf.setDouble(4, pagoTransf.getRecargo());
                            stmtTransf.setDouble(5, pagoTransf.getMonto());
                            stmtTransf.setDate(6, java.sql.Date.valueOf(pagoTransf.getFecha()));
                            stmtTransf.executeUpdate();
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Pago buscarPago(long idPago) {
        String sql = "SELECT * FROM pago WHERE id_pago = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, idPago);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Pago pago;
                    long id = rs.getLong("id_pago");
                    double monto = rs.getDouble("monto");
                    java.sql.Date fecha = rs.getDate("fecha");

                    // Check if it's a PagoPorMP
                    String sqlMP = "SELECT * FROM pagopormp WHERE id_pago = ?";
                    try (PreparedStatement stmtMP = connection.prepareStatement(sqlMP)) {
                        stmtMP.setLong(1, id);
                        try (ResultSet rsMP = stmtMP.executeQuery()) {
                            if (rsMP.next()) {
                                String alias = rsMP.getString("alias");
                                double recargo = rsMP.getDouble("recargo");
                                pago = new PagoPorMP(id, monto, fecha.toLocalDate(), alias, recargo);
                                return pago;
                            }
                        }
                    }

                    // Check if it's a PagoPorTransferencia
                    String sqlTransf = "SELECT * FROM pagoportransferencia WHERE id_pago = ?";
                    try (PreparedStatement stmtTransf = connection.prepareStatement(sqlTransf)) {
                        stmtTransf.setLong(1, id);
                        try (ResultSet rsTransf = stmtTransf.executeQuery()) {
                            if (rsTransf.next()) {
                                String cbu = rsTransf.getString("cbu");
                                String cuit = rsTransf.getString("cuit");
                                pago = new PagoPorTransferencia(id, monto, fecha.toLocalDate(), cbu, cuit, 0.0);
                                return pago;
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
}
