package q1326;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by Administrator on 2017/6/3 0003.
 */
class StringIarraySeq{
    public final int[] arr;//[0,1,1,0,1]
    public final int changedTime;//0
    public final int lastChanedIndex;

    /**
     * 这里的arr是题目输入的字符串转化而来的
     * @param arr
     */
    StringIarraySeq(int[] arr) {
        this.arr = arr;
        changedTime=0;
        lastChanedIndex =-1;
    }
    private StringIarraySeq(int[] arr, int changedTime, int lastChanedIndex) {
        this.arr = arr;
        this.changedTime = changedTime;
        this.lastChanedIndex = lastChanedIndex;
    }

    public StringIarraySeq[] getNextGeneration(){
        StringIarraySeq[] seqs = new StringIarraySeq[arr.length-lastChanedIndex-1];
        for(int i =0;i<seqs.length;i++){
            int[] newArr;
            newArr = Arrays.copyOf(arr,arr.length);
            int nextIndex = i+lastChanedIndex+1;
            newArr[nextIndex] = 1-newArr[nextIndex];
            seqs[i] = new StringIarraySeq(newArr,changedTime+1,nextIndex);
        }
        return seqs;
    }

    @Override
    public String toString() {
        String raw = "";
        for(int i:arr){
            raw+=i;
        }
        return String.format("[time:%d,lastIndex:%d,raw:%s]",changedTime,lastChanedIndex,raw);
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

    static TreeMap<String, Integer> O1string2changedBits = new TreeMap<String, Integer>(){{
        /**
         * 比如 "1"->0,"10"->1,"1010"->2
         * 保存起来的目的是方便下一个string查,比如"0000101"可以简化为"10",直接得到1
         */
        //预先放入一些简单的
        put("1",0);
        put("0",0);

    }};

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

    public int getMinChangeNeeded(){

        if (toStringed.length()>1&&(toStringed.startsWith("0")||toStringed.endsWith("1"))){
            String copy = toStringed;
            while (copy.length()>1&&copy.startsWith("0")){
                copy = copy.substring(1);
            }
            while (copy.length()>1&&copy.endsWith("1")){
                copy = copy.substring(0,copy.length()-1);
            }
            Solver smaller = new Solver(copy);
            return smaller.getMinChangeNeeded();
        }else{
            //已经排除了天生就是有序01字符串的可能性
            if(!Solver.O1string2changedBits.containsKey(toStringed)){
                ArrayList<ArrayList<StringIarraySeq[]>> table = new ArrayList<ArrayList<StringIarraySeq[]>>(){{
                    add(new ArrayList<StringIarraySeq[]>(){{
                        add(new StringIarraySeq[]{new StringIarraySeq(s01)});
                    }});
                    //初始化在0位置
                }};
                while (true){
                    ArrayList<StringIarraySeq[]> newGeneration= new ArrayList<StringIarraySeq[]>();
                    for(StringIarraySeq[] seqs:table.get(0)){
                        for(StringIarraySeq seq:seqs){
                            if(Solver.isOrdered01String(seq.arr)){
                                Solver.O1string2changedBits.put(toStringed,seq.changedTime);
                                return Solver.O1string2changedBits.get(toStringed);
                            }
                        }
                        for(StringIarraySeq seq:seqs){
                            newGeneration.add(seq.getNextGeneration());
                        }
                    }
                    table.add(newGeneration);
                    table.remove(0);
                }
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
