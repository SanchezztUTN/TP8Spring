import React, { useEffect, useState } from 'react';
import axios from 'axios';
import '../App.css'; // Correct the path to App.css

const PedidoList = () => {
    const [pedidos, setPedidos] = useState([]);
    const [expandedPedidoId, setExpandedPedidoId] = useState(null);
    const [searchQuery, setSearchQuery] = useState('');

    useEffect(() => {
        fetchPedidos();
    }, []);

    const fetchPedidos = () => {
        axios.get('http://localhost:8080/pedidos')
            .then(response => {
                setPedidos(response.data);
            })
            .catch(error => {
                console.error('There was an error fetching the pedidos!', error);
            });
    };

    const deletePedido = (id) => {
        axios.delete(`http://localhost:8080/pedidos/${id}`)
            .then(() => {
                fetchPedidos();
            })
            .catch(error => {
                console.error('There was an error deleting the pedido!', error);
            });
    };

    const toggleExpand = (pedidoId) => {
        setExpandedPedidoId(expandedPedidoId === pedidoId ? null : pedidoId);
    };

    const handleSearchChange = (e) => {
        setSearchQuery(e.target.value);
    };

    const filteredPedidos = pedidos.filter(pedido =>
        pedido.id.toString().includes(searchQuery)
    );

    return (
        <div>
            <h1 className="header">Lista de Pedidos</h1>
            <input
                type="text"
                placeholder="Buscar por ID"
                value={searchQuery}
                onChange={handleSearchChange}
                className="search-bar"
            />
            <ul>
                {filteredPedidos.map(pedido => (
                    <li key={pedido.id} className="pedido-container">
                        <p><strong>ID:</strong> {pedido.id}</p>
                        <p><strong>Cliente ID:</strong> {pedido.cliente.id}</p>
                        <p><strong>Vendedor ID:</strong> {pedido.vendedor.id}</p>
                        <p><strong>Tipo de Pago:</strong> {pedido.metodoDePago}</p>
                        <p><strong>Total Price:</strong> ${pedido.totalPrice}</p>
                        <div className="botones-acep-cancel">
                            <button className="btn-aceptar" onClick={() => deletePedido(pedido.id)}>Eliminar</button>
                            <button className="btn-aceptar" onClick={() => toggleExpand(pedido.id)}>Ver Items</button>
                        </div>
                        {expandedPedidoId === pedido.id && (
                            <ul className="item-list">
                                {pedido.itemsPedidoMemory.map(itemPedido => (
                                    <li key={itemPedido.id}>
                                        <p><strong>Nombre:</strong> {itemPedido.itemPedido.nombre}</p>
                                        <p><strong>Precio:</strong> ${itemPedido.itemPedido.precio}</p>
                                        {itemPedido.itemPedido.type === 'bebida' && (
                                            <>
                                                <p><strong>Volumen:</strong> {itemPedido.itemPedido.volumen} ml</p>
                                                <p><strong>Graduación Alcohólica:</strong> {itemPedido.itemPedido.graduacionAlcoholica}%</p>
                                            </>
                                        )}
                                        {itemPedido.itemPedido.type === 'plato' && (
                                            <>
                                                <p><strong>Calorías:</strong> {itemPedido.itemPedido.calorias} kcal</p>
                                                <p><strong>Peso:</strong> {itemPedido.itemPedido.peso} g</p>
                                                <p><strong>Apto Celiacos:</strong> {itemPedido.itemPedido.aptoCeliacos ? 'Sí' : 'No'}</p>
                                                <p><strong>Apto Vegetarianos:</strong> {itemPedido.itemPedido.aptoVegetarianos ? 'Sí' : 'No'}</p>
                                            </>
                                        )}
                                    </li>
                                ))}
                            </ul>
                        )}
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default PedidoList;