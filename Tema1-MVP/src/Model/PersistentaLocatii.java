package Model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import Oras.Strada;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import Oras.Strada;

public class PersistentaLocatii {
    private List<Locatie> locatii;

    public PersistentaLocatii(List<Locatie> lista){
        this.locatii = lista;
    }
    public PersistentaLocatii(){
        this.locatii = this.readXMLFile();
    }

    public List<Locatie> getLocatii(String utilizator){
        List<Locatie> l = new ArrayList<>();
        for(Locatie l1:locatii)
            if(l1.getUtilizator().equals(utilizator))
                l.add(l1);
        return l;
    }
    public List<Locatie> getAllLoc(){
        return this.locatii;
    }

    public boolean adaugareLocatie(Locatie l){
        for(Locatie loc:locatii)
            if(loc.equals(l))
                return false;
        locatii.add(l);
        return true;
    }

    public boolean stergereLocatie(int l){
        return((locatii.remove(l))!=null);
    }

    public List<Locatie> readXMLFile() {
        List<Locatie> u = new ArrayList<>();
        try {
            File fXmlFile = new File("deseuri.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("locatie");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    u.add(new Locatie(Strada.valueOf(eElement.getElementsByTagName("strada").item(0).getTextContent()),  Integer.parseInt(eElement.getElementsByTagName("nr").item(0).getTextContent()),eElement.getElementsByTagName("utilizator").item(0).getTextContent()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return u;
    }


    public void saveToXML() {
        Element e = null;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document dom = db.newDocument();
            Element rootEle = dom.createElement("locatie1");
            Iterator var6 = this.locatii.iterator();
            while(var6.hasNext()) {
                Locatie u = (Locatie)var6.next();
                Element elem = dom.createElement("locatie");
                e = dom.createElement("strada");
                e.appendChild(dom.createTextNode(u.getStrada().toString()));
                elem.appendChild(e);
                e = dom.createElement("nr");
                e.appendChild(dom.createTextNode(String.valueOf(u.getNr())));
                elem.appendChild(e);
                e = dom.createElement("utilizator");
                e.appendChild(dom.createTextNode(String.valueOf(u.getUtilizator())));
                elem.appendChild(e);
                rootEle.appendChild(elem);
            }
            dom.appendChild(rootEle);
            try {
                Transformer tr = TransformerFactory.newInstance().newTransformer();
                tr.setOutputProperty("indent", "yes");
                tr.setOutputProperty("method", "xml");
                tr.setOutputProperty("encoding", "UTF-8");
                tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
                tr.transform(new DOMSource(dom), new StreamResult(new FileOutputStream("deseuri.xml")));
            } catch (TransformerException var9) {
                System.out.println(var9.getMessage());
            } catch (IOException var10) {
                System.out.println(var10.getMessage());
            }
        } catch (ParserConfigurationException var11) {
            System.out.println("UsersXML: Error trying to instantiate DocumentBuilder " + var11);
        }
    }

    public String[] toStringList() {
        String[] s = new String[this.locatii.size()];
        for(int i = 0; i < this.locatii.size(); ++i) {
            s[i] = "[ " + i + " ]  " + ((Locatie)this.locatii.get(i)).toString();
        }
        return s;
    }
}
