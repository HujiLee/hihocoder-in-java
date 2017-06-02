package q1485;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by Administrator on 2017/6/2 0002.
 */

/**
 * @anchor http://hihocoder.com/problemset/problem/1485
 */
public class Main {
    static TreeMap<Character, LinkedList<Integer>> char2indexes = new TreeMap<Character, LinkedList<Integer>>() {{
        put('h', new LinkedList<Integer>());
        put('i', new LinkedList<Integer>());
        put('o', new LinkedList<Integer>());
    }};
    static String debugger(){
        String show="";
        for(Object i:char2indexes.get('h').toArray()){
            show+=i+",";
        }
        show+=" | ";
        for(Integer i:char2indexes.get('i')){
            show+=i+",";
        }
        show+=" | ";
        for(Integer i:char2indexes.get('o')){
            show+=i+",";
        }
        return show;
    }
    static private boolean checkHIO() {
        if (char2indexes.get('i').size() != 1) return false;
        if (char2indexes.get('h').size() != 2) return false;
        if (char2indexes.get('o').size() != 1) return false;
        return true;
    }
    static private char lastChar = ' ';
    static boolean read(char ch, int index) {
        LinkedList<Integer> list;
        boolean result;
        switch (ch) {
            case 'h':
                list = char2indexes.get('h');
//                list.push(index);//todo:should use 'add' to act as a Queue
                list.add(index);
                if (list.size() > 2) {
                    //list.pop();//todo:not correct,should use 'removeFirst' to act as a Queue
                    list.removeFirst();
                    if(lastChar=='h'){
                        char2indexes.get('i').clear();
                        char2indexes.get('o').clear();
                    }
                }
                result= checkHIO();
                lastChar='h';
                return result;
            case 'i':
                list = char2indexes.get('i');
                list.add(index);
                if (list.size() > 1) {
                    list.removeFirst();
                    if(lastChar=='i'){
                        char2indexes.get('h').clear();
                        char2indexes.get('o').clear();
                    }
                }
                result= checkHIO();
                lastChar='i';
                return result;
            case 'o':
                list = char2indexes.get('o');
                list.add(index);
                if (list.size() > 1) {
                    list.removeFirst();
                    if(lastChar=='o'){
                        char2indexes.get('h').clear();
                        char2indexes.get('i').clear();
                    }
                }
                result=  checkHIO();
                lastChar='o';
                return result;
            default:
                return false;
        }
    }

    static int calcLength() {
        int maxIndex = Integer.MIN_VALUE, minIndex = Integer.MAX_VALUE;
        for (Integer integer : char2indexes.get('h')) {
            if (integer > maxIndex) maxIndex = integer;
            if (integer < minIndex) minIndex = integer;
        }
        for (Integer integer : char2indexes.get('o')) {
            if (integer > maxIndex) maxIndex = integer;
            if (integer < minIndex) minIndex = integer;
        }
        for (Integer integer : char2indexes.get('i')) {
            if (integer > maxIndex) maxIndex = integer;
            if (integer < minIndex) minIndex = integer;
        }
        return maxIndex - minIndex + 1;
    }

    public static void main(String[] args) throws IOException {
        DataInputStream stream = new DataInputStream(System.in);
        char temp;
        int index = 0;
        int minLength = Integer.MAX_VALUE;
        while (true) {
            temp = (char) stream.read();
            if (temp == '\uFFFF' || temp == '\n') {
                break;
            }
            index++;
            boolean isHihoStr = read(temp, index);
            if (isHihoStr) {
                int length = calcLength();
                if(minLength>length){
                    minLength=length;
                }
            }
        }
        if(minLength<Integer.MAX_VALUE){
            System.out.println(minLength);
        }else {
            System.out.println(-1);
        }

    }
}
