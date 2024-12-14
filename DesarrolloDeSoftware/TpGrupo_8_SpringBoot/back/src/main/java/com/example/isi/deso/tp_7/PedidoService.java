package com.example.isi.deso.tp_7;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {
    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private VendedorRepository vendedorRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ItemMenuRepository itemMenuRepository;

    @Autowired
    private PagoRepository pagoRepository;

    public Pedido crearPedido(Pedido pedido, long vendedorId, long clienteId, List<Long> itemMenuIds, String tipoPago) {
        // Validate Vendedor
        Optional<Vendedor> vendedorOpt = vendedorRepository.findById(vendedorId);
        if (!vendedorOpt.isPresent()) {
            throw new IllegalArgumentException("Vendedor with ID " + vendedorId + " does not exist.");
        }
        Vendedor vendedor = vendedorOpt.get();

        // Validate Cliente
        Optional<Cliente> clienteOpt = clienteRepository.findById(clienteId);
        if (!clienteOpt.isPresent()) {
            throw new IllegalArgumentException("Cliente with ID " + clienteId + " does not exist.");
        }
        Cliente cliente = clienteOpt.get();

        // Validate and calculate total price
        double totalPrice = 0;
        for (Long itemId : itemMenuIds) {
            Optional<ItemMenu> itemOpt = itemMenuRepository.findById(itemId);
            if (!itemOpt.isPresent()) {
                throw new IllegalArgumentException("ItemMenu with ID " + itemId + " does not exist.");
            }
            ItemMenu item = itemOpt.get();
            if (!vendedor.getList().contains(item)) {
                throw new IllegalArgumentException("ItemMenu with ID " + itemId + " is not sold by Vendedor with ID " + vendedorId);
            }
            totalPrice += item.getPrecio();
            ItemPedido itemPedido = new ItemPedido(item);
            itemPedido.setPedido(pedido);
            pedido.getItemsPedidoMemory().add(itemPedido);
        }

        // Create Pago
        Pago pago;
        if ("MP".equalsIgnoreCase(tipoPago)) {
            pago = new PagoMP(totalPrice=totalPrice * 1.4, cliente.getAlias());
            pedido.setMetodoDePago("MP");
        } else if ("Transferencia".equalsIgnoreCase(tipoPago)) {
            pago = new PagoTransferencia(totalPrice=totalPrice* 1.2, cliente.getCbu(), cliente.getCuit());
            pedido.setMetodoDePago("Transferencia");
        } else {
            throw new IllegalArgumentException("Invalid payment type: " + tipoPago);
        }
        pago = pagoRepository.save(pago);

        // Set Pedido details
        pedido.setVendedor(vendedor);
        pedido.setCliente(cliente);
        pedido.setPago(pago);
        pedido.setTotalPrice(totalPrice);

        return pedidoRepository.save(pedido);
    }

    public Optional<Pedido> buscarPedido(long id) {
        return pedidoRepository.findById(id);
    }

    public Pedido actualizarPedido(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    public void eliminarPedido(long id) {
        pedidoRepository.deleteById(id);
    }

    public List<Pedido> listarPedidos() {
        return pedidoRepository.findAll();
    }

    public List<ItemPedido> listarItemsPedido(long pedidoId) {
        Optional<Pedido> pedidoOpt = pedidoRepository.findById(pedidoId);
        if (!pedidoOpt.isPresent()) {
            throw new IllegalArgumentException("Pedido with ID " + pedidoId + " does not exist.");
        }
        return pedidoOpt.get().getItemsPedidoMemory();
    }
}
