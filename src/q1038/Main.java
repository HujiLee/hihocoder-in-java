package q1038;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.util.HashMap;
import java.util.TreeMap;

/**
 * Created by Administrator on 2017/6/7 0007.
 */
class Shouhin {
    public final int need;

    public Shouhin(int need, int value) {
        this.need = need;
        this.value = value;
    }

    public final int value;

    @Override
    public String toString() {
        return String.format("[need:%s,value:%s]", need, value);
    }
}

class O1KnapsackSolver {
    private final Shouhin[] shouhins;
    private final int quantityOfShouken;
    private int[][] index_weight_maxValue;


    O1KnapsackSolver(Shouhin[] shouhins, int quantityOfShouken) {
        this.shouhins = shouhins;
        this.quantityOfShouken = quantityOfShouken;

        need2MaxValueMaps = new HashMap[shouhins.length + 1];
        for (int i = 0; i < need2MaxValueMaps.length; i++) {
            need2MaxValueMaps[i] = new HashMap<Integer, Integer>();
        }

      /*  index_weight_maxValue = new int[shouhins.length + 1][quantityOfShouken + 1];
        for (int i = 1; i <= shouhins.length; i++) {
            for (int j = 1; j <= quantityOfShouken; j++) {
                index_weight_maxValue[i][j] = -1;
            }
        }*/


    }

/*    private int get(int index, int weight) {
        if (index_weight_maxValue[index][weight] < 0) {
            if (shouhins[index - 1].need > weight) {
                index_weight_maxValue[index][weight] = get(index - 1, weight);
            } else {
                index_weight_maxValue[index][weight] = Math.max(shouhins[index - 1].value + get(index - 1, weight - shouhins[index - 1].need), get(index - 1, weight));
            }
        }
        return index_weight_maxValue[index][weight];
    }*/

    private HashMap<Integer, Integer>[] need2MaxValueMaps;

    /**
     * @param index  　表示第index个对象是否放入背包
     * @param weight 表示该子问题中背包的容量
     * @return 该子问题能得到的最大总价值
     */
    private int getByMap(int index, int weight) {
        if (!need2MaxValueMaps[index].containsKey(weight)) {
            if(index==0||weight==0){
                need2MaxValueMaps[index].put(weight, 0);
            }else{
                if (weight < shouhins[index - 1].need) {
                    need2MaxValueMaps[index].put(weight, getByMap(index - 1, weight));
                } else {
                    need2MaxValueMaps[index].put(weight, Math.max(
                            shouhins[index - 1].value + getByMap(index - 1, weight - shouhins[index - 1].need),
                            getByMap(index - 1, weight)
                    ));
                }
            }

        }
        return need2MaxValueMaps[index].get(weight);

    }

    public int getAnswer() {
        return getByMap(shouhins.length, quantityOfShouken);
    }


}

/**
 * @anchor https://hihocoder.com/problemset/problem/1038
 */
public class Main {
    static class Helper {
        static private StreamTokenizer streamTokenizer = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));

        static public int getInt() throws IOException {
            streamTokenizer.nextToken();
            return (int) streamTokenizer.nval;
        }
    }

    public static void main(String[] args) throws IOException {
        int quantityOfShouhin = Helper.getInt();//賞品
        int quantityOfShouken = Helper.getInt();//賞券
        Shouhin[] shouhins = new Shouhin[quantityOfShouhin];
        for (int i = 0; i < quantityOfShouhin; i++) {
            shouhins[i] = new Shouhin(Helper.getInt(), Helper.getInt());
        }
        O1KnapsackSolver solver = new O1KnapsackSolver(shouhins, quantityOfShouken);
        System.out.println(solver.getAnswer());


    }
}
//TLE	Java	17374ms	268MB	29秒前