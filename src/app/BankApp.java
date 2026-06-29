package app;
import java.util.List;
import java.util.Scanner;
import service.BankService;
import model.Account;
import model.Transaction;

public class BankApp {
    static void main(){
        BankService bankService = new BankService();
        System.out.println("App runnning Succesfully");
        Scanner sc=new Scanner(System.in);
        do {
            System.out.println("""
                    --------------------
                     Bank Application
                    --------------------
                     1.Create Account
                     2.Login
                     3.exit
                     -------------------
                     Enter your choice:""");
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("Create Account Selected Successfully");
                    System.out.println("Enter your Name,pin,deposit amount");
                    sc.nextLine();
                    String name = sc.nextLine();
                    if (name.trim().isEmpty()) {
                        System.out.println("Name cannot be empty!");
                        break;
                    }
                    if (!name.matches("[a-zA-Z ]+")) {
                        System.out.println("Invalid Name! Only alphabets allowed.");
                        break;
                    }
                    int pin = sc.nextInt();
                    double deposit = sc.nextDouble();
                    long accountNumber = bankService.createAccount(name, pin, deposit);
                    System.out.println("Account Created Successfully");
                    System.out.println("AccountNumber:" + accountNumber);
                    break;
                case 2:
                    System.out.println("Login Selected Successfully");
                    System.out.println("Enter your account number & your pin :");
                    long accountnu = sc.nextLong();
                    int pinu = sc.nextInt();
                    Account acc = bankService.login(accountnu, pinu);
                    if (acc == null){
                        System.out.println("invalid Account or Pin");
                        break;
                    }
                    else {
                        System.out.println("Logined to your Account  Successfully");
                        boolean LoggedIn = true;
                        while (LoggedIn) {
                            System.out.println("""
                                    -------------------
                                    Welcome to the Bank
                                    -------------------
                                    1.CheckBalance
                                    2.Deposit
                                    3.Withdrawal
                                    4.Check Transactions
                                    5.Account to Account Transaction
                                    6.Logout
                                    -------------------
                                    """);
                            int choice1 = sc.nextInt();
                            switch (choice1) {
                                case 1:
                                    System.out.println("Balance of Account :" + bankService.getBalance(acc.getAccountNumber()));
                                    break;
                                case 2:
                                    System.out.println(" Amount to Deposit");
                                    double depo = sc.nextDouble();
                                    boolean b = bankService.deposit(acc.getAccountNumber(), depo);
                                    if (b) {
                                        System.out.println("Deposited Successfully");
                                    }
                                    break;
                                case 3:
                                    System.out.println("Withdrawal");
                                    double depot = sc.nextDouble();
                                    boolean b2 = bankService.withdraw(acc.getAccountNumber(), depot);
                                    if (b2) {
                                        System.out.println("Withdrawal Successfully");
                                    } else {
                                        System.out.println("Insufficient Funds");
                                    }
                                    break;
                                case 4:
                                    System.out.println("Transaction details");
                                    List<Transaction> txList = bankService.getTransactions(acc.getAccountNumber());

                                    if (txList.isEmpty()) {
                                        System.out.println("No Transactions Found");
                                    } else {
                                        for (Transaction t : txList) {
                                            System.out.println("---------------");
                                            System.out.println("Type:" + t.getType());
                                            System.out.println("AfterBalance:" + t.getAfterBalance());
                                            System.out.println("Amount:" + t.getAmount());
                                            System.out.println("Date:" + t.getDate());
                                            System.out.println("-----------------");
                                        }
                                    }
                                    break;
                                case 5:
                                    System.out.println("Enter the sender Account to Transfer the money");
                                    long Snum=sc.nextInt();
                                    System.out.println("Enter the receiver Account to Transfer the money");
                                    long Rnum=sc.nextInt();
                                    System.out.println("Enter the amount to Transfer the money");
                                    double Anum=sc.nextDouble();
                                     System.out.println(bankService.moneyTransfer(Snum,Rnum,Anum));
                                    System.out.println("Transfer Successfully");
                                    break;


                                case 6:
                                    System.out.println("Logout Successfully");
                                    LoggedIn = false;
                                    break;
                                default:
                                    System.out.println("Invalid choice");
                            }
                        }
                    }

                    break;
                case 3:
                    System.out.println("Exit Selected Successfully");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice: Try again");
            }
        }

        while(true);
    }
}
