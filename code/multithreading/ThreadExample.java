class MyThread extends Thread{
    @Override
    public void run(){
        for(int i=0;i<5;i++){
            System.out.println("Thread "+ Thread.currentThread().getName()+" is running: " + i);
            try {
                Thread.sleep(2000); // Sleep for 2 second
            } catch (InterruptedException e) {
                System.out.println("Thread was interrupted: " + e.getMessage());
            }
        }
    }
}

public class ThreadExample {
    public static void main(String[] args) {
        MyThread thread1 = new MyThread();
        MyThread thread2 = new MyThread();


        thread1.start(); // Start the thread1
        thread2.start(); // Start the thread2 concurrently

    }
}