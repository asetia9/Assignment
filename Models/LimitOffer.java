package Models;

import java.time.LocalDateTime;

public class LimitOffer {
    private long limitOfferId;
    private long accountId;
    private LimitType limitType;
    private double newLimit;
    private LocalDateTime offerActivationTime;
    private LocalDateTime offerExpiryTime;
    private LimitOfferStatus status;
    public LimitOffer(){
   
    }
    
    public long getLimitOfferId(){
        return this.limitOfferId;
    }

    public long getAccountId(){
        return this.accountId;
    }

    public LimitType getLimitType(){
        return this.limitType;
    }

    public double getNewLimit(){
        return this.newLimit;
    }

    public LocalDateTime getOfferActivationTime(){
        return this.offerActivationTime;
    }

    public LocalDateTime getOfferExpiryTime(){
        return this.offerExpiryTime;
    }

    public LimitOfferStatus getStatus(){
        return this.status;
    }

    public void setLimitOfferId(long limitOfferId){
        this.limitOfferId = limitOfferId;
    }

    public void setAccountId(long accountId){
        this.accountId = accountId;
    }

    public void setLimitType(LimitType limitType){
        this.limitType = limitType;
    }

    public void setNewLimit(double newLimit){
        this.newLimit = newLimit;
    }

    public void setOfferActivationTime(LocalDateTime offerActivationTime){
        this.offerActivationTime = offerActivationTime;
    }

    public void setOfferExpiryTime(LocalDateTime offerExpiryTime){
        this.offerExpiryTime = offerExpiryTime;
    }

    public void setStatus(LimitOfferStatus status){
        this.status = status;
    }
}