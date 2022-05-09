package MazeGUI;

import java.nio.file.attribute.UserPrincipal;
import java.util.ArrayList;
import java.util.Arrays;
class DatabaseLibrary {
    private final ArrayList<String[]> userData;
    private final ArrayList<String[]> mazeData;
    private final String[] userColumnNames = {"ID", "Name", "Password", "Permissions"};
    private final String[] mazeColumnNames = {"Maze ID", "Designer Name", "Date Created", "Difficulty"};
    DatabaseLibrary(){
        //init database and save data
        userData = new ArrayList<>();
        mazeData = new ArrayList<>();
        String[][] TestUserData = {{"1","root", "123", "Admin"},{"2","John","321","Creator"}, {"3", "Daniel","123","Publisher"}};
        String[][] TestMazeData = {{"1","John", "29/10/2021", "Hard"},{"2","John", "28/10/2021", "Easy"},{"3","John","27/10/2021", "Medium"}};
        this.userData.addAll(Arrays.asList(TestUserData));
        this.mazeData.addAll(Arrays.asList(TestMazeData));
    }

    void setUserData(String new_value, int user, int item){
        String[] chosen_user = this.userData.get(user);
        chosen_user[item] = new_value;
        this.userData.set(user,chosen_user);
    }

    void addUser(String[] user){
        user[0] = String.valueOf(this.userData.size()+1);
        String[] temp = {user[0],user[1],user[2],user[3]};
        this.userData.add(temp);
    }

    boolean checkUser(String username, String password, String Permission){
        for(int i = 0; i < this.userData.size(); ++i)
            if (this.userData.get(i)[1].equals(username) && this.userData.get(i)[2].equals(password) && this.userData.get(i)[3].equals(Permission)) {
                return true;
            }
        return false;
    }


    String[] getUserColumnNames(){
        return this.userColumnNames;
    }

    ArrayList<String[]> getUserData(){
        return this.userData;
    }

    ArrayList<String[]> getMazeData() {
        return this.mazeData;
    }

    String[] getMazeColumnNames() {
        return this.mazeColumnNames;
    }

    String[][] getUserDataAsArray(){
        String[][] dataArray = new String[this.userData.size()][4];
        for (int i = 0; this.userData.size() > i; i++){
            dataArray[i] = this.userData.get(i);
        }
        return dataArray;
    }
    String[][] getMazeDataAsArray(){
        String[][] dataArray = new String[this.mazeData.size()][4];
        for (int i = 0; this.mazeData.size() > i; i++){
            dataArray[i] = this.mazeData.get(i);
        }
        return dataArray;
    }


}
