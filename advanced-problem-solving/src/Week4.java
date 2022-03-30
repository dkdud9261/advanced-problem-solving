import java.util.Arrays;

public class Week4 {

    static int longestIncreasingSubSeq(int[] nums) {
        int[] dp = new int[nums.length];        // dp[i]는 i번째까지 longest increasing subsequence의 수
        for(int i = 0; i < nums.length; i++) {
            int num = 1;                        // dp[i] = 1로 초기화
            for(int j = 0; j < i; j++) {
                if(nums[j] < nums[i]) num = Math.max(num, dp[j]+1);
            }
            dp[i] = num;                // nums[j]<nums[i]이고 j<i일 때, dp[i] = max(dp[i],dp[j]+1)
        }
        return dp[nums.length-1];       // nums에서 longest increasing subsequence
    }

    static int paintFence(int n, int k) {
        if(n == 1) return k;    // n=1이면 k가지의 방법
        if(n == 2) return k*k;  // n=2이면 k*k가지의 방법

        int[] total_ways = new int[n+1];    // total_ways[i]는 i개 포스트의 울타리를 칠할 수 있는 방법의 수
        total_ways[1] = k;
        total_ways[2] = k*k;
        // F(i) = (k-1)*F(i-1) + (k-1)*F(i-2) = (k-1)*(F(i-1)+F(i-2))
        for (int i = 3; i <= n; i++) total_ways[i] = (k - 1) * (total_ways[i - 1] + total_ways[i - 2]);
        return total_ways[n];   // n개 포스트의 울타리를 칠할 수 있는 방법
    }

    static int uniquePath(int m, int n) {
        int[][] grid = new int[m][n];               // grid[i][j]는 (i,j)까지 도달할 수 있는 경로의 수
        for(int i = 0; i < m; i++) grid[i][0] = 1;  // col이 0인 경우 1로 초기화
        for(int i = 0; i < n; i++) grid[0][i] = 1;  // row가 0인 경우 1로 초기화
        for(int i = 1; i < m; i++) {
            for(int j = 1; j < n; j++) grid[i][j] = grid[i-1][j] + grid[i][j-1];    // F(i,j)=F(i-1,j)+F(i,j-1)
        }
        return grid[m-1][n-1];      // 하단 모서리에 도달할 수 있는 경로의 수
    }

    static int uniquePath2(int[][] obstacleGrid) {
        int r = obstacleGrid.length;
        int c = obstacleGrid[0].length;
        int[][] grid = new int[r][c];       // grid[i][j]는 (i,j)까지 도달할 수 있는 경로의 수
        for(int i = 0; i < r; i++) {
            for(int j = 0; j < c; j++) {
                if(obstacleGrid[i][j] == 0) {                       // 장애물이 없는 곳만 이동 가능
                    if(i == 0 || j == 0) grid[i][j] = 1;            // row나 col이 0인 경우 1로 초기화
                    else grid[i][j] = grid[i-1][j] + grid[i][j-1];  // F(i,j)=F(i-1,j)+F(i,j-1)
                }
            }
        }
        return grid[r-1][c-1];      // 하단 모서리에 도달할 수 있는 경로의 수
    }

    public static void main(String[] args) {
//        longestIncreasingSubSeq(new int[]{10,9,2,5,3,7});
//        longestIncreasingSubSeq(new int[]{10,9,2,5,3,7,101,18});
//        longestIncreasingSubSeq(new int[]{0,1,0,3,2,3});
//        longestIncreasingSubSeq(new int[]{7,7,7,7,7,7,7});

        System.out.println(paintFence(3,2));
        System.out.println(paintFence(1,1));
        System.out.println(paintFence(7,2));

//        System.out.println(uniquePath(3,7));
//        System.out.println(uniquePath(3,2));
//
//        System.out.println(uniquePath2(new int[][]{{0,0,0}, {0,1,0}, {0,0,0}}));
//        System.out.println(uniquePath2(new int[][]{{0,1}, {0,0}}));
    }
}
