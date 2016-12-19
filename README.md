# SystemIDGenerator

Simple SystemID Generator. It collects user variables (HashMap in getuservars()) and the MAC of the Network Card. 
The collected data will be parsed to a String. The pared String gets Encrypted with AES.
The key for the Encryption are the first 16 bytes of the MAC address. The first 30 chars of the Encrypted string is the SystemID. 
