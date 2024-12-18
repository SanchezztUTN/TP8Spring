package isi.deso.tp.grupo8;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class GestionPago extends JFrame {
    private JTextField txtMonto;
    private JButton btnGuardarPago;
    private JButton btnActualizarPago;
    private JComboBox<String> comboMetodoPago;
    private Pedido pedido;
    private JLabel lblRecargo;

    public GestionPago(Pedido pedido) {
        this.pedido = pedido;

        setTitle("Gestión de Pago");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new FlowLayout());

        txtMonto = new JTextField(10);
        btnGuardarPago = new JButton("Guardar Pago");
        btnActualizarPago = new JButton("Actualizar Pago");
        comboMetodoPago = new JComboBox<>(new String[]{"MP", "Por Transferencia"});
        lblRecargo = new JLabel("Recargo: $0.00");

        add(new JLabel("Monto:"));
        txtMonto.setText(String.valueOf(calcularMontoTotal()));
        txtMonto.setEditable(false);
        add(txtMonto);

        add(new JLabel("Método de Pago:"));
        add(comboMetodoPago);

        add(lblRecargo);
        add(btnGuardarPago);
        add(btnActualizarPago);

        comboMetodoPago.addActionListener(this::cambiarMetodoPago);
        btnGuardarPago.addActionListener(e -> {
            try {
                guardarPago(e);
            } catch (SQLException ex) {
                ex.printStackTrace();
                // Handle the exception appropriately
            }
        });

        btnActualizarPago.addActionListener(e -> {
            try {
                actualizarPago(e);
            } catch (SQLException ex) {
                ex.printStackTrace();
                // Handle the exception appropriately
            }
        });

        cambiarMetodoPago(null); // Inicializar campos para el método de pago seleccionado

        setVisible(true);
    }

    private double calcularMontoTotal() {
        return pedido.getItemsPedidoMemory().getLista().stream()
                .mapToDouble(itemPedido -> itemPedido.getItemPedido().getPrecio())
                .sum();
    }

    private void cambiarMetodoPago(ActionEvent e) {
        String metodoPago = (String) comboMetodoPago.getSelectedItem();
        double monto = Double.parseDouble(txtMonto.getText());
        double recargo = 0.0;

        if ("MP".equals(metodoPago)) {
            recargo = monto * 0.04; // Set recargo to 0.04 for MP
        } else if ("Por Transferencia".equals(metodoPago)) {
            recargo = monto * 0.02; // Set recargo to 0.02 for Transferencia
        }

        lblRecargo.setText("Recargo: $" + String.format("%.2f", recargo));
    }

    private void guardarPago(ActionEvent e) throws SQLException {
        String metodoPago = (String) comboMetodoPago.getSelectedItem();
        LocalDate fechaActual = LocalDate.now();

        double monto = Double.parseDouble(txtMonto.getText());
        Pago pago;
        double recargo = 0.0;

        Cliente cliente = pedido.getCliente();

        if ("MP".equals(metodoPago)) {
            String alias = cliente.getAlias();
            if (alias == null || alias.isEmpty()) {
                throw new IllegalArgumentException("Alias cannot be null or empty for MP payment.");
            }
            recargo = monto * 0.04; // Set recargo to 0.04 for MP
            pago = new PagoPorMP(0, monto, fechaActual, alias, recargo);
        } else if ("Por Transferencia".equals(metodoPago)) {
            String cbu = cliente.getCbu();
            String cuit = cliente.getCuit();
            if (cbu == null || cbu.isEmpty() || cuit == null || cuit.isEmpty()) {
                throw new IllegalArgumentException("CBU and CUIT cannot be null or empty for Transferencia payment.");
            }
            recargo = monto * 0.02; // Set recargo to 0.02 for Transferencia
            pago = new PagoPorTransferencia(0, monto, fechaActual, cbu, cuit, recargo);
        } else {
            throw new IllegalArgumentException("Método de pago desconocido: " + metodoPago);
        }

        // Update the recargo label
        lblRecargo.setText("Recargo: $" + String.format("%.2f", recargo));

        // Associate the payment with the order
        if (pedido != null) {
            pedido.setPago(pago);
            pedido.setMetodoDePago(metodoPago);

            // Save the order with the payment
            PedidoDAO pedidoDAO = new PedidoMemory();
            pedidoDAO.crearPedido(pedido);

        } else {
            System.err.println("No current order found.");
        }
        dispose();
    }

    private void actualizarPago(ActionEvent e) throws SQLException {
        String metodoPago = (String) comboMetodoPago.getSelectedItem();
        LocalDate fechaActual = LocalDate.now();

        double monto = Double.parseDouble(txtMonto.getText());
        Pago pago;
        double recargo = 0.0;

        Cliente cliente = pedido.getCliente();

        if ("MP".equals(metodoPago)) {
            String alias = cliente.getAlias();
            if (alias == null || alias.isEmpty()) {
                throw new IllegalArgumentException("Alias cannot be null or empty for MP payment.");
            }
            recargo = monto * 0.04; // Set recargo to 0.04 for MP
            pago = new PagoPorMP(pedido.getPago().getId(), monto, fechaActual, alias, recargo);
        } else if ("Por Transferencia".equals(metodoPago)) {
            String cbu = cliente.getCbu();
            String cuit = cliente.getCuit();
            if (cbu == null || cbu.isEmpty() || cuit == null || cuit.isEmpty()) {
                throw new IllegalArgumentException("CBU and CUIT cannot be null or empty for Transferencia payment.");
            }
            recargo = monto * 0.02; // Set recargo to 0.02 for Transferencia
            pago = new PagoPorTransferencia(pedido.getPago().getId(), monto, fechaActual, cbu, cuit, recargo);
        } else {
            throw new IllegalArgumentException("Método de pago desconocido: " + metodoPago);
        }

        // Update the recargo label
        lblRecargo.setText("Recargo: $" + String.format("%.2f", recargo));

        // Associate the payment with the order
        if (pedido != null) {
           
            pedido.setPago(pago);
            pedido.setMetodoDePago(metodoPago);

            // Update the order with the payment
            PedidoDAO pedidoDAO = new PedidoMemory();
            pedidoDAO.actualizarPedido(pedido);

        } else {
            System.err.println("No current order found.");
        }
        dispose();
    }
}
