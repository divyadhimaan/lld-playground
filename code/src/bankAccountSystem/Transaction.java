class Transaction {
    private final String type;
    private final double amount;
    private final String timestamp;

    Transaction(String type, double amount){
        this.type = type;
        this.amount = amount;
        this.timestamp = java.time.LocalDateTime.now().toString();
    }

//    @Override
    public String toString(){
        return timestamp + " - " + type + ": " + amount;
    }
}
