package src.DataBaseLibrary;

import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class DatabaseLibrary {

    private final Connection connect = DBConnection.getInstance();
    final Statement st = connect.createStatement();
    private final PreparedStatement addUser = connect.prepareStatement("INSERT INTO userdata VALUES( ?, ?, ?, ?);");
    private final PreparedStatement removeUser = connect.prepareStatement("DELETE FROM userdata WHERE userid = ?;");
    private final PreparedStatement checkUser = connect.prepareStatement("SELECT * FROM userdata WHERE username = ? AND password = ?;");
    private final PreparedStatement alterUser = connect.prepareStatement("UPDATE userdata SET username = ?, password = ?, permission = ? WHERE userid = ?;");
    private final PreparedStatement addMaze = connect.prepareStatement("INSERT INTO mazedata VALUES(?, ?, ?, ?, ?, ?, ?)");
    private final PreparedStatement editDate = connect.prepareStatement("UPDATE mazedata SET lastedited = ? WHERE mazeid = ?;");
    private final Statement getData = connect.createStatement();
    private final LinkedList<Integer> userIdsAvailable = new LinkedList<>();
    private final LinkedList<Integer> mazeIdsAvailable = new LinkedList<>();
    private int userCount = 1;
    private int mazeCount = 0;

    public DatabaseLibrary() throws SQLException {
        //init database
        st.execute("CREATE DATABASE IF NOT EXISTS mazeco;");
        st.execute("USE mazeco;");
        st.execute("CREATE TABLE IF NOT EXISTS userdata (userid INT NOT NULL PRIMARY KEY, username VARCHAR(100), password VARCHAR(32), permission VARCHAR(9));");
        st.execute("CREATE TABLE IF NOT EXISTS mazedata (mazeid INT, mazename VARCHAR(100), creatorname VARCHAR(100), creatorid INT NOT NULL, date VARCHAR(20), difficulty VARCHAR(20), lastedited VARCHAR(20), FOREIGN KEY (creatorid) REFERENCES userdata(userid))");
        if (!userExists("root", "root").next()){
            st.execute("INSERT INTO userdata VALUES(0, 'root', 'root', 'Admin');");
        }
        connect.commit();
    }
    // User data functions

    /**
     * Add a user to the database
     *
     * @param user Hashmap string representation of user object
     * @return true of successfully added user to database
     */
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

    /**
     * Removes a user from database
     *
     * @param user Hashmap string representation of user object
     */
    public void removeUser(HashMap<String, String> user){
        try {
            removeUser.clearParameters();
            removeUser.setInt(1, Integer.parseInt(user.get("ID")));
            removeUser.execute();
            this.userCount--;
        }catch(SQLException e){ e.printStackTrace();}
    }

    /**
     * Accesses and checks permission of a user
     *
     * @param username username of user
     * @param password password of user
     * @return null
     */
    public String getPermission(String username, String password){
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

    /**
     * Checks if a user exists in the database
     *
     * @param username username of user
     * @param password password of user
     * @return null
     */
    ResultSet userExists(String username, String password) {
        try{
            checkUser.clearParameters();
            checkUser.setString(1, username);
            checkUser.setString(2, password);
            return checkUser.executeQuery();
        }catch(SQLException e){ e.printStackTrace();}
        return null;
    }

    /**
     * Alter a user's information
     *
     * @param user Hashmap string representation of current version of user object
     * @param newUser Hashmap string representation of new version of user object
     */
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

    public DefaultTableModel getUserTableModel(){
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

    /**
     * Add maze to database
     *
     * @param maze string representation of walls of each cell of maze
     * @param mazeName name of maze
     * @param xSize x size of maze
     * @param ySize y size of maze
     */
    // Maze data functions
    public void addMaze(String[][] maze, String mazeName, int xSize, int ySize, HashMap<String, String> currentUser){
        try{
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            LocalDateTime date = LocalDateTime.now();
            ResultSet user = userExists(currentUser.get("Username"), currentUser.get("Password"));
            addMaze.setString(5, dtf.format(date));
            addMaze.setString(7, dtf.format(date));
            if (xSize == 20 && ySize == 20) {
                addMaze.setString(6, "Easy");
            }
            else if (xSize == 30 && ySize == 30){
                addMaze.setString(6, "Medium");
            }
            else {
                addMaze.setString(6, "Hard");
            }
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

    /**
     * Recreate maze from database as maze object
     *
     * @param mazeid ID of maze being pulled
     * @param Xsize x size of maze
     * @param Ysize y size of maze
     * @return
     */
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
            for (int y = 0; y < Ysize; y++){
                rs.next();
                for (int x = 0; x < Xsize; x++){
                    mazestr[y][x] = rs.getString(x+1);
                }
            }
            return mazestr;
        }catch(SQLException e){e.printStackTrace();}
        return null;
    }

    /**
     * Edit a selected maze
     *
     * @param newMaze string representation of walls of each cell of maze
     * @param mazeId ID of maze being edited
     */
    public void editMaze(String[][] newMaze, int mazeId){
        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            LocalDateTime date = LocalDateTime.now();
            st.execute("DROP TABLE m"+mazeId+";");

            PreparedStatement editedMaze = connect.prepareStatement("CREATE TABLE IF NOT EXISTS m"+ mazeId +"(c0 VARCHAR(5))");
            editedMaze.execute();
            for (int i = 1; i < newMaze[0].length; i++) st.execute("ALTER TABLE m"+ mazeId +" ADD COLUMN(c"+ i +" VARCHAR(5));");
            for (String[] strings : newMaze) {
                String currentLine = "";
                for (int j = 0; j < newMaze[0].length; j++) {
                    if (j == newMaze[0].length - 1) currentLine += "'" + strings[j] + "'";
                    else currentLine += "'" + strings[j] + "', ";
                }
                st.execute("INSERT INTO m" + mazeId + " VALUES(" + currentLine + ");");
            }

            editDate.setString(1, dtf.format(date));
            editDate.setInt(2, mazeId);
            editDate.execute();
        }catch(SQLException e){e.printStackTrace();}

    }

    public DefaultTableModel getMazeTableModel(){
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
        tm.addColumn("last edited");
        tm.setRowCount(0);
        try {
            rs = getData.executeQuery("SELECT * FROM mazedata;");
            while(rs.next()){
                Object[] o = {rs.getInt("mazeid"), rs.getString("mazename"), rs.getString("creatorname"), rs.getInt("creatorid"), rs.getString("date"), rs.getString("difficulty"), rs.getString("lastedited")};
                tm.addRow(o);
            }
        }
        catch (SQLException e) {e.printStackTrace();}
        return tm;
    }
}

