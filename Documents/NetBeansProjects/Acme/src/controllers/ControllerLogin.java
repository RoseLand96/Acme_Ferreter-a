/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import models.ModelLogin;
import views.ViewIniciarSecion;
import views.ViewProductos;
import views.ViewMain;
import sax.DBConnection;
import java.sql.ResultSet; 
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
/**
 *
 * @author RoseLandjlord
 */
public class ControllerLogin implements ActionListener {
    private DBConnection conection = new DBConnection(3306,"localhost", "acme", "root", "");
     ModelLogin modelLogin;
     ViewIniciarSecion viewIniciarSecion;
     ViewProductos viewProductos;
     ViewMain viewMain;
    private Statement st;     
    private ResultSet rs;   
   // private ResultSet rs;
    
    public ControllerLogin( ModelLogin modelLogin,ViewIniciarSecion viewIniciarSecion,ViewProductos viewProductos)
    {
       this.modelLogin=modelLogin;
       this.viewIniciarSecion=viewIniciarSecion;
       this.viewProductos=viewProductos;
       this.viewMain=viewMain;
       this.viewIniciarSecion.jbtnIngresar.addActionListener(this);
       this.viewIniciarSecion.setVisible(true);
       init_view();
    
    
    }
    public void init_view(){
        
         modelLogin.initValues();
         
         showValues();
         
       

    } 
  private void showValues(){
        this.viewIniciarSecion.jtfUser.setText(""+modelLogin.getUs());
        this.viewIniciarSecion.jPassword.setText(""+modelLogin.getPass());
        
    }
    
     

    @Override
    public void actionPerformed(ActionEvent ae) {
       if(ae.getSource()==this.viewIniciarSecion.jbtnIngresar)
       {
          
       
       
       }
    }
 
}
