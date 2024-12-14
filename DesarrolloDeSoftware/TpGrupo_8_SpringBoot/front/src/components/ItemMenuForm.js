import React, { useState, useEffect } from 'react';
import axios from 'axios';
import '../App.css'; // Correct the path to App.css

const ItemMenuForm = ({ item, fetchItems, onCancel }) => {
    const [formData, setFormData] = useState({
        nombre: '',
        precio: '',
        type: 'plato', // or 'bebida'
        volumen: '',
        graduacionAlcoholica: '',
        calorias: '',
        peso: '',
        aptoCeliacos: false,
        aptoVegetarianos: false,
        descripcion: '',
        categoriaId: ''
    });
    const [categorias, setCategorias] = useState([]);

    useEffect(() => {
        if (item) {
            setFormData({
                nombre: item.nombre,
                precio: item.precio,
                type: item.type,
                volumen: item.volumen || '',
                graduacionAlcoholica: item.graduacionAlcoholica || '',
                calorias: item.calorias || '',
                peso: item.peso || '',
                aptoCeliacos: item.aptoCeliacos || false,
                aptoVegetarianos: item.aptoVegetarianos || false,
                descripcion: item.descripcion,
                categoriaId: item.categoria ? item.categoria.id_categoria : ''
            });
        }
        fetchCategorias();
    }, [item]);

    const fetchCategorias = () => {
        axios.get('http://localhost:8080/categorias')
            .then(response => {
                setCategorias(response.data);
            })
            .catch(error => {
                console.error('There was an error fetching the categorias!', error);
            });
    };

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData(prevState => ({
            ...prevState,
            [name]: value
        }));
    };

    const handleCheckboxChange = (e) => {
        const { name, checked } = e.target;
        setFormData(prevState => ({
            ...prevState,
            [name]: checked
        }));
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        const endpoint = formData.type === 'plato' ? 'plato' : 'bebida';
        const payload = { 
            ...formData, 
            type: formData.type,
            categoria: categorias.find(categoria => categoria.id_categoria === parseInt(formData.categoriaId))
        };
        const request = item
            ? axios.put(`http://localhost:8080/items/${item.id}`, payload)
            : axios.post(`http://localhost:8080/items/${endpoint}`, payload);

        request
            .then(() => {
                fetchItems();
                setFormData({
                    nombre: '',
                    precio: '',
                    type: 'plato',
                    volumen: '',
                    graduacionAlcoholica: '',
                    calorias: '',
                    peso: '',
                    aptoCeliacos: false,
                    aptoVegetarianos: false,
                    descripcion: '',
                    categoriaId: ''
                });
                if (onCancel) onCancel();
            })
            .catch(error => {
                console.error('There was an error saving the item!', error);
            });
    };

    return (
        <form onSubmit={handleSubmit} className="container">
            <h2>{item ? 'Modificar Item' : 'Crear Item'}</h2>
            <div className="entradas">
                <label>Nombre</label>
                <input type="text" name="nombre" value={formData.nombre} onChange={handleChange} placeholder="Nombre" required />
            </div>
            <div className="entradas">
                <label>Precio</label>
                <input type="text" name="precio" value={formData.precio} onChange={handleChange} placeholder="Precio" required />
            </div>
            <div className="entradas">
                <label>Descripción</label>
                <input type="text" name="descripcion" value={formData.descripcion} onChange={handleChange} placeholder="Descripción" required />
            </div>
            <div className="entradas">
                <label>Categoria</label>
                <select name="categoriaId" value={formData.categoriaId} onChange={handleChange} required>
                    <option value="">Seleccione una Categoria</option>
                    {categorias.map(categoria => (
                        <option key={categoria.id_categoria} value={categoria.id_categoria}>
                            {categoria.descripcion}
                        </option>
                    ))}
                </select>
            </div>
            <div className="entradas">
                <label>Tipo</label>
                <select name="type" value={formData.type} onChange={handleChange}>
                    <option value="plato">Plato</option>
                    <option value="bebida">Bebida</option>
                </select>
            </div>
            {formData.type === 'bebida' && (
                <>
                    <div className="entradas">
                        <label>Volumen</label>
                        <input type="text" name="volumen" value={formData.volumen} onChange={handleChange} placeholder="Volumen" required />
                    </div>
                    <div className="entradas">
                        <label>Graduación Alcohólica</label>
                        <input type="text" name="graduacionAlcoholica" value={formData.graduacionAlcoholica} onChange={handleChange} placeholder="Graduación Alcohólica" required />
                    </div>
                </>
            )}
            {formData.type === 'plato' && (
                <>
                    <div className="entradas">
                        <label>Calorías</label>
                        <input type="text" name="calorias" value={formData.calorias} onChange={handleChange} placeholder="Calorías" required />
                    </div>
                    <div className="entradas">
                        <label>Peso</label>
                        <input type="text" name="peso" value={formData.peso} onChange={handleChange} placeholder="Peso" required />
                    </div>
                    <div className="entradas">
                        <label>
                            Apto Celiacos
                            <input type="checkbox" name="aptoCeliacos" checked={formData.aptoCeliacos} onChange={handleCheckboxChange} />
                        </label>
                    </div>
                    <div className="entradas">
                        <label>
                            Apto Vegetarianos
                            <input type="checkbox" name="aptoVegetarianos" checked={formData.aptoVegetarianos} onChange={handleCheckboxChange} />
                        </label>
                    </div>
                </>
            )}
            <div className="botones-acep-cancel">
                <button type="submit" className="btn-aceptar">{item ? 'Modificar' : 'Crear'}</button>
                {item && <button type="button" className="btn-cancelar" onClick={onCancel}>Cancelar</button>}
            </div>
        </form>
    );
};

export default ItemMenuForm;
