package Model;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import Oras.Strada;
import Oras.Intersectie;
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class Oras {
    private final List<Locatie> deseuri;
    private final List<Muchie> muchiiDeseuri = new ArrayList<>();
    private final List<Intersectie> intersectii = Arrays.asList(Intersectie.values());
    private final List<Muchie> muchii = new ArrayList<>();
    private final List<Locatie> noduri = new ArrayList<>();
    PersistentaLocatii persistentaLocatii = new PersistentaLocatii();

    public Oras(String utilizator){
        deseuri = persistentaLocatii.getLocatii(utilizator);
        deseuri.add(new Locatie(Strada.STR1, 0,utilizator));
        procesareMuchii();
        for(Locatie l:deseuri)
            algo_dijkstra(l);
    }

    public PersistentaLocatii getLocatii() {
        return this.persistentaLocatii;
    }
    public PersistentaLocatii getDeseuri(){
        return new PersistentaLocatii(this.deseuri);
    }

    public boolean adaugareLocatieDeseu(Locatie l){
        return this.persistentaLocatii.adaugareLocatie(l);
    }
    public boolean stergereLocatieDeseu(int index){
        return this.persistentaLocatii.stergereLocatie(index);
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
                for(Locatie l: this.persistentaLocatii.getLocatii(u.getNume()) ){
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

    public String convertToCSV(String[] data) {
        return String.join(",", data);
    }


    public void writeJson(String filename) throws Exception {
        StringBuilder s = new StringBuilder();
        for(Utilizator u:(new PersistentaUtilizatori()).getListaUtilizatori())
            if(u.getTip() == Tip.ANGAJAT){
                JSONObject sampleObject = new JSONObject();
                JSONArray messages = new JSONArray();
                for(Locatie l: this.persistentaLocatii.getLocatii(u.getNume()) ){
                    messages.put(l.toString1());
                }
                sampleObject.put(u.getNume(), messages);
                s.append(sampleObject.toString()).append("\n\n");
            }
        Files.write(Paths.get(filename), s.toString().getBytes());
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
                for(Locatie l: this.persistentaLocatii.getLocatii(u.getNume()) ){
                    Element el = doc.createElement("locatie");
                    el.appendChild(doc.createTextNode(l.toString1()));
                    re.appendChild(el);
                }
            }
            try (FileOutputStream output =
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
