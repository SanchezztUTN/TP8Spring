import React, { useState } from 'react';
import axios from 'axios';

const AddVendedor = () => {
    const [vendedor, setVendedor] = useState({
        nombre: '',
        direccion: '',
        coordenadas: {
            latitud: '',
            longitud: ''
        }
    });

    const [message, setMessage] = useState('');

    const handleChange = (e) => {
        const { name, value } = e.target;
        if (name === 'latitud' || name === 'longitud') {
            setVendedor({
                ...vendedor,
                coordenadas: {
                    ...vendedor.coordenadas,
                    [name]: value
                }
            });
        } else {
            setVendedor({ ...vendedor, [name]: value });
        }
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        axios.post('http://localhost:8080/vendedores', vendedor)
            .then(response => {
                setMessage('Vendedor agregado exitosamente');
                setVendedor({
                    nombre: '',
                    direccion: '',
                    coordenadas: {
                        latitud: '',
                        longitud: ''
                    }
                });
            })
            .catch(error => {
                setMessage('Error al agregar el vendedor');
                console.error('There was an error adding the vendedor!', error);
            });
    };

    return (
        <div>
            <h1>Agregar Vendedor</h1>
            <form onSubmit={handleSubmit}>
                <div>
                    <label>Nombre:</label>
                    <input type="text" name="nombre" value={vendedor.nombre} onChange={handleChange} />
                </div>
                <div>
                    <label>Dirección:</label>
                    <input type="text" name="direccion" value={vendedor.direccion} onChange={handleChange} />
                </div>
                <div>
                    <label>Latitud:</label>
                    <input type="number" name="latitud" value={vendedor.coordenadas.latitud} onChange={handleChange} />
                </div>
                <div>
                    <label>Longitud:</label>
                    <input type="number" name="longitud" value={vendedor.coordenadas.longitud} onChange={handleChange} />
                </div>
                <button type="submit">Agregar</button>
            </form>
            {message && <p>{message}</p>}
        </div>
    );
};

export default AddVendedor;