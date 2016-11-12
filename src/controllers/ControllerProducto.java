/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection; 
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet; 
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement; 
import java.util.ArrayList;
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
    private Connection conexion;     
    private Statement st;     
    private ResultSet rs; 
    
    private ModelProductos modelProductos;
    private ViewProductos viewProductos;
    private String nombreArchivo;
    private String ruta;
    private String nuevaRuta;
    private File archivo;
      PreparedStatement ps;

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
               this.viewProductos.jbtnCargarImagen.addActionListener(this);
                this.viewProductos.jbtnNuevo.addActionListener(this);
                 this.viewProductos.jbtnAnter.addActionListener(this);
               this.viewProductos.setVisible(true);
               initComponents();
             // Conectar();
            
}

 public void Conectar(){
         try{ 
            conexion=DriverManager.getConnection("jdbc:mysql://localhost/acme","root","");                     
            st=conexion.createStatement(); 

            rs=st.executeQuery("Select * from productos ");             

             rs.next(); 
            //this.viewProductos.jLId.setText(rs.getString("id_producto"));     
            this.viewProductos.jtfProducto.setText(rs.getString("producto"));                         
         
            this.viewProductos.jTextAreaDescripción.setText(rs.getString("descripción"));
            this.viewProductos.jtfPrecioC.setText(rs.getString("precio_compra")); 
            
            this.viewProductos.jtfPrecioV.setText(rs.getString("precio_venta"));
            this.viewProductos.jtfExistencia.setText(rs.getString("existencia")); 
             
             this.viewProductos.jtfNombreImagen.setText(rs.getString("imagen")); 
           
            this.viewProductos.JlImagen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/" + rs.getString("imagen"))));

           // mostrarTable();
        }catch(SQLException err){ 
            JOptionPane.showMessageDialog(null,"No hay nada a mostrar "+err.getMessage()); 
        } catch (NullPointerException err) {
            JOptionPane.showMessageDialog(null, "la imagen esta null, verifica!!" );
        }

    } 
 
 public static void copiarArchivo(String origen, String destino) {
        try {
            Path rutaOrigen = Paths.get(origen);
            Path rutaDestino = Paths.get(destino);
            CopyOption[] opciones = new CopyOption[]{
                StandardCopyOption.REPLACE_EXISTING,
                StandardCopyOption.COPY_ATTRIBUTES
            };
            Files.copy(rutaOrigen, rutaDestino, opciones);
        } catch (IOException err) {
            JOptionPane.showMessageDialog(null, "No hay archivo seleccionado" );
        }
    }
public void bloquearDesbloquear(boolean todos) {
        this.viewProductos.jbtnAgregar.setEnabled(todos);
        this.viewProductos.jbtnAnter.setEnabled(todos);
        this.viewProductos.jbtnBusacr.setEnabled(todos);
        this.viewProductos.jbtnCargarImagen.setEnabled(todos);
        this.viewProductos.jbtnEditar.setEnabled(todos);
        this.viewProductos.jbtnEliminar.setEnabled(!todos);
        this.viewProductos.jbtnPrimero.setEnabled(!todos);
        this.viewProductos.jbtnSiguiente.setEnabled(todos);
        this.viewProductos.jbtnUltimo.setEnabled(todos);
        
           
         
        
    }
