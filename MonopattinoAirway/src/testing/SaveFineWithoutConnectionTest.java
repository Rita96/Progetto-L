package testing;

import controller.TicketCollectorSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import ticketcollector.TicketCollector;


public class SaveFineWithoutConnectionTest {


    public static void main(String[] args) throws IOException {
        TicketCollector tCollector = new TicketCollector("127.0.0.1");
        System.out.println("Stato controllore: "+tCollector.loginCollector("COLLECTOR", "COLLECTOR")+" == TRUE");
      
        
        System.out.println("Close centralSystem with line");
        BufferedReader line = new BufferedReader(new InputStreamReader(System.in));
        String ciao =line.readLine();
        
        TicketCollectorSession controller = new TicketCollectorSession(tCollector);
        controller.makingFine("cf", 5);
    }
    
}
