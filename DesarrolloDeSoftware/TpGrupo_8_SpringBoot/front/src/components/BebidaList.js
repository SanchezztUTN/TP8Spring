import React, { useState, useEffect } from 'react';
import axios from 'axios';

const BebidaList = () => {
    const [bebidas, setBebidas] = useState([]);

    useEffect(() => {
        axios.get('http://localhost:8080/bebidas')
            .then(response => {
                setBebidas(response.data);
            })
            .catch(error => {
                console.error('There was an error fetching the bebidas!', error);
            });
    }, []);

    return (
        <div>
            <h1>Lista de Bebidas</h1>
            <ul>
                {bebidas.map(bebida => (
                    <li key={bebida.id}>
                        {bebida.nombre} - {bebida.volumen}L - {bebida.graduacionAlcoholica}% - ${bebida.precio}
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default BebidaList;