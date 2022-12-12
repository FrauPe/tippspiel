/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server;
import abiturklassen.netzklassen.*;
import abiturklassen.listenklassen.List;
import server.Kundenverwaltung;

/**
 *
 * @author q2.02Schaaf
 */
public class Tippserver extends Server {

    Kundenverwaltung data;

    public Tippserver(){
        super(2000);
        Kundenverwaltung data = new Kundenverwaltung();
    }

    public void processNewConnection(String pClientIP, int pClientPort) {
    
    }

    public void processMessage(String pClientIP, int pClientPort, String pMessage) {
        int userID;

        userID = data.getUserID(pClientIP, pClientPort);

    }
    
    public void processClosedConnection(String pClientIP, int pClientPort) {
      
    }
    
}
