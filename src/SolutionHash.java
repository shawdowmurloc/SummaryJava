import java.util.*;

public class SolutionHash {


    /**
     * lc242 有效的字母异位词
     * lc383 赎金信
     * lc49字母异位词分组
     * lc1002 查找常用字符
     * lc349 两个数组的交集
     * lc202 快乐数
     * lc1 两数之和
     * lc15 三数之和
     * lc454 四数相加
     */

    /**
     * lc242 有效的字母异位词
     */
    public boolean isAnagram(String s, String t){
        if(s.length()!=t.length()) return false;
        int[] arr = new int[26];
        char[] s_arr = s.toCharArray();
        char[] t_arr = t.toCharArray();
        for (int i=0;i<s_arr.length;i++){
            // 并不需要记住字符a的ASCII，只要求出一个相对数值就可以了
            int index = s_arr[i]-'a'; //索引
            arr[index] += 1;
        }
        for (int i=0;i<t_arr.length;i++){
            int index  = t_arr[i]-'a';
            arr[index] -= 1; //当前索引
            if(arr[index]!=0) return false;
        }
        // arr数组所有元素都为零0，说明字符串s和t是字母异位词
        return true;
    }

    /**
     * lc383 赎金信 与上题类似
     * @param ransomNote
     * @param magazine
     * @return
     */
    public boolean canConstruct(String ransomNote, String magazine){
        //记录杂志字符串出现的字符次数
        int[] arr = new int[26];
        int temp;
        for (int i=0;i<magazine.length();i++){
            temp = magazine.charAt(i)-'a';
            arr[temp]++;
        }
        for (int i=0;i<ransomNote.length();i++){
            temp = ransomNote.charAt(i)-'a';
            //对于赎金中的每一个字符都在数组中查找
            //找到相应的位置-1，找不到返回False
            if(arr[temp]>0) arr[temp]--;
            else return false;
        }
        return true;
    }

    /**
     * lc49字母异位词分组
     * 给定一个字符串数组，将字母异位词组合在一起。可以按任意顺序返回结果列表。
     * 字母异位词指字母相同，但排列不同的字符串。
     */
    public List<List<String>> groupAnagrams(String[] strs){
        //由于互为字母异位词的两个字符串包含的字母相同，
        //因此对两个字符串分别进行排序之后得到的字符串一定是相同的，
        //故可以将排序之后的字符串作为哈希表的键。
        Map<String, List<String>> map = new HashMap<String, List<String>>();
        for (String str:strs){
            char[] charArr = str.toCharArray();
            //排序
            Arrays.sort(charArr);
            String key = new String(charArr);
            List<String> list = map.getOrDefault(key,new ArrayList<String>());
            list.add(str);
            map.put(key,list);
        }
        return  new ArrayList<List<String>>(map.values());

    }


    /**
     * lc1002 查找常用字符
     */
    public List<String> commonChars(String[] A){
        List<String> res = new ArrayList<>();
        if(A.length==0) return res;
        int[] hash = new int[26];// 用来统计所有字符串里字符出现的最小频率
        for (int i=0;i<A[0].length();i++){ // 用第一个字符串给hash初始化
            hash[A[0].charAt(i)-'a']++;
        }
        // 统计除第一个字符串外字符的出现频率
        for (int i=1;i<A.length;i++){
            int[] hashOtherStr = new int[26];
            for (int j=0;j<A[i].length();j++){
                hashOtherStr[A[i].charAt(j)-'a']++;
            }
            // 更新hash，保证hash里统计26个字符在所有字符串里出现的最小次数
            for (int k=0;k<26;k++){
                hash[k] = Math.min(hash[k],hashOtherStr[k]);
            }
        }
        for (int i=0;i<26;i++){
            while (hash[i]!=0){
                // 注意这里是while，多个重复的字符
                char c = (char)(i+'a');
                res.add(String.valueOf(c));
                hash[i]--;
            }
        }
        return res;
    }

