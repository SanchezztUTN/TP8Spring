package isi.deso.tp.grupo8;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

public class GestionPedidos extends JFrame {
    private PedidoController controlador;
    private ClienteController clienteController;
    private VendedorController vendedorController;
    private ItemsMenuController itemsMenuController;
    private JTextField txtIdPedido, txtIdCliente, txtIdVendedor;
    private JTextArea areaResultados;
    private JButton btnCrearPedido, btnModificarPedido, btnEliminarPedido, btnListarPedidos, btnMostrarItems, btnPagarPedido;
    private JList<ItemMenu> listItemsMenu;
    private Set<ItemMenu> itemsSeleccionados;

    public GestionPedidos(PedidoController pedidoController,
                          ClienteController clienteController,
                          VendedorController vendedorController,
                          ItemsMenuController itemsMenuController) {
        this.controlador = pedidoController;
        this.clienteController = clienteController;
        this.vendedorController = vendedorController;
        this.itemsMenuController = itemsMenuController;
        this.itemsSeleccionados = new HashSet<>();

        setTitle("Gestión de Pedidos");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        txtIdPedido = new JTextField(10);
        txtIdCliente = new JTextField(10);
        txtIdVendedor = new JTextField(10);
        listItemsMenu = new JList<>(); // JList para seleccionar múltiples items del menú
        listItemsMenu.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        areaResultados = new JTextArea(20, 40);
        areaResultados.setEditable(false);

        btnCrearPedido = new JButton("Crear Pedido");
        btnModificarPedido = new JButton("Modificar Pedido");
        btnEliminarPedido = new JButton("Eliminar Pedido");
        btnListarPedidos = new JButton("Listar Pedidos");
        btnMostrarItems = new JButton("Mostrar Items");
        btnPagarPedido = new JButton("Pagar Pedido");

        add(new JLabel("ID Pedido:"));
        add(txtIdPedido);
        add(new JLabel("ID Cliente:"));
        add(txtIdCliente);
        add(new JLabel("ID Vendedor:"));
        add(txtIdVendedor);
        add(new JLabel("Items del Menú:"));
        add(new JScrollPane(listItemsMenu));

        add(btnMostrarItems);
        add(btnCrearPedido);
        add(btnModificarPedido);
        add(btnEliminarPedido);
        add(btnListarPedidos);
        add(btnPagarPedido);
        add(new JScrollPane(areaResultados));

        btnMostrarItems.addActionListener(this::mostrarItemsVendedor);
        btnCrearPedido.addActionListener(this::abrirVentanaPago);
        btnModificarPedido.addActionListener(this::modificarPedido);
        btnEliminarPedido.addActionListener(this::eliminarPedido);
        btnListarPedidos.addActionListener(e -> listarPedidos());
        btnPagarPedido.addActionListener(this::pagarPedido);

        setVisible(true);
    }

    private void mostrarItemsVendedor(ActionEvent e) {
        try {
            long idVendedor = Long.parseLong(txtIdVendedor.getText());
            Set<ItemMenu> items = vendedorController.obtenerListaProductosVendedor(idVendedor);
            listItemsMenu.setListData(items.toArray(new ItemMenu[0]));
            areaResultados.setText("Items del vendedor cargados.");
        } catch (NumberFormatException ex) {
            areaResultados.setText("Por favor, ingresa un ID de vendedor válido.");
        } catch (Exception ex) {
            areaResultados.setText("Error al cargar items: " + ex.getMessage());
        }
    }

    private void abrirVentanaPago(ActionEvent e) {
        try {
            long idCliente = Long.parseLong(txtIdCliente.getText());
            long idVendedor = Long.parseLong(txtIdVendedor.getText());

            itemsSeleccionados.clear();
            for (ItemMenu item : listItemsMenu.getSelectedValuesList()) {
                itemsSeleccionados.add(item);
            }

            // Prepare the pedido without setting the Pago
            Pedido pedido = new Pedido(0, clienteController.buscarCliente(idCliente), new ItemsPedidoMemory(), vendedorController.buscarVendedor(idVendedor), null);
            for (ItemMenu item : itemsSeleccionados) {
                pedido.getItemsPedidoMemory().agregarItem(item);
            }

            areaResultados.setText("Seleccione el método de pago en la siguiente ventana.");

            // Open the payment window and pass the pedido
            new GestionPago(pedido);

            // Do not call controlador.crearPedido here

        } catch (NumberFormatException ex) {
            areaResultados.setText("Error: Por favor, ingresa valores numéricos válidos.");
        } catch (Exception ex) {
            areaResultados.setText("Error al crear el pedido: " + ex.getMessage());
        }
    }

    private Set<Long> getIdsItemsSeleccionados() {
        Set<Long> ids = new HashSet<>();
        for (ItemMenu item : itemsSeleccionados) {
            ids.add(item.getId());
        }
        return ids;
    }

