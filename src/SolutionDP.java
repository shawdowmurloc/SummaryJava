import com.sun.org.apache.xerces.internal.impl.dv.dtd.XML11NMTOKENDatatypeValidator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;

/**
 * 动态规划问题组合：
 * 1.lc377 组合总数4
 * 2.lc343.整数拆分
 * 3.lc279.完全平方数
 * 4.lc322 零钱兑换
 * 5.lc322 零钱兑换2
 * 6.lc392 判断子序列
 * 7.lc1143 最长公共子序列
 * 8.lc300 最长递增子序列
 * 9.lc516 最长回文子序列
 * 10.lc5 最长回文子串
 * 11.lc64 最小路径和
 * 12.lc62 不同路径
 * 13.lc63 不同路径2
 * 14.lc118 杨辉三角
 * 15.lc119 杨辉三角2
 * 16.lc198 打家劫舍
 * 17.lc213 打家劫舍2
 */
public class SolutionDP {
/**
 * 动态规划问题解题思想：
 * 1.判断一个问题是不是动态规划问题？重叠子问题、最优子结构
 * 2.确定其状态及其含义
 * 3.构造状态转移方程，枚举若干子状态，然后尝试用这些子状态构造出未知状态的解，就可以轻松得到状态转移方程，
 * 4.为这些状态加上备忘录（自顶向下）或者dp table（自底向上）
 */

    /**1.
     * lc377 组合总数4
     * @param nums 数组
     * @param target 目标整数
     * @return
     */
    public int combinationSum4(int[] nums, int target){
        //构造dp table，dp[i]表示当数字为i时组合的总数
        int[] dp = new int[target+1];
        dp[0] = 1;//初始化
        for(int i=1;i<=target;i++){
            //计算组合总数时，数组中每一个数字都要考虑到
            //所以数组的遍历在内，目标值的遍历在外
            for(int num:nums){
                if(i>=num){
                    dp[i] += dp[i-num];
                }
            }
        }
        return dp[target];
    }

    /**2.
     * 343.整数拆分
     * @param n  n在2和58之间
     * @return
     */
    public int integerBreak(int n){
        //dp[i]表示将正整数拆分成至少两个正整数的和后，正整数的最大乘积
        int[] dp = new int[n+1];
        //0,1都不在考虑范围之内，0不是正整数，1拆不开
        //当i大于等于2时，假设正整数i拆出来的第一个正整数是j，有如下两种方案：
        //1.i拆成j,i-j的和，i-j不再继续拆分成多个整数，此时乘积j*(i-j)；
        //2.i拆成j,i-j的和，i-j继续拆分成多个整数,此时乘积j*dp[i-j]
        //当j固定时，dp[i]=max(j*(i-j),j*dp[i-j])
        for (int i=2;i<=n;i++){
            int curMax = 0;
            for (int j=1;j<i;j++){
                curMax = Math.max(curMax,Math.max(j*(i-j),j*dp[i-j]));
            }
            dp[i] = curMax;
        }
        return dp[n];
    }

