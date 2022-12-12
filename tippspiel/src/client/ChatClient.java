package client;


import javax.swing.*;
import abiturklassen.netzklassen.*;

public class ChatClient extends Client {

  final String ENDE = "*bye*";
  JTextArea textbereich;
  
  public ChatClient(String serverIP, JTextArea pTextbereich){
    super(serverIP, 2000);
    textbereich=pTextbereich;
  }
  
  public void processMessage(String pMessage){
     if (this.istVerbunden())
       if (pMessage.equals(ENDE))
         this.close();
       else
         textbereich.setText(textbereich.getText() + "\n" + pMessage);
  }
  
  public void disconnect() {
    this.send(ENDE);
  }
}
