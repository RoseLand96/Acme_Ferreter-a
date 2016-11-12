/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import sax.DBConnection;
import views.ViewProveedores;
import models.ModelProveedores;

/**
 *
 * @author RoseLandjlord
 */
public class ControllerProveedores implements ActionListener{
     private DBConnection conection = new DBConnection(3306,"localhost", "acme", "root", "");
     public ViewProveedores viewProveedores;
     public ModelProveedores modelProveedores;
     DefaultTableModel model= new DefaultTableModel();
     
    public ControllerProveedores(ModelProveedores modelProveedores,ViewProveedores viewProveedores)
    {
        this.modelProveedores=modelProveedores;
        this.viewProveedores=viewProveedores;
        this.viewProveedores.jbtnAgregar.addActionListener(this);
        this.viewProveedores.jbtnEliminar.addActionListener(this);
        this.viewProveedores.jbtnModificar.addActionListener(this);
        this.viewProveedores.jbtnPrimero.addActionListener(this);
        this.viewProveedores.jbtnSiguiente.addActionListener(this);
        this.viewProveedores.jbtnUltimo.addActionListener(this);
        this.viewProveedores.jbtnBuscar.addActionListener(this);
        this.viewProveedores.jbtnNuevo.addActionListener(this);
        this.viewProveedores.jftID_prov.setVisible(false);
           initValues();
        
    }
    
    public void initValues()
    {
        this.modelProveedores.initValues();
        showValues();
        
    }
    public void showValues()
    {
        this.viewProveedores.jftID_prov.setText(""+this.modelProveedores.getId());
        this.viewProveedores.jtfNombre.setText(""+this.modelProveedores.getNombre());
         this.viewProveedores.jtfCalle.setText(""+this.modelProveedores.getCalle());
          this.viewProveedores.jtfCiudad.setText(""+this.modelProveedores.getCiudad());
         this.viewProveedores.jtfColonia.setText(""+this.modelProveedores.getColonia());
          this.viewProveedores.jtfEmail.setText(""+this.modelProveedores.getEmail());
          this.viewProveedores.jtfEstado.setText(""+this.modelProveedores.getEstado());
           this.viewProveedores.jtfNo.setText(""+this.modelProveedores.getNo());
          this.viewProveedores.jtfNom_contact.setText(""+this.modelProveedores.getNom_cont());
           this.viewProveedores.jtfTelefono.setText(""+this.modelProveedores.getTelefono());
            this.viewProveedores.jtfRfc.setText(""+this.modelProveedores.getRfc());
        
       
    }
    public void Primero(){
       modelProveedores.moveFirst();
        showValues();
    }
   
