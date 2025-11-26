import java.util.Scanner;
public class BankApp {
    public static void main(String[] args){
        BankService bankService = new BankService();
        System.out.println("App runnning Succesfully");
        Scanner sc=new Scanner(System.in);
        do {
            System.out.println("""
                    --------------------
                     Bank Application\n
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
                    String name = sc.next();
                    sc.nextLine();
                    if (name.matches("[a-zA-Z]+")) {
                    } else {
                        System.out.println("Invalid name! Only alphabets allowed. Try again.");
                        break;
                    }
                    int pin = sc.nextInt();
                    double deposit = sc.nextDouble();
                    long accountNumber = bankService.createAccount(name, pin, deposit);
                    System.out.println("Account Created Successfully");
                    System.out.println("AccountNumber:"+accountNumber);
                    break;
                case 2:
                    System.out.println("Login Selected Successfully");
                    System.out.println("Enter your account number & your pin :");
                    long accountnu = sc.nextLong();
                    int pinu = sc.nextInt();
                    Account acc = bankService.login(accountnu, pinu);
                    if (acc == null)
                        System.out.println("invalid Account or Pin");
                    else {
                        System.out.println("Logined to your Account  Successfully");
                        boolean LoggedIn=true;
                        while (LoggedIn) {
                            System.out.println("""
                                    -------------------
                                    Welcome to the Bank
                                    -------------------
                                    1.CheckBalance
                                    2.Deposit
                                    3.Withdrawal
                                    4.Check Transactions
                                    5.Logout
                                    -------------------
                                    """);
                            int choice1 = sc.nextInt();
                            switch (choice1) {
                                case 1:
                                    System.out.println("Balance of Account :" + acc.getBalance());
                                    break;
                                case 2:
                                    System.out.println(" Amount to Deposit");
                                    double depo = sc.nextDouble();
                                    boolean b = bankService.deposit(accountnu, depo);
                                    if (b) {
                                        System.out.println("Deposited Successfully");
                                    }
                                    break;
                                case 3:
                                    System.out.println("Withdrawal");
                                    double depou = sc.nextDouble();
                                    boolean b2 = bankService.withdraw(accountnu, depou);
                                    if (b2) {
                                        System.out.println("Withdrawal Successfully");
                                    }
                                    else {
                                        System.out.println("Insufficient Funds");
                                    }
                                    break;
                                case 4:
                                    System.out.println("Transaction details");
                                    if(acc.getTransactions().isEmpty())
                                    {
                                        System.out.println("No Transactions Found");
                                    }
                                    else{
                                        for(Transaction t:acc.getTransactions())
                                        {
                                            System.out.println("---------------");
                                            System.out.println("Type:"+t.getType());
                                            System.out.println("AfterBalance:"+t.getAfterBalance());
                                            System.out.println("Amount:"+t.getAmount());
                                            System.out.println("Date:"+t.getDate());
                                            System.out.println("-----------------");
                                        }
                                    }
                                    break;
                                case 5:
                                    System.out.println("Logout Successfully");
                                    LoggedIn=false;
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
                default:
                    System.out.println("Invalid choice: Try again");
            }
        }
        while(true);
    }
}
