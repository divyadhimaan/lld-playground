package vendingMachine.product;

public class Product {
    private final String productName;
    private final double productPrice;
    private int quantity;

    Product(String name, double price) {
        this.productName = name;
        this.productPrice = price;
    }

    public String getProductName(){
        return this.productName;
    }

    public double getProductPrice(){
        return this.productPrice;
    }

    public int getQuantity(){
        return this.quantity;
    }

    public void setQuantity(int quantity){
        this.quantity = this.quantity + quantity;
    }

    public void decrementQuantity(){
        if(this.quantity > 0)
        {
            this.quantity = this.quantity-1;
        }
    }
}
