import React, { useState, useEffect } from 'react';
import axios from 'axios';
import VendedorForm from './VendedorForm';
import '../App.css'; // Correct the path to App.css

const VendedorList = () => {
    const [vendedores, setVendedores] = useState([]);
    const [items, setItems] = useState([]);
    const [editingVendedor, setEditingVendedor] = useState(null);
    const [selectedItemId, setSelectedItemId] = useState('');
    const [vendedorItems, setVendedorItems] = useState({});
    const [searchQuery, setSearchQuery] = useState('');

    useEffect(() => {
        fetchVendedores();
        fetchItems();
    }, []);

    const fetchVendedores = () => {
        axios.get('http://localhost:8080/vendedores')
            .then(response => {
                setVendedores(response.data);
            })
            .catch(error => {
                console.error('There was an error fetching the vendedores!', error);
            });
    };

    const fetchItems = () => {
        axios.get('http://localhost:8080/items')
            .then(response => {
                setItems(response.data);
            })
            .catch(error => {
                console.error('There was an error fetching the items!', error);
            });
    };

    const fetchVendedorItems = (vendedorId) => {
        axios.get(`http://localhost:8080/vendedores/${vendedorId}/items`)
            .then(response => {
                setVendedorItems(prevState => ({
                    ...prevState,
                    [vendedorId]: response.data
                }));
            })
            .catch(error => {
                console.error('There was an error fetching the vendedor items!', error);
            });
    };

    const deleteVendedor = (id) => {
        axios.delete(`http://localhost:8080/vendedores/${id}`)
            .then(() => {
                fetchVendedores();
            })
            .catch(error => {
                console.error('There was an error deleting the vendedor!', error);
            });
    };

    const handleEdit = (vendedor) => {
        setEditingVendedor(vendedor);
    };

    const handleCancelEdit = () => {
        setEditingVendedor(null);
    };

    const handleItemChange = (e) => {
        setSelectedItemId(e.target.value);
    };

    const addItemToVendedor = (vendedorId) => {
        const selectedItem = items.find(item => item.id === parseInt(selectedItemId));
        axios.post(`http://localhost:8080/vendedores/${vendedorId}/items`, selectedItem)
            .then(() => {
                fetchVendedores();
                setSelectedItemId('');
            })
            .catch(error => {
                console.error('There was an error adding the item to the vendedor!', error);
            });
    };

    const handleSearchChange = (e) => {
        setSearchQuery(e.target.value);
    };

    const filteredVendedores = vendedores.filter(vendedor =>
        vendedor.nombre.toLowerCase().includes(searchQuery.toLowerCase())
    );

    return (
        <div>
            <h1 className="header">Lista de Vendedores</h1>
            <input
                type="text"
                placeholder="Buscar por nombre"
                value={searchQuery}
                onChange={handleSearchChange}
                className="search-bar"
            />
            {editingVendedor ? (
                <VendedorForm vendedor={editingVendedor} fetchVendedores={fetchVendedores} onCancel={handleCancelEdit} />
            ) : (
                <ul>
                    {filteredVendedores.map(vendedor => (
                        <li key={vendedor.id} className="vendedor-container">
                            <p><strong>Nombre:</strong> {vendedor.nombre}</p>
                            <p><strong>Dirección:</strong> {vendedor.direccion}</p>
                            <p><strong>Latitud:</strong> {vendedor.coordenada.latitud }</p>
                            <p><strong>Longitud:</strong> {vendedor.coordenada.longitud }</p>
                            <div className="botones-acep-cancel">
                                <button className="btn-aceptar" onClick={() => handleEdit(vendedor)}>Modificar</button>
                                <button className="btn-cancelar" onClick={() => deleteVendedor(vendedor.id)}>Eliminar</button>
                            </div>
                            <div className="botones-acep-cancel">
                                <h3>Agregar ItemMenu</h3>
                                <select value={selectedItemId} onChange={handleItemChange}>
                                    <option value="">Seleccione un Item</option>
                                    {items.map(item => (
                                        <option key={item.id} value={item.id}>
                                            {item.nombre} - ${item.precio}
                                        </option>
                                    ))}
                                </select>
                                <button className="btn-aceptar" onClick={() => addItemToVendedor(vendedor.id)}>Agregar Item</button>
                            </div>
                            <div className="botones-acep-cancel">
                                <button className="btn-aceptar" onClick={() => fetchVendedorItems(vendedor.id)}>Ver Items</button>
                                {vendedorItems[vendedor.id] && (
                                    <ul className="item-list">
                                        {vendedorItems[vendedor.id].map(item => (
                                            <li key={item.id}>
                                                <p><strong>Nombre:</strong> {item.nombre}</p>
                                                <p><strong>Precio:</strong> ${item.precio}</p>
                                                <p><strong>Tipo:</strong> {item.type}</p>
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
                                        ))}
                                    </ul>
                                )}
                            </div>
                        </li>
                    ))}
                </ul>
            )}
        </div>
    );
};

export default VendedorList;