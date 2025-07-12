interface coffeeInterface {
    int price;
    void selectCoffee(String coffeeType);
    void selectSize(String size);
    void addExtras(String extras);
    void confirmOrder();
    void cancelOrder();
    String getOrderDetails();
}
