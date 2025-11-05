package service;

import model.Card;

public class CardReaderService {
    private Card insertedCard;
    /**
     * Simulates reading a card from the slot.
     */
    public Card readCard() {
        // In real ATM: hardware would detect card and read its chip/magnetic data
        System.out.println("Reading card...");
        this.insertedCard = new Card("123456789", "1234", "12/27", "XYZ Bank");
        System.out.println("Card detected: " + insertedCard.getBankName());
        return insertedCard;
    }

    /**
     * Returns the currently inserted card (if any).
     */
    public Card getInsertedCard() {
        return insertedCard;
    }

    /**
     * Ejects the card from the machine.
     */
    public void ejectCard() {
        if (insertedCard != null) {
            System.out.println("Please collect your card (" + insertedCard.getCardNumber() + ")");
            insertedCard = null;
        } else {
            System.out.println("No card to eject.");
        }
    }

    /**
     * Checks if a card is currently inserted.
     */
    public boolean isCardInserted() {
        return insertedCard != null;
    }
}
