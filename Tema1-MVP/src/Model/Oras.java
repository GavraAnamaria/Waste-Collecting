package Model;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import Oras.Strada;
import Oras.Intersectie;

public class Oras {
    private List<Locatie> deseuri = new ArrayList<>();
    private List<Muchie> muchiiDeseuri = new ArrayList<>();
    private List<Intersectie> intersectii = Arrays.asList(Intersectie.values());
    private List<Muchie> muchii = new ArrayList<>();
    private List<Locatie> noduri = new ArrayList<>();
    PersistentaLocatii persistentaLocatii = new PersistentaLocatii();

    public Oras(String utilizator){
        deseuri = persistentaLocatii.getLocatii(utilizator);
        deseuri.add(new Locatie(Strada.STR1, 0));
        procesareMuchii();
        for(Locatie l:deseuri)
            algo_dijkstra(l);
    }

    public PersistentaLocatii getPersistenta() {
        return this.persistentaLocatii;
    }
    public PersistentaLocatii getDeseuri(){
        return new PersistentaLocatii(this.deseuri);
    }

    public void adaugareLocatieDeseu(Locatie l){
        this.persistentaLocatii.adaugareLocatie(l);
    }
    public void stergereLocatieDeseu(int index){
        this.persistentaLocatii.stergereLocatie(index);
    }


    public void writeData(){
        persistentaLocatii.saveToXML();
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
    private int dist[];
    private Set<Locatie> visited;
    private PriorityQueue<Muchie> pqueue;



    // Dijkstra's Algorithm implementation
    public void algo_dijkstra(Locatie sursa)
    {
        int V = noduri.size(); // Number of vertices
        //List<List<Node> > adj_list;
        dist = new int[V];
        visited = new HashSet<Locatie>();
        pqueue = new PriorityQueue<Muchie>(V, new Muchie());

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
        int edgeDistance = -1;
        int newDistance = -1;

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


    public void writeCSV()
    {
        File csvOutputFile = new File("rapoarte.csv");
        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            List<String[]> dataLines = new ArrayList<>();
            for(Utilizator u:(new PersistentaUtilizatori()).getListaUtilizatori()) 
                if(u.getTip() == Tip.ANGAJAT){
                int j = 0;
                String[] s = new String[100];
                s[j]=u.getNume();
                for(Locatie l: this.persistentaLocatii.getLocatii(u.getNume()) ){
                    j=j+1;
                    s[j] =l.toString(); 
                }
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
        return Stream.of(data)
                .collect(Collectors.joining(","));
    }



    public List<Locatie> traseuOptim(){

        List<Locatie> visited = new ArrayList<Locatie>();
        PriorityQueue<Muchie> queue = new PriorityQueue<Muchie>(deseuri.size(), new Muchie());
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
}