    public void Siguiente()
    {
        modelProveedores.moveNext();
        showValues();
    
    
    }
    public void Anterior()
    {
         modelProveedores.movePrevious();
        showValues();
    } public void ultimo()
    {
         modelProveedores.moveLast();
         showValues();
    }
    public void bloquearDesbloquear(boolean todos) {
        this.viewProveedores.jbtnAgregar.setEnabled(todos);
        this.viewProveedores.jbtnAnterior.setEnabled(!todos);
        //this.viewProveedores.jbtnBusacr.setEnabled(!todos);
        
        this.viewProveedores.jbtnModificar.setEnabled(!todos);
        this.viewProveedores.jbtnEliminar.setEnabled(!todos);
        this.viewProveedores.jbtnPrimero.setEnabled(!todos);
        this.viewProveedores.jbtnSiguiente.setEnabled(!todos);
        this.viewProveedores.jbtnUltimo.setEnabled(!todos);
        
           
         
        
    }
public void DesbloquearBloquear(boolean todos)
{
    this.viewProveedores.jbtnAgregar.setEnabled(todos);
        this.viewProveedores.jbtnAnterior.setEnabled(todos);
        //this.viewProveedores.jbtnBusacr.setEnabled(!todos);
        
        this.viewProveedores.jbtnModificar.setEnabled(todos);
        this.viewProveedores.jbtnEliminar.setEnabled(todos);
        this.viewProveedores.jbtnPrimero.setEnabled(todos);
        this.viewProveedores.jbtnSiguiente.setEnabled(todos);
        this.viewProveedores.jbtnUltimo.setEnabled(todos);


}
    public void buscar()
{
    String[] campos={"nombre","rfc","ciudad","estado","calle","nombre contacto","telefono","email"};
    String[] registros=new String[50];
    String sql="select * from proveedores where nombre like '%"+this.viewProveedores.jtfBuscar.getText()+"%'"
                    +"OR rfc like'%"+this.viewProveedores.jtfBuscar.getText()+"%'"
                    +"OR calle like '%"+this.viewProveedores.jtfBuscar.getText()+"%'"
                    +"OR ciudad like '%"+this.viewProveedores.jtfBuscar.getText()+"%'"
                    +"OR colonia like '%"+this.viewProveedores.jtfBuscar.getText()+"%'"
                    +"OR estado like'%"+this.viewProveedores.jtfBuscar.getText()+"%'"
                    +"OR nombre_contacto like '%"+this.viewProveedores.jtfBuscar.getText()+"%'"
                    +"OR nombre like '%"+this.viewProveedores.jtfBuscar.getText()+"%'"
                    +"OR telefono like '%"+this.viewProveedores.jtfBuscar.getText()+"%'"
                    +"OR email like '%"+this.viewProveedores.jtfBuscar.getText()+"%'";
    model= new DefaultTableModel(null,campos);
    if(!this.viewProveedores.jtfBuscar.getText().equals("")){
    try{
        
         conection.executeQuery(sql);
         while(conection.moveNext())
         {
             registros[0]=conection.getString("nombre");
             registros[1]=conection.getString("rfc");
             registros[2]=conection.getString("ciudad");
             registros[3]=conection.getString("estado");
             registros[4]=conection.getString("calle");
             registros[5]=conection.getString("no");
             registros[6]=conection.getString("nombre_contacto");
             registros[7]=conection.getString("telefono");
             registros[8]=conection.getString("email");
             
             model.addRow(registros);
              
         }
          this.viewProveedores.jTable1.setModel(model);
       
    }catch(Exception ex)
    {
        JOptionPane.showMessageDialog(viewProveedores, ex);
    }
    }else
    {
        JOptionPane.showMessageDialog(null, "No hay nada a buscar");
    }

} 
    public void Guardar()
{
     try{
           
             String respu=this.modelProveedores.Message("Desea Guardar");
                
            String nombre=this.viewProveedores.jtfNombre.getText();
             String id=this.viewProveedores.jftID_prov.getText();
            //String id_producto=this.viewProductos.jLId.getText();
            String rfc=this.viewProveedores.jtfRfc.getText();
            String calle=this.viewProveedores.jtfCalle.getText();
            String ciudad=this.viewProveedores.jtfCiudad.getText();
            String colonia=this.viewProveedores.jtfColonia.getText();
            String estado=this.viewProveedores.jtfColonia.getText();
            String email=this.viewProveedores.jtfEmail.getText();
            String nom_cont=this.viewProveedores.jtfNom_contact.getText();
            String no=this.viewProveedores.jtfNo.getText();
            String tel=this.viewProveedores.jtfTelefono.getText();
            
            
            if (!nombre.equals("") && !rfc.equals("") && !calle.equals("") && !ciudad.equals("") && !colonia.equals("") && !estado.equals("") && respu=="Si")
                 {
            String sql= "insert into proveedores(nombre,rfc,calle,no,colonia,ciudad,estado,nombre_contacto,telefono,email) values (" + "'"+nombre+"','"+rfc+"','"+calle+"','"+no+"','"+colonia+"','"+ciudad+"','"+estado+"','"+nom_cont+"','"+tel+"','"+email+"');";
            
            System.out.println("SQL " + sql);
            
            conection.executeUpdate(sql);
            
           conection.executeQuery("Select * from proveedores");
           Primero();
           showValues();
           DesbloquearBloquear(true);
           JOptionPane.showMessageDialog(viewProveedores,"Operación Exitosa");
                 } else if (nombre.equals("") || rfc.equals("") || calle.equals("") || ciudad.equals("") || colonia.equals("") && respu=="Si")
                 {
            
                     JOptionPane.showMessageDialog(viewProveedores,"Operación fracasada, verifique si no hay campo vacio");
                 }else
                 {
                 
                 JOptionPane.showMessageDialog(viewProveedores,"Operación cancelada");
                
                 }
             this.modelProveedores.initValues();
           
        }catch (Exception err){
            JOptionPane.showMessageDialog(this.viewProveedores, "No hay producto seleccionado");
        }
        

}
    
