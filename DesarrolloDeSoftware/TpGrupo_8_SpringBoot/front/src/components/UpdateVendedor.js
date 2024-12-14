
import React, { useState } from 'react';
import axios from 'axios';

const UpdateVendedor = () => {
    const [vendedor, setVendedor] = useState({
        id: '',
        nombre: '',
        direccion: '',
        coordenadas: {
            latitud: '',
            longitud: ''
        }
    });

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
        axios.put(`http://localhost:8080/vendedores/${vendedor.id}`, vendedor)
            .then(response => {
                console.log(response.data);
                // Optionally, refresh the list or clear the form
            })
            .catch(error => {
                console.error('There was an error updating the vendedor!', error);
            });
    };

    return (
        <div>
            <h1>Actualizar Vendedor</h1>
            <form onSubmit={handleSubmit}>
                <div>
                    <label>ID:</label>
                    <input type="text" name="id" value={vendedor.id} onChange={handleChange} />
                </div>
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
                <button type="submit">Actualizar</button>
            </form>
        </div>
    );
};

export default UpdateVendedor;