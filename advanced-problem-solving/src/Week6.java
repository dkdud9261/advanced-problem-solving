public class Week6 {

    public static int find(int a, int[] parent) {
        if(parent[a] == a) return a;
        return parent[a] = find(parent[a], parent);
    }

    public static boolean union(int a, int b, int[] parent) {
        a = find(a, parent);
        b = find(b, parent);
        if(a == b) return false;
        parent[b] = a;
        return true;
    }

    public static int connectedComponent(int n, int[][] edges) {
        int answer = 0;
        int[] parent = new int[n];
        for(int i = 0; i < n; i++) parent[i] = i;
        for(int[] edge : edges) union(edge[0], edge[1], parent);
        for(int i = 0; i < n; i++) {
            if(parent[i] == i) answer++;
        }
        return answer;
    }

    public static boolean checkTree(int n, int[][] edges) {
        int[] parent = new int[n];
        for(int i = 0; i < n; i++) parent[i] = i;
        for(int[] edge : edges) {
            if(!union(edge[0], edge[1], parent)) return false;
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(connectedComponent(5, new int[][]{{0,1},{1,2},{3,4}}));
        System.out.println(connectedComponent(10, new int[][]{{0,1},{0,2},{1,3},{4,8},{5,6},{5,7}}));
        System.out.println(connectedComponent(5, new int[][]{{0,1},{1,2},{2,3},{3,4}}));
        System.out.println(checkTree(5, new int[][]{{0,1},{0,2},{0,3},{1,4}}));
        System.out.println(checkTree(5, new int[][]{{0,1},{1,2},{2,3},{1,3},{1,4}}));
    }
}
