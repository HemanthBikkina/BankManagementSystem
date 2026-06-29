package service;

import db.DBConnection;
import model.Account;
import model.Transaction;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BankService {
    /* private HashMap<Long,Account> accounts;
     public BankService(){
         accounts=new HashMap<>();
     }
     public BankService(HashMap<Long,Account> accounts){
         this.accounts=accounts;
     }
     public HashMap<Long,Account> getAccounts(){
         return accounts;
     }
 */
    public Long createAccount(String name, int pin, double balance) {
        long accountNumber = System.currentTimeMillis();
        /*Account account = new Account(accountNumber,name,initialDeposit,pin);
        accounts.put(accountNumber,account);
        return accountNumber; */
        try {
            Connection con = DBConnection.getConnection();
            String sql = "Insert into account(accountNumber,name,balance,pin) values (?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, accountNumber);
            ps.setString(2, name);
            ps.setDouble(3, balance);
            ps.setInt(4, pin);
            ps.executeUpdate();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return accountNumber;
    }

    public Account login(long accountNumber, int pin) {
        /*Account acc = accounts.get(accountNumber);
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

    */
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * From account where accountNumber=? and pin=?";
            ResultSet rs;
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setLong(1, accountNumber);
                ps.setInt(2, pin);
                rs = ps.executeQuery();
                if (rs.next()) {
                    return new Account(
                            rs.getLong("accountNumber"),
                            rs.getString("name"),
                            rs.getDouble("balance"),
                            rs.getInt("pin")
                    );
                }
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean deposit(long accountNumber, double amount) {
/*
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
*/
        if (amount <= 0) {
            return false;
        } else {
            Connection con = null;
            ResultSet rs = null;
            PreparedStatement ps = null;
            PreparedStatement ps2 = null;

            try {
                con = DBConnection.getConnection();
                String sql = "SELECT balance from account where accountNumber=?";
                ps2 = con.prepareStatement(sql);
                ps2.setLong(1, accountNumber);
                rs = ps2.executeQuery();
                if (rs.next()) {
                    double balance = rs.getDouble("balance");
                    String sq = "UPDATE account SET balance=balance+? WHERE accountNumber=?";
                    ps = con.prepareStatement(sq);
                    ps.setDouble(1, amount);
                    ps.setLong(2, accountNumber);
                    ps.executeUpdate();
                    double newBalance = balance + amount;
                    recordTransaction(con,accountNumber, "Deposit", amount, newBalance);
                    return true;
                } else {
                    return false;
                }
               /* finally {
                    // CLOSE RESOURCES SAFELY
                    try { if (rs != null) rs.close(); } catch (Exception e) {}
                    try { if (ps2 != null) ps2.close(); } catch (Exception e) {}
                    try { if (ps != null) ps.close(); } catch (Exception e) {}
                    try { if (con != null) con.close(); } catch (Exception e) {}
                }*/

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean withdraw(long accountNumber, double amount) {
        /*Account acc=accounts.get(accountNumber);
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
    }*/
        if (amount <= 0)
            return false;
        else {
            try {
                Connection con = DBConnection.getConnection();
                String sql = "SELECT balance from account where accountNumber=?";
                PreparedStatement ps1 = con.prepareStatement(sql);
                ps1.setLong(1, accountNumber);
                ResultSet rs = ps1.executeQuery();
                if (rs.next()) {
                    double balance = rs.getDouble("balance");
                    if (balance >= amount) {
                        String sq = "UPDATE account SET balance=balance-? WHERE accountNumber=?";
                        PreparedStatement ps = con.prepareStatement(sq);
                        ps.setDouble(1, amount);
                        ps.setLong(2, accountNumber);
                        ps.executeUpdate();
                        double newBalance = balance - amount;
                        recordTransaction(con,accountNumber, "Withdraw", amount, newBalance);
                        ps.close();
                        return true;
                    } else {
                        return false;
                    }
                }
                con.close();
                ps1.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
        return false;
    }

    private void recordTransaction(Connection con,
                                   long accountNumber,
                                   String type,
                                   double amount,
                                   double newBalance) throws SQLException {

        String sql = "INSERT INTO transaction(accountNumber,type,amount,afterBalance,date) "
                + "VALUES(?,?,?,?,NOW())";

        PreparedStatement ps = con.prepareStatement(sql);

        ps.setLong(1, accountNumber);
        ps.setString(2, type);
        ps.setDouble(3, amount);
        ps.setDouble(4, newBalance);

        ps.executeUpdate();

        ps.close();
    }

    public double getBalance(long accountNumber) {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT balance from account where accountNumber=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, accountNumber);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getDouble("balance");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return -1;
    }

    public List<Transaction> getTransactions(long accountNumber) {
        List<Transaction> list = new ArrayList<>();
        Connection con = null;
        try {
            con = DBConnection.getConnection();
            String sql = "SELECT  type, amount, afterBalance, date FROM transaction WHERE accountNumber = ? ORDER BY date DESC";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, accountNumber);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String type = rs.getString("type");
                double amount = rs.getDouble("amount");
                double afterBalance = rs.getDouble("afterBalance");
                LocalDateTime date = rs.getTimestamp("date").toLocalDateTime();

                Transaction t = new Transaction(type, afterBalance, amount, date);
                list.add(t);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public boolean moneyTransfer(long senderAcc, long receiverAcc, double amount) {

        if (amount <= 0) {
            return false;
        }

        Connection con = null;

        try {

            con = DBConnection.getConnection();
            con.setAutoCommit(false);

            // Check sender balance
            String senderSql = "SELECT balance FROM account WHERE accountNumber=?";
            PreparedStatement ps1 = con.prepareStatement(senderSql);
            ps1.setLong(1, senderAcc);
            ResultSet rs1 = ps1.executeQuery();

            if (!rs1.next()) {
                return false;
            }

            double senderBalance = rs1.getDouble("balance");

            if (senderBalance < amount) {
                return false;
            }

            // Check receiver exists
            String receiverSql = "SELECT balance FROM account WHERE accountNumber=?";
            PreparedStatement ps2 = con.prepareStatement(receiverSql);
            ps2.setLong(1, receiverAcc);
            ResultSet rs2 = ps2.executeQuery();

            if (!rs2.next()) {
                return false;
            }

            double receiverBalance = rs2.getDouble("balance");

            // Deduct sender
            String deductSql =
                    "UPDATE account SET balance = balance - ? WHERE accountNumber=?";

            PreparedStatement ps3 = con.prepareStatement(deductSql);
            ps3.setDouble(1, amount);
            ps3.setLong(2, senderAcc);
            ps3.executeUpdate();

            // Credit receiver
            String creditSql =
                    "UPDATE account SET balance = balance + ? WHERE accountNumber=?";

            PreparedStatement ps4 = con.prepareStatement(creditSql);
            ps4.setDouble(1, amount);
            ps4.setLong(2, receiverAcc);
            ps4.executeUpdate();

            // New balances
            double senderNewBalance = senderBalance - amount;
            double receiverNewBalance = receiverBalance + amount;

            // Record both transactions
            recordTransaction(con, senderAcc,
                    "TRANSFER_OUT",
                    amount,
                    senderNewBalance);

            recordTransaction(con, receiverAcc,
                    "TRANSFER_IN",
                    amount,
                    receiverNewBalance);

            con.commit();

            return true;

        } catch (Exception e) {

            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            e.printStackTrace();
            return false;

        } finally {

            try {
                if (con != null) {
                    con.setAutoCommit(true);
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }
    }
