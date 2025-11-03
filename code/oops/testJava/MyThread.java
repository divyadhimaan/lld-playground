package testJava;

import java.util.concurrent.*;

class MyThread extends Thread{
    @Override
    public void run(){
        System.out.println("thread is running");
    }
}

class MyRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println("thread is running");
    }
}

class MyCallable implements Callable<String> {
    @Override
    public String call() throws Exception {
        return "thread is running";
    }
}

class TestMyThread{
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        MyThread t1 = new MyThread();
        t1.start();

        Thread t2 = new Thread(new MyRunnable());
        t2.start();

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<String> future = executor.submit(new MyCallable());

        System.out.println(future.get());
        System.out.println(future.isDone());

        executor.shutdown();

    }
}
