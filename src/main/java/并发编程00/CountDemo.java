package 并发编程00;

/**
 * @Author linhao
 * @Date created in 10:21 上午 2022/5/25
 */
public class CountDemo {

    public static void compareTest(int a, int b, int[] arr) {
        int t1 = countSum(a);
        int t2 = countSum(b);
        if (t1 > t2) {
            arr[0] = 1;
        } else {
            arr[1] = 1;
        }
    }

    //1～a的求和计算
    public static int countSum(int a) {
        int sum = 0;
        for (int i = 1; i <= a; i++) {
            sum += i;
        }
        return a;
    }


    public static void main(String[] args) {
        int a[] = {-1,-1};
        compareTest(1, 2, a); // ----- code_1
    }

}
