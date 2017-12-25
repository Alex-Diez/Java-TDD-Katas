package kata.java;

public class BTreeSet {
    private final int pageLength;
    private int height;
    private Page root;

    public BTreeSet(int pageLength) {
        this.pageLength = pageLength;
        root = new Page(0);
    }

    public boolean contains(int key) {
        return contains(root, key, height);
    }

    private boolean contains(Page page, int key, int height) {
        Entry[] entries = page.entries;

        if (height == 0) {
            for (int i = 0; i < page.size; i++) {
                if (key == entries[i].key) {
                    return true;
                }
            }
        }
        else {
            for (int i = 0; i < page.size; i++) {
                if (i + 1 == page.size || key < entries[i + 1].key) {
                    return contains(entries[i].next, key, height - 1);
                }
            }
        }
        return false;
    }

    public void add(int key) {
        Page page = add(root, key, height);
        if (page != null) {
            Page local = new Page(2);
            local.entries[0] = new Entry(root.entries[0].key, root);
            local.entries[1] = new Entry(page.entries[0].key, page);
            root = local;
            height += 1;
        }
    }

    private Page add(Page page, int key, int height) {
        int index;
        Entry entry = new Entry(key, null);
        if (height == 0) {
            for (index = 0; index < page.size; index++) {
                if (key < page.entries[index].key) {
                    break;
                }
            }
        }
        else {
            for (index = 0; index < page.size; index++) {
                if (index + 1 == page.size || key < page.entries[index + 1].key) {
                    Page local = add(page.entries[index++].next, key, height - 1);
                    if (local != null) {
                        entry.key = local.entries[0].key;
                        entry.next = local;
                        break;
                    }
                    else {
                        return null;
                    }
                }
            }
        }
        for (int i = page.size; i > index; i--) {
            page.entries[i] = page.entries[i - 1];
        }
        page.entries[index] = entry;
        page.size += 1;
        if (page.size < pageLength) {
            return null;
        }
        else {
            return split(page);
        }
    }

    private Page split(Page page) {
        Page newPage = new Page(pageLength / 2);
        page.size = pageLength / 2;
        int end = pageLength / 2;
        for (int i = end; i < pageLength; i++) {
            newPage.entries[i - end] = page.entries[i];
        }
        return newPage;
    }

    private class Page {
        private final Entry[] entries = new Entry[pageLength];
        private int size;

        private Page(int size) {
            this.size = size;
        }
    }

    private class Entry {
        private int key;
        private Page next;

        private Entry(int key, Page next) {
            this.key = key;
            this.next = next;
        }
    }
}
