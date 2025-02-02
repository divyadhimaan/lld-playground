import java.util.HashMap;
import java.util.Map;

public class EvictionEngine {
    Map<Integer, DoublyLinkedListNode> Nodes;
    DoublyLinkedList doublyLinkedList;
    public EvictionEngine()
    {
        this.doublyLinkedList = new DoublyLinkedList();
        Nodes = new HashMap<>();
    }

    public void KeyAccessed(int key)
    {
        DoublyLinkedListNode Node;
        if(Nodes.containsKey(key))
        {
            Node = Nodes.get(key);
            doublyLinkedList.detachNode(Node);

        }
        Node = new DoublyLinkedListNode(key);
        Nodes.put(key, Node);
        doublyLinkedList.addNode(Node);
    }
    public int EvictKey()
    {

        DoublyLinkedListNode Node = doublyLinkedList.getOldest().prev;
        int evictedKey = Node.getKey();
        doublyLinkedList.detachNode(Node);
        Nodes.remove(evictedKey);
        return evictedKey;
    }


}
