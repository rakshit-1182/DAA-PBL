import java.io.*;
import java.util.*;

public class ExamScheduler {

    static int V; // number of subjects

    // if safe
    static boolean isSafe(int v, int[][] graph, int[] color, int c) {
        for (int i = 0; i < V; i++) {
            if (graph[v][i] == 1 && color[i] == c)
                return false;
        }
        return true;
    }

    static boolean solve(int[][] graph, int[] color, int m, int v) {
        if (v == V)
            return true;

        for (int c = 1; c <= m; c++) {
            if (isSafe(v, graph, color, c)) {
                color[v] = c; // storing the color/time-slot if it is safe;

                if (solve(graph, color, m, v + 1)) // check for next one
                    return true;

                color[v] = 0; // backtracking
            }
        }
        return false;
    }

    // Main function that returns slots array
    public static Object[] getTimeSlots(String filename) throws Exception {

        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line;

        // Store subjects dynamically
        Set<String> subjectSet = new HashSet<>();
        List<List<String>> studentData = new ArrayList<>();

        while ((line = br.readLine()) != null) {

            line = line.trim();
            if (line.isEmpty())
                continue;

            String[] parts = line.split("\\s+", 3);
            if (parts.length < 3)
                continue;

            int roll = Integer.parseInt(parts[0]); // extracted roll

            String[] core = parts[1].split(",");
            String[] elective = parts[2].split(",");

            List<String> subjects = new ArrayList<>();

            // Core subjects
            for (String s : core) {
                s = s.trim().toLowerCase();
                subjects.add(s);
                subjectSet.add(s);
            }

            // Electives
            for (String s : elective) {
                s = s.trim().toLowerCase();
                subjects.add(s);
                subjectSet.add(s);
            }

            studentData.add(subjects);
        }

        br.close();

        // Map subjects to index
        V = subjectSet.size(); // hashmap's size
        String[] subjects = subjectSet.toArray(new String[0]); // converting the hashmap into the array

        Map<String, Integer> map = new HashMap<>(); // String | Integer-- mapping so that we can easily make graph using
                                                    // the indexes
        for (int i = 0; i < V; i++) { // java | 0
            map.put(subjects[i], i); // cloud | 1
        }

        // Building graph
        int[][] graph = new int[V][V];

        for (List<String> list : studentData) {

            List<Integer> subIndex = new ArrayList<>();

            for (String s : list) {
                subIndex.add(map.get(s)); // return idx corresponding to subject from the map;
            }

            for (int i = 0; i < subIndex.size(); i++) {
                for (int j = i + 1; j < subIndex.size(); j++) {
                    int u = subIndex.get(i);
                    int v = subIndex.get(j);
                    graph[u][v] = 1;
                    graph[v][u] = 1;
                }
            }
        }

        // Finding minimum slots
        int[] color = new int[V];
        int minSlots = 0;

        for (int m = 1; m <= V; m++) {
            Arrays.fill(color, 0);

            if (solve(graph, color, m, 0)) {
                minSlots = m;
                break;
            }
        }

        return new Object[] { color, subjects };
    }
}
