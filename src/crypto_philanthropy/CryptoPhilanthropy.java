package crypto_philanthropy;

import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CryptoPhilanthropy {

    public static ArrayList<Block> blockChain;
    public static HashMap<Integer, String> idToName = new HashMap<>();
    public static int difficulty = 5;
    
    public static void main(String[] args) {
        int choice = 0;
        blockChain = new ArrayList<>();
        
        Scanner sc = new Scanner(System.in);
        
        while(choice < 3){
            System.out.println("1. Donate Money\n2. View Donor Charity History\n3. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            if(choice == 1){
                createBlock();
            } else if (choice == 2){
                int userID;
                System.out.print("Enter Donor ID to view History: ");
                
                try{
                    userID = sc.nextInt();
                } catch(Exception e){
                    System.out.println("Enter Integer");
                    continue;
                }
                if(!idToName.containsKey(userID)){
                    System.out.println("************************************");
                    System.out.println("No such donor found");
                    System.out.println("************************************");
                    continue;
                }

                viewUser(userID);
            }
        }
        
    }
    
    public static void createBlock(){
        int transID, donorID;
        long amount;
        String donor, org, prevHash, transDate, donorIDString, amountString;
        Scanner sc = new Scanner(System.in);
        
        System.out.print("Enter Donor ID: ");
        donorIDString = sc.nextLine();
        
        try{
            donorID = Integer.parseInt(donorIDString);
        } catch (Exception e){
            System.out.println("Donor ID should be an integer");
            return;
        }
        
        if(idToName.containsKey(donorID)){
            System.out.println("Donor exists with name " + idToName.get(donorID));
            donor = idToName.get(donorID);
        } else {
            System.out.print("Enter Donor Name: ");
            donor = sc.nextLine();
            idToName.put(donorID, donor);
        }

        System.out.print("Enter Organization Name: ");
        org = sc.nextLine();
        System.out.print("Enter Charity Amount: ");
        amountString = sc.nextLine();
        try{
            amount = Long.parseLong(amountString);
        } catch (Exception e){
            System.out.println("Amount should be an integer");
            return;
        }
        
        transID = 1000+blockChain.size()+1;
        
        Date date = new Date(); 
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
        transDate = formatter.format(date);
        
        if(blockChain.size() == 0)
            prevHash = "0";
        else
            prevHash = blockChain.get(blockChain.size()-1).hash;
        
        Transaction newTransaction = new Transaction(transID, donorID, donor, org, amount, transDate);
        Block newBlock = new Block(newTransaction, prevHash);
        
        System.out.println("************************************");
        System.out.println("New Block created");
        System.out.println("Hash: " + newBlock.hash);
        System.out.println("Previous Hash: " + newBlock.previousHash);
        System.out.println("************************************");
        
        System.out.println("Trying to verify block(zero knowledge proof)");
        if(isBlockValid(transID)){
            System.out.println("Block Verified");
            blockChain.add(newBlock);
            System.out.println("Trying to Mine the added block(proof of work)...");
            blockChain.get(blockChain.size()-1).mineBlock(difficulty);

            if(verifyTransaction() == true){
                System.out.println("Transaction Verified....");
            }
            else{
                System.out.println("Transaction Failed!!!!...");
            }
        }
        else{
            System.out.println("Block could not be verified!!!!...");
        }
    }
    
    public static Boolean verifyTransaction(){
        Block currBlock, prevBlock;
        String minedHashCheck = new String(new char[difficulty]).replace('\0', '0');
        
        for(int i = 1; i < blockChain.size(); i++){
            currBlock = blockChain.get(i);
            prevBlock = blockChain.get(i-1);
            
            if(!currBlock.hash.equals(currBlock.getSHAHashValue())){
                System.out.println("Current hashes of block " + i+1 + " are not equal");
                return false;
            }
            
            if(!prevBlock.hash.equals(currBlock.previousHash)){
                System.out.println("Previous hashes of block " + i + " are not equal");
                return false;
            }
            
            if(!currBlock.hash.substring( 0, difficulty).equals(minedHashCheck)) {
                System.out.println("This block hasn't been mined");
                return false;
            }
        }
        return true;
    }
    
    public static void viewUser(int userID){
        int flag = 0;
        System.out.println("\nTransactions for user "+ idToName.get(userID) + " with User ID "+userID+": ");
        
        for(int i = 0; i < blockChain.size(); i++){
            Block block = blockChain.get(i);
            
            if(block.getDonorID() == userID){
                System.out.println("************************************");
                block.showData();
                flag = 1;
            }
        }
        System.out.println("************************************");
        if(flag == 0){
            System.out.println("No charity history found");
        }
    }
    
    private static long pow(long x,long y,long p)
    {
            long res=1;
            x=x%p;
            while(y>0)
            {
                    if((y&1)==1)
                            res=(res*x)%p;
                    y=y>>1;
                    x=(x*x)%p;
            }
            return res;
    }
    
    private static long getRandomNumberInRange(long min, long max) {
            return (long)(Math.random() * ((max - min) + 1)) + min;
    }
    
    public static Boolean zeroKnowledge(long gen,long prime,int transID,int ite){
        long y = pow(gen,transID,prime);
        for(int i = 0; i < ite; i++){
            long r = getRandomNumberInRange(0,prime-2);
            long h = pow(gen,r,prime);
            long b = getRandomNumberInRange(0,1);
            long s = (r + b*transID)%(prime-1);
            if( pow(gen,s,prime) != (h*pow(y,b,prime))%prime)
                return false;
        }
        return true;
    }
    
    public static Boolean isBlockValid(int transID){
        return zeroKnowledge(24,2695139,transID,1000);
    }
}
