package com.company.main;

public class ArithmeticDemo {


    public static void rabbit() {
        /**
         * 有一对兔子，从出生后第3个月起每个月都生一对兔子，小兔子长到第三个月后每个月又生一对兔子。假如兔子都不死，求第n个月兔子对数。
         */
        int n = 10;
        System.out.println("第" + n + "个月，兔子对数：" + fun(n));
    }

    /**
     * 斐波那契数列
     * 0、1、1、2、3、5、8、13、21、34、55
     *
     * @param n
     */
    public static int fun(int n) {
        if (n == 1 || n == 2) {
            return 1;
        } else {
            return fun(n - 1) + fun(n - 2);
        }
    }


    public static void primeNumber() {
        /**
         * 指定范围内包含的素数
         * 判断 101 - 200 之间有多少个素数，并输出所有素数
         *
         * 程序分析：判断素数的方法：用一个数分别去除2到 sqrt(这个数) 如果能被整除，则表明此数不是素数，反之则是素数
         */
        int m = 1;
        int n = 1000;
        int count = 0;

        // 统计素数个数
        for (int i = m; i < n; i ++) {
            if (isPrime(i)) {
                count ++;
                System.out.println(i + " ");
                if (count % 10 == 0) {
                    System.out.println();
                }
            }
        }
        System.out.println();
        System.out.println("在" + m + "和" + n + "之间共有" + count + "素数");
    }

    /**
     * 判断素数
     *
     * @param n
     * return
     */
    public static boolean isPrime(int n) {
        boolean flag = true;
        if (n == 1) {
            flag = false;
        } else {
            for (int i = 2; i <= Math.sqrt(n); i ++) {
                if ((n % i) == 0 || n == 1) {
                    flag = false;
                    break;
                } else {
                    flag = true;
                }
            }
        }
        return flag;
    }


    /**
     * 打印出所有的 水仙花数 ，所谓的水仙花数是指一个三位数，其各位数字立方和等于该数本身。列如：153 是一个水仙花数，因为 153 = 1的三次方 + 5的三次方 + 3的三次方。
     *
     * 程序分析：利用for循环控制 100 - 999 个数，每个数分解出个位，十位， 百位。
     */



    public static void main(String[] args) {



    }


}
