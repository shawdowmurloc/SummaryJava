import java.util.Arrays;

public class SolutionShuangZhiZhen {
    /**
     * 双指针法习题合集
     * 1.lc27 移除元素
     * 2.lc26 删除有序数组中的重复项
     * 3.lc283 移动0
     * 4。lc611 有效三角形的个数
     */

    /**1.
     * lc27 移除元素
     * @param nums
     * @param val
     * @return
     * 快慢指针
     */
    public int removeElement(int[] nums, int val){
        int fast = 0;
        int slow ;
        for(slow = 0;fast<nums.length;fast++){
            if(nums[fast]!=val){
                nums[slow] = nums[fast];
                slow++;
            }
        }
        return slow;
    }

    /**2.
     * lc26 删除有序数组中的重复项
     * @param nums
     * @return
     * 给定有序数组，原地删除重复出现的元素，返回最后数组的长度
     */
    public int removeDuplicates(int[] nums){
        //双指针，前后指针
        int p = 0;
        int q = 1;
        while (q<nums.length){
            if(nums[q]!=nums[p]){
                nums[p+1] = nums[q];
                p++;
            }
            q++;

        }
        return p+1;
    }


    /**3.
     * lc283 移动0
     * @param nums
     * 给定一个数组 nums，编写一个函数将所有 0 移动到数组的末尾，同时保持非零元素的相对顺序。
     * 双指针
     */
    public void moveZeroes(int[] nums){
        int n = nums.length, left = 0, right = 0;
        while (right<n){
            if (nums[right] != 0) {
                swap(nums, left, right);
                left++;
            }
            right++;
        }
    }
    private void swap(int[]nums,int left,int right){
        int temp = nums[left];
        nums[left] = nums[right];
        nums[right] = temp;
    }


    /**4.
     * lc611 有效三角形的个数
     * 给定一个包含非负整数的数组，是统计其中可以组成三角形三条边的三元组个数。
     *
     */
    public int triangleNumber(int[] nums){
        int n = nums.length;
        Arrays.sort(nums);
        int ans = 0;
        for(int i=0;i<n;i++){
            int k=i;
            for (int j=i+1;j<n;j++){
                while(k+1<n && nums[k]<nums[i]+nums[j]){
                    k++;
                }
                ans += Math.max(k-j,0);
            }

        }
        return ans;

    }

}
