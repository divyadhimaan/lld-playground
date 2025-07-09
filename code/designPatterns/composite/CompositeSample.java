package composite;

import java.util.ArrayList;
import java.util.List;

// Common Component interface
interface FileSystemComponent {
    void display();
    int getSize();
}

// Leaf
class File2 implements FileSystemComponent {
    private String name;
    private int size;

    public File2(String name, int size) {
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

// Composite
class Folder2 implements FileSystemComponent {
    private String name;
    private List<FileSystemComponent> children = new ArrayList<>();

    public Folder2(String name) {
        this.name = name;
    }

    public void add(FileSystemComponent component) {
        children.add(component);
    }

    public void display() {
        System.out.println("Folder: " + name);
        for (FileSystemComponent component : children) {
            component.display();
        }
    }

    public int getSize() {
        int total = 0;
        for (FileSystemComponent component : children) {
            total += component.getSize();
        }
        return total;
    }
}

// Usage
public class CompositeSample {
    public static void main(String[] args) {
        File2 f1 = new File2("file1.txt", 10);
        File2 f2 = new File2("file2.txt", 20);
        Folder2 folder1 = new Folder2("Folder1");

        folder1.add(f1);
        folder1.add(f2);

        File2 f3 = new File2("file3.txt", 30);
        Folder2 root = new Folder2("Root");
        root.add(folder1);
        root.add(f3);

        root.display();
        System.out.println("Total size: " + root.getSize());
    }
}