public void Guardar()
{
     try{
            String producto=this.viewProductos.jtfProducto.getText();
            //String id_producto=this.viewProductos.jLId.getText();
            String descripción=this.viewProductos.jTextAreaDescripción.getText();
            String precioCompra=this.viewProductos.jtfPrecioC.getText();
            String precioVenta=this.viewProductos.jtfPrecioV.getText();
            String existencia=this.viewProductos.jtfExistencia.getText();
            String imagen=this.viewProductos.jtfNombreImagen.getText();
            String sql= "insert into productos(producto,descripción,precio_compra,precio_venta,existencia,imagen) values (" + "'"+producto+"','"+descripción+"','"+precioCompra+"','"+precioVenta+"','"+existencia+"', '" + nombreArchivo + "');";
            
            System.out.println("Nombre " + producto);
            System.out.println("SQL " + sql);
            
            st.executeUpdate(sql);
            
            rs = st.executeQuery("Select * from productos");
           Primero();
          
           
        }catch (Exception err){
            JOptionPane.showMessageDialog(this.viewProductos, "No hay producto seleccionado");
        }
        

}
 public void Primero(){
        try {
                rs.first();
                   //this.viewProductos.jLId.setText(rs.getString("id_producto"));     
               this.viewProductos.jtfProducto.setText(rs.getString("producto"));                         

               this.viewProductos.jTextAreaDescripción.setText(rs.getString("descripción"));
               this.viewProductos.jtfPrecioC.setText(rs.getString("precio_compra")); 

               this.viewProductos.jtfPrecioV.setText(rs.getString("precio_venta"));
               this.viewProductos.jtfExistencia.setText(rs.getString("existencia")); 

                this.viewProductos.jtfNombreImagen.setText(rs.getString("imagen")); 

               this.viewProductos.JlImagen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/" + rs.getString("imagen"))));


           } catch (SQLException err) {
            JOptionPane.showMessageDialog(null, "No hay producto seleccionado" );
        }
    }
    public void cargarImagen()
    {
         try {
                ImageIcon imagen; //permite almacenar la imagen que se abre con el JFileChooser
                FileNameExtensionFilter filtro = new FileNameExtensionFilter("PNG,JPG", "png","jpg");
             this.viewProductos.jfcAbrirArchivo.setFileFilter(filtro);

                int abrir = this.viewProductos.jfcAbrirArchivo.showOpenDialog(this.viewProductos);

                if (abrir == this.viewProductos.jfcAbrirArchivo.APPROVE_OPTION) {
                    archivo = this.viewProductos.jfcAbrirArchivo.getSelectedFile();
                    ruta = archivo.getAbsolutePath();
                    nombreArchivo = archivo.getName();
                    nuevaRuta = System.getProperty("user.dir") + "\\src\\images\\" + nombreArchivo;

                    imagen = new ImageIcon(ruta);

                    this.viewProductos.jtfNombreImagen.setText(nombreArchivo);
                    this.viewProductos.JlImagen.setIcon(imagen);
                }
            } catch (NullPointerException err) {
                JOptionPane.showMessageDialog(null, "No hay producto seleccionado" );
            }
             copiarArchivo(ruta, nuevaRuta);

    
    
    }
    public void Siguiente()
    {
         try {
                if (!rs.isLast()) {
                    rs.next();
                     this.viewProductos.jtfProducto.setText(rs.getString("producto"));                         

                this.viewProductos.jTextAreaDescripción.setText(rs.getString("descripción"));
                this.viewProductos.jtfPrecioC.setText(rs.getString("precio_compra")); 

                this.viewProductos.jtfPrecioV.setText(rs.getString("precio_venta"));
                this.viewProductos.jtfExistencia.setText(rs.getString("existencia")); 

                 this.viewProductos.jtfNombreImagen.setText(rs.getString("imagen")); 

                this.viewProductos.JlImagen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/" + rs.getString("imagen"))));

             }
        } catch (SQLException err) {
            JOptionPane.showMessageDialog(null, "No hay producto seleccionado" );
        }
    
    
    }
    public void Anterior()
    {
         try {
            if (!rs.isFirst()) {
                rs.previous();
                this.viewProductos.jtfProducto.setText(rs.getString("producto"));                         

                this.viewProductos.jTextAreaDescripción.setText(rs.getString("descripción"));
                this.viewProductos.jtfPrecioC.setText(rs.getString("precio_compra")); 

                this.viewProductos.jtfPrecioV.setText(rs.getString("precio_venta"));
                this.viewProductos.jtfExistencia.setText(rs.getString("existencia")); 

                 this.viewProductos.jtfNombreImagen.setText(rs.getString("imagen")); 

                this.viewProductos.JlImagen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/" + rs.getString("imagen"))));
 
            
            }
        } catch (SQLException err) {
            JOptionPane.showMessageDialog(null, "No hay producto seleccionado" );
        }
    }
    
    
    public void Eliminar()
    {
        try{ 
                String producto=this.viewProductos.jtfProducto.getText();

              st.executeUpdate("delete from productos where producto="+producto);
              rs=st.executeQuery("Select * from productos order by nombre");

              this.viewProductos.jtfProducto.setText("");                         

               this.viewProductos.jTextAreaDescripción.setText("");
               this.viewProductos.jtfPrecioC.setText(""); 

               this.viewProductos.jtfPrecioV.setText("");
               this.viewProductos.jtfExistencia.setText(""); 

                this.viewProductos.jtfNombreImagen.setText(""); 

              this.viewProductos.JlImagen.setIcon(null);
        }catch(SQLException err){ 
            JOptionPane.showMessageDialog(null,"No hay producto seleccionado"); 
        } 
    }
    public void Editar()
    {
        String producto=this.viewProductos.jtfProducto.getText();
            //String id_producto=this.viewProductos.jLId.getText();
            String descripción=this.viewProductos.jTextAreaDescripción.getText();
            String precioCompra=this.viewProductos.jtfPrecioC.getText();
            String precioVenta=this.viewProductos.jtfPrecioV.getText();
            String existencia=this.viewProductos.jtfExistencia.getText();
            String imagen=this.viewProductos.jtfNombreImagen.getText();
        try{ 
            
          
            st.executeUpdate("update productos set producto='"+producto+"',descripción='"+descripción+"',precio_compra='"+precioCompra+"',precio_venta='"+precioVenta+"',existencia='"+existencia+"',imagen='"+nombreArchivo+"' where producto='"+producto+"';");
      this.viewProductos.jtfProducto.setText("");                         
         
            this.viewProductos.jTextAreaDescripción.setText("");
            this.viewProductos.jtfPrecioC.setText(""); 
            
            this.viewProductos.jtfPrecioV.setText("");
            this.viewProductos.jtfExistencia.setText(""); 
             
             this.viewProductos.jtfNombreImagen.setText(""); 
             this.viewProductos.JlImagen.setIcon(null);
         
          
                //this.rs.first();   
       } catch(SQLException err){ 
            JOptionPane.showMessageDialog(null,"No hay producto seleccionado "); 
        } 
    }
    public void ultimo()
    {
         try {
            rs.last();
           this.viewProductos.jtfProducto.setText(rs.getString("producto"));                         
         
            this.viewProductos.jTextAreaDescripción.setText(rs.getString("descripción"));
            this.viewProductos.jtfPrecioC.setText(rs.getString("precio_compra")); 
            
            this.viewProductos.jtfPrecioV.setText(rs.getString("precio_venta"));
            this.viewProductos.jtfExistencia.setText(rs.getString("existencia")); 
             
             this.viewProductos.jtfNombreImagen.setText(rs.getString("imagen")); 
           
            this.viewProductos.JlImagen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/" + rs.getString("imagen"))));
            
          
        } catch (SQLException err) {
            JOptionPane.showMessageDialog(null, "No hay producto seleccionado");
        }
    }
     public void mostrarTable()
    {
       try
        {
           
            String producto=this.viewProductos.jtfProducto.getText();
           ps=conexion.prepareStatement("Select producto,descripción,precio_compra,precio_venta,existencia,imagen from productos where producto='"+producto+"'");
           rs=ps.executeQuery();
           rsm=rs.getMetaData();
           ArrayList<Object[]> data=new ArrayList<>();
           while(rs.next())
           {
           Object[] rows= new Object[rsm.getColumnCount()];
           
           for (int i=0; i<rows.length; i++)
           {
             rows[i]=rs.getObject(i+1);
           }
            data.add(rows);
           }
           dtm=(DefaultTableModel)this.viewProductos.jTable1.getModel();
           
          for (int i=0; i<data.size(); i++)
           {
             dtm.addRow(data.get(i));
           }
        
        }catch(SQLException e){
         
        JOptionPane.showMessageDialog(null, "Producto no existe" );
        
        }    
    }
    public void Nuevo()
    {
                this.viewProductos.jtfProducto.setText("");                         

               this.viewProductos.jTextAreaDescripción.setText("");
               this.viewProductos.jtfPrecioC.setText(""); 

               this.viewProductos.jtfPrecioV.setText("");
               this.viewProductos.jtfExistencia.setText(""); 

                this.viewProductos.jtfNombreImagen.setText(""); 

              this.viewProductos.JlImagen.setIcon(null);
              
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
       if(ae.getSource()==this.viewProductos.jbtnAgregar)
       {
           Guardar();
       }else if(ae.getSource()==this.viewProductos.jbtnCargarImagen)
       {
           cargarImagen();
       }else if(ae.getSource()==this.viewProductos.jbtnSiguiente)
       {
           Siguiente();
       }else if(ae.getSource()==this.viewProductos.jbtnAnter)
       {
           Anterior();
       }else if(ae.getSource()==this.viewProductos.jbtnBusacr)
       {
           mostrarTable();
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
       }
       
       
    }
   
    private void initComponents() {
       
    }
    
}
