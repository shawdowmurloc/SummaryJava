import java.util.*;

public class test2 {
    public static void main(String[] args) {
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        arrayList.add(-1);
        arrayList.add(3);
        arrayList.add(3);
        arrayList.add(-5);
        arrayList.add(7);
        arrayList.add(4);
        arrayList.add(-9);
        arrayList.add(-7);
        System.out.println("原始数组:");
        System.out.println(arrayList);
        // void reverse(List list)：反转
        Collections.reverse(arrayList);
        System.out.println("Collections.reverse(arrayList):");
        System.out.println(arrayList);


        Collections.rotate(arrayList, 4);
        System.out.println("Collections.rotate(arrayList, 4):");
        System.out.println(arrayList);

        // void sort(List list),按自然排序的升序排序
        Collections.sort(arrayList);
        System.out.println("Collections.sort(arrayList):");
        System.out.println(arrayList);

        // void shuffle(List list),随机排序
        Collections.shuffle(arrayList);
        System.out.println("Collections.shuffle(arrayList):");
        System.out.println(arrayList);

        // void swap(List list, int i , int j),交换两个索引位置的元素
        Collections.swap(arrayList, 2, 5);
        System.out.println("Collections.swap(arrayList, 2, 5):");
        System.out.println(arrayList);

        // 定制排序的用法
        Collections.sort(arrayList, new Comparator<Integer>() {

            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        });
        System.out.println("定制排序后：");
        System.out.println(arrayList);


    }
}

class test3{
    public static void main(String[] args) {
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        arrayList.add(-1);
        arrayList.add(3);
        arrayList.add(3);
        arrayList.add(-5);
        arrayList.add(7);
        arrayList.add(4);
        arrayList.add(-9);
        arrayList.add(-7);
        ArrayList<Integer> arrayList2 = new ArrayList<Integer>();
        arrayList2.add(-3);
        arrayList2.add(-5);
        arrayList2.add(7);
        System.out.println("原始数组:");
        System.out.println(arrayList);

        System.out.println("Collections.max(arrayList):");
        System.out.println(Collections.max(arrayList));

        System.out.println("Collections.min(arrayList):");
        System.out.println(Collections.min(arrayList));

        System.out.println("Collections.replaceAll(arrayList, 3, -3):");
        Collections.replaceAll(arrayList, 3, -3);
        System.out.println(arrayList);

        System.out.println("Collections.frequency(arrayList, -3):");
        System.out.println(Collections.frequency(arrayList, -3));

        System.out.println("Collections.indexOfSubList(arrayList, arrayList2):");
        System.out.println(Collections.indexOfSubList(arrayList, arrayList2));

        System.out.println("Collections.binarySearch(arrayList, 7):");
        // 对List进行二分查找，返回索引，List必须是有序的
        Collections.sort(arrayList);
        System.out.println(Collections.binarySearch(arrayList, 7));

    }

}
class test4{
    public static void main(String[] args) {
        // *************排序 sort****************
        int a[] = { 1, 3, 2, 7, 6, 5, 4, 9 };
        // sort(int[] a)方法按照数字顺序排列指定的数组。
        Arrays.sort(a);
        System.out.println("Arrays.sort(a):");
        for (int i : a) {
            System.out.print(i);
        }
        // 换行
        System.out.println();

        // sort(int[] a,int fromIndex,int toIndex)按升序排列数组的指定范围
        int b[] = { 1, 3, 2, 7, 6, 5, 4, 9 };
        Arrays.sort(b, 2, 6);
        System.out.println("Arrays.sort(b, 2, 6):");
        for (int i : b) {
            System.out.print(i);
        }
        // 换行
        System.out.println();

        int c[] = { 1, 3, 2, 7, 6, 5, 4, 9 };
        // parallelSort(int[] a) 按照数字顺序排列指定的数组(并行的)。同sort方法一样也有按范围的排序
        Arrays.parallelSort(c);
        System.out.println("Arrays.parallelSort(c)：");
        for (int i : c) {
            System.out.print(i);
        }
        // 换行
        System.out.println();

        // parallelSort给字符数组排序，sort也可以
        char d[] = { 'a', 'f', 'b', 'c', 'e', 'A', 'C', 'B' };
        Arrays.parallelSort(d);
        System.out.println("Arrays.parallelSort(d)：");
        for (char d2 : d) {
            System.out.print(d2);
        }
        // 换行
        System.out.println();

    }
}

class test5 {
    public static void main(String[] args) throws Exception{
        List<String> list = new ArrayList<>();
        list.add("Java");
        list.add("Java虚拟机");
        list.add("Java中文社群");
        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
            String str = (String) iterator.next();
            if (str.equals("Java中文社群")) {
                iterator.remove();
            }
        }
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
        System.out.println("Over");
    }
}
class test6 {
    public static void main(String[] args) throws Exception{
        ArrayList al= new ArrayList();
        al.add("red");
        al.add("green");
        al.add("yellow");
        al.add("black");
        al.add("white");
        al.add("blue");
        al.add("cyan");
        al.add("grey");
        Iterator it = al.iterator();
        while(it.hasNext()){
            System.out.println(it.next());
        }
    }
}



