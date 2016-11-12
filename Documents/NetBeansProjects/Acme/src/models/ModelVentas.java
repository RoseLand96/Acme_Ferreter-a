/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import javax.swing.JOptionPane;
import sax.DBConnection;

/**
 *
 * @author RoseLandjlord
 */
public class ModelVentas {
    private DBConnection conection = new DBConnection(3306,"localhost", "acme", "root", "");
    private int id_venta;
    private int id_detVent;

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
     * @return the id_venta
     */
    public int getId_venta() {
        return id_venta;
    }

    /**
     * @param id_venta the id_venta to set
     */
    public void setId_venta(int id_venta) {
        this.id_venta = id_venta;
    }
     public void initValues(){
         
        conection.executeQuery("SELECT MAX(id_venta)+1 FROM ventas;");
        //conection.executeQuery("SELECT MAX(id_detalle_venta)+1 FROM detalle_venta;");
        conection.moveNext();
        setValues();
    }
    
     public void setValues()
     {
         this.id_venta=conection.getInteger("MAX(id_venta)+1");
         //this.id_detVent=conection.getInteger("MAX(id_detalle_venta)+1");
         
     }

    /**
     * @return the id_detVent
     */
    public int getId_detVent() {
        return id_detVent;
    }

    /**
     * @param id_detVent the id_detVent to set
     */
    public void setId_detVent(int id_detVent) {
        this.id_detVent = id_detVent;
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
   
    
}

    