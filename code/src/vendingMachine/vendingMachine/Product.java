package vendingMachine;

public class Product {
    private final String productName;
    private final double productPrice;
    private int quantity;

    Product(String name, double price) {
        this.productName = name;
        this.productPrice = price;
    }

    String getProductName(){
        return this.productName;
    }

    double getProductPrice(){
        return this.productPrice;
    }

    int getQuantity(){
        return this.quantity;
    }

    void setQuantity(int quantity){
        this.quantity = this.quantity + quantity;
    }

    void decrementQuantity(){
        if(this.quantity > 0)
        {
            this.quantity = this.quantity-1;
        }
    }
}
