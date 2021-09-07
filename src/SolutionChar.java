public class SolutionChar {

    /**
     * 剑指offer05 替换空格
     * lc541 翻转字符串2
     * lc151 翻转字符串里的单词
     *
     *
     *
     *
     */



    /**
     * 剑指offer05 替换空格
     */
    public static String replaceSpace(StringBuffer str){
        if(str==null) return null;
        StringBuilder sb = new StringBuilder();
        for (int i=0;i<str.length();i++){
            if(" ".equals(String.valueOf(str.charAt(i)))){
                sb.append("%20");
            }else {
                sb.append(str.charAt(i));
            }
        }
        return sb.toString();
    }
    /**
     * lc541 翻转字符串2
     */
    public String reverseStr(String s, int k){
        char[] a = s.toCharArray();
        for (int start = 0;start<a.length;start+=2*k){
            int i=start,j = Math.min(start+k-1,a.length-1);
            while (i<j){
                char tmp = a[i];
                a[i++]  = a[j];
                a[j--] =tmp;
            }
        }
        return new String(a);
    }



    /**
     * lc151 翻转字符串里的单词
     * 给定一个字符串，逐个翻转字符串中的每个单词。
     * 移除多余空格
     * 将整个字符串反转
     * 将每个单词反转
     */

    public String reverseWords(String s){
        // 1.去除首尾以及中间多余空格
        StringBuilder sb = removeSpace(s);
        // 2.反转整个字符串
        reverseString(sb, 0, sb.length() - 1);
        // 3.反转各个单词
        reverseEachWord(sb);
        return sb.toString();
    }
    //去除空格
    private StringBuilder removeSpace(String s){
        int start = 0;
        int end = s.length() - 1;
        while (s.charAt(start) == ' ') start++;
        while (s.charAt(end) == ' ') end--;
        StringBuilder sb = new StringBuilder();
        while (start <= end){
            char c = s.charAt(start);
            if (c != ' ' || sb.charAt(sb.length() - 1) != ' ') {
                sb.append(c);
            }
            start++;
        }
        return sb;
    }
    //翻转整个字符串
    private void reverseString(StringBuilder sb, int start, int end){
        while (start<end){
            char temp = sb.charAt(start);
            sb.setCharAt(start,sb.charAt(end));
            sb.setCharAt(end,temp);
            start++;
            end--;
        }
    }
    //翻转单个字符串
    private void reverseEachWord(StringBuilder sb){
        int start = 0;
        int end = 1;
        int n = sb.length();
        while (start<n){
            while (end<n&& sb.charAt(end)!=' '){
                end++;
            }
            reverseString(sb,start,end-1);
            start = end+1;
            end = start+1;
        }
    }
}
