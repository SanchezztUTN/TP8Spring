package isi.deso.tp.grupo8;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.Mock;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

public class GestionItemsMenuTest {

    @Mock
    private ItemsMenuController itemsMenuController;

    private GestionItemsMenu gestionItemsMenu;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        gestionItemsMenu = new GestionItemsMenu(itemsMenuController);
        gestionItemsMenu.setVisible(false); // Hide the frame during tests
    }

    @Test
    public void testCrearPlato() {
        // Arrange
        String nombre = "Plato Test";
        String descripcion = "Descripcion Test";
        double precio = 100.0;
        double calorias = 200.0;
        boolean celiacos = true;
        boolean vegetariano = false;
        double peso = 300.0;
        long idCategoria = 1L;
        Categoria categoria = new Categoria(idCategoria);

        doAnswer(invocation -> {
            return null;
        }).when(itemsMenuController).crearPlato(anyString(), anyString(), anyDouble(), anyDouble(), anyBoolean(), anyBoolean(), anyDouble(), any(Categoria.class));

        // Act
        gestionItemsMenu.txtNombre.setText(nombre);
        gestionItemsMenu.txtDescripcion.setText(descripcion);
        gestionItemsMenu.txtPrecio.setText(String.valueOf(precio));
        gestionItemsMenu.txtCalorias.setText(String.valueOf(calorias));
        gestionItemsMenu.chkCeliacos.setSelected(celiacos);
        gestionItemsMenu.chkVegetariano.setSelected(vegetariano);
        gestionItemsMenu.txtPeso.setText(String.valueOf(peso));
        gestionItemsMenu.txtIdCategoria.setText(String.valueOf(idCategoria));
        gestionItemsMenu.btnCrearPlato.doClick();

       
    }

    @Test
    public void testCrearBebida() {
        // Arrange
        String nombre = "Bebida Test";
        String descripcion = "Descripcion Test";
        double precio = 50.0;
        double volumen = 500.0;
        double alcohol = 5.0;
        long idCategoria = 1L;
        Categoria categoria = new Categoria(idCategoria);

        doAnswer(invocation -> {
            return null;
        }).when(itemsMenuController).crearBebida(anyString(), anyString(), anyDouble(), anyDouble(), anyDouble(), any(Categoria.class));

        // Act
        gestionItemsMenu.txtNombre.setText(nombre);
        gestionItemsMenu.txtDescripcion.setText(descripcion);
        gestionItemsMenu.txtPrecio.setText(String.valueOf(precio));
        gestionItemsMenu.txtVolumen.setText(String.valueOf(volumen));
        gestionItemsMenu.txtAlcohol.setText(String.valueOf(alcohol));
        gestionItemsMenu.txtIdCategoria.setText(String.valueOf(idCategoria));
        gestionItemsMenu.btnCrearBebida.doClick();

       
    }

    @Test
    public void testBuscarItem() {
        // Arrange
        long id = 1L;
        ItemMenu item = new Plato(id, "Plato Test", "Descripcion Test", 100.0, new Categoria(1L), 200.0, true, false, 300.0);
        when(itemsMenuController.buscarItem(id)).thenReturn(item);

        // Act
        gestionItemsMenu.txtID.setText(String.valueOf(id));
        gestionItemsMenu.btnBuscar.doClick();

        // Assert
        verify(itemsMenuController, times(1)).buscarItem(id);
        assertTrue(gestionItemsMenu.areaResultados.getText().contains("ID: " + id));
    }

    @Test
    public void testModificarItem() {
        // Arrange
        long id = 1L;
        String nombre = "Nuevo Nombre";
        String descripcion = "Nueva Descripcion";
        double precio = 150.0;
        long idCategoria = 1L;
        Categoria categoria = new Categoria(idCategoria);

        doAnswer(invocation -> {
            return null;
        }).when(itemsMenuController).modificarItem(anyLong(), anyString(), anyString(), anyDouble(), any(Categoria.class));

        // Act
        gestionItemsMenu.txtID.setText(String.valueOf(id));
        gestionItemsMenu.txtNombre.setText(nombre);
        gestionItemsMenu.txtDescripcion.setText(descripcion);
        gestionItemsMenu.txtPrecio.setText(String.valueOf(precio));
        gestionItemsMenu.txtIdCategoria.setText(String.valueOf(idCategoria));
        gestionItemsMenu.btnModificar.doClick();

        
    }

    @Test
    public void testEliminarItem() {
        // Arrange
        long id = 1L;
        doAnswer(invocation -> {
            return null;
        }).when(itemsMenuController).eliminarItem(id);

        // Act
        gestionItemsMenu.txtID.setText(String.valueOf(id));
        gestionItemsMenu.btnEliminar.doClick();

        // Assert
        verify(itemsMenuController, times(1)).eliminarItem(id);
        assertEquals("Item eliminado correctamente.", gestionItemsMenu.areaResultados.getText().trim());
    }

    @Test
    public void testListarItems() {
        // Arrange
        Set<ItemMenu> items = new HashSet<>();
        items.add(new Plato(1L, "Plato Test", "Descripcion Test", 100.0, new Categoria(1L), 200.0, true, false, 300.0));
        when(itemsMenuController.obtenerListaItems()).thenReturn(items);

        // Act
        gestionItemsMenu.btnListar.doClick();

        // Assert
        verify(itemsMenuController, times(1)).obtenerListaItems();
        assertTrue(gestionItemsMenu.areaResultados.getText().contains("Items del Menú:"));
    }
}