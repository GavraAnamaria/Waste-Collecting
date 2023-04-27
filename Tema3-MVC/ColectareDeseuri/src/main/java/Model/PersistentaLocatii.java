package Model;
import Oras.Strada;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersistentaLocatii extends Observable {
    private final List<Locatie> locatii;

    public PersistentaLocatii(List<Locatie> lista){
        this.locatii = lista;
    }
    public PersistentaLocatii(){
        locatii=new ArrayList<>();
        this.readData();
        locatii.sort(Locatie::compareTo);
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
        locatii.sort(Locatie::compareTo);
        notifyUpdate();
        return insert(l);
    }

    public boolean stergereLocatie(int l){
        boolean r = delete(locatii.remove(l));
        notifyUpdate();
        return r;
    }



    public void readData() {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Connection connection = null;
        try {
            connection = ConectareBD.getConnection();
            statement = connection.prepareStatement("SELECT * FROM locatie");
            resultSet = statement.executeQuery();
            while(resultSet.next()) {
                locatii.add(new Locatie(Strada.valueOf(resultSet.getObject("Strada").toString()),(int)resultSet.getObject("nr"),resultSet.getObject("utilizator").toString()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConectareBD.close(resultSet);
            ConectareBD.close(statement);
            ConectareBD.close(connection);
        }
    }

    public boolean insert(Locatie l) {
        String s = "INSERT INTO locatie (strada, nr, utilizator) VALUES ( '" + l.getStrada()+"', "+ l.getNr() + ", '"+ l.getUtilizator()+"')";
        PreparedStatement statement = null;
        Connection connection = null;
        try {
            connection = ConectareBD.getConnection();
            statement = connection.prepareStatement(s);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            System.out.println(s);
            ConectareBD.close(statement);
            ConectareBD.close(connection);
        }
        return true;
    }

    public boolean delete(Locatie l) {
        String s = "DELETE FROM locatie WHERE strada = '" +l.getStrada() +"' AND nr = "+l.getNr()+" AND utilizator = '"+l.getUtilizator()+"'" ;
        System.out.println(s);
        PreparedStatement statement = null;
        Connection connection = null;
        try {
            connection = ConectareBD.getConnection();
            statement = connection.prepareStatement(s);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            ConectareBD.close(statement);
            ConectareBD.close(connection);
        }
        return true;
    }

    public String[] toStringList() {
        String[] s = new String[this.locatii.size()];
        for(int i = 0; i < this.locatii.size(); ++i) {
            s[i] = "[ " + i + " ]  " + (this.locatii.get(i)).toString();
        }
        return s;
    }

    public String[] toStringList1() {
        String[] s = new String[this.locatii.size()];
        for(int i = 0; i < this.locatii.size(); ++i) {
            s[i] = "[ " + i + " ]  " + (this.locatii.get(i)).toString1();
        }
        return s;
    }
}
