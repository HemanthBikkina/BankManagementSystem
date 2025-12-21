package model;
public class Account {
    private final long accountNumber;
    private String accountName;
    private double balance;
    private final int pin;
    //private  ArrayList<Transaction> transactions;
     public Account(long accountNumber, String accountName ,double balance ,int pin) {
        this.accountNumber=accountNumber;
        this.accountName=accountName;
        this.balance=balance;
        this.pin=pin;
      //  this.transactions=new ArrayList<>();
    }

    //getters
    public long getAccountNumber() {
         return accountNumber;
    }
    public String getAccountName(){
         return accountName;
    }
     public double getBalance()
    {
        return balance;
    }
    //setters
    public void setAccountName(String accountName) {
         this.accountName = accountName;
    }
    void setBalance(double balance) {
         this.balance = balance;
    }
    /*public boolean verifyPin(int pin){
         if(pin==this.pin) {
             return true;
         } else return false;
    }
    */
    /* public ArrayList<Transaction> getTransactions() {
         return transactions;
    }*/
    public static void main(String[] args)
    {

    }

    public int getPin() {
        return pin;
    }
}
