import java.util.*;

public class SolutionArrayAndString {
    /**
     * 数组和字符串相关的问题集合
     * 1.字母异位词分组
     * 2.无重复字符的最长子串
     * 3.递增的三元子序列
     * 4.矩阵中战斗力最弱的k行
     */
    /**
     * 二分法相关
     * lc35 搜索插入位置
     * lc34 在排序数组中查找元素的第一个和最后一个位置
     */
    /**
     * 双指针相关
     * lc26 删除有序数组中的重复项
     * lc283 移动0
     * lc977 有序数组的平方
     * lc209 长度最小的子数组***
     * lc59 螺旋矩阵2
     */


    /**1.
     * lc49 字母异位词分组
     * @param strs
     * @return
     * 给定一个字符串数组，将字母异位词组合在一起，按任意顺序返回结果
     * 字母异位词指字母相同但是排列不同的字符串
     */
    public List<List<String>> groupAnagrams(String[] strs){
        //由于互为字母异位词的两个字符串包含的字母相同，
        //因此对两个字符串分别进行排序之后得到的字符串一定是相同的，
        //故可以将排序之后的字符串作为哈希表的键。
        Map<String,List<String>> map = new HashMap<String,List<String>>();
        for(String str:strs){
            char[] array = str.toCharArray();
            Arrays.sort(array);
            String key = new String(array);
            List<String> list = map.getOrDefault(key,new ArrayList<String>());
            list.add(str);
            map.put(key,list);
        }
        return new ArrayList<List<String >>(map.values());

    }

    /**2.
     * lc3 无重复字符的最长子串
     * @param s
     * @return
     * 给定一个字符串s，请找出不含重复字符的最长子串的长度
     */
    public int lengthOfLongestSubstring(String s){
        if(s.length()==0) return 0;
        HashMap<Character,Integer> map = new HashMap<>();
        //左指针
        int left = 0;
        //结果
        int max = 0;
        //从头遍历字符串
        for (int i=0;i<s.length();i++){
            if(map.containsKey(s.charAt(i))){
                left = Math.max(left,map.get(s.charAt(i))+1);
            }
            map.put(s.charAt(i),i);
            max = Math.max(max,i-left+1);

        }
        return max;

    }

    /**3.
     * lc334 递增的三元子序列
     * @param nums
     * @return
     * 给定一个整数数组，判断这个数组中是否存在长度为3的递增子序列
     * 如果存在这样的三元组下标 (i, j, k)且满足 i < j < k ，使得nums[i] < nums[j] < nums[k] ，返回 true ；
     * 否则，返回 false 。
     *

     */
    public boolean increasingTriplet(int[] nums){
        return true;



    }


    /**4
     * lc1337 矩阵中战斗力最弱的k行
     * @param mat
     * @param k
     * @return
     */
    public int[] kWeakestRows(int[][] mat, int k){
        int m = mat.length;
        int n = mat[0].length;
        int[][] all = new int[m][n];
        for (int i=0;i<m;i++){
            int cur =  0;
            for (int j=0;j<n;j++){
                cur += mat[i][j];
            }
            all[i] = new int[]{cur,i};
        }
        Arrays.sort(all,(a,b)->{
            if(a[0]!=b[0]) return a[0]-b[0];
            return a[1]-b[1];
        });
        int[] ans = new int[k];
        for (int i=0;i<k;i++){
            ans[i] = all[i][1];
        }
        return ans;

    }

    /***********************************************************
     * 二分法相关题目
     ***********************************************************/
    /**
     * 二分法及其注意事项
     * 有序的数组，可以使用二分法
     */
    //二分法的第一种写法 定义target在一个左闭右闭的区间里
    public int erFenFa(int[]nums,int target){
        int left = 0;
        int right = nums.length-1; // 定义target在左闭右闭的区间里，[left, right]
        while(left<=right){// 当left==right，区间[left, right]依然有效，所以用 <=
            int mid = left + ((right-left)/2);// 防止溢出 等同于(left + right)/2
            if(nums[mid]>target){
                right = mid-1;// target 在左区间，所以[left, middle - 1]
            } else if (nums[mid] < target) {
                left = mid+1;// target 在右区间，所以[middle + 1, right]
            }else{ // nums[middle] == target
                return mid;// 数组中找到目标值，直接返回下标
            }
        }
        // 未找到目标值
        return -1;
    }
    //二分法的第二种写法
    public int erFenFa2(int[]nums,int target){
        int left = 0;
        int right = nums.length-1; // 定义target在左闭右闭的区间里，[left, right）
        while(left<right){// 因为left == right的时候，在[left, right)是无效的空间，所以使用 <
            int middle = left + ((right - left) >> 1);
            if (nums[middle] > target) {
                right = middle; // target 在左区间，在[left, middle)中
            } else if (nums[middle] < target) {
                left = middle + 1; // target 在右区间，在[middle + 1, right)中
            } else { // nums[middle] == target
                return middle; // 数组中找到目标值，直接返回下标
            }
        }
        return -1;
    }

