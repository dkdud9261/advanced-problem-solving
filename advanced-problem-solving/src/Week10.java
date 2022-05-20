import java.util.Arrays;

public class Week10 {

    static class MyHashSet {

        // 트리의 노드, key == value
        static class TreeNode {
            int value;
            TreeNode left, right;

            public TreeNode(int value) {
                this.value = value;
                this.left = null;
                this.right = null;
            }
        }

        TreeNode[] set; // 각 bucket을 BST로 구성

        // set 초기화
        public MyHashSet() {
            set = new TreeNode[769];
        }

        int hashFunction(int key) { return key % 769; }

        // input이 배열 형태로 돼있어서 일단 이렇게 해봤습니다 ,,
        void add(int[] keys) {
            for(int key : keys) {
                int hash_value = hashFunction(key);
                if (set[hash_value] == null) set[hash_value] = new TreeNode(key);   // bucket이 null 이면 새로 만들고 삽입
                else {  // bucket이 존재하면 넣을 곳 찾아서 삽입
                    TreeNode node = set[hash_value];
                    while (true) {
                        if (key < node.value) {
                            if (node.left == null) {    // 들어갈 곳 찾으면 삽입
                                node.left = new TreeNode(key);
                                break;
                            }
                            node = node.left;   // 삽입할 key가 더 작으면 왼쪽 서브트리 탐색
                        }
                        else if(key > node.value) {
                            if (node.right == null) {   // 들어갈 곳 찾으면 삽입
                                node.right = new TreeNode(key);
                                break;
                            }
                            node = node.right;  // 삽입할 key가 더 크면 오른쪽 서브트리 탐색
                        }
                        else break;     // 중복된 key이면 삽입 x
                    }
                }
            }
        }

        boolean contains(int[] keys) {
            for(int key : keys) {
                int hash_value = hashFunction(key);
                TreeNode node = set[hash_value];
                while (true) {
                    if(node == null) return false;  // 해당 bucket이나 key가 존재하지 않으면 False 반환
                    if (key < node.value) node = node.left;         // 찾을 key가 작으면 왼쪽 서브트리 탐색
                    else if (key > node.value) node = node.right;   //          크면 오른쪽 서브트리 탐색
                    else break;                                     // 같으면 존재 => True
                }
            }
            return true;
        }

         void remove(int[] keys) {
            for(int key : keys) {
                TreeNode root = set[hashFunction(key)];
                if (root == null) return;
                if(root.value == key && root.right == null && root.left == null) {     // 해당 key의 노드가 유일한 BST의 유일 노드이면 bucket 삭제
                    set[hashFunction(key)] = null;
                    break;
                }
                TreeNode parent = root;
                int direct = 0; // 1이면 지울노드가 부모노드의 왼쪽자녀, 2이면 오른쪽 자녀
                while (root.value != key) {    // 지울 노드 찾기 (부모의 어느쪽 자식인지도 탐색)
                    parent = root;
                    if (root.value > key) {
                        root = root.left;
                        direct = 1;
                    } else {
                        root = root.right;
                        direct = 2;
                    }
                    if (root == null) {    // 찾는 key가 존재하지 않는 경우
                        System.out.println("해당 key가 없습니다");
                        return;
                    }
                }
                if (root.left == null) {
                    if (root.right == null) {  // No Child => 부모의 해당 방향 자식을 null
                        if (direct == 1) parent.left = null;
                        else parent.right = null;
                    } else {  // 오른쪽 child만 존재 => 부모의 해당 방향 자식을 오른쪽 자식으로 연결
                        if (direct == 1) parent.left = root.right;
                        else parent.right = root.right;
                    }
                } else {
                    if (root.right == null) {  // 왼쪽 child만 존재 => 부모의 해당 방향 자식을 왼쪽 자식으로 연결
                        if (direct == 1) parent.left = root.left;
                        else parent.right = root.left;
                    } else {  // Two Children
                        TreeNode temp = root.right;
                        TreeNode tempParent = root;
                        while (temp.left != null) {     // 오른쪽 서브트리의 가장 왼쪽 리프노드 찾기
                            tempParent = temp;
                            temp = temp.left;
                        }
                        root.value = temp.value;  // 찾은 노드의 값을 지울 노드에 대입
                        tempParent.right = null;    // 찾은 리프노드 제거
                    }
                }
            }
        }
    }

    static Boolean[] designHashSet(String[] cmd, int[][] input_val) {
        MyHashSet myHashSet = null;
        Boolean[] output = new Boolean[cmd.length];
        for(int i = 0; i < cmd.length; i++) {
            Boolean out = null;
            switch (cmd[i]) {
                case "MyHashSet":
                    myHashSet = new MyHashSet();
                    break;
                case "add":
                    myHashSet.add(input_val[i]);
                    break;
                case "contains":
                    out = myHashSet.contains(input_val[i]);
                    break;
                case "remove":
                    myHashSet.remove(input_val[i]);
                    break;
            }
            output[i] = out;
        }   // 각 command에 따른 결과값 저장
        return output;
    }

    static class MyHashMap {

        // 트리 노드, key와 value를 각각 저장
        static class TreeNode {
            int key, value;
            TreeNode left, right;

            public TreeNode(int key, int value) {
                this.key = key;
                this.value = value;
                this.left = null;
                this.right = null;
            }
        }

        TreeNode[] map; // 각 bucket을 BST로 구성

        // map 초기화
        public MyHashMap() { map = new TreeNode[769]; }

        int hashFunction(int key) { return key % 769; }

