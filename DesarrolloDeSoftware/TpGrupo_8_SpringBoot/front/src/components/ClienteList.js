import React, { useState, useEffect } from 'react';
import axios from 'axios';
import ClienteForm from './ClienteForm';
import '../App.css'; // Correct the path to App.css

const ClienteList = () => {
    const [clientes, setClientes] = useState([]);
    const [editingCliente, setEditingCliente] = useState(null);
    const [searchQuery, setSearchQuery] = useState('');

    useEffect(() => {
        fetchClientes();
    }, []);

    const fetchClientes = () => {
        axios.get('http://localhost:8080/clientes')
            .then(response => {
                setClientes(response.data);
            })
            .catch(error => {
                console.error('There was an error fetching the clientes!', error);
            });
    };

    const deleteCliente = (id) => {
        axios.delete(`http://localhost:8080/clientes/${id}`)
            .then(() => {
                fetchClientes();
            })
            .catch(error => {
                console.error('There was an error deleting the cliente!', error);
            });
    };

    const handleEdit = (cliente) => {
        setEditingCliente(cliente);
    };

    const handleCancelEdit = () => {
        setEditingCliente(null);
    };

    const handleSearchChange = (e) => {
        setSearchQuery(e.target.value);
    };

    const filteredClientes = clientes.filter(cliente =>
        cliente.id.toString().includes(searchQuery)
    );

    return (
        <div>
            <h1>Lista de Clientes</h1>
            <input
                type="text"
                placeholder="Buscar por ID"
                value={searchQuery}
                onChange={handleSearchChange}
                className="search-bar"
            />
            {editingCliente ? (
                <ClienteForm cliente={editingCliente} fetchClientes={fetchClientes} onCancel={handleCancelEdit} />
            ) : (
                <ul>
                    {filteredClientes.map(cliente => (
                        <li key={cliente.id}>
                            <p><strong>ID:</strong> {cliente.id}</p>
                            <p><strong>Dirección:</strong> {cliente.direccion}</p>
                            <p><strong>CUIT:</strong> {cliente.cuit}</p>
                            <p><strong>Email:</strong> {cliente.email}</p>
                            <p><strong>Alias:</strong> {cliente.alias}</p>
                            <p><strong>CBU:</strong> {cliente.cbu}</p>
                            <p><strong>Latitud:</strong> {cliente.coor.latitud}</p>
                            <p><strong>Longitud:</strong> {cliente.coor.longitud}</p>
                            <div className="botones-acep-cancel">
                                <button className="btn-aceptar" onClick={() => handleEdit(cliente)}>Modificar</button>
                                <button className="btn-cancelar" onClick={() => deleteCliente(cliente.id)}>Eliminar</button>
                            </div>
                        </li>
                    ))}
                </ul>
            )}
        </div>
    );
};

export default ClienteList;
