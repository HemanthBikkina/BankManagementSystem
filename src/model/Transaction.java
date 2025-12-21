
package model;
import java.time.LocalDateTime;

public class Transaction {
    private final String type;
    private final LocalDateTime date;
    private final double afterBalance;
    private final double amount;

    // Constructor for NEW transactions (Java side)
    public Transaction(String type, double afterBalance, double amount) {
        this.type = type;
        this.afterBalance = afterBalance;
        this.amount = amount;
        this.date = LocalDateTime.now();
    }

    // Constructor for transactions coming FROM DATABASE
    public Transaction(String type, double afterBalance, double amount, LocalDateTime date) {
        this.type = type;
        this.afterBalance = afterBalance;
        this.amount = amount;
        this.date = date;  // Use date from DB
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

    public double getAmount() {
        return amount;
    }
}
