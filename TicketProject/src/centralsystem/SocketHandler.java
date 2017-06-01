package centralsystem;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;



public class SocketHandler extends Thread {
    ServerSocket socketListener = null;
    Socket newSocket  = null;
    ArrayList<Skeleton> connectionList;
    CSystem centralSystem;
     
    public SocketHandler (ServerSocket socketListener,CSystem centralSystem) {
       this.socketListener = socketListener;
       this.centralSystem = centralSystem;
       connectionList = new ArrayList<Skeleton>();
    }

    @Override
    public void run() {
        while(true){
            try {
                newSocket = socketListener.accept();
                connectionList.add(new Skeleton(newSocket,centralSystem));
                connectionList.get(connectionList.size()-1).start();
                removeDeadThread();          
            }catch (IOException ex) {
                System.err.println("Socket handler error");
            }
            
        }
    }

    private void removeDeadThread() {
        ArrayList<Skeleton> toRemove = new ArrayList<>();
        for (Skeleton stubTicket : connectionList) {
                    if(!stubTicket.isAlive()) toRemove.add(stubTicket);
        }
         connectionList.removeAll(toRemove);
    }
    
}
