package atm;

public class ATMService {
    KeypadService keypadService;
    DisplayService displayService;
    CardReaderService cardReaderService;
    CashDispenserService cashDispenserService;
    ATMReserve atmReserve;
    ReceiptPrinter receiptPrinter;

    AuthenticationService authenticationService;
    TransactionService transactionService;

    ATMState currentState;

}
