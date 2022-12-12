/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server;

/**
 *
 * @author q2.02Schaaf
 */
public class Kundenverwaltung {

    public Kundenverwaltung() {}


    public int prüfeAnmeldedaten(String bentzername, String pwd, String IP, int port) {return 0;}
    public int getUserID(String IP, int Port) {return 0;}
    public void setzeTipp(int UserID, int spielID, int tore1, int tore2) {}
    public int[] getTipp(int UserID, int SpielID) {return null;}
    public void abmelden(int UserID) {}
    public void erstelleUser(String Username, String pwd) {}
}
