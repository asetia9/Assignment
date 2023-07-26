package Services;

import java.time.LocalDateTime;
import java.util.List;
import DAO.LimitOfferDAO;
import Models.Account;
import Models.LimitOffer;
import Models.LimitOfferStatus;
import Models.LimitType;

public class LimitOfferService {
    private LimitOfferDAO limitOfferDAO;

    public LimitOfferService() {
        this.limitOfferDAO = new LimitOfferDAO();
    }

    public void createAccount(Account account) {
        limitOfferDAO.createAccount(account);
    }

    public Account getAccountById(long accountId) {
        return limitOfferDAO.getAccountById(accountId);
    }

    // Method to create a limit offer in the database
    public void createLimitOffer(LimitOffer limitOffer) {
        // Check if the new limit is greater than the current limit
        Account account = getAccountById(limitOffer.getAccountId());
        if (account == null) {
            throw new IllegalArgumentException("Account not found.");
        }

        double currentLimit;
        if (limitOffer.getLimitType() == LimitType.ACCOUNT_LIMIT) {
            currentLimit = account.getAccountLimit();
        } else {
            currentLimit = account.getPerTransactionLimit();
        }

        if (limitOffer.getNewLimit() <= currentLimit) {
            throw new IllegalArgumentException("New limit must be greater than the current limit." + currentLimit);
        }

        // Set offer status to PENDING
        limitOffer.setStatus(LimitOfferStatus.PENDING);

        limitOfferDAO.createLimitOffer(limitOffer);
        
    }

    // Method to list active limit offers for a given account ID and active date
    public List<LimitOffer> listActiveLimitOffers(long accountId, LocalDateTime activeDate) {
        return limitOfferDAO.getActiveOffers(accountId, activeDate);
    }

    // Method to update the status of an active and pending limit offer in the database
    public void updateLimitOfferStatus(long limitOfferId, LimitOfferStatus status) {
        limitOfferDAO.updateLimitOfferStatus(limitOfferId, status);
    }
}

