package singleton;


class PrinterSpooler {
    public PrinterSpooler() {
        System.out.println("Spooler started");
    }

    public void addPrintJob(String job) {
        System.out.println("Added print job: " + job);
    }
}

public class SingletonViolation {
    public static void main(String[] args) {
        PrinterSpooler spooler1 = new PrinterSpooler();
        spooler1.addPrintJob("Doc1");

        PrinterSpooler spooler2 = new PrinterSpooler();
        spooler2.addPrintJob("Doc2");

        // ❌ Two separate instances created!
    }
}
