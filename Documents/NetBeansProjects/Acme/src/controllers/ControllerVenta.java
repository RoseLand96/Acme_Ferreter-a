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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.table.DefaultTableModel;
import models.ModelVentas;
import views.ViewVenta;
import views.ViewConfirmarEliminar;
import views.ViewProductos;
import javax.swing.JTextField;
/**
 *
 * @author RoseLandjlord
 */
public class ControllerVenta implements ActionListener {
    private DBConnection conection = new DBConnection(3306,"localhost", "acme", "root", "");
    ModelVentas modelVentas;
    ViewVenta viewVenta;
    DefaultTableModel model = new DefaultTableModel();
//    DecimalFormat dFormat=new DecimalFormat("##.##");
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
         this.viewVenta.jbtnid_form.addActionListener(this);
        this.viewVenta.setVisible(true);
       showPago();
        init_values();
        id_det_venta();
        
    
    
    }
    public void id_det_venta()
    {
        conection.executeQuery("select MAX(id_detalle_venta)+1 from detalle_venta");
          while(conection.moveNext()){
           int id=conection.getInteger("MAX(id_detalle_venta)+1");
           this.viewVenta.jtfId_detal.setText(""+id);
          }
    
    }
    public void init_values()
    {
        modelVentas.initValues();
        showValues();
        viewVenta.jtfProducto.setEnabled(false);
        viewVenta.jtfFecha.setEnabled(false);
        viewVenta.jtfSubtotal.setEnabled(false);
        viewVenta.jtfTotalCantPrecio.setEnabled(false);
        viewVenta.jttfTotal.setEnabled(false);
        viewVenta.jtfPrecio_Venta.setEnabled(false);
        viewVenta.jtfIva.setEnabled(false);
              
        
        
    }
    
    public void showValues()
    {
        viewVenta.jtfId_venta.setText(""+modelVentas.getId_venta());
       // viewVenta.jtfId_detal.setText(""+modelVentas.getId_detVent());
        //viewVenta.jtfExistencia.setText(""+modelVentas.getExistencia());
       
    }
   
          
    public void showPago()
     {
           //this.viewBuscar.jComboBoxProductos.setSelectedItem(""+this.model.getNombre());
         conection.executeQuery("select tipo_pago from formato_pago");
          while(conection.moveNext()){
           String nom=conection.getString("tipo_pago");
           this.viewVenta.jComboBoxFormato.addItem(nom);
           formato();
          }
     }
    public void formato()
    {
      
    
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
        this.viewVenta.jbtnBuscar.setEnabled(true);
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
        
        showPago();
        init_values();
        id_det_venta();
        
         
    
    
    }
    
    public void buscarProductos()
    {   if(this.viewVenta.jtfIdProduct.getText().equals("") )
        {
            JOptionPane.showMessageDialog(null,"Verifica si no hay campos vacio");
        }else{
        String sql="select P.producto,T.descuento,P.precio_venta,P.existencia from productos P,promociones T where P.id_producto=T.id_producto and P.id_producto="+this.viewVenta.jtfIdProduct.getText();
        conection.executeQuery(sql);
        if(conection.moveNext())
        {
            
            this.viewVenta.jtfProducto.setText(conection.getString("P.producto"));
            this.viewVenta.jtfPrecio_Venta.setText(conection.getString("P.precio_venta"));
            this.viewVenta.jtfExistencia.setText(conection.getString("P.existencia"));
            this.viewVenta.jtfDescuento.setText(conection.getString("T.descuento"));
            
        }else
        {
            JOptionPane.showMessageDialog(null,"No existe este producto");
            
        }}
        
    
    
    }
    public void buscarCliente()
    {
        if(this.viewVenta.jtfIdCliente.getText().equals(""))
        {
            JOptionPane.showMessageDialog(null,"Favor de llenar este campo");
        }else{
        String sql="select  CONCAT(nombre,' ',apellido_pat,' ',apellido_mat),CONCAT(Estado,' ',ciudad,' ',colonia,' ',calle,' ',no),rfc from cliente where id_cliente="+this.viewVenta.jtfIdCliente.getText();
        conection.executeQuery(sql);
        if(conection.moveNext())
        {
            
            this.viewVenta.JLCliente.setText(conection.getString("CONCAT(nombre,' ',apellido_pat,' ',apellido_mat)"));
            this.viewVenta.JLDireccion.setText(conection.getString("CONCAT(Estado,' ',ciudad,' ',colonia,' ',calle,' ',no)"));
            this.viewVenta.JLRfc.setText(conection.getString("rfc"));
            
            
        }else
        {
            JOptionPane.showMessageDialog(null,"No existe este cliente");
            
        }
        }
    }
    public void totalP()
    {
     if(this.viewVenta.jtfCantidad.getText().equals("") )
        {
            JOptionPane.showMessageDialog(null,"Verifica si no hay campos vacio");
        }else{   
     
        int existen=conection.getInteger("existencia");
        
        int cantidad=Integer.parseInt(this.viewVenta.jtfCantidad.getText());
         double precio_V=Double.parseDouble(conection.getString("precio_venta"));
         double descuent=Float.parseFloat(this.viewVenta.jtfDescuento.getText());
                    
        if(existen>=cantidad){
               
        double total=(cantidad*precio_V)-(cantidad*descuent);
        this.viewVenta.jtfTotalCantPrecio.setText(""+total);
        
        }else
        {
            JOptionPane.showMessageDialog(null,"No hay esta cantidad de productos");
        }
     }
       /* double iva=1.6;
        this.viewVenta.jtfIva.setText(""+iva);
        /*this.viewVenta.jttfTotal.setText(""+(total+iva));*/
        
    }
    public float redondear(float num)
    {
       return (float) (Math.rint(num*100)/100);
    
    }
    public void guardarVenta()
    {
        
     try{
                    String fecha= this.viewVenta.jtfFecha.getText();
                    String id=this.viewVenta.jtfIdCliente.getText();
                    int id_form=0;
                    float subtotal=Float.parseFloat(this.viewVenta.jtfTotalCantPrecio.getText());
                    float iva= 0.0f;
                   
                    float sub=0.0f;
                    float tot=0.0f;
                    
                    int id_venta=Integer.parseInt(this.viewVenta.jtfId_venta.getText());
                    int id_producto=Integer.parseInt(this.viewVenta.jtfIdProduct.getText());
                    int cantidad=Integer.parseInt(this.viewVenta.jtfCantidad.getText());
              if(this.viewVenta.jtfIdProduct.getText().equals("") )
              {
                  JOptionPane.showMessageDialog(null,"Verifica si no hay campos vacio");
              }else
              {
                    if((this.viewVenta.jTable2.getRowCount()<1))
                    {
       
                     conection.executeQuery("start transaction");
                    String sql= "insert into ventas(fecha,id_cliente,subtotal,iva,total,id_formato) values (" + "'"+fecha+"','"+id+"','"+sub+"','"+iva+"','"+tot+"','"+id_form+"');";
                    String sql2="insert into detalle_venta(id_venta,id_producto,cantidad,total_producto,precio) values (" + "'"+id_venta    +"','"+id_producto+"','"+cantidad+"','"+cantidad+"','"+subtotal+"');";
                      conection.executeUpdate(sql);
                       conection.executeUpdate(sql2);
                       System.out.println("SQL " + sql);
                       System.out.println("SQL " + sql2);


                    int ex=Integer.parseInt(this.viewVenta.jtfExistencia.getText());
                    int exist=ex-cantidad;
                    
                    conection.executeUpdate("update productos set existencia='"+exist+"' where id_producto='"+id_producto+"';" );
                                  



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
                          registros[5]=this.viewVenta.jtfId_detal.getText();
                          //this.viewVenta.jtfId_venta.setText(null);

                          this.viewVenta.jtfIdProduct.grabFocus();
                          model.addRow(registros);



                       this.viewVenta.jTable2.setModel(model);
                       n=model.getRowCount();
                        int c=0;
                        this.viewVenta.jtfSubtotal.setText(""+subtotal);
                        float sub2=Float.parseFloat(this.viewVenta.jtfSubtotal.getText());
                            float iva2=(sub2*10)/100;
                                iva2=redondear(iva2);
                            this.viewVenta.jtfIva.setText(""+iva2);
                             float total2=sub2+iva2;
                             this.viewVenta.jttfTotal.setText(""+total2);
                        id_det_venta();
                        /* this.viewVenta.jtfSubtotal.setText("0.0");
                        do
                        {
                            int f=c++;
                            
                            double n1=Double.parseDouble(model.getValueAt(f, 4).toString());
                            double n2=Double.parseDouble(this.viewVenta.jtfSubtotal.getText());
                            double re=n1+n2;

                            //double dec=Double.parseDouble(dFormat.format(re));
                            this.viewVenta.jtfSubtotal.setText(String.valueOf(re));


                        }while(c<n);*/
                       
                       
                    }else 
                    {
                /*  float sub2=Float.parseFloat(this.viewVenta.jtfSubtotal.getText());
                    float iva2=(sub2*10)/100;
                    float total2=sub2*iva2;**/
               //  float subtot=Float.parseFloat(this.viewVenta.jtfTotalCantPrecio.getText());
                
               // while(conection.moveNext()){
               // conection.executeQuery("select subtotal from ventas");
                
                              
                
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
                          registros[5]=this.viewVenta.jtfId_detal.getText();
                          //this.viewVenta.jtfId_venta.setText(null);

                          this.viewVenta.jtfIdProduct.grabFocus();
                          model.addRow(registros);
                          this.viewVenta.jTable2.setModel(model);
                          int n;
                          n=model.getRowCount();
                               
                          int c=0;
//                            double n1=Double.parseDouble(this.viewVenta.jtfSubtotal.getText());
//                           double n2=Double.parseDouble(this.viewVenta.jtfTotalCantPrecio.getText());    
//                           double res=n1+n2;
//                           this.viewVenta.jtfSubtotal.setText(""+res);
//                       
//                         do
//                          {
//                            int f=c++;
//                            double n3=Double.parseDouble(model.getValueAt(f, 4).toString());
//                            double n4=Double.parseDouble(this.viewVenta.jtfSubtotal.getText());
//                            double re=n4+n3;
//
//                            //double dec=Double.parseDouble(dFormat.format(re));
//                            this.viewVenta.jtfSubtotal.setText(String.valueOf(re));
//
//
//                       }while(c<n);
                                            double sumatoria1=0.0;
                          int totalRow= model.getRowCount();
                          totalRow-=1;
                          for(int i=0;i<=(totalRow);i++)
                          {
                               double sumatoria= Double.parseDouble(String.valueOf(model.getValueAt(i,4)));
                  //en la parte de arriba indica el primer parametro la fila y el segundo la columna la cual estaras //manejando
                               sumatoria1+=sumatoria;
                               this.viewVenta.jtfSubtotal.setText(String.valueOf(sumatoria1));
                            //System.out.println(""+sumatoria1);
                            float sub2=Float.parseFloat(this.viewVenta.jtfSubtotal.getText());
                            float iva2=(sub2*10)/100;
                             iva2=redondear(iva2);
                            this.viewVenta.jtfIva.setText(""+iva2);
                             float total2=sub2+iva2;
                             this.viewVenta.jttfTotal.setText(""+total2);

                             }


                 // float sub3=subtot+sub2;


                            // int val=Integer.parseInt(model.getValueAt(f, 5).toString());
                      //  conection.executeUpdate("update ventas set subtotal='"+sub2+"',iva='"+iva2+"',total='"+total2+"' where id_venta='"+id_venta+"';");
                     //   String sql3="insert into detalle_venta(id_venta,id_producto,cantidad,total_producto,precio) values (" + "'"+id_venta    +"','"+id_producto+"','"+cantidad+"','"+cantidad+"','"+total+"' where id_venta='"+id_venta+"');";
                        conection.executeUpdate("insert into detalle_venta(id_venta,id_producto,cantidad,total_producto,precio) values (" + "'"+id_venta    +"','"+id_producto+"','"+cantidad+"','"+cantidad+"','"+subtotal+"');"
                       );
                         int ex=Integer.parseInt(this.viewVenta.jtfExistencia.getText());
                               int exist=ex-cantidad;

                               conection.executeUpdate("update productos set existencia='"+exist+"' where id_producto='"+id_producto+"';" );



                       // System.out.println(sql3);


                      
            }
              }         
       
         id_det_venta();
        }catch(Exception ex)
        {
            JOptionPane.showMessageDialog(viewVenta, ex);
        }

        } 
    
    public void eliminarVenta()
    {
            JPasswordField jpf = new JPasswordField(); 
          JLabel titulo = new JLabel ("Ingrese su password"); 
          //String usuario = JOptionPane.showInputDialog ("Usuario"); 
          JOptionPane.showConfirmDialog (null, new Object[]{titulo, jpf}, "Inicio de sesión", JOptionPane.OK_CANCEL_OPTION); 

          char p[] = jpf.getPassword(); 
          String pass = new String(p); 

     //JOptionPane.showMessageDialog (null, pass); 
        /*Icon Alerta;
           Alerta=new ImageIcon("src/imagenes/autorizad.png");
           String pass= JOptionPane.showInputDialog(null,Alerta,"Password");*/
           
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
            
//           String sql2="delete from ventas where id_venta="+this.viewVenta.jTable2.getValueAt(fila, 5);
//           conection.executeUpdate(sql2);
           String sql3="delete from detalle_venta where id_detalle_venta="+this.viewVenta.jTable2.getValueAt(fila, 5);
           conection.executeUpdate(sql3);
           int cantidad=Integer.parseInt(this.viewVenta.jTable2.getValueAt(fila, 2).toString());
            int id_producto=Integer.parseInt(this.viewVenta.jTable2.getValueAt(fila, 0).toString());
            
                   // int existen=conection.getInteger("existencia");
                    int ex=Integer.parseInt(this.viewVenta.jtfExistencia.getText());
                    int exist=ex+cantidad;
               //this.viewVenta.jtfExistencia.setText(""+existen);
                   conection.executeUpdate("update productos set existencia='"+exist+"' where id_producto='"+id_producto+"';" );
                
               
            
               
                    
                
                 
           int n=model.getRowCount();
           
            
             //int f=c++;
               double n1=Double.parseDouble(this.viewVenta.jTable2.getValueAt(fila, 4).toString());
               double n2=Double.parseDouble(this.viewVenta.jtfSubtotal.getText());
               double re=n2-n1;
               
               
               //double dec=Double.parseDouble(dFormat.format(re));
               this.viewVenta.jtfSubtotal.setText(String.valueOf(re));
               float sub2=Float.parseFloat(this.viewVenta.jtfSubtotal.getText());
          float iva2=(sub2*10)/100;
           iva2=redondear(iva2);
          this.viewVenta.jtfIva.setText(""+iva2);
           float total2=sub2+iva2;
           this.viewVenta.jttfTotal.setText(""+total2);
                      
           model.removeRow(fila);
           
         //  System.out.print(sql2);
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
                     
              
    
    }public void exist()
    {
        int fila;
       fila=this.viewVenta.jTable2.getSelectedRow();
       String sql="select existencia from productos where id_producto="+this.viewVenta.jTable2.getValueAt(fila, 0).toString();
        conection.executeQuery(sql);
        while(conection.moveNext()){
        int existen=conection.getInteger("existencia");
        this.viewVenta.jtfExistencia.setText(""+existen);
        }
        
    }
    public void CancelarVenta()
    {
         JPasswordField jpf = new JPasswordField(); 
          JLabel titulo = new JLabel ("Ingrese su password"); 
          //String usuario = JOptionPane.showInputDialog ("Usuario"); 
          JOptionPane.showConfirmDialog (null, new Object[]{titulo, jpf}, "Inicio de sesión", JOptionPane.OK_CANCEL_OPTION); 

          char p[] = jpf.getPassword(); 
          String pass = new String(p); 
        /*Icon Alerta;
           Alerta=new ImageIcon("src/imagenes/autorizad.png");
           String pass= JOptionPane.showInputDialog(null,Alerta,"Password");*/
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
       
       
       if(respu=="Si")
       { 
            conection.executeQuery("Rollback");
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
       
               
           if(this.viewVenta.jComboBoxFormato.getSelectedItem().equals("Tarjeta de credito"))
          {
                     JOptionPane.showMessageDialog(null, "Todavia este sistema no usa tarjeta de credito favor de pagar en efectivo");
          
          }else if(this.viewVenta.jComboBoxFormato.getSelectedItem().equals("Efectivo"))
          {   int id_form=Integer.parseInt(this.viewVenta.jtfId_formato.getText());
              int id_venta=Integer.parseInt(this.viewVenta.jtfId_venta.getText());
              float sub2=Float.parseFloat(this.viewVenta.jtfSubtotal.getText());
              float iva2=Float.parseFloat(this.viewVenta.jtfIva.getText());
              float total2=Float.parseFloat(this.viewVenta.jttfTotal.getText());
              conection.executeUpdate("update ventas set subtotal='"+sub2+"',iva='"+iva2+"',total='"+total2+"',id_formato='"+id_form+"' where id_venta='"+id_venta+"';");
              
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


                      
                       float totalT=Float.parseFloat(this.viewVenta.jttfTotal.getText());
                        
                        JPanel jPanel=new JPanel();
                        JTextField filed1= new JTextField();
                         JTextField filed2= new JTextField();
                          JTextField filed3= new JTextField();
                          Object[] message={
                            "total :" +totalT,  
                            "Efectivo :", filed1,
                           
                          };
                          int option=JOptionPane.showConfirmDialog(jPanel, message, "enter all", JOptionPane.OK_CANCEL_OPTION);
                        if(option==JOptionPane.OK_OPTION)
                        {
                           
                             float efectivo=Float.parseFloat(filed1.getText());
                            if(efectivo<totalT)
                            {
                                 JOptionPane.showMessageDialog(null,"efectivo no alcanza");}
                            }
                            else{
                             float efectivo=Float.parseFloat(filed1.getText());
                             float cambio=efectivo-totalT;
                             JOptionPane.showMessageDialog(null,"Cambio :"+cambio);
                        
                        }
                        /*JOptionPane.showMessageDialog(null,"total : "+totalT,);
                        float efect=Float.parseFloat(JOptionPane.showInputDialog("efectivo:"));
                        double cambio1=efect-totalT;
                        JOptionPane.showInputDialog(null,"cambio : "+cambio1);
                        JOptionPane.showMessageDialog(null, totalT +""+efect+""+cambio2);*/
          
          }else if(this.viewVenta.jComboBoxFormato.getSelectedItem().equals("tarjeta de debito"))
          {
                      JOptionPane.showMessageDialog(null, "Todavia este sistema no usa tarjeta de debito favor de pagar en efectivo");
          
          
          }else if(this.viewVenta.jComboBoxFormato.getSelectedItem().equals("Paypal"))
          {
                       JOptionPane.showMessageDialog(null, "Todavia este sistema no usa paypal favor de pagar en efectivo");
          
          
          }

        
      
        
    
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
          
        }else if(ae.getSource()==this.viewVenta.jbtnCalcular)
        {
           totalP();
          
        }else if(ae.getSource()==this.viewVenta.jbtnEliminar)
        {
            exist();
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
        }else if(ae.getSource()==this.viewVenta.jbtnid_form)
        {
           conection.executeQuery("select id_formato from formato_pago where tipo_pago='"+this.viewVenta.jComboBoxFormato.getSelectedItem()+"'");
           while(conection.moveNext()){
           String nom=conection.getString("id_formato");
           this.viewVenta.jtfId_formato.setText(""+nom);
          //  formato();
        }
    }
    }
}
