//import java.util.*;
//
//public class Week5 {
//
//    // 트리노드 클래스
//    // 편의상 멤버 변수를 private로 하지 않고
//    // getter와 setter 없이 조작 가능하도록 했습니다.
//    static class TreeNode {
//        int value;
//        TreeNode left, right;
//
//        public TreeNode(int value) {
//            this.value = value;
//            this.left = null;
//            this.right = null;
//        }
//    }
//
//    public static TreeNode buildBinaryTree(int[] root) {
//        Queue<TreeNode> queue = new LinkedList<>();
//        TreeNode myRoot = new TreeNode(root[0]);
//        queue.add(myRoot);
//        int i = 0;
//        while(!queue.isEmpty()) {
//            TreeNode temp = queue.remove();
//            if (i+1 < root.length && temp.left == null) {
//                if(root[++i] != -1) {
//                    temp.left = new TreeNode(root[i]);
//                    queue.add(temp.left);
//                }
//            }
//            if (i+1 < root.length && temp.right == null) {
//                if(root[++i] != -1) {
//                    temp.right = new TreeNode(root[i]);
//                    queue.add(temp.right);
//                }
//            }
//        }
////        printRecursive(myRoot, 0, "root");
//        return myRoot;
//    }
//
//    public static void printRecursive(TreeNode root, int level, String title) {
//        for(int i = 0; i < level; i++) System.out.print("  ");
//        System.out.print(title + " : " + root.value + "\n");
//        if(root.left != null) printRecursive(root.left, level+1, "left");
//        if(root.right != null) printRecursive(root.right, level+1, "right");
//    }
//
//    // preorder : root -> left -> right
//    // root를 먼저 push하고 pop해서 출력
//    // right를 push 후 left를 push => left를 먼저 pop해서 출력 후 right를 출력하게 됨
//    public static void preorderWithStack(TreeNode root) {
//        Stack<TreeNode> stack = new Stack<>();
//        stack.push(root);
//        while(!stack.isEmpty()) {
//            root = stack.pop();
//            System.out.print(root.value + " ");
//            if(root.right != null) stack.push(root.right);
//            if(root.left != null) stack.push(root.left);
//        }
//    }
//
//    // inorder : left -> root -> right
//    // root의 left들을 먼저 push 후 꺼내서 출력
//    // 그 후 각 노드의 right 탐색
//    public static void inorderWithStack(TreeNode root) {
//        Stack<TreeNode> stack = new Stack<>();
//        while(true) {
//            for (; root != null; root = root.left) stack.push(root);
//            if(stack.isEmpty()) break;
//            root = stack.pop();
//            System.out.print(root.value + " ");
//            root = root.right;
//        }
//    }
//
//    // postorder : left -> right -> root
//    // stack(스택1)을 이용해서 탐색, 결과를 traverse(스택2)에 저장 후 한번에 출력
//    public static void postorderWithStack(TreeNode root) {
//        Stack<TreeNode> stack = new Stack<>();
//        Stack<TreeNode> traverse = new Stack<>();
//        while(root != null || !stack.isEmpty()) {
//            if(root != null) {
//                stack.push(root);
//                traverse.push(root);
//                root = root.right;
//            }
//            else {
//                root = stack.pop();
//                root = root.left;
//            }
//        }
//        while(!traverse.isEmpty()) System.out.print(traverse.pop().value + " ");
//    }
//
//    public static void traverse(int[] root) {
//        TreeNode myRoot = buildBinaryTree(root);
//        System.out.println("1. Preorder");
//        preorderWithStack(myRoot);
//        System.out.println("\n2. Inorder");
//        inorderWithStack(myRoot);
//        System.out.println("\n3. Postorder");
//        postorderWithStack(myRoot);
//        System.out.println();
//    }
//
//    // inorder : left - root - right
//    // postorder : left - right - root
//    // postorder의 마지막 노드를 root로 지정
//    // inorder에서 root보다 왼쪽 인덱스는 왼쪽 노드, 오른쪽 인덱스는 오른쪽 노드
//    // 각각 왼쪽과 오른쪽 서브트리의 탐색 범위를 지정한 후 재귀호출
//    public static TreeNode binaryTreeFromInAndPost(int[] inorder, int[] postorder, int in_start, int in_end, int post_start, int post_end) {
//        if(post_end == -1) return null;
//        TreeNode root = new TreeNode(postorder[post_end]);
//        if(post_start == post_end) {
//            root.left = null;
//            root.right = null;
//        }
//        else {
//            int left = 0;   // 왼쪽 자식의 수
//            for (int i = in_start; i <= in_end; i++) {
//                if (inorder[i] == root.value) break;
//                left++;
//            }
//            root.left = binaryTreeFromInAndPost(inorder, postorder, in_start, in_start+left-1, post_start, post_start + left - 1);
//            root.right = binaryTreeFromInAndPost(inorder, postorder, in_end-left+1, in_end, post_start+left, post_end - 1);
//        }
//        return root;
//    }
//
//    // bfs로 트리 출력, queue 이용
//    public static void bfs(TreeNode root, int n) {
//        int print = 0;  // 마지막 레벨의 자식노드(null)는 출력하지 않기 위한 count
//        if(root == null) return;
//        Queue<TreeNode> queue = new LinkedList<>();
//        queue.add(root);
//        while(!queue.isEmpty()) {
//            TreeNode node = queue.remove();
//            if(node != null) {
//                System.out.print(node.value + " ");
//                print++;
//                if(print == n){     // 마지막 레벨의 리프노드까지 출력하면 끝
//                    System.out.println();
//                    return;
//                }
//                if (node.value != -1) {     // node.value == -1 이면 null인 노드
//                    if (node.left != null) queue.add(node.left);
//                    else queue.add(new TreeNode(-1));
//                    if (node.right != null) queue.add(node.right);
//                    else queue.add(new TreeNode(-1));
//                }
//            }
//        }
//    }
//
//    public static void constructBinaryTree(int[] inorder, int[] postorder) {
//        int l = inorder.length;
//        int n = (int)Math.pow(2,(int) Math.ceil(Math.log(l) / Math.log(2.0)))-1;    // 트리의 레벨 계산
//        bfs(binaryTreeFromInAndPost(inorder, postorder, 0, l-1, 0, l-1), n);
//    }
//
//    // 루트에서 리프까지 모든 path를 탐색해서 sum을 계산
//    // 하나라도 나오면 탐색 중단, return true
//    public static boolean sum(TreeNode root, int val, int target) {
//        if(root == null) {
//            if(val == target) return true;
//            return false;
//        }
//        boolean rs1 = sum(root.left, val + root.value, target);     // 왼쪽 서브트리 탐색
//        if(!rs1) return sum(root.right, val + root.value, target);  // 왼쪽 서브트리에서 target이 안나올 때만 탐색 후 결과 반환
//        return true;    // 왼쪽 서브트리에서 target을 발견하면 return true
//    }
//
//    public static boolean pathSum(int[] root, int targetSum) {
//        TreeNode myRoot = buildBinaryTree(root);    // 트리 생성
//        return sum(myRoot, 0, targetSum);
//    }
//
//    // 각 경우에 따라 왼쪽 서브트리와 오른쪽 서브트리를 재귀호출로 탐색해서 검사함
//    public static boolean validate(TreeNode root) {
//        if(root == null) return true;
//        if(root.left != null) { // left가 null이 아닐 때 : 양쪽 다 검사
//            if(root.left.value < root.value) {  // 왼쪽 서브트리가 유효한 경우
//                if(root.right != null) {    // 오른쪽 서브트리 검사
//                    if(root.value < root.right.value) return validate(root.left) && validate(root.right);   // 양쪽 자식 서브트리 탐색
//                    return false;   // 오른쪽 서브트리가 유효하지 않음
//                }
//                return true;    // 왼쪽 : 유효 / 오른쪽 : null -> 유효
//            }
//            return false;   // 왼쪽 서브트리가 유효하지 않음 => 오른쪽 서브트리 탐색할 필요 없이 false 반환
//        }
//        else {  // left가 null일 때 : right만 검사하면 됨
//            if(root.right != null) {
//                if(root.value < root.right.value) return validate(root.right);  // 오른쪽 자식 서브트리 탐색
//                return false;   // 오른쪽 서브트리가 유효하지 않음
//            }
//            return true;    // 양쪽 다 null이므로 유효함
//        }
//    }
//
//    public static boolean validateBST(int[] root) {
//        TreeNode myRoot = buildBinaryTree(root);    // 트리 생성
//        return validate(myRoot);
//    }
//
//    public static void deleteNodeInBST(int[] root, int key) {
//        TreeNode myRoot = buildBinaryTree(root);
//        TreeNode preserveMyRoot = myRoot;   // myRoot가 가리키는 노드가 바뀌기 때문에 root노드 저장해두기
//        TreeNode parent = myRoot;
//        int direct = 0; // 1이면 지울노드가 부모노드의 왼쪽자녀, 2이면 오른쪽 자녀
//        while(myRoot.value != key) {    // 지울 노드 찾기 (부모의 어느쪽 자식인지도 탐색)
//            parent = myRoot;
//            if(myRoot.value > key) {
//                myRoot = myRoot.left;
//                direct = 1;
//            }
//            else {
//                myRoot = myRoot.right;
//                direct = 2;
//            }
//            if(myRoot == null) {    // 찾는 key가 존재하지 않는 경우
//                System.out.println("해당 key가 없습니다");
//                return;
//            }
//        }
//        if(myRoot.left == null) {
//            if(myRoot.right == null) {  // No Child => 부모의 해당 방향 자식을 null
//                if (direct == 1) parent.left = null;
//                else parent.right = null;
//            }
//            else {  // 오른쪽 child만 존재 => 부모의 해당 방향 자식을 오른쪽 자식으로 연결
//                if(direct == 1)  parent.left = myRoot.right;
//                else parent.right = myRoot.right;
//            }
//        }
//        else {
//            if(myRoot.right == null) {  // 왼쪽 child만 존재 => 부모의 해당 방향 자식을 왼쪽 자식으로 연결
//                if(direct == 1) parent.left = myRoot.left;
//                else parent.right = myRoot.left;
//            }
//            else {  // Two Children
//                TreeNode temp = myRoot.right;
//                TreeNode tempParent = myRoot;
//                while (temp.left != null) {     // 오른쪽 서브트리의 가장 왼쪽 리프노드 찾기
//                    tempParent = temp;
//                    temp = temp.left;
//                }
//                myRoot.value = temp.value;  // 찾은 노드의 값을 지울 노드에 대입
//                tempParent.right = null;    // 찾은 리프노드 제거
//            }
//        }
//        int n = (int)Math.pow(2,(int) Math.ceil(Math.log(root.length) / Math.log(2.0)))-1;
//        bfs(preserveMyRoot, n);
//    }
//
//    public static void main(String[] args) {
////        int[] root = {1,-1,2,3};
////        int[] root = {1,2,3,4,5,6,7};
////        traverse(root);
////        int[] root = {5,4,8,11,-1,13,4,7,2,-1,-1,-1,1};
////        pathSum(root, 22);
////        int[] root = {2,1,3};
////        int[] root = {5,1,4,-1,-1,3,6};
////        System.out.println(validateBST(root));
////        int[] root = {5,3,6,2,4,-1,7};
////        deleteNodeInBST(root, 3);
////        int[] inorder = {4,2,5,1,6,3,7};
////        int[] postorder = {4,5,2,6,7,3,1};
////        constructBinaryTree(inorder, postorder);
//        int[] root = new int[]{5,3,6,2,4,-1,7};
//        deleteNodeInBST(root, 3);
////        deleteNodeInBST(root, 6);
////        int[] inorder = {9,3,15,20,7};
////        int[] postorder = {9, 15, 7, 20, 3};
////        constructBinaryTree(inorder,postorder);
//    }
//}
