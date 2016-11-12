/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import models.ModelMain;
import sax.DBConnection;
import views.ViewMain;
import views.ViewProductos;
import views.ViewUsuario;
import views.ViewIniciarSecion;
/*import views.ViewCompras;
import views.ViewClientes;*/
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.ResultSet;
import java.sql.Statement;


/**
 *
 * @author RoseLandjlord
 */
public class ControllerMAin implements ActionListener{
    
     private DBConnection conection = new DBConnection(3306,"localhost", "acme", "root", "");
            private ModelMain modelMain;
            private ViewMain viewMain;
            //JPanel views[];
                private Connection conexion; 
             private ResultSet rs;
             private Statement st;
            Object modules[];
    
            ControllerProducto controllerProducto;
            ControllerLogin controllerLogin;
            ControllerUsuario controllerUsuario;
            ControllerVenta controllerVenta;
            /*ControllerClientes controllerCliente;
            ControllerCompras controllerCompra;*/
            ControllerProveedores controllerProveedores;
            
            
    public ControllerMAin(ModelMain modelMain,ViewMain viewMain, /*JPanel views[]*/ Object modules[])
    {
        this.viewMain=viewMain;
         this.modelMain=modelMain;
         this.modules=modules;
         controllerProducto = (ControllerProducto)modules[0];
         controllerUsuario = (ControllerUsuario)modules[1];
          controllerLogin= (ControllerLogin )modules[2];
          controllerVenta=(ControllerVenta)modules[3];
          /*controllerCliente=(ControllerClientes)modules[4];
          controllerCompra=(ControllerCompras)modules[5];*/
          controllerProveedores=(ControllerProveedores)modules[6];
         //this.views=views;
         this.viewMain.jMenuItemProductos.addActionListener(this);
         this.viewMain.jMenuItemIniciar.addActionListener(this);
         this.viewMain.jMenuItemUsuario.addActionListener(this);
         this.controllerLogin.viewIniciarSecion.jbtnIngresar.addActionListener(this);
         this.viewMain.jMenuItemVenta.addActionListener(this);
         this.viewMain.jMenuItemClientes.addActionListener(this);
         this.viewMain.jMenuItemCompras.addActionListener(this);
         this.viewMain.jMenuItemProveed.addActionListener(this);
         
        
    }
     public void init_view()
     {
         this.viewMain.setTitle("Acme");
         this.viewMain.setLocationRelativeTo(null);
         this.viewMain.setVisible(true);
         this.viewMain.jMenuItemProductos.setEnabled(false);
         this.viewMain.jMenuItemUsuario.setEnabled(false);
         this.viewMain.jMenuItemVenta.setEnabled(false);
         this.viewMain.jMenuItemClientes.setEnabled(false);
         this.viewMain.jMenuItemCompras.setEnabled(false);
         this.viewMain.jMenuItemProveed.setEnabled(false);
     
     }
     public void actionPerfomedProductos()
    {
       this.viewMain.setContentPane(controllerProducto.viewProductos);
       this.viewMain.revalidate();
       this.viewMain.repaint();
    
    }
      public void actionPerfomedLogin()
    {
       this.viewMain.setContentPane(controllerLogin.viewIniciarSecion);
       
       this.viewMain.revalidate();
       this.viewMain.repaint();
    
    }
      public void actionPerfomedUsuario()
    {
       this.viewMain.setContentPane(controllerUsuario.viewUsuario);
       this.viewMain.revalidate();
       this.viewMain.repaint();
    
    }
       public void actionPerfomedVenta()
    {
       this.viewMain.setContentPane(controllerVenta.viewVenta);
       this.viewMain.revalidate();
       this.viewMain.repaint();
    
    }
      /* public void actionPerfomedCompra()
    {
       this.viewMain.setContentPane(controllerCompra.viewCompras);
       this.viewMain.revalidate();
       this.viewMain.repaint();
    
    }  public void actionPerfomedCliente()
    {
       this.viewMain.setContentPane(controllerCliente.viewClientes);
       this.viewMain.revalidate();
       this.viewMain.repaint();
    
    }*/
    public void actionPerfomedProveedo()
    {
       this.viewMain.setContentPane(controllerProveedores.viewProveedores);
       this.viewMain.revalidate();
       this.viewMain.repaint();
    
    }
       public void fecha()
       {
           /*DateFormat df=  DateFormat.getDateInstance();
           Date fechaActual=new Date();
           this.controllerVenta.viewVenta.jtfFecha.setText(df.format(fechaActual));*/
           Date fecha= new Date();
           SimpleDateFormat formatoFecha=new SimpleDateFormat("yyyy-MM-dd");
           this.controllerVenta.viewVenta.jtfFecha.setText(formatoFecha.format(fecha));
       }
    
       
    public void actionPerfomedIniCerr()
    {
       try{
        conexion=DriverManager.getConnection("jdbc:mysql://localhost/acme","root","");
         String  us=this.controllerLogin.viewIniciarSecion.jtfUser.getText();
        String pass=new String(this.controllerLogin.viewIniciarSecion.jPassword.getPassword());
               
        String niv="";
        String est1="";
        String sql="SELECT * FROM admin WHERE usuario = '"+us+"'&& contrasena='"+pass+"'";
         
            st=conexion.createStatement();
            rs=st.executeQuery(sql);
            //conection.executeQuery(sql);
            while(rs.next())
            {
              niv=rs.getString("nivel");
              est1=rs.getString("estado");
            }
            if(niv.equals("administrador") && est1.equals("Activo") )
            {
                
                
                JOptionPane.showMessageDialog(null,"Bienvenido  " + us);
                      this.viewMain.jMenuItemUsuario.setEnabled(true);
                      this.viewMain.jMenuItemProductos.setEnabled(true);
                      this.viewMain.jMenuItemVenta.setEnabled(true);
                      this.viewMain.jMenuItemClientes.setEnabled(true);
                      this.viewMain.jMenuItemCompras.setEnabled(true);
                      this.viewMain.jMenuItemProveed.setEnabled(true);
                       this.controllerVenta.viewVenta.JLVendedor.setText(us);
                      this.controllerLogin.viewIniciarSecion.setVisible(false);
                      this.viewMain.setVisible(true);
                       cerrarSesionText();
                        fecha();
                
            }
            else  if(niv.equals("vendedor") && est1.equals("Activo"))
            {
                JOptionPane.showMessageDialog(null,"Bienvenido  " + us);
                      this.viewMain.jMenuItemUsuario.setEnabled(false);
                      this.viewMain.jMenuItemProductos.setEnabled(false);
                       this.viewMain.jMenuItemClientes.setEnabled(false);
                      this.viewMain.jMenuItemCompras.setEnabled(false);
                      this.viewMain.jMenuItemProveed.setEnabled(false);
                      this.viewMain.jMenuItemVenta.setEnabled(true);
                      this.controllerVenta.viewVenta.JLVendedor.setText(us);
                       this.controllerLogin.viewIniciarSecion.setVisible(false);
                       this.viewMain.setVisible(true);
                        cerrarSesionText();
                      fecha();
                
            }else if(niv.equals("administrador") && est1.equals("Desactivo"))
            {
                 JOptionPane.showMessageDialog(null,"Esta persona esta Desactivado");
            }else if(niv.equals("vendedor") && est1.equals("Desactivo"))
            {
                 JOptionPane.showMessageDialog(null,"Esta persona esta Desactivado");
            }
            else
                JOptionPane.showMessageDialog(null,"Vuelve a intentar");
            
        }catch(SQLException err)
        {
              Logger.getLogger(ControllerMAin.class.getName()).log(Level.SEVERE, null, err);
        } 
    }
        
