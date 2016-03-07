package kata.joins;

public enum Join {
    INNER {
        @Override
        void keyMatching() {
        }

        @Override
        boolean checkMatchingAndReset() {
            return true;
        }
    },
    OUTER {
        private boolean match;

        @Override
        void keyMatching() {
            match = true;
        }

        @Override
        boolean checkMatchingAndReset() {
            boolean res = match;
            match = false;
            return res;
        }
    };

    abstract void keyMatching();

    abstract boolean checkMatchingAndReset();
}
