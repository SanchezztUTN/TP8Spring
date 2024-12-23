package com.example.isi.deso.tp_7;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {
    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    public PedidoDTO crearPedido(@RequestBody PedidoRequest pedidoRequest) {
        Pedido pedido = new Pedido();
        Pedido createdPedido = pedidoService.crearPedido(
            pedido,
            pedidoRequest.getVendedorId(),
            pedidoRequest.getClienteId(),
            pedidoRequest.getItemMenuIds(),
            pedidoRequest.getTipoPago()
        );
        return new PedidoDTO(createdPedido);
    }

    @GetMapping("/{id}")
    public Optional<PedidoDTO> buscarPedido(@PathVariable long id) {
        return pedidoService.buscarPedido(id).map(PedidoDTO::new);
    }

    @PutMapping("/{id}")
    public PedidoDTO actualizarPedido(@PathVariable long id, @RequestBody PedidoRequest pedidoRequest) {
        Pedido pedido = pedidoService.buscarPedido(id).orElseThrow(() -> new IllegalArgumentException("Pedido with ID " + id + " does not exist."));
        Pedido updatedPedido = pedidoService.actualizarPedido(
            pedido,
            pedidoRequest.getVendedorId(),
            pedidoRequest.getClienteId(),
            pedidoRequest.getItemMenuIds(),
            pedidoRequest.getTipoPago()
        );
        return new PedidoDTO(updatedPedido);
    }

    @DeleteMapping("/{id}")
    public void eliminarPedido(@PathVariable long id) {
        pedidoService.eliminarPedido(id);
    }

    @GetMapping
    public List<PedidoDTO> listarPedidos() {
        return pedidoService.listarPedidos().stream().map(PedidoDTO::new).collect(Collectors.toList());
    }

    @GetMapping("/{id}/items")
    public Set<ItemMenu> listarItemsPedido(@PathVariable long id) {
        return pedidoService.listarItemsPedido(id);
    }
}