    /**3.
     * lc279.完全平方数 其实就是变相的完全背包零钱兑换
     * @param n
     * @return
     */
    public int numSquares(int n){
        //完全平方数最小为1，最大为sqrt(n);
        //要从 nums = [1, 2, ..., sqrt(n)] 数组里选出几个数，令其平方和为 target = n。
        //dp[i] 表示和为 i 的 nums 组合中完全平方数最少有 dp[i] 个。
        int[] dp = new int[n+1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        for(int num=1;num<=Math.sqrt(n);num++){//选择池
            for(int i=0;i<=n;i++){ //目标池
                if(i>=num*num){
                    dp[i] = Math.min(dp[i],dp[i-num*num]+1);
                }
            }
        }
        return dp[n];
    }
    /**4
     * lc322 零钱兑换
     *
     */
    /**给你一个整数数组 coins ，表示不同面额的硬币；以及一个整数 amount ，表示总金额。
     * 计算并返回可以凑成总金额所需的 最少的硬币个数 。如果没有任何一种硬币组合能组成总金额，返回-1 。
    你可以认为每种硬币的数量是无限的。
     */
    public int coinChange(int[] coins, int amount){
        //是否可以用 coins 中的数组合和成 amount，完全背包问题，
        //并且为“不考虑排列顺序的完全背包问题”，外层循环为选择池 coins，内层循环为 amount。
        //dp[i] 表示和为 i 的 coin 组合中硬币最少有 dp[i] 个。
        //对于元素之和等于 i - coin 的每一种组合，
        //在最后添加 coin 之后即可得到一个元素之和等于 i 的组合，因此在计算 dp[i] 时，应该计算所有的 dp[i − coin] + 1 中的最小值。
        //硬币数一定不会超过总金额
        int [] dp = new int[amount+1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        for (int coin:coins){ //选择池
            for(int i=0;i<= amount;i++){ //目标池
                if(coin<=i){
                    dp[i] = Math.min(dp[i],dp[i-coin]+1);
                }
            }

        }
        return dp[amount] != Integer.MAX_VALUE?dp[amount]:-1;

    }
    /**5
     * lc322 零钱兑换2
     *
     * @param amount
     * @param coins
     * @return
    /**
     * 给你一个整数数组 coins 表示不同面额的硬币，另给一个整数 amount 表示总金额。
     * 请你计算并返回可以凑成总金额的硬币组合数。如果任何硬币组合都无法凑出总金额，返回 0 。
     * 假设每一种面额的硬币有无限个
     */
    public int change(int amount, int[] coins){
        //如果是组合，决策就只需要考虑哪个 coin（硬币）可用，所以 coin的遍历在外部
        int[] dp = new int[amount + 1];
        dp[0] = 1;
        for (int coin : coins){
            for (int i = coin; i <= amount; i++){
                if(coin <= i){
                    dp[i] += dp[i - coin];
                }
            }
        }
        return dp[amount];
    }

    /**6
     * lc392 判断子序列
     * @param s
     * @param t
     * @return
     */
    //双指针法判断
    public boolean isSubsequence(String s, String t) {
        //s,t字符串长度
        int n = s.length(), m = t.length();
        //双指针
        //i,j分别从String开始往右走
        int i = 0, j = 0;
        while (i < n && j < m) {
            if (s.charAt(i) == t.charAt(j)) {
                i++;
            }
            j++;
        }
        //如果能找到则i一定能走到最后
        return i == n;
    }


    /**7
     * lc1143 最长公共子序列
     * @param text1
     * @param text2
     * @return
     * 给定两个字符串，返回它们的最长公共子序列，如果不存在公共子序列返回0
     */
    public int longestCommonSubsequence(String text1, String text2){
        int M = text1.length();
        int N = text2.length();
        //dp[][]表示text1[0:i]和text2[0:j]最长公共子序列的长度
        int[][] dp = new int[M+1][N+1];
        for(int i=1;i<=M;i++){
            for (int j=1;j<=N;j++){
                //如果前一个字符是相同的
                if(text1.charAt(i-1)==text2.charAt(j-1)){
                    dp[i][j] = dp[i-1][j-1]+1;
                    //前一个字符不同的话，就找text1[i-1]与text2[j]和text1[i]与test2[j-1]的最大值
                }else{
                    Math.max(dp[i-1][j],dp[i][j-1]);
                }
            }
        }
        return dp[M][N];
    }

    /**8.
     * lc300 最长递增子序列
     * @param nums
     * @return
     */
    public int lengthOfLIS(int[] nums){
        if(nums.length==0) return 0;
        int N = nums.length;
        int[] dp = new int[N+1];
        int res = 0;
        Arrays.fill(dp,1);
        for (int i=0;i<N;i++){
            for(int j=0;j<i;j++){
                //如果前一个值比上一个大
                if(nums[j]<nums[i]){
                    dp[i] = Math.max(dp[i],dp[j]+1);
                }
            }
            res = Math.max(res,dp[i]);
        }
        return res;


    }

    /**9
     * lc516 最长回文子序列
     *
     * @param s
     * @return
     * 给定一个字符串s，找出其中最长的回文子序列，并返回该序列长度
     */
    public int longestPalindromeSubseq(String s){
        //将s反转成s1，然后相当于找两字符串的最长公共子序列
        int n = s.length();
        //长度为[0,i-1]的字符串与长度[0,j-1]的s1的最长公共子序列为dp[i][j]
        int[][] dp = new int[n+1][n+1];
        for(int i=1;i<=n;i++){
            for(int j=1;j<=n;j++){
                //当两个指针指向的字符相等时，s.charAt(i-1)==s.charAt(n-j)
                //其中(n-j)表示从后往前遍历字符串，开始为n-1...0
                //则说明该字符可以作为两字符串的公共子序列（回文子序列）
                //即公共子序列的长度在原来的基础上+1
                if(s.charAt(i-1)==s.charAt(n-j)){
                    dp[i][j] = dp[i-1][j-1]+1;
                }
                //当两个指针指向的字符不相等的时候，该字符不能作为两个字符串的公共子序列
                //那么分别加入s.charAt(i-1)、s.charAt(n-j)看看哪一个可以组成更长的公共子序列
                else{
                    dp[i][j] = Math.max(dp[i-1][j],dp[i][j-1]);
                }
            }
        }
        return dp[n][n];
    }


    /**10.
     * lc5 最长回文子串
     * @param s
     * @return  注意 子串一定是连续的，子序列不一定
     */
    public String longestPalindrome(String s){
        int len = s.length();
        if(len<2) return s;
        int maxLen = 1;
        //起始索引
        int begin = 0;
        //s.charAt(i) 每次都会检查数组下标越界，因此先转换成字符数组
        char[] charArray = s.toCharArray();
        //枚举所有长度大于1的子串，charArray[i...j]
        for (int i=0;i<len-1;i++){
            for (int j = i+1;j<len;j++){
                if (j - i + 1 > maxLen && validPalindromic(charArray, i, j)) {
                    maxLen = j - i + 1;
                    begin = i;
                }
            }
        }
        return s.substring(begin, begin + maxLen);

    }
    /**
     * 验证子串 s[left..right] 是否为回文串
     * 双指针
     */
    private boolean validPalindromic(char[] charArray, int left, int right) {
        while (left < right) {
            if (charArray[left] != charArray[right]) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    /**11.
     * lc64 最小路径和
     * @param grid
     * @return
     * 直接在数组上进行操作，不用构造dp table
     */
    public int minPathSum(int[][] grid){
        for (int i=0;i<grid.length;i++){
            for (int j=0;j<grid[0].length;j++){
                if(i==0&&j==0) continue;
                else if(i==0){
                    //第一行
                    grid[i][j] = grid[i-1][j]+grid[i][j];
                }
                else if(j==0){
                    //第一列
                    grid[i][j] = grid[i][j-1]+grid[i][j];
                }else{
                    grid[i][j] = Math.min(grid[i-1][j],grid[i][j-1])+grid[i][j];
                }
            }
        }
        return grid[grid.length-1][grid[0].length-1];

    }


    /**12.
     * lc62 不同路径
     * @param m
     * @param n
     * @return
     * 机器人位于网格mxn的左上角，机器人试图到达网络的左下角
     * 请问总共有多少条不同路径
     */

    public int uniquePaths(int m, int n){
        //走到mn一共有多少条不同的路径
        int[][] dp = new int[m][n];
        //第一行走法只有一种
        for(int i=0;i<n;i++) dp[0][i] = 1;
        //第一列走法只有一种
        for(int j=0;j<m;j++) dp[j][0] = 1;
        for (int i=1;i<m;i++){
            for (int j=1;j<n;j++){
                dp[i][j] = dp[i-1][j]+dp[i][j-1];
            }
        }
        return dp[m-1][n-1];
    }


    /**13.
     * lc63 不同路径2
     * @param obstacleGrid
     * @return
     * 与上题问题一样，加入考虑了网格中有障碍物，（有障碍物为1，无障碍物为0）计算一共有多少条不同的路径
     */
    public int uniquePathsWithObstacles(int[][] obstacleGrid){
        if(obstacleGrid.length==0||obstacleGrid==null) return 0;
        //定义dp数组，初始化第一列和第一行
        int m = obstacleGrid.length;
        int n = obstacleGrid[0].length;
        //表示格子数走到最后时的方法数
        int[][] dp = new int[m][n];
        //第一列格子只有从最边上上面走过去这一种走法，初始化dp[i][0]=1;存在障碍物为0
        for (int i=0;i<m&&obstacleGrid[i][0]==0;i++){
            dp[i][0] =1;
        }
        //第一行格子只有从最边上左面走过去这一种走法，初始化dp[i][0]=1;存在障碍物为0
        for (int j=0;j<n&&obstacleGrid[0][j]==0;j++){
            dp[0][j] =1;
        }
        //状态转移方程
        for (int i=1;i<m;i++){
            for (int j=1;j<n;j++){
                if(obstacleGrid[i][j]==0){
                    dp[i][j] = dp[i-1][j]+dp[i][j-1];
                }
            }
        }
        return dp[m-1][n-1];
    }


    /**14
     * lc118 杨辉三角
     * @param numRows
     * @return
     * 给定一个非负整数numRows，生成杨辉三角的前numRows行
     */
    public List<List<Integer>> generate(int numRows){
        List<List<Integer>> res = new ArrayList<>();
        if(numRows<=0) return res;
        for (int i=0;i<numRows;i++){
            List<Integer> tempRes = new ArrayList<>();
            for (int j=0;j<=i;j++){
                if(j==0||j==i){
                    tempRes.add(1);
                }else {
                    tempRes.add(res.get(i-1).get(j-1)+res.get(i-1).get(j));
                }
            }
            res.add(tempRes);
        }
        return res;


    }

    /**15
     * lc119 杨辉三角2
     * @param rowIndex
     * @return
     * 给定非负索引，返回第rowIndex行
     * 在上一题的基础上最后.get(rowIndex)就完了
     *
     */
    public List<Integer> getRow(int rowIndex){
        List<List<Integer>> result = new ArrayList<>();
        for (int i=0;i<rowIndex;i++){
            List<Integer> tempRes = new ArrayList<>();
            for (int j=0;j<=i;j++){
                if(j==0||j==i){
                    tempRes.add(1);
                }else {
                    tempRes.add(result.get(i-1).get(j-1)+result.get(i-1).get(j));
                }
            }
            result.add(tempRes);
        }
        return result.get(rowIndex);
    }

    /**16
     * lc198 打家劫舍
     * @param nums
     * @return
     * 警报响的条件是偷相邻的两家
     * 给定一个代表每个房屋存放金额的非负整数数组，不触发警报后，一晚上可偷的最多的值
     */ 
    public int rob(int[] nums){
        if(nums.length==0) return 0;
        // 子问题：
        // f(k) = 偷 [0..k) 房间中的最大金额
        // f(0) = 0
        // f(1) = nums[0]
        // f(k) = max{ rob(k-1), nums[k-1] + rob(k-2) }
        int N = nums.length;
        //dp数组表示偷到第i家以后可以偷的最大金额
        int[] dp = new int[N+1];
        dp[0] = 0;
        //第一个房子打劫的最大金额
        dp[1] = nums[0];
        for (int k=2;k<=N;k++){
            //结果为不偷最后一家和选择偷最后一家的最大值
            dp[k] = Math.max(dp[k-1],nums[k-1]+dp[k-2]);
        }
        return dp[N];
    }

    /**17.
     * lc213.打家劫舍2
     * @param nums
     * @return
     * 在上一题的基础上，房屋改成围一圈偷
     */
    public int rob2(int[] nums){
        if(nums == null || nums.length == 0){
            return 0;
        }
        if(nums.length == 1){//针对只有一个长度的【0】  因为下面要复制数组
            return nums[0];
        }
        //第一个偷：最后一个不偷
        //第一个不偷：最后一个偷
        return Math.max(
                robdSingle(Arrays.copyOfRange(nums,0,nums.length-1))  ,
                robdSingle( Arrays.copyOfRange(nums,1,nums.length) )  );
    }
    public int robdSingle(int[] nums){
        int len = nums.length;
        int[] dp = new int[len+1];
        dp[0] = 0 ;//为了方便操作i-2
        dp[1] = nums[0];//第一个房子打劫的最大金额
        for(int i = 2 ; i <= len ; i++){
            dp[i] = Math.max(dp[i-1], dp[i-2] + nums[i-1]);
        }
        return dp[len];
    }



/*****************************************************************************************
    /**背包问题：
     * 有N件物品和一个最多能被重量为W 的背包。第i件物品的重量是weight[i]，
     * 得到的价值是value[i] 。每件物品只能用一次，求解将哪些物品装入背包里物品价值总和最大。
     * 背包问题有多种背包方式，常见的有：01背包、完全背包、多重背包、分组背包和混合背包等等。
     * 01背包，元素我们只能用一次。
     */
    /**
     * 动规五步走：
     * 1.确定dp数组以及下标的含义
     * 2.确定递推公式
     * 3.dp数组如何初始化
     * 4.确定遍历顺序
     * 5.举例推导dp数组
     */


    /**
     * lc416 分割等和子集
     * 给你一个 只包含正整数 的 非空 数组 nums 。请你判断是否可以将这个数组分割成两个子集，使得两个子集的元素和相等。
     * 背包的体积为sum / 2
     * 背包要放入的商品（集合里的元素）重量为 元素的数值，价值也为元素的数值
     * 背包如何正好装满，说明找到了总和为 sum / 2 的子集。
     * 背包中每一个元素是不可重复放入。
     * 01背包的递推公式为：dp[j] = max(dp[j], dp[j - weight[i]] + value[i]);
     * dp[i]表示 背包总容量是i，最大可以凑成i的子集总和为dp[i]。
     * 本题，相当于背包里放入数值，那么物品i的重量是nums[i]，其价值也是nums[i]。
     * dp[j] = max(dp[j], dp[j - nums[i]] + nums[i]);
     * 如果使用一维dp数组，物品遍历的for循环放在外层，遍历背包的for循环放在内层，且内层for循环倒叙遍历！
     */
    public boolean canPartition(int[] nums){
        int sum = 0;
        int[] dp = new int[20001];
        for (int i=0;i<nums.length;i++){
            sum += nums[i];
        }
        if(sum%2==1) return false;
        int target = sum/2;
        //开始01背包
        for (int i=0;i<nums.length;i++){
            // 每一个元素一定是不可重复放入，所以从大到小遍历
            for (int j = target;j>=nums[i];j--){
                dp[j] = Math.max(dp[j],dp[j-nums[i]]+nums[i]);
            }
        }
        // 集合中的元素正好可以凑成总和target
        if(dp[target]==target) return true;
        return false;
    }

    /**
     * lc1049 最后一块石头的重量
     */
    public int lastStoneWeightII(int[] stones) {
        //dp[j]表示容量（这里说容量更形象，其实就是重量）为j的背包，最多可以背dp[j]这么重的石头。
        //本题物品的重量为stone[i]，物品的价值也为stone[i]。
        int[] dp = new int[15001];
        int sum = 0;
        for(int i=0;i<stones.length;i++){
            sum += stones[i];
        }
        int target = sum/2;
        //遍历物品
        for(int i=0;i<stones.length;i++){
            //遍历背包
            for(int j = target;j>=stones[i];j--){
                dp[j] = Math.max(dp[j],dp[j-stones[i]]+stones[i]);
            }
        }
        return (sum - dp[target]) - dp[target];

    }


    /**
     * lc494 目标和
     * 给定一个非负整数数组，a1, a2, ..., an, 和一个目标数，S。
     * 现在你有两个符号 + 和 -。对于数组中的任意一个整数，你都可以从 + 或 -中选择一个符号添加在前面。
     */

    public int findTargetSumWays(int[] nums, int S){
        int sum = 0;
        for (int i=0;i<nums.length;i++){
            sum += nums[i];
        }
        if((sum+S)%2==1) return 0;
        if(S>sum) return 0;
        int bagSize = (sum+S)/2;
        int[] dp = new int[bagSize+1];
        dp[0] = 1;
        for (int i=0;i<nums.length;i++){
            for (int j=bagSize;j>=nums[i];j--){
                dp[j] += dp[j-nums[i]];
            }
        }
        return dp[bagSize];
    }


    /**
     * 一和零
     * @param strs
     * @param m
     * @param n
     * 给你一个二进制字符串数组 strs 和两个整数 m 和 n 。
     * 请你找出并返回 strs 的最大子集的大小，该子集中 最多 有 m 个 0 和 n 个 1 。
     * @return
     */
    public int findMaxForm(String[] strs, int m, int n){
        //dp[i][j]最多有i个0和j个1的strs的最大子集大小为dp[i][j]
        //dp[i][j] = max(dp[i][j], dp[i - zeroNum][j - oneNum] + 1);
        //字符串的zeroNum和oneNum相当于物品的重量（weight[i]）
        //字符串本身的个数相当于物品的价值（value[i]）。
        int[][] dp = new int[m+1][n+1];// 默认初始化0
        for (String str:strs){
            int oneNum =0 ;
            int zeroNum = 0;
            for (int i=0;i<str.length();i++){
                if (str.charAt(i)=='0') zeroNum++;
                else  oneNum++;
            }
            for (int i=m;i>=zeroNum;i--){
                for (int j=n;j>=oneNum;j--){
                    dp[i][j] = Math.max(dp[i][j],dp[i-zeroNum][j-oneNum]+1);
                }
            }
        }
        return dp[m][n];

    }

    /**
     * lc377 组合总数     其实本题求的是排列数
     * 给你一个由 不同 整数组成的数组 nums ，和一个目标整数 target 。
     * 请你从 nums 中找出并返回总和为 target 的元素组合的个数。
     * 组合不强调顺序，排列强调顺序
     * dp[i]: 凑成目标正整数为i的排列个数为dp[i]
     * dp[i] += dp[i - nums[j]];
     * dp[0]要初始化为1，这样递归其他dp[i]的时候才会有数值基础。
     * ********************************************
     * 如果求组合数就是外层for循环遍历物品，内层for遍历背包。
     * 如果求排列数就是外层for遍历背包，内层for循环遍历物品。
     * **********************************************
     */
    public int combinationSum42(int[] nums, int target){
        int[] dp = new int[target+1];
        dp[0] = 1;
        //遍历背包
        for (int i=0;i<=target;i++){
            //遍历物品
            for (int j=0;j<nums.length;j++){
                if (i - nums[j] >= 0){
                    dp[i] += dp[i-nums[j]];
                }

            }
        }
        return dp[target];
    }

    /**
     * lc322 零钱兑换
     * 给你一个整数数组 coins ，表示不同面额的硬币；以及一个整数 amount ，表示总金额。
     * 计算并返回可以凑成总金额所需的 最少的硬币个数 。
     * 每种硬币的数量是无限的。
     */
    public int coinChange2(int[] coins, int amount){
        //凑成amount所需要的最少硬币个数
        int[] dp = new int[amount+1];
        Arrays.fill(dp,10001);
        dp[0] = 0;
        for (int i=0;i<coins.length;i++){//遍历零钱
            for (int j=coins[i];j<=amount;j++){ //遍历背包
                // 如果dp[j - coins[i]]是初始值则跳过
                if(dp[j-coins[i]]!=10001) dp[j]  = Math.min(dp[j],dp[j-coins[i]]+1);
            }
        }
        if(dp[amount]==10001) return -1;
        return dp[amount];
    }

    /**
     * lc518 零钱兑换2
     * 给定不同面额的硬币和一个总金额。写出函数来计算可以凑成总金额的硬币组合数。
     */
    public int change2(int amount, int[] coins) {

        // //如果是组合，决策就只需要考虑哪个 coin（硬币）可用，所以 coin的遍历在外部
        //组合问题
        int[] dp = new int [amount+1];
        dp[0] = 1;
        for(int i=0;i<coins.length;i++){
            for(int j = coins[i];j<=amount;j++){
                dp[j] += dp[j-coins[i]];
            }
        }
        return dp[amount];

    }

    /**
     * lc279 完全平方数
     */
    public int numSquares1(int n){
        //和为i的完全平方数的最少数量为dp[i]
        int[] dp = new int[n+1];
        Arrays.fill(dp,Integer.MAX_VALUE);
        dp[0] = 0;
        for (int i=0;i<=n;i++){//遍历背包
            for (int j=1;j*j<=i;j++){  //遍历物品
                dp[i] = Math.min(dp[i],dp[i-j*j]+1);
            }

        }
        return dp[n];

    }



/*****************************************************************************************/
    /**
     * lc198 打家劫舍
     */
    public int rob1(int[] nums){
        int[] dp = new int[nums.length+1];
        if (nums.length == 0) {
            return 0;
        }
        if(nums.length==1){
            return nums[0];
        }
        dp[0] = 0;
        dp[1] = Math.max(nums[0],nums[1]);
        for (int i=2;i<nums.length;i++){
            dp[i] = Math.max(dp[i-1],nums[i]+dp[i-2]);
        }
        return dp[nums.length-1];
    }
    /**
     * lc213 打家劫舍2
     */
    public int rob2p(int[] nums){
        if(nums.length==0) return 0;
        if(nums.length==1) return nums[0];
        int res1 = robSin(nums,0,nums.length-2);
        int res2 = robSin(nums,1,nums.length-1);
        return Math.max(res1,res2);
    }
    public int robSin(int[]nums,int start,int end){
        int[] dp = new int[nums.length];
        if(end==start) return nums[start];
        dp[start] = nums[start];
        dp[start+1] = Math.max(nums[start],nums[start+1]);
        for (int i=start+2;i<=end;i++){
            dp[i] = Math.max(dp[i-1],dp[i-2]+nums[i]);
        }
        return dp[end];
    }

    /**
     * lc337打家劫舍3
     */


    /************************************************************/
    /**
     * 子序列相关问题
     *
     */

    /**
     * lc300 最长递增子序列
     * 给你一个整数数组 nums ，找到其中最长严格递增子序列的长度。
     */
    public int lengthOfLIS1(int[] nums){
        if(nums.length<=1) return  nums.length;
        //dp[i]表示i之前包括i的最长上升子序列。
        int[] dp = new int[nums.length+1];
        Arrays.fill(dp,1);
        int res = 0;
        for (int i=1;i<nums.length;i++){
            for (int j=0;j<i;j++){
                if(nums[j]<nums[i]){
                    dp[i] = Math.max(dp[i],dp[j]+1);
                }
            }
            if(dp[i]>res) res = dp[i];
        }
            return res;
    }

    /**
     * lc674 最长连续递增子序列
     * 给定一个未经排序的整数数组，找到最长且 连续递增的子序列，并返回该序列的长度。
     */
    public int findLengthOfLCIS(int[] nums){
        if(nums.length<=1) return nums.length;
        //以下标i为结尾的数组的连续递增的子序列长度为dp[i]。
        int[] dp = new  int[nums.length+1];
        dp[0] = 1;
        int res = 0;
        for (int i=1;i<nums.length;i++){
            if(dp[i]>dp[i-1]){
                dp[i] = dp[i-1]+1;
            }else {
                dp[i]  = 1;
            }
            res = Math.max(res,dp[i]);
        }
        return res;

    }

    /**
     * lc718 最长重复子数组
     * 给两个整数数组 A 和 B ，返回两个数组中公共的、长度最长的子数组的长度。
     */
    public int findLength(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;
        //dp[i][j] ：以下标i - 1为结尾的A，和以下标j - 1为结尾的B，最长重复子数组长度为dp[i][j]。
        int[][] dp = new int[m+1][n+1];
        for(int i=0;i<m;i++){
            dp[i][0] = 0;
        }
        for(int j=0;j<n;j++){
            dp[0][j] = 0;
        }
        int res = 0;
        for(int i=1;i<=nums1.length;i++){
            for(int j=1;j<=nums2.length;j++){
                if(nums1[i-1]==nums2[j-1]){
                    dp[i][j] = dp[i-1][j-1]+1;
                }
                if(dp[i][j]>res) res = dp[i][j];
            }
        }
        return res;
    }

    /**
     * lc53 最大子序和
     * 给定一个整数数组 nums ，找到一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
     */

    public int maxSubArray(int[] nums){
        if(nums.length==0) return 0;
        //dp[i]：包括下标i之前的最大连续子序列和为dp[i]。
        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        int res = dp[0];
        for (int i=1;i<nums.length;i++){
            dp[i] = Math.max(dp[i-1]+nums[i],nums[i]);
            if(dp[i]>res) res = dp[i];
        }
        return res;

    }

    /**
     * lc392 判断子序列
     * 给定字符串 s 和 t ，判断 s 是否为 t 的子序列。
     */
    public boolean isSubsequence1(String s, String t) {
        int length1 = s.length(); int length2 = t.length();
        //dp[i][j] 表示以下标i-1为结尾的字符串s，和以下标j-1为结尾的字符串t，相同子序列的长度为dp[i][j]。
        int[][] dp = new int[length1+1][length2+1];
        for(int i = 1; i <= length1; i++){
            for(int j = 1; j <= length2; j++){
                if(s.charAt(i-1) == t.charAt(j-1)){
                    dp[i][j] = dp[i-1][j-1] + 1;
                }else{
                    dp[i][j] = dp[i][j-1];
                }
            }
        }
        if(dp[length1][length2] == length1){
            return true;
        }else{
            return false;
        }
    }
    /**
     * lc115 不同的子序列  **
     * 给定一个字符串 s 和一个字符串 t ，计算在 s 的子序列中 t 出现的个数。
     */
    public int numDistinct(String s, String t) {
        int lens = s.length();
        int lent = t.length();
        //定义dp数组
        //dp[i][j]：以i-1为结尾的s子序列中出现以j-1为结尾的t的个数为dp[i][j]。
        int[][] dp = new int[lens+1][lent+1];
        //初始化
        for(int i=0;i<lens+1;i++) dp[i][0] = 1;
        //遍历dp数组
        for(int i=1;i<=lens;i++){
            for(int j=1;j<=lent;j++){
                if((s.charAt(i-1)) == (t.charAt(j-1)))
                    dp[i][j] = dp[i-1][j-1] + dp[i-1][j];
                else
                    dp[i][j] = dp[i-1][j];
            }
        }
        return dp[lens][lent];
    }
    /**
     * lc583 两个字符串的删除操作
     */
    public int minDistance(String word1, String word2){
        //dp[i][j]：以i-1为结尾的字符串word1，和以j-1位结尾的字符串word2，想要达到相等，所需要删除元素的最少次数。
        int[][] dp = new int[word1.length() + 1][word2.length() + 1];
        for (int i = 0; i < word1.length() + 1; i++) dp[i][0] = i;
        for (int j = 0; j < word2.length() + 1; j++) dp[0][j] = j;

        for (int i = 1; i < word1.length() + 1; i++) {
            for (int j = 1; j < word2.length() + 1; j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                }else{
                    dp[i][j] = Math.min(dp[i - 1][j - 1] + 2,
                            Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1));
                }
            }
        }

        return dp[word1.length()][word2.length()];
    }


    /**
     * lc647 回文子串
     * 给定一个字符串，你的任务是计算这个字符串中有多少个回文子串。
     *具有不同开始位置或结束位置的子串，即使是由相同的字符组成，也会被视作不同的子串。
     */
    public int countSubstrings(String s){
        //dp[i][j]表示[i,j]区间范围内的子串是否是回文子串，如果是返回true
        boolean[][] dp = new boolean[s.length()][s.length()];
        int res = 0;
        //Arrays.fill(dp,false); 这句不写也能过lc
        for (int i=s.length()-1;i>=0;i--){
            for (int j=i;j<s.length();j++){
                if(s.charAt(i)==s.charAt(j)){
                    if(j-i<=1){
                        res ++;
                        dp[i][j] = true;
                    }else if(dp[i+1][j-1]){
                        res++;
                        dp[i][j] = true;
                    }
                }
            }
        }
        return res;
    }

    /**
     * lc516 最长回文子序列
     * 给你一个字符串 s ，找出其中最长的回文子序列，并返回该序列的长度。
     * @param s
     * @return
     */
    public int longestPalindromeSubseq1(String s){
        //dp[i][j]：字符串s在[i, j]范围内最长的回文子序列的长度为dp[i][j]。
        int[][] dp = new int[s.length()][s.length()];
        for (int i=0;i<s.length();i++){
            dp[i][i] = 1;
        }
        for (int i=s.length()-1;i>=0;i--){
            for (int j=i+1;j<s.length();j++){
                if(s.charAt(i)==s.charAt(j)){
                    dp[i][j] = dp[i+1][j-1]+2;
                }else{
                    dp[i][j] = Math.max(dp[i+1][j],dp[i][j-1]);
                }
            }
        }
        return dp[0][s.length()-1];


    }
    /**
     * lc5 最长回文子串
     */
    public String longestPalindrome2(String s){
        if(s.length()<2) return s;
        //字符串s在[i，j]范围内 是否是回文子串
        boolean[][] dp = new boolean[s.length()][s.length()];
        //Arrays.fill(dp,false);
        int max = 0;
        int left = 0;
        int right = 0;
        for (int i=s.length()-1;i>=0;i--){
            for (int j=i;j<s.length();j++){
                if(s.charAt(i)==s.charAt(j)){
                    if(j-i<=1){
                        dp[i][j] = true;
                    }else if(dp[i+1][j-1]){
                        dp[i][j] = true;
                    }
                }
                if(dp[i][j] && j-i+1>max){
                    max = j-i+1;
                    left = i;
                    right = j;
                }
            }
        }
        return s.substring(left,max);


    }



    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        //f[k][i]定义为通过k次航班,从出发城市到达目的城市i所要的最小花费
        //f[k][i] = min(f[k-1][i]+cost{j,i}) {j,i}表示在给定数组中，存在从城市j出发到达i的航班，cost{j,i}表示航班花费
        //前k-1次航班的最小花费，加上最后一条航班的最小花费
        final int INF = 10000 * 101 + 1;
        int[][] f = new int[k + 2][n];
        for (int i = 0; i < k + 2; ++i) {
            Arrays.fill(f[i], INF);
        }
        f[0][src] = 0;
        for (int t = 1; t <= k + 1; ++t) {
            for (int[] flight : flights) {
                int j = flight[0], i = flight[1], cost = flight[2];
                f[t][i] = Math.min(f[t][i], f[t - 1][j] + cost);
            }
        }
        int ans = INF;
        for (int t = 1; t <= k + 1; ++t) {
            ans = Math.min(ans, f[t][dst]);
        }
        return ans == INF ? -1 : ans;
    }

    public int maxEnvelopes(int[][] envelopes){
        int n = envelopes.length;
        if (n==1) return 1;
        //按宽度升序排，然后按高度降序排
        Arrays.sort(envelopes,(a,b)->a[0]-b[0]==0?b[1]-a[1]:a[0]-b[0]);
        int ans = 0;

        int[] dp = new int[n];
        for (int i=0;i<n;i++){
            for (int j=0;j<i;j++){
                if(envelopes[j][1]<envelopes[i][1]){
                    dp[i]= Math.max(dp[i],dp[j]);
                }

            }
            ans = Math.max(ans,++dp[i]);
        }
        return  ans;
    }
















}

