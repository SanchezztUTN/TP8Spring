package isi.deso.tp.grupo8;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class GestionClientes extends JFrame {
    private final ClienteController controlador;
    private JTextField txtID, txtCuit, txtEmail, txtDireccion, txtAlias, txtCbu, txtLatitud, txtLongitud;
    private JTextArea areaResultados;
    private JButton btnCrear, btnBuscar, btnModificar, btnEliminar, btnListar;

    public GestionClientes(ClienteController controlador) {
        this.controlador = controlador;

        setTitle("Gestión de Clientes");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new FlowLayout());

        txtID = new JTextField(10);
        txtCuit = new JTextField(15);
        txtEmail = new JTextField(20);
        txtDireccion = new JTextField(20);
        txtAlias = new JTextField(10);
        txtCbu = new JTextField(10);
        txtLatitud = new JTextField(10);
        txtLongitud = new JTextField(10);
        areaResultados = new JTextArea(15, 40);
        areaResultados.setEditable(false);

        btnCrear = new JButton("Crear");
        btnBuscar = new JButton("Buscar");
        btnModificar = new JButton("Modificar");
        btnEliminar = new JButton("Eliminar");
        btnListar = new JButton("Listar");

        add(new JLabel("ID:"));
        add(txtID);
        add(new JLabel("CUIT:"));
        add(txtCuit);
        add(new JLabel("Email:"));
        add(txtEmail);
        add(new JLabel("Dirección:"));
        add(txtDireccion);
        add(new JLabel("Alias:"));
        add(txtAlias);
        add(new JLabel("CBU:"));
        add(txtCbu);
        add(new JLabel("Latitud:"));
        add(txtLatitud);
        add(new JLabel("Longitud:"));
        add(txtLongitud);
        add(btnCrear);
        add(btnBuscar);
        add(btnModificar);
        add(btnEliminar);
        add(btnListar);
        add(new JScrollPane(areaResultados));

        btnCrear.addActionListener(this::crearCliente);
        btnBuscar.addActionListener(this::buscarCliente);
        btnModificar.addActionListener(this::modificarCliente);
        btnEliminar.addActionListener(this::eliminarCliente);
        btnListar.addActionListener(e -> listarClientes());

        setVisible(true);
    }

    private void crearCliente(ActionEvent e) {
        try {
            String cuit = txtCuit.getText();
            String email = txtEmail.getText();
            String direccion = txtDireccion.getText();
            String alias = txtAlias.getText();
            String cbu = txtCbu.getText();
            double latitud = Double.parseDouble(txtLatitud.getText());
            double longitud = Double.parseDouble(txtLongitud.getText());
            Coordenada coordenadas = new Coordenada(latitud, longitud);
            CoordenadaDAO coordenadaDAO = new CoordenadaDAO();
            coordenadaDAO.save(coordenadas); // Esto debería generar un ID para la coordenada

            controlador.crearNuevoCliente(cuit, email, direccion, coordenadas, alias, cbu);
            areaResultados.setText("Cliente creado.");
        } catch (NumberFormatException ex) {
            areaResultados.setText("Por favor, ingresa valores válidos para latitud y longitud.");
        } catch (Exception ex) {
            areaResultados.setText("Error al crear el cliente: " + ex.getMessage());
        }
    }

    private void buscarCliente(ActionEvent e) {
        try {
            long id = Long.parseLong(txtID.getText());
            Cliente cliente = controlador.buscarCliente(id);

            if (cliente != null) {
                StringBuilder sb = new StringBuilder();
                sb.append("ID: ").append(cliente.getId()).append("\n")
                  .append("CUIT: ").append(cliente.getCuit()).append("\n")
                  .append("Email: ").append(cliente.getEmail()).append("\n")
                  .append("Dirección: ").append(cliente .getDireccion()).append("\n")
                  .append("Alias: ").append(cliente.getAlias()).append("\n")
                  .append("CBU: ").append(cliente.getCbu()).append("\n")
                  .append("Coordenadas: (").append(cliente.getCoor().getLatitud()).append(", ")
                  .append(cliente.getCoor().getLongitud()).append(")\n");
                areaResultados.setText(sb.toString());
            } else {
                areaResultados.setText("Cliente con ID " + id + " no encontrado.");
            }
        } catch (NumberFormatException ex) {
            areaResultados.setText("Por favor, ingresa un ID válido.");
        } catch (Exception ex) {
            areaResultados.setText("Error al buscar el cliente: " + ex.getMessage());
        }
    }

   private boolean modificarCliente(ActionEvent e) {
    try {
        // Buscar el cliente por ID
        Cliente cliente = controlador.buscarCliente(Long.parseLong(txtID.getText()));
        
        if (cliente == null) {
            areaResultados.setText("Cliente no encontrado. Verifica el ID.");
            return false;
        }

        // Actualizar los datos del cliente
        cliente.setCuit(txtCuit.getText());
        cliente.setEmail(txtEmail.getText());
        cliente.setDireccion(txtDireccion.getText());
        cliente.setAlias(txtAlias.getText());
        cliente.setCbu(txtCbu.getText());

        // Actualizar coordenadas
        Coordenada coord = cliente.getCoor();
        coord.setLatitud(Double.parseDouble(txtLatitud.getText()));
        coord.setLongitud(Double.parseDouble(txtLongitud.getText()));
        cliente.setCoor(coord); // Asociar la coordenada al cliente

        // Modificar el cliente a través del controlador
        boolean modificado = controlador.modificarCliente(cliente.getId(), cliente.getCuit(), cliente.getEmail(), cliente.getDireccion(), coord, cliente.getAlias(), cliente.getCbu());

        // Mostrar mensaje en el área de resultados
        if (modificado) {
            areaResultados.setText("Cliente modificado exitosamente.");
        } else {
            areaResultados.setText("No se pudo modificar el cliente. Verifica el ID.");
        }
    } catch (NumberFormatException ex) {
        areaResultados.setText("Por favor, ingresa datos válidos en los campos.");
    } catch (Exception ex) {
        areaResultados.setText("Ocurrió un error al modificar el cliente: " + ex.getMessage());
    }
    return true;
}

    private void eliminarCliente(ActionEvent e) {
        try {
            long id = Long.parseLong(txtID.getText());
            controlador.eliminarCliente(id);
            areaResultados.setText("Cliente eliminado.");
        } catch (NumberFormatException ex) {
            areaResultados.setText("Por favor, ingresa un ID válido.");
        } catch (Exception ex) {
            areaResultados.setText("Error al eliminar el cliente: " + ex.getMessage());
        }
    }

    private void listarClientes() {
        Set<Cliente> clientes = controlador.obtenerListaClientes(); // Obtener la lista desde el controlador

        if (clientes.isEmpty()) {
            areaResultados.setText("No hay clientes registrados.");
        } else {
            StringBuilder sb = new StringBuilder("Clientes:\n");
            for (Cliente cliente : clientes) {
                sb.append("ID: ").append(cliente.getId()).append("\n")
                  .append("CUIT: ").append(cliente.getCuit()).append("\n")
                  .append("Email: ").append(cliente.getEmail()).append("\n")
                  .append("Dirección: ").append(cliente.getDireccion()).append("\n")
                  .append("Alias: ").append(cliente.getAlias()).append("\n")
                  .append("CBU: ").append(cliente.getCbu()).append("\n")
                  .append("Coordenadas: (").append(cliente.getCoor().getLatitud()).append(", ")
                  .append(cliente.getCoor().getLongitud()).append(")\n")
                  .append("--------------------------------------\n");
            }
            areaResultados.setText(sb.toString());
        }
    }
    public static void main(String[] args) throws SQLException {
       ClienteMemory cliente = new ClienteMemory();
        ClienteController controlador = new ClienteController(cliente);
        new GestionClientes(controlador);
    }
}