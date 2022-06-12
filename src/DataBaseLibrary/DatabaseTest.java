package src.DataBaseLibrary;


import org.junit.jupiter.api.*;
import src.MazeGUI.MainGUI;

import javax.swing.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DatabaseTest {
    static Statement st;
    static HashMap<String, String> user = new HashMap<>();
    static DatabaseLibrary database;
    static {
        try {
            database = new DatabaseLibrary();
            st = database.st;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Test
    @Order(1)
    void testAddUser(){
        try{
            user.put("Username", "Daniel");
            user.put("Password", "123");
            user.put("Permission", "Publisher");
            database.addUser(user);
            ResultSet rs = st.executeQuery("SELECT * FROM userdata WHERE username = 'Daniel' AND password = '123' AND permission = 'Publisher';");
            assertTrue(rs.next(), "Did not find user.");
            assertEquals(user.get("Username"),  rs.getString("username"), "Incorrect Username.");
            assertEquals(user.get("Password"),  rs.getString("password"), "Incorrect Password.");
            assertEquals(user.get("Permission"),  rs.getString("permission"), "Incorrect Permission");
        } catch (SQLException e) {e.printStackTrace();
        }
    }
    @Test
    @Order(2)
    void testRemoveUser(){
        user.put("ID", "1");
        user.put("Username", "Daniel");
        user.put("Password", "123");
        user.put("Permission", "Publisher");
        database.removeUser(user);
        try {
            ResultSet rs = st.executeQuery("SELECT * FROM userdata WHERE userid = 1 AND username = 'Daniel' AND password = '123' AND permission = 'Publisher';");
            assertFalse(rs.next(), "User still exists.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Test
    @Order(2)
    void testGetPermission(){
        user.put("ID", "1");
        user.put("Username", "Daniel");
        user.put("Password", "123");
        user.put("Permission", "Publisher");
        database.addUser(user);
        assertEquals("Publisher", database.getPermission(user.get("Username"), user.get("Password")));
    }
    @Test
    @Order(3)
    void testUserExists(){
        user.put("ID", "1");
        user.put("Username", "Daniel");
        user.put("Password", "123");
        user.put("Permission", "Publisher");
        database.addUser(user);
        try {
            assertTrue(database.userExists(user.get("Username"), user.get("Password")).next());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Test
    @Order(4)
    void testAlterUser(){
        user.put("ID", "1");
        user.put("Username", "Daniel");
        user.put("Password", "123");
        user.put("Permission", "Publisher");
        HashMap<String,String> newUser = user;
        newUser.put("Password","321");
        database.addUser(user);
        database.alterUser(user, newUser);
        try {
            ResultSet rs = st.executeQuery("SELECT * FROM userdata WHERE userid = 1 AND username = 'Daniel' AND password = '321' AND permission = 'Publisher';");
            assertTrue(rs.next(), "Password unchanged.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Test
    @Order(5)
    void testAddMaze(){
        user.put("ID", "1");
        user.put("Username", "Daniel");
        user.put("Password", "123");
        user.put("Permission", "Publisher");
        database.addUser(user);
        String[][] maze = new String[20][20];
        for (int y = 0; y < maze.length; y++){
            for (int x = 0; x < maze[0].length; x++){
                maze[y][x] = "0202";
            }
        }
        database.addMaze(maze, "TestMaze", 20,20, user);
        try {
            ResultSet rs = st.executeQuery("SELECT * FROM mazedata WHERE mazeid = 0;");
            assertTrue(rs.next(), "Maze not found.");
            assertEquals("TestMaze", rs.getString("mazename"), "Maze not added correctly.");
            rs = st.executeQuery("SELECT * FROM m0;");
            assertTrue(rs.next(), "Maze cells not found.");
            assertEquals("0202", rs.getString("c0"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Test
    @Order(6)
    void testGetMazeCells(){
        user.put("ID", "1");
        user.put("Username", "Daniel");
        user.put("Password", "123");
        user.put("Permission", "Publisher");
        database.addUser(user);
        String[][] maze = new String[20][20];
        for (int y = 0; y < maze.length; y++){
            for (int x = 0; x < maze[0].length; x++){
                maze[y][x] = "0202";
            }
        }
        database.addMaze(maze, "TestMaze", 20,20, user);
        String[][] testmaze = database.getMazeCells(0,20,20);
        for (int j = 0; j < testmaze.length; j++) {
            for (int i = 0; i < testmaze[0].length; i++) {
                assertEquals(testmaze[j][i], maze[j][i], "Cell not added properly");
            }
        }
    }
    @Test
    @Order(7)
    void testEditMaze(){
        user.put("ID", "1");
        user.put("Username", "Daniel");
        user.put("Password", "123");
        user.put("Permission", "Publisher");
        database.addUser(user);
        String[][] maze = new String[20][20];
        for (int y = 0; y < maze.length; y++){
            for (int x = 0; x < maze[0].length; x++){
                maze[y][x] = "0202";
            }
        }
        database.addMaze(maze, "TestMaze", 20,20, user);
        String [][] newmaze = maze.clone();
        newmaze[0][0] = "0000";
        database.editMaze(newmaze, 0);
        try {
            ResultSet rs = st.executeQuery("SELECT * FROM m0;");
            assertTrue(rs.next(), "Maze cells not found.");
            assertEquals("0000", rs.getString("c0"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
