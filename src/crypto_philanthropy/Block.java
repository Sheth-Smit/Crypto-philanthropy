package crypto_philanthropy;

import static crypto_philanthropy.CryptoPhilanthropy.difficulty;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Block {
    
    private Transaction transaction;
    private long timestamp;
    public String hash;
    public String previousHash;
    private int nonce;
    
    public Block(Transaction transaction, String previousHash){
        this.transaction = transaction;
        this.previousHash = previousHash;
        
        try{
            Date transDate = new SimpleDateFormat("dd/MM/yyyy").parse(transaction.getDate());
            timestamp = transDate.getTime();
        } catch(Exception e){
            timestamp = new Date().getTime();
        }
        
        this.hash = getSHAHashValue();
        
    }
    
    public String getSHAHashValue(){
        String hashString = this.previousHash + 
                            Long.toString(timestamp) + 
                            Long.toString(transaction.getAmount()) + 
                            Integer.toString(nonce);
        
        return SHA256.applySha256(hashString);
    }   
    
    public int getDonorID(){
        return this.transaction.getUserID();
    }
    
    public void showData(){
        System.out.println("Transaction ID: " + this.transaction.getTransactionID());
        System.out.println("Donated to: " + this.transaction.getOrg());
        System.out.println("Charity Amount: " + this.transaction.getAmount());
        System.out.println("Charity date: " + this.transaction.getDate());
    }
    
     public void mineBlock(int difficulty){
        String target = new String(new char[difficulty]).replace('\0', '0'); //Create a string with difficulty * "0" 
        while(!hash.substring( 0, difficulty).equals(target)) {
                nonce ++;
                hash = getSHAHashValue();
        }
        System.out.println("Block Mined!!! : " + hash);
    }
    
}
