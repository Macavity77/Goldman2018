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

    static String winner(String erica, String bob) {
        if (erica == null && bob != null && bob.length() > 0) {
            return "Bob";
        }
        if (bob == null && erica != null && erica.length() > 0) {
            return "Erica";
        }
        int erica_e = (int) erica.chars().filter(chi -> chi == 'E').count();
        int erica_m = (int) erica.chars().filter(chi -> chi == 'M').count();
        int erica_h = (int) erica.chars().filter(chi -> chi == 'H').count();

        int bob_e = (int) bob.chars().filter(chi -> chi == 'E').count();
        int bob_m = (int) bob.chars().filter(chi -> chi == 'M').count();
        int bob_h = (int) bob.chars().filter(chi -> chi == 'H').count();

        int erica_score = 3 * erica_h + 2 * erica_m + erica_e;
        int bob_score = 3 * bob_h + 2 * bob_m + bob_e;

        if (erica_score > bob_score) {
            return "Erica";
        } else if (bob_score > erica_score) {
            return "Bob";
        } else if (erica_h > bob_h) {
            return "Erica";
        } else if (bob_h > erica_h) {
            return "Bob";
        } else if (erica_m > bob_m) {
            return "Erica";
        } else if (bob_m > erica_m) {
            return "Bob";
        } else if (erica_e > bob_e) {
            return "Erica";
        } else if (bob_e > erica_e) {
            return "Bob";
        } else {
            return "Tie";
        }
    }

    static List<Integer> allocateSchools(List<Integer> schoolSeatsArray, List<Integer> studentScoreArray, List<List<Integer>> studentSchoolPreferencesArray) {
        class Student {
            int ID;
            int score;
            Student (int id, int score) {
                ID = id;
                this.score = score;
            }
        }

        Student[] studentRank = new Student[studentScoreArray.size()];
        for (int i = 0; i < studentScoreArray.size(); i++) {
            studentRank[i] = new Student(i, studentScoreArray.get(i));
        }
        Arrays.sort(studentRank, new Comparator<Student>() {
            public int compare(Student s1, Student s2) {
                return s2.score - s1.score;
            }
        });

        List<Integer> currRank;
        int studNotAllocate = 0;
        for (Student currStudent : studentRank) {
            currRank = studentSchoolPreferencesArray.get(currStudent.ID);
            for (int i = 0; i < currRank.size(); i++) {
                if (schoolSeatsArray.get(currRank.get(i)) > 0) {
                    schoolSeatsArray.set(currRank.get(i), schoolSeatsArray.get(currRank.get(i)) - 1);
                    break;
                }
                if (i == currRank.size() - 1) {
                    studNotAllocate++;
                }
            }
        }

        int seatNotFilled = 0;
        for (int i : schoolSeatsArray) {
            seatNotFilled += i;
        }

        List<Integer> result = new ArrayList<>();
        result.add(seatNotFilled);
        result.add(studNotAllocate);

        return result;
    }

    public static void main(String[] args) {
//        int[][] temp = new int[][]{{1,2,3},{3,2,1},{1,2,3}};
//        System.out.print(MinPath(temp));
        List<Integer> seats = new ArrayList<>();
        seats.add(1);
        seats.add(3);
        seats.add(1);
        seats.add(2);
        List<Integer> seats1 = new ArrayList<>();
        seats1.add(990);
        seats1.add(780);
        seats1.add(830);
        seats1.add(860);
        seats1.add(920);
        List<List<Integer>> seats2 = new ArrayList<>();
        List<Integer> p1 = new ArrayList<>();
        List<Integer> p2 = new ArrayList<>();
        List<Integer> p3 = new ArrayList<>();
        List<Integer> p4 = new ArrayList<>();
        List<Integer> p5 = new ArrayList<>();
        p1.add(3);
        p1.add(2);
        p1.add(1);
        p2.add(3);
        p2.add(0);
        p2.add(0);
        p3.add(2);
        p3.add(0);
        p3.add(1);
        p4.add(0);
        p4.add(1);
        p4.add(3);
        p5.add(0);
        p5.add(2);
        p5.add(3);
        seats2.add(p1);
        seats2.add(p2);
        seats2.add(p3);
        seats2.add(p4);
        seats2.add(p5);
        System.out.print(allocateSchools(seats, seats1,seats2));
    }
}