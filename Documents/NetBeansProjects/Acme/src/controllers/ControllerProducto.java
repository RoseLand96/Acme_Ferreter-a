/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;
import sax.DBConnection;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection; 
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet; 
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement; 
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import models.ModelProductos;
import views.ViewProductos;


//librería para almacenar temporalmente una imagen
import javax.swing.ImageIcon;

//librería para mostrar el cuadro de dialogo abrir
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author RoseLandjlord
 */
public class ControllerProducto implements ActionListener {
   
     private DBConnection conection = new DBConnection(3306,"localhost", "acme", "root", "");
    
        ModelProductos modelProductos;
        ViewProductos viewProductos;
     DefaultTableModel model= new DefaultTableModel();

       ResultSetMetaData rsm;
       DefaultTableModel dtm;
    public ControllerProducto(ModelProductos modelProductos,ViewProductos viewProductos)
            {
               this.modelProductos=modelProductos;
               this.viewProductos=viewProductos;
               this.viewProductos.jbtnAgregar.addActionListener(this);
               this.viewProductos.jbtnBusacr.addActionListener(this);
               this.viewProductos.jbtnEditar.addActionListener(this);
               this.viewProductos.jbtnEliminar.addActionListener(this);
               this.viewProductos.jbtnPrimero.addActionListener(this);
               this.viewProductos.jbtnSiguiente.addActionListener(this);
               this.viewProductos.jbtnUltimo.addActionListener(this);
               
               this.viewProductos.jbtnNuevo.addActionListener(this);
               this.viewProductos.jbtnAnter.addActionListener(this);
               this.viewProductos.setVisible(true);
               init_view();
            
}

