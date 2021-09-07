import java.util.*;

/**
 * 树的问题递归思路：
 * 1.确定递归函数的参数和返回值
 * 2.确定终止条件
 * 3.确定单层递归的逻辑
 */

public class SolutionTree {

    /****************************************************/
    //二叉树的遍历问题
    /***************************************************/

    /**1.
     * lc102二叉树的层序遍历
     * @param root
     * @return
     */
    public List<List<Integer>> resList = new ArrayList<List<Integer>>();
    public List<List<Integer>> levelOrder(TreeNode root){
        //递归方式实现
        //checkFun01(root,0);

        //迭代方式实现
        checkFunc(root);
        return resList;

    }

    //DFS--递归方式
    public void checkFun01(TreeNode node, Integer deep) {
        if (node == null) return;
        deep++;

        if (resList.size() < deep) {
            //当层级增加时，list的Item也增加，利用list的索引值进行层级界定
            List<Integer> item = new ArrayList<Integer>();
            resList.add(item);
        }
        resList.get(deep - 1).add(node.val);

        checkFun01(node.left, deep);
        checkFun01(node.right, deep);
    }

    //BFS--迭代方式--借助队列

    public void checkFunc(TreeNode node){
        if(node==null){
            return;
        }
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        //加入根节点的值
        queue.offer(node);

        //先保证队列不为空
        while (!queue.isEmpty()){
            //不空的情况下，构建临时列表
            List<Integer> itemList = new ArrayList<>();

            int len = queue.size();
            while(len>0){
                //poll出队列里的节点
                TreeNode tmpNode = queue.poll();
                //临时列表里加入当前出队列的值
                itemList.add(tmpNode.val);
                //有左子节点，就加入队列
                if(tmpNode.left!=null) queue.offer(tmpNode.left);
                //有右子节点，就加入队列
                if(tmpNode.right !=null) queue.offer(tmpNode.right);
                len--;
            }
            resList.add(itemList);
        }
    }


    /**2.
     * lc107 二叉树的层序遍历2
     */


    /**
     * 自底向上层序遍历，其实就是相当于上一题把最后的list集合倒叙输出就好了~
     */
    /**
     * 解法：队列，迭代。
     * 层序遍历，再翻转数组即可。
     */
    public List<List<Integer>> solution1(TreeNode root) {
        List<List<Integer>> list = new ArrayList<>();
        Deque<TreeNode> que = new LinkedList<>();

        if (root == null) {
            return list;
        }

        que.offerLast(root);
        while (!que.isEmpty()) {
            List<Integer> levelList = new ArrayList<>();

            int levelSize = que.size();
            for (int i = 0; i < levelSize; i++) {
                TreeNode peek = que.peekFirst();
                levelList.add(que.pollFirst().val);

                if (peek.left != null) {
                    que.offerLast(peek.left);
                }
                if (peek.right != null) {
                    que.offerLast(peek.right);
                }
            }
            list.add(levelList);
        }

        List<List<Integer>> result = new ArrayList<>();
        for (int i = list.size() - 1; i >= 0; i-- ) {
            result.add(list.get(i));
        }

        return result;
    }




    /**3.
     * lc199 二叉树的右视图
     * 给定一棵二叉树，想象自己站在它的右侧，按照从顶部到底部的顺序，返回从右侧所能看到的节点值。
     */
    public List<Integer> rightSideView(TreeNode root){
        Deque<TreeNode> que = new LinkedList<>();
        List<Integer> list = new ArrayList<>();
        if(root==null){
            return list;
        }
        que.offerLast(root);
        while (!que.isEmpty()){
            int levelSize = que.size();
            for (int i=0;i<levelSize;i++){
                TreeNode poll = que.pollFirst();
                if(poll.left!=null){
                    que.addLast(poll.left);
                }
                if(poll.right!=null){
                    que.addLast(poll.right);
                }
                if(i==levelSize-1){
                    list.add(poll.val);
                }
            }
        }
        return list;
    }


