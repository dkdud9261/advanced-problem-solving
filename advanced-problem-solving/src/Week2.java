import java.util.Arrays;

public class Week2 {

    // 연결리스트 노드 클래스
    public static class Node {
        private int data;   // 노드의 값
        private Node link;  // 다음 노드를 가리키는 포인터

        public Node(int data) {
            this.data = data;
            this.link = null;
        }

        public int getData() {
            return data;
        }

        public Node getLink() { return link; }

        public void setLink(Node link) {
            this.link = link;
        }
    }

    public static void main(String[] args) {
        System.out.println(printReverse("hello"));
        for(Node temp = swapInPairs(new int[]{}); temp != null; temp = temp.getLink()) System.out.print(temp.getData() + " ");
        System.out.println();
        System.out.println(pascalTriangle1(5, 3));
        pascalTriangle2(3);
        System.out.println(climbStairs(2));
        System.out.println(pow(2.00000, 10));
        System.out.println(pow(2.10000, 3));
        System.out.println(pow(2.00000, -2));
        generatePermutation(new int[]{2, 3, 5, 7});
    }

    // 1.
    // 파라미터 : 문자열
    // 반환값 : 거꾸로 만든 문자열
    static String printReverse(String str) {
        if(str.equals("")) return str;                         // 문자열이 끝나면 끝
        return printReverse(str.substring(1)) + str.charAt(0); // 문자열의 첫번째 문자를 맨 뒤로 보내며 재귀 호출
    }

    // 2.
    // 파라미터 : 정수 배열
    // 반환값 : 두 노드씩 교환한 정수 연결리스트의 헤드 노드
    public static Node swapInPairs(int[] list) {
        Node head = null;                       // head 노드를 가리킴
        Node tail = null;                       // tail(마지막) 노드를 가리킴

        // 연결리스트 마지막에 노드 삽입
        for(int i = 0; i < list.length; i++) {
            Node newNode = new Node(list[i]);   // 새 노드 선언
            if(head == null) {                  // 첫 노드는 head로
                head = newNode;
                tail = head;
            }
            else {                              // 노드를 가장 마지막에 삽입
                tail.setLink(newNode);
                tail = newNode;
            }
        }

        // 재귀 호출로 두 노드씩 교환
        head = swapPairs(head);

        return head;
    }

    // 파라미터 : 연결리스트의 헤드 노드
    // 반환값 : 교환 후의 새로운 헤드 노드
    public static Node swapPairs(Node head) {
        if(head == null || head.getLink() == null) return head; // 연결리스트가 끝나면 끝

        Node next = head.getLink();                             // head가 업데이트되기 전에 head.next와
        Node next_next = next.getLink();                        // head.next.next를 미리 저장해둠

        head.getLink().setLink(head);                           // 기존 head.next가 head를 가리키게 함
        head.setLink(swapPairs(next_next));                     // 기존 head가 head.next.next의 재귀 호출 결과를 가리키게 함

        return next;                                            // 기존 head.next가 새로운 head가 됨
    }

    // 연결리스트를 출력하는 함수
    public static void printList(Node head) {
        Node temp = null;
        System.out.print("[");
        for(temp = head; temp.getLink() != null; temp = temp.getLink()) System.out.print(temp.getData() + ",");
        System.out.print(temp.getData() + "]");
    }

    // 3.
    // 파라미터 : 파스칼의 삼각형에서 값을 구할 열과 행의 인덱스
    // 반환값 : 파스칼의 삼각형에서 해당 인덱스의 값
    public static int pascalTriangle1(int i, int j) {
        if(j == 1 || i == j) return 1;
        return pascalTriangle1(i-1, j-1) + pascalTriangle1(i-1, j);
    }

    // 4.
    // 파라미터 : 파스칼의 삼각형에서 열을 구할 인덱스
    // 반환값 : 해당 인덱스의 열
    public static int[] pascalTriangle2(int rowIndex) {
        int[] relation = new int[rowIndex+1];                                                       // 답을 저장할 배열
        int[][] pascalTriangle = new int[rowIndex+1][rowIndex+1];                                   // Duplicate Calculations Problem을 방지하기 위해 값을 저장할 이차원 배열
        for(int i = 0; i <= rowIndex; i++) relation[i] = getPascalVal(rowIndex, i, pascalTriangle); // 구하고자 하는 열의 값을 하나씩 구함
        return relation;
    }

