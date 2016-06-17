package kata.java;

import java.util.Optional;
import java.util.stream.Stream;

public class BTreeSet {
    private final int pageLength;
    private Page root;
    private int height;

    public BTreeSet(int pageLength) {
        this.pageLength = pageLength;
        this.root = new Page(0);
        this.height = 0;
    }

    public boolean contains(int key) {
        Page page = root;
        int index = page.indexOf(key);
        if (index == -1) return false;
        while (page.entries[index].next != null) {
            page = page.entries[index].next;
            index = page.indexOf(key);
        }
        return page.contains(key);
    }

    public void add(int key) {
        add(root, key, height).ifPresent(
                page -> {
                    Page local = new Page(2);
                    local.entries[0] = new Entry(root.entries[0].key, root);
                    local.entries[1] = new Entry(page.entries[0].key, page);
                    root = local;
                    height += 1;
                }
        );
    }

    private Optional<Page> add(Page page, int key, int height) {
        int index = (int) page.entries().filter(e -> e.key < key).count();
        Entry entry = new Entry(key, null);
        if (height != 0) {
            if (!add(page.entries[index - 1].next, key, height - 1)
                    .map(entry::update)
                    .isPresent()) {
                return Optional.empty();
            }
        }

        page.insert(index, entry);
        if (page.isNotFull()) return Optional.empty();
        else return Optional.of(page.split());
    }

    private class Page {
        private Entry[] entries = new Entry[pageLength];
        private int size;

        Page(int size) {
            this.size = size;
        }

        Stream<Entry> entries() {
            return Stream.of(entries).limit(size);
        }

        void insert(int index, Entry entry) {
            shiftRight(index);
            entries[index] = entry;
            size += 1;
        }

        private void shiftRight(int index) {
            System.arraycopy(entries, index, entries, index + 1, size - index);
        }

        Page split() {
            int aHalf = pageLength / 2;
            Page aNewPage = new Page(aHalf);
            size = aHalf;
            System.arraycopy(entries, aHalf, aNewPage.entries, 0, pageLength - aHalf);
            return aNewPage;
        }

        boolean isNotFull() {
            return size < pageLength;
        }

        int indexOf(int key) {
            return (int) entries().filter(e -> e.key < key).count();
        }

        boolean contains(int key) {
            return entries().anyMatch(e -> e.key == key);
        }
    }

    private class Entry {
        private int key;
        private Page next;

        Entry(int key, Page next) {
            this.key = key;
            this.next = next;
        }

        private Entry update(Page page) {
            key = page.entries[0].key;
            next = page;
            return this;
        }
    }
}
