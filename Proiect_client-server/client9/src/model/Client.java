package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    private ObjectInputStream ois=null;
    private ObjectOutputStream oos =null;
    private Socket socket;

    public void connect() throws IOException {
        InetAddress host = InetAddress.getLocalHost();
        socket = new Socket("127.0.0.1", 9876);
        oos = new ObjectOutputStream(socket.getOutputStream());
        ois = new ObjectInputStream(socket.getInputStream());
    }

    public void sendCommand(String m)  {
        try {
            oos.writeObject(m);
            oos.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public String readMessage()  {
        try {
            return (String) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }




    public Utilizator readU() {
        try {
            return (Utilizator) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Locatie readLoc() {
        try {
            return (Locatie) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendLoc(Locatie l) {
        try {
            oos.writeObject(l);
            oos.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
