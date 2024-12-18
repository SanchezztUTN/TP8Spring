import React, { useEffect, useState } from 'react';
import axios from 'axios';
import PedidoForm from './PedidoForm'; // Import PedidoForm
import '../App.css'; // Correct the path to App.css

const PedidoList = () => {
    const [pedidos, setPedidos] = useState([]);
    const [expandedPedidoId, setExpandedPedidoId] = useState(null);
    const [searchQuery, setSearchQuery] = useState('');
    const [pedidoToEdit, setPedidoToEdit] = useState(null);

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

    const handleEdit = (pedido) => {
        setPedidoToEdit(pedido);
    };

    const handleCancelEdit = () => {
        setPedidoToEdit(null);
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
            {pedidoToEdit ? (
                <PedidoForm fetchPedidos={fetchPedidos} pedidoToEdit={pedidoToEdit} onCancelEdit={handleCancelEdit} />
            ) : (
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
                                <button className="btn-aceptar" onClick={() => handleEdit(pedido)}>Modificar</button>
                            </div>
                            {expandedPedidoId === pedido.id && (
                                <ul className="item-list">
                                    {pedido.items && Array.isArray(pedido.items) && pedido.items.length > 0 ? (
                                        pedido.items.map(item => (
                                            <li key={item.id}>
                                                <p><strong>Nombre:</strong> {item.nombre}</p>
                                                <p><strong>Precio:</strong> ${item.precio}</p>
                                                {item.type === 'bebida' && (
                                                    <>
                                                        <p><strong>Volumen:</strong> {item.volumen} ml</p>
                                                        <p><strong>Graduación Alcohólica:</strong> {item.graduacionAlcoholica}%</p>
                                                    </>
                                                )}
                                                {item.type === 'plato' && (
                                                    <>
                                                        <p><strong>Calorías:</strong> {item.calorias} kcal</p>
                                                        <p><strong>Peso:</strong> {item.peso} g</p>
                                                        <p><strong>Apto Celiacos:</strong> {item.aptoCeliacos ? 'Sí' : 'No'}</p>
                                                        <p><strong>Apto Vegetarianos:</strong> {item.aptoVegetarianos ? 'Sí' : 'No'}</p>
                                                    </>
                                                )}
                                            </li>
                                        ))
                                    ) : (
                                        <p>El pedido no tiene items</p>
                                    )}
                                </ul>
                            )}
                        </li>
                    ))}
                </ul>
            )}
        </div>
    );
};

export default PedidoList;