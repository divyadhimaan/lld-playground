public class DoublyLinkedListNode {
    private final int key;
    public DoublyLinkedListNode prev;
    public DoublyLinkedListNode next;

    public DoublyLinkedListNode(int key)
    {
        this.key = key;
        this.prev = null;
        this.next = null;
    }
    public int getKey()
    {
        return key;
    }
}
