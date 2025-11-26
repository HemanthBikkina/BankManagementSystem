import java.util.HashMap;
public class BankService {
    private HashMap<Long,Account> accounts;
    public BankService(){
        accounts=new HashMap<>();
    }
    public BankService(HashMap<Long,Account> accounts){
        this.accounts=accounts;
    }
    public HashMap<Long,Account> getAccounts(){
        return accounts;
    }

    public Long createAccount(String name,int pin,double initialDeposit)
    {
        long accountNumber = System.currentTimeMillis();
        Account account = new Account(accountNumber,name,initialDeposit,pin);
        accounts.put(accountNumber,account);
        return accountNumber;
    }
    public Account login(long accountNumber,int pin)
    {
        Account acc = accounts.get(accountNumber);
        if(acc==null)
            return null;
        else {
            boolean b = acc.verifyPin(pin);
            if(b) {
                return acc;
            }
            else
                return null;
        }
    }
    public boolean deposit(long accountNumber,double amount)
    {
        Account acc=accounts.get(accountNumber);
        if(acc==null)
            return false;
        else
        {
            if(amount>0)
            {
                double newBalance=acc.getBalance()+amount;
                acc.setBalance(newBalance);
                recordTransaction(acc,"deposit",amount);
                return true;
            }
            else
                return false;
        }
    }
    public boolean withdraw(long accountNumber,double amount)
    {
        Account acc=accounts.get(accountNumber);
        if(acc==null)
            return false;
        else {
            if(amount<=0)
                return false;
            else {
                if (acc.getBalance() < amount)
                    return false;
                else {
                    double newBalance = acc.getBalance() - amount;
                    acc.setBalance(newBalance);
                    recordTransaction(acc, "withdraw", amount);
                    return true;
                }
            }
        }
    }
    private void recordTransaction(Account account,String type,double amount)
    {
        double money=account.getBalance();
        Transaction t=new Transaction(type,money,amount);
        account.getTransactions().add(t);

    }

}
