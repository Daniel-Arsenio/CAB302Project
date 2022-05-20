package src.MazeGUI;

import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.*;

class DatabaseLibrary {

    private final Connection connect = DBConnection.getInstance();
    private final Statement st = connect.createStatement();
    private final PreparedStatement addUser = connect.prepareStatement("INSERT INTO userdata VALUES( ?, ?, ?, ?);");
    private final PreparedStatement removeUser = connect.prepareStatement("DELETE FROM userdata WHERE userid = ?;");
    private final PreparedStatement checkUser = connect.prepareStatement("SELECT * FROM userdata WHERE username = ? AND password = ?;");
    private final PreparedStatement alterUser = connect.prepareStatement("UPDATE userdata SET username = ?, password = ?, permission = ? WHERE userid = ?;");
    private final Statement getData = connect.createStatement();
    private final LinkedList<Integer> idsAvailable = new LinkedList<>();
    private int userCount = 1;

    DatabaseLibrary() throws SQLException {
        //init database
        st.execute("DROP DATABASE IF EXISTS mazeco;");
        st.execute("CREATE DATABASE IF NOT EXISTS mazeco;");
        st.execute("USE mazeco;");
        st.execute("CREATE TABLE IF NOT EXISTS userdata (userid INT, username VARCHAR(100), password VARCHAR(32), permission VARCHAR(9));");
        st.execute("CREATE TABLE IF NOT EXISTS mazedata (mazeid INT, creatorid INT);");// REFERENCES mazeco(userdata))
        st.execute("INSERT INTO userdata VALUES(0, 'root', 'root', 'Admin');");
        connect.commit();
    }
    // User data functions

    void addUser(HashMap<String, String> user){
        try{
            ResultSet rs = st.executeQuery("Select * FROM userdata;");
            int oldid = 0;
            while (rs.next()){
                if (rs.getInt("userid") != oldid + 1){
                    for (int i = oldid + 1; i < rs.getInt("userid"); i++){
                        idsAvailable.add(i);
                    }
                }
                oldid = rs.getInt("userid");
            }
            if (userExists(user.get("Username"), user.get("Password")).next()) return;
            addUser.clearParameters();
            if (idsAvailable.isEmpty()){addUser.setInt(1, userCount);}
            else{
                addUser.setInt(1, idsAvailable.pop());
            }
            addUser.setString(2, user.get("Username"));
            addUser.setString(3, user.get("Password"));
            addUser.setString(4, user.get("Permission"));
            addUser.execute();
            this.userCount++;
        } catch(SQLException e){ e.printStackTrace();}
    }

    void removeUser(HashMap<String, String> user){
        try {
            removeUser.clearParameters();
            removeUser.setInt(1, Integer.parseInt(user.get("ID")));
            removeUser.execute();
            System.out.println("deleted " + Integer.parseInt(user.get("ID")));
        }catch(SQLException e){ e.printStackTrace();}
    }

    String getPermission(String username, String password){
        try{
            checkUser.clearParameters();
            checkUser.setString(1, username);
            checkUser.setString(2, password);
            ResultSet rs = checkUser.executeQuery();
            rs.next();
            if (rs.getString(4) == null) return null;
            else return rs.getString(4);
        }catch(SQLException e){ e.printStackTrace();}
        return null;
    }

    ResultSet userExists(String username, String password) {
        try{
            checkUser.clearParameters();
            checkUser.setString(1, username);
            checkUser.setString(2, password);
            return checkUser.executeQuery();
        }catch(SQLException e){ e.printStackTrace();}
        return null;
    }

    void alterUser(HashMap<String, String> user, HashMap<String, String> newUser){
        try{
            alterUser.clearParameters();
            alterUser.setString(1, newUser.get("Username"));
            alterUser.setString(2, newUser.get("Password"));
            alterUser.setString(3, newUser.get("Permission"));
            alterUser.setInt(4, Integer.parseInt(user.get("ID")));
            alterUser.execute();
        }catch(SQLException e){e.printStackTrace();}
    }

    DefaultTableModel getUserTableModel(){
        ResultSet rs;
        DefaultTableModel tm = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        };
        tm.addColumn("userID");
        tm.addColumn("username");
        tm.addColumn("password");
        tm.addColumn("permission");
        tm.setRowCount(0);
        try {
            rs = getData.executeQuery("SELECT * FROM userdata;");
            while(rs.next()){
                Object[] o = {rs.getInt("userid"), rs.getString("username"), rs.getString("password"), rs.getString("permission")};
                tm.addRow(o);
            }
        }
        catch (SQLException e) {e.printStackTrace();}
        return tm;
    }

    // Maze data functions
    void addMaze(){

    }

    void removeMaze(){

    }

    Byte[][] getMaze(int mazeid){
        return null;
    }

    DefaultTableModel getMazeTableModel(){
        ResultSet rs;
        DefaultTableModel tm = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        };
        tm.setRowCount(0);
        try {
            rs = getData.executeQuery("SELECT * FROM mazedata;");
            while(rs.next()){
                Object[] o = {rs.getInt("mazeid"), rs.getInt("creatorid")};
                tm.addRow(o);
            }
        }
        catch (SQLException e) {e.printStackTrace();}
        return tm;
    }
}

