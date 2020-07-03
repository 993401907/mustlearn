package context.suffixArray;

import java.util.Arrays;

/**
 * @author 伍立子
 */
public class SuffixArray {
    private  String[] suffixArray;
    private final int N;

    public SuffixArray(String text) {
        N = text.length();
        suffixArray = new String[N];
        for (int i = 0; i <N ; i++) {
            suffixArray[i] = text.substring(i);
        }
        Arrays.sort(suffixArray);
    }

    public int length() {
        return N;
    }

    public String select(int i) {
        return suffixArray[i];
    }

    public int index(int i) {
        return N-suffixArray[i].length();
    }

    public int lcp(int i) {
        return lcp(suffixArray[i],suffixArray[i-1]);
    }
    private int lcp(String curr, String last) {
        int N = Math.min(curr.length(),last.length());
        for (int i = 0; i < N; i++) {
            if (curr.charAt(i) != last.charAt(i)) {
                return i;
            }
        }
        return N;
    }

    private int rank(String key) {
        int lo = 0, hi = N-1;
        while (lo <= hi) {
            int mid = lo+(hi-lo)/2;
            int cmp = key.compareTo(suffixArray[mid]);
            if (cmp < 0) {
                hi = mid-1;
            } else if (cmp > 0) {
                lo = mid+1;
            }else {
                return mid;
            }
        }
        return lo;
    }
}
