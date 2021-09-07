import java.util.Stack;

public class SolutionDanDiaoZhan {
    //通常是一维数组，要寻找任一个元素的右边或者左边第一个比自己大或者小的元素的位置，此时我们就要想到可以用单调栈了。

    /**
     * lc739 每日温度
     */
    public  int[] dailyTemperatures(int[] temperatures){
        Stack<Integer> stack = new Stack<>();
        int[] res = new int[temperatures.length];
        for (int i=0;i<temperatures.length;i++){
            /**
             * 去除下标进行元素值的比较
             * 单调栈里只需要存放元素的下标i就可以了，如果需要使用对应的元素，直接T[i]就可以获取。
             */
            while (!stack.isEmpty()&&temperatures[i]>temperatures[stack.peek()]){
                int preIndex = stack.pop();
                res[preIndex] = i-preIndex;
            }
            /**
             * 注意放进去的是元素索引
             */
            stack.push(i);
        }
        return res;
    }

}
