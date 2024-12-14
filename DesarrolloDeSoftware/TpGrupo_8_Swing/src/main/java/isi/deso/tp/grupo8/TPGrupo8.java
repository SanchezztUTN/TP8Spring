package isi.deso.tp.grupo8;


import java.sql.Connection;
import java.util.HashSet;
import java.util.Set;

public class TPGrupo8 {
   /*public static Vendedor [] eliminarVendedor(Vendedor [] v, Vendedor vAEliminar){
        int i = 0;
        int k=0;
        int tam = v.length;
        while(i < tam){
            if(v[i] ==  vAEliminar){
                System.out.println("\n");
                System.out.println("El vendedor: "+ v[i].getNombre()+" ha sido eliminado \n");
                tam--;
                for(int j = i; j < tam; j++){
                    v[j] = v[j+1];
                     
                }
            }
        i++;
        }
        Vendedor[] nuevoArreglo = new Vendedor[tam];
         while(k < tam){
             nuevoArreglo[k] = v[k];
             k++;
            }
         return nuevoArreglo;
         }
       
    public static Vendedor buscarVendedor(Vendedor [] v, String nombreOid){
        int i = 0;
        while(i < v.length){
          if(((v[i].getId()).equals(nombreOid)) || ((v[i].getNombre()).equals(nombreOid))){
            System.out.println("ID del vendedor: "+ v[i].getId()+"\n"
                    + "Nombre del Vendedor: " +v[i].getNombre()+"\n"
                    + "Direccion del vendedor: "+ v[i].getDireccion()+"\n"
                    + "Coordenadas de latitud "+ (v[i].getCoor()).getLatitud()+"Id coordenada es: "+ (v[i].getCoor()).getId()+ "\n" 
                    + "Coordenadas de Longitud: "+(v[i].getCoor()).getLongitud());
                    return v[i];
                    //i = v.length;
            }
          i++;
        }
        if(i == v.length) System.out.println("No existe el VENDEDOR"); 
             return null;
        
    }
    
    public static Cliente [] eliminarCliente(Cliente [] v, Cliente cAEliminar){
        int i = 0;
        int k=0;
        int tam = v.length;
        while(i < tam){
            if(v[i] ==  cAEliminar){
                System.out.println("\n");
                System.out.println("El cliente: "+ v[i].getId()+" ha sido eliminado \n");
                tam--;
                for(int j = i; j < tam; j++){
                    v[j] = v[j+1];
                }
            }
        i++;
        }
        Cliente[] nuevoArreglo = new Cliente[tam];
         while(k < tam){
             nuevoArreglo[k] = v[k];
             k++;
            }
         return nuevoArreglo;
         }
    
    public static Cliente buscarCliente(Cliente [] v, String id){
        int i = 0;
        while(i < v.length){
          if(((v[i].getId()).equals(id)) || ((v[i].getId()).equals(id))){
            System.out.println("ID del cliente: "+ v[i].getId()+"\n"
                    + "CUIT del cliente: "+ v[i].getCuit()+"\n"
                    + "Email del cliente: "+ v[i].getEmail()+"\n"
                    + "Direccion del cliente: "+ v[i].getDireccion()+"\n"
                    + "Coordenadas de latitud "+ (v[i].getCoor()).getLatitud()+"\n" 
                    + "Coordenadas de Longitud: "+(v[i].getCoor()).getLongitud() +"\n"
                    + "Alias del clinete: " + v[i].getAlias() +"\n"
                    + "CBU del cliente: " + v[i].getCbu());
                    return v[i];
                    //i = v.length;
            }
          i++;
        }
        if(i == v.length) System.out.println("No existe el CLIENTE"); 
             return null;
        
    }
    public static void main(String[] args) throws ItemNoEncontradoException {
        // INSTANCIA 1
        //-----------------------------VENDEDOR------------------------------------------------------
        //coordenadas vendedor
        Coordenada coorV1 = new Coordenada (3.18f,7.47f);
        Coordenada coorV2 = new Coordenada (1.25f,4.89f); //LATITUD LONGITUD
        Coordenada coorV3 = new Coordenada (6.59f,12.1f);
        //Lista
        Set<ItemMenu> productos = new HashSet<>();
        Set<ItemMenu> productos2 = new HashSet<>();
        Set<ItemMenu> productos3 = new HashSet<>();
        Set<ItemMenu> productos4 = new HashSet<>();
        //vendedores
        Vendedor v1 = new Vendedor ("v00001", "Horacio", "Iriondo 1582", coorV1,productos);  //EL ULTIMO ATRUBUTO ES LA LISTA DE PRODUCTOS ASOCIADO A ESE VENDEDOR
        Vendedor v2 = new Vendedor ("v00002", "Marcelo", "Misiones 492", coorV2,productos2);
        Vendedor v3 = new Vendedor ("v00003", "Florencia", "Santa Fe 2123", coorV3,productos3);
        Vendedor v4 = new Vendedor ("v00004", "Rodrigo", "Tacural 2123", coorV3,productos4);
        //vector de vendedores
        Vendedor[] vendedores = new Vendedor[4];
        vendedores[0] = v1;
        vendedores[1] = v2;
        vendedores[2] = v3;
        vendedores[3] = v4;
        //busqueda por nombre o id
        System.out.println("BUSCAR VENDEDORES ---"); 
        buscarVendedor(vendedores, "Florencia");
        //buscarVendedor(vendedores, "Horacio");
        //buscarVendedor(vendedores, "Rodrigo");
        buscarVendedor(vendedores, "Marcelo");
        System.out.println("\n");
        System.out.println("ELIMINAMOS UN VENDEDOR ---"); 
        vendedores = eliminarVendedor(vendedores, buscarVendedor(vendedores,"Marcelo"));
        //---------------------------------------------CLIENTE----------------------------------------------  
        //coordenadas cliente
        Coordenada coorC1 = new Coordenada (45.3f,99.3f);
        Coordenada coorC2 = new Coordenada (18.9f,78.6f);
        Coordenada coorC3 = new Coordenada (65.36f,91.5f);
        //clientes 
        //Cliente(String id, String cuit, String email, String direc, Coordenada coor, String alias, String cbu)
        Cliente c1 = new Cliente ("c00001", "20-30495842-7", "jAlvarez@gmail.com","San Martin 1523", coorC1,"JS","1234");
        Cliente c2 = new Cliente ("c00002", "27-15378964-3", "juanPerez25@outlook.es","9 de Julio 2876", coorC2,"JS","1234");
        Cliente c3 = new Cliente ("c00003", "20-27894561-9", "lopezLopezCarlos@hotmail.com"," Gobernador Crespo 4201", coorC3,"JS","1234");
       //vector de clientes
        Cliente[] clientes = new Cliente[3];
        clientes[0] = c1;
        clientes[1] = c2;
        clientes[2] = c3;
        System.out.println("\n");
        //buscar clientes
        System.out.println("BUSCAR CLIENTE ---");
        buscarCliente(clientes, "c00003");
         buscarCliente(clientes, "c00001");
        //System.out.println("\n");
        //eliminar cliente por id
        System.out.println("\n");
        System.out.println("ELIMINAMOS UN CLIENTE ---"); 
        clientes= eliminarCliente(clientes,buscarCliente(clientes,"c00001"));
        //imprimir distancia en km
        System.out.println("DISTANCIA EN KILOMETROS ENTRE VENDEDOR Y CLIENTE ---"); 
        System.out.println("La distancia es de: "+v2.distancia(c2)+"Km");

        //INSTANCIA 2
        //System.out.println("PLATOS ---"); 
        Plato plato1 = new Plato(15,false,false,"Bife",15, "as");
        Plato plato2 = new Plato(15,true,true,"Lechuga",69,"as");
        Plato plato3 = new Plato(23,true,false,"Pizza",12,"as");
        Plato plato4 = new Plato(18,false,true,"Wok de verduras",225.5,"as");
        Plato plato5 = new Plato(20,false,false,"Alfajor",10,"as");
        Plato plato6 = new Plato(36,false,false,"Papa Frita",39,"as");
        //System.out.println("BEBIDAS ---"); 
        Bebida bebida1 = new Bebida(40, 450,"Vodka",25,"as");
        Bebida bebida2 = new Bebida(0, 450,"Manaos",30,"as");
        Bebida bebida3 = new Bebida(0, 500,"Limonada",33,"as");
        Bebida bebida4 = new Bebida(39,750,"Fernet con Coca",150,"as");
        Bebida bebida5 = new Bebida(0,750,"Agua Tonica",70,"as");
        Bebida bebida6 = new Bebida(0,500,"Pepsi",60,"as");

        //Productos asignados a Vendedor 1
        productos.add(bebida1);
        productos.add(bebida2);
        productos.add(plato1);
        productos.add(plato2);
        //Productos asignados a Vendedor 2
        productos2.add(bebida3);
        productos2.add(bebida4);
        productos2.add(plato3);
        productos2.add(plato4);
        //Productos asignados a Vendedor 4
        productos4.add(bebida5);
        productos4.add(bebida6);
        productos4.add(plato5);
        productos4.add(plato6);
        
        System.out.println("PESO DEL PEDIDO Y CLASIFICACION ---"); 
        System.out.println("El peso del plato es: "+plato1.peso(15));
        plato1.esComida();
        plato1.esBebida();
        System.out.println("El peso de la bebida es: "+bebida1.peso(15));
        bebida1.esComida();
        bebida1.esBebida();
        System.out.println(); 
        System.out.println("Comidas asociadas al vendedor "+v2.getNombre()+": "+v2.getComidas()); //IMPRIME LISTA DE COMIDAS QUE VENDE ESE VENDEDOR
        System.out.println("Bebibdas asociadas al vendedor "+v2.getNombre()+": "+v2.getBebidas()); //IMPRIME LISTA DE BEBIDAS QUE VENDE ESE VENDEDOR
        System.out.println(); 
        
        v2.mostrarProductos();                              //IMPRIME LISTA TOTAL QUE VENDE ESE VENDEDOR
        v2.bebidaSinAlcohol();                             //IMPRIME LISTA DE BEBIDAS SIN ALCOHOL QUE VENDE ESE VENDEDOR
        v2.comidaVegana();                                //IMPRIME LISTA DE COMIDA VEGANA QUE VENDE ESE VENDEDOR
        System.out.println("\n");
        
        //INSTANCIA 3
        ItemPedido p1 = new ItemPedido(plato1);
        ItemPedido p2 = new ItemPedido(plato2);
        ItemPedido p3 = new ItemPedido(plato3);
        ItemPedido p4 = new ItemPedido(plato4);
        ItemPedido p5 = new ItemPedido(plato5);
        ItemPedido p6 = new ItemPedido(plato6);
        ItemPedido b1 = new ItemPedido(bebida1);
        ItemPedido b2 = new ItemPedido(bebida2);
        ItemPedido b3 = new ItemPedido(bebida3);
        ItemPedido b4 = new ItemPedido(bebida4);
        ItemPedido b5 = new ItemPedido(bebida5);
        ItemPedido b6 = new ItemPedido(bebida6);
        
        //PEDIDO QUE SE ASIGNA AL VENDEDOR 1
        ItemsPedidoMemory ipm1 = new ItemsPedidoMemory();
        ipm1.agregarItem(p1);
        ipm1.agregarItem(p2);
        ipm1.agregarItem(b1);
        ipm1.agregarItem(b2);
        //PEDIDO QUE SE ASIGNA AL VENDEDOR 2
        ItemsPedidoMemory ipm2 = new ItemsPedidoMemory();
        ipm2.agregarItem(p3);
        ipm2.agregarItem(p4);
        ipm2.agregarItem(b3);
        ipm2.agregarItem(b4);
        //PEDIDO QUE SE ASIGNA AL VENDEDOR 4
        ItemsPedidoMemory ipm4 = new ItemsPedidoMemory();
        ipm4.agregarItem(p5);
        ipm4.agregarItem(p6);
        ipm4.agregarItem(b5);
        ipm4.agregarItem(b6);
        //Pedido(String id, Cliente c, ItemsPedidoMemory ip, Vendedor v, String metodo, Pago p, EstadoPedido e)
        Pedido ped1 = new Pedido("1",c1, ipm1,v1,"Mercado Pago");
        // Suscribir cliente al pedido
        ped1.addObserver(c1);
        Pedido ped2 = new Pedido("2",c2, ipm2,v2,"Transferencia");
        // Suscribir cliente al pedido
        ped2.addObserver(c2);
        Pedido ped3 = new Pedido("3",c3, ipm4,v4,"Mercado Pago" );
        // Suscribir cliente al pedido
        ped3.addObserver(c3);
        Pedido ped11 = new Pedido("11", c1, ipm1, v1,"Mercado Pago");
        ped11.addObserver(c1);
        System.out.println("BUSCAR POR RANGO DE PRECIOS ---");
        ipm1.busquedaPorRangodePecios(0, 700);
        //v2.mostrarProductos(); // EJEMPLO, ESTE VENDEDOR TIENE MUCHOS ITEMS A SU VENTA
        System.out.println("ORDENAR POR CRITERIO ---");
        ipm2.ordenarPorCriterio(); // ORDENA ALFABETICAMENTE
         //en el argumento se coloca el tipo de plato (Comida, Bebida, Comida Vegetariana y bebidas Alcoholicas), y filtra por aquellas que cumplen la condicion
        System.out.println("FILTRAR ---");
        ipm2.filtrado('C');  // filtra por COMIDAS
        ipm2.filtrado('A'); //filtra por BEBIDAS ALCOHOLICAS
        ipm2.filtrado('B'); //filtra por BEBIDAS
        ipm2.filtrado('V'); //filtra por COMIDA VEGANA
        System.out.println("BUSCAR POR RESTAURANTE ---");
        ipm2.buscarPorRestaurante(v2); // BUSCA QUE VENDEDOR, CUMPLE CON TODOS LOS ITEMS A PROVEER , ESTOS ITEMS SON LOS QUE ESTAN DENTRO DE DE LA INSTANCIA DE ItemsPedidosMemory

        
        // VERIFICACION PARA MP
        //Verifica si el precio acumulado es el correcto----OK!
        System.out.println("\n");
        System.out.println("El costo total del pedido2 es: $"+ipm2.calcularTotal()); 
        //Compruebo el monto tatal a pagay y si el estado cambia -----OK!
        ped2.estadoDelPedido();
        System.err.println("Costo al pagar con MP: $"+ped2.pagarConMP("JoaquinSola.MP"));
        
        ped2.estadoDelPedido();

        // VERIFICACION PARA TRANSFERENCIA
        //Verifica si el precio acumulado es el correcto----OK!
        System.out.println("\n");
        System.out.println("El costo total del pedido1 es: $"+ipm1.calcularTotal()); 
        //Compruebo el monto tatal a pagay y si el estado cambia -----OK!
        ped1.estadoDelPedido();
        System.err.println("Costo al pegar con TRANSFERENCIA: $"+ped1.pagarConTransferencia("123456","20202020"));
        ped1.estadoDelPedido();

        //ETAPA 5
        System.out.println("ETAPA 5--- ");
        ped11.setEstado(EstadoPedido.PENDIENTE);
        System.out.println("IDs de pedidos asociados al vendedor "+v1.getNombre() +": "+ v1.getIdsPedidos());
        // Verificar y mostrar pedidos PENDIENTES
        mostrarPedidosPendientes(v1);
        mostrarPedidosPendientes(v2);
        mostrarPedidosPendientes(v3);
        // Actualizar estado de un pedido específico
        actualizarEstadoPedido(v1, ped1, EstadoPedido.RECIBIDO);
        actualizarEstadoPedido(v2, ped2, EstadoPedido.EN_ENVIO);
        actualizarEstadoPedido(v4, ped3, EstadoPedido.EN_ENVIO);
        CoordenadaDAO dao = new CoordenadaDAO();
        Coordenada coord1 = new Coordenada(12.5, 22.3);
        dao.save(coord1);

        System.out.println("Coordenada guardada con ID: " + coord1.getId()); // ID generado por la base de

    }  















    // Método para mostrar pedidos pendientes
    private static void mostrarPedidosPendientes(Vendedor vendedor) {
        Set<Pedido> pedidosPendientes = vendedor.getPedidosPorEstado(EstadoPedido.PENDIENTE);
        // Muestra el primer pedido pendiente, si existe
        if (!pedidosPendientes.isEmpty()) {
            Pedido pedidoPendiente = pedidosPendientes.iterator().next(); // Toma un pedido pendiente
            System.out.println("Pedido pendiente encontrado para el vendedor "+vendedor.getNombre()+ ", pedido ID: " + pedidoPendiente.getId());
        } else {
            System.out.println("No hay pedidos pendientes del vendedor "+vendedor.getNombre());
        }
    }

    // Método para actualizar estado de un pedido
    private static void actualizarEstadoPedido(Vendedor vendedor, Pedido pedido, EstadoPedido nuevoEstado) {
        vendedor.actualizarEstadoPedido(pedido, nuevoEstado);
    }
    */
    
    
   
}