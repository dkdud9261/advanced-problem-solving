public class Week3 {

    public static void main(String[] args) {
        System.out.println(thief(new int[]{1,2,3,1}));
        System.out.println(thief(new int[]{2,7,9,3,1}));
        System.out.println(climbStairs(new int[]{10,15,20}));
        System.out.println(climbStairs(new int[]{1,100,1,1,1,100,1,1,100,1}));
        System.out.println(LevenshteinDistance("Carthorse", "Orchestra"));
        System.out.println(LevenshteinDistance("Saturday", "Sunday"));
    }

    public static int thief(int[] nums) {
        int l = nums.length;
        if(l == 1) return nums[0];                      // 집이 1채면 그 집에 있는 돈의 금액 반환
        if(l == 2) return Math.max(nums[0], nums[1]);   // 집이 2채면 두 집 중 더 돈이 많은 집의 금액 반환
        int[] steal = new int[l];               // i개의 집에서 도둑질 할 수 있는 최대 금액
        steal[0] = nums[0];                     // 집이 한 채면 그 집 돈의 금액
        steal[1] = Math.max(nums[0], nums[1]);  // 집이 두 채면 두 집 중 더 돈이 많은 집의 금액
        for(int i = 2; i < l; i++) steal[i] = Math.max(steal[i-1], steal[i-2]+nums[i]); // F[i] = max(F[i-1], F[i-2]+nums[i])
        return steal[l-1];  // 집이 l채일 때 도둑질 할 수 있는 최대 금액 반환
    }

    public static int climbStairs(int[] cost) {
        int l = cost.length;
        if(l == 1) return cost[0];                      // 계단이 1개면 그 칸의 비용 반환
        if(l == 2) return Math.min(cost[0], cost[1]);   // 계단이 2개면 둘 중 적은 비용 반환
        int[] climb = new int[l];   // i번째 계단에 오를 수 있는 최소 비용
        climb[0] = cost[0];         // 0번째 계단에서 시작 => 0번째 계단의 비용
        climb[1] = cost[1];         // 1번째 계단에서 시작 => 1번째 계단의 비용
        for(int i = 2; i < l; i++) climb[i] = Math.min(climb[i-1], climb[i-2]) + cost[i];   // F[i] = min(F[i-1],F[i-2)) + cost[i]
        return Math.min(climb[l-2], climb[l-1]);    // i개의 계단을 오를 때 최소 비용
    }

    public static int LevenshteinDistance(String str1, String str2) {
        int l1 = str1.length();
        int l2 = str2.length();
        int[][] arr = new int[l1+1][l2+1];
        for(int i = 0; i <= l1; i++) arr[i][0] = i;     // 0열 초기화
        for(int i = 1; i <= l2; i++) arr[0][i] = i;     // 0행 초기화

        int[] dr = {0, -1, -1};     // 왼쪽, 대각선, 위의 행 좌표
        int[] dc = {-1, -1, 0};     // 왼쪽, 대각선, 위의 열 좌표
        for(int i = 1; i <= l1; i++) {
            for(int j = 1; j <= l2; j++) {
                if(str1.charAt(i-1) == str2.charAt(j-1)) arr[i][j] = arr[i-1][j-1];     // 비교하는 문자가 같은 경우 대각선 위의 값을 그대로
                else {
                    int min = 100;
                    for (int k = 0; k < 3; k++) min = Math.min(min, arr[i + dr[k]][j + dc[k]]);
                    arr[i][j] = min + 1;    // 다를 경우 (왼쪽, 대각선, 위 중 최솟값) + 1
                }
            }
        }

        return arr[l1][l2];
    }

}
