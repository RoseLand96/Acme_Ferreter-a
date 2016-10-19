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
        ViewProductos viewProductos=new  ViewProductos();
        ModelProductos modelProductos= new ModelProductos();
        ControllerProducto  controllerProducto= new  ControllerProducto(modelProductos,viewProductos); 
        controllerProducto.Conectar();
        
         JPanel view[]=new JPanel[2];
                view[0]=viewProductos;
               // view[1]=viewVolumen;
    
       ViewMain viewMain= new ViewMain();
       ModelMain modelMain=new ModelMain();
       ControllerMAin controllerMain = new ControllerMAin(modelMain,viewMain,view);
       controllerMain.init_view();
    }
}
