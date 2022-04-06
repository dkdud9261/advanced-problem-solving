import java.util.Arrays;
import java.util.Stack;

public class Week5 {

    static class TreeNode {
        int value;
        TreeNode left, right;
        TreeNode root;

        public TreeNode(int value) {
            this.value = value;
            this.left = null;
            this.right = null;
        }
    }

    public static TreeNode buildBinaryTree(TreeNode[] root) {
        TreeNode myRoot = null, temp = null;
        for(int i = 0; i < root.length; i++) {
            if(i == 0) {
                myRoot = root[i];
                temp = myRoot;
            }
            else {
                temp.left = root[i++];
                if(i < root.length) {
                    temp.right = root[i];
                    if(temp.left == null )temp = temp.right;
                    else temp = temp.left;
                }
            }
        }

        return myRoot;
    }

    public static void preorderWithStack(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while(!stack.isEmpty()) {
            root = stack.pop();
            System.out.print(root.value + " ");
            if(root.right != null) stack.push(root.right);
            if(root.left != null) stack.push(root.left);
        }
    }

    public static void inorderWithStack(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();
        while(true) {
            for (; root != null; root = root.left) stack.push(root);
            if(stack.isEmpty()) break;
            root = stack.pop();
            System.out.print(root.value + " ");
            root = root.right;
        }
    }

    public static void postorderWithStack(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();

    }

    public static void traverse(TreeNode[] root) {
        TreeNode myRoot = buildBinaryTree(root);
        preorderWithStack(myRoot);
        System.out.println();
        inorderWithStack(myRoot);
    }

    public static void main(String[] args) {
        TreeNode[] root = new TreeNode[]{new TreeNode(1), null, new TreeNode(2), new TreeNode(3)};
        traverse(root);
    }
}
