import React from 'react';
import { BrowserRouter as Router, Route, Routes, Link } from 'react-router-dom';
import ClienteList from './components/ClienteList';
import ClienteForm from './components/ClienteForm';
import VendedorList from './components/VendedorList';
import VendedorForm from './components/VendedorForm';
import PedidoList from './components/PedidoList';
import PedidoForm from './components/PedidoForm';
import ItemMenuList from './components/ItemMenuList';
import ItemMenuForm from './components/ItemMenuForm';
import logo from './images/Logo.jpeg'; // Import the logo image
import './App.css';

const App = () => {
    return (
        <Router>
            <div>
                <header className="App-header">
                    <nav>
                        <ul className="menu-ul">
                            <li><Link to="/clientes">Clientes</Link></li>
                            <li><Link to="/vendedores">Vendedores</Link></li>
                            <li><Link to="/pedidos">Pedidos</Link></li>
                            <li><Link to="/items">Items Menu</Link></li>
                        </ul>
                    </nav>
                </header>
                <div className="main-content">
                    <div className="form-container">
                        <Routes>
                            <Route path="/clientes" element={<ClienteForm />} />
                            <Route path="/vendedores" element={<VendedorForm />} />
                            <Route path="/pedidos" element={<PedidoForm />} />
                            <Route path="/items" element={<ItemMenuForm />} />
                            <Route path="/" element={<><h2>Bienvenido a la Gestión de Restaurante</h2></>} />
                        
                        </Routes>
                    </div>
                    <div className="logo-container">
                        <img src={logo} className="logo" alt="Logo" />
                    </div>
                    <div className="list-container">
                        <Routes>
                            <Route path="/clientes" element={<ClienteList />} />
                            <Route path="/vendedores" element={<VendedorList />} />
                            <Route path="/pedidos" element={<PedidoList />} />
                            <Route path="/items" element={<ItemMenuList />} />
                            <Route path="/" element={<><h2>Bienvenido a la Gestión de Restaurante</h2></>} />
                        </Routes>
                    </div>
                </div>
            </div>
        </Router>
    );
};

export default App;