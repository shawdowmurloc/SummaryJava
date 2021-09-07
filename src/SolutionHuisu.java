import sun.awt.image.ImageWatched;

import java.util.*;

/**
 * 回溯问题集合
 * 1.组合
 * 2.电话号码的字母组合
 * 3.组合总数
 * 4.组合总数2
 * 5.组合总数3、
 * 6.全排列
 * 7.全排列2
 * 8.子集
 * 9.子集2
 */
public class SolutionHuisu {
    /**
     * 回溯问题一般解决以下类型的问题：
     * 1.组合问题:N个数里面按一定规则找出k个数的集合 不强调元素顺序
     * 2.排列问题：N个数按一定规则全排列，有几种排列方式 强调元素顺序
     * 3.切割问题：一个字符串按一定规则有几种切割方式
     * 4.子集问题：一个N个数的集合里有多少符合条件的子集
     * 5.棋盘问题：N皇后，解数独等等
     * 回溯问题解决的模板如下
     * void backtracking(参数) {
     *     if (终止条件) {
     *         存放结果;
     *         return;
     *     }
     *     for (选择：本层集合中元素（树中节点孩子的数量就是集合的大小）) {
     *         处理节点;
     *         backtracking(路径，选择列表); // 递归
     *         回溯，撤销处理结果
     *     }
     * }
     * 经典三部曲：
     * 1.确定回溯函数参数
     * 2.确定终止条件
     * 3.确定单层遍历逻辑
     */

    /**1。
     * lc77 组合
     * @param n
     * @param k
     * @return
     * 给定两个整数n和k，返回[1,n]中所有可能的k个数的组合
     */
    public List<List<Integer>> combine(int n, int k){
        List<List<Integer>> ans = new ArrayList<>();
        LinkedList<Integer> tempAns = new LinkedList<>();
        backtrack1(n,k,1,ans,tempAns);
        return ans;

    }
    private void backtrack1(int n,int k,int startIndex,List<List<Integer>> ans,LinkedList<Integer> tempAns){
        //结束条件
        if(tempAns.size()==k){
            ans.add(new ArrayList<>(tempAns));
            return;
        }
        for (int i=startIndex;i<=n;i++){
            tempAns.add(i);
            backtrack1(n,k,i+1,ans,tempAns);
            tempAns.remove();
        }
    }





    /**2.
     * lc17电话号码的字母组合
     * @param digits
     * @return
     * 给定一个仅包含2-9的字符串，返回它所能表示的字母组合
     * 答案可以按任意顺序返回
     */
    public static List<String> letterCombinations(String digits){
        List<String> ans = new ArrayList<>();
        if(digits.length()==0) return ans;
        Deque<Character> path = new ArrayDeque<>();
        Map<Character,String> map = new HashMap<>();
        map.put('2',"abc");
        map.put('3', "def");
        map.put('4', "ghi");
        map.put('5', "jkl");
        map.put('6', "mno");
        map.put('7', "pqrs");
        map.put('8', "tuv");
        map.put('9', "wxyz");
        backtrack2(digits,0,ans,path,map);
        return ans;

    }
    private static void backtrack2(String digits, int begin, List<String> ans, Deque<Character>path, Map<Character,String> map){
        //回溯问题首先要找到结束条件，结束把结果加入结果集
        //这里begin记录遍历到第几个数字了
        if (path.size()==digits.length()){
            StringBuilder sb = new StringBuilder();
            for (Character character : path) {
                sb.append(character);
            }
            ans.add(sb.toString());
            return;
        }
        //单层逻辑，取begin指向的数字，找到对应的字符集
        for (int i=begin;i<digits.length();i++){
            //找到对应的String s
            String s = map.get(digits.charAt(i));
            for (int j=0;j<s.length();j++){
                path.addLast(s.charAt(j));
                backtrack2(digits,i+1,ans,path,map);
                path.removeLast();
            }
        }

    }

