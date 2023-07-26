package Models;
import java.time.LocalDateTime;

public class Account {
    private long accountId;
    private long customerId;
    private double accountLimit;
    private double perTransactionLimit;
    private double lastAccountLimit;
    private double lastPerTransactionLimit;
    private LocalDateTime accountLimitUpdateTime;
    private LocalDateTime perTransactionLimitUpdateTime;

    public Account(){
     
    }
    
    public long getCustomerId(){
        return this.customerId;
    }
    public double getAccountLimit(){
        return this.accountLimit;
    }
    public double getPerTransactionLimit(){
        return this.perTransactionLimit;
    }
    public double getLastAccountLimit(){
        return this.lastAccountLimit;
    }
    public double getLastPerTransactionLimit(){
        return this.lastPerTransactionLimit;
    }
    public LocalDateTime getAccountLimitUpdateTime(){
        return this.accountLimitUpdateTime;
    }
    public LocalDateTime getPerTransactionLimitUpdateTime(){
        return this.perTransactionLimitUpdateTime;
    }
    public long getAccountId(){
        return this.accountId;
    }
    public void setCustomerId(long customerId){
        this.customerId = customerId;
    }
    public void setAccountLimit(double accountLimit){
        this.accountLimit = accountLimit;
    }
    public void setPerTransactionLimit(double perTransactionLimit){
        this.perTransactionLimit = perTransactionLimit;
    }
    public void setLastAccountLimit(double lastAccountLimit){
        this.lastAccountLimit = lastAccountLimit;
    }
    public void setLastPerTransactionLimit(double lastPerTransactionLimit){
        this.lastPerTransactionLimit = lastPerTransactionLimit;
    }
    public void setAccountLimitUpdateTime(LocalDateTime accountLimitUpdateTime){
        this.accountLimitUpdateTime = accountLimitUpdateTime;
    }
    public void setPerTransactionLimitUpdateTime(LocalDateTime perTransactionLimitUpdateTime){
        this.perTransactionLimitUpdateTime = perTransactionLimitUpdateTime;
    }
    public void setAccountId(long accountId){
        this.accountId = accountId;
    }
    
}