    /**4.
     * lc637 二叉树的层平均值
     * 给定一个非空二叉树, 返回一个由每层节点平均值组成的数组。
     * 解法：队列，迭代。
     * 层序遍历的时候把一层求个总和在取一个均值
     */
    public List<Double> averageOfLevels(TreeNode root){
        List<Double> list = new ArrayList<>();
        Deque<TreeNode> que = new LinkedList<>();
        if(root==null) return list;
        que.offerLast(root);
        while (!que.isEmpty()){
            int levelSize = que.size();
            double levelSum = 0.0;
            //TreeNode peek = que.peekFirst();
            for (int i=0;i<levelSize;i++){
                TreeNode poll = que.pollFirst();
                levelSum += poll.val;
                if(root.left!=null) que.addLast(poll.left);
                if(poll.right!=null) que.addLast(poll.right);
            }
            list.add(levelSum/levelSize);
        }
        return list;

    }

    /**5.
     * lc515 找到在每个树行中的最大值
     * 给定一棵二叉树的根节点 root ，请找出该二叉树中每一层的最大值。
     * 依旧套用模板，多了一个判断大小 设定一个max_Val, 比较
     */
    public List<Integer> largestValues(TreeNode root){
        List<Integer> res = new ArrayList<>();
        Deque<TreeNode> que = new LinkedList<>();
        if(root==null) return res;
        que.offerLast(root);
        while (!que.isEmpty()){
            int size = que.size();
            ///
            int max_Val = Integer.MIN_VALUE;
            ///
            for (int i=0;i<size;i++){
                TreeNode node = que.pollFirst();
                max_Val = node.val>max_Val? node.val:max_Val;
                if(node.left!=null) que.addLast(node.left);
                if(node.right!=null) que.addLast(node.right);
            }
            res.add(max_Val);
        }
        return res;

    }

    /**6.
     * lc116 填充每个节点的下一个右侧节点指针
     * 填充它的每个 next 指针，让这个指针指向其下一个右侧节点。如果找不到下一个右侧节点，则将 next 指针设置为 NULL。
     */
    public NodePer connect(NodePer root){


        return root;
    }

    /***************************************************/
    //二叉树的属性问题
    /***************************************************/


    /**1.
     * lc101 对称二叉树
     * 给定一个二叉树，检查它是否是镜像对称的。
     */
    public boolean isSymmetric(TreeNode root){
        if (root==null) return false;
        return check(root.left,root.right);
    }
    public boolean check(TreeNode left,TreeNode right){
        if(left==null&&right==null) return true;
        if (left==null||right==null) return false;
        return left.val ==right.val && check(left.left,right.right) && check(left.right,right.left);
    }


    /**2.
     * lc104 二叉树的最大深度
     * 给定一个二叉树，找出其最大深度。
     * 二叉树的深度为根节点到最远叶子节点的最长路径上的节点数。
     * 二叉树的后序遍历找左右的最大深度，然后进行比较
     */
    public int maxDepth(TreeNode root){
        if (root==null) return 0;
        int maxLeft = maxDepth(root.left);
        int maxRight = maxDepth(root.right);
        return Math.max(maxLeft,maxRight);
    }



    /**3.
     *lc111 二叉树的最小深度
     * 给定一个二叉树，找出其最小深度。
     * 最小深度是从根节点到最近叶子节点的最短路径上的节点数量。
     */
    public int getDepth(TreeNode root){
        if(root == null) return 0;
        int m1 = getDepth(root.left);
        int m2 = getDepth(root.right);
        //1.如果左子树有为空的情况
        if(root.left==null&&root.right!=null){
            return 1+m2;
        }
        //2.如果右子树有为空的情况
        if(root.right==null&&root.left!=null){
            return 1+m1;
        }
        return  Math.min(m1,m2) + 1;

    }
    public int minDepth(TreeNode root){
        return getDepth(root);
    }


