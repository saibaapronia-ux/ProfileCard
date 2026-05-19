// class MobileAccount
class MobileAccount {
    // Private fields
    private String ownerName;
    private String phoneNumber;
    private double balance;
    private boolean active;

    // Private static fields
    private static int totalAccountsCreated = 0;
    private static double totalMoneyLoaded = 0.0;

    // Static final constants
    private static final double RATE_PER_MINUTE = 30.0;
    private static final double SMS_COST = 50.0;
    private static final double MAX_TOPUP = 100000.0;

    // Constructor
    public MobileAccount(String ownerName, String phoneNumber) {
        // Using 'this' to avoid the shadowing bug
        this.ownerName = ownerName;
        this.phoneNumber = phoneNumber;
        this.balance = 0.0;      // Starts with 0 balance
        this.active = true;      // Starts as active
        
        // Increment total accounts created counter
        totalAccountsCreated++;
    }

    // Validated method: topUp
    public boolean topUp(double amount) {
        if (amount > 0 && amount <= MAX_TOPUP) {
            this.balance += amount;
            totalMoneyLoaded += amount; // Track across all accounts
            return true;
        } else {
            System.out.println("Top-up rejected: Amount must be between 0 and " + MAX_TOPUP);
            return false;
        }
    }

    // Validated method: makeCall
    public boolean makeCall(double minutes) {
        double cost = minutes * RATE_PER_MINUTE;
        if (!this.active) {
            System.out.println("Call failed: Account is inactive.");
            return false;
        }
        if (this.balance < cost) {
            System.out.println("Call failed: Insufficient balance.");
            return false;
        }
        this.balance -= cost;
        return true;
    }

    // Validated method: sendSms
    public boolean sendSms(int count) {
        double cost = count * SMS_COST;
        if (!this.active) {
            System.out.println("SMS failed: Account is inactive.");
            return false;
        }
        if (this.balance < cost) {
            System.out.println("SMS failed: Insufficient balance.");
            return false;
        }
        this.balance -= cost;
        return true;
    }

    // State-changing methods
    public void deactivate() {
        this.active = false;
    }

    public void activate() {
        this.active = true;
    }

    // Getter for balance
    public double getBalance() {
        return this.balance;
    }

    // Static getter for total accounts created
    public static int getTotalAccountsCreated() {
        return totalAccountsCreated;
    }

    // Static getter for total money loaded
    public static double getTotalMoneyLoaded() {
        return totalMoneyLoaded;
    }

    // Print individual account statement formatted cleanly
    public void printStatement() {
        String status = this.active ? "Active" : "Inactive";
        System.out.printf("%-15s | %-15s | %,12.2f TZS | %-10s\n", 
                this.ownerName, this.phoneNumber, this.balance, status);
    }
}

//  public class AirtimeSystem
public class AirtimeSystem {
    public static void main(String[] args) {
        
        // Initialize the exactly three accounts provided in the table
        MobileAccount acc1 = new MobileAccount("Amina Hassan", "0712-345-678");
        MobileAccount acc2 = new MobileAccount("Baraka Juma", "0755-987-654");
        MobileAccount acc3 = new MobileAccount("Neema Salehe", "0623-111-222");

        System.out.println("--- Executing Initial Top-ups ---");
        // Perform initial top-ups from the summary data
        acc1.topUp(10000);
        acc2.topUp(5000);
        acc3.topUp(20000);

        System.out.println("\n--- Simulating Account Activities ---");
        
        // Simulating some operations to test all validated methods
        System.out.print("Amina makes a 10-minute call: ");
        acc1.makeCall(10); // Costs 300 TZS
        
        System.out.print("Baraka sends 5 SMS messages: ");
        acc2.sendSms(5);   // Costs 250 TZS

        System.out.print("Neema attempts an invalid top-up (-500): ");
        acc3.topUp(-500);  // Should fail validation

        System.out.print("Neema attempts an invalid top-up (150,000): ");
        acc3.topUp(150000); // Should fail validation (> MAX_TOPUP)

        System.out.println("\nDeactivating Baraka's account...");
        acc2.deactivate();
        System.out.print("Baraka tries to make a call while inactive: ");
        acc2.makeCall(5);  // Should fail because account is inactive

        // Print final formatted report
        System.out.println("\n=================================================================");
        System.out.println("                    AIRTIME SYSTEM REPORT                        ");
        System.out.println("=================================================================");
        System.out.printf("%-15s | %-15s | %-16s | %-10s\n", "Owner Name", "Phone Number", "Balance", "Status");
        System.out.println("-----------------------------------------------------------------");
        acc1.printStatement();
        acc2.printStatement();
        acc3.printStatement();
        System.out.println("=================================================================");
        
        // Summary Metrics (Static aggregators)
        System.out.printf("Total Mobile Accounts Created : %d\n", MobileAccount.getTotalAccountsCreated());
        System.out.printf("Total Revenue Loaded (System) : %,.2f TZS\n", MobileAccount.getTotalMoneyLoaded());
        System.out.println("=================================================================");
   
	}
}