class MyScort {
    public static void main(String[] args) {
        List<String> list = new ArrayList<String>();
        list.add("apple");
        list.add("grape");
        list.add("banana");
        list.add("pear");
        for(int j = 0;j<list.size()-1;j++){
            for(int i = 0;i<list.size()-1-j;i++){
                if(list.get(i).compareTo(list.get(i+1))>0){
                    String temp = list.get(i);
                    list.set(i, list.get(i+1));
                    list.set(i+1, temp);
                }
            }
        }

        System.out.print("排序后的顺序：");
        for(int i = 0;i<list.size();i++){
            System.out.print(list.get(i)+" ");
        }
        System.out.println();
        System.out.println("集合中最大的元素是"+list.get(0));
        System.out.println("集合中最小的元素是"+list.get(3));
    }
}


class FindRepeatChar {
    public static void main(String[] args) {
        String strInput = new String("aavzcadfdsfsdhshgWasdfasdf");
        doString(strInput);
    }

    public static void doString(String strInput) {
        char[] chars = strInput.toCharArray();
        ArrayList lists = new ArrayList();
        TreeSet set = new TreeSet();
        for (int i = 0; i < chars.length; i++) {
            lists.add(String.valueOf(chars[i]));
            set.add(String.valueOf(chars[i]));
        }
        System.out.println(set);
        Collections.sort(lists);
        System.out.println(lists);
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < lists.size(); i++) {
            sb.append(lists.get(i));
        }
        strInput = sb.toString();
        System.out.println(strInput);
        int max = 0;
        String maxString = "";
        ArrayList maxList = new ArrayList();
        for (Iterator its = set.iterator(); its.hasNext();) {
            String os = (String) its.next();
            int begin = strInput.indexOf(os);
            int end = strInput.lastIndexOf(os);
            int value = end - begin + 1;
            if (value > max && value > 1) {
                max = value;
                maxString = os;
                maxList.add(os);
            } else if (value == max) {
                maxList.add(os);
            }
        }
        int index = 0;
        for (int i = 0; i < maxList.size(); i++) {
            if (maxList.get(i).equals(maxString)) {
                index = i;
                break;
            }
        }
        System.out.println("出现最多的字符为：");
        for (int i = 0; i < maxList.size(); i++) {
            System.out.println(maxList.get(i) + "");
        }
        System.out.println("出现最多的次数为：" + max);
    }
}


class Box<T> {
    private T t;
    public void add(T t) {
        this.t = t;
    }
    public T get() {
        return t;
    }
    public static void main(String[] args) {
        Box<Integer> integerBox = new Box<Integer>();
        Box<String> stringBox = new Box<String>();

        integerBox.add(new Integer(10));
        stringBox.add(new String("Hello World"));

        System.out.printf("Integer Value :%d\n\n", integerBox.get());
        System.out.printf("String Value :%s\n", stringBox.get());
    }
}


interface InterfaceA{
    String S = "good";
    Void f();
}

abstract class ClassA{
    abstract void g();
}

/**
 * ClassB继承了抽象类ClassA并实现了接口InterfaceA，
 * 在main函数中，向上实例化一个抽象类的实例a，一个接口A的实例b，通过a,b调用他们各自的方法，
 * 则输出一个来自a.g()的good，一个来自b.f()的 good。
 */
class ClassB extends ClassA implements InterfaceA{
    void g(){
        System.out.print(S);
    }
    public Void f(){
        System.out.print(""+S);
        return null;
    }

}

class Test{
    public static void main(String[] args){
        ClassA a = new ClassB();
        InterfaceA b = new ClassB();
        a.g();
        b.f();
    }
}



class Solution {
    public static void main(String[] args) {
        boolean t = validWordAbbreviation("internationalization", "i12iz4n");
        System.out.println(t);
    }
    public static boolean validWordAbbreviation(String word, String abbr) {
        //从前往后遍历abbr
        //num记录字母间的数字大小
        //abbrLen记录当前字母对应原串的位置
        int lenWord = word.length();
        int lenAbbr = abbr.length();
        int abbrLen = 0;
        int num  = 0;
        for(int i=0;i<lenAbbr;i++){
            if(abbr.charAt(i)>='a'&& abbr.charAt(i)<='z'){
                //如果是字母则 abbrLen+=num+1
                //之前的数字大小加上该字母贡献的长度 11
                //同时对比原单词中的位置 word[abbrLen-1]是否也为该字母，然后清空 num
                abbrLen += num+1;
                num = 0;
                if(abbrLen>lenWord || abbr.charAt(i)!=word.charAt(abbrLen-1)) return false;
            }
            else{
                if(num==0 && abbr.charAt(i)=='0') return false;  //不能出现前导0
                //如果碰到数字则更新num， num=num*10+abbr[i]-'0'
                //其实就是一个十进制数字字符串转十进制数字的过程
                num = num*10+abbr.charAt(i)-'0';
                //最后比较 abbrLen 与单词长度是否相等。

            }
        }
        //如果以上条件满足则说明缩写可行
        return abbrLen+num == lenWord;
    }
}



