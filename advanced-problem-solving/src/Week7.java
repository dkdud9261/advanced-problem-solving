import java.util.Arrays;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class Week7 {

    static boolean dfs(int n, LinkedList<Integer>[] adj_list, int pos, int dest, boolean[] visited) {
        if(pos == dest) return true;    // 목적지에 도착했으면 return true

        visited[pos] = true;            // 방문한 vertex 표시
        for(int v : adj_list[pos]) {
            if(!visited[v]) return dfs(n, adj_list, v, dest, visited);  // 해당 vertex에 방문하지 않았으면 다음 vertex 방문
        }
        return false;   // 모든 vertex를 탐색했는데도 도착지에 도착하지 못했으면 return false
    }

    static boolean ifPathExists(int n, int[][] edges, int source, int destination) {
        // 1. adjacency list 만들기
        LinkedList<Integer>[] adj_list = new LinkedList[n];
        for(int i = 0; i < n; i++) adj_list[i] = new LinkedList<>();
        for(int[] e : edges) {
            adj_list[e[0]].add(e[1]);
            adj_list[e[1]].add(e[0]);
        }

        // 2. dfs로 출발점과 도착점 사이의 경로가 존재하는지 확인
        boolean[] visited = new boolean[n];
        return dfs(n, adj_list, source, destination, visited);
    }

    static void backTracking(int[][] adj_list, int pos, int dest, LinkedList<Integer> apath, LinkedList<LinkedList<Integer>> path) {
        apath.add(pos);     // 현재 위치를 현재 경로에 저장

        if(pos == dest) {
            path.add(new LinkedList<>(apath));  // 도착점에 도착했을 경우 경로 리스트에 경로 저장
            return;                             // 도착했으므로 탐색 중지
        }

        for(int nv : adj_list[pos]) {           // 다음 vertex가 있을 때까지 탐색
            LinkedList<Integer> temp = new LinkedList<>(apath);     // 지금까지의 현재 경로 백업
            backTracking(adj_list, nv, dest, apath, path);          // 다음 vertex 탐색
            apath = new LinkedList<>(temp);                         // 지금까지의 현재 경로 복구
        }
    }

    static int[][] allPathsSrcToTarget(int[][] adj_list) {
        int n = adj_list.length;
        LinkedList<LinkedList<Integer>> path = new LinkedList<>();
        LinkedList<Integer> apath = new LinkedList<>();
        backTracking(adj_list, 0, n-1, apath, path);    // backtracking으로 모든 경로 탐색 후 path에 저장

        // 결과를 LinkedList에서 이차원 배열로 변환 후 반환
        int[][] answer = new int[path.size()][];
        for(int i = 0; i < path.size(); i++) {
            answer[i] = new int[path.get(i).size()];
            for(int j = 0; j < path.get(i).size(); j++) answer[i][j] = path.get(i).get(j);
        }
        return answer;
    }

    // 맨해튼 거리 구하는 함수
    static int ManhattanDist(int x1, int y1, int x2, int y2) {
        return Math.abs(x1-x2) + Math.abs(y1-y2);
    }

    // 현재 vertex와 그 전 vertex 간의 weight를 저장하는 클래스
    static class Vertex implements Comparable<Vertex> {
        int v;
        int weight;

        public Vertex(int v, int weight) {
            this.v = v;
            this.weight = weight;
        }

        @Override
        public int compareTo(Vertex o) {
            return Integer.compare(this.weight, o.weight);  // weight로 비교
        }
    }

    static int prim(int[][] weight) {
        int n = weight.length;
        boolean[] visited = new boolean[n];
        PriorityQueue<Vertex> pq = new PriorityQueue<>();
        pq.add(new Vertex(0, 0));   // 우선순위 큐에 첫 vertex(0)을 넣고 시작
        int sum = 0;                        // 최소 비용만 구하면 되므로 따로 그래프를 저장하는 대신 비용의 합만 저장
        while(!pq.isEmpty()) {
            Vertex v = pq.remove();         // 큐에서 탐색할 vertex 꺼내기
            if(visited[v.v]) continue;      // 이미 선택된 vertex는 탐색 하지 x
            visited[v.v] = true;            // 선택한 vertex 표시
            sum += v.weight;                // 비용의 합 저장
            for(int nv = 0; nv < n; nv++) {
                if(nv != v.v && !visited[nv]) pq.add(new Vertex(nv, weight[v.v][nv]));
            }
        }
        return sum;     // 최소 비용 반환
    }

    static int minCostToConnectAllPoints(int[][] points) {
        int n = points.length;

        // 각 점들 간 맨해튼 거리를 weight로 adjacency matrix 만들기
        int[][] weight = new int[n][n];
        for(int i = 0; i < n-1; i++) {
            for(int j = i+1; j < n; j++) {
                int dist = ManhattanDist(points[i][0], points[i][1], points[j][0], points[j][1]);
                weight[i][j] = dist;
                weight[j][i] = dist;
            }
        }
        return prim(weight);    // prim 알고리즘으로 최소 비용 구하기
    }

    public static void main(String[] args) {
//        System.out.println(ifPathExists(3, new int[][]{{0,1},{1,2},{2,0}},0,2));
//        System.out.println(ifPathExists(6, new int[][]{{0,1},{0,2},{3,5},{5,4},{4,3}},0,5));
//        allPathsSrcToTarget(new int[][]{{1,2},{3},{3},{}});
//        allPathsSrcToTarget(new int[][]{{4,3,1},{3,2,4},{3},{4},{}});
        minCostToConnectAllPoints(new int[][]{{0,0},{2,2},{3,10},{5,2},{7,0}});
        System.out.println(minCostToConnectAllPoints(new int[][]{{3,12},{-2,5},{-4,1}}));
    }
}