        void put(int key, int value) {
            int hash_value = hashFunction(key);
            if (map[hash_value] == null) map[hash_value] = new TreeNode(key, value);    // bucket이 존재하지 않으면 만들어서 삽입
            else {  // 존재하면 넣을 곳 찾아서 삽입
                TreeNode node = map[hash_value];
                while (true) {
                    if (key < node.key) {
                        if (node.left == null) {
                            node.left = new TreeNode(key, value);   // 넣을 곳 찾으면 삽입
                            return;
                        }
                        node = node.left;   // 넣을 key가 더 작으면 왼쪽 서브트리 탐색
                    }
                    else if(key > node.key) {
                        if (node.right == null) {
                            node.right = new TreeNode(key, value);  // 넣을 곳 찾으면 삽입
                            return;
                        }
                        node = node.right;  // 넣을 key가 더 크면 오른쪽 서브트리 탐색
                    }
                    else {
                        node.value = value; // 이미 존재하면 새 값으로 update
                        return;
                    }
                }
            }
        }

        int get(int key) {
            TreeNode node = map[hashFunction(key)];
            while(node != null) {
                if(node.key < key) node = node.left;        // 찾는 key가 더 작으면 왼쪽 서브트리 탐색
                else if(node.key > key) node = node.right;  //             크면 오른쪽 서브트리 탐색
                else return node.value;                     //             같으면 value 반환
            }
            return -1;  // 못찾았으면 -1 반환
        }

        void remove(int key) {
            TreeNode root = map[hashFunction(key)];
            if (root == null) return;   // 해당 bucket이 존재하지 않으면 종료
            if(root.key == key && root.left == null && root.right == null) {    // 해당 key의 노드가 BST의 유일노드이면 bucket을 삭제
                map[hashFunction(key)] = null;
                return;
            }
            TreeNode parent = root;
            int direct = 0; // 1이면 지울노드가 부모노드의 왼쪽자녀, 2이면 오른쪽 자녀
            while (root.key != key) {    // 지울 노드 찾기 (부모의 어느쪽 자식인지도 탐색)
                parent = root;
                if (root.key > key) {
                    root = root.left;
                    direct = 1;
                } else {
                    root = root.right;
                    direct = 2;
                }
                if (root == null) {    // 찾는 key가 존재하지 않는 경우
                    System.out.println("해당 key가 없습니다");
                    return;
                }
            }
            if (root.left == null) {
                if (root.right == null) {  // No Child => 부모의 해당 방향 자식을 null
                    if (direct == 1) parent.left = null;
                    else parent.right = null;
                } else {  // 오른쪽 child만 존재 => 부모의 해당 방향 자식을 오른쪽 자식으로 연결
                    if (direct == 1) parent.left = root.right;
                    else parent.right = root.right;
                }
            } else {
                if (root.right == null) {  // 왼쪽 child만 존재 => 부모의 해당 방향 자식을 왼쪽 자식으로 연결
                    if (direct == 1) parent.left = root.left;
                    else parent.right = root.left;
                } else {  // Two Children
                    TreeNode temp = root.right;
                    TreeNode tempParent = root;
                    while (temp.left != null) {     // 오른쪽 서브트리의 가장 왼쪽 리프노드 찾기
                        tempParent = temp;
                        temp = temp.left;
                    }
                    root.key = temp.key;
                    root.value = temp.value;  // 찾은 노드의 값을 지울 노드에 대입
                    tempParent.right = null;    // 찾은 리프노드 제거
                }
            }
        }
    }

    static Boolean[] designHashMap(String[] cmd, int[][] input_val) {
        MyHashMap myHashMap = null;
        Boolean[] output = new Boolean[cmd.length];
        for(int i = 0; i < cmd.length; i++) {
            Boolean out = null;
            switch (cmd[i]) {
                case "MyHashMap":
                    myHashMap = new MyHashMap();
                    break;
                case "put":
                    myHashMap.put(input_val[i][0], input_val[i][1]);
                    break;
                case "get":
                    int rs = myHashMap.get(input_val[i][0]);
                    out = rs != -1;
                    break;
                case "remove":
                    myHashMap.remove(input_val[i][0]);
                    break;
            }
            output[i] = out;
        }   // 각 command의 결과값 저장
        return output;
    }

    // 각 자릿수 제곱의 합 반환
    static int squareSum(int n) {
        int sum = 0;
        String[] strings = Integer.toString(n).split("");   // 각 자릿수를 String 배열로 치환
        for(String s : strings) sum += Math.pow(Integer.parseInt(s),2); // 각 자릿수의 제곱의 합 구하기
        return sum;
    }

    static boolean happyNumber(int n) {
        MyHashSet set = new MyHashSet();
        while(!set.contains(new int[]{n})) {    // cycle이 발생하면 종료 후 False 반환
            if(n == 1) return true;             // 1이 되면 happy number => True 반환
            set.add(new int[]{n});              // set에 n을 삽입
            n = squareSum(n);                   // 각 자릿수 제곱의 합으로 n을 update
        }
        return false;                           // happy number가 되지 못하고 cycle 발생 시 False 반환
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(designHashSet(new String[]{"MyHashSet", "add", "add", "contains", "contains", "add", "contains", "remove", "contains"}, new int[][]{{},{1},{2},{1},{3},{2},{2},{2},{2}})));
        System.out.println(Arrays.toString(designHashMap(new String[]{"MyHashMap", "put", "put", "get", "get", "put", "get", "remove", "get"}, new int[][]{{},{1,1},{2,2},{1},{3},{2,1},{2},{2},{2}})));
        System.out.println(happyNumber(7));
        System.out.println(happyNumber(19));
        System.out.println(happyNumber(116));
    }

}
