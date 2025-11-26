import java.time.LocalDateTime;
public class Transaction {
    private String type;
    private LocalDateTime date;
    private double afterBalance;
    private double amount;
    public Transaction(String type, double afterBalance, double amount) {
        this.type = type;
        this.afterBalance = afterBalance;
        this.amount = amount;
        date = LocalDateTime.now();
    }
    public String getType() {
        return type;
    }
    public LocalDateTime getDate() {
        return date;
    }
    public double getAfterBalance() {
        return afterBalance;
    }
    public double getAmount(){
        return amount;
    }

}