     public void Eliminar()
    {
        try{ 
             String respu=this.modelProveedores.Message("Desea Eliminar");
                 if (respu=="Si")
                 {
                String id=this.viewProveedores.jftID_prov.getText();

              conection.executeUpdate("delete from proveedores where id_proveedor="+ id);
              conection.executeQuery("Select * from proveedores order by id_proveedor");

              this.viewProveedores.jtfNombre.setText("");                         

               this.viewProveedores.jtfCalle.setText("");
               this.viewProveedores.jtfCiudad.setText(""); 

               this.viewProveedores.jtfColonia.setText("");
               this.viewProveedores.jtfEmail.setText(""); 
               this.viewProveedores.jtfEstado.setText("");                         

               this.viewProveedores.jtfNo.setText("");
               this.viewProveedores.jtfNom_contact.setText(""); 

               this.viewProveedores.jtfRfc.setText("");
               this.viewProveedores.jtfTelefono.setText(""); 
                JOptionPane.showMessageDialog(viewProveedores,"Operación exitosa");
                
                 }else
                    {
                        JOptionPane.showMessageDialog(viewProveedores,"Operación cancelada");
                    }      
                  this.modelProveedores.initValues();
            
        }catch(Exception err){ 
            JOptionPane.showMessageDialog(null,"No hay producto seleccionado"); 
        } 
    }
    public void Editar()
    {
        
            String nombre=this.viewProveedores.jtfNombre.getText();
             String id=this.viewProveedores.jftID_prov.getText();
            //String id_producto=this.viewProductos.jLId.getText();
            String rfc=this.viewProveedores.jtfRfc.getText();
            String calle=this.viewProveedores.jtfCalle.getText();
            String ciudad=this.viewProveedores.jtfCiudad.getText();
            String colonia=this.viewProveedores.jtfColonia.getText();
            String estado=this.viewProveedores.jtfColonia.getText();
            String email=this.viewProveedores.jtfEmail.getText();
            String nom_cont=this.viewProveedores.jtfNom_contact.getText();
            String no=this.viewProveedores.jtfNo.getText();
            String tel=this.viewProveedores.jtfTelefono.getText();
            
        try{ 
           
                String respu=this.modelProveedores.Message("Desea Editar");
                   if (!nombre.equals("") && !rfc.equals("") && !calle.equals("") && !ciudad.equals("") && !colonia.equals("") && !estado.equals("") && respu=="Si")
                 {
               

               conection.executeUpdate("update proveedores set nombre='"+nombre+"',rfc='"+rfc+"',calle='"+calle+"',no='"+no+"',colonia='"+colonia+"',ciudad='"+ciudad+"',estado='"+estado+"',nombre_contacto='"+nom_cont+"',telefono='"+tel+"',email='"+email+"' where id_proveedor='"+id+"';");

               this.viewProveedores.jtfNombre.setText("");                         

               this.viewProveedores.jtfCalle.setText("");
               this.viewProveedores.jtfCiudad.setText(""); 

               this.viewProveedores.jtfColonia.setText("");
               this.viewProveedores.jtfEmail.setText(""); 
               this.viewProveedores.jtfEstado.setText("");                         

               this.viewProveedores.jtfNo.setText("");
               this.viewProveedores.jtfNom_contact.setText(""); 

               this.viewProveedores.jtfRfc.setText("");
               this.viewProveedores.jtfTelefono.setText(""); 
               
               JOptionPane.showMessageDialog(viewProveedores,"Operación exitosa");
                    } else if (nombre.equals("") || rfc.equals("") || calle.equals("") || ciudad.equals("") || colonia.equals("") && respu=="Si")
                 {
            
                     JOptionPane.showMessageDialog(viewProveedores,"Operación fracasada, verifique si no hay campo vacio");
                 }else
                 {
                 
                 JOptionPane.showMessageDialog(viewProveedores,"Operación cancelada");
                
                 }

                    this.modelProveedores.initValues();
                //this.rs.first();   
       } catch(Exception err){ 
            JOptionPane.showMessageDialog(null,"No hay producto seleccionado "); 
        } 
    }
    public void nuevo()
    {
        this.viewProveedores.jtfNombre.setText("");                         

               this.viewProveedores.jtfCalle.setText("");
               this.viewProveedores.jtfCiudad.setText(""); 

               this.viewProveedores.jtfColonia.setText("");
               this.viewProveedores.jtfEmail.setText(""); 
               this.viewProveedores.jtfEstado.setText("");                         

               this.viewProveedores.jtfNo.setText("");
               this.viewProveedores.jtfNom_contact.setText(""); 

               this.viewProveedores.jtfRfc.setText("");
               this.viewProveedores.jtfTelefono.setText(""); 
    }
 
    

    @Override
    public void actionPerformed(ActionEvent ae) {
     if(ae.getSource()==this.viewProveedores.jbtnPrimero)
     {
         Primero();
     }else if(ae.getSource()==this.viewProveedores.jbtnSiguiente)
     {
         Siguiente();
     }else if(ae.getSource()==this.viewProveedores.jbtnUltimo)
     {
           ultimo();

     }else if(ae.getSource()==this.viewProveedores.jbtnAnterior)
     {
        Anterior();
     }else if(ae.getSource()==this.viewProveedores.jbtnAgregar)
     {
         Guardar();
     }else if(ae.getSource()==this.viewProveedores.jbtnEliminar)
     {
         Eliminar();
     }else if(ae.getSource()==this.viewProveedores.jbtnModificar)
     {
         Editar();
     }else if(ae.getSource()==this.viewProveedores.jbtnBuscar)
     {
         buscar();
     }else if(ae.getSource()==this.viewProveedores.jbtnNuevo)
     {
         nuevo();
     }
      
    }
    
    
}
