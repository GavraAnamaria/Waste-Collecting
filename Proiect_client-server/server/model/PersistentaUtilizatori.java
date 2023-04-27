package model;

import Enum.Tip;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
@Setter
@Getter
@AllArgsConstructor
public class PersistentaUtilizatori implements Serializable {
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
            connection = ConexiuneBD.getConnection();
            statement = connection.prepareStatement("SELECT * FROM utilizator");
            resultSet = statement.executeQuery();
            while(resultSet.next()) {
                utilizatori.add(new Utilizator(resultSet.getObject("nume").toString(),resultSet.getObject("parola").toString(), Tip.valueOf(resultSet.getObject("tip").toString()), resultSet.getObject("email").toString(), resultSet.getObject("numar").toString()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConexiuneBD.close(resultSet);
            ConexiuneBD.close(statement);
            ConexiuneBD.close(connection);
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
        PreparedStatement statement = null;
        Connection connection = null;
        try {
            connection = ConexiuneBD.getConnection();
            statement = connection.prepareStatement("INSERT INTO utilizator (nume , parola, tip, email, numar) VALUES ( " + " '" + u.getNume() + "' , '"+ u.getParola() + "' , '"+ u.getTip() + "' , '"+ u.getEmail() + "' , '"+ u.getNr()+"' )");
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            ConexiuneBD.close(statement);
            ConexiuneBD.close(connection);
        }
        return true;
    }

    public Utilizator stergereUtilizator(int poz) throws IndexOutOfBoundsException{
        Utilizator l=utilizatori.remove(poz);
        PreparedStatement statement = null;
        Connection connection = null;
        try {
            connection = ConexiuneBD.getConnection();
            statement = connection.prepareStatement("DELETE FROM utilizator WHERE nume = '" +l.getNume() +"' AND parola = '"+l.getParola()+ "'");
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            ConexiuneBD.close(statement);
            ConexiuneBD.close(connection);
        }
        return l;
    }


    public String[] toStringList(){
        String[] s = new String[utilizatori.size()];

        for (int i = 0; i < utilizatori.size(); i++) {
            s[i] = utilizatori.get(i).toString();
        }
        return s;
    }
}