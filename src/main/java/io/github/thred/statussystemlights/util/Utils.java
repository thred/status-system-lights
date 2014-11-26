package io.github.thred.statussystemlights.util;

import java.text.Collator;
import java.util.Comparator;

public class Utils {

    /**
     * A collator set to primary strength, which means 'a', 'A' and '&auml;' is the same
     */
    public static final Collator DICTIONARY_COLLATOR;

    public static final Comparator<String> DICTIONARY_COMPARATOR = new Comparator<String>() {

        @Override
        public int compare(String left, String right) {
            return dictionaryCompare(left, right);
        }

    };

    static {
        DICTIONARY_COLLATOR = Collator.getInstance();

        DICTIONARY_COLLATOR.setStrength(Collator.PRIMARY);
        DICTIONARY_COLLATOR.setDecomposition(Collator.CANONICAL_DECOMPOSITION);
    }

    public static <TYPE> int compare(final Comparator<TYPE> comparator, final TYPE left, final TYPE right) {
        if (left == null) {
            if (right != null) {
                return 1;
            }
        }
        else {
            if (right != null) {
                return comparator.compare(left, right);
            }

            return -1;
        }

        return 0;
    }

    public static int dictionaryCompare(final String left, final String right) {
        return compare(DICTIONARY_COLLATOR, left, right);
    }

}
