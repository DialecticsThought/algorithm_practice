package heima_data_structure.java.src.main.java.com.itheima.algorithm.recursion_single;

/**
 * 递归求和 n + n-1, ... + 1
 * <pre>
 *  尾调用
 *    如果函数的最后一步是调用一个函数，那么称为尾调用，例如
 *      function a() {
 *          return b()
 *      }
 *   下面三段代码不能叫做尾调用
 *      function a() {
 *          const c = b()
 *          return c// 因为最后一步并非调用函数
 *      }
 *      function a() {
 *          return b() + 1 // 最后一步是加法
 *      }
 *      function a(x) {
 *          return b() + x// 最后一步是加法
 *      }
 *
 *  一些语言的编译器能够对尾调用做优化
 *  eg:
 *      function a() {
 *          // 做前面的事
 *          // 在执行函数b之前 a的所有操作都做完了
 *          // 所以函数a的调用可以提前结束
 *          return b() // 这里尾调用了函数b
 *      }
 *      function b() {
 *          // 做前面的事
 *          // 在执行函数c之前 b的所有操作都做完了
 *          // 所以函数b的调用可以提前结束
 *          return c()  // 这里尾调用了函数c
 *      }
 *      function c() {
 *          return 1000
 *      }
 *      没优化之前的伪码
 *      function a() {
 *          return function b() {
 *              return function c() {
 *                  return 1000
 *              }
 *          }
 *       }
 *      优化后伪码如下
 *      // 优化成 平级地调用
 *      a()
 *      b()
 *      c()
 *   对于scala而言，可以优化
 *   @tailrec
 *   def sum(n: Long, accumulator: Long): Long = {
 *     if (n == 1) {
 *       return 1 + accumulator
 *     }
 *     return sum(n - 1, n + accumulator)
 *   }
 *   不能写成
 *   def sum(n: Long): Long = {
 *     if (n == 1) {
 *       return 1 + accumulator
 *     }
 *     return n + sum(n - 1) // 这里不是尾调用
 *   }
 * </pre>
 */
public class E06Sum {

    // f(n) = f(n-1) + n

    public static long sum(long n) {
        if (n == 1) {
            return 1;
        }
        return n + sum(n - 1);
    }

    public static void main(String[] args) {
        System.out.println(sum(15000));
    }

    /**
     * <pre>
     *      long sum(long n = 15000) {
     *         return 15000 + long sum(long n = 14999) {
     *             return 14999 + sum(14998) {
     *                 ...
     *                 return 2 + long sum(long n = 1) {
     *                     if (n == 1) {
     *                         return 1;
     *                     }
     *                 }
     *             }
     *         };
     *     }
     *
     * </pre>
     */

}
