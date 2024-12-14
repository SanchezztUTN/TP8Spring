import React, { useState, useEffect } from 'react';
import axios from 'axios';
import '../App.css'; // Correct the path to App.css

const ClienteForm = ({ cliente, fetchClientes, onCancel }) => {
    const [formData, setFormData] = useState({
        direccion: '',
        cuit: '',
        email: '',
        alias: '',
        cbu: '',
        coor: { latitud: '', longitud: '' }
    });

    useEffect(() => {
        if (cliente) {
            setFormData({
                direccion: cliente.direccion,
                cuit: cliente.cuit,
                email: cliente.email,
                alias: cliente.alias,
                cbu: cliente.cbu,
                coor: cliente.coor || { latitud: '', longitud: '' }
            });
        }
    }, [cliente]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        if (name.includes('coor.')) {
            const key = name.split('.')[1];
            setFormData(prevState => ({
                ...prevState,
                coor: {
                    ...prevState.coor,
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
        const request = cliente
            ? axios.put(`http://localhost:8080/clientes/${cliente.id}`, formData)
            : axios.post('http://localhost:8080/clientes', formData);

        request
            .then(() => {
                fetchClientes();
                setFormData({
                    direccion: '',
                    cuit: '',
                    email: '',
                    alias: '',
                    cbu: '',
                    coor: { latitud: '', longitud: '' }
                });
                if (onCancel) onCancel();
            })
            .catch(error => {
                console.error('There was an error saving the cliente!', error);
            });
    };

    return (
        <form onSubmit={handleSubmit} className="container">
            <h2>{cliente ? 'Modificar Cliente' : 'Crear Cliente'}</h2>
            <div className="entradas">
                <label>Dirección</label>
                <input type="text" name="direccion" value={formData.direccion} onChange={handleChange} placeholder="Dirección" required />
            </div>
            <div className="entradas">
                <label>CUIT</label>
                <input type="text" name="cuit" value={formData.cuit} onChange={handleChange} placeholder="CUIT" required />
            </div>
            <div className="entradas">
                <label>Email</label>
                <input type="email" name="email" value={formData.email} onChange={handleChange} placeholder="Email" required />
            </div>
            <div className="entradas">
                <label>Alias</label>
                <input type="text" name="alias" value={formData.alias} onChange={handleChange} placeholder="Alias" required />
            </div>
            <div className="entradas">
                <label>CBU</label>
                <input type="text" name="cbu" value={formData.cbu} onChange={handleChange} placeholder="CBU" required />
            </div>
            <div className="entradas">
                <label>Latitud</label>
                <input type="text" name="coor.latitud" value={formData.coor.latitud} onChange={handleChange} placeholder="Latitud" required />
            </div>
            <div className="entradas">
                <label>Longitud</label>
                <input type="text" name="coor.longitud" value={formData.coor.longitud} onChange={handleChange} placeholder="Longitud" required />
            </div>
            <div className="botones-acep-cancel">
                <button type="submit" className="btn-aceptar">{cliente ? 'Modificar' : 'Crear'}</button>
                {cliente && <button type="button" className="btn-cancelar" onClick={onCancel}>Cancelar</button>}
            </div>
        </form>
    );
};

export default ClienteForm;
