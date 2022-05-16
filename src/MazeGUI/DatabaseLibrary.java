package MazeGUI;

import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.HashMap;

class DatabaseLibrary {

    private final Connection connect = DBConnection.getInstance();
    private final Statement st = connect.createStatement();
    private final PreparedStatement addUser = connect.prepareStatement("INSERT INTO userdata VALUES( ?, ?, ?, ?);");
    private final PreparedStatement removeUser = connect.prepareStatement("DELETE FROM userdata WHERE username = ? AND password = ?;");
    private final PreparedStatement checkUser = connect.prepareStatement("SELECT * FROM userdata WHERE username = ? AND password = ?;");
    private final PreparedStatement alterUser = connect.prepareStatement("UPDATE userdata SET username = ?, password = ?, permission = ? WHERE username = ? AND password = ?;");
    private final Statement getData = connect.createStatement();
    private int userCount = 4;

    DatabaseLibrary() throws SQLException {
        final Statement statement = connect.createStatement();
        //init database
        statement.execute("DROP DATABASE IF EXISTS mazeco;");
        statement.execute("CREATE DATABASE IF NOT EXISTS mazeco;");
        statement.execute("USE mazeco;");
        statement.execute("CREATE TABLE IF NOT EXISTS userdata (userid INT, username VARCHAR(100), password VARCHAR(32), permission VARCHAR(9));");
        statement.execute("INSERT INTO userdata VALUES(0, 'root', 'root', 'Admin');");
        connect.commit();
    }

    void addUser(HashMap<String, String> user){
        try{
            if (userExists(user.get("Username"), user.get("Password")).next()) return;
            addUser.clearParameters();
            addUser.setInt(1, userCount);
            addUser.setString(2, user.get("Username"));
            addUser.setString(3, user.get("Password"));
            addUser.setString(4, user.get("Permission"));
            addUser.execute();
            this.userCount++;
        } catch(SQLException e){ e.printStackTrace();}
    }

    void removeUser(HashMap<String, String> user) throws SQLException{
        removeUser.clearParameters();
        removeUser.setString(1, user.get("Username"));
        removeUser.setString(2, user.get("Password"));
        st.getResultSet();
        removeUser.execute();
        //for (int i = user.; i < userCount; i++){initDatabase.execute("UPDATE userdata SET userid = "+  +" WHERE userid = ");}

    }

    String getPermission(String username, String password){
        try{
            checkUser.clearParameters();
            checkUser.setString(1, username);
            checkUser.setString(2, password);
            return checkUser.executeQuery().getString(4);
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
            alterUser.setString(4, user.get("Username"));
            alterUser.setString(5, user.get("Password"));
            alterUser.execute();
        }catch(SQLException e){e.printStackTrace();}
    }

    DefaultTableModel getTableModel(){
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
            rs = getData.executeQuery("SELECT * FROM userdata;");
            while(rs.next()){
                Object[] o = {rs.getInt("userid"), rs.getString("username"), rs.getString("password"), rs.getString("permission")};
                tm.addRow(o);
            }
        }
        catch (SQLException e) {e.printStackTrace();}
        return tm;

    }

}