    /**4.
     *lc222 完全二叉树的节点个数
     * 完全二叉树中，除了最底层节点可能没填满外，其余每层节点数都达到最大值，
     * 并且最下面一层的节点都集中在该层最左边的若干位置。
     * 若最底层为第 h 层，则该层包含 1~ 2h 个节点。
     * 完全二叉树只有两种情况，情况一：就是满二叉树，情况二：最后一层叶子节点没有满。
     * 对于情况一，可以直接用 2^树深度 - 1 来计算，注意这里根节点深度为1。
     * 对于情况二，分别递归左孩子，和右孩子，递归到某一深度一定会有左孩子或者右孩子为满二叉树，然后依然可以按照情况1来计算。
     * 如果整个树不是满二叉树，就递归其左右孩子，直到遇到满二叉树为止，用公式计算这个子树（满二叉树）的节点数量。
     */
    /**
     * 针对完全二叉树的解法
     * 满二叉树的结点数为：2^depth - 1
     * @param root
     * @return
     */
    public int countNodes(TreeNode root){
        if(root==null) return 0;
        int leftDepth = getDepth2(root.left);
        int rightDepth = getDepth2(root.right);
        if(leftDepth==rightDepth){
            //左子树是满二叉树
            // 2^leftDepth其实是 （2^leftDepth - 1） + 1 ，左子树 + 根结点
            return (1 << leftDepth) + countNodes(root.right);
            //右子树是满二叉树
        }else {
            return (1 << rightDepth) + countNodes(root.left);
        }

    }

    /**
     * 求二叉树深度
     * @param root
     * @return
     */
    private int getDepth2(TreeNode root){
        int depth = 0;
        while (root!=null){
            root = root.left;
            depth++;
        }
        return depth;

    }


    /**5.
     *lc110 平衡二叉树
     * 给定一个二叉树，判断它是否是高度平衡的二叉树。
     * 本题中高度平衡二叉树是一个二叉树每个节点 的左右两个子树的高度差的绝对值不超过 1 。
     */

    public boolean isBalanced(TreeNode root){
        if (root == null) return true;
        return Math.abs(depth(root.left)-depth(root.right))<=1 && isBalanced(root.left) && isBalanced(root.right);
    }
    private int depth(TreeNode root){
        if (root==null) return 0;
        return Math.max(depth(root.left),depth(root.right))+1;
    }

    /**6.
     *lc257 二叉树的所有路径
     * 给定一个二叉树，返回所有从根节点到叶子节点的路径。
     */
    public List<String> binaryTreePaths(TreeNode root){
        return new ArrayList<>();

    }


    /**7.
     *lc404 左叶子之和
     * 计算给定二叉树的所有左叶子之和。
     * 关键在于搞清什么是左叶子，
     * 该节点的左节点不为空，该节点的左节点的左节点为空，该节点的左节点的右节点为空，则找到了一个左叶子
     */
    //递归法
    public int sumOfLeftLeaves(TreeNode root){
        //后序遍历
        if(root==null) return 0;
        int midVal = 0;
        //该节点的左节点不为空，该节点的左节点的左节点为空，该节点的左节点的右节点为空，则找到了一个左叶子
        if(root.left!=null&&root.left.left==null&&root.left.right==null){
            midVal = root.left.val;
        }
        return midVal + sumOfLeftLeaves(root.left) + sumOfLeftLeaves(root.right);
    }



    /**8.
     *lc513 找到树左下角的值
     * 请找出该二叉树的 最底层 最左边 节点的值。‘
     * 层序遍历
     */
    //迭代法
    public int findBottomLeftValue(TreeNode root){
        Deque<TreeNode> que = new LinkedList<>();
        if (root==null) return 0;
        que.offerLast(root);
        int res = 0;
        while (!que.isEmpty()){
            //size一定要写死
            int size = que.size();
            for (int i=0;i<size;i++){
                TreeNode tmp = que.pollFirst();
                if(i==0) res = tmp.val;
                if(tmp.left!=null) que.addLast(tmp.left);
                if(tmp.right!=null) que.addLast(tmp.right);
            }
        }
        return res;
    }

