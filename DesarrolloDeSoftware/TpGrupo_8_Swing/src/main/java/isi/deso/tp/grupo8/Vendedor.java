/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.tp.grupo8;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Vendedor {
    private long id;
    private String nombre;
    private String direccion;
    private Coordenada coordenadas;
    private Set<ItemMenu> listaProductos;
    private List<Pedido> pedidos; // Lista para almacenar los pedidos

    public Vendedor(long id, String nombre, String direc, Coordenada coor, Set<ItemMenu> lista) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direc;
        this.coordenadas = coor;
        this.listaProductos = (lista != null) ? lista : new HashSet<>();
        this.pedidos = new ArrayList<>(); // Inicializar la lista de pedidos
    }

    public Vendedor() {
        this.listaProductos = new HashSet<>();
        this.pedidos = new ArrayList<>(); // Inicializar la lista de pedidos
    }

    public void setCoordenada(Coordenada coor) {
        this.coordenadas = coor;
    }

    // Método para agregar un pedido a la lista
    public void agregarPedido(Pedido pedido) {
        if (pedidos == null) {
            pedidos = new ArrayList<>();
        }
        pedidos.add(pedido);
        System.out.println("Pedido agregado id: " + pedido.getId() + " al vendedor: " + this.nombre);
    }

    public void agregarItemMenu(ItemMenu item) {
        if (listaProductos == null) {
            listaProductos = new HashSet<>();
        }
        listaProductos.add(item);
    }

    public long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public Coordenada getCoor() {
        return coordenadas;
    }

    public Set<ItemMenu> getList() {
        return listaProductos;
    }
    public void setListaProductos(Set<ItemMenu> lista){
        this.listaProductos = lista;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public Set<Long> getIdsPedidos() {
        Set<Long> idsPedidos = new HashSet<>(); // Usamos un Set para evitar duplicados
    
        for (Pedido pedido : pedidos) {
            idsPedidos.add(pedido.getId()); // Agregamos el ID de cada pedido
        }
    
        return idsPedidos; // Devolvemos el conjunto de IDs
    }

    public Set<Pedido> getPedidosPorEstado(EstadoPedido estadoBuscado) {
        Set<Pedido> pedidosFiltrados = new HashSet<>();
    
        for (Pedido pedido : pedidos) {
            if (pedido.getEstado() == estadoBuscado) {
                pedidosFiltrados.add(pedido);
            }
        }
    
        return pedidosFiltrados;
    }

    public void actualizarEstadoPedido(Pedido pedido, EstadoPedido nuevoEstado) {
        if (pedidos.contains(pedido)) {
            pedido.setEstado(nuevoEstado); // Asumiendo que tienes un método setEstado en la clase Pedido
            System.out.println("El estado del pedido " + pedido.getId() + " ha sido actualizado a " + nuevoEstado);
        } else {
            System.out.println("El pedido no pertenece a este vendedor.");
        }
    }

    
     public void setNombre(String n){
        this.nombre = n;
    }
     public void setId(long i){
        this.id = i;
    }
      public void setDireccion(String Direc){
        this.direccion = Direc;
    }
    
    public double distancia(Cliente cliente){
        Coordenada coordVend = this.coordenadas; //ESTAS SON COORDENADAS EN GRADOS
        Coordenada coordClient = cliente.getCoor(); //ESTAS SON COORDENADAS EN GRADOS
        double phi1 = Math.toRadians(coordVend.getLatitud()); //ESTAS SON COORDENADAS EN RADIANES
        double lamb1 = Math.toRadians(coordVend.getLongitud()); //ESTAS SON COORDENADAS EN RADIANES
        double phi2 = Math.toRadians(coordClient.getLatitud()); //ESTAS SON COORDENADAS EN RADIANES
        double lamb2 = Math.toRadians(coordClient.getLongitud()); //ESTAS SON COORDENADAS EN RADIANES
        //System.out.println("phi1: "+phi1+" ,phi2: "+phi2+" ,lam1: "+lamb1+" ,lamb2:"+lamb2);
        double r = 6371;
        double deltaPhi = (phi2 - phi1);
        double deltaLam = (lamb2 - lamb1);
        double distancia;
        double a = Math.pow(Math.sin(deltaPhi / 2), 2) +
                   Math.cos(phi1) * Math.cos(phi2) * Math.pow(Math.sin(deltaLam / 2), 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        // Calcular distancia Total en KM.
        distancia = r * c;
        return distancia;
    }

    public Set getComidas(){
        Set<ItemMenu> comidas = new HashSet<>();
        for(ItemMenu producto: this.listaProductos){
            if(producto.esComida()){
                comidas.add(producto);
            }
        }
        return comidas;
    }

    public Set getBebidas(){
        Set<ItemMenu> bebidas = new HashSet<>();
        for(ItemMenu producto: this.listaProductos){
            if(producto.esBebida()){
                bebidas.add(producto);
            }
        }
        return bebidas;
    }
       
    public boolean bebidaSinAlcohol() {
        // Obtenemos el conjunto de bebidas sin alcohol
        Set<ItemMenu> bebidasSinAlcohol = this.getBebidasSinAlcohol();
        
        // Usamos StringBuilder para armar la cadena de salida
        StringBuilder resultado = new StringBuilder("Lista de Bebidas sin Alcohol asociados al vendedor "+this.getNombre()+" : [");
    
        // Iteramos sobre el conjunto usando un bucle for-each
        for (ItemMenu bebida : bebidasSinAlcohol) {
            resultado.append(bebida.nombre).append(", ");
        }
    
        // Eliminamos la última coma y el espacio adicional
        if (resultado.length() > 21) { // 21 es la longitud de "Bebidas sin Alcohol: ["
            resultado.setLength(resultado.length() - 2);
        }
    
        // Cerramos el formato de la lista con corchete
        resultado.append("]");
    
        // Mostramos el resultado final
        System.out.println(resultado.toString());
    
        return true;
    }

    public void comidaVegana() {
        Set<ItemMenu> comidasVeganas = this.getComidasVegana();
    
        StringBuilder resultado = new StringBuilder("Lista de Comida vegana asociados al vendedor "+this.getNombre()+" : [");
        for (ItemMenu plato : comidasVeganas) {
            resultado.append(plato).append(", "); 
        }
    
        if (resultado.length() > 17) { // 17 es la longitud de "Comidas veganas: ["
            resultado.setLength(resultado.length() - 2);
        }
        resultado.append("]");
        System.out.println(resultado.toString());
    }
 
    public Set getComidasVegana(){
            Set<ItemMenu> comidaVegana = new HashSet<>();
            for(ItemMenu producto: this.listaProductos){
                if(producto.aptoVegano()){
                    comidaVegana.add(producto);
                }
            }
            return comidaVegana;
        }

    public Set<ItemMenu> getBebidasSinAlcohol() {
        Set<ItemMenu> BebidasSinAlcohol = new HashSet<>();
        for (ItemMenu producto : this.listaProductos) {
            if (producto instanceof Bebida && !producto.esAlcoholica()) {
                BebidasSinAlcohol.add(producto);
            }
        }
        return BebidasSinAlcohol;
    }

    public void mostrarProductos() {
        StringBuilder resultado = new StringBuilder("Lista de Productos asociados al vendedor "+this.getNombre()+" : [");
    
        for (ItemMenu item : listaProductos) {
            resultado.append(item.nombre).append(", ");
        }
 
        if (resultado.length() > 20) { 
            resultado.setLength(resultado.length() - 2);
        }
    
        resultado.append("]");
    
        System.out.println(resultado.toString());
    }

    
  
}