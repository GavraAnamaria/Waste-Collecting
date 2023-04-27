package Model;
import lombok.AllArgsConstructor;
import lombok.Setter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Setter
@AllArgsConstructor
public class PersistentaUtilizatori {
    List<Utilizator> utilizatori ;

    public PersistentaUtilizatori(){
        utilizatori = new ArrayList<>();
     readData();
     utilizatori.sort(Utilizator::compareTo);
    }

    public List<Utilizator> getListaUtilizatori (){
        return utilizatori;
    }

    public void readData() {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Connection connection = null;
        try {
            connection = ConectareBD.getConnection();
            statement = connection.prepareStatement("SELECT * FROM utilizator");
            resultSet = statement.executeQuery();
            while(resultSet.next()) {
                utilizatori.add(new Utilizator(resultSet.getObject("nume").toString(),resultSet.getObject("parola").toString(),Tip.valueOf(resultSet.getObject("tip").toString())));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConectareBD.close(resultSet);
            ConectareBD.close(statement);
            ConectareBD.close(connection);
        }
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
       this.utilizatori.add(u);
        utilizatori.sort(Utilizator::compareTo);
           return insert(u);
    }

    public Boolean stergereUtilizator(int poz) throws IndexOutOfBoundsException{
        return delete(utilizatori.remove(poz));
    }

    public boolean insert(Utilizator l) {
        PreparedStatement statement = null;
        Connection connection = null;
        try {
            connection = ConectareBD.getConnection();
            statement = connection.prepareStatement("INSERT INTO utilizator (nume , parola, tip) VALUES ( " + " '" + l.getNume() + "' , '"+ l.getParola() + "' , '"+ l.getTip()+"' )");
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

    public boolean delete(Utilizator l) {
        PreparedStatement statement = null;
        Connection connection = null;
        try {
            connection = ConectareBD.getConnection();
            statement = connection.prepareStatement("DELETE FROM utilizator WHERE nume = '" +l.getNume() +"' AND parola = '"+l.getParola()+ "'");
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


    public String[] toStringList(){
        String[] s = new String[utilizatori.size()];

        for (int i = 0; i < utilizatori.size(); i++) {
            s[i] = utilizatori.get(i).toString();
        }
        return s;
    }
}