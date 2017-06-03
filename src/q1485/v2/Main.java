package q1485.v2;

/**
 * Created by Administrator on 2017/6/3 0003.
 */

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @anchor http://hihocoder.com/problemset/problem/1485
 */
public class Main {

    static boolean isHihoStr(String subStr){
        Pattern pattern;
        Matcher matcher;
        int count;

        count = 0;
        pattern = Pattern.compile("h",Pattern.CASE_INSENSITIVE);
        matcher=pattern.matcher(subStr);
        while (matcher.find()){
            count++;
        }
        if(count!=2)return false;

        count=0;
        pattern = Pattern.compile("i",Pattern.CASE_INSENSITIVE);
        matcher=pattern.matcher(subStr);
        while (matcher.find()){
            count++;
        }
        if(count!=1)return false;

        count=0;
        pattern = Pattern.compile("o",Pattern.CASE_INSENSITIVE);
        matcher=pattern.matcher(subStr);
        while (matcher.find()){
            count++;
        }
        if(count!=1)return false;

        return true;
    }
    static int calcMinLen(String line){
        int fullLength = line.length();
        int subLength = 4;
        for(;subLength<=fullLength;subLength++){
            for(int i=0;i<=fullLength-subLength;i++){
                if(isHihoStr(line.substring(i,i+subLength))){
                    return subLength;
                }
            }
        }
        return -1;

    }
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        String line = scanner.nextLine();
        System.out.println(calcMinLen(line));


    }
}
//AC
//Java	2659ms	25MB