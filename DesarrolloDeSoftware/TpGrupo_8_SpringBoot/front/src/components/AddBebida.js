import React, { useState } from 'react';
import axios from 'axios';

const AddBebida = () => {
    const [bebida, setBebida] = useState({
        nombre: '',
        volumen: '',
        graduacionAlcoholica: '',
        precio: '',
        descripcion: ''
    });

    const handleChange = (e) => {
        const { name, value } = e.target;
        setBebida({ ...bebida, [name]: value });
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        axios.post('http://localhost:8080/bebidas', bebida)
            .then(response => {
                console.log(response.data);
                // Optionally, refresh the list or clear the form
            })
            .catch(error => {
                console.error('There was an error adding the bebida!', error);
            });
    };

    return (
        <div>
            <h1>Agregar Bebida</h1>
            <form onSubmit={handleSubmit}>
                <div>
                    <label>Nombre:</label>
                    <input type="text" name="nombre" value={bebida.nombre} onChange={handleChange} />
                </div>
                <div>
                    <label>Volumen (L):</label>
                    <input type="number" name="volumen" value={bebida.volumen} onChange={handleChange} />
                </div>
                <div>
                    <label>Graduación Alcohólica (%):</label>
                    <input type="number" name="graduacionAlcoholica" value={bebida.graduacionAlcoholica} onChange={handleChange} />
                </div>
                <div>
                    <label>Precio:</label>
                    <input type="number" name="precio" value={bebida.precio} onChange={handleChange} />
                </div>
                <div>
                    <label>Descripción:</label>
                    <input type="text" name="descripcion" value={bebida.descripcion} onChange={handleChange} />
                </div>
                <button type="submit">Agregar</button>
            </form>
        </div>
    );
};

export default AddBebida;