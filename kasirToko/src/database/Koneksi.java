/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.*;

/**
 *
 * @author Asus
 */
public class Koneksi {
    Connection connection;
    Statement statement;
    String query;
    
    public Koneksi(){
        
    }
    
    public Connection connectionDatabase(){
        try{
            
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql:///kasir_toko_prototype",
                    "root",
                    ""
            );
            
        }catch(ClassNotFoundException | SQLException e){
            System.out.println("Error Connection Database : " + e.getMessage());
        }
        return connection;
    }
    
    public Connection closeDatabase(){
        try{
            connection.close();
        }catch(SQLException e){
            System.out.println("Error Close Database : " + e.getMessage());
        }
        
        return connection;
    }
    
    public ResultSet eksekusiQuery(String sqlQuery){
        connectionDatabase();
        ResultSet resultSet = null;
        try{
            statement = (Statement) connection.createStatement();
            resultSet = statement.executeQuery(sqlQuery);
            
        }catch(SQLException e){
            System.out.println("Error eksekusi Query : " + e.getMessage());
        }
        
        return resultSet;
    }
    
    public String eksekusiUpdate(String sqlQuery){
        connectionDatabase();
        String result = "";
        System.out.println(sqlQuery);
        try{
            statement = (Statement)connection.createStatement();
            statement.executeUpdate(sqlQuery);
        }catch(SQLException e){
            System.out.println("Fungsi Eksekusi update database Error: " + e.getMessage());
        }
        
        return result;
    }
    
    //overload fungsi untuk eksekusi query select semua kolom dengan where
    public ResultSet querySelect(String nameTable, String condition){
        query = "SELECT * FROM " + nameTable + " WHERE " + condition;
        System.out.println(query);
        return this.eksekusiQuery(query);
    }
    
    //fungsi untuk eksekusi query select dengan kolom spesifik
    public ResultSet querySellect(String[] nameColumn, String nameTable){
        query = "SELECT ";
        for(int i = 0; i < nameColumn.length; i++){
            query += nameColumn[i];
            
            if(i < nameColumn.length -1){
                query += ",";
            }
        }
        query += " FROM " + nameTable;
        System.out.println(query);
        return this.eksekusiQuery(query);
    }
    
    //overload fungsi untuk eksekusi query dengan kolom spesifik dengan where
    public ResultSet selectCommand(String[] column, String nameTable, String condition){
        query = " SELECT ";
        for(int i = 0; i < column.length; i++){
            if(i < column.length - 1){
                query += ",";
            }
        }
        query += " FROM " + nameTable + " WHERE " + condition;
        System.out.println(query);
        return this.eksekusiQuery(query);
    }
    
    //fungsi eksekusi query insert
    public String queryInsert(String nameTable, String[] nameColumn, String[] value){
        query = "INSERT INTO " + nameTable + " (";
        for(int i= 0; i < nameColumn.length; i++){
            query += nameColumn[i];
            if( i < nameColumn.length - 1){
                query += ",";
            }
        }
         query += ") VALUES (";
         for(int i = 0; i < value.length; i++){
             query += "'" + value[i] + "'";
             if(i < value.length - 1){
                 query += ",";
             }
         }
         query += ")";
         System.out.println(query);
         return this.eksekusiUpdate(query);
    }
    
    //fungsi eksekusi query delete
    public String queryDelete(String nameTable){
        query = "DELETE FROM " + nameTable;
        return this.eksekusiUpdate(query);
    }
}