 public void init_view(){
        
         modelProductos.initValues();
         showValues();
        
         
         this.viewProductos.jtfId.setVisible(false);
         
       

    } 
  private void showValues(){
        viewProductos.jtfId.setText(""+modelProductos.getIdProducto());
        viewProductos.jtfProducto.setText(""+modelProductos.getProducto());
        viewProductos.jtfPrecioC.setText(modelProductos.getPrecioCompra());
         viewProductos.jtfPrecioV.setText(modelProductos.getPrecioVenta());
         viewProductos.jTextAreaDescripción.setText(modelProductos.getDescripcion());
         viewProductos.jtfExistencia.setText(modelProductos.getExistencia());
       
    }
 

  
          
public void bloquearDesbloquear(boolean todos) {
        this.viewProductos.jbtnAgregar.setEnabled(todos);
        this.viewProductos.jbtnAnter.setEnabled(!todos);
        this.viewProductos.jbtnBusacr.setEnabled(!todos);
        
        this.viewProductos.jbtnEditar.setEnabled(!todos);
        this.viewProductos.jbtnEliminar.setEnabled(!todos);
        this.viewProductos.jbtnPrimero.setEnabled(!todos);
        this.viewProductos.jbtnSiguiente.setEnabled(!todos);
        this.viewProductos.jbtnUltimo.setEnabled(!todos);
        
           
         
        
    }
public void DesbloquearBloquear(boolean todos)
{
    this.viewProductos.jbtnAgregar.setEnabled(todos);
        this.viewProductos.jbtnAnter.setEnabled(todos);
        this.viewProductos.jbtnBusacr.setEnabled(todos);
        
        this.viewProductos.jbtnEditar.setEnabled(todos);
        this.viewProductos.jbtnEliminar.setEnabled(todos);
        this.viewProductos.jbtnPrimero.setEnabled(todos);
        this.viewProductos.jbtnSiguiente.setEnabled(todos);
        this.viewProductos.jbtnUltimo.setEnabled(todos);


}
public void llenarTabla()
{
    String[] campos={"productos","precio compra","precio venta","existencia","descripcion"};
    String[] registros=new String[5];
    model= new DefaultTableModel(null,campos);
    try{
         
         while(conection.moveNext())
         {
             registros[0]=conection.getString("producto");
             registros[1]=conection.getString("precio_compra");
             registros[2]=conection.getString("precio_venta");
             registros[3]=conection.getString("descripción");
             registros[4]=conection.getString("existencia");
            model.addRow(registros);
              
         }
          this.viewProductos.jTable1.setModel(model);
       
    }catch(Exception ex)
    {
        JOptionPane.showMessageDialog(viewProductos, ex);
    }
/*
    model= new DefaultTableModel();
    model.addColumn("poductos");
    model.addColumn("descripcion");
    model.addColumn("precio compra");
    model.addColumn("precio venta");
    model.addColumn("existencia");
    this.viewProductos.jTable1.setModel(model);
     String[] registros=new String[5];
             registros[0]=this.viewProductos.jtfProducto.getText();
             registros[1]=this.viewProductos.jtfPrecioC.getText();
             registros[2]=this.viewProductos.jtfPrecioV.getText();
             registros[3]=this.viewProductos.jtfExistencia.getText();
             registros[4]=this.viewProductos.jTextAreaDescripción.getText();
              model.addRow(registros);
  */         
}

public void buscar()
{
    String[] campos={"productos","descripción","precio_compra","precio_venta","existencia"};
    String[] registros=new String[50];
    String sql="select * from productos where producto like '%"+this.viewProductos.jtfBuscar.getText()+"%'"
                    +"OR descripción like'%"+this.viewProductos.jtfBuscar.getText()+"%'"
                    +"OR precio_compra like '%"+this.viewProductos.jtfBuscar.getText()+"%'"
                    +"OR precio_venta like '%"+this.viewProductos.jtfBuscar.getText()+"%'"
                    +"OR existencia like '%"+this.viewProductos.jtfBuscar.getText()+"%'";
    model= new DefaultTableModel(null,campos);
    if(!this.viewProductos.jtfBuscar.getText().equals("")){
    try{
         conection.executeQuery(sql);
         while(conection.moveNext())
         {
             registros[0]=conection.getString("producto");
             registros[1]=conection.getString("descripción");
             registros[2]=conection.getString("precio_compra");
             registros[3]=conection.getString("precio_venta");
             registros[4]=conection.getString("existencia");
             model.addRow(registros);
              
         }
          this.viewProductos.jTable1.setModel(model);
       
    }catch(Exception ex)
    {
        JOptionPane.showMessageDialog(viewProductos, ex);
    }
    }else
    {
         JOptionPane.showMessageDialog(null, "No hay nada a buscar");
    }

}

public void Guardar()
{
     try{
           
             String respu=this.modelProductos.Message("Desea Guardar");
                
            String producto=this.viewProductos.jtfProducto.getText();
             String idproducto=this.viewProductos.jtfId.getText();
            //String id_producto=this.viewProductos.jLId.getText();
            String descripción=this.viewProductos.jTextAreaDescripción.getText();
            String precioCompra=this.viewProductos.jtfPrecioC.getText();
            String precioVenta=this.viewProductos.jtfPrecioV.getText();
            String existencia=this.viewProductos.jtfExistencia.getText();
            if (!producto.equals("") && !descripción.equals("") && !precioCompra.equals("") && !precioVenta.equals("") && !precioCompra.equals("") && !existencia.equals("") && respu=="Si")
                 {
            String sql= "insert into productos(producto,descripción,precio_compra,precio_venta,existencia) values (" + "'"+producto+"','"+descripción+"','"+precioCompra+"','"+precioVenta+"','"+existencia+"');";
            
            System.out.println("Nombre " + producto);
            System.out.println("SQL " + sql);
            
            conection.executeUpdate(sql);
            
           conection.executeQuery("Select * from productos");
           Primero();
           showValues();
           DesbloquearBloquear(true);
           JOptionPane.showMessageDialog(viewProductos,"Operación Exitosa");
                 } else if (producto.equals("") || descripción.equals("") || precioCompra.equals("") || precioVenta.equals("") || existencia.equals("") && respu=="Si")
                 {
            
                     JOptionPane.showMessageDialog(viewProductos,"Operación fracasada, verifique si no hay campo vacio");
                 }else
                 {
                 
                 JOptionPane.showMessageDialog(viewProductos,"Operación cancelada");
                
                 }
            llenarTabla();
           modelProductos.initValues();
        }catch (Exception err){
            JOptionPane.showMessageDialog(this.viewProductos, "No hay producto seleccionado");
        }
        

}
 public void Primero(){
       modelProductos.moveFirst();
        showValues();
    }
   
