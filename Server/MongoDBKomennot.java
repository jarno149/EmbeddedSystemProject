/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Mongodb;

import com.mongodb.DB;
import com.mongodb.MongoClient;


import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;
import com.mongodb.MongoClientOptions;

import com.mongodb.ServerAddress;
//import java.awt.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Axelius
 */
public class MongoDBKomennot {


 public static void main( String args[] ) {
        String salanro= "1234321";
        int i = Integer.parseInt(salanro);
        char[] charp = salanro.toCharArray();
        String collectionin_nimi = "temperatures";
        
      try{
		
         // To connect to mongodb server
         MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
			
         // Now connect to your databases
         DB db = mongoClient.getDB( "TemperatureDB" );
         DB db2 = mongoClient.getDB("TemperatureDB");
         System.out.println("Connect to database successfully");
         System.out.println(db2.toString());
        // boolean auth = db.authenticate("axi", "1234321".toCharArray());
        // boolean auth = db.authenticate("username", "password".toCharArray());
       // List<String> lista = new List<String>();
        List<String> lista = new ArrayList();
        lista = mongoClient.getDatabaseNames();
        HaePaivamaarallaList("TemperatureDB", i,new BasicDBObject("Aika", 121212));
        
        for (int l = 0; l < 3; l++)
        {
            System.out.println(lista.get(l).toString());
        }
        // System.out.println("Authentication: " +auth);
			
      }catch(Exception e){
         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      }

       }
           public static List<String> DBListStrings(){
               MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
               List<String> lista = new ArrayList();
               lista = mongoClient.getDatabaseNames();
               return lista;
        }
           
           public String PaivamaaraStringit(int paivamaaraXXYYZZZZ)
           {
               MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
               String s;
               s = "Tämä ei toimi vielä";
               return s;
           }
           public static List<String> HaePaivamaarallaList(String dbName, int pvm, BasicDBObject query){
               MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
               
               DB db = mongoClient.getDB(dbName);
               System.out.println(db.command("db.TemperatureDB.find();").toJson());
               DBCollection coll = db.getCollection("temperatures");
               DBObject tieto = coll.findOne();
               DBCursor osoitin = coll.find();
               //System.out.println(tieto);
                try 
                {
                    while(osoitin.hasNext()) {
                        System.out.println(osoitin.next());
                    }
                     } finally {
                        osoitin.close();
                     }
               
               return null;
           }
           

}
