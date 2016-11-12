/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;
import sax.DBConnection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Connection; 
import java.sql.SQLException;
import java.text.DecimalFormat;
import javax.accessibility.AccessibleContext;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import models.ModelVentas;
import views.ViewVenta;
import views.ViewConfirmarEliminar;
/**
 *
 * @author RoseLandjlord
 */
public class ControllerVenta implements ActionListener {
    private DBConnection conection = new DBConnection(3306,"localhost", "acme", "root", "");
    ModelVentas modelVentas;
    ViewVenta viewVenta;
    DefaultTableModel model = new DefaultTableModel();
    DecimalFormat dFormat=new DecimalFormat();
    public ControllerVenta(ModelVentas modelVentas, ViewVenta viewVenta)
    {
        this.modelVentas=modelVentas;
        this.viewVenta=viewVenta;
        
        this.viewVenta.jbtnCancelarVenta.addActionListener(this);
        this.viewVenta.jbtnCerrarVenta.addActionListener(this);
        this.viewVenta.jbtnEliminar.addActionListener(this);
        this.viewVenta.jbtnNueva_venta.addActionListener(this);
        this.viewVenta.jbtnAgregar.addActionListener(this);
        this.viewVenta.jtfBuscarProduct.addActionListener(this);
        this.viewVenta.jbtnBuscar.addActionListener(this);
        this.viewVenta.jbtnCalcular.addActionListener(this);
        this.viewVenta.setVisible(true);
       
        init_values();
    
    
    }
    public void init_values()
    {
        modelVentas.initValues();
        showValues();
    }
    public void showValues()
    {
        viewVenta.jtfId_venta.setText(""+modelVentas.getId_venta());
        viewVenta.jtfID_Det_V.setText(""+modelVentas.getId_detVent());
    }
    public void nueva()
    {
        this.viewVenta.jtfIdCliente.setEnabled(true);
        this.viewVenta.jtfIdProduct.setEnabled(true);
        this.viewVenta.jtfCantidad.setEnabled(true);
        this.viewVenta.jtfTotalCantPrecio.setEnabled(true);
        this.viewVenta.jbtnCalcular.setEnabled(true);
        this.viewVenta.jbtnEliminar.setEnabled(true);
         this.viewVenta.jbtnCancelarVenta.setEnabled(true);
        this.viewVenta.jbtnAgregar.setEnabled(true);
        this.viewVenta.jtfId_venta.setEnabled(true);
        
         this.viewVenta.jtfIdCliente.setText("");
        this.viewVenta.jtfIdProduct.setText("");
        this.viewVenta.JLCliente.setText("");
        this.viewVenta.JLDireccion.setText("");
        this.viewVenta.JLRfc.setText("");
        this.viewVenta.jtfSubtotal.setText("");
        this.viewVenta.jtfIva.setText("");
        this.viewVenta.jttfTotal.setText("");
        int filas=model.getRowCount();
        for(int i=0; i<filas; i++)
        {
            model.removeRow(0);
        }
        
        
        
         
    
    
    }
    public void buscarProductos()
    {
        String sql="select  producto,precio_venta  from productos where id_producto="+this.viewVenta.jtfIdProduct.getText();
        conection.executeQuery(sql);
        if(conection.moveNext())
        {
            
            this.viewVenta.jtfProducto.setText(conection.getString("producto"));
            this.viewVenta.jtfPrecio_Venta.setText(conection.getString("precio_venta"));
            
        }else
        {
            JOptionPane.showMessageDialog(null,"No existe este producto");
            
        }
        
    
    
    }
    public void buscarCliente()
    {
        String sql="select  CONCAT(nombre,' ',apellido_pat,' ',apellido_mat),CONCAT(Estado,' ',ciudad,' ',colonia,' ',calle,' ',no),rfc from cliente where id_cliente="+this.viewVenta.jtfIdCliente.getText();
        conection.executeQuery(sql);
        if(conection.moveNext())
        {
            
            this.viewVenta.JLCliente.setText(conection.getString("CONCAT(nombre,' ',apellido_pat,' ',apellido_mat)"));
            this.viewVenta.JLDireccion.setText(conection.getString("CONCAT(Estado,' ',ciudad,' ',colonia,' ',calle,' ',no)"));
            this.viewVenta.JLRfc.setText(conection.getString("rfc"));
            
            
        }else
        {
            JOptionPane.showMessageDialog(null,"No existe este producto");
            
        }
    }
    public void totalP()
    {
        
        int cantidad=Integer.parseInt(this.viewVenta.jtfCantidad.getText());
        double precio_V=Double.parseDouble(conection.getString("precio_venta"));
        
        double total=cantidad*precio_V;
        this.viewVenta.jtfTotalCantPrecio.setText(""+total);
        
       /* double iva=1.6;
        this.viewVenta.jtfIva.setText(""+iva);
        /*this.viewVenta.jttfTotal.setText(""+(total+iva));*/
        
    }
    public void guardarVenta()
    {
       
       String fecha= this.viewVenta.jtfFecha.getText();
      String id=this.viewVenta.jtfIdCliente.getText();
       float subtotal=Float.parseFloat(this.viewVenta.jtfTotalCantPrecio.getText());
       float iva= (subtotal*10)/100;
        float total=subtotal+iva;
        int id_venta=Integer.parseInt(this.viewVenta.jtfId_venta.getText());
       int id_producto=Integer.parseInt(this.viewVenta.jtfIdProduct.getText());
       int cantidad=Integer.parseInt(this.viewVenta.jtfCantidad.getText());
         String sql= "insert into ventas(fecha,id_cliente,subtotal,iva,total) values (" + "'"+fecha+"','"+id+"','"+subtotal+"','"+iva+"','"+total+"');";
        String sql2="insert into detalle_venta(id_venta,id_producto,cantidad,total_producto,precio) values (" + "'"+id_venta    +"','"+id_producto+"','"+cantidad+"','"+cantidad+"','"+total+"');";
         
           
            System.out.println("SQL " + sql);
          System.out.println("SQL " + sql2);
            
            conection.executeUpdate(sql);
          conection.executeUpdate(sql2);
          
         
    
    try{
    //Statement  st;
//         st = (Statement) conection.createStament();
             int n;
                 String campos[]={"id producto","producto","Cantidad","precio_venta","total","Id venta"};
                String registros[]=new String[6];
                model.setColumnIdentifiers(campos);
               // model= new DefaultTableModel(null,campos);
        
         
             registros[0]=this.viewVenta.jtfIdProduct.getText();
             this.viewVenta.jtfIdProduct.setText(null);
             registros[1]=this.viewVenta.jtfProducto.getText();
             this.viewVenta.jtfProducto.setText(null);
             registros[2]=this.viewVenta.jtfCantidad.getText();
             this.viewVenta.jtfCantidad.setText(null);
             registros[3]=this.viewVenta.jtfPrecio_Venta.getText();
             this.viewVenta.jtfPrecio_Venta.setText(null);
             registros[4]=this.viewVenta.jtfTotalCantPrecio.getText();
             this.viewVenta.jtfTotalCantPrecio.setText(null);
             registros[5]=this.viewVenta.jtfId_venta.getText();
             this.viewVenta.jtfId_venta.setText(null);
             
             this.viewVenta.jtfIdProduct.grabFocus();
             model.addRow(registros);
              
              
         
          this.viewVenta.jTable2.setModel(model);
          n=model.getRowCount();
           int c=0;
            this.viewVenta.jtfSubtotal.setText("0.0");
           do
           {
               int f=c++;
               double n1=Double.parseDouble(model.getValueAt(f, 4).toString());
               double n2=Double.parseDouble(this.viewVenta.jtfSubtotal.getText());
               double re=n1+n2;
               
               //double dec=Double.parseDouble(dFormat.format(re));
               this.viewVenta.jtfSubtotal.setText(String.valueOf(re));
               
               
           }while(c<n);
         
    }catch(Exception ex)
    {
        JOptionPane.showMessageDialog(viewVenta, ex);
    }
      
    } 
    public void eliminarVenta()
    {
        Icon Alerta;
           Alerta=new ImageIcon("src/imagenes/autorizad.png");
           String pass= JOptionPane.showInputDialog(null,Alerta,"Password");
           String nivel="";
           String est="";
        String sql="SELECT * FROM admin WHERE contrasena='"+pass+"'";
                  
           conection.executeQuery(sql);
           
            while(conection.moveNext())
            {
                nivel=conection.getString("nivel");
                est=conection.getString("estado");
            }
              if(nivel.equals("administrador") && est.equals("Activo")) {
                  
                  String respu=this.modelVentas.Message("Desea Eliminar");
       int fila;
       fila=this.viewVenta.jTable2.getSelectedRow();
       if(respu=="Si")
       { 
         if(fila>=0)
       {
           
           String sql2="delete from ventas where id_venta="+this.viewVenta.jTable2.getValueAt(fila, 5);
           conection.executeUpdate(sql2);
           String sql3="delete from detalle_venta where id_venta="+this.viewVenta.jTable2.getValueAt(fila, 5);
           conection.executeUpdate(sql3);
           int n=model.getRowCount();
           
            
             //int f=c++;
               double n1=Double.parseDouble(this.viewVenta.jTable2.getValueAt(fila, 4).toString());
               double n2=Double.parseDouble(this.viewVenta.jtfSubtotal.getText());
               double re=n2-n1;
               
               //double dec=Double.parseDouble(dFormat.format(re));
               this.viewVenta.jtfSubtotal.setText(String.valueOf(re));
                      
           model.removeRow(fila);
           
           System.out.print(sql2);
          System.out.print(sql3);
          JOptionPane.showMessageDialog(null,"La venta ha sido cancelado");
       }else
       {
           JOptionPane.showMessageDialog(null,"No ha seleccionado ninguna fila");
       }
       }else
       {
            JOptionPane.showMessageDialog(null,"Operación Cancelada");
       }
    
              }else if(nivel.equals("administrador") && est.equals("Desactivo"))
              {
                   JOptionPane.showMessageDialog(null,"Persona desactivada");
              }else 
                 
                     JOptionPane.showMessageDialog(null,"Password incorrecto, Vuelve a intentar");
                     
              
    
    }public void CancelarVenta()
    {
        Icon Alerta;
           Alerta=new ImageIcon("src/imagenes/autorizad.png");
           String pass= JOptionPane.showInputDialog(null,Alerta,"Password");
           String nivel="";
           String est="";
        String sql="SELECT * FROM admin WHERE contrasena='"+pass+"'";
                  
           conection.executeQuery(sql);
           
            while(conection.moveNext())
            {
                 nivel=conection.getString("nivel");
                 est=conection.getString("estado");
            }
              if(nivel.equals("administrador")&& est.equals("Activo")) {
                  
                  String respu=this.modelVentas.Message("Desea Cancelar");
       int fila;
       fila=this.viewVenta.jTable2.getSelectedRow();
       if(respu=="Si")
       { 
         if(fila>=0)
       {
           
           String sql2="delete from ventas where id_venta="+this.viewVenta.jTable2.getValueAt(fila, 5);
           conection.executeUpdate(sql2);
           String sql3="delete from detalle_venta where id_venta="+this.viewVenta.jTable2.getValueAt(fila, 5);
           conection.executeUpdate(sql3);
          
           model.removeRow(fila);
           System.out.print(sql2);
          System.out.print(sql3);
          JOptionPane.showMessageDialog(null,"La venta ha sido cancelado");
       }else
       {
           JOptionPane.showMessageDialog(null,"No ha seleccionado ninguna fila");
       }
       }else
       {
            JOptionPane.showMessageDialog(null,"Operación Cancelada");
       }
    
                      
              }else if(nivel.equals("administrador") && est.equals("Desactivo"))
              {
                   JOptionPane.showMessageDialog(null,"Persona desactivada");
              }else 
                 
                     JOptionPane.showMessageDialog(null,"Password incorrecto, Vuelve a intentar");
                     
              
         
    }
    public void CerrarVenta()
    {
       this.viewVenta.jtfIdCliente.setEnabled(false);
       this.viewVenta.jtfTotalCantPrecio.setEnabled(false);
       this.viewVenta.jbtnAgregar.setEnabled(false);
       this.viewVenta.jbtnBuscar.setEnabled(false);
       this.viewVenta.jbtnCalcular.setEnabled(false);
       this.viewVenta.jbtnCancelarVenta.setEnabled(false);
       this.viewVenta.jbtnEliminar.setEnabled(false);
       this.viewVenta.jtfId_venta.setEnabled(false);
       this.viewVenta.jtfIdProduct.setEnabled(false);
       this.viewVenta.jtfCantidad.setEnabled(false);
       
            
          float sub=Float.parseFloat(this.viewVenta.jtfSubtotal.getText());
               float iva2=(sub*10)/100;
               double totalT=(sub+iva2);
               this.viewVenta.jtfIva.setText(""+iva2);
               //String dec1=dFormat.format(totalT);
               this.viewVenta.jttfTotal.setText(""+totalT);
        
      
        
    
    }
    

    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource()==this.viewVenta.jtfBuscarProduct)
        {
            buscarProductos();
        }else if(ae.getSource()==this.viewVenta.jbtnBuscar)
        {
           buscarCliente();
        }else if(ae.getSource()==this.viewVenta.jbtnAgregar)
        {
           guardarVenta();
           init_values();
        }else if(ae.getSource()==this.viewVenta.jbtnCalcular)
        {
           totalP();
          
        }else if(ae.getSource()==this.viewVenta.jbtnEliminar)
        {
            eliminarVenta();
        }
        else if(ae.getSource()==this.viewVenta.jbtnNueva_venta)
        {
           nueva();
        }else if(ae.getSource()==this.viewVenta.jbtnCancelarVenta)
        {
           CancelarVenta();
        }else if(ae.getSource()==this.viewVenta.jbtnCerrarVenta)
        {
           CerrarVenta();
        }
    }
    
}