    /**
     * lc349 两个数组的交集
     * @param nums1
     * @param nums2
     * @return
     * 使用数组来做哈希的题目，是因为题目都限制了数值的大小
     * 如果哈希值比较少、特别分散、跨度非常大，使用数组就造成空间的极大浪费。
     */

    public int[] intersection(int[] nums1, int[] nums2){
        if (nums1 == null || nums1.length == 0 || nums2 == null || nums2.length == 0) {
            return new int[0];
        }
        Set<Integer> set1 = new HashSet<>();
        Set<Integer> resSet = new HashSet<>();
        //遍历数组1
        for (int i:nums1){
            set1.add(i);
        }
        //遍历数组2的过程中判断哈希表中是否存在该元素
        for (int i:nums2){
            if(set1.contains(i)){
                resSet.add(i);
            }
        }
        int[] resArr = new int[resSet.size()];
        int index = 0;
        //结果转为矩阵
        for (int i:resSet){
            resArr[index++] = i;
        }
        return resArr;
    }


    /**
     * lc202 快乐数
     * 「快乐数」定义为：对于一个正整数，每一次将该数替换为它每个位置上的数字的平方和，
     * 然后重复这个过程直到这个数变为 1，也可能是 无限循环 但始终变不到 1。如果 可以变为  1，
     * 那么这个数就是快乐数。
     */
    public boolean isHappy(int n){
        //本题主要关注在求快乐数的过程中，n是否重复出现，如果重复出现一定不能是快乐数
        Set<Integer> record = new HashSet<>();
        while (n!=1 && !record.contains(n)){
            record.add(n);
            n = getNextNum(n);
        }
        return  n==1;
    }
    private int getNextNum(int n){
        int res = 0;
        while (n>0){
            int temp = n%10;
            res += temp*temp;
            n = n/10;
        }
        return res;
    }


    /**
     * lc1 两数之和
     */
    public int[] twoSum(int[] nums, int target){
        //Map<数值，索引下标>
        Map<Integer,Integer> map = new HashMap<>();
        for (int i=0;i<nums.length;i++){
            if(map.containsKey(target-nums[i])){//target-nums[i]是否在map中
                //在的话直接返回
                return new int[]{map.get(target-nums[i]),i};
            }
            map.put(nums[i],i);
        }
        return new int[0];
    }

    /**
     * lc454 四数相加
     * 给定四个包含整数的数组列表 A , B , C , D ,
     * 计算有多少个元组 (i, j, k, l) ，使得 A[i] + B[j] + C[k] + D[l] = 0。
     * 关键在于两两分组
     */
    public int fourSumCount(int[] A, int[] B, int[] C, int[] D){
        Map<Integer,Integer> map = new HashMap<>();
        int res = 0;
        //两两分组，先求AB的和，放入Hash表，然后再在CD里面找AB和的相反数
        for (int i=0;i<A.length;i++){
            for (int j=0;j<B.length;j++){
                int sumAB = A[i]+B[j];
                if(map.containsKey(sumAB)) map.put(sumAB,map.get(sumAB)+1);
                else map.put(sumAB,1);
            }
        }
        for (int i=0;i<C.length;i++){
            for (int j=0;j<D.length;j++){
                //sumCD如果是sumAB的相反数
                int sumCD = -(C[i]+D[j]);;
                if(map.containsKey(sumCD)) res += map.get(sumCD);
            }
        }
        return  res;
    }

    /**
     * lc15 三数之和
     * 给你一个包含 n 个整数的数组nums，判断nums中是否存在三个元素 a，b，c ，使得a + b + c = 0 ？
     * 请你找出所有和为 0 且不重复的三元组。
     */
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums);
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > 0) {
                return result;
            }
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            int left = i + 1;
            int right = nums.length - 1;
            while (right > left) {
                int sum = nums[i] + nums[left] + nums[right];
                if (sum > 0) {
                    right--;
                } else if (sum < 0) {
                    left++;
                } else {
                    result.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    while (right > left && nums[right] == nums[right - 1]) right--;
                    while (right > left && nums[left] == nums[left + 1]) left++;

                    right--;
                    left++;
                }
            }
        }
        return result;
    }
}
