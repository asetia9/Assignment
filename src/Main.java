package src;

import java.time.LocalDateTime;
import Models.Account;
import Models.LimitOffer;
import Models.LimitOfferStatus;
import Models.LimitType;
import Services.LimitOfferService;
public class Main {
    public static void main(String[] args) {
        // Create an instance of LimitOfferService
        LimitOfferService limitOfferService = new LimitOfferService();

        // Create and save an account
        Account account = new Account();
        account.setAccountId(1);
        account.setCustomerId(1);
        account.setAccountLimit(1000.0);
        account.setPerTransactionLimit(500.0);
        account.setLastAccountLimit(900.0);
        account.setLastPerTransactionLimit(150.0);
        account.setAccountLimitUpdateTime(LocalDateTime.now());
        account.setPerTransactionLimitUpdateTime(LocalDateTime.now());
        limitOfferService.createAccount(account);

        // Retrieve the account by ID
        long accountId = 1;
        Account retrievedAccount = limitOfferService.getAccountById(accountId);
        if (retrievedAccount != null) {
            System.out.println("Account Details:");
            System.out.println("Account ID: " + retrievedAccount.getAccountId());
            System.out.println("Customer ID: " + retrievedAccount.getCustomerId());
            System.out.println("Account Limit: " + retrievedAccount.getAccountLimit());
            System.out.println("Per Transaction Limit: " + retrievedAccount.getPerTransactionLimit());
            // Add other account details if needed
        } else {
            System.out.println("Account not found.");
        }

        // Create a limit offer for the account
        LimitOffer limitOffer = new LimitOffer();
        limitOffer.setAccountId(1);
        limitOffer.setLimitType(LimitType.ACCOUNT_LIMIT);
        limitOffer.setNewLimit(1100.0);
        limitOffer.setOfferActivationTime(LocalDateTime.now());
        limitOffer.setOfferExpiryTime(LocalDateTime.now().plusDays(30));
        limitOfferService.createLimitOffer(limitOffer);

        // List active limit offers for the account on the current date
        LocalDateTime activeDate = LocalDateTime.now();
        System.out.println("\nActive Limit Offers for Account ID " + 1 + " on " + activeDate + ":");
        limitOfferService.listActiveLimitOffers(1, activeDate).forEach(Main::printLimitOffer);

        // Update the status of a limit offer
        long limitOfferIdToUpdate = 9; // Replace with the actual limit offer ID
        limitOfferService.updateLimitOfferStatus(limitOfferIdToUpdate, LimitOfferStatus.ACCEPTED);
    }

    private static void printLimitOffer(LimitOffer limitOffer) {
        System.out.println("Limit Offer ID: " + limitOffer.getLimitOfferId());
        System.out.println("Account ID: " + limitOffer.getAccountId());
        System.out.println("Limit Type: " + limitOffer.getLimitType());
        System.out.println("New Limit: " + limitOffer.getNewLimit());
        System.out.println("Activation Time: " + limitOffer.getOfferActivationTime());
        System.out.println("Expiry Time: " + limitOffer.getOfferExpiryTime());
        System.out.println("Status: " + limitOffer.getStatus());
        System.out.println();
    }
}
