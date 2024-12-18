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

public class GestionVendedores extends JFrame {
    VendedorController controlador;
    ItemsMenuController itemsMenuController;
    JTextField txtID, txtNombre, txtDireccion, txtLatitud, txtLongitud;
    JTextArea areaResultados;
    JButton btnCrear, btnBuscar, btnModificar, btnEliminar, btnListar, btnMostrarProductos, btnListarTodosItems, btnAgregarItemMenu;
    JList<ItemMenu> listItemsMenu;
    JButton btnEliminarItemMenu;

    public GestionVendedores(VendedorController controlador, ItemsMenuController itemsMenuController) {
        this.controlador = controlador;
        this.itemsMenuController = itemsMenuController;

        setTitle("Gestión de Vendedores");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new FlowLayout());

        txtID = new JTextField(10);
        txtNombre = new JTextField(15);
        txtDireccion = new JTextField(20);
        txtLatitud = new JTextField(10);
        txtLongitud = new JTextField(10);
        areaResultados = new JTextArea(20, 40);
        areaResultados.setEditable(false);

        btnCrear = new JButton("Crear");
        btnBuscar = new JButton("Buscar");
        btnModificar = new JButton("Modificar");
        btnEliminar = new JButton("Eliminar");
        btnListar = new JButton("Listar");
        btnMostrarProductos = new JButton("Mostrar Productos");
        btnListarTodosItems = new JButton("Listar Todos los Items");
        btnAgregarItemMenu = new JButton("Agregar ItemMenu");
        btnEliminarItemMenu = new JButton("Eliminar ItemMenu");

        listItemsMenu = new JList<>();
        listItemsMenu.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        add(new JLabel("ID:"));
        add(txtID);
        add(new JLabel("Nombre:"));
        add(txtNombre);
        add(new JLabel("Dirección:"));
        add(txtDireccion);
        add(new JLabel("Latitud:"));
        add(txtLatitud);
        add(new JLabel("Longitud:"));
        add(txtLongitud);
        add(btnCrear);
        add(btnBuscar);
        add(btnModificar);
        add(btnEliminar);
        add(btnListar);
        add(btnMostrarProductos);
        add(btnListarTodosItems);
        add(btnAgregarItemMenu);
        add(btnEliminarItemMenu);
        add(new JScrollPane(areaResultados));
        add(new JScrollPane(listItemsMenu));

        btnCrear.addActionListener(this::crearVendedor);
        btnBuscar.addActionListener(this::buscarVendedor);
        btnModificar.addActionListener(this::modificarVendedor);
        btnEliminar.addActionListener(this::eliminarVendedor);
        btnListar.addActionListener(e -> listarVendedores());
        btnMostrarProductos.addActionListener(this::mostrarProductosVendedor);
        btnListarTodosItems.addActionListener(this::listarTodosItems);
        btnAgregarItemMenu.addActionListener(this::agregarItemMenuAVendedor);
        btnEliminarItemMenu.addActionListener(this::eliminarItemMenuDeVendedor);

