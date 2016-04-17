package kata.org;

public enum Join {
    INNER {

        void keyMatched() {
        }

        boolean checkMatchingAndReset() {
            return true;
        }
    },

    OUTER {
        private boolean matching = false;

        void keyMatched() {
            matching = true;
        }

        boolean checkMatchingAndReset() {
            boolean res = matching;
            matching = false;
            return res;
        }
    };

    abstract void keyMatched();

    abstract boolean checkMatchingAndReset();
}
