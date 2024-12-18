package isi.deso.tp.grupo8;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class GestionItemsMenu extends JFrame {
    final ItemsMenuController controlador;
     JTextField txtID, txtNombre, txtDescripcion, txtPrecio, txtCalorias, txtVolumen, txtAlcohol, txtPeso, txtIdCategoria;
     JCheckBox chkCeliacos, chkVegetariano;
     JTextArea areaResultados;
     JButton btnCrearPlato, btnCrearBebida, btnBuscar, btnModificar, btnEliminar, btnListar;

    public GestionItemsMenu(ItemsMenuController controlador) {
        this.controlador = controlador;
        initComponents();
    }

    private void initComponents() {
        setTitle("Gestión de Items del Menú");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        txtID = new JTextField(10);
        txtNombre = new JTextField(10);
        txtDescripcion = new JTextField(10);
        txtPrecio = new JTextField(10);
        txtCalorias = new JTextField(10);
        txtVolumen = new JTextField(10);
        txtAlcohol = new JTextField(10);
        txtPeso = new JTextField(10);
        txtIdCategoria = new JTextField(10);
        chkCeliacos = new JCheckBox("Apto Celiacos");
        chkVegetariano = new JCheckBox("Apto Vegetariano");
        areaResultados = new JTextArea(20, 40);
        areaResultados.setEditable(false);

        btnCrearPlato = new JButton("Crear Plato");
        btnCrearBebida = new JButton("Crear Bebida");
        btnBuscar = new JButton("Buscar");
        btnModificar = new JButton("Modificar");
        btnEliminar = new JButton("Eliminar");
        btnListar = new JButton("Listar");

        add(new JLabel("ID:"));
        add(txtID);
        add(new JLabel("Nombre:"));
        add(txtNombre);
        add(new JLabel("Descripción:"));
        add(txtDescripcion);
        add(new JLabel("Precio:"));
        add(txtPrecio);
        add(new JLabel("Calorías:"));
        add(txtCalorias);
        add(new JLabel("Volumen:"));
        add(txtVolumen);
        add(new JLabel("Alcohol:"));
        add(txtAlcohol);
        add(new JLabel("Peso:"));
        add(txtPeso);
        add(new JLabel("ID Categoría:"));
        add(txtIdCategoria);
        add(chkCeliacos);
        add(chkVegetariano);
        add(btnCrearPlato);
        add(btnCrearBebida);
        add(btnBuscar);
        add(btnModificar);
        add(btnEliminar);
        add(btnListar);
        add(new JScrollPane(areaResultados));

        btnCrearPlato.addActionListener(this::crearPlato);
        btnCrearBebida.addActionListener(this::crearBebida);
        btnBuscar.addActionListener(this::buscarItem);
        btnModificar.addActionListener(this::modificarItem);
        btnEliminar.addActionListener(this::eliminarItem);
        btnListar.addActionListener(this::listarItems);
    }

    private void crearPlato(ActionEvent e) {
        String nombre = txtNombre.getText();
        String descripcion = txtDescripcion.getText();
        double precio = Double.parseDouble(txtPrecio.getText());
        double calorias = Double.parseDouble(txtCalorias.getText());
        boolean celiacos = chkCeliacos.isSelected();
        boolean vegetariano = chkVegetariano.isSelected();
        double peso = Double.parseDouble(txtPeso.getText());
        long idCategoria = Long.parseLong(txtIdCategoria.getText());
        Categoria categoria = new Categoria(idCategoria);

        controlador.crearPlato(nombre, descripcion, precio, calorias, celiacos, vegetariano, peso, categoria);
        areaResultados.setText("Plato creado exitosamente. Añadido correctamente.");
    }

    private void crearBebida(ActionEvent e) {
        String nombre = txtNombre.getText();
        String descripcion = txtDescripcion.getText();
        double precio = Double.parseDouble(txtPrecio.getText());
        double volumen = Double.parseDouble(txtVolumen.getText());
        double alcohol = Double.parseDouble(txtAlcohol.getText());
        long idCategoria = Long.parseLong(txtIdCategoria.getText());
        Categoria categoria = new Categoria(idCategoria);

        controlador.crearBebida(nombre, descripcion, precio, volumen, alcohol, categoria);
        areaResultados.setText("Bebida creada exitosamente. Añadido correctamente.");
    }

    private void buscarItem(ActionEvent e) {
        try {
            long id = Long.parseLong(txtID.getText());
            ItemMenu item = controlador.buscarItem(id);
            if (item != null) {
                StringBuilder sb = new StringBuilder();
                sb.append("ID: ").append(item.getId()).append("\n")
                  .append("Nombre: ").append(item.getNombre()).append("\n")
                  .append("Descripción: ").append(item.getDesc()).append("\n")
                  .append("Precio: $").append(item.getPrecio()).append("\n")
                  .append("Categoría: ").append(item.getCategoria() != null ? item.getCategoria().toString() : "Sin categoría").append("\n")
                  .append("Es comida: ").append(item.esComida() ? "Sí" : "No").append("\n")
                  .append("Es bebida: ").append(item.esBebida() ? "Sí" : "No").append("\n")

                  .append("Es alcohólica: ").append(item.esAlcoholica() ? "Sí" : "No").append("\n");

                if (item instanceof Plato) {
                    Plato plato = (Plato) item;
                    sb.append("Calorías: ").append(plato.getCalorias()).append("\n")
                      .append("Apto Celiacos: ").append(plato.getAptoC() ? "Sí" : "No").append("\n")
                      .append("Apto Vegetariano: ").append(plato.getAptoV() ? "Sí" : "No").append("\n")
                      .append("Peso: ").append(plato.getPeso()).append("\n");
                } else if (item instanceof Bebida) {
                    Bebida bebida = (Bebida) item;
                    sb.append("Volumen: ").append(bebida.getVol()).append("\n")
                      .append("Graduación Alcohólica: ").append(bebida.getGradA()).append("\n");
                }

                areaResultados.setText(sb.toString());
            } else {
                areaResultados.setText("Item no encontrado.");
            }
        } catch (NumberFormatException ex) {
            areaResultados.setText("Por favor, ingresa un ID válido.");
        } catch (Exception ex) {
            areaResultados.setText("Error al buscar el item: " + ex.getMessage());
        }
    }

    private void modificarItem(ActionEvent e) {
        try {
            long id = Long.parseLong(txtID.getText());
            String nombre = txtNombre.getText();
            String descripcion = txtDescripcion.getText();
            double precio = Double.parseDouble(txtPrecio.getText());
            long idCategoria = Long.parseLong(txtIdCategoria.getText());
            Categoria categoria = new Categoria(idCategoria);

            controlador.modificarItem(id, nombre, descripcion, precio, categoria);
            areaResultados.setText("Item modificado exitosamente.");
        } catch (NumberFormatException ex) {
            areaResultados.setText("Por favor, ingresa valores válidos.");
        } catch (Exception ex) {
            areaResultados.setText("Error al modificar el item: " + ex.getMessage());
        }
    }

    private void eliminarItem(ActionEvent e) {
        try {
            long id = Long.parseLong(txtID.getText());
            controlador.eliminarItem(id);
            areaResultados.setText("Item eliminado correctamente.");
        } catch (NumberFormatException ex) {
            areaResultados.setText("Por favor, ingresa un ID válido.");
        } catch (Exception ex) {
            areaResultados.setText("Error al eliminar el item: " + ex.getMessage());
        }
    }

  private void listarItems(ActionEvent e) {
    try {
        Set<ItemMenu> items = controlador.obtenerListaItems();
        if (items.isEmpty()) {
            areaResultados.setText("No hay items en el menú registrados.");
        } else {
            StringBuilder sb = new StringBuilder("Items del Menú:\n");
            for (ItemMenu item : items) {
                sb.append("ID: ").append(item.getId()).append("\n")
                  .append("Nombre: ").append(item.getNombre()).append("\n")
                  .append("Descripción: ").append(item.getDesc()).append("\n")
                  .append("Precio: $").append(item.getPrecio()).append("\n")
                  .append("Categoría: ").append(item.getCategoria() != null ? item.getCategoria().toString() : "Sin categoría").append("\n")
                  .append("Es comida: ").append(item.esComida() ? "Sí" : "No").append("\n")
                  .append("Es bebida: ").append(item.esBebida() ? "Sí" : "No").append("\n")
                  .append("--------------------------------------\n");
            }
            areaResultados.setText(sb.toString());
        }
    } catch (Exception ex) {
        areaResultados.setText("Error al listar los items: " + ex.getMessage());
    }
}

   public static void main(String[] args) throws SQLException {
        ItemMenuDAO itemsMenuMemory = new ItemsMenuMemory();
        ItemsMenuController controlador = new ItemsMenuController(itemsMenuMemory);
        GestionItemsMenu frame = new GestionItemsMenu(controlador);
        frame.setVisible(true);
    }


}
