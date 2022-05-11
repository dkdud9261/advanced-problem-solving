import java.util.*;

public class Week9 {

    static int minCostToConnectSticks(int[] sticks) {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for(int s : sticks) pq.add(s);  // 막대의 길이를 작은 순서대로 정렬
        int sum = 0;
        while(pq.size() > 1) {  // 작은 것 순서대로 더하기
            int temp = pq.remove() + pq.remove();
            sum += temp;
            pq.add(temp);
        }
        return sum;
    }

    static int[] topKFrequentElements(int[] nums, int k) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for(int n : nums) map.put(n, map.getOrDefault(n, 0) + 1);   // map에 정수와 frequency 저장
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for(int key : map.keySet()) {   // frequency가 k 이상인 정수로 우선순위 큐 구성
            if(map.get(key) >= k) pq.add(key);
        }
        Integer[] answer = pq.toArray(new Integer[0]);  // output 배열 만들기
        return Arrays.stream(answer).mapToInt(i->i).toArray();
    }

    // 트리 노드
    static class TreeNode implements Comparable<TreeNode> {
        char c;
        double freq;
        TreeNode left, right;

        public TreeNode(char c, double freq) {
            this.c = c;
            this.freq = freq;
            this.left = null;
            this.right = null;
        }

        @Override
        public int compareTo(TreeNode o) {
            return Double.compare(this.freq, o.freq);
        }
    }

    // 트리 만들기
    public static TreeNode buildBinaryTree(PriorityQueue<TreeNode> pq) {
        TreeNode root = pq.remove();
        Queue<TreeNode> queue = new LinkedList<>();     // 자식을 붙여주기 위해 탐색할 노드들 저장
        queue.add(root);
        while(!pq.isEmpty()) {
            TreeNode temp = queue.remove();
            TreeNode a = pq.remove();
            TreeNode b = pq.remove();   // 자식 후보
            if(a.freq + b.freq == temp.freq) {      // 내 자식이 맞으면
                temp.right = a;
                temp.left = b;                      // 자식을 붙여주고
                queue.add(a);
                queue.add(b);                       // 탐색할 노드로 넣어주기
            }
            else {                                  // 내 자식이 아니면
                pq.add(a);                          // 남의 자식 후보로 넣어주기
                pq.add(b);
            }
        }
        return root;
    }

    // dfs로 트리 탐색
    static void encoding(TreeNode node, String code, HashMap<Character, String> map) {
        if(node.left == null) {     // 리프 노드면 맵에 넣어주기
            map.put(node.c, code);
            return;
        }
        encoding(node.left, code+"0", map);     // 왼쪽 서브트리 탐색
        encoding(node.right, code+"1", map);    // 오른쪽 서브트리 탐색
    }

    static void HuffmanCoding(String input) {
        String[] seq = input.split(" ");
        PriorityQueue<TreeNode> temp = new PriorityQueue<>();
        for(int i = 0; i < seq.length; i += 2) temp.add(new TreeNode(seq[i].charAt(0), Double.parseDouble(seq[i+1])));  // frequency가 작은 순서대로 준비
        PriorityQueue<TreeNode> pq = new PriorityQueue<>(Collections.reverseOrder());
        char c = 0;
        while(temp.size() > 1) {
            TreeNode a = temp.remove();
            TreeNode b = temp.remove();
            pq.add(a);
            pq.add(b);
            temp.add(new TreeNode(c, a.freq+b.freq));
        }   // 트리를 구성할 모든 노드를 큰 순서대로 준비
        pq.add(temp.remove());
        TreeNode root = buildBinaryTree(pq);    // 이진 트리 만들기
        HashMap<Character, String> map = new HashMap<>();
        encoding(root, "", map);    // 맵에 각 알파벳과 하프만 코드 저장
        Object[] keys = map.keySet().toArray(new Character[0]);
        Arrays.sort(keys);  // 알파벳으로 정렬
        for(Character key : (Character[]) keys) System.out.print(key + " " + map.get(key) + " ");
    }

    public static void main(String[] args) {
//        minCostToConnectSticks(new int[]{2,4,3});
//        minCostToConnectSticks(new int[]{1,8,3,5});
//        minCostToConnectSticks(new int[]{3,7,1,10,8});
//        int[] result;
//        result = topKFrequentElements(new int[]{1,1,1,2,2,3}, 2);
//        System.out.println(Arrays.toString(result));
//        result = topKFrequentElements(new int[]{1,1,1,2,2,2,3,3,4,5,5,5,5}, 3);
//        System.out.println(Arrays.toString(result));
        HuffmanCoding("a 8.17 b 1.49 c 2.78 d 4.25 e 12.70 f 2.23 g 2.02 h 6.09 i 6.97 j 0.15 k 0.77 l 4.03 m 2.41 n 6.75 o 7.51 p 1.93 q 0.10 r 5.99 s 6.33 t 9.06 u 2.76 v 0.98 w 2.36 x 0.15 y 1.97 z 0.07");
    }
}
