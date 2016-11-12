/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import sax.DBConnection;

/**
 *
 * @author RoseLandjlord
 */
public class ModelProveedores {
     private DBConnection conection = new DBConnection(3306,"localhost", "acme", "root", "");
     private int id;
     private String nombre;
     private String rfc;
     private String calle;
     private int no;
     private String colonia;
     private String ciudad;
     private String estado;
     private String nom_cont;
     private String telefono;
     private String email;

    /**
     * @return the conection
     */
    public DBConnection getConection() {
        return conection;
    }

    /**
     * @param conection the conection to set
     */
    public void setConection(DBConnection conection) {
        this.conection = conection;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the rfc
     */
    public String getRfc() {
        return rfc;
    }

    /**
     * @param rfc the rfc to set
     */
    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    /**
     * @return the calle
     */
    public String getCalle() {
        return calle;
    }

    /**
     * @param calle the calle to set
     */
    public void setCalle(String calle) {
        this.calle = calle;
    }

    /**
     * @return the no
     */
    public int getNo() {
        return no;
    }

    /**
     * @param no the no to set
     */
    public void setNo(int no) {
        this.no = no;
    }

    /**
     * @return the colonia
     */
    public String getColonia() {
        return colonia;
    }

    /**
     * @param colonia the colonia to set
     */
    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    /**
     * @return the ciudad
     */
    public String getCiudad() {
        return ciudad;
    }

    /**
     * @param ciudad the ciudad to set
     */
    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    /**
     * @return the estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * @return the nom_cont
     */
    public String getNom_cont() {
        return nom_cont;
    }

    /**
     * @param nom_cont the nom_cont to set
     */
    public void setNom_cont(String nom_cont) {
        this.nom_cont = nom_cont;
    }

    /**
     * @return the telefono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * @param telefono the telefono to set
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
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
        conection.executeQuery("SELECT id_Proveedor,nombre,rfc,calle,no,colonia,ciudad,estado,nombre_contacto,telefono,email FROM proveedores;");
        conection.moveNext();
        setValues();
    }
    
    
    public void setValues(){
         this.id = conection.getInteger("id_proveedor");
        this.nombre = conection.getString("nombre");
        this.rfc = conection.getString("rfc");
        this.calle = conection.getString("calle");
        this.no = conection.getInteger("no");
        this.colonia= conection.getString("colonia");
         this.estado = conection.getString("estado");
        this.nom_cont = conection.getString("nombre_contacto");
        this.telefono = conection.getString("telefono");
        this.email = conection.getString("email");
          
    }
    
}
