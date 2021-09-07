import java.util.List;

public class SolutionLianbiao {

    /**
     * lc203 移除链表元素
     */
    public ListNode removeElements(ListNode head, int val){
        ListNode dummyHead = new ListNode(0);// 设置一个虚拟头结点
        dummyHead.next = head;// 将虚拟头结点指向head，这样方面后面做删除操作
        ListNode cur = dummyHead;
        while (cur.next!=null){
            if(cur.next.val==val){
                //ListNode tmp = cur.next;
                cur.next = cur.next.next;
            }else {
                cur = cur.next;
            }
        }
        return dummyHead.next;
    }

    /**
     * lc206 反转链表
     */
    //双指针法
    public ListNode reverseList(ListNode head){
        ListNode pre = null;//定义一个pre指针，初始化为null。
        ListNode next = null;
        ListNode cur = head;//定义一个cur指针，指向头结点
        while (cur!=null){
            next = cur.next;//要把 cur.next 节点用next指针保存一下
            cur.next = pre; //接下来要改变 cur.next 的指向了，将cur.next 指向pre ，此时已经反转了第一个节点了。
            pre = cur; //继续移动pre和cur指针。
            cur = next;
        }
        //cur 指针已经指向了null，循环结束，链表也反转完毕了。此时我们return pre指针就可以了，pre指针就指向了新的头结点。
        return pre;
    }


    /**
     * lc19 删除链表的倒数第N个节点
     * 双指针，快慢指针
     */
    public ListNode removeNthFromEnd(ListNode head, int n){
        ListNode dum = new ListNode(-1);
        dum.next = head;
        ListNode fast  = dum;
        ListNode slow = dum;
        while (n-->0){
            fast = fast.next;
        }
        while (fast.next!=null){
            slow = slow.next;
            fast = fast.next;
        }
        slow.next = slow.next.next;
        return dum.next;

    }




























}
   class ListNode {
      int val;
      ListNode next;
      ListNode() {}
      ListNode(int val) { this.val = val; }
      ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}
