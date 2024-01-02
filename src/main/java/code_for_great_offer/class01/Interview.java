package code_for_great_offer.class01;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author veritas
 * @Data 2023/2/28 14:18
 */
public class Interview {
    public static void main(String[] args) {
        /*
         * 1) 对orderList 按shop分组；
         * 2) 求各个shop的value之和；
         * 3) 求各个shop的value的平均值，保留2位小数
         * 4) 求各个shop的value最大、最小值
         * 考虑真实情况下，可能有的order的属性是null，这种order不作处理，
         * 尽量使用最简的运算复杂度O(n)
         * */
        List<Order> orderList = new ArrayList<>();
        Collections.addAll(orderList,
                new Order(1, "shopA", new BigDecimal(10)),
                new Order(2, "shopB", new BigDecimal(10)),
                new Order(3, "shopB", new BigDecimal(20)),
                new Order(4, "shopC", new BigDecimal(10)),
                new Order(5, "shopC", new BigDecimal(20)),
                new Order(6, "shopC", new BigDecimal(30)),
                new Order(7, "shopC", null));


        Map<String, String> resultMap = orderList.stream().
                filter(order -> order.getValue() != null).collect(Collectors.groupingBy(Order::getShop,
                        Collectors.collectingAndThen(Collectors.toList(), orders -> {
                            Optional<Order> max = orders.stream().max(Comparator.comparing(Order::getValue));

                            Optional<Order> min = orders.stream().min(Comparator.comparing(Order::getValue));

                            BigDecimal sum = orders.stream().collect(Collectors.reducing(BigDecimal.ZERO, Order::getValue, BigDecimal::add));

                            long count = orders.stream().count();

                            BigDecimal divide = sum.divide(BigDecimal.valueOf(count), 2, BigDecimal.ROUND_HALF_UP);

                            Result result = new Result(sum, max.get().getValue(), min.get().getValue(), divide);

                            return result.toString();
                        })));

        System.out.println(resultMap);
    }
}

class Result {

    private BigDecimal sum;
    private BigDecimal max;
    private BigDecimal min;
    private BigDecimal divide;

    public Result() {
    }

    public Result(BigDecimal sum, BigDecimal max, BigDecimal min, BigDecimal divide) {
        this.sum = sum;
        this.max = max;
        this.min = min;
        this.divide = divide;
    }

    public BigDecimal getDivide() {
        return divide;
    }

    public void setDivide(BigDecimal divide) {
        this.divide = divide;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    public BigDecimal getMax() {
        return max;
    }

    public void setMax(BigDecimal max) {
        this.max = max;
    }

    public BigDecimal getMin() {
        return min;
    }

    public void setMin(BigDecimal min) {
        this.min = min;
    }

    @Override
    public String toString() {
        return "Result{" +
                "sum=" + sum +
                ", max=" + max +
                ", min=" + min +
                ", divide=" + divide +
                '}';
    }
}


class Order {
    private long id;
    private String shop;
    private BigDecimal value;

    public Order() {
    }

    public Order(long id, String shop, BigDecimal value) {
        this.id = id;
        this.shop = shop;
        this.value = value;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
