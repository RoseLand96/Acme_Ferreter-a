/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;
import models.*;
import views.*;
import controllers.*;
import javax.swing.JPanel;
/**
 *
 * @author RoseLandjlord
 */
public class Main {
    public static void main(String[]Rose)
    {
        Object modules[] = new Object[8];
        ViewProductos viewProductos=new  ViewProductos();
        ModelProductos modelProductos= new ModelProductos();
        ControllerProducto  controllerProducto= new  ControllerProducto(modelProductos,viewProductos); 
        
        ViewIniciarSecion viewIniciarSecion=new ViewIniciarSecion();
        ModelLogin modelLogin=new ModelLogin();
        ControllerLogin controllerLogin=new ControllerLogin(modelLogin,viewIniciarSecion,viewProductos);
        
        ModelUsuario modelUsuario=new ModelUsuario();
        ViewUsuario viewUsuario=new ViewUsuario();
        ControllerUsuario controllerUsuario=new ControllerUsuario(modelUsuario,viewUsuario);
        
        ModelVentas modelVentas= new ModelVentas();
        ViewVenta viewVenta= new ViewVenta();
        ControllerVenta controllerVenta=new ControllerVenta(modelVentas,viewVenta);
        
       /* ModelClientes modelClientes = new ModelClientes();
        ViewClientes viewClientes=new ViewClientes();
        ControllerClientes controllerClientes=new ControllerClientes(modelClientes,viewClientes);
        
        ModelCompras modelCompras= new ModelCompras();
        ViewCompras viewCompras= new ViewCompras();
        ControllerCompras controllerCompras=new ControllerCompras(viewCompras,modelCompras);*/
        
        ModelProveedores modelProveedores=new ModelProveedores();
        ViewProveedores viewProveedores=new ViewProveedores();
        ControllerProveedores controllerProveedores=new ControllerProveedores(modelProveedores,viewProveedores);
        
        ModelReporte modelReporte = new ModelReporte();
        ReportePanel reportePanel = new ReportePanel();
        ControllerReporte controllerReporte= new ControllerReporte(modelReporte,reportePanel);
        
        /* JPanel view[]=new JPanel[3];
                view[0]=viewProductos;
                view[1]=viewIniciarSecion;
                view[2]=viewUsuario;*/
         modules[0] = controllerProducto;
         modules[1] = controllerUsuario;
         modules[2] = controllerLogin;
         modules[3] = controllerVenta;
         /*modules[4] = controllerClientes;
         modules[5] =controllerCompras;*/
         modules[6] = controllerProveedores;
         modules[7] = controllerReporte;
    
       ViewMain viewMain= new ViewMain();
       ModelMain modelMain=new ModelMain();
       ControllerMAin controllerMain = new ControllerMAin(modelMain,viewMain,modules);
       controllerMain.init_view();
    }
}
