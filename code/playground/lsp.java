abstract class Bird3 {
}

abstract class FlyingBird3 extends Bird3 {
    abstract void fly();
}

abstract class NonFlyingBird3 extends Bird3 { }

class Sparrow3 extends FlyingBird3 {
    @Override
    void fly() {
        System.out.println("Sparrow is flying.");
    }
}
class Penguin3 extends NonFlyingBird3 { }


public class lsp {
    public static void main(String[] args){

    }
}