    /**
     * lc35 搜索插入位置
     * 就是二分法
     */
    /**
     * lc34 在排序数组中查找元素的第一个和最后一个位置
     */
    public int[] searchRange(int[] nums, int target){
        int left = getLeft(nums,target);
        int right = getRight(nums,target);
        if(left==-2||right==-2) return new int[]{-1,-1};
        if(right-left>1) return new int[]{left+1,right+1};
        else return new int[]{-1,-1};

    }
    private int getRight(int[]nums,int target){
        int left = 0;
        int right = nums.length-1;// 定义target在左闭右闭的区间里，[left, right]
        int rightBorder  = -2;// 记录一下rightBorder没有被赋值的情况
        while (left<=right){// 当left==right，区间[left, right]依然有效
            int mid = left+((right-left)/2);
            if(nums[mid]>target){
                right = mid-1;// target 在左区间，所以[left, middle - 1]
            }else{// 当nums[middle] == target的时候，更新left，这样才能得到target的右边界
                left = mid+1;
                rightBorder = left;
            }
        }
        return rightBorder;
    }
    private int getLeft(int[]nums,int target){
        int left = 0;
        int right = nums.length-1;// 定义target在左闭右闭的区间里，[left, right]
        int leftBorder  = -2;// 记录一下leftBorder没有被赋值的情况
        while (left<=right){
            int mid = left+((right-left)/2);
            if (nums[mid] >= target) { // 寻找左边界，nums[middle] == target的时候更新right
                right = mid - 1;
                leftBorder = right;
            } else {
                left = mid + 1;
            }
        }
        return leftBorder;
    }

    /***********************************************************/
    //双指针 移除元素问题
    /**********************************************************/
    /**
     * lc26 删除有序数组中的重复项
     * 快慢指针
     */
    public int removeDuplicates(int[] nums){
        int slowIndex = 0;
        int fastIndex = 1;
        while (fastIndex<nums.length){
            if(nums[slowIndex]!=nums[fastIndex]){
                nums[slowIndex+1] = nums[fastIndex];
                slowIndex++;
            }
            fastIndex++;
        }
        return slowIndex+1;
    }

    /**
     * lc283 移动0
     */
    public void moveZeroes(int[] nums){
        int slowIndex = 0;
        for (int fastIndex = 0;fastIndex<nums.length;fastIndex++){
            if(nums[fastIndex]!=0){
                nums[slowIndex++] = nums[fastIndex];
            }
        }
        for (int i=slowIndex;i<nums.length;i++){
            nums[i] = 0;
        }

    }

    /**
     * lc977 有序数组的平方
     * 数组平方的最大值就在数组的两端，不是最左边就是最右边，不可能是中间。
     * i指向起始位置，j指向终止位置。
     * 定义一个新数组result，和A数组一样的大小，让k指向result数组终止位置。
     * 如果A[i] * A[i] < A[j] * A[j] 那么result[k--] = A[j] * A[j]; 。
     * 如果A[i] * A[i] >= A[j] * A[j] 那么result[k--] = A[i] * A[i]; 。

     */
    public int[] sortedSquares(int[] nums){
        int[] res = new int[nums.length];
        int k = nums.length-1;
        for (int i=0,j=nums.length-1;i<=j;){
            if(nums[i]*nums[i]<nums[j]*nums[j]){
                res[k--] = nums[j]*nums[j];
                j--;
            }else{
                res[k--] = nums[i]*nums[i];
                i++;
            }
        }
        return res;

    }


    /**
     * lc209 长度最小的子数组
     */
    //滑动窗口
    //其实也就是双指针问题的一种，
    //实现滑动窗口，要确定三点 1.窗口内有什么？2.如何移动窗口起始位置？3，如何移动窗口终止位置
    public int minSubArrayLen(int s, int[] nums){
        int left = 0;//滑动窗口起始位置
        int sum = 0;//滑动窗口数值之和
        int res = Integer.MAX_VALUE;
        for (int right = 0;right<nums.length;right++){
            sum+= nums[right];
            //这里使用while，每次更新i的起始位置，并不断比较子序列是否符合条件
            while (sum>=s){
                res = Math.min(res, right-left+1); //与子序列长度进行比较
                sum-=nums[left++];//这里体现滑动窗口的精髓，不断变更起始位置
            }
        }
        return  res == Integer.MAX_VALUE?0:res;
    }

    /**
     * lc904 水果成篮
     */

    /**
     * lc59 螺旋矩阵2
     */
    public int[][] generateMatrix(int n) {
        int[][] res = new int[n][n];

        // 循环次数
        int loop = n / 2;
        // 定义每次循环起始位置
        int startX = 0;
        int startY = 0;

        // 定义偏移量
        int offset = 1;
        // 定义填充数字
        int count = 1;
        // 定义中间位置
        int mid = n / 2;
        while (loop > 0) {
            int i = startX;
            int j = startY;

            // 模拟上侧从左到右
            for (; j<startY + n -offset; ++j) {
                res[startX][j] = count++;
            }

            // 模拟右侧从上到下
            for (; i<startX + n -offset; ++i) {
                res[i][j] = count++;
            }

            // 模拟下侧从右到左
            for (; j > startY; j--) {
                res[i][j] = count++;
            }

            // 模拟左侧从下到上
            for (; i > startX; i--) {
                res[i][j] = count++;
            }

            loop--;

            startX += 1;
            startY += 1;

            offset += 2;
        }


        if (n % 2 == 1) {
            res[mid][mid] = count;
        }

        return res;
    }

    public int[][] generateMatrix2(int n) {
        int[][] arr = new int[n][n];
        int left = 0;
        int right = n-1;
        int top = 0;
        int buttom = n-1;
        int num=1;
        int numsize = n*n;
        while (true){
            for(int i=left;i<=right;i++){
                arr[top][i] = num++;
            }
            top++;
            if(num>numsize) break;
            for (int i = top; i <= buttom; ++i) {
                arr[i][right] = num++;
            }
            right--;
            if (num > numsize) break;


            for (int i = right; i >= left; --i) {
                arr[buttom][i] = num++;
            }
            buttom--;
            if (num > numsize) break;


            for (int i = buttom; i >= top; --i) {
                arr[i][left] = num++;
            }
            left++;
            if (num > numsize) break;
        }
        return arr;
    }













































































































}
