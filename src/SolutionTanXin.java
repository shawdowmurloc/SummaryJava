import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class SolutionTanXin {
    /**
     * 贪心算法集合
     * 贪心算法一般解题步骤：
     * 1.将问题分解为若干子问题
     * 2.找出合适的贪心策略
     * 3.求解每一个子问题的最优解
     * 4.将局部解堆叠成全局最优解
     */

    /**
     * lc455 分发饼干
     * lc53 最大子序和
     * lc122 买卖股票的最佳时期
     * lc55 跳跃游戏
     * lc45 跳跃游戏2
     * lc1005 K次取反后最大化的数组和
     * lc134 加油站
     * lc135 分发糖果
     * lc860 柠檬水找零钱
     * 用最少的箭射爆气球
     * lc763 划分字母区间
     * lc783 单调递增的数字
     */
    /**1
     * lc455 分发饼干
     * @param g
     * @param s
     * @return
     */
    public int findContentChildren(int[] g, int[] s){
        //先对两数组排序
        //满足条件的情况是尽可能先满足胃口大的孩子
        Arrays.sort(g);
        Arrays.sort(s);
        int index = s.length-1;
        int res = 0;
        for (int i = g.length-1;i>=0;i--){
            if(s[index]>=g[i]){
                res++;
                index--;
            }
        }
        return res;

    }

    /**2.
     * lc53 最大子序和
     * @param nums
     * @return
     * 给定一个整数数组 nums ，找到一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
     * 这里的贪心在于，如果我加上一个nums[i]负数以后，值会变小，那么nums[i]我就不计和，从下一个nums[i+1]开始
     *
     */
    public int maxSubArray(int[] nums){
        if(nums.length==1) return nums[0];
        int sum = Integer.MIN_VALUE;
        int count = 0;
        for (int i=0;i<nums.length;i++){
            count += nums[i];
            // 取区间累计的最大值（相当于不断确定最大子序终止位置）
            sum = Math.max(sum,count);
            if(count<=0){
                // 相当于重置最大子序起始位置，因为遇到负数一定是拉低总和
                count = 0;
            }
        }
        return sum;
    }

    /**3.
     * lc122 买卖股票的最佳时期
     * 把相邻的利润算出来，大于0的都加一起，就是最优解
     */
    public int maxProfit(int[] prices) {

        int res = 0;
        for(int i=1;i<prices.length;i++){
            res += Math.max(prices[i]-prices[i-1],0);
        }
        return res;
    }

    /**4.
     * lc55 跳跃游戏
     * 给定一个非负整数数组nums ，你最初位于数组的 第一个下标 。
     * 数组中的每个元素代表你在该位置可以跳跃的最大长度。
     * 判断你是否能够到达最后一个下标。
     * 这个的贪心在于，我不管每次能跳几步，我只要知道我的覆盖范围就行，
     * 能覆盖到最后就成功
     */
    public boolean canJump(int[] nums){
        int cover = 0;
        if(nums.length==1) return true;
        for (int i=0;i<= cover;i++) {
            cover = Math.max(i+nums[i],cover);
            if(cover >= nums.length-1) return true;
        }
        return false;
    }

    /**5.
     * lc45 跳跃游戏2
     * 目标是使用最少的跳跃次数到达数组的最后一个位置。
     * 直观上来看，我们可以「贪心」地选择距离最后一个位置最远的那个位置，也就是对应下标最小的那个位置。因此，我们可以从左到右遍历数组，选择第一个满足要求的位置。
     * 找到最后一步跳跃前所在的位置之后，我们继续贪心地寻找倒数第二步跳跃前所在的位置，以此类推，直到找到数组的开始位置。
     *
     */
    public int jump(int[] nums) {
        int position = nums.length - 1;
        int steps = 0;
        while (position > 0) {
            for (int i = 0; i < position; i++) {
                if (i + nums[i] >= position) {
                    position = i;
                    steps++;
                    break;
                }
            }
        }
        return steps;
    }


    /**6.
     * lc1005 K次取反后最大化的数组和
     * 第一步：将数组按照绝对值大小从大到小排序，注意要按照绝对值的大小
     * 第二步：从前向后遍历，遇到负数将其变为正数，同时K--
     * 第三步：如果K还大于0，那么反复转变数值最小的元素，将K用完
     * 第四步：求和
     */
    public int largestSumAfterKNegations(int[] A, int k){
        int sum = 0;
        int n = A.length;
        //1.
        Integer[] AA = new Integer[n];
        for (int i=0;i<n;i++){
            AA[i] = A[i];
        }
        Arrays.sort(AA,(a,b)->{
            //逆序排序
            return Math.abs(b)-Math.abs(a);
        });
        //2.
        for (int i=0;i<n;i++){
            if(AA[i]<0&&k>0){
                AA[i] *= -1;
                k--;
            }
        }
        //3.
        if(k!=0){
            AA[n-1] = (k%2==1)?(-AA[n-1]):AA[n-1];
        }
        //4.
        for (int i=0;i<n;i++){
            sum += AA[i];
        }
        return sum;
    }

    /**7.
     * lc134 加油站
     * 首先如果总油量减去总消耗大于等于零那么一定可以跑完一圈，
     * 说明各个站点的加油站 剩油量rest[i]相加一定是大于等于零的。
     * 每个加油站的剩余量rest[i]为gas[i] - cost[i]。
     * i从0开始累加rest[i]，和记为curSum，一旦curSum小于零，
     * 说明[0, i]区间都不能作为起始位置，起始位置从i+1算起，再从0计算curSum。
     *
     */
    public int canCompleteCircuit(int[] gas, int[] cost) {
        /**
         * 当前累加rest[i]和 cursum
         * 开始的位置索引 start
         * sum
         */
        int cursum = 0, begin = 0, sum = 0;
        for (int i = 0; i < gas.length; i++) {
            sum += gas[i] - cost[i];
            cursum += gas[i] - cost[i];
            if (cursum < 0) { //当前累加rest[i]和 curSum一旦小于0
                begin = i + 1;  // 起始位置更新为i+1
                cursum = 0;  // curSum从0开始
            }
        }
        //循环完sum<0,怎么都不可能跑完一圈了
        return sum < 0 ? -1 : begin;
    }


    /**
     * lc135 分发糖果
     * 一次是从左到右遍历，只比较右边孩子评分比左边大的情况。
     * 一次是从右到左遍历，只比较左边孩子评分比右边大的情况。
     * 这样从局部最优推出了全局最优，即：相邻的孩子中，评分高的孩子获得更多的糖果。
     */
    public int candy(int[] ratings){
        int[] candy = new int[ratings.length];
        //每个孩子至少给一个糖，先给完再说
        Arrays.fill(candy,1);
        //要两边兼顾，贪心的原则在于从局部最优，推到全局最优
        //从前向后  右孩子大于左孩子
        for (int i=1;i<ratings.length;i++){
            if(ratings[i]>ratings[i-1]) candy[i] = candy[i-1]+1;
        }
        //从后向前   左孩子大于右孩子
        for (int j=ratings.length-2;j>=0;j--){
            //如果 ratings[i] > ratings[i + 1]，此时candyVec[i]（第i个小孩的糖果数量）就有两个选择了，
            // 一个是candyVec[i + 1] + 1（从右边这个加1得到的糖果数量），一个是candyVec[i]（之前比较右孩子大于左孩子得到的糖果数量）。
            if(ratings[j]>ratings[j+1]){
                candy[j] = Math.max(candy[j],candy[j+1]+1);
            }
        }
        //最后遍历相加得到结果
        int res = 0;
        for (int i=0;i<candy.length;i++){
            res += candy[i];
        }
        return  res;

    }

    /**
     * lc860 柠檬水找零钱
     */
    //情况一：账单是5，直接收下。
    //情况二：账单是10，消耗一个5，增加一个10
    //情况三：账单是20，优先消耗一个10和一个5，如果不够，再消耗三个5
    public boolean lemonadeChange(int[] bills) {
        int cash_5 = 0;
        int cash_10 = 0;

        for (int i = 0; i < bills.length; i++) {
            // 情况一
            if (bills[i] == 5) {
                cash_5++;
                // 情况二
            } else if (bills[i] == 10) {
                cash_5--;
                cash_10++;
                // 情况三
            } else if (bills[i] == 20) {
                // 优先消耗10美元，因为5美元的找零用处更大，能多留着就多留着
                if (cash_10 > 0) {
                    cash_10--;
                    cash_5--;
                } else {
                    cash_5 -= 3;
                }
            }
            if (cash_5 < 0 || cash_10 < 0) return false;
        }

        return true;
    }

    /**
     * 用最少的箭射爆气球
     * @param points
     * @return
     */
    public int findMinArrowShots(int[][] points){
        if(points.length==0) return 0;
        //按数组中起始元素比较，排序
        Arrays.sort(points,(o1,o2) ->Integer.compare(o1[0],o2[0]));
        int count = 1;
        for (int i=1;i<points.length;i++){
            if(points[i][0]>points[i-1][1]){ // 气球i和气球i-1不挨着，注意这里不是>=
                count++; // 需要一支箭
            }else{ // 气球i和气球i-1挨着
                points[i][1] = Math.min(points[i][1],points[i-1][1]); // 更新重叠气球最小右边界
            }
        }
        return count;

    }

    /**
     * lc435 无重叠区间
     * 给定一个区间的集合，找到需要移除区间的最小数量，使剩余区间互不重叠。
     *
     */

    /**
     * lc763 划分字母区间
     */
    public List<Integer> partitionLabels(String S){
        List<Integer> list = new LinkedList<>();
        int[] edge = new int[123];
        char[] chars = S.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            edge[chars[i] - 'a'] = i;
        }
        int idx = 0;
        int last = -1;
        for (int i = 0; i < chars.length; i++){
            idx = Math.max(idx,edge[chars[i] - 'a']);
            if (i == idx) {
                list.add(i - last);
                last = i;
            }
        }
        return list;
    }

    /**
     * lc783 单调递增的数字
     * 给定一个非负整数 N，找出小于或等于 N 的最大的整数，
     * 同时这个整数需要满足其各个位数上的数字是单调递增。
     * 局部最优：遇到strNum[i - 1] > strNum[i]的情况，让strNum[i - 1]--，
     * 然后strNum[i]给为9，可以保证这两位变成最大单调递增整数。
     * 全局最优：得到小于等于N的最大单调递增的整数。
     * 一定从后往前遍历
     */
    public int monotoneIncreasingDigits(int n){
        //把数转换成字符串数组
        String[] str = (n + "").split("");
        // start用来标记赋值9从哪里开始
        // 设置为这个默认值，为了防止第二个for循环在flag没有被赋值的情况下执行
        int start = str.length;
        for (int i = str.length-1;i>0;i--){
            if(Integer.parseInt(str[i])<Integer.parseInt(str[i-1])){
                str[i-1] = (Integer.parseInt(str[i-1])-1)+"";
                start = i;
            }
        }
        for (int i=start;i<str.length;i++){
            str[i] = "9";
        }
        return Integer.parseInt(String.join("",str));

    }




}
