package q1326;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.util.*;

/**
 * Created by Administrator on 2017/6/3 0003.
 */
public class Main {
    static class Solver {
        //static//todo:to be used in V2?
        private static final HashMap<String, Solver> O1Str2Solver = new HashMap<>();/*
        "000"->Solver("000")
        "0001001"->Solver("0001001")
        */
        private final static String separator = ",";

        private static String indexes2str(List<Integer> list) {
            String s = "";
            for (Integer i : list) {
                s += i + separator;
            }
            return s;
        }

        private static boolean isOrdered01String(String s) {
            int maxIndexFor0 = -1, minIndexFor1 = -1;
            final int strLength = s.length();
            final char[] chars = s.toCharArray();
            for (int i = 0; i < strLength; i++) {
                if (chars[i] == '0') {
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

        /**
         * @param raw             待反转的字符串
         * @param indexNeedChange 代表在某个位置进行反转;比如长得像"0","0,1,","0,1,3,",总之是有序的
         * @return 反转后得到的字符串
         */
        private String changeAt(String raw, String indexNeedChange) throws Exception {
            char[] rawArray = raw.toCharArray();//example:['0','1','0','1']
            //todo
            String[] indexes = indexNeedChange.split(Solver.separator);
            for (String index : indexes) {
                int i = Integer.parseInt(index);
                switch (rawArray[i]) {
                    case '0' :
                        rawArray[i] = '1';
                        break;
                    case '1' :
                        rawArray[i] = '0';
                        break;
                    default:
                        throw new Exception("should NEVER happen");
                }
            }
            String after = String.valueOf(rawArray);
            indexes2after01Changed.put(indexNeedChange,after);
            return after;
        }


        private final String s01;
        private final int length;

        Solver(String s01) {
            this.s01 = s01;
            length = s01.length();
        }


        private TreeMap<String, String> indexes2after01Changed = new TreeMap<>();/*
        比如 "0,"->"1"
        "0,2,4,"->"11000"
        这样的对应
        */


        public int needMinChange() throws Exception {
            if (isOrdered01String(s01)) return 0;
            TreeMap<Integer, Vector<LinkedList<Integer>>> length2indexes;
            length2indexes = new TreeMap<Integer,  Vector<LinkedList<Integer>>>() {{
                put(1, new Vector<LinkedList<Integer>>());
                for (int index = 0; index < length; index++) {
                    get(1).add(new LinkedList<Integer>());
                }
                int i = 0;

                for (LinkedList<Integer> list : get(1)) {
                    list.add(i);
                    i++;
                }
                /*
                1=>Set(List(0).List(1),List(2))
                2=>Set(List(0,1),List(0,2),List(1,2))
                3=>Set(List(0,1,2))
                 */
            }};

            //首先看看能不能只变处次
            for (LinkedList<Integer> list : length2indexes.get(1)) {
                if (isOrdered01String(changeAt(s01, indexes2str(list)))) {
                    return 1;
                }
            }

            //变更多处
            for (int howManyNeedChange = 2; howManyNeedChange <= length; howManyNeedChange++) {
                length2indexes.put(howManyNeedChange, new Vector<LinkedList<Integer>>());
                Vector<LinkedList<Integer>> newListSet = length2indexes.get(howManyNeedChange);
                for (LinkedList<Integer> oldList : length2indexes.get(howManyNeedChange - 1)) {
                    for (int maxValue = oldList.getLast() + 1; maxValue < length; maxValue++) {
                        LinkedList<Integer> newList = new LinkedList<>(oldList);
                        newList.add(maxValue);
                        newListSet.add(newList);
                        if (isOrdered01String(changeAt(s01, indexes2str(newList)))) {
                            return howManyNeedChange;
                        }
                    }
                }
            }


            return s01.length();

        }
    }
//
//    static StreamTokenizer st = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));
//
//    static int getInt() throws IOException {
//        st.nextToken();
//        return (int) st.nval;
//    }

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
            System.out.println(solver.needMinChange());
            count--;
        }


    }
}
