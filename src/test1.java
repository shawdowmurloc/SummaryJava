import javax.print.DocFlavor;
import java.util.*;

public class test1 {
    public static void main(String[] args) {
        String a = new String("ab"); //a,b均为引用，对象内容一样
        String b = new String("ab");
        String aa = "ab";  //放在常量池
        String bb = "ab";
        if(aa == bb ){  //true
            System.out.println("aa==bb");
        }
        if(a==b){   //false 不是同一个对象
            System.out.println("a==b");
        }
        if(a.equals(b)){  //true
            System.out.println("aEQb");
        }
        if(42==42.0){  //true
            System.out.println("true");
        }
        int as = 3;
        System.out.println(f(as));

    }
    public static int f(int value){
        try{
            return value*value;
        }finally {
            if(value==2){
                return 0;
            }
        }
    }
}

class num2{
    public static void main(String[] args) {
        Random rm = new Random();
        HashSet<Integer> hs = new HashSet<>();
        while (hs.size()<10){
            hs.add(rm.nextInt(20)+1);
        }
        for(Integer i:hs){
            System.out.println(i);
        }
    }
}

class k3{
    public static void main(String[] args) {
        List<String> list = new ArrayList<String>();
        Scanner in = new Scanner(System.in);
        System.out.println("请输入需要排序的单词数量");
        int num = in.nextInt();
        System.out.println("请输入单词");
        for (int i=0;i<num;i++){
            list.add(in.next());
            //Collections.sort(list);
        }

        //3.集合数字排序
        for(int j=0;j<list.size()-1;j++) {
            for (int i = 0; i < list.size() - 1 - j; i++) {
                if (list.get(i).compareTo(list.get(i + 1)) > 0) {
                    String temp = list.get(i);
                    list.set(i, list.get(i + 1));
                    list.set(i + 1, temp);
                }
            }
        }

        for (int i=0;i<list.size();i++){
            System.out.println(list.get(i));
        }

    }
}