    // 파라미터 : 값을 구할 열과 행의 인덱스, 값을 저장할 배열
    // 반환값 : 해당 인덱스의 값
    public static int getPascalVal(int i, int j, int[][] pascalTriangle) {
        if(j == 0 || i == j) pascalTriangle[i][j] = 1;                                                                       // 양 옆 가장자리의 값은 1
        else {
            if (pascalTriangle[i - 1][j - 1] == 0) pascalTriangle[i-1][j-1] = getPascalVal(i-1, j-1, pascalTriangle);   // (i-1,j-1)의 값이 배열에 없으면 재귀호출로 계산해서 저장
            if (pascalTriangle[i - 1][j] == 0) pascalTriangle[i-1][j] = getPascalVal(i-1, j, pascalTriangle);              // (i-1,j)의 값이 배열에 없으면 재귀호출로 계산해서 저장
            pascalTriangle[i][j] = pascalTriangle[i-1][j-1] + pascalTriangle[i-1][j];                                         // (i,j)의 값 구해서 저장 F(i,j) = F(i-1,j-1) + F(i-1,j)
        }
        return pascalTriangle[i][j];
    }

    // 리스트를 출력하는 함수
    public static void printList1(int[] list) {
        System.out.println(Arrays.toString(list));
    }

    // 5.
    // 파라미터 : 계단의 수
    // 반환값 : 한 칸 또는 두 칸씩 전체 계단을 오르는 횟수
    public static int climbStairs(int n) {
        if(n == 1) return 1;                                // n이 1이면 1
        if(n == 2) return 2;                                // n이 2이면 2
        return climbStairs(n-2) + climbStairs(n-1);   // F(n) = F(n-2) + F(n-1)
    }

    // 6.
    // 파라미터 : 밑 x, 지수 n
    // 반환값 : x^n
    public static double pow(double x, int n) {
        if(n >= 0) return helper(x, n, 1);  // n이 양수면 x^(|n|)
        return 1 / helper(x, n, 1);         // n이 음수면 1/x^(|n|)
    }

    // 파라미터 : 밑 x, 지수 n, 누적 계산 값을 저장할 acc
    // 반환값 : x^(|n|)
    public static double helper(double x, int n, double acc) {
        if(x == 1 || n == 0) return acc;                    // 1의 n제곱과 x의 0제곱은 1
        if(n > 0) return helper(x, n-1, acc * x);   // 계산한 값을 마지막 파라미터로 전달, n이 양수면 n을 줄여가면서 계산 횟수 세기
        return helper(x, n+1, acc * x);             // n이 음수면 n을 증가시키면서 계산 횟수 세기
    }

    // 7.
    // 파라미터 : 정수 배열
    // 반환값 없이 바로 출력하도록 작성함
    public static void generatePermutation(int[] list) {
        permutation(list, 0, list.length-1);    // 탐색 범위를 지정해주기 위해 파라미터를 추가하여 새로운 함수에서 작성함
    }

    // 파라미터 : 정수 배열, 문자열 시작 위치와 끝 위치
    public static void permutation(int[] list, int start, int end) {
        if (start == end) System.out.println(Arrays.toString(list));    // 모든 문자를 탐색했으면 출력
        for (int i = start; i <= end; i++) {
            swap(list, start, i);                                       // i번째 문자를 맨 앞에 놓기
            permutation(list, start + 1, end);                     // i번째 문자로 시작하는 모든 문자열 출력
            swap(list, start, i);                                       // 맨 앞에 놓았던 i번째 문자를 제자리로
        }
    }

    // 배열에서 두 인덱스의 값을 변경
    public static void swap(int[] list, int i, int j) {
        int temp = list[i];
        list[i] = list[j];
        list[j] = temp;
    }
}
