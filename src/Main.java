import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

class CustomHashSet<T> {
    private static final int DEFAULT_CAPACITY = 16;
    private Node<T>[] table;
    private int size;

    static class Node<T> {
        T value;
        Node<T> next;

        Node(T value) {
            this.value = value;
        }
    }

    public CustomHashSet() {
        table = new Node[DEFAULT_CAPACITY];
    }

    public void insert(T value) {
        int index = getIndex(value);
        Node<T> current = table[index];

        while (current != null) {
            if (current.value.equals(value)) return;
            current = current.next;
        }

        Node<T> newNode = new Node<>(value);
        newNode.next = table[index];
        table[index] = newNode;
        size++;
    }

    public boolean remove(T value) {
        int index = getIndex(value);
        Node<T> current = table[index];
        Node<T> prev = null;

        while (current != null) {
            if (current.value.equals(value)) {
                if (prev == null) {
                    table[index] = current.next;
                } else {
                    prev.next = current.next;
                }
                size--;
                return true;
            }
            prev = current;
            current = current.next;
        }
        return false;
    }

    public int getIndex(T value) {
        return Math.abs(value.hashCode()) % table.length;
    }

    public int size() {
        return size;
    }

    public String toString() {
        if (size == 0) {
            return "[]";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("[");

        boolean first = true;
        for (int i = 0; i < table.length; i++) {
            Node<T> current = table[i];
            while (current != null) {
                if (!first) {
                    sb.append(", ");
                }
                sb.append(current.value);
                first = false;
                current = current.next;
            }
        }

        sb.append("]");
        return sb.toString();
    }
}

class CustomArrayList<T> {
    private static final int DEFAULT_CAPACITY = 10;
    private Object[] elements;
    private int size;

    public CustomArrayList() {
        elements = new Object[DEFAULT_CAPACITY];
    }

    public void add(T element) {
        ensureCapacity();
        elements[size++] = element;
    }

    public void add(int index, T element) {
        checkIndexForAdd(index);
        ensureCapacity();
        System.arraycopy(elements, index, elements, index + 1, size - index);
        elements[index] = element;
        size++;
    }

    public T get(int index) {
        checkIndex(index);
        return (T) elements[index];
    }

    public T remove(int index) {
        checkIndex(index);
        T removed = (T) elements[index];
        System.arraycopy(elements, index + 1, elements, index, size - index - 1);
        elements[--size] = null;
        return removed;
    }

    public void addAll(Collection<? extends T> collection) {
        for (T element : collection) {
            add(element);
        }
    }

    private void ensureCapacity() {
        if (size == elements.length) {
            elements = Arrays.copyOf(elements, elements.length * 2);
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    private void checkIndexForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    public int size() {
        return size;
    }

    public String toString() {
        if (size == 0) {
            return "[]";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < size; i++) {
            sb.append(elements[i]);
            if (i < size - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}

public class Main {
    public static void main(String[] args) {
        CustomHashSet<String> set = new CustomHashSet<>();
        set.insert("first");
        set.insert("second");
        set.insert("third");
        System.out.println("HashSet: " + set + ", Size: " + set.size());

        set.remove("first");
        System.out.println("После удаления: " + set + ", Size: " + set.size());

        Collection<Integer> c = new ArrayList<>();
        for (Integer i = 1; i < 6; i++) {
            c.add(i);
        }

        CustomArrayList<Integer> list = new CustomArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        System.out.println("ArrayList: " + list + ", Size: " + list.size());

        list.add(1, 99);
        System.out.println("После вставки по индексу: " + list + ", Size: " + list.size());

        System.out.println("Элемент с индексом 1 (get): " + list.get(1));

        list.remove(0);
        System.out.println("После удаления: " + list + ", Size: " + list.size());

        list.addAll(c);
        System.out.println("После addAll: " + list + ", Size: " + list.size());
    }
}