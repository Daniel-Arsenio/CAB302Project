package src.MazeGUI;

import com.sun.tools.javac.Main;

import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

class DatabaseLibrary {

    private final Connection connect = DBConnection.getInstance();
    private final Statement st = connect.createStatement();
    private final PreparedStatement addUser = connect.prepareStatement("INSERT INTO userdata VALUES( ?, ?, ?, ?);");
    private final PreparedStatement removeUser = connect.prepareStatement("DELETE FROM userdata WHERE userid = ?;");
    private final PreparedStatement checkUser = connect.prepareStatement("SELECT * FROM userdata WHERE username = ? AND password = ?;");
    private final PreparedStatement alterUser = connect.prepareStatement("UPDATE userdata SET username = ?, password = ?, permission = ? WHERE userid = ?;");
    private final PreparedStatement addMaze = connect.prepareStatement("INSERT INTO mazedata VALUES(?, ?, ?, ?, ?, ?, ?)");
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
        st.execute("CREATE TABLE IF NOT EXISTS mazedata (mazeid INT, mazename VARCHAR(100), creatorname VARCHAR(100), creatorid INT NOT NULL, date VARCHAR(20), difficulty VARCHAR(20), size VARCHAR(20), FOREIGN KEY (creatorid) REFERENCES userdata(userid))");
        st.execute("INSERT INTO userdata VALUES(0, 'root', 'root', 'Admin');");
        connect.commit();
    }
    // User data functions

    public boolean addUser(HashMap<String, String> user){
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

    public void removeUser(HashMap<String, String> user){
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

    public void alterUser(HashMap<String, String> user, HashMap<String, String> newUser){
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
    public void addMaze(String[][] maze, String mazeName, int xSize, int ySize){
        try{
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            LocalDateTime date = LocalDateTime.now();
            ResultSet user = userExists(MainGUI.currentUser.get("Username"), MainGUI.currentUser.get("Password"));
            addMaze.setString(5, dtf.format(date));
            addMaze.setString(6, "Not Implemented");
            addMaze.setString(7,xSize + "x" + ySize);
            int id;
            user.next();
            addMaze.setInt(4, user.getInt("userid"));
            addMaze.setString(3, user.getString("username"));
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
            PreparedStatement newMaze = connect.prepareStatement("CREATE TABLE IF NOT EXISTS m"+ id +"(c0 VARCHAR(5))");
            newMaze.execute();
            for (int i = 1; i < maze[0].length; i++) st.execute("ALTER TABLE m"+ id +" ADD COLUMN(c"+ i +" VARCHAR(5));");
            for (String[] strings : maze) {
                String currentLine = "";
                for (int j = 0; j < maze[0].length; j++) {
                    if (j == maze[0].length - 1) currentLine += "'" + strings[j] + "'";
                    else currentLine += "'" + strings[j] + "', ";
                }
                st.execute("INSERT INTO m" + id + " VALUES(" + currentLine + ");");
            }
        } catch(SQLException e){ e.printStackTrace();}
    }

    public String[][] getMazeCells(int mazeid, int Xsize, int Ysize){
        try {
            int EdgeSize = 0;
            if(Xsize>=40 && Ysize>=40){
                EdgeSize = 15;
            }

            if(Xsize>=60 && Ysize>=60){
                EdgeSize = 12;
            }

            if(Xsize>=80 && Ysize>=80){
                EdgeSize = 7;
            }
            ResultSet rs = st.executeQuery("SELECT * FROM m" + mazeid + ";");
            String[][] mazestr = new String[Ysize][Xsize];
            for (int i = 0; i < Ysize; i++){
                rs.next();
                for (int j = 0; j < Xsize; j++){
                    mazestr[i][j] = rs.getString(j+1);
                }
            }
            return mazestr;
        }catch(SQLException e){e.printStackTrace();}
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
        tm.addColumn("mazeid");
        tm.addColumn("mazename");
        tm.addColumn("creatorname");
        tm.addColumn("creatorid");
        tm.addColumn("date");
        tm.addColumn("difficulty");
        tm.addColumn("size");
        tm.setRowCount(0);
        try {
            rs = getData.executeQuery("SELECT * FROM mazedata;");
            while(rs.next()){
                Object[] o = {rs.getInt("mazeid"), rs.getString("mazename"), rs.getString("creatorname"), rs.getInt("creatorid"), rs.getString("date"), rs.getString("difficulty"),rs.getString("size")};
                tm.addRow(o);
            }
        }
        catch (SQLException e) {e.printStackTrace();}
        return tm;
    }
}

