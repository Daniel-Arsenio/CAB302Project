package Users;
//User Class

public class User implements iUser {
    //fields
    private String userID;
    private String name;
    private String permission;
    private String role;

    //constructor
    public User(String id, String userName, String permission, String userRole){
        this.userID = id;
        this.name = userName;
        this.permission = permission;
        this.role = userRole;
    }

}
