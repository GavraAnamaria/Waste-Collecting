package Model;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PersistentaUtilizatori {
    List<Utilizator> utilizatori ;

    public PersistentaUtilizatori(){
       utilizatori =  readXMLFile();
    }
    public PersistentaUtilizatori(List<Utilizator> u){
       utilizatori =  u;
    }
    public List<Utilizator> getListaUtilizatori (){
        return utilizatori;
    }


    public List<Utilizator> readXMLFile() {
        List<Utilizator> u = new ArrayList<>();
        try {
            File fXmlFile = new File("utilizatori.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("utilizator");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    u.add(new Utilizator(eElement.getElementsByTagName("nume").item(0).getTextContent(),  eElement.getElementsByTagName("parola").item(0).getTextContent(), Tip.valueOf(eElement.getElementsByTagName("tip").item(0).getTextContent())));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return u;
    }

    public Tip cautareUtilizator(String uname, String parola){
        for(Utilizator u:utilizatori){
            if(u.getNume().equals(uname) && u.getParola().equals(parola))
                return u.getTip();
        }
        return null;
    }

    public boolean adaugareUtilizator(Utilizator u){
        for(Utilizator util:utilizatori)
            if(util.equals(u))
                return false;
        System.out.println(utilizatori.size());
       return this.utilizatori.add(u);
    }

    public Utilizator stergereUtilizator(int poz) throws IndexOutOfBoundsException{
        return(utilizatori.remove(poz));
    }

    public void saveToXML() {
        Document dom;
        Element e = null;

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            dom = db.newDocument();

            Element rootEle = dom.createElement("util");
            for(Utilizator u:utilizatori) {
                Element elem = dom.createElement("utilizator");
                e = dom.createElement("nume");
                e.appendChild(dom.createTextNode(u.getNume()));
                elem.appendChild(e);
                e = dom.createElement("parola");
                e.appendChild(dom.createTextNode(u.getParola()));
                elem.appendChild(e);
                e = dom.createElement("tip");
                e.appendChild(dom.createTextNode(u.getTip().toString()));
                elem.appendChild(e);
                rootEle.appendChild(elem);
            }
            dom.appendChild(rootEle);

            try {
                Transformer tr = TransformerFactory.newInstance().newTransformer();
                tr.setOutputProperty(OutputKeys.INDENT, "yes");
                tr.setOutputProperty(OutputKeys.METHOD, "xml");
                tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
                tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

                // send DOM to file
                tr.transform(new DOMSource(dom),
                        new StreamResult(new FileOutputStream("utilizatori.xml")));

            } catch (TransformerException te) {
                System.out.println(te.getMessage());
            } catch (IOException ioe) {
                System.out.println(ioe.getMessage());
            }
        } catch (ParserConfigurationException pce) {
            System.out.println("UsersXML: Error trying to instantiate DocumentBuilder " + pce);
        }
    }

    public String[] toStringList(){
        String[] s = new String[utilizatori.size()];

        for (int i = 0; i < utilizatori.size(); i++) {
            s[i] = utilizatori.get(i).toString();
        }
        return s;
    }
}