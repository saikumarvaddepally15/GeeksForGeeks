//{ Driver Code Starts
import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader br;
    static PrintWriter ot;

    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        ot = new PrintWriter(System.out);

        int t = Integer.parseInt(br.readLine().trim());

        while (t-- > 0) {
            String s[] = br.readLine().trim().split(" ");
            int n = Integer.parseInt(s[0]), m = Integer.parseInt(s[1]);
            int edges[][] = new int[m][3];
            for (int i = 0; i < m; i++) {
                s = br.readLine().trim().split(" ");
                edges[i][0] = Integer.parseInt(s[0]);
                edges[i][1] = Integer.parseInt(s[1]);
                edges[i][2] = Integer.parseInt(s[2]);
            }

            List<Integer> list = new Solution().shortestPath(n, m, edges);

            ot.println(list.get(0));
            if (list.get(0) != -1 && !check(list, edges)) ot.println(-1);
        }
        ot.close();
    }

    static boolean check(List<Integer> list, int edges[][]) {
        Set<Integer> hs = new HashSet<>();
        Map<Integer, Map<Integer, Integer>> hm = new HashMap<>();
        for (int i = 1; i < list.size(); i++) hs.add(list.get(i));
        for (int x[] : edges) {
            if (hs.contains(x[0]) || hs.contains(x[1])) {
                if (!hm.containsKey(x[0])) hm.put(x[0], new HashMap<>());
                if (!hm.containsKey(x[1])) hm.put(x[1], new HashMap<>());
                hm.get(x[0]).put(x[1], x[2]);
                hm.get(x[1]).put(x[0], x[2]);
            }
        }
        int sum = 0;
        for (int i = 1; i < list.size() - 1; i++) {
            if (!hm.containsKey(list.get(i)) ||
                !hm.get(list.get(i)).containsKey(list.get(i + 1)))
                return false;
            sum += hm.get(list.get(i)).get(list.get(i + 1));
        }
        return sum == list.get(0);
    }
}

// } Driver Code Ends


class Solution {
     static class Pair implements Comparable<Pair> {
        int vertex, weight;
        Pair(int vertex, int weight) {
            this.vertex = vertex;
            this.weight = weight;
        }
        public int compareTo(Pair other) {
            return this.weight - other.weight;
        }
    }
    public List<Integer> shortestPath(int n, int m, int edges[][]) {
        //  Code Here.
          List<List<Pair>> adj = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            adj.add(new ArrayList<>());
        }
        for (int[] edge : edges) {
            int a = edge[0];
            int b = edge[1];
            int w = edge[2];
            adj.get(a).add(new Pair(b, w));
            adj.get(b).add(new Pair(a, w));
        }

        int[] dist = new int[n + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        int[] parent = new int[n + 1];
        boolean[] visited = new boolean[n + 1];
        dist[1] = 0;
        parent[1] = -1;

        PriorityQueue<Pair> pq = new PriorityQueue<>();
        pq.add(new Pair(1, 0));

        while (!pq.isEmpty()) {
            Pair current = pq.poll();
            int u = current.vertex;

            if (visited[u]) continue;
            visited[u] = true;

            for (Pair p : adj.get(u)) {
                int v = p.vertex;
                int weight = p.weight;

                if (dist[u] + weight < dist[v]) {
                    dist[v] = dist[u] + weight;
                    parent[v] = u;
                    pq.add(new Pair(v, dist[v]));
                }
            }
        }

        if (dist[n] == Integer.MAX_VALUE) {
            return Collections.singletonList(-1);
        }

        List<Integer> path = new ArrayList<>();
        for (int at = n; at != -1; at = parent[at]) {
            path.add(at);
        }

        Collections.reverse(path);
        path.add(0, dist[n]); // Adding the weight of the path at the beginning

        return path;
    }
}