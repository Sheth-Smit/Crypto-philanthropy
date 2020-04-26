
# Crypto-philanthropy
***Block chain-based Charity Transactions***

# Members:
### Daivik Purani(2017A7PS0166H)
### Vivek Soni(2017A7PS0173H)
### Dhruvil Shah(2017A7PS1566H)
### Sheth Smit(2017A7PS1666H)

Programming Language: **Java**

IDE used: **NetBeans**

## Main functions of the Code:

### createBlock()

For each donation made a block will be created containing the Donor ID, Receiver ID, Amount donated, Time of donation and a Unique token.

### verifyTransaction()

For the authentication of unique donation token (Zero knowledge proof).

### mineBlock()

Verification of the confidentiality and integrity of the blockchain by using the hashes of preceding or succeeding blocks and it is validated by majority of miners.

### viewUser()

Lists all the successful transaction against the user.

## How to run

The **CryptoPhilanthropy.java** is the main java file to run.

Upon compiling and running, the code gives three options:

1. Donate

2. View donor charity history

3. Exit

Upon entering choice 1, details of donor is taken along with the details of organization and amount.	

As soon as the transaction is done from donor end, zeroknowledge() is called to verify identity, after which the block is added to the Blockchain, Block is mined to set the hash with difficulty , and finally verify Transaction is called to confirm all blocks are mined and their hashes are also accurate.

If choice 2 is selected transaction history of the donor is displayed asking for a donor ID. The transactions are displayed with the following details.

*	Transaction ID

*	Organization donated to

*	Amount donated

*	Date of donation


