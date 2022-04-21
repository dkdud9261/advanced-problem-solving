/*
    - 재귀함수 : 더 이상 호출할 수 없을 때까지 subroutine으로 스스로를 호출하는 함수
        recurrence relation : problem의 결과와 subproblem 사이의 관계
        base case : 더 이상 재귀호출을 할 수 없어 바로 답을 계산하는 경우
        base case에 도달할 때까지 recurrence relation에 따라 함수 스스로를 호출하도록 구현한다.

    - Memorization : 함수 호출의 결과를 cache에 저장하고 같은 입력에 대하여 cache에 저장된 값을 반환함으로써 실행속도를 줄인다.
                같은 입력에 대하여 여러번 계산하지 않고 값을 재사용한다.
                fibonacci를 그냥 하면 O(2^n), memorization하면 O(n) - 이진트리로 그려보면 알 수 있다.

    - Space Complexity : 함수 호출의 반환 주소(returning address), 함수에 전달되는 파라미터, 함수 내의 지역 변수

    - Tail Recursion : 재귀 호출이 그 함수 내에서 마지막 instruction이 되도록 하며, 함수 내에서 재귀 호출은 단 한번만 한다.
                스택에서 고정된 공간을 재사용 하기 때문에 재귀 호출을 하는 동안 stack overhead가 쌓이는 것을 피할 수 있다.

    - Divide-and-conquer : subproblem이 바로 해결될 수 있을 만큼 단순해질 때까지 같거나 비슷한 subproblem으로 겹치지 않게 나눈다.
                        하나의 작은 subproblem을 가지는 recursion과 달리 둘 이상의 subproblem으로 나누어진다.
                        divide -> conquer(재귀적으로 각 subproblem 해결) -> combine
                        Merge Sort : O(1)을 N번 반복 => O(N)

    - Dynamic Programming : 체계적이고 효율적으로 문제를 풀기 위한 모든 가능한 solution을 탐색한다.
                        overlapping subproblem으로 나눠지며, 각 subproblem은 여러번 재사용된다.
                        optimal substructure이다.
                        각 subproblem은 한번만 풀고 table에 저장한다.(time-memory trade off
                        top-down은 재귀 함수와 hash map(memorization)으로, bottom-up은 반복문과 배열로 구현한다.
                        optimal value를 구하거나 과거의 결과가 현재에 영향을 줄 때 사용한다.
                        framework = a function or data structure(array) + a recurrence relation + base cases
                        1. 재귀적 특성을 이용하여 반복하기 - 전 값에서 연산해
                        2. state transition by inaction - 전 값에서 연산 하지마
                        3. state reduction - extra boolean state 변수 사용 등
                        4. counting DP - base case를 0으로 set하지 않는다.
                        5. pathing problems on matrix

    - DFS : postorder(left-right-root), preorder(root-left-right), inorder(left-root-right)
    - BFS : level-order
    - traverse : recursive solution과 iterative solution 모두 O(N) => 트리가 깊어지면 recursion의 경우 overflow 주의
    - solve tree problems recursively : top-down(preorder traversal), bottom-up(postorder traversal)
    - Deletion in a BST : No child - 그냥 삭제 / one child - child랑 자리 바꾸고 삭제 / two child - 오른쪽 subtree에서 가장 왼쪽 노드랑 바꾸고 삭제

    - Disjoint set : find - 해당 vertex의 root 노드를 찾는 것 / union - 두 노드의 root 노드를 같게 만들어 주는 것
 */

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Midterm {

    static String reverse(String str) {
        if(str.equals("")) return "";
        return reverse(str.substring(1)) + str.charAt(0);
    }

    static class Node {
        int value;
        Node next;
        Node(int val) {
            this.value = val;
            this.next = null;
        }
    }

    static void swapNodes(int[] items) {
        Node head = null, tail = null;
        for(int i : items) {
            Node node = new Node(i);
            if(head == null) {
                head = node;
            }
            else {
                tail.next = node;
            }
            tail = node;
        }
        head = swap(head);
        for(Node temp = head; temp != null; temp = temp.next) System.out.println(temp.value);
    }

    static Node swap(Node head) {
        if(head == null) return null;
        Node next = head.next;
        Node nextNext = next.next;
        next.next = head;
        head.next = swap(nextNext);
        return next;
    }

    static int pascal(int i, int j) {
        if(j == 1 || i == j) return 1;
        return pascal(i-1,j-1) + pascal(i-1,j);
    }

    static int pascal1(int i, int j, int[][] store) {
        if(j == 1 || i == j) {
            store[i][j] = 1;
            return 1;
        }
        if(store[i-1][j-1] == 0) store[i-1][j-1] = pascal1(i-1,j-1,store);
        if(store[i-1][j] == 0) store[i-1][j] = pascal1(i-1,j,store);
        store[i][j] = store[i-1][j-1] + store[i-1][j];
        return store[i][j];
    }

    static int[] recurrenceRelation(int rowIndex) {
        int[] answer = new int[rowIndex+1];
        int[][] store = new int[rowIndex+2][rowIndex+2];
        for(int i = 1; i <= rowIndex+1; i++) answer[i-1] = pascal1(rowIndex+1, i, store);
        return answer;
    }

    static int fib(int n, int[] store) {
        if(n == 0 || n == 1) {
            store[n] = n;
            return n;
        }
        if(store[n-1] == 0) store[n-1] = fib(n-1, store);
        if(store[n-2] == 0) store[n-2] = fib(n-2, store);
        store[n] = store[n-1] + store[n-2];
        return store[n];
    }

    static int climbingStairs(int n, int[] store) {
        if(n <= 2) {
            store[n] = n;
            return store[n];
        }
        if(store[n-1] == 0) store[n-1] = climbingStairs(n-1, store);
        if(store[n-2] == 0) store[n-2] = climbingStairs(n-2, store);
        store[n] = store[n-1] + store[n-2];
        return store[n];
    }

    static double pow(double x, int n) {
        if(n < 0) return 1/helper(x,n,1);
        return helper(x,n,1);
    }

    static double helper(double x, int n, double acc) {
        if(n == 0) return acc;
        if(n < 0) return helper(x, n+1, acc*x);
        return helper(x, n-1, acc*x);
    }

    static void permutation(int[] arr) {
        boolean[] visited = new boolean[arr.length];
        permutation1(arr, new int[arr.length], 0, visited);
    }

    static void permutation1(int[] arr, int[] sub, int r, boolean[] visited) {
        if(r == arr.length) {
            System.out.println(Arrays.toString(sub));
            return;
        }
        for(int i = 0; i < arr.length; i++) {
            if(!visited[i]) {
                visited[i] = true;
                sub[r] = arr[i];
                permutation1(arr, sub, r+1, visited);
                visited[i] = false;
            }
        }
    }

    static int houseRobber(int[] nums) {
        if(nums.length == 1) return nums[0];
        if(nums.length == 2) return Math.max(nums[0], nums[1]);

        int[] money = new int[nums.length];
        money[0] = nums[0];
        money[1] = Math.max(nums[0], nums[1]);
        for(int i = 2; i < nums.length; i++) nums[i] = Math.max(nums[i-2]+nums[i], nums[i-1]);
        return nums[nums.length-1];
    }

    static int minCostClimbingStairs(int[] cost) {
        if(cost.length == 1) return cost[0];
        if(cost.length == 2) return Math.min(cost[0], cost[1]);
        int[] costs = new int[cost.length];
        costs[0] = cost[0];
        costs[1] = cost[1];
        for(int i = 2; i < cost.length; i++) costs[i] = Math.min(costs[i-2], costs[i-1])+cost[i];
        return Math.min(costs[costs.length-1], costs[costs.length-2]);
    }

    static int levenshteinDist(String str1, String str2) {
        int l1 = str1.length();
        int l2 = str2.length();
        int[][] arr = new int[l1+1][l2+1];
        for(int i = 0; i <= l1; i++) arr[i][0] = i;
        for(int i = 0; i <= l2; i++) arr[0][i] = i;
        for(int i = 1; i <= l1; i++) {
            for(int j = 1; j <= l2; j++) {
                if(str1.charAt(i-1) == str2.charAt(j-1)) arr[i][j] = arr[i-1][j-1];
                else arr[i][j] = Math.min(arr[i][j-1], Math.min(arr[i-1][j-1], arr[i-1][j]))+1;
            }
        }
        for(int i = 0; i <= l1; i++) System.out.println(Arrays.toString(arr[i]));

        return arr[l1][l2];
    }

    static int longestIncreasing(int[] nums) {
        int[] dp = new int[nums.length];
        dp[0] = 1;
        for(int i = 1; i < nums.length; i++) {
            if(nums[i-1] < nums[i]) dp[i] = dp[i-1]+1;
            else dp[i] = dp[i-1];
        }
        return dp[nums.length-1];
    }

    static int paintFence(int n, int k) {
        if(n == 1) return k;
        if(n == 2) return k*k;
        int[] dp = new int[n+1];
        dp[1] = k;
        dp[2] = k*k;
        // 전과 같은 색깔로 칠하는 경우 = 전전이랑은 달라야 해 = dp[i-2]*(k-1)
        // 전과 다른 색깔로 칠하는 경우 = dp[i-1]*(k-1)
        for(int i = 3; i <= n; i++) dp[i] = (k-1) * (dp[i-2] + dp[i-1]);
        return dp[n];
    }

    static int findPath(int m, int n) {
        int[][] arr = new int[m][n];
        for(int i = 0; i < m; i++) arr[i][0] = 1;
        for(int i = 0; i < n; i++) arr[0][i] = 1;
        for(int i = 1; i < m; i++) {
            for(int j = 1; j < n; j++) arr[i][j] = arr[i-1][j] + arr[i][j-1];
        }
        return arr[m-1][n-1];
    }

    static int findPath1(int[][] obstacle) {
        int m = obstacle.length;
        int n = obstacle[0].length;
        int[][] arr = new int[m][n];
        for(int i = 0; i < m; i++) {
            if(obstacle[i][0] == 0) arr[i][0] = 1;
        }
        for(int i = 0; i < n; i++) {
            if(obstacle[0][i] == 0) arr[0][i] = 1;
        }
        for(int i = 1; i < m; i++) {
            for(int j = 1; j < n; j++) {
                if(obstacle[i][j] == 0) arr[i][j] = arr[i-1][j] + arr[i][j-1];
            }
        }
        return arr[m-1][n-1];
    }

    static class TreeNode {
        int value;
        TreeNode left, right;
        TreeNode(int value) {
            this.value = value;
            this.left = null;
            this.right = null;
        }
    }

    static TreeNode buildBinaryTree(int[] items) {
        Queue<TreeNode> queue = new LinkedList<>();
        TreeNode root = new TreeNode(items[0]);
        queue.add(root);
        int i = 0;
        while(!queue.isEmpty()) {
            TreeNode temp = queue.remove();
            if(i+1 < items.length && temp.left == null) {
                if(items[++i] != -1) {
                    temp.left = new TreeNode(items[i]);
                    queue.add(temp.left);
                }
            }
            if(i+1 < items.length && temp.right == null) {
                if(items[++i] != -1) {
                    temp.right = new TreeNode(items[i]);
                    queue.add(temp.right);
                }
            }
        }
        return root;
    }

    static void bfs(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while(!queue.isEmpty()) {
            TreeNode temp = queue.remove();
            System.out.print(temp.value + " ");
            if(temp.left != null) queue.add(temp.left);
            if(temp.right != null) queue.add(temp.right);
        }
        System.out.println();
    }

    static void postorder(TreeNode root) {
        if(root!=null) {
            postorder(root.left);
            postorder(root.right);
            System.out.print(root.value + " ");
        }
    }

    static void preorder(TreeNode root) {
        if(root!=null) {
            System.out.print(root.value + " ");
            preorder(root.left);
            preorder(root.right);
        }
    }

    static void inorder(TreeNode root) {
        if(root!=null) {
            inorder(root.left);
            System.out.print(root.value + " ");
            inorder(root.right);
        }
    }

    // preorder : root -> left -> right
    // root를 먼저 push하고 pop해서 출력
    // right를 push 후 left를 push => left를 먼저 pop해서 출력 후 right를 출력하게 됨
    public void preorderWithStack(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while(!stack.isEmpty()) {
            root = stack.pop();
            System.out.print(root.value + " ");
            if(root.right != null) stack.push(root.right);
            if(root.left != null) stack.push(root.left);
        }
    }

    // inorder : left -> root -> right
    // root의 left들을 먼저 push 후 꺼내서 출력
    // 그 후 각 노드의 right 탐색
    public void inorderWithStack(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();
        while(true) {
            for (; root != null; root = root.left) stack.push(root);
            if(stack.isEmpty()) break;
            root = stack.pop();
            System.out.print(root.value + " ");
            root = root.right;
        }
    }

    // postorder : left -> right -> root
    // stack(스택1)을 이용해서 탐색, 결과를 traverse(스택2)에 저장 후 한번에 출력
    public void postorderWithStack(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();
        Stack<TreeNode> traverse = new Stack<>();
        while(root != null || !stack.isEmpty()) {
            if(root != null) {
                stack.push(root);
                traverse.push(root);
                root = root.right;
            }
            else {
                root = stack.pop();
                root = root.left;
            }
        }
        while(!traverse.isEmpty()) System.out.print(traverse.pop().value + " ");
    }

    public static void main(String[] args) {
//        System.out.println(reverse("hello"));
//        swapNodes(new int[]{1,2,3,4});
//        System.out.println(pascal(5,3));
//        System.out.println(pascal1(5,3, new int[5][5]));
//        System.out.println(Arrays.toString(recurrenceRelation(3)));
//        int[] store = new int[5];
//        System.out.println(fib(4, store));
//        System.out.println(climbingStairs(3, store));
//        System.out.println(Arrays.toString(store));
//        System.out.println(pow(2.00000, 10));
//        System.out.println(pow(2.1, 3));
//        System.out.println(pow(2.00000, -2));
//        permutation(new int[]{2,3,5,7});
//        System.out.println(houseRobber(new int[]{9,1,1,1}));
//        System.out.println(minCostClimbingStairs(new int[]{1,100,1,1,1,100,1,1,100,1}));
//        System.out.println(levenshteinDist("Sunday", "Saturday"));
//        System.out.println(longestIncreasing(new int[]{10,9,2,5,3,7,101,18}));
//        System.out.println(longestIncreasing(new int[]{0,1,0,3,2,3}));
//        System.out.println(longestIncreasing(new int[]{7,7,7,7,7,7}));
//        System.out.println(paintFence(3,2));
//        System.out.println(paintFence(1,1));
//        System.out.println(paintFence(7,2));
//        System.out.println(findPath(3,7));
//        System.out.println(findPath(3,2));
//        System.out.println(findPath1(new int[][]{{0,0,0},{0,1,0},{0,0,0}}));
//        System.out.println(findPath1(new int[][]{{0,1},{0,0}}));
        TreeNode root = buildBinaryTree(new int[]{1,2,3,4,5});
        bfs(root);
        postorder(root);
        System.out.println();
        preorder(root);
        System.out.println();
        inorder(root);
    }
}
