import java.util.*;

public class SolutionStackAndQueue {
    /**
     * 1.lc347 前k个高频元素
     * 2.lc225 用队列实现栈
     * 3.lc232 用栈实现队列
     * 4.lc20 有效括号
     * lc239 滑动窗口的最大值
     */

    /**1.
     * lc347 前k个高频元素  ****
     * @param nums
     * @param k
     * @return
     * 给数组nums和k，返回其中出现频率前k高的元素
     */
    public int[] topKFrequent(int[] nums, int k) {
        //1.对元素出现频率统计
        //2.对频率排序
        //3.找出前k个高频元素
        //结果数组
        int[] result = new int[k];
        // 构造 HashMap。Key：nums 中的每个元素；Value：对应元素出现的次数（即频率）
        Map<Integer, Integer> store = new HashMap<>();
        for(int num:nums){
            if(store.containsKey(num)){
                store.put(num,store.get(num)+1);
            }else{
                store.put(num,1);
            }
        }
        Set<Map.Entry<Integer, Integer>> entries = store.entrySet();
        //用小顶堆是因为统计是最大前k个元素，只有小顶堆每次将最小的元素弹出
        //最后小顶堆里累计的就是前k个最大元素
        // 根据map的value值正序排，相当于一个小顶堆
        //优先级队列其实就是一个披着队列外衣的堆
        PriorityQueue<Map.Entry<Integer, Integer>> queue = new PriorityQueue<>((o1, o2) -> o1.getValue() - o2.getValue());
        for (Map.Entry<Integer, Integer> entry : entries){
            queue.offer(entry);    //加入队列用offer
            if (queue.size() > k) {
                queue.poll();
            }

        }
        //然后倒叙把数放入数组中
        for (int i = k - 1; i >= 0; i--) {
            result[i] = queue.poll().getKey();   //弹出队列用poll
        }
        return result;
    }


    /**2.
     * lc225 用队列实现栈
     * 用一个队列就可以实现
     */
    Queue<Integer> queue;
    public void MyStack(){
        queue = new LinkedList<Integer>();
    }

    public void push1(int x) {
        int n = queue.size();
        queue.offer(x);
        for (int i = 0; i < n; i++) {
            queue.offer(queue.poll());
        }
    }

    public int pop1() {
        return queue.poll();

    }


    public int top1(){
        return queue.peek();

    }


    public boolean empty1() {
            return queue.isEmpty();

    }

    /**3.
     * lc232.用栈实现队列
     * 用两个栈实现
     */
    Deque<Integer> inStack;
    Deque<Integer> outStack;

    public void MyQueue() {
        inStack = new LinkedList<Integer>();
        outStack = new LinkedList<Integer>();
    }

    public void push2(int x) {
        inStack.push(x);
    }

    public int pop2() {
        if (outStack.isEmpty()) {
            in2out();
        }
        return outStack.pop();
    }

    public int peek2() {
        if (outStack.isEmpty()) {
            in2out();
        }
        return outStack.peek();
    }

    public boolean empty2() {
        return inStack.isEmpty() && outStack.isEmpty();
    }

    private void in2out() {
        while (!inStack.isEmpty()) {
            outStack.push(inStack.pop());
        }
    }

    /**4.
     * lc20 有效括号
     * @param s
     * @return
     * 给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串 s ，判断字符串是否有效。
     * 左括号必须用相同类型的右括号闭合。
     * 左括号必须以正确的顺序闭合。
     */
    public boolean isValid(String s){
        if(s.isEmpty()) return  true;
        Stack<Character> stack=new Stack<Character>();
        //压入栈内
        for (char c:s.toCharArray()){
            if(c=='('){
                stack.push(')');
            }else if(c=='{'){
                stack.push('}');
            }
            else if(c=='['){
                stack.push(']');
            }
            else if(stack.empty()||c!=stack.pop()){
                return false;
            }
        }
        if(stack.empty())
            return true;
        return false;

    }

    /**5.
     * lc239 滑动窗口的最大值
     * 给你一个整数数组 nums，有一个大小为k的滑动窗口从数组的最左侧移动到数组的最右侧。你只可以看到在滑动窗口内的 k个数字。滑动窗口每次只向右移动一位。
     * 返回滑动窗口中的最大值。
     *********经典单调栈问题************
     */
    class MyQueue {
        Deque<Integer> deque = new LinkedList<>();
        //弹出元素时，比较当前要弹出的数值是否等于队列出口的数值，如果相等则弹出
        //同时判断队列当前是否为空
        //如果窗口移除元素值等于单调队列的出口元素，那么队列弹出元素，
        //否则不用任何操作
        void poll(int val) {
            if (!deque.isEmpty() && val == deque.peek()) {
                deque.poll();
            }
        }
        //添加元素时，如果要添加的元素大于入口处的元素，就将入口元素弹出
        //保证队列元素单调递减
        //比如此时队列元素3,1，2将要入队，比1大，所以1弹出，此时队列：3,2
        //如果加入的值大于入口元素的值，那么就将队列入口的元素弹出，
        //直到加入的值小于等于队列入口元素的值
        void add(int val) {
            while (!deque.isEmpty() && val > deque.getLast()) {
                deque.removeLast();
            }
            deque.add(val);
        }
        //队列队顶元素始终为最大值
        int peek() {
            return deque.peek();
        }
    }

    public int[] maxSlidingWindow(int[] nums, int k) {
        if (nums.length == 1) {
            return nums;
        }
        int len = nums.length - k + 1;
        //存放结果元素的数组
        int[] res = new int[len];

        int num = 0;
        //自定义队列
        MyQueue myQueue = new MyQueue();
        //先将前k的元素放入队列
        for (int i = 0; i < k; i++) {
            myQueue.add(nums[i]);
        }
        res[num++] = myQueue.peek();
        for (int i = k; i < nums.length; i++) {
            //滑动窗口移除最前面的元素，移除是判断该元素是否放入队列
            myQueue.poll(nums[i - k]);
            //滑动窗口加入最后面的元素
            myQueue.add(nums[i]);
            //记录对应的最大值
            res[num++] = myQueue.peek();
        }
        return res;
    }




}
