import React, { useState, useEffect } from 'react';
import axios from 'axios';
import '../App.css'; // Correct the path to App.css

const VendedorForm = ({ vendedor, fetchVendedores, onCancel }) => {
    const [formData, setFormData] = useState({
        nombre: '',
        direccion: '',
        coordenada: { latitud: '', longitud: '' }
    });

    useEffect(() => {
        if (vendedor) {
            setFormData({
                nombre: vendedor.nombre,
                direccion: vendedor.direccion,
                coordenada: vendedor.coordenada || { latitud: '', longitud: '' }
            });
        }
    }, [vendedor]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        if (name.includes('coordenada.')) {
            const key = name.split('.')[1];
            setFormData(prevState => ({
                ...prevState,
                coordenada: {
                    ...prevState.coordenada,
                    [key]: value
                }
            }));
        } else {
            setFormData(prevState => ({
                ...prevState,
                [name]: value
            }));
        }
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        const request = vendedor
            ? axios.put(`http://localhost:8080/vendedores/${vendedor.id}`, formData)
            : axios.post('http://localhost:8080/vendedores', formData);

        request
            .then(() => {
                fetchVendedores();
                setFormData({
                    nombre: '',
                    direccion: '',
                    coordenada: { latitud: '', longitud: '' }
                });
                if (onCancel) onCancel();
            })
            .catch(error => {
                console.error('There was an error saving the vendedor!', error);
            });
    };

    return (
        <form onSubmit={handleSubmit} className="container">
            <h2>{vendedor ? 'Modificar Vendedor' : 'Crear Vendedor'}</h2>
            <div className="entradas">
                <label>Nombre</label>
                <input type="text" name="nombre" value={formData.nombre} onChange={handleChange} placeholder="Nombre" required />
            </div>
            <div className="entradas">
                <label>Dirección</label>
                <input type="text" name="direccion" value={formData.direccion} onChange={handleChange} placeholder="Dirección" required />
            </div>
            <div className="entradas">
                <label>Latitud</label>
                <input type="text" name="coordenada.latitud" value={formData.coordenada.latitud} onChange={handleChange} placeholder="Latitud" required />
            </div>
            <div className="entradas">
                <label>Longitud</label>
                <input type="text" name="coordenada.longitud" value={formData.coordenada.longitud} onChange={handleChange} placeholder="Longitud" required />
            </div>
            <div className="botones-acep-cancel">
                <button type="submit" className="btn-aceptar">{vendedor ? 'Modificar' : 'Crear'}</button>
                {vendedor && <button type="button" className="btn-cancelar" onClick={onCancel}>Cancelar</button>}
            </div>
        </form>
    );
};

export default VendedorForm;
