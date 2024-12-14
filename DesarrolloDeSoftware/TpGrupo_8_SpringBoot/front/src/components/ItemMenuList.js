import React, { useEffect, useState } from 'react';
import axios from 'axios';
import ItemMenuForm from './ItemMenuForm';
import '../App.css'; // Correct the path to App.css

const ItemMenuList = () => {
    const [items, setItems] = useState([]);
    const [editingItem, setEditingItem] = useState(null);
    const [searchQuery, setSearchQuery] = useState('');

    useEffect(() => {
        fetchItems();
    }, []);

    const fetchItems = () => {
        axios.get('http://localhost:8080/items')
            .then(response => {
                setItems(response.data);
            })
            .catch(error => {
                console.error('There was an error fetching the items!', error);
            });
    };

    const deleteItem = (id) => {
        axios.delete(`http://localhost:8080/items/${id}`)
            .then(() => {
                fetchItems();
            })
            .catch(error => {
                console.error('There was an error deleting the item!', error);
            });
    };

    const handleEdit = (item) => {
        setEditingItem(item);
    };

    const handleCancelEdit = () => {
        setEditingItem(null);
    };

    const handleSearchChange = (e) => {
        setSearchQuery(e.target.value);
    };

    const filteredItems = items.filter(item =>
        item.nombre.toLowerCase().includes(searchQuery.toLowerCase())
    );

    return (
        <div>
            <h1>Lista de Items</h1>
            <input
                type="text"
                placeholder="Buscar por nombre"
                value={searchQuery}
                onChange={handleSearchChange}
                className="search-bar"
            />
            {editingItem ? (
                <ItemMenuForm item={editingItem} fetchItems={fetchItems} onCancel={handleCancelEdit} />
            ) : (
                <ul>
                    {filteredItems.map(item => (
                        <li key={item.id}>
                            <p><strong>Nombre:</strong> {item.nombre}</p>
                            <p><strong>Precio:</strong> ${item.precio}</p>
                            <p><strong>Descripción:</strong> {item.descripcion}</p>
                            <p><strong>Categoria:</strong> {item.categoria ? item.categoria.descripcion : 'Sin Categoria'}</p>
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
                            <div className="botones-acep-cancel">
                                <button className="btn-aceptar" onClick={() => handleEdit(item)}>Modificar</button>
                                <button className="btn-cancelar" onClick={() => deleteItem(item.id)}>Eliminar</button>
                            </div>
                        </li>
                    ))}
                </ul>
            )}
        </div>
    );
};

export default ItemMenuList;