package kata.java;

import java.util.stream.Stream;

public class BTreeSet<SetKey extends Comparable<SetKey>> {
    private final int pageLength;
    private Page<SetKey> root;
    private int height;

    public BTreeSet(int pageLength) {
        this.pageLength = pageLength;
        this.height = 0;
        this.root = new Page<>();
    }

    public boolean contains(SetKey key) {
        Page<SetKey> page = root;
        int index = page.indexOf(key);
        while (page.hasNext(index)) {
            page = page.next(index);
            index = page.indexOf(key);
        }
        return page.contains(key);
    }

    public void add(SetKey key) {
        Page<SetKey> page = root.addKey(key);
        if (page != null) {
            Page<SetKey> local = new Page<>();
            local.insert(0, new Entry<>(root.firstKey(), root));
            local.insert(1, new Entry<>(page.firstKey(), page));
            root = local;
            height += 1;
        }
    }

    public int height() {
        return height;
    }

    private class Page<PageKey extends Comparable<PageKey>> {
        private Entry<PageKey>[] entries = new Entry[pageLength];
        private int size;

        int indexOf(PageKey key) {
            return (int) Stream.of(entries).limit(size).filter(e -> e.key.compareTo(key) <= 0).count() - 1;
        }

        boolean contains(PageKey key) {
            return Stream.of(entries).limit(size).anyMatch(e -> e.key == key);
        }

        boolean hasNext(int index) {
            return index > -1 && entries[index].next != null;
        }

        Page<PageKey> next(int index) {
            return entries[index].next;
        }

        void insert(int index, Entry<PageKey> entry) {
            shiftRight(index);
            entries[index] = entry;
            size += 1;
        }

        private void shiftRight(int index) {
            System.arraycopy(entries, index, entries, index + 1, size - index);
        }

        Page<PageKey> split() {
            int aHalf = pageLength / 2;
            Page<PageKey> aNewPage = new Page<>();
            System.arraycopy(entries, aHalf, aNewPage.entries, 0, size - aHalf);
            size = aHalf;
            aNewPage.size = aHalf;
            return aNewPage;
        }

        Page<PageKey> addKey(PageKey key) {
            int index = indexOf(key);
            Entry<PageKey> entry;
            if (hasNext(index)) {
                Page<PageKey> local = next(index).addKey(key);
                if (local != null) {
                    entry = new Entry<>(local.firstKey(), local);
                }
                else {
                    return null;
                }
            }
            else {
                entry = new Entry<>(key, null);
            }
            index += 1;

            insert(index, entry);
            if (isFull()) return split();
            else return null;
        }

        private boolean isFull() {
            return size == pageLength;
        }

        PageKey firstKey() {
            return entries[0].key;
        }
    }

    private class Entry<EntryKey extends Comparable<EntryKey>> {
        private final EntryKey key;
        private final Page<EntryKey> next;

        Entry(EntryKey key, Page<EntryKey> next) {
            this.key = key;
            this.next = next;
        }
    }
}
