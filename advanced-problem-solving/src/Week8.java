import java.util.*;

public class Week8 {

    // vertex와 weight를 같이 저장하기 위한 클래스
    static class Pair implements Comparable<Pair> {
        int v, weight;
        public Pair(int v, int weight) {
            this.v = v;
            this.weight = weight;
        }

        @Override
        public int compareTo(Pair o) {  // weight로 정렬
            return Integer.compare(this.weight, o.weight);
        }
    }

    static int networkDelayTime(int[][] times, int n, int k) {
        ArrayList[] G = new ArrayList[n+1];
        for(int i = 1; i <= n; i++) G[i] = new ArrayList<Pair>();
        int[] dist = new int[n+1];
        Arrays.fill(dist, Integer.MAX_VALUE);   // dist는 INF로 초기화
        dist[k] = 0;    // src->src 의 weight는 0
        boolean[] visited = new boolean[n+1];   // 방문한 노드는 재방문 하지 않기 위해 방문 여부 저장
        for(int[] t : times) {
            G[t[0]].add(new Pair(t[1], t[2]));  // 그래프 만들기
            if(t[0] == k) dist[t[1]] = t[2];    // dist[i] = G[src][i]
        }

        PriorityQueue<Pair> pq = new PriorityQueue<>();
        pq.add(new Pair(k, 0));     // pq에 src를 넣고 시작
        while(!pq.isEmpty()) {
            Pair v = pq.remove();
            if(visited[v.v]) continue;     // 방문한 적 있으면 재방문 x
            visited[v.v] = true;
            for(Pair nv : (ArrayList<Pair>)G[v.v]) {
                if(dist[nv.v] > v.weight + nv.weight) dist[nv.v] = v.weight + nv.weight;    // 더 짧은 거리로 dist 갱신
                if(!visited[nv.v]) pq.add(new Pair(nv.v, dist[nv.v]));  // 방문 하지 않은 노드는 enqueue
            }
        }
        int delay = -1;
        for(int i = 1; i <= n; i++) delay = Math.max(delay, dist[i]);   // 끝까지 탐색 했을 때의 시간 찾기
        if(delay == Integer.MAX_VALUE) return -1;   // 방문한 적 없는 vertex가 있으면 실패
        return delay;
    }

    static int minimumTicketPrice(int n, int[][] flights, int src, int dst, int k) {
        ArrayList[] G = new ArrayList[n];
        for(int i = 0; i < n; i++) G[i] = new ArrayList<Pair>();
        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);       // dist는 INF로 초기화
        dist[src] = 0;      // src->src 의 weight는 0
        for(int[] f : flights) {
            G[f[0]].add(new Pair(f[1], f[2]));      // 그래프 만들기
            if(f[0] == src) dist[f[1]] = f[2];      // dist[i] = G[src][i]
        }
        int[] cnt = new int[n];     // src->i 사이의 경유 횟수 저장
        cnt[src] = -1;
        int[] min = new int[k+1];   // 인덱스는 경유 횟수를 의미, 값은 해당 경유 횟수에서 최소 표 가격
        Arrays.fill(min, Integer.MAX_VALUE);
        for(int i = 1; i < n; i++) {
            for(int u = 0; u < G.length; u++) { // u는 vertex, dist[u]는 src에서 u까지 shortest path
                for(Pair p : (ArrayList<Pair>)G[u]) {   // u와 연결된 vertex p, p.weight는 u에서 p까지 weight
                    if(dist[p.v] > dist[u] + p.weight) {
                        cnt[p.v] = cnt[u]+1;            // 경유 횟수 증가 시키기
                        dist[p.v] = dist[u] + p.weight; // 더 싼 가격으로 dist 갱신
                    }
                }
                if(cnt[dst] > k) break;     // 최대 경유 횟수보다 많이 경유하는 경우는 탐색 x
                min[cnt[dst]] = Math.min(min[cnt[dst]], dist[dst]);     // src->dst 사이의 해당 경유 횟수에서 최소 표 가격 갱신
            }
        }
        int answer = Integer.MAX_VALUE;
        for(int i = 0; i <= k; i++) answer = Math.min(answer, min[i]);  // k 이하의 경유 횟수 중 가장 싼 가격
        return answer;
    }

    static int[] orderEnrollCourse(int numCourses, int[][] prerequisites) {
        int[] inDegree = new int[numCourses];
        ArrayList[] G = new ArrayList[numCourses];
        for(int i = 0; i < numCourses; i++) G[i] = new ArrayList<Integer>();
        for(int[] p : prerequisites) {
            G[p[1]].add(p[0]);  // 그래프 만들기
            inDegree[p[0]]++;   // 각 vertex의 in-degree 저장
        }
        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[numCourses];    // 하나의 신청 순서만 정하면 되므로 방문한 노드는 재방문 x
        while(queue.size() < numCourses) {
            for(int i = 0; i < numCourses; i++) {
                if(inDegree[i] == 0 && !visited[i]) {                       // in-degree가 0이면
                    queue.add(i);                                           // enqueue 하고
                    visited[i] = true;
                    for(int v : (ArrayList<Integer>)G[i]) inDegree[v]--;    // 연결된 vertex들의 degree 감하기
                }
            }
        }
        int[] answer = new int[numCourses];
        for(int i = 0; i < numCourses; i++) answer[i] = queue.remove();     // queue를 array로 변환
        return answer;
    }

    public static void main(String[] args) {
//        System.out.println(networkDelayTime(new int[][]{{2,1,1},{2,3,1},{3,4,1}}, 4, 2));
//        System.out.println(networkDelayTime(new int[][]{{1,2,1}}, 2, 1));
//        System.out.println(networkDelayTime(new int[][]{{1,2,1}}, 2, 2));
//        System.out.println(minimumTicketPrice(4, new int[][]{{0,1,100},{1,2,100},{2,0,100},{1,3,600},{2,3,200}}, 0, 3, 1));
//        System.out.println(minimumTicketPrice(3, new int[][]{{0,1,100},{1,2,100},{0,2,500}}, 0, 2, 1));
//        System.out.println(minimumTicketPrice(3, new int[][]{{0,1,100},{1,2,100},{0,2,500}}, 0, 2, 0));
        int[] answer = orderEnrollCourse(2,  new int[][]{{1,0}});
        int[] answer1 = orderEnrollCourse(4,  new int[][]{{1,0},{2,0},{3,1},{3,2}});
        System.out.println(Arrays.toString(answer));
        System.out.println(Arrays.toString(answer1));
    }
}
