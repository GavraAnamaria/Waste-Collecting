import model.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.stream.Collectors;

import Enum.Tip;

class Main{
    private static ServerSocket server;
    private static int port = 9876;

        public static void main(String[] argv) throws IOException, ClassNotFoundException {
            server = new ServerSocket(port);
            System.out.println("Waiting for the client request");
            Socket socket = server.accept();
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

            PersistentaUtilizatori utilizatori = new PersistentaUtilizatori();
            PersistentaLocatii locatii = new PersistentaLocatii();
            while(true){
                String comanda = (String) ois.readObject();
                switch (comanda) {
                    case "searchUser" :{
                        String username =(String) ois.readObject();
                        String password = (String) ois.readObject();
                        if(utilizatori.cautareUtilizator(username, password)!=null)
                        {String m = utilizatori.cautareUtilizator(username, password).toString();
                        oos.writeObject(m);
                        oos.flush();}
                        else
                            oos.writeObject("ADMINISTRATOR");
                        break;
                    }
                    case "allUsers" :{
                        for(Utilizator u : utilizatori.getUtilizatori()) {
                            oos.writeObject(u.toString());
                            oos.flush();
                        }
                        oos.writeObject("STOP");
                        oos.flush();
                        break;
                    }
                    case "deleteUser" :{
                        int user =Integer.parseInt((String)ois.readObject());
                        oos.writeObject(utilizatori.stergereUtilizator(user));
                        oos.flush();
                        break;
                    }
                    case "addUser" :{
                        oos.writeObject(String.valueOf(utilizatori.adaugareUtilizator(new Utilizator((String)ois.readObject(), (String)ois.readObject(), Tip.valueOf((String)ois.readObject()), (String)ois.readObject(), (String)ois.readObject()))));
                        oos.flush();
                        break;
                    }
                    case "delLoc" :{
                        int user =Integer.parseInt((String)ois.readObject());
                        oos.writeObject(String.valueOf(locatii.stergereLocatie(user)));
                        oos.flush();
                        break;
                    }
                    case "addLoc" :{
                        oos.writeObject(String.valueOf(locatii.adaugareLocatie((Locatie)ois.readObject())));
                        oos.flush();
                        break;
                    }
                    case "getAllLoc" :{
                        for(Locatie l : locatii.getLocatii()) {
                            oos.writeObject(l);
                            oos.flush();
                        }
                        oos.writeObject(null);
                        oos.flush();
                        break;
                    }
                    case "getUserLoc" :{
                        String name =String.valueOf(ois.readObject());
                        for(Locatie u : locatii.getLocatii(name)) {
                            oos.writeObject(u);
                            oos.flush();
                        }
                        oos.writeObject(null);
                        oos.flush();
                        break;
                    }
                    case "getUNames" :{
                        for(Utilizator u : utilizatori.getUtilizatori()) {
                            if(u.getTip().equals(Tip.ANGAJAT)) {
                                oos.writeObject(u.getNume());
                                oos.flush();
                            }
                        }
                        oos.writeObject("");
                        oos.flush();
                        break;
                    }
                    case "filter" :{
                        String f = (String)ois.readObject();
                        PersistentaUtilizatori allUtil = new PersistentaUtilizatori();
                        if(!f.equals("ALL") && !f.equals("TOT")) {
                            for (Tip t : Tip.values())
                                if (f.equals(t.name()))
                                    utilizatori.setUtilizatori(allUtil.getUtilizatori().stream().filter(u -> u.getTip().name().equals(f)).collect(Collectors.toList()));
                        } else
                            utilizatori = new PersistentaUtilizatori();
                          break;
                    }
                    case "utilizList" :{
                        PersistentaUtilizatori allUtil = new PersistentaUtilizatori();
                        for(Utilizator u : allUtil.getUtilizatori()) {
                            oos.writeObject(u);
                            oos.flush();
                        }
                        oos.writeObject(null);
                        oos.flush();
                          break;
                    }
                    default:
                        throw new IllegalStateException("Unexpected value: " + comanda);
                }
            }

               // socket.close();
           // server.close();
        }
}