    /**9.
     *lc112 路径总和
     * 给你二叉树的根节点root 和一个表示目标和的整数argetSum ，
     * 判断该树中是否存在 根节点到叶子节点 的路径，这条路径上所有节点值相加等于目标和targetSum 。
     */
    public boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null) {
            return false;
        }
        targetSum -= root.val;
        // 叶子结点
        if (root.left == null && root.right == null) {
            return targetSum == 0;
        }
        if (root.left != null) {
            boolean left = hasPathSum(root.left, targetSum);
            if (left) {// 已经找到
                return true;
            }
        }
        if (root.right != null) {
            boolean right = hasPathSum(root.right, targetSum);
            if (right) {// 已经找到
                return true;
            }
        }
        return false;
    }



    /***************************************************/
    //二叉树的修改与构造
    /***************************************************/

    /**
     *lc226 翻转二叉树
     */
    //递归法
    public TreeNode invertTree(TreeNode root){
        if(root==null) return null;
        swap(root);
        invertTree(root.left);
        invertTree(root.right);
        return  root;

    }
    private void swap(TreeNode root){
        TreeNode tmp = root.left;
        root.left = root.right;
        root.right = tmp;
    }



    /**
     *lc106 从中序和后序遍历序列构造二叉树
     */
    public TreeNode buildTree(int[] inorder, int[] postorder) {
        return buildTree1(inorder, 0, inorder.length, postorder, 0, postorder.length);
    }
    public TreeNode buildTree1(int[] inorder, int inLeft, int inRight,
                               int[] postorder, int postLeft, int postRight) {
        // 没有元素了
        if (inRight - inLeft < 1) {
            return null;
        }
        // 只有一个元素了
        if (inRight - inLeft == 1) {
            return new TreeNode(inorder[inLeft]);
        }
        // 后序数组postorder里最后一个即为根结点
        int rootVal = postorder[postRight - 1]; //根节点
        TreeNode root = new TreeNode(rootVal);
        int rootIndex = 0;
        // 根据根结点的值找到该值在中序数组inorder里的位置
        for (int i = inLeft; i < inRight; i++) {
            if (inorder[i] == rootVal) {
                rootIndex = i;
            }
        }
        // 根据rootIndex划分左右子树
        root.left = buildTree1(inorder, inLeft, rootIndex,
                postorder, postLeft, postLeft + (rootIndex - inLeft));
        root.right = buildTree1(inorder, rootIndex + 1, inRight,
                postorder, postLeft + (rootIndex - inLeft), postRight - 1);
        return root;
    }





    /**
     *lc654 最大二叉树
     */
    public TreeNode constructMaximumBinaryTree(int[] nums) {
        return constructMaximumBinaryTree1(nums, 0, nums.length);
    }

    public TreeNode constructMaximumBinaryTree1(int[] nums,int leftIndex,int rightindex){
        if(rightindex-leftIndex<1){ //已经没有元素了
            return null;
        }
        if(rightindex-leftIndex==1){ //只有一个元素
            return new TreeNode(nums[leftIndex]);
        }
        int maxIndex = leftIndex;
        int maxVal = nums[leftIndex];
        for (int i=leftIndex+1;i<rightindex;i++){
            if(nums[i]>maxVal){
                maxVal = nums[i];
                maxIndex = i;
            }
        }
        TreeNode root = new TreeNode(maxVal);
        root.left = constructMaximumBinaryTree1(nums,leftIndex,maxIndex);
        root.right = constructMaximumBinaryTree1(nums,maxIndex+1,rightindex);
        return root;
    }


    /**
     * lc617 合并二叉树
     */
    public TreeNode mergeTrees(TreeNode t1, TreeNode t2){
        if(t1==null) return t2;
        if(t2==null) return t1;
        TreeNode newRoot = new TreeNode(t1.val+t2.val);
        newRoot.left = mergeTrees(t1.left,t2.left);
        newRoot.right = mergeTrees(t1.right,t2.right);
        return  newRoot;
    }

    /******************************************************/
    //二叉搜索树
    /******************************************************/
    /**
     * lc700 二叉搜素树中的搜素
     * BTS左子树值一定小于当前节点，右子树值一定大于当前节点
     * 二叉搜索树节点具有有序性
     */
    //递归法
    //如果搜索一条边，递归函数一定要return
    public TreeNode searchBST(TreeNode root, int val){
        if(root==null||root.val==val){
            return root;
        }
        //找到目标节点，立即return
        if(root.val>val) return searchBST(root.left,val);
        if(root.val<val) return searchBST(root.right,val);
        return null;

    }
    //迭代法
    //对于二叉搜索树迭代法不需要回溯，因为节点有序性已经帮我们确定了搜索方向
    public TreeNode searchBst(TreeNode treeNode,int val){
        while (treeNode!=null){
            if(treeNode.val>val) treeNode = treeNode.left;
            else if(treeNode.val<val) treeNode = treeNode.right;
            else return treeNode;
        }
        return null;
    }


    /**
     * lc98 验证二叉搜索树
     * 给定一个二叉树，判断其是否是一个有效的二叉搜索树。
     * 中序遍历下，二叉搜索树的节点数值是递增排序
     */
    List<Integer>arr = new ArrayList<>();
    public boolean isValidBST(TreeNode root){
        tranverSal1(root);
        for (int i=1;i<arr.size();i++){
            if(arr.get(i)<=arr.get(i-1)){
                return false;
            }
        }
        return true;
    }
    public void tranverSal1(TreeNode treeNode){
        if(treeNode==null) return;
        tranverSal1(treeNode.left);
        arr.add(treeNode.val);
        tranverSal1(treeNode.right);

    }


    /**
     * lc530 二叉搜索树的最小绝对差
     * 遇到在二叉搜索树上求什么最值啊，差值之类的，就把它想成在一个有序数组上求最值，求差值，这样就简单多了。
     */
    List<Integer> arr2 = new ArrayList<>();
    public int getMinimumDifference(TreeNode root){
        tranverSal2(root);  //直接转化成有序list
        int res = Integer.MAX_VALUE;
        for (int i=1;i<arr2.size();i++){
            res = Math.min(res,arr2.get(i)-arr2.get(i-1));
        }
        return res;

    }
    public void tranverSal2(TreeNode treeNode){
        if(treeNode==null) return;
        tranverSal2(treeNode.left);
        arr2.add(treeNode.val);
        tranverSal2(treeNode.right);
    }

    /**
     * lc538 把二叉搜索树转化为累加树
     * 将其转换为累加树（Greater Sum Tree），使每个节点 node 的新值等于原树中大于或等于 node.val 的值之和。
     */
    int sum;
    public TreeNode convertBST(TreeNode root){
        sum = 0;
        convertBst1(root);
        return root;

    }
    //按右中左的顺序遍历，累加
    public void convertBst1(TreeNode treeNode){
        if(treeNode==null) return;
        convertBst1(treeNode.right);
        sum += treeNode.val;
        treeNode.val = sum;
        convertBst1(treeNode.left);
    }






}

// Definition for a Node
//完美二叉树，所有叶子节点都在同一层
//每个父节点有两个子节点
class NodePer {
    public int val;
    public NodePer left;
    public NodePer right;
    public NodePer next;

    public NodePer() {}

    public NodePer(int _val) {
        val = _val;
    }

    public NodePer(int _val, NodePer _left, NodePer _right, NodePer _next) {
        val = _val;
        left = _left;
        right = _right;
        next = _next;
    }
};


