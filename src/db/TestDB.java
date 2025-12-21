package db;
public class TestDB {
    public static void main(String[] args) {
        try{
            DBConnection.getConnection();
            System.out.println("Driver Connected Succesfully");
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

}
