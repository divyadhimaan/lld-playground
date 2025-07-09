package composite;
import java.util.ArrayList;
import java.util.List;

// Leaf class
class File {
    private String name;
    private int size;

    public File(String name, int size) {
        this.name = name;
        this.size = size;
    }

    public void display() {
        System.out.println("File: " + name + ", Size: " + size);
    }

    public int getSize() {
        return size;
    }
}

// Composite class
class Folder {
    private String name;
    private List<Object> children = new ArrayList<>();

    public Folder(String name) {
        this.name = name;
    }

    public void add(Object obj) {
        children.add(obj);
    }

    public void display() {
        System.out.println("Folder: " + name);
        for (Object obj : children) {
            if (obj instanceof File) {
                ((File) obj).display();
            } else if (obj instanceof Folder) {
                ((Folder) obj).display();
            }
        }
    }

    public int getSize() {
        int total = 0;
        for (Object obj : children) {
            if (obj instanceof File) {
                total += ((File) obj).getSize();
            } else if (obj instanceof Folder) {
                total += ((Folder) obj).getSize();
            }
        }
        return total;
    }
}

public class CompositeViolation {
    public static void main(String[] args) {
        File f1 = new File("file1.txt", 10);
        File f2 = new File("file2.txt", 20);

        Folder folder = new Folder("Documents");
        folder.add(f1);
        folder.add(f2);

        folder.display();
        System.out.println("Total size: " + folder.getSize());
    }
}
