package model;

import Enum.Strada;
import Enum.Tip;
import lombok.Setter;
import model.State.Context;
import model.State.ErrorState;
import model.State.SuccesState;
import model.adapter.Adapter;
import model.adapter.Json1;
import model.enums.Intersectie;
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@Setter
public class Oras extends Observable{
    private final List<Locatie> deseuri;
    private final List<Muchie> muchiiDeseuri = new ArrayList<>();
    private final List<Intersectie> intersectii = Arrays.asList(Intersectie.values());
    private final List<Muchie> muchii = new ArrayList<>();
    private final List<Locatie> noduri = new ArrayList<>();
 //   PersistentaLocatii persistentaLocatii;
    private List<Locatie> locatii ;
    private Client client;
    private Context context= new Context();

    public Oras(String utilizator, Client c){
        client = c;
    //    this.persistentaLocatii = new PersistentaLocatii();
        locatii = getAllLoc();
        deseuri = getUserLoc(utilizator);
        deseuri.add(new Locatie(Strada.STR1, 0,utilizator));
        procesareMuchii();
        for(Locatie l:deseuri)
            algo_dijkstra(l);
    }

    public List<Locatie> getAllLoc(){
        client.sendCommand("getAllLoc");
        List<Locatie> l = new ArrayList<>();
        Locatie loc = null;
        do{
            loc = client.readLoc();
            if(loc!=null){
                l.add(loc);
            }
        }while(loc!=null);
            return l;
    }
    public List<String> getUNames(){
        client.sendCommand("getUNames");
        List<String> l = new ArrayList<>();
        String s = "";
        do{
            s = client.readMessage();
            if(!s.equals("")){
                l.add(s);
            }
        }while(!s.equals(""));
            return l;
    }
    public List<Locatie> getUserLoc(String username){
        client.sendCommand("getUserLoc");
        client.sendCommand(username);
        List<Locatie> l = new ArrayList<>();
        Locatie loc = null;
        do{
            loc = client.readLoc();
            if(loc!=null){
                l.add(loc);
            }
        }while(loc!=null);
            return l;
    }
    public String[] getLocListToString(List<Locatie> l){
        String[] s = new String[l.size()];
        for(int i=0; i<l.size();i++){
            s[i] = "[ " + i + " ]  " + l.get(i).toString();
        }
        return s;
    }

    public PersistentaLocatii getDeseuri(){
        return new PersistentaLocatii(this.deseuri);
    }

    public boolean adaugareLocatieDeseu(Locatie l){
        client.sendCommand("addLoc");
        client.sendLoc(l);
        String s=client.readMessage();
        notifyUpdate();
        System.out.println(s);
        return Boolean.parseBoolean(s);
    }
    public void stergereLocatieDeseu(int index){
        if(index == -1)
            context.setState(new ErrorState());
        else
            context.setState(new SuccesState(client, index));
        context.doAction();
        notifyUpdate();
    }


    public void procesareMuchii() {
        for (Intersectie i : intersectii) {    //luam fiecare intersectie din enum
            List<Locatie> locIntersectie = i.getLoc();
            for (Locatie l : locIntersectie) { //luam fiecare locatie din intersectie
                noduri.add(l);
                for (int index = locIntersectie.indexOf(l) + 1; index < locIntersectie.size(); index++) {  //egalam costul muchiei cu toate celalalte locatii din intersectie cu 0
                    muchii.add(new Muchie(l, locIntersectie.get(index), 0));
                }
            }
        }
        noduri.addAll(deseuri);
        for (Locatie n : noduri) {
            for (int index = noduri.indexOf(n) + 1; index < noduri.size(); index++) {
                if (n.getStrada().equals(noduri.get(index).getStrada()))
                    muchii.add(new Muchie(n, noduri.get(index), Math.abs(n.getNr() - noduri.get(index).getNr())));
            }
        }
    }
    private int[] dist;
    private Set<Locatie> visited;
    private PriorityQueue<Muchie> pqueue;



