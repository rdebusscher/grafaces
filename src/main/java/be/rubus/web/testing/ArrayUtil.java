package be.rubus.web.testing;

/**
 *
 */
public final class ArrayUtil {

    private ArrayUtil() {
    }

    public static Integer[] dropFirstItem(Integer[] data) {
        Integer[] result = null;
        if (data.length > 1) {
            result = new Integer[data.length - 1];
            System.arraycopy(data, 1, result, 0, result.length);
        }
        return result;
    }
}
