package isi.deso.tp.grupo8;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

public class GestionVendedoresTest {

    @Mock
    private VendedorController vendedorController;

    @Mock
    private ItemsMenuController itemsMenuController;

    private GestionVendedores gestionVendedores;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        gestionVendedores = new GestionVendedores(vendedorController, itemsMenuController);
        gestionVendedores.setVisible(false); // Hide the frame during tests
    }

    @Test
    public void testCrearVendedor() {
        // Arrange
        String nombre = "Vendedor Test";
        String direccion = "Direccion Test";
        double latitud = 10.0;
        double longitud = 20.0;
        Coordenada coordenada = new Coordenada(longitud, latitud);
        coordenada.setId(1L);

        doNothing().when(vendedorController).crearNuevoVendedor(anyString(), anyString(), any(Coordenada.class));

        // Act
        gestionVendedores.txtNombre.setText(nombre);
        gestionVendedores.txtDireccion.setText(direccion);
        gestionVendedores.txtLatitud.setText(String.valueOf(latitud));
        gestionVendedores.txtLongitud.setText(String.valueOf(longitud));
        gestionVendedores.btnCrear.doClick();

        // Assert
        verify(vendedorController, times(1)).crearNuevoVendedor(eq(nombre), eq(direccion), any(Coordenada.class));
        assertEquals("Vendedor creado: " + nombre, gestionVendedores.areaResultados.getText());
    }

    @Test
    public void testBuscarVendedor() {
        // Arrange
        long id = 1L;
        Vendedor vendedor = new Vendedor(id, "Vendedor Test", "Direccion Test", new Coordenada(10.0, 20.0), new HashSet<>());
        when(vendedorController.buscarVendedor(id)).thenReturn(vendedor);

        // Act
        gestionVendedores.txtID.setText(String.valueOf(id));
        gestionVendedores.btnBuscar.doClick();

        // Assert
        verify(vendedorController, times(1)).buscarVendedor(id);
        assertEquals("Vendedor encontrado: " + vendedor.getNombre(), gestionVendedores.areaResultados.getText());
    }

    @Test
    public void testModificarVendedor() {
        // Arrange
        long id = 1L;
        Vendedor vendedor = new Vendedor(id, "Vendedor Test", "Direccion Test", new Coordenada(10.0, 20.0), new HashSet<>());
        when(vendedorController.buscarVendedor(id)).thenReturn(vendedor);
        when(vendedorController.modificarVendedor(any(Vendedor.class))).thenReturn(true);

        // Act
        gestionVendedores.txtID.setText(String.valueOf(id));
        gestionVendedores.txtNombre.setText("Nuevo Nombre");
        gestionVendedores.txtDireccion.setText("Nueva Direccion");
        gestionVendedores.txtLatitud.setText("30.0");
        gestionVendedores.txtLongitud.setText("40.0");
        gestionVendedores.btnModificar.doClick();

        // Assert
        verify(vendedorController, times(1)).modificarVendedor(any(Vendedor.class));
        assertEquals("Vendedor modificado exitosamente: Nuevo Nombre", gestionVendedores.areaResultados.getText());
    }

    @Test
    public void testEliminarVendedor() {
        // Arrange
        long id = 1L;
        doNothing().when(vendedorController).eliminarVendedor(id);

        // Act
        gestionVendedores.txtID.setText(String.valueOf(id));
        gestionVendedores.btnEliminar.doClick();

        // Assert
        verify(vendedorController, times(1)).eliminarVendedor(id);
        assertEquals("Vendedor eliminado correctamente.", gestionVendedores.areaResultados.getText());
    }

    @Test
    public void testListarVendedores() {
        // Arrange
        Set<Vendedor> vendedores = new HashSet<>();
        vendedores.add(new Vendedor(1L, "Vendedor Test", "Direccion Test", new Coordenada(10.0, 20.0), new HashSet<>()));
        when(vendedorController.obtenerListaVendedores()).thenReturn(vendedores);

        // Act
        gestionVendedores.btnListar.doClick();

        // Assert
        verify(vendedorController, times(1)).obtenerListaVendedores();
        assertTrue(gestionVendedores.areaResultados.getText().contains("Lista de Vendedores:"));
    }
}
