package q1038;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;

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
        return String.format("[need:%s,value:%s]",need,value);
    }
}

class O1KnapsackSolver {
    private final Shouhin[] shouhins;
    private final int quantityOfShouken;
    private int[][] index_weight_maxValue;

    O1KnapsackSolver(Shouhin[] shouhins, int quantityOfShouken) {
        this.shouhins = shouhins;
        this.quantityOfShouken = quantityOfShouken;
        index_weight_maxValue = new int[shouhins.length + 1][quantityOfShouken + 1];
        for (int i = 1; i <= shouhins.length; i++) {
            for (int j = 1; j <= quantityOfShouken; j++) {
                index_weight_maxValue[i][j] = -1;
            }
        }
    }

    private int get(int index, int weight) {
        if (index_weight_maxValue[index][weight] < 0) {
            if (shouhins[index - 1].need > weight) {
                index_weight_maxValue[index][weight] = get(index - 1, weight);
            } else {
                index_weight_maxValue[index][weight] = Math.max(shouhins[index - 1].value + get(index - 1, weight - shouhins[index - 1].need), get(index - 1, weight));
            }
        }
        return index_weight_maxValue[index][weight];
    }

    public int getAnswer(){
        return get(shouhins.length,quantityOfShouken);
    }


}

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
        O1KnapsackSolver solver = new O1KnapsackSolver(shouhins,quantityOfShouken);
        System.out.println(solver.getAnswer());


    }
}
//AC	Java	9745ms	217MB