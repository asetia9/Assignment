package DAO;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import Models.Account;
import Models.LimitOffer;
import Models.LimitOfferStatus;
import Models.LimitType;
public class LimitOfferDAO {
    // PostgreSQL connection details
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/limit_offer_db";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "password";


    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }


    // Method to create an account in the database
    public void createAccount(Account account) {
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(
                     "INSERT INTO account (account_id,customer_id, account_limit, per_transaction_limit, " +
                     "last_account_limit, last_per_transaction_limit, account_limit_update_time, " +
                     "per_transaction_limit_update_time) VALUES (?,?, ?, ?, ?, ?, ?, ?)")
        ) {
            pstmt.setLong(1, account.getAccountId());
            pstmt.setLong(2, account.getCustomerId());
            pstmt.setDouble(3, account.getAccountLimit());
            pstmt.setDouble(4, account.getPerTransactionLimit());
            pstmt.setDouble(5, account.getLastAccountLimit());
            pstmt.setDouble(6, account.getLastPerTransactionLimit());
            pstmt.setTimestamp(7, Timestamp.valueOf(account.getAccountLimitUpdateTime()));
            pstmt.setTimestamp(8, Timestamp.valueOf(account.getPerTransactionLimitUpdateTime()));

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to get an account by ID from the database
    public Account getAccountById(long accountId) {
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM account WHERE account_id = ?")
        ) {
            pstmt.setLong(1, accountId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                
                Account account = new Account();
                account.setAccountId(rs.getLong("account_id"));
                account.setCustomerId(rs.getLong("customer_id"));
                account.setAccountLimit(rs.getDouble("account_limit"));
                account.setPerTransactionLimit(rs.getDouble("per_transaction_limit"));
                account.setLastAccountLimit(rs.getDouble("last_account_limit"));
                account.setLastPerTransactionLimit(rs.getDouble("last_per_transaction_limit"));
                account.setAccountLimitUpdateTime(rs.getTimestamp("account_limit_update_time").toLocalDateTime());
                account.setPerTransactionLimitUpdateTime(rs.getTimestamp("per_transaction_limit_update_time").toLocalDateTime());
                return account;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // Account not found
    }
    // method to create a limit offer in the database
    public void createLimitOffer(LimitOffer limitOffer) {
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(
                     "INSERT INTO limit_offer (account_id, limit_type, new_limit, " +
                     "offer_activation_time, offer_expiry_time, status) VALUES (?, ?, ?, ?, ?, ?)")
        ) {
            pstmt.setLong(1, limitOffer.getAccountId());
            pstmt.setString(2, limitOffer.getLimitType().name());
            pstmt.setDouble(3, limitOffer.getNewLimit());
            pstmt.setTimestamp(4, Timestamp.valueOf(limitOffer.getOfferActivationTime()));
            pstmt.setTimestamp(5, Timestamp.valueOf(limitOffer.getOfferExpiryTime()));
            pstmt.setString(6, limitOffer.getStatus().name());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // Method to fetch active limit offers for a given account ID and active date
    public List<LimitOffer> getActiveOffers(long accountId, LocalDateTime activeDate) {
        List<LimitOffer> activeOffers = new ArrayList<>();
        try (Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(
                    "SELECT * FROM limit_offer WHERE account_id = ? AND " +
                    "offer_activation_time <= ? AND offer_expiry_time >= ? AND status = ?")
        ) {
            pstmt.setLong(1, accountId);
            pstmt.setTimestamp(2, Timestamp.valueOf(activeDate));
            pstmt.setTimestamp(3, Timestamp.valueOf(activeDate));
            pstmt.setString(4, LimitOfferStatus.PENDING.name());

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    LimitOffer limitOffer = new LimitOffer();
                    limitOffer.setLimitOfferId(rs.getLong("limit_offer_id"));
                    limitOffer.setAccountId(rs.getLong("account_id"));
                    limitOffer.setLimitType(LimitType.valueOf(rs.getString("limit_type")));
                    limitOffer.setNewLimit(rs.getDouble("new_limit"));
                    limitOffer.setOfferActivationTime(rs.getTimestamp("offer_activation_time").toLocalDateTime());
                    limitOffer.setOfferExpiryTime(rs.getTimestamp("offer_expiry_time").toLocalDateTime());
                    limitOffer.setStatus(LimitOfferStatus.valueOf(rs.getString("status")));

                    activeOffers.add(limitOffer);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return activeOffers;
    }
    public LimitOffer getLimitOfferById(long limitOfferId) {
        LimitOffer limitOffer = null;
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM limit_offer WHERE limit_offer_id = ?")
        ) {
            pstmt.setLong(1, limitOfferId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    limitOffer = new LimitOffer();
                    limitOffer.setLimitOfferId(rs.getLong("limit_offer_id"));
                    limitOffer.setAccountId(rs.getLong("account_id"));
                    limitOffer.setLimitType(LimitType.valueOf(rs.getString("limit_type")));
                    limitOffer.setNewLimit(rs.getDouble("new_limit"));
                    limitOffer.setOfferActivationTime(rs.getTimestamp("offer_activation_time").toLocalDateTime());
                    limitOffer.setOfferExpiryTime(rs.getTimestamp("offer_expiry_time").toLocalDateTime());
                    limitOffer.setStatus(LimitOfferStatus.valueOf(rs.getString("status")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return limitOffer;
    }

    public void updateAccount(Account account) {
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(
                     "UPDATE account SET account_limit = ?, per_transaction_limit = ?, " +
                     "last_account_limit = ?, last_per_transaction_limit = ?, " +
                     "account_limit_update_time = ?, per_transaction_limit_update_time = ? WHERE account_id = ?")
        ) {
            pstmt.setDouble(1, account.getAccountLimit());
            pstmt.setDouble(2, account.getPerTransactionLimit());
            pstmt.setDouble(3, account.getLastAccountLimit());
            pstmt.setDouble(4, account.getLastPerTransactionLimit());
            pstmt.setTimestamp(5, Timestamp.valueOf(account.getAccountLimitUpdateTime()));
            pstmt.setTimestamp(6, Timestamp.valueOf(account.getPerTransactionLimitUpdateTime()));
            pstmt.setLong(7, account.getAccountId());
            int rowsUpdated = pstmt.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Account with ID " + account.getAccountId() + " updated successfully.");
            } else {
                System.out.println("No account found with the provided ID: " + account.getAccountId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to update the status of an active and pending limit offer in the database
    public void updateLimitOfferStatus(long limitOfferId, LimitOfferStatus status) {
        try (Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(
                    "UPDATE limit_offer SET status = ? WHERE limit_offer_id = ? AND status = ?")
        ) {
            conn.setAutoCommit(false);

            pstmt.setString(1, status.name());
            pstmt.setLong(2, limitOfferId);
            pstmt.setString(3, LimitOfferStatus.PENDING.name());

            int rowsUpdated = pstmt.executeUpdate();

            if (rowsUpdated == 0) {
                System.out.println("No active or pending limit offer found with the provided ID: " + limitOfferId);
                conn.rollback();
            } else {
                System.out.println("Limit offer with ID " + limitOfferId + " status updated to: " + status);

                if (status == LimitOfferStatus.ACCEPTED) {
                    // Fetch the accepted limit offer details
                    LimitOffer acceptedOffer = getLimitOfferById(limitOfferId);
                    if (acceptedOffer != null) {
                        // Update account limit values and limit update date
                        Account account = getAccountById(acceptedOffer.getAccountId());
                        if (account != null) {
                            if(acceptedOffer.getLimitType() == LimitType.PER_TRANSACTION_LIMIT){
                                double old_limit = account.getPerTransactionLimit();
                                account.setPerTransactionLimit(acceptedOffer.getNewLimit());
                                account.setLastPerTransactionLimit(old_limit);
                                account.setPerTransactionLimitUpdateTime(LocalDateTime.now());
                            }
                            else{
                                double old_limit = account.getAccountLimit();
                                account.setAccountLimit(acceptedOffer.getNewLimit());
                                account.setLastAccountLimit(old_limit);
                                account.setAccountLimitUpdateTime(LocalDateTime.now());
                            }

                            // Update account in the database
                            updateAccount(account);
                        }
                    }
                }

                conn.commit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}
