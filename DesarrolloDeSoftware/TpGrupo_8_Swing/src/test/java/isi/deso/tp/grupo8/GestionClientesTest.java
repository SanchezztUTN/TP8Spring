package isi.deso.tp.grupo8;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

public class GestionClientesTest {

    @Mock
    private ClienteController clienteController;

    private GestionClientes gestionClientes;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        gestionClientes = new GestionClientes(clienteController);
        gestionClientes.setVisible(false); // Hide the frame during tests
    }

    @Test
    public void testCrearCliente() {
        // Arrange
        String cuit = "123456789";
        String email = "test@example.com";
        String direccion = "Direccion Test";
        String alias = "alias";
        String cbu = "cbu";
        double latitud = 10.0;
        double longitud = 20.0;
        Coordenada coordenada = new Coordenada(longitud, latitud);
        coordenada.setId(1L);

        doNothing().when(clienteController).crearNuevoCliente(anyString(), anyString(), anyString(), any(Coordenada.class), anyString(), anyString());

        // Act
        gestionClientes.txtCuit.setText(cuit);
        gestionClientes.txtEmail.setText(email);
        gestionClientes.txtDireccion.setText(direccion);
        gestionClientes.txtAlias.setText(alias);
        gestionClientes.txtCbu.setText(cbu);
        gestionClientes.txtLatitud.setText(String.valueOf(latitud));
        gestionClientes.txtLongitud.setText(String.valueOf(longitud));
        gestionClientes.btnCrear.doClick();

        // Assert
        verify(clienteController, times(1)).crearNuevoCliente(eq(cuit), eq(email), eq(direccion), any(Coordenada.class), eq(alias), eq(cbu));
        assertEquals("Cliente creado.", gestionClientes.areaResultados.getText());
    }

    @Test
    public void testBuscarCliente() {
        // Arrange
        long id = 1L;
        Cliente cliente = new Cliente(id, "123456789", "test@example.com", "Direccion Test", new Coordenada(10.0, 20.0), "alias", "cbu");
        when(clienteController.buscarCliente(id)).thenReturn(cliente);

        // Act
        gestionClientes.txtID.setText(String.valueOf(id));
        gestionClientes.btnBuscar.doClick();

        // Assert
        verify(clienteController, times(1)).buscarCliente(id);
        assertTrue(gestionClientes.areaResultados.getText().contains("ID: " + id));
    }

    @Test
    public void testModificarCliente() {
        // Arrange
        long id = 1L;
        Cliente cliente = new Cliente(id, "123456789", "test@example.com", "Direccion Test", new Coordenada(10.0, 20.0), "alias", "cbu");
        when(clienteController.buscarCliente(id)).thenReturn(cliente);
        when(clienteController.modificarCliente(anyLong(), anyString(), anyString(), anyString(), any(Coordenada.class), anyString(), anyString())).thenReturn(true);

        // Act
        gestionClientes.txtID.setText(String.valueOf(id));
        gestionClientes.txtCuit.setText("987654321");
        gestionClientes.txtEmail.setText("new@example.com");
        gestionClientes.txtDireccion.setText("Nueva Direccion");
        gestionClientes.txtAlias.setText("newAlias");
        gestionClientes.txtCbu.setText("newCbu");
        gestionClientes.txtLatitud.setText("30.0");
        gestionClientes.txtLongitud.setText("40.0");
        gestionClientes.btnModificar.doClick();

        // Assert
        verify(clienteController, times(1)).modificarCliente(anyLong(), anyString(), anyString(), anyString(), any(Coordenada.class), anyString(), anyString());
        assertEquals("Cliente modificado exitosamente.", gestionClientes.areaResultados.getText());
    }

    @Test
    public void testEliminarCliente() {
        // Arrange
        long id = 1L;
        doNothing().when(clienteController).eliminarCliente(id);

        // Act
        gestionClientes.txtID.setText(String.valueOf(id));
        gestionClientes.btnEliminar.doClick();

        // Assert
        verify(clienteController, times(1)).eliminarCliente(id);
        assertEquals("Cliente eliminado.", gestionClientes.areaResultados.getText());
    }

    @Test
    public void testListarClientes() {
        // Arrange
        Set<Cliente> clientes = new HashSet<>();
        clientes.add(new Cliente(1L, "123456789", "test@example.com", "Direccion Test", new Coordenada(10.0, 20.0), "alias", "cbu"));
        when(clienteController.obtenerListaClientes()).thenReturn(clientes);

        // Act
        gestionClientes.btnListar.doClick();

        // Assert
        verify(clienteController, times(1)).obtenerListaClientes();
        assertTrue(gestionClientes.areaResultados.getText().contains("Clientes:"));
    }
}
