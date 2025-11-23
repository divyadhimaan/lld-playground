// ****** It's better to write code in your local code editor and paste it back here *********

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;


// use helper.print("") or helper.println("") for printing logs else logs will not be visible.
public class Solution implements Q06WebpageVisitCounterInterface {
    private Helper06 helper;
    private ConcurrentHashMap<Integer, AtomicInteger> map;

    public Solution(){}

    public void init(int totalPages, Helper06 helper){
        this.helper=helper;
        this.map = new ConcurrentHashMap<>();
    }

    // increment visit count for pageIndex by 1
    public void incrementVisitCount(int pageIndex) {
        map.computeIfAbsent(pageIndex, k -> new AtomicInteger(0)).incrementAndGet();
    }

    // return total visit count for a given page
    public int getVisitCount(int pageIndex) {
        return map.getOrDefault(pageIndex, new AtomicInteger(0)).get();
    }
}

// uncomment below code in case you are using your local ide like intellij, eclipse etc and
// comment it back again back when you are pasting completed solution in the online CodeZym editor.
// if you don't comment it back, you will get "java.lang.AssertionError: java.lang.LinkageError"
// This will help avoid unwanted compilation errors and get method autocomplete in your local code editor.

 interface Q06WebpageVisitCounterInterface {
 void init(int totalPages, Helper06 helper);
 void incrementVisitCount(int pageIndex);
 int getVisitCount(int pageIndex);
 }

 class Helper06 {
 void print(String s){System.out.print(s);}
 void println(String s){System.out.println(s);}
 }

 class Tester{
    public static void main(String[] args) throws InterruptedException {
        Solution sol = new Solution();
        sol.init(10, new Helper06());


        int pageIndex = 3;

        Runnable task = () -> {
            for (int i = 0; i < 1000; i++) {
                sol.incrementVisitCount(pageIndex);
            }
        };

        Thread[] threads = new Thread[10];
        for (int i = 0; i < 10; i++) {
            threads[i] = new Thread(task);
        }

        for (Thread t : threads) t.start();
        for (Thread t : threads) t.join();

        System.out.println("Expected count = 10000");
        System.out.println("Actual count   = " + sol.getVisitCount(pageIndex));
    }
 }