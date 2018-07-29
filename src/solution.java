import sun.tools.tree.UplevelReference;

import java.util.*;

public class solution {
    /**whole minute dilemma
     * */
    public static int MinuteDilemma(int[] songs) {
        if (songs == null || songs.length == 0) {
            return 0;
        }
        /**store the song's length and its frequency*/
        Map<Integer, Integer> map = new HashMap<>();
        /**record the max time to reduce time complexity */
        int max = songs[0];
        int pairs = 0;
        //start_point and end_point to reduce time complexity
        int start_point, end_point;
        for (int i = 0; i < songs.length; i++) {
            if (songs[i] > max) {
                max = songs[i];
            }
            start_point = ((songs[i] / 60) + 1) * 60;
            end_point = 2 * max > start_point ? 2 * max : start_point;
            /**search for pair*/
            for (int j = start_point; j <= end_point; j += 60) {
                pairs += map.getOrDefault(j - songs[i],0);
            }
            /**store in hashmap*/
            if (!map.containsKey(songs[i])) {
                map.put(songs[i], 1);
            } else {
                map.put(songs[i], map.get(songs[i]) + 1);
            }
        }
        return pairs;
    }

    /**charitable giving
     * */
    public static String[] giving(int[] income) {
        class Charity {
            String name;
            int money;
            Charity (String s, int num) {
                name = s;
                money = num;
            }
        }
        String[] result = new String[income.length];
        Queue<Charity> queue = new PriorityQueue<>(3, new Comparator<Charity>() {
            public int compare(Charity o1, Charity o2) {
                if (o1.money == o2.money) {
                    return o1.name.compareToIgnoreCase(o2.name);//不同的降序
                } else {
                    return o1.money - o2.money;//相同的升序
                }
            }
        });
        queue.offer(new Charity("A", 0));
        queue.offer(new Charity("B", 0));
        queue.offer(new Charity("C", 0));
        int index = 0;
        for (int donate : income) {
            Charity curr = queue.poll();
            result[index++] = curr.name;
            curr.money += donate;
            queue.offer(curr);
        }
        return result;
    }

    /**min path
     * */
    public static int MinPath(int[][] map) {
        if (map == null || map.length == 0 || map[0].length == 0) {
            return 0;
        }
        int row = map.length;
        int column = map[0].length;
        int UpLevelMin = 0;
        for (int i = 1; i < row; i++) {
            for (int j = 0; j < column; j++) {
                UpLevelMin = map[i - 1][j];
                if (j > 0) {
                    UpLevelMin = Math.min(map[i - 1][j - 1], UpLevelMin);
                }
                if (j < column - 1) {
                    UpLevelMin = Math.min(map[i - 1][j + 1], UpLevelMin);
                }
                map[i][j] += UpLevelMin;
            }
        }
        int result = map[row - 1][0];
        for (int j = 1; j < column; j++) {
            if (map[row - 1][j] < result) {
                result = map[row - 1][j];
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int[][] temp = new int[][]{{1,2,3},{3,2,1},{1,2,3}};
        System.out.print(MinPath(temp));
    }
}