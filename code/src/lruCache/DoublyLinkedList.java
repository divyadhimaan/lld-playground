public class DoublyLinkedList {
    private final DoublyLinkedListNode latest;
    private final DoublyLinkedListNode oldest;

    public DoublyLinkedList()
    {
        this.latest = new DoublyLinkedListNode(-1);
        this.oldest = new DoublyLinkedListNode(-1);
        latest.next = oldest;
        oldest.prev = latest;
    }

    public void detachNode(DoublyLinkedListNode Node)
    {
        Node.next.prev = Node.prev;
        Node.prev.next = Node.next;
    }

    public void addNode(DoublyLinkedListNode Node)
    {
        Node.prev = latest;
        Node.next = latest.next;

        latest.next = Node;
        Node.next.prev = Node;
    }

    public DoublyLinkedListNode getLatest() {
        return latest;
    }
    public DoublyLinkedListNode getOldest() {
        return oldest;
    }

}