        setVisible(true);
    }

    private void crearVendedor(ActionEvent e) {
        try {
            String nombre = txtNombre.getText();
            String direccion = txtDireccion.getText();

            // Validar que los campos de latitud y longitud no estén vacíos
            if (txtLatitud.getText().isEmpty() || txtLongitud.getText().isEmpty()) {
                areaResultados.setText("Por favor, completa todos los campos.");
                return; // Detener la ejecución si algún campo está vacío
            }

            // Convertir latitud y longitud a double
            double latitud = Double.parseDouble(txtLatitud.getText());
            double longitud = Double.parseDouble(txtLongitud.getText());

            System.out.println("Latitud: " + latitud);
            System.out.println("Longitud: " + longitud);

            // Crear la coordenada con latitud y longitud (sin ID, se asignará automáticamente)
            Coordenada c1 = new Coordenada(longitud, latitud); // ID de coordenada aún no asignado

            // Guardar la coordenada en la base de datos
            CoordenadaDAO coordenadaDAO = new CoordenadaDAO();
            coordenadaDAO.save(c1); // Esto debería generar un ID para la coordenada
            System.out.println("id " + c1.getId());
            // Crear el vendedor con la coordenada existente o recién creada
            controlador.crearNuevoVendedor(nombre, direccion, c1);
            areaResultados.setText("Vendedor creado: " + nombre);

        } catch (NumberFormatException ex) {
            areaResultados.setText("Por favor, ingresa valores válidos en los campos numéricos.");
            ex.printStackTrace(); // Puedes registrar el error para depuración
        } catch (Exception ex) {
            areaResultados.setText("Ocurrió un error al crear el vendedor: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void buscarVendedor(ActionEvent e) {
        try {
            long id = Long.parseLong(txtID.getText());
            Vendedor vendedor = controlador.buscarVendedor(id);
            if (vendedor != null) {
                txtNombre.setText(vendedor.getNombre());
                txtDireccion.setText(vendedor.getDireccion());
                txtLatitud.setText(String.valueOf(vendedor.getCoor().getLatitud()));
                txtLongitud.setText(String.valueOf(vendedor.getCoor().getLongitud()));
                areaResultados.setText("Vendedor encontrado: " + vendedor.getNombre());
            } else {
                areaResultados.setText("Vendedor no encontrado.");
            }
        } catch (NumberFormatException ex) {
            areaResultados.setText("Por favor, ingresa un ID válido.");
        } catch (Exception ex) {
            areaResultados.setText("Error al buscar el vendedor: " + ex.getMessage());
        }
    }

    private void modificarVendedor(ActionEvent e) {
        try {
            long id = Long.parseLong(txtID.getText());
            String nombre = txtNombre.getText();
            String direccion = txtDireccion.getText();
            double latitud = Double.parseDouble(txtLatitud.getText());
            double longitud = Double.parseDouble(txtLongitud.getText());

            // Fetch the existing Vendedor to get the Coordenada ID
            Vendedor existingVendedor = controlador.buscarVendedor(id);
            if (existingVendedor == null) {
                areaResultados.setText("Vendedor no encontrado.");
                return;
            }

            Coordenada coordenada = existingVendedor.getCoor();
            coordenada.setLatitud(latitud);
            coordenada.setLongitud(longitud);

            Vendedor vendedor = new Vendedor(id, nombre, direccion, coordenada, new HashSet<>());
            boolean exito = controlador.modificarVendedor(vendedor);
            if (exito) {
                areaResultados.setText("Vendedor modificado exitosamente: " + nombre);
            } else {
                areaResultados.setText("Error al modificar el vendedor.");
            }
        } catch (NumberFormatException ex) {
            areaResultados.setText("Por favor, ingresa valores válidos en los campos numéricos.");
        } catch (Exception ex) {
            areaResultados.setText("Error al modificar el vendedor: " + ex.getMessage());
        }
    }

    private void eliminarVendedor(ActionEvent e) {
        try {
            long id = Long.parseLong(txtID.getText());
            controlador.eliminarVendedor(id);
            areaResultados.setText("Vendedor eliminado correctamente.");
        } catch (NumberFormatException ex) {
            areaResultados.setText("Por favor, ingresa un ID válido.");
        } catch (Exception ex) {
            areaResultados.setText("Error al eliminar el vendedor: " + ex.getMessage());
        }
    }

    private void listarVendedores() {
        try {
            Set<Vendedor> vendedores = controlador.obtenerListaVendedores();
            if (vendedores.isEmpty()) {
                areaResultados.setText("No hay vendedores registrados.");
            } else {
                StringBuilder sb = new StringBuilder("Lista de Vendedores:\n");
                for (Vendedor v : vendedores) {
                    sb.append("ID: ").append(v.getId()).append(", Nombre: ").append(v.getNombre())
                      .append(", Dirección: ").append(v.getDireccion()).append("\n");
                }
                areaResultados.setText(sb.toString());
            }
        } catch (Exception ex) {
            areaResultados.setText("Error al listar los vendedores: " + ex.getMessage());
        }
    }

    private void mostrarProductosVendedor(ActionEvent e) {
        try {
            long idVendedor = Long.parseLong(txtID.getText());
            Set<ItemMenu> productos = controlador.obtenerListaProductosVendedor(idVendedor);
            StringBuilder sb = new StringBuilder("Productos del Vendedor:\n");
            for (ItemMenu producto : productos) {
                sb.append("ID: ").append(producto.getId()).append(", Nombre: ").append(producto.getNombre())
                  .append(", Descripción: ").append(producto.getDesc()).append(", Precio: ").append(producto.getPrecio()).append("\n");
            }
            areaResultados.setText(sb.toString());
        } catch (NumberFormatException ex) {
            areaResultados.setText("Por favor, ingresa un ID válido.");
        } catch (Exception ex) {
            areaResultados.setText("Error al mostrar los productos: " + ex.getMessage());
        }
    }

    private void listarTodosItems(ActionEvent e) {
        try {
            Set<ItemMenu> items = itemsMenuController.obtenerListaItems();
            listItemsMenu.setListData(items.toArray(new ItemMenu[0]));
            areaResultados.setText("Items del menú cargados.");
        } catch (Exception ex) {
            areaResultados.setText("Error al listar los items: " + ex.getMessage());
        }
    }

    private void agregarItemMenuAVendedor(ActionEvent e) {
        try {
            long idVendedor = Long.parseLong(txtID.getText());
            for (ItemMenu item : listItemsMenu.getSelectedValuesList()) {
                controlador.agregarItemMenuAVendedor(idVendedor, item.getId());
            }
            areaResultados.setText("Items agregados al vendedor exitosamente.");
        } catch (NumberFormatException ex) {
            areaResultados.setText("Por favor, ingresa un ID válido.");
        } catch (Exception ex) {
            areaResultados.setText("Error al agregar items: " + ex.getMessage());
        }
    }

    private void eliminarItemMenuDeVendedor(ActionEvent e) {
        try {
            long idVendedor = Long.parseLong(txtID.getText());
            for (ItemMenu item : listItemsMenu.getSelectedValuesList()) {
                controlador.eliminarItemMenuDeVendedor(idVendedor, item.getId());
            }
            areaResultados.setText("Items eliminados del vendedor exitosamente.");
        } catch (NumberFormatException ex) {
            areaResultados.setText("Por favor, ingresa un ID válido.");
        } catch (Exception ex) {
            areaResultados.setText("Error al eliminar items: " + ex.getMessage());
        }
    }

    public static void main(String[] args) throws SQLException {
        VendedorMemory vendedorMemory = new VendedorMemory();
        ItemsMenuMemory itemsMenuMemory = new ItemsMenuMemory();
        VendedorController controlador = new VendedorController(vendedorMemory, itemsMenuMemory);
        ItemsMenuController itemsMenuController = new ItemsMenuController(itemsMenuMemory);
        new GestionVendedores(controlador, itemsMenuController);
    }
}
