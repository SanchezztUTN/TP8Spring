import React, { useState } from 'react';
import axios from 'axios';

const DeleteVendedor = () => {
    const [id, setId] = useState('');

    const handleChange = (e) => {
        setId(e.target.value);
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        axios.delete(`http://localhost:8080/vendedores/${id}`)
            .then(response => {
                console.log(response.data);
                // Optionally, refresh the list or clear the form
            })
            .catch(error => {
                console.error('There was an error deleting the vendedor!', error);
            });
    };

    return (
        <div>
            <h1>Eliminar Vendedor</h1>
            <form onSubmit={handleSubmit}>
                <div>
                    <label>ID:</label>
                    <input type="text" name="id" value={id} onChange={handleChange} />
                </div>
                <button type="submit">Eliminar</button>
            </form>
        </div>
    );
};

export default DeleteVendedor;