    private void modificarPedido(ActionEvent e) {
        try {
            // Verificar y parsear el ID del pedido
            String idPedidoTexto = txtIdPedido.getText();
            if (idPedidoTexto == null || idPedidoTexto.isEmpty()) {
                areaResultados.setText("Error: El ID del pedido no puede estar vacío.");
                return;
            }
    
            long idPedido = Long.parseLong(idPedidoTexto);
    
            // Buscar el pedido
            Pedido pedido = controlador.buscarPedido(idPedido);
            if (pedido != null) {
                System.out.println("Pedido encontrado con ID: " + idPedido);
                System.out.println("Estado actual del pedido: " + pedido.getEstado());
    
                // Modificar el estado del pedido
                pedido.setEstado(EstadoPedido.RECIBIDO);
                System.out.println("Nuevo estado del pedido: " + pedido.getEstado());
    
                // Actualizar en la base de datos
                controlador.actualizarPedido(pedido);
    
                // Confirmación
                areaResultados.setText("Pedido modificado exitosamente.");
                System.out.println("Pedido con ID " + idPedido + " modificado exitosamente.");
            } else {
                areaResultados.setText("Pedido no encontrado.");
                System.out.println("No se encontró un pedido con el ID " + idPedido);
            }
        }catch (NumberFormatException ex) {
            // Manejar error al parsear el ID
            areaResultados.setText("Error: Por favor, ingresa un ID de pedido válido.");
            System.out.println("Error al parsear el ID: " + ex.getMessage());
            ex.printStackTrace();
        }
        // Manejar errores SQL
         catch (Exception ex) {
            // Manejar otros errores
            areaResultados.setText("Error al modificar el pedido: " + ex.getMessage());
            System.out.println("Error inesperado al modificar el pedido: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    

    private void eliminarPedido(ActionEvent e) {
        try {
            long idPedido = Long.parseLong(txtIdPedido.getText());
            controlador.eliminarPedido(idPedido);
            areaResultados.setText("Pedido eliminado correctamente.");
        } catch (NumberFormatException ex) {
            areaResultados.setText("Error: Por favor, ingresa un ID de pedido válido.");
        } catch (Exception ex) {
            areaResultados.setText("Error al eliminar el pedido: " + ex.getMessage());
        }
    }

    private void listarPedidos() {
        try {
            Set<Pedido> pedidos = controlador.listarPedidos();
            if (pedidos.isEmpty()) {
                areaResultados.setText("No hay pedidos registrados.");
            } else {
                StringBuilder sb = new StringBuilder("Lista de Pedidos:\n");
                for (Pedido p : pedidos) {
                    sb.append("ID: ").append(p.getId())
                      .append(", Cliente: ").append(p.getCliente().getId())
                      .append(", Vendedor: ").append(p.getVendedor().getId())
                      .append(", Estado: ").append(p.getEstado())
                      .append(", Método de Pago: ").append(p.getMetodoDePago())
                      .append("\n");

                    // Add Pago information
                    Pago pago = p.getPago();
                    if (pago != null) {
                        sb.append("  Pago ID: ").append(pago.getId())
                          .append(", Monto: ").append(pago.getMonto())
                          .append(", Fecha: ").append(pago.getFecha())
                          .append(", Tipo: ").append(pago.getTipoPago());

                        if (pago instanceof PagoPorMP) {
                            PagoPorMP pagoMP = (PagoPorMP) pago;
                            sb.append(", Alias: ").append(pagoMP.getAlias())
                              .append(", Recargo: ").append(pagoMP.getRecargo());
                        } else if (pago instanceof PagoPorTransferencia) {
                            PagoPorTransferencia pagoTrans = (PagoPorTransferencia) pago;
                            sb.append(", CBU: ").append(pagoTrans.getCbu())
                              .append(", CUIT: ").append(pagoTrans.getCuit())
                              .append(", Recargo: ").append(pagoTrans.getRecargo());
                        }
                        sb.append("\n");
                    }
                }
                areaResultados.setText(sb.toString());
            }
        } catch (Exception ex) {
            areaResultados.setText("Error al listar los pedidos: " + ex.getMessage());
        }
    }

    private void pagarPedido(ActionEvent e) {
        try {
            long idPedido = Long.parseLong(txtIdPedido.getText());
            Pedido pedido = controlador.buscarPedido(idPedido);
            if (pedido != null) {
                pedido.setEstado(EstadoPedido.PAGADO);
                controlador.actualizarPedido(pedido);
                areaResultados.setText("Pedido pagado exitosamente.");
            } else {
                areaResultados.setText("Pedido no encontrado.");
            }
        } catch (NumberFormatException ex) {
            areaResultados.setText("Error: Por favor, ingresa un ID de pedido válido.");
        } catch (Exception ex) {
            areaResultados.setText("Error al pagar el pedido: " + ex.getMessage());
        }
    }

    public void guardarPedidoConPago(Pedido pedido) {
        try {
            controlador.crearPedido(pedido.getId(), pedido.getCliente().getId(), pedido.getVendedor().getId(), pedido.getMetodoDePago(), getIdsItemsSeleccionados());
            areaResultados.setText("Pedido creado y pago confirmado exitosamente.");
        } catch (Exception ex) {
            areaResultados.setText("Error al guardar el pedido: " + ex.getMessage());
        }
    }

    public static void main(String[] args) throws SQLException {
        ClienteMemory clienteMemory = new ClienteMemory();
        VendedorMemory vendedorMemory = new VendedorMemory();
        ItemsMenuMemory itemsMenuMemory = new ItemsMenuMemory();

        ClienteController clienteController = new ClienteController(clienteMemory);
        VendedorController vendedorController = new VendedorController(vendedorMemory, itemsMenuMemory);
        ItemsMenuController itemsMenuController = new ItemsMenuController(itemsMenuMemory);

        PedidoController pedidoController = new PedidoController(new PedidoMemory(), clienteMemory, vendedorMemory, itemsMenuMemory);

        new GestionPedidos(pedidoController, clienteController, vendedorController, itemsMenuController);
    }
}


