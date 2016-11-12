/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;
import sax.DBConnection;

import javax.swing.JOptionPane;
/**
 *
 * @author RoseLandjlord
 */
public class ModelProductos {
    private DBConnection conection = new DBConnection(3306,"localhost", "acme", "root", "");
    private int idProducto;
    private String producto;
    private String descripcion;
    private String precioCompra;
    private String precioVenta;
    private String existencia;
    private String nombre_imagen;
    private String JLFoto;
    /**
     * @return the producto
     */
    public String getProducto() {
        return producto;
    }

    /**
     * @param producto the producto to set
     */
    public void setProducto(String producto) {
        this.producto = producto;
    }

    /**
     * @return the Descripción
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param Descripción the Descripción to set
     */
    public void setDescripcion(String Descripción) {
        this.descripcion = Descripción;
    }

    /**
     * @return the precioCompra
     */
    public String getPrecioCompra() {
        return precioCompra;
    }

    /**
     * @param precioCompra the precioCompra to set
     */
    public void setPrecioCompra(String precioCompra) {
        this.precioCompra = precioCompra;
    }

    /**
     * @return the precioVenta
     */
    public String getPrecioVenta() {
        return precioVenta;
    }

    /**
     * @param precioVenta the precioVenta to set
     */
    public void setPrecioVenta(String precioVenta) {
        this.precioVenta = precioVenta;
    }

    /**
     * @return the existencia
     */
    public String getExistencia() {
        return existencia;
    }

    /**
     * @param existencia the existencia to set
     */
    public void setExistencia(String existencia) {
        this.existencia = existencia;
    }

    /**
     * @return the imagen
     */
    public String getNombre_imagen() {
        return nombre_imagen;
    }

    /**
     * @param imagen the imagen to set
     */
    public void setNombre_imagen(String imagen) {
        this.nombre_imagen = imagen;
    }

    /**
     * @return the idProducto
     */
    public int getIdProducto() {
        return idProducto;
    }

    /**
     * @param idProducto the idProducto to set
     */
    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }
    public void moveNext(){
        conection.moveNext();
        setValues();
    }
    
    public void movePrevious(){
        conection.movePrevious();
        setValues();
    }
    
      public static String Message(String OptMessage)
    {
     int Selection=JOptionPane.showOptionDialog(null,OptMessage,"Seleccione una opcion",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,new Object[] {"Si","No"},"Si");
     if(Selection != -1)
     {
         if((Selection +1)==1)
         {
             return "Si";
         }
         else
         {
            return "No";
         }
     
     }
     return null;
    }
   
    public void moveFirst(){
        conection.moveFirst();
        setValues();
    }
    
    public void moveLast(){
        conection.moveLast();
        setValues();
    }
    
    public void initValues(){
        conection.executeQuery("SELECT id_Producto,producto,descripción,precio_compra,precio_venta,existencia FROM productos;");
        conection.moveNext();
        setValues();
    }
    
    
    public void setValues(){
         this.idProducto = conection.getInteger("id_producto");
        this.producto = conection.getString("producto");
        this.descripcion = conection.getString("descripción");
        this.existencia = conection.getString("existencia");
        this.precioCompra = conection.getString("precio_compra");
        this.precioVenta = conection.getString("precio_venta");
       // this.nombre_imagen = conection.getString("imagen");
       //  this.jLFoto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/" + conection.getString("imagen"))));
                
    }
    
}