    public void Siguiente()
    {
        modelProductos.moveNext();
        showValues();
    
    
    }
    public void Anterior()
    {
         modelProductos.movePrevious();
        showValues();
    }
    
    
    public void Eliminar()
    {
        try{ 
             String respu=this.modelProductos.Message("Desea Eliminar");
                 if (respu=="Si")
                 {
                String idproducto=this.viewProductos.jtfId.getText();

              conection.executeUpdate("delete from productos where id_producto="+ idproducto);
              conection.executeQuery("Select * from productos order by id_producto");

              this.viewProductos.jtfProducto.setText("");                         

               this.viewProductos.jTextAreaDescripción.setText("");
               this.viewProductos.jtfPrecioC.setText(""); 

               this.viewProductos.jtfPrecioV.setText("");
               this.viewProductos.jtfExistencia.setText(""); 
                JOptionPane.showMessageDialog(viewProductos,"Operación exitosa");
                
                 }else
                    {
                        JOptionPane.showMessageDialog(viewProductos,"Operación cancelada");
                    }      
            llenarTabla();
            modelProductos.initValues();
        }catch(Exception err){ 
            JOptionPane.showMessageDialog(null,"No hay producto seleccionado"); 
        } 
    }
    public void Editar()
    {
        
            String producto=this.viewProductos.jtfProducto.getText();
            String descripción=this.viewProductos.jTextAreaDescripción.getText();
            String precioCompra=this.viewProductos.jtfPrecioC.getText();
            String precioVenta=this.viewProductos.jtfPrecioV.getText();
            String existencia=this.viewProductos.jtfExistencia.getText();
            
        try{ 
           
                String respu=this.modelProductos.Message("Desea Editar");
                    if (!producto.equals("") && !descripción.equals("") && !precioCompra.equals("") && !precioVenta.equals("") && !precioCompra.equals("") && !existencia.equals("") && respu=="Si")
                    {
               String idproducto=this.viewProductos.jtfId.getText();

               conection.executeUpdate("update productos set producto='"+producto+"',descripción='"+descripción+"',precio_compra='"+precioCompra+"',precio_venta='"+precioVenta+"',existencia='"+existencia+"' where id_producto='"+idproducto+"';");
                
               this.viewProductos.jtfId.setText("");
               this.viewProductos.jtfProducto.setText("");                         

               this.viewProductos.jTextAreaDescripción.setText("");
               this.viewProductos.jtfPrecioC.setText(""); 

               this.viewProductos.jtfPrecioV.setText("");
               this.viewProductos.jtfExistencia.setText(""); 
               JOptionPane.showMessageDialog(viewProductos,"Operación exitosa");
                    }else if (producto.equals("") || descripción.equals("") || precioCompra.equals("") || precioVenta.equals("") || existencia.equals("") && respu=="Si")
                    {

                        JOptionPane.showMessageDialog(viewProductos,"Operación fracasada, verifique si no hay campo vacio");
                    }else
                    {

                    JOptionPane.showMessageDialog(viewProductos,"Operación cancelada");
                    }
         
          modelProductos.initValues();
                //this.rs.first();   
       } catch(Exception err){ 
            JOptionPane.showMessageDialog(null,"No hay producto seleccionado "); 
        } 
    }
    public void ultimo()
    {
         modelProductos.moveLast();
        showValues();
    }
   
    public void Nuevo()
    {
                bloquearDesbloquear(true);
                this.viewProductos.jtfProducto.setText("");                         

               this.viewProductos.jTextAreaDescripción.setText("");
               this.viewProductos.jtfPrecioC.setText(""); 

               this.viewProductos.jtfPrecioV.setText("");
               this.viewProductos.jtfExistencia.setText(""); 
           
              
    }
    
    
    @Override
    public void actionPerformed(ActionEvent ae) {
       if(ae.getSource()==this.viewProductos.jbtnAgregar)
       {
           Guardar();
          
           
       }else if(ae.getSource()==this.viewProductos.jbtnSiguiente)
       {
           Siguiente();
       }else if(ae.getSource()==this.viewProductos.jbtnAnter)
       {
           Anterior();
       }else if(ae.getSource()==this.viewProductos.jbtnEditar)
       {
           Editar();
           
       }else if(ae.getSource()==this.viewProductos.jbtnEliminar)
       {
           
           Eliminar();
          
       }else if(ae.getSource()==this.viewProductos.jbtnUltimo)
       {
           ultimo();
          
       }else if(ae.getSource()==this.viewProductos.jbtnPrimero)
       {
          Primero();
       }else if(ae.getSource()==this.viewProductos.jbtnNuevo)
       {
          Nuevo();
       }else if(ae.getSource()==this.viewProductos.jbtnBusacr)
       {
          buscar();
       }
       
       
    }
    
  
    
}
