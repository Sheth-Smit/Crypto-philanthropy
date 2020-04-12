package crypto_philanthropy;



public class Transaction {
    private int transactionID;
    private int userID;
    private String donor;
    private String organization;
    private long amount;
    private String date;
    
    public Transaction(int transactionID, int userID, String donor, String organization, long amount, String date){
        this.transactionID = transactionID;
        this.userID = userID;
        this.donor = donor;
        this.organization = organization;
        this.amount = amount;
        this.date = date;
    }
    
    public String getDate(){
        return this.date;
    }
    
    public int getUserID(){
        return this.userID;
    }
    
    public int getTransactionID(){
        return this.transactionID;
    }
    
    public long getAmount(){
        return this.amount;
    }
    
    public String getOrg(){
        return this.organization;
    }
    
}
