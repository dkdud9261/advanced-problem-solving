import java.util.*;

public class Week11 {

    public static class Bridge {
        int v1, v2; // v1->v2
        int capacity;
        int flow;
        public Bridge(int v1, int v2, int c, int f) {
            this.v1 = v1;
            this.v2 = v2;
            this.capacity = c;
            this.flow = f;
        }
    }

    public static void maxFlow(int[][] graph) {
        int n = graph.length;

        // 양방향 그래프 생성
        ArrayList<Bridge>[] G = new ArrayList[n];
        for(int i = 0; i < n; i++) {
            G[i] = new ArrayList<>();
            for(int j = 0; j < n; j++) G[i].add(new Bridge(i, j, graph[i][j], 0));
        }

        while(true) {
            Stack<Bridge> stack = new Stack<>();
            Queue<Bridge> path = new LinkedList<>();
            boolean[] visited = new boolean[n];
            stack.push(new Bridge(0, 0, Integer.MAX_VALUE, 0));
            int max_flow = Integer.MAX_VALUE;
            boolean path_exist = false;
            // dfs로 경로 탐색, 해당 경로에서 흘려보낼 수 있는 최대 유량 구하기
            while (!stack.isEmpty()) {
                Bridge b = stack.pop();
                if (visited[b.v2]) continue;
                path.add(b);
                visited[b.v2] = true;
                max_flow = Math.min(max_flow, b.capacity);
                if (b.v2 == n - 1) {
                    path_exist = true;
                    break;
                }
                for (Bridge nb : G[b.v2]) {
                    if (!visited[nb.v2] && nb.capacity > 0) stack.push(nb);
                }
            }
            // flow가 최대인 edge 저장(min-cut 후보)
            ArrayList<Bridge> min_cut = new ArrayList<>();
            for(int i = 1; i < n; i++) {
                for(int j = 0; j < G[i].size(); j++) {
                    if(G[i].get(j).flow > 0) {
                        if(G[i].get(j).flow == graph[i][j]) min_cut.add(new Bridge(i, j, 0, 0));
                    }
                }
            }
            if (path_exist) {   // 각 edge에 flow 증가시키고, 반대 방향은 감소시키기
                Bridge b1 = path.remove();
                while (!path.isEmpty()) {
                    Bridge b2 = path.remove();
                    if (graph[b1.v2][b2.v2] > 0 || graph[b2.v2][b1.v2] > 0) {
                        G[b1.v2].get(b2.v2).capacity -= max_flow;
                        G[b1.v2].get(b2.v2).flow += max_flow;
                        G[b2.v2].get(b1.v2).capacity = G[b1.v2].get(b2.v2).capacity;
                        G[b2.v2].get(b1.v2).flow = -1 * G[b1.v2].get(b2.v2).flow;
                    }
                    b1 = b2;
                }
            }
            else {  // 더 이상 흘려보낼 수 없으면 종료
                int final_max_flow = 0;
                for(int i = 0; i < n; i++) final_max_flow += G[i].get(n-1).flow;    // 도착지에 들어온 유량의 합
                System.out.println("Max flow: " + final_max_flow);
                System.out.print("Min cut: ");
                for(Bridge b : min_cut) System.out.print("(" + b.v1 + "," + b.v2 + ")");
                System.out.println();
                break;
            }
        }
    }

    // 1번 문제와 동일합니다.
    public static void maxCouple(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;

        int[][] graph = new int[n+m+2][n+m+2];
        for(int i = 0; i < n+m+2; i++) Arrays.fill(graph[i], -1);
        for(int i = 1; i <= n; i++) {
            graph[0][i] = 1;
            for(int j = 0; j < m; j++) {
                graph[i][j+m+1] = grid[i-1][j];
            }
        }
        for(int i = n+1; i <= n+m; i++) graph[i][n+m+1] = 1;

        ArrayList<Bridge>[] G = new ArrayList[n+m+2];
        for(int i = 0; i < n+m+2; i++) G[i] = new ArrayList<>();
        for(int i = 0; i < n+m+2; i++) {
            G[i] = new ArrayList<>();
            for(int j = 0; j < n+m+2; j++) G[i].add(new Bridge(i, j, graph[i][j], 0));
        }

        while(true) {
            Stack<Bridge> stack = new Stack<>();
            Queue<Bridge> path = new LinkedList<>();
            boolean[] visited = new boolean[n+m+2];
            stack.push(new Bridge(0, 0, Integer.MAX_VALUE, 0));
            int max_flow = Integer.MAX_VALUE;
            boolean path_exist = false;
            while (!stack.isEmpty()) {
                Bridge b = stack.pop();
                if (visited[b.v2]) continue;
                path.add(b);
                visited[b.v2] = true;
                max_flow = Math.min(max_flow, b.capacity);
                if (b.v2 == n+m+1) {
                    path_exist = true;
                    break;
                }
                for (Bridge nb : G[b.v2]) {
                    if (!visited[nb.v2] && nb.capacity > 0) stack.push(nb);
                }
            }

            if (path_exist) {
                Bridge b1 = path.remove();
                while (!path.isEmpty()) {
                    Bridge b2 = path.remove();
                    if(graph[b1.v2][b2.v2] > 0 || graph[b2.v2][b1.v2] > 0) {
                        G[b1.v2].get(b2.v2).capacity -= max_flow;
                        G[b1.v2].get(b2.v2).flow += max_flow;
                        G[b2.v2].get(b1.v2).capacity = G[b1.v2].get(b2.v2).capacity;
                        G[b2.v2].get(b1.v2).flow = -1 * G[b1.v2].get(b2.v2).flow;
                    }
                    b1 = b2;
                }
            }
            else {
                int final_max_flow = 0;
                for(int i = 0; i < n+m+2; i++) final_max_flow += G[i].get(n+m+1).flow;
                System.out.println("Max # of couples: " + final_max_flow);
                break;
            }
        }

    }


    public static void main(String[] args) {
        maxFlow(new int[][]{{0,16,13,0,0,0}, {0,0,10,12,0,0}, {0,4,0,0,14,0}, {0,0,9,0,0,20}, {0,0,0,7,0,4}, {0,0,0,0,0,0}});
        maxCouple(new int[][]{{1,1,1},{1,0,1},{0,1,1}});
        maxCouple(new int[][]{{1,0,1,0},{1,0,0,0},{0,1,0,1}, {1,1,0,1}});
    }
}