     public void cerrarSesionText()
     {
         this.viewMain.jMenuItemIniciar.setText("Cerrar Sesión");
         
     }
     public void actionPerfomedCerrarSesion()
     {
         this.viewMain.jMenuItemIniciar.setText("Iniciar Sesión");
         this.viewMain.jMenuItemUsuario.setEnabled(false);
         this.viewMain.jMenuItemProductos.setEnabled(false);
         this.viewMain.jMenuItemVenta.setEnabled(false);
         this.viewMain.jMenuItemClientes.setEnabled(false);
         this.viewMain.jMenuItemCompras.setEnabled(false);
         this.viewMain.jMenuItemProveed.setEnabled(false);
         this.controllerLogin.viewIniciarSecion.setVisible(false);
         this.controllerLogin.viewIniciarSecion.setVisible(false);
         this.controllerProducto.viewProductos.setVisible(false);
         this.controllerUsuario.viewUsuario.setVisible(false);
         this.controllerVenta.viewVenta.setVisible(false);
         /*this.controllerCliente.viewClientes.setVisible(false);
         this.controllerCompra.viewCompras.setVisible(false);*/
         
               
    
     }

      
      
      
    @Override
    public void actionPerformed(ActionEvent ae) {
     if(ae.getSource()==this.viewMain.jMenuItemProductos)
        {
          actionPerfomedProductos();
         
        
        }else if(ae.getSource()==this.viewMain.jMenuItemIniciar &&  this.viewMain.jMenuItemIniciar.getText().equals("Iniciar Sesión"))
        {
           actionPerfomedLogin();
                
        
        }else if(ae.getSource()==this.viewMain.jMenuItemIniciar &&  this.viewMain.jMenuItemIniciar.getText().equals("Cerrar Sesión"))
        {
           actionPerfomedCerrarSesion();
                
        
        }
        else if(ae.getSource()==this.viewMain.jMenuItemUsuario)
        {
            actionPerfomedUsuario();
        
        } else if(ae.getSource()==this.controllerLogin.viewIniciarSecion.jbtnIngresar)
        {
            actionPerfomedIniCerr();
        }else if(ae.getSource()==this.viewMain.jMenuItemVenta)
        {
            actionPerfomedVenta();
        }else if(ae.getSource()==this.viewMain.jMenuItemProveed)
        {
            actionPerfomedProveedo();
        }/*else if(ae.getSource()==this.viewMain.jMenuItemCompras)
        {
            actionPerfomedCompra();
        }else if(ae.getSource()==this.viewMain.jMenuItemClientes)
        {
            actionPerfomedCliente();
        }*/
     
    
        
    }
    
}
