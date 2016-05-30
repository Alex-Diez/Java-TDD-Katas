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

    public int height() {
        return height;
    }

    public boolean contains(SetKey key) {
        Page<SetKey> page = root;
        int index = page.indexOf(key);
        while (index > -1 && page.next(index) != null) {
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

    private class Page<PageKey extends SetKey> {
        private Entry<PageKey>[] entries = new Entry[pageLength];
        private int size;

        PageKey firstKey() {
            return entries[0].key;
        }

        int indexOf(PageKey key) {
            return (int) Stream.of(entries).limit(size).filter(e -> e.key.compareTo(key) <= 0).count() - 1;
        }

        boolean contains(PageKey key) {
            return Stream.of(entries).limit(size).anyMatch(e -> e.key.compareTo(key) == 0);
        }

        Page<PageKey> split() {
            int aHalf = pageLength / 2;
            Page<PageKey> aNewPage = new Page<>();
            System.arraycopy(entries, aHalf, aNewPage.entries, 0, pageLength - aHalf);
            aNewPage.size = aHalf;
            size = aHalf;
            return aNewPage;
        }

        void insert(int index, Entry<PageKey> entry) {
            shiftRight(index);
            entries[index] = entry;
            size += 1;
        }

        private void shiftRight(int index) {
            System.arraycopy(entries, index, entries, index + 1, size - index);
        }

        Page<PageKey> addKey(PageKey key) {
            int index = indexOf(key);
            Entry<PageKey> entry;
            if (index > -1 && next(index) != null) {
                Page<PageKey> local = next(index).addKey(key);
                if (local != null) entry = new Entry<>(local.firstKey(), local);
                else return null;
            }
            else {
                entry = new Entry<>(key, null);
            }
            index += 1;

            insert(index, entry);
            if (size < pageLength) return null;
            else return split();
        }

        Page<PageKey> next(int index) {
            return entries[index].next;
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
