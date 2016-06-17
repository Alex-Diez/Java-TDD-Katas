package kata.java;

import java.util.stream.Stream;

public class BTreeSet<SetKey extends Comparable<SetKey>> {

    private final int pageLength;
    private Page<SetKey> root;

    public BTreeSet(int pageLength) {
        this.pageLength = pageLength;
        this.root = new Page<>();
    }

    public boolean contains(SetKey key) {
        Page<SetKey> page = root;
        int index = page.indexOf(key);
        while (index > -1 && page.entries[index].next != null) {
            page = page.entries[index].next;
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
        }
    }

    private class Page<PageKey extends SetKey> {
        private final Entry<PageKey>[] entries = (Entry<PageKey>[])new Entry[pageLength];
        private int size;

        Page() {
            this(0);
        }

        Page(int size) {
            this.size = size;
        }

        PageKey firstKey() {
            return entries[0].key;
        }

        int indexOf(PageKey key) {
            return (int) entries().filter(e -> e.key.compareTo(key) <= 0).count() - 1;
        }

        private Stream<Entry<PageKey>> entries() {
            return Stream.of(entries).limit(size);
        }

        boolean contains(SetKey key) {
            return entries().anyMatch(e -> e.key.compareTo(key) == 0);
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
            Page<PageKey> aNewPage = new Page<>(aHalf);
            System.arraycopy(entries, aHalf, aNewPage.entries, 0, size - aHalf);
            size = aHalf;
            return aNewPage;
        }

        boolean isFull() {
            return size == entries.length;
        }

        Page<PageKey> addKey(PageKey key) {
            int index = indexOf(key);
            Page<PageKey> local;
            Entry<PageKey> entry = index > -1 && entries[index].next != null && (local = entries[index].next.addKey(key)) != null
                    ? new Entry<>(local.firstKey(), local)
                    : new Entry<>(key, null);
            insert(index + 1, entry);
            if (isFull()) return split();
            else return null;
        }
    }

    private class Entry<EntryKey extends SetKey> {
        private EntryKey key;
        private Page<EntryKey> next;

        Entry(EntryKey key, Page<EntryKey> next) {
            this.key = key;
            this.next = next;
        }
    }
}
