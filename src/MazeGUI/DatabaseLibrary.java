package src.MazeGUI;

import com.sun.tools.javac.Main;

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
    private final PreparedStatement addMaze = connect.prepareStatement("INSERT INTO mazedata VALUES(?, ?, ?)");
    private final Statement getData = connect.createStatement();
    private final LinkedList<Integer> userIdsAvailable = new LinkedList<>();
    private final LinkedList<Integer> mazeIdsAvailable = new LinkedList<>();
    private int userCount = 1;
    private int mazeCount = 0;

    DatabaseLibrary() throws SQLException {
        //init database
        st.execute("DROP DATABASE IF EXISTS mazeco;");
        st.execute("CREATE DATABASE IF NOT EXISTS mazeco;");
        st.execute("USE mazeco;");
        st.execute("CREATE TABLE IF NOT EXISTS userdata (userid INT NOT NULL PRIMARY KEY, username VARCHAR(100), password VARCHAR(32), permission VARCHAR(9));");
        st.execute("CREATE TABLE IF NOT EXISTS mazedata (mazeid INT, mazename VARCHAR(100), creatorid INT NOT NULL, FOREIGN KEY (creatorid) REFERENCES userdata(userid))");
        st.execute("INSERT INTO userdata VALUES(0, 'root', 'root', 'Admin');");
        connect.commit();
    }
    // User data functions

    boolean addUser(HashMap<String, String> user){
        try{
            ResultSet rs = st.executeQuery("Select * FROM userdata;");
            int oldid = 0;
            while (rs.next()){
                if (rs.getInt("userid") != oldid + 1){
                    for (int i = oldid + 1; i < rs.getInt("userid"); i++){
                        if(!userIdsAvailable.contains(i)) userIdsAvailable.add(i);
                    }
                }
                oldid = rs.getInt("userid");
            }
            if (userExists(user.get("Username"), user.get("Password")).next()) return false;
            addUser.clearParameters();
            if (userIdsAvailable.isEmpty()){addUser.setInt(1, userCount);}
            else{
                addUser.setInt(1, userIdsAvailable.pop());
            }
            addUser.setString(2, user.get("Username"));
            addUser.setString(3, user.get("Password"));
            addUser.setString(4, user.get("Permission"));
            addUser.execute();
            this.userCount++;
        } catch(SQLException e){ e.printStackTrace();}
        return true;
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
            if (!rs.next()) return null;
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
    boolean addMaze(String[][] maze, String mazeName){
        try{
            ResultSet user = userExists(MainGUI.currentUser.get("Username"), MainGUI.currentUser.get("Password"));
            System.out.println(user.next());
            int id;
            addMaze.setInt(3, user.getInt("userid"));
            addMaze.setString(2, mazeName);
            ResultSet rs = st.executeQuery("Select * FROM mazedata;");
            int oldid = 0;
            while (rs.next()){
                if (rs.getInt("mazeid") != oldid + 1){
                    for (int i = oldid + 1; i < rs.getInt("mazeid"); i++){
                        if(!mazeIdsAvailable.contains(i)) mazeIdsAvailable.add(i);
                    }
                }
                oldid = rs.getInt("mazeid");
            }
            if (mazeIdsAvailable.isEmpty()){id = mazeCount;}
            else{
                id = mazeIdsAvailable.pop();
            }
            addMaze.setInt(1, id);
            addMaze.execute();
            this.mazeCount++;
            PreparedStatement newMaze = connect.prepareStatement("CREATE TABLE IF NOT EXISTS t"+ id +"(c0 VARCHAR(5))");
            newMaze.execute();
            for (int i = 1; i < maze[0].length; i++) st.execute("ALTER TABLE t"+ id +" ADD COLUMN(c"+ i +" VARCHAR(5));");
            for (String[] strings : maze) {
                String currentLine = "";
                for (int j = 0; j < maze[0].length; j++) {
                    if (j == maze[0].length-1) currentLine += "'"+strings[j]+"'";
                    else currentLine += "'"+strings[j]+"', ";
                }
                st.execute("INSERT INTO t"+ id +" VALUES("+currentLine+");");
            }
        } catch(SQLException e){ e.printStackTrace();}
        return true;
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