    /**3.
     * lc39 组合总数
     * @param candidates
     * @param target
     * @return
     * 给定一个无重复元素的正整数数组，和一个正整数
     * 找出数组中所有可以使数字和为目标和的唯一组合
     * 数组中的数可以无限制的重复选取，如果至少一个所选数字数量不同，则两种组合是唯一的。
     */
    public List<List<Integer>> combinationSum(int[] candidates, int target){
        //结果集
        List<List<Integer>> res = new ArrayList<>();
        if(candidates.length==0) return res;
        Deque<Integer> tempAns = new ArrayDeque<>();
        backtrack3(candidates,target,0,tempAns,res);
        return res;

    }
    private void backtrack3(int[]candidates,int target,int begin,Deque<Integer>tempAns,List<List<Integer>> res){
        if(target<0){
            return;
        }
        if(target==0){
            res.add(new ArrayList<>(tempAns));
            return;
        }
        for (int i=begin;i<candidates.length;i++){
            tempAns.addLast(candidates[i]);
            // **注意：由于每一个元素可以重复使用，下一轮搜索的起点依然是 i，这里非常容易弄错
            backtrack3(candidates,target,i,tempAns,res);
            tempAns.removeLast();

        }
    }


    /**4.
     * lc40 组合总数2
     * @param candidates
     * @param target
     * @return
     * 跟上题一样，不过这次数数组中的数在每个组合中只能用一次
     * 而且数组中的数使有重复的
     */
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> res = new ArrayList<>();
        Deque<Integer> deque = new ArrayDeque<>();
        //为了将重复的数字都放到一起，所以先进行排序
        Arrays.sort(candidates);
        //加标志数组，用来辅助判断同层节点是否已经遍历
        boolean[] flag = new boolean[candidates.length];
        backtrack4(candidates,target,0,flag,res,deque);
        return res;
    }
    private int sum = 0;
    private void backtrack4(int[]arr,int target,int index,boolean[]flag,List<List<Integer>>res,Deque<Integer> deque){
        //结束条件
        if(sum==target){
            res.add(new ArrayList<>(deque));
            return;
        }
        for(int i=index;i<arr.length&&arr[i]+sum<=target;i++){
            //如果在同一层中，同层的第一个节点已经被选过了，则跳过本次
            if(i>0&&arr[i]==arr[i-1]&&!flag[i-1]){
                continue;
            }
            flag[i]=true;
            sum+=arr[i];
            deque.push(arr[i]);
            //每个节点只能选择一次，回溯从下一位开始
            backtrack4(arr, target, i+1, flag, res, deque);
            int temp = deque.pop();
            flag[i] = false;
            sum -= temp;
        }
    }

    /**5.
     * lc216 组合总数3
     * @param k
     * @param n
     * @return
     * 找出所有相加之和为 n 的 k 个数的组合。组合中只允许含有 1 - 9 的正整数，并且每种组合中不存在重复的数字。
     * 所有数字都是正整数
     * 解集不包括重复的组合
     * 从1-9选择k个数，和为n
     */
    public List<List<Integer>> combinationSum3(int k, int n){
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> tempRes = new ArrayList<>();
        backtrack5(n,k,0,1,res,tempRes);
        return res;
    }
    private void backtrack5(int n,int k,int sum,int start, List<List<Integer>>res,List<Integer>tempRes){
        if(tempRes.size()==k){
            if(n==sum){
                res.add(new ArrayList<Integer>(tempRes));
                return;
            }
        }
        for (int i=start;i<=9;i++){
            sum += i;
            tempRes.add(i);
            backtrack5(n,k,sum,i+1,res,tempRes);
            sum -= i;
            tempRes.remove(tempRes.size()-1);
        }
    }


    /**6.
     * lc46 全排列
     * @param nums
     * @return
     */
    public List<List<Integer>> permute(int[] nums){
        List<List<Integer>> resList = new ArrayList<>();
        List<Integer> tempList = new ArrayList<>();
        backtrack6(nums,resList,tempList);
        return resList;

    }
    private void backtrack6(int[] nums,List<List<Integer>> resList,List<Integer> tempList){
        if(tempList.size()==nums.length){
            resList.add(new ArrayList<>(tempList));
            return;
        }
        for (int i=0;i<nums.length;i++){
            //tempList中包含nums中的数，排列问题不能出现重复数，跳过本次循环
            if(tempList.contains(nums[i])) continue;
            //其余的按框架来加入，删除
            tempList.add(nums[i]);
            backtrack6(nums,resList,tempList);
            tempList.remove(tempList.size()-1);
        }
    }

    /**7.
     * lc47 全排列2
     * @param nums
     * @return
     * 给定一个可包含重复数字的序列 nums ，按任意顺序返回所有不重复的全排列。
     */
    public List<List<Integer>> permuteUnique(int[] nums){
        //有重复数字的问题一般用一个Boolean数组
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> tempRes = new ArrayList<>();
        boolean[] used = new boolean[nums.length];
        Arrays.fill(used,false);
        Arrays.sort(nums);
        backtrack7(nums,used,tempRes,result);
        return result;

    }
    private void backtrack7(int[] nums,boolean[] used,List<Integer>tempRes,List<List<Integer>> result){
        if(tempRes.size()==nums.length){
            result.add(new ArrayList<>(tempRes));
            return;
        }
        for (int i=0;i<nums.length;i++){
            // used[i - 1] == true，说明同⼀树⽀nums[i - 1]使⽤过
            // used[i - 1] == false，说明同⼀树层nums[i - 1]使⽤过
            // 如果同⼀树层nums[i - 1]使⽤过则直接跳过
            if(used[i-1]==false&&nums[i]==nums[i-1]&&i>0){
                continue;
            }
            //同一个树支的nums[i]没用过，开始处理
            //同一树支做的事情
            if(used[i]==false){
                //标记同⼀树⽀nums[i]使⽤过，防止同一树支重复使用
                used[i]=true;
                tempRes.add(nums[i]);
                backtrack7(nums,used,tempRes,result);
                //回溯，说明同⼀树层nums[i]使⽤过，防止下一树层重复
                tempRes.remove(tempRes.size()-1);
                //回溯
                used[i] = false;
                //回溯完这一树支结束，本次循环结束，开始本树层中下一节点
            }

        }

    }


    /**8.
     * lc78 子集
     * @param nums
     * @return
     * 给定一个整数数组，其中元素互不相同，返回该数组中所有可能的子集（幂集）
     * 子集中不能包含重复的子集，可以按任意顺序返回解
     */
    public List<List<Integer>> subsets(int[] nums){
        List<List<Integer>> res  = new ArrayList<>();
        List<Integer> cur = new ArrayList<>();
        backtrack8(nums,0,res,cur);
        return res;


    }
    private void backtrack8(int[] nums,int start,List<List<Integer>> res,List<Integer> cur){
        // 当前位数时的cur直接加到res中，一开始是空集，后续每调用一层代表多一个位数的集合
        res.add(new ArrayList<>(cur));
        // 当前位从start开始（start以前的元素在前面的位中用过了），遍历到nums末尾
        for (int i=start;i<nums.length;i++){
            cur.add(nums[i]);
            // 当前位取nums[i]，继续从i+1开始遍历下一位，避免重复
            backtrack8(nums,i+1,res,cur);
            // 取消当前位的选择，下一轮循环重新选择一次当前位
            cur.remove(cur.size()-1);
        }
    }


    /**9.
     * lc90 子集2
     * @param nums
     * @return
     * 给定一个整数数组，其中包含重复元素，
     * 请返回该数组所有可能的幂集
     */
    public List<List<Integer>> subsetsWithDup(int[] nums){
        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> temp = new ArrayList<>();
        Arrays.sort(nums);
        int n = nums.length;
        // 使用 visited 数组来记录哪一个元素在当前路径中被使用了
        boolean[] visited = new boolean[n];
        //开始回溯
        backtrack9(nums,0,visited,ans, temp);
        return ans;

    }

    private void backtrack9(int[]nums,int start,boolean[]visited,List<List<Integer>> ans,List<Integer>temp){
        ans.add(new ArrayList<>(temp));
        for (int i=start;i<nums.length;i++){
            // 如果当前元素和前一个元素相同，而且前一个元素没有被访问，说明前一个相同的元素在当前层已经被用过了
            //跳过本次循环
            if(i>0 && nums[i]==nums[i-1]&& !visited[i-1]){
                continue;
            }
            visited[i] = true;
            //加入路径
            temp.add(nums[i]);
            backtrack9(nums,i+1,visited,ans,temp);
            //回溯
            visited[i] = false;
            temp.remove(temp.size()-1);

        }

    }










}