    // Dijkstra's Algorithm implementation
    public void algo_dijkstra(Locatie sursa)
    {
        int V = noduri.size(); // Number of vertices
        dist = new int[V];
        visited = new HashSet<>();
        pqueue = new PriorityQueue<>(V, new Muchie());

        for (int i = 0; i < V; i++)
            dist[i] = Integer.MAX_VALUE;

        // first add source vertex to PriorityQueue
        pqueue.add(new Muchie(sursa, new Locatie(), 0));

        // Distance to the source from itself is 0
        dist[noduri.indexOf(sursa)] = 0;
        while (visited.size() != V) {

            // u is removed from PriorityQueue and has min distance
            Muchie u = pqueue.remove();

            // add node to finalized list (visited)
            visited.add(u.getSursa());
            graph_adjacentNodes(u.getSursa());
        }
        for(Locatie l : noduri) {
            if((deseuri.contains(l)) && (l!= sursa))
                muchiiDeseuri.add(new Muchie(sursa, l, dist[noduri.indexOf(l)]));
        }
    }
    // this methods processes all neighbours of the just visited node
    private void graph_adjacentNodes(Locatie u)   {
        int edgeDistance ;
        int newDistance ;

        // process all neighbouring nodes of u
        for(Muchie m:muchii){
            Locatie v = null;
            if(u.equals(m.getSursa())  && !visited.contains(m.getDest()))
                v = m.getDest();
            else if(u.equals(m.getDest())  && !visited.contains(m.getSursa()))
                v = m.getSursa();
            if (v!=null) {
                edgeDistance = m.getCost();
                newDistance = dist[noduri.indexOf(u)] + edgeDistance;
                // compare distances
                if (newDistance < dist[noduri.indexOf(v)])
                    dist[noduri.indexOf(v)] = newDistance;
                // Add the current vertex to the PriorityQueue
                pqueue.add(new Muchie(v,new Locatie(), noduri.indexOf(v)));
            }
        }
    }


    public void writeCSV(String path)
    {
        File csvOutputFile = new File(path);
        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            List<String[]> dataLines = new ArrayList<>();
            for(Utilizator u:(new PersistentaUtilizatori()).getListaUtilizatori())
                if(u.getTip() == Tip.ANGAJAT){
                int j = 0;
                String[] s = new String[100];
                s[j]=u.getNume();
                for(Locatie l: this.getUserLoc(u.getNume()) ){
                    j=j+1;
                    s[j] =l.toString()+"\n";
                }
                s[j]+="\n\n";
                    dataLines.add(s);
            }
            dataLines.stream()
                    .map(this::convertToCSV)
                    .forEach(pw::println);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void generateAll(String path) throws Exception {
        path+=".json";
      writeJson(path);
       Json1 json = new Json1(path, this.getUNames());
       Adapter adapter = new Adapter(json);
       adapter.convert();
    }

    public String convertToCSV(String[] data) {
        return String.join(",", data);
    }


    public void writeJson(String filename) throws Exception {
        StringBuilder s = new StringBuilder();
        JSONArray sampleObject1 = new JSONArray();
        JSONObject sampleObject2 = new JSONObject();
        JSONArray messages1 = new JSONArray();
        for(Utilizator u:(new PersistentaUtilizatori()).getListaUtilizatori())
            if(u.getTip() == Tip.ANGAJAT){
                JSONObject sampleObject = new JSONObject();
                StringBuilder str = new StringBuilder(new String(""));
                for(Locatie l: this.getUserLoc(u.getNume()) ){
                    str.append(l.toString1());
                }
               // str.append("\n ");
                sampleObject.put(u.getNume(), str.toString());
                messages1.put(sampleObject);
                s.append(sampleObject.toString()).append("\n\n");
            }
        sampleObject1.put( messages1);
        sampleObject2.put("UTILIZATOR", sampleObject1);
        Files.write(Paths.get(filename), sampleObject2.toString().getBytes());
    }



    public List<Locatie> traseuOptim(){
        List<Locatie> visited = new ArrayList<>();
        PriorityQueue<Muchie> queue = new PriorityQueue<>(deseuri.size(), new Muchie());
        Locatie sursa = new Locatie(Strada.STR1, 0);
        queue.add(new Muchie(sursa, new Locatie(),0));
        while(visited.size() != deseuri.size()){
            // u is removed from PriorityQueue and has min distance
            Muchie u = queue.remove();
            queue.removeAll(Arrays.asList(queue.toArray()));
        // add node to finalized list (visited)
        visited.add(u.getSursa());
        for(Muchie m:muchiiDeseuri){
            if((m.getSursa().equals(sursa)) && (!visited.contains(m.getDest())))
                queue.add(new Muchie(m.getDest(), new Locatie(), m.getCost()));
            else if((m.getDest().equals(sursa)) &&(!visited.contains(m.getSursa())))
                queue.add(new Muchie(m.getSursa(), new Locatie(), m.getCost()));
        }
        }
        return visited;
    }

    public void writeXml(String path) throws ParserConfigurationException {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("angajati");
            doc.appendChild(rootElement);

        for(Utilizator u:(new PersistentaUtilizatori()).getListaUtilizatori())
            if(u.getTip() == Tip.ANGAJAT){
                Element re = doc.createElement("angajat");
                re.appendChild(doc.createTextNode(u.getNume()));
                rootElement.appendChild(re);
                for(Locatie l: this.getUserLoc(u.getNume()) ){
                    Element el = doc.createElement("locatie");
                    el.appendChild(doc.createTextNode(l.toString1()));
                    re.appendChild(el);
                }
            }try (FileOutputStream output =
                         new FileOutputStream(path)) {
                writeXml(doc, output);
            } catch (IOException | TransformerException e) {
                e.printStackTrace();
            }

        }

        private static void writeXml(Document doc, OutputStream output) throws TransformerException {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(output);
            transformer.transform(source, result);

        }
}
