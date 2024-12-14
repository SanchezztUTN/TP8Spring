package isi.deso.tp.grupo8;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class PagoMemory implements PagoDAO {
    private Connection connection;

    public PagoMemory() throws SQLException {
        this.connection = ConexionDB.getConnection();
    }

    @Override
    public void crearPago(Pago pago) {
        String sqlPago = "INSERT INTO pago (monto, fecha) VALUES (?, ?)";
        try (PreparedStatement stmtPago = connection.prepareStatement(sqlPago, Statement.RETURN_GENERATED_KEYS)) {
            stmtPago.setDouble(1, pago.getMonto());
            stmtPago.setDate(2, Date.valueOf(pago.getFecha()));
            stmtPago.executeUpdate();

            try (ResultSet generatedKeys = stmtPago.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    long idPago = generatedKeys.getLong(1);
                    pago.setId(idPago);

                    if (pago instanceof PagoPorMP) {
                        PagoPorMP pagoMP = (PagoPorMP) pago;
                        // Insert alias, recargo, monto, and fecha
                        String sqlMP = "INSERT INTO pagopormp (id_pago, alias, recargo, monto, fecha) VALUES (?, ?, ?, ?, ?)";
                        try (PreparedStatement stmtMP = connection.prepareStatement(sqlMP)) {
                            stmtMP.setLong(1, idPago);
                            stmtMP.setString(2, pagoMP.getAlias());
                            stmtMP.setDouble(3, pagoMP.getRecargo());
                            stmtMP.setDouble(4, pagoMP.getMonto());
                            stmtMP.setDate(5, Date.valueOf(pagoMP.getFecha()));
                            stmtMP.executeUpdate();
                        }
                    } else if (pago instanceof PagoPorTransferencia) {
                        PagoPorTransferencia pagoTrans = (PagoPorTransferencia) pago;
                        // Insert cbu, cuit, recargo, monto, and fecha
                        String sqlTrans = "INSERT INTO pagoportransferencia (id_pago, cbu, cuit, recargo, monto, fecha) VALUES (?, ?, ?, ?, ?, ?)";
                        try (PreparedStatement stmtTrans = connection.prepareStatement(sqlTrans)) {
                            stmtTrans.setLong(1, idPago);
                            stmtTrans.setString(2, pagoTrans.getCbu());
                            stmtTrans.setString(3, pagoTrans.getCuit());
                            stmtTrans.setDouble(4, pagoTrans.getRecargo());
                            stmtTrans.setDouble(5, pagoTrans.getMonto());
                            stmtTrans.setDate(6, Date.valueOf(pagoTrans.getFecha()));
                            stmtTrans.executeUpdate();
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Pago buscarPago(long id) {
        String sqlPago = "SELECT * FROM pago WHERE id_pago = ?";
        try (PreparedStatement stmtPago = connection.prepareStatement(sqlPago)) {
            stmtPago.setLong(1, id);
            try (ResultSet rsPago = stmtPago.executeQuery()) {
                if (rsPago.next()) {
                    double monto = rsPago.getDouble("monto");
                    LocalDate fecha = rsPago.getDate("fecha").toLocalDate();

                    // Check PagoPorMP
                    String sqlMP = "SELECT * FROM pagopormp WHERE id_pago = ?";
                    try (PreparedStatement stmtMP = connection.prepareStatement(sqlMP)) {
                        stmtMP.setLong(1, id);
                        try (ResultSet rsMP = stmtMP.executeQuery()) {
                            if (rsMP.next()) {
                                double recargo = rsMP.getDouble("recargo");
                                String alias = rsMP.getString("alias");
                                return new PagoPorMP(id, monto, fecha, alias, recargo);
                            }
                        }
                    }

                    // Check PagoPorTransferencia
                    String sqlTrans = "SELECT * FROM pagoportransferencia WHERE id_pago = ?";
                    try (PreparedStatement stmtTrans = connection.prepareStatement(sqlTrans)) {
                        stmtTrans.setLong(1, id);
                        try (ResultSet rsTrans = stmtTrans.executeQuery()) {
                            if (rsTrans.next()) {
                                double recargo = rsTrans.getDouble("recargo");
                                String cbu = rsTrans.getString("cbu");
                                String cuit = rsTrans.getString("cuit");
                                return new PagoPorTransferencia(id, monto, fecha, cbu, cuit, recargo);
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