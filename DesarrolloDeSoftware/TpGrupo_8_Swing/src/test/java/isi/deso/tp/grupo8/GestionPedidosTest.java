package isi.deso.tp.grupo8;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

public class GestionPedidosTest {

    @Mock
    private PedidoController pedidoController;

    @Mock
    private ClienteController clienteController;

    @Mock
    private VendedorController vendedorController;

    @Mock
    private ItemsMenuController itemsMenuController;

    private GestionPedidos gestionPedidos;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        gestionPedidos = new GestionPedidos(pedidoController, clienteController, vendedorController, itemsMenuController);
        gestionPedidos.setVisible(false); // Hide the frame during tests
    }

    @Test
    public void testCrearPedido() {
        // Arrange
        long idCliente = 1L;
        long idVendedor = 2L;
        Cliente cliente = new Cliente(idCliente, "123456789", "test@example.com", "Direccion Test", new Coordenada(10.0, 20.0), "alias", "cbu");
        Vendedor vendedor = new Vendedor(idVendedor, "Vendedor Test", "Direccion Test", new Coordenada(10.0, 20.0), new HashSet<>());
        when(clienteController.buscarCliente(idCliente)).thenReturn(cliente);
        when(vendedorController.buscarVendedor(idVendedor)).thenReturn(vendedor);

        // Act
        gestionPedidos.txtIdCliente.setText(String.valueOf(idCliente));
        gestionPedidos.txtIdVendedor.setText(String.valueOf(idVendedor));
        gestionPedidos.btnCrearPedido.doClick();

        // Assert
        verify(clienteController, times(1)).buscarCliente(idCliente);
        verify(vendedorController, times(1)).buscarVendedor(idVendedor);
        assertEquals("Seleccione el método de pago en la siguiente ventana.", gestionPedidos.areaResultados.getText());
    }

    @Test
    public void testModificarPedido() {
        // Arrange
        long idPedido = 1L;
        long idCliente = 1L;
        long idVendedor = 2L;
        Cliente cliente = new Cliente(idCliente, "123456789", "test@example.com", "Direccion Test", new Coordenada(10.0, 20.0), "alias", "cbu");
        Vendedor vendedor = new Vendedor(idVendedor, "Vendedor Test", "Direccion Test", new Coordenada(10.0, 20.0), new HashSet<>());
        Pedido pedido = new Pedido(idPedido, cliente, new ItemsPedidoMemory(), vendedor, "MercadoPago");
        pedido.setPago(new PagoPorMP(1L, 100.0, LocalDate.now(), "alias", 4.0));
        when(pedidoController.buscarPedido(idPedido)).thenReturn(pedido);
        when(clienteController.buscarCliente(idCliente)).thenReturn(cliente);
        when(vendedorController.buscarVendedor(idVendedor)).thenReturn(vendedor);

        // Act
        gestionPedidos.txtIdPedido.setText(String.valueOf(idPedido));
        gestionPedidos.txtIdCliente.setText(String.valueOf(idCliente));
        gestionPedidos.txtIdVendedor.setText(String.valueOf(idVendedor));
        gestionPedidos.btnModificarPedido.doClick();

        // Assert
        verify(pedidoController, times(1)).buscarPedido(idPedido);
        verify(clienteController, times(1)).buscarCliente(idCliente);
        verify(vendedorController, times(1)).buscarVendedor(idVendedor);
        }

    @Test
    public void testEliminarPedido() {
        // Arrange
        long idPedido = 1L;
        doNothing().when(pedidoController).eliminarPedido(idPedido);

        // Act
        gestionPedidos.txtIdPedido.setText(String.valueOf(idPedido));
        gestionPedidos.btnEliminarPedido.doClick();

        // Assert
        verify(pedidoController, times(1)).eliminarPedido(idPedido);
        assertEquals("Pedido eliminado correctamente.", gestionPedidos.areaResultados.getText());
    }

    @Test
    public void testListarPedidos() {
        // Arrange
        Set<Pedido> pedidos = new HashSet<>();
        Cliente cliente = new Cliente(1L, "123456789", "test@example.com", "Direccion Test", new Coordenada(10.0, 20.0), "alias", "cbu");
        Vendedor vendedor = new Vendedor(2L, "Vendedor Test", "Direccion Test", new Coordenada(10.0, 20.0), new HashSet<>());
        pedidos.add(new Pedido(1L, cliente, new ItemsPedidoMemory(), vendedor, "PagoPorMP"));
        when(pedidoController.listarPedidos()).thenReturn(pedidos);

        // Act
        gestionPedidos.btnListarPedidos.doClick();

        // Assert
        verify(pedidoController, times(1)).listarPedidos();
        assertTrue(gestionPedidos.areaResultados.getText().contains("Lista de Pedidos:"));
    }
}