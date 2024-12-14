import React, { useState, useEffect, useCallback } from 'react';
import axios from 'axios';
import '../App.css'; // Correct the path to App.css

const PedidoForm = ({ fetchPedidos }) => {
    const [pedido, setPedido] = useState({
        vendedorId: '',
        clienteId: '',
        itemMenuIds: [],
        tipoPago: 'MP'
    });
    const [items, setItems] = useState([]);
    const [selectedItems, setSelectedItems] = useState([]);
    const [totalPrice, setTotalPrice] = useState(0);

    useEffect(() => {
        if (pedido.vendedorId) {
            axios.get(`http://localhost:8080/vendedores/${pedido.vendedorId}/items`)
                .then(response => {
                    setItems(response.data);
                })
                .catch(error => {
                    console.error('There was an error fetching the items!', error);
                });
        }
    }, [pedido.vendedorId]);

    const calculateTotalPrice = useCallback(() => {
        const selectedItemsDetails = items.filter(item => selectedItems.includes(item.id.toString()));
        const basePrice = selectedItemsDetails.reduce((total, item) => total + item.precio, 0);
        const recargo = pedido.tipoPago === 'MP' ? 0.4 : 0.2;
        setTotalPrice(basePrice * (1 + recargo));
    }, [items, selectedItems, pedido.tipoPago]);

    useEffect(() => {
        calculateTotalPrice();
    }, [selectedItems, pedido.tipoPago, calculateTotalPrice]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setPedido(prevState => ({
            ...prevState,
            [name]: value
        }));
    };

    const handleItemChange = (e) => {
        const { value, checked } = e.target;
        setSelectedItems(prevState => {
            if (checked) {
                return [...prevState, value];
            } else {
                return prevState.filter(item => item !== value);
            }
        });
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        const itemMenuIdsArray = selectedItems.map(id => parseInt(id, 10));
        axios.post('http://localhost:8080/pedidos', { ...pedido, itemMenuIds: itemMenuIdsArray })
            .then(() => {
                fetchPedidos();
                setPedido({
                    vendedorId: '',
                    clienteId: '',
                    itemMenuIds: [],
                    tipoPago: 'MP'
                });
                setSelectedItems([]);
                setTotalPrice(0);
            })
            .catch(error => {
                console.error('There was an error creating the pedido!', error);
            });
    };

    return (
        <form onSubmit={handleSubmit} className="container">
            <h2>Crear Pedido</h2>
            <div className="entradas">
                <label>Vendedor ID</label>
                <input type="text" name="vendedorId" value={pedido.vendedorId} onChange={handleChange} placeholder="Vendedor ID" required />
            </div>
            <div className="entradas">
                <label>Cliente ID</label>
                <input type="text" name="clienteId" value={pedido.clienteId} onChange={handleChange} placeholder="Cliente ID" required />
            </div>
            <div className="entradas">
                <h3>Items del Vendedor</h3>
                {items.map(item => (
                    <div key={item.id}>
                        <label>
                            <input type="checkbox" value={item.id} onChange={handleItemChange} />
                            {item.nombre} - ${item.precio}
                        </label>
                    </div>
                ))}
            </div>
            <div className="entradas">
                <h3>Tipo de Pago</h3>
                <label>
                    <input type="radio" name="tipoPago" value="MP" checked={pedido.tipoPago === 'MP'} onChange={handleChange} />
                    Mercado Pago (0.4 recargo)
                </label>
                <label>
                    <input type="radio" name="tipoPago" value="Transferencia" checked={pedido.tipoPago === 'Transferencia'} onChange={handleChange} />
                    Transferencia (0.2 recargo)
                </label>
            </div>
            <div>
                <h3>Precio Total: ${totalPrice.toFixed(2)}</h3>
            </div>
            <button type="submit">Crear</button>
        </form>
    );
};

export default PedidoForm;
