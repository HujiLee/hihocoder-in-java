package q1326;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by Administrator on 2017/6/3 0003.
 */
class StringIarraySeq {
    public final int[] arr;//[0,1,1,0,1]
    public final int changedTime;//0
    public final int lastChanedIndex;

    /**
     * 这里的arr是题目输入的字符串转化而来的
     *
     * @param arr
     */
    StringIarraySeq(int[] arr) {
        this.arr = arr;
        changedTime = 0;
        lastChanedIndex = -1;
    }

    private StringIarraySeq(int[] arr, int changedTime, int lastChanedIndex) {
        this.arr = arr;
        this.changedTime = changedTime;
        this.lastChanedIndex = lastChanedIndex;
    }

    public StringIarraySeq[] getNextGeneration() {
        StringIarraySeq[] seqs = new StringIarraySeq[arr.length - lastChanedIndex - 1];
        for (int i = 0; i < seqs.length; i++) {
            int[] newArr;
            newArr = Arrays.copyOf(arr, arr.length);
            int nextIndex = i + lastChanedIndex + 1;
            newArr[nextIndex] = 1 - newArr[nextIndex];
            seqs[i] = new StringIarraySeq(newArr, changedTime + 1, nextIndex);
        }
        return seqs;
    }

    @Override
    public String toString() {
        String raw = "";
        for (int i : arr) {
            raw += i;
        }
        return String.format("[time:%d,lastIndex:%d,raw:%s]", changedTime, lastChanedIndex, raw);
    }
}

class Solver {

    /**
     * @param str example:[0,1,0,1,0,0,1]
     * @return
     */
    static private boolean isOrdered01String(int[] str) {
        int maxIndexFor0 = -1;
        int minIndexFor1 = -1;
        for (int i = 0; i < str.length; i++) {
            if (str[i] == 0) {
                maxIndexFor0 = i;
            } else {
                if (minIndexFor1 == -1) {
                    minIndexFor1 = i;
                }
            }
        }
        if (maxIndexFor0 == -1) return true;//全是1
        if (minIndexFor1 == -1) return true;//全是0
        if (minIndexFor1 > maxIndexFor0) return true;
        return false;
    }

    static TreeMap<String, Integer> O1string2changedBits = new TreeMap<String, Integer>() {{
        /**
         * 比如 "1"->0,"10"->1,"1010"->2
         * 保存起来的目的是方便下一个string查,比如"0000101"可以简化为"10",直接得到1
         */
        //预先放入一些简单的
        put("1", 0);
        put("0", 0);

    }};
    static TreeMap<Integer, int[][]> length2refArrays = new TreeMap<Integer, int[][]>() {{
        put(1, new int[2][]);
        get(1)[0] = new int[]{0};
        get(1)[1] = new int[]{1};
        put(2, new int[3][]);
        get(2)[0] = new int[]{0, 0};
        get(2)[1] = new int[]{0, 1};
        get(2)[2] = new int[]{1, 1};

    }};
    static int calcDiff(int[] s01,int[] ref){
        assert (s01.length==ref.length);
        int diff = 0;
        for(int i=0;i<s01.length;i++){
            if(s01[i]!=ref[i])diff++;
        }
        return diff;
    }
    static int[][] getRefArray(int length){
        if (!length2refArrays.containsKey(length)){
            int[][] arrs = new int[length+1][];
            for(int i =0;i<arrs.length;i++){
                arrs[i] = new int[length];//[0,0,0,0,0,0]
                int j = i;
                while (j>0){
                    arrs[i][length-j]=1;
                    j--;
                }
            }
            length2refArrays.put(length,arrs);
        }

        return length2refArrays.get(length);
    }
    private final int[] s01;
    private final String toStringed;//只用来toString()

    Solver(String s01) {
        this.s01 = new int[s01.length()];
        this.toStringed = s01;
        char[] chs = s01.toCharArray();
        for (int i = 0; i < chs.length; i++) {
            if (chs[i] == '1') {
                this.s01[i] = 1;
            } else {
                this.s01[i] = 0;
            }
        }
    }

    @Override
    public String toString() {
        return String.format("%s", this.toStringed);
    }

    public int getMinChangeNeeded() {

        if (toStringed.length() > 1 && (toStringed.startsWith("0") || toStringed.endsWith("1"))) {
            String copy = toStringed;
            while (copy.length() > 1 && copy.startsWith("0")) {
                copy = copy.substring(1);
            }
            while (copy.length() > 1 && copy.endsWith("1")) {
                copy = copy.substring(0, copy.length() - 1);
            }
            Solver smaller = new Solver(copy);
            return smaller.getMinChangeNeeded();
        } else {
            //已经排除了天生就是有序01字符串的可能性
            if(!Solver.O1string2changedBits.containsKey(toStringed)){
                int[][] refArray = Solver.getRefArray(s01.length);
                int min = Integer.MAX_VALUE;

                int tempDiff;
                for(int[] ref:refArray){
                    tempDiff=Solver.calcDiff(s01,ref);
                    if(tempDiff<min){
                        min=tempDiff;
                    }
                }
                Solver.O1string2changedBits.put(toStringed,min);
            }
            return Solver.O1string2changedBits.get(toStringed);
        }

    }
}

/**
 * 第二次尝试:
 */
public class Main {
    static BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

    static String getStr() throws IOException {
        return bufferedReader.readLine();
    }

    public static void main(String[] args) throws Exception {

        int count = Integer.parseInt(getStr());
        String line;
        Solver solver;
        while (count > 0) {
            line = getStr();
            solver = new Solver(line);
            System.out.println(solver.getMinChangeNeeded());
            count--;
        }


    }
}
//AC Java	996ms	26MB
/**
 * @anchor http://www.mamicode.com/info-detail-1420722.html
 */
/*
枚举所有状态。

比如长度为4的字符串最终可能的状态为：

0000

0001

0011

0111

1111

所以n^2可以解决

 */