import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Flow;

/**
 * <pre>
 *       public interface Publisher<T> {
 *          //用于1.中订阅请求
 *          public void subscribe(Subscriber<? super T> s);
 *      }
 *      public interface Subscriber<T> {
 *          //用于2.中回调发送令牌
 *          public void onSubscribe(Subscription s);
 *          //用于3.用于接受4中发送过来的数据
 *          public void onNext(T t);
 *          //用于3，4，5接收中间异常了之后的调用
 *          public void onError(Throwable t);
 *          //用于6.中结束信号的回调
 *          public void onComplete();
 *      }
 *      public interface Subscription {
 *          //用于3.的发送请求N个数据
 *          public void request(long n);
 *          //用于3，4，5订阅者异步的向
 *          public void cancel();
 *      }
 *      public interface Processor<T,R> extends Subscriber<T>, Publisher<R> {
 *      }
 *
 *     1   订阅者向发布者发送订阅请求。
 *          // 本质是执行Publisher.subscribe方法
 *     2   发布者根据订阅请求生成令牌发送给订阅者。
 *          // 本质是Publisher.subscribe方法里面执行Subscription subscription = new Subscription() 并执行Subscriber.onSubscribe(subscription)
 *     3   订阅者根据令牌向发布者发送请求N个数据。
 *          // 本质是Subscriber.onSubscribe(subscription)方法里面，执行subscription.request
 *     4   发送者根据订阅者的请求数量返回M(M<=N)个数据
 *          // 本质是执行subscription.request方法里面，执行Subscriber.onNext
 *     5   重复3，4
 *     6   数据发送完毕后由发布者发送给订阅者结束信号
 *
 *  时序图（基于文字）
 *
 * Subscriber            Publisher                Subscription (令牌)
 *  |                          |                                 |
 *  | 1. subscribe()           |                                 |  // 订阅者向发布者发送订阅请求 => 本质是执行Publisher.subscribe方法
 *  |---------------------->   |                                 |
 *  |                          |                                 |
 *  |                          | 2.1 创建 Subscription 实例       |  // 发布者生成令牌 => 本质是执行Subscription subscription = new Subscription()
 *  |                          |-------------------------------->|
 *  |                          |                                 |
 *  | 2.2 Subscription执行Subscriber.onSubscribe(Subscription)    |  // 发布者把令牌发送给订阅者 => 本质是执行Subscriber.onSubscribe(subscription)
 *  |<---------------------------------------------------------- |
 *  |                          |                                 |
 *  |                          |                                 |
 *  |           3.Subscriber执行Subscription.request(N)          |  // 订阅者根据令牌向发布者发送请求N个数据 => 本质是执行Subscriber.onSubscribe(subscription)方法里面，subscription.request
 *  |----------------------------------------------------------> |
 *  |                          |                                 |
 *  |                          |                                 |
 *  |                          | 4. onNext(data)                 |  // 发送者根据订阅者的请求数量返回M(M<=N)个数据 => 本质是执行subscription.request方法里面，执行Subscriber.onNext
 *  |<---------------------------------------------------------- |
 *  |                          |                                 |
 *  |                          |                                 |
 *  |                          |                                 |
 *  |     （重复步骤3和4，直到数据发送完毕）                          |
 *  |                          |                                 |
 *  |                          |                                 |
 *  |                          |                                 |
 *  |                          | 5. onComplete()                 |
 *  |<---------------------------------------------------------- |
 *  |                          |                                 |
 *  |                          |                                 |
 *
 * </pre>
 *
 * @Description
 * @Author veritas
 * @Data 2024/10/27 17:09
 */
public class ReactiveDemo {

    // 实现一个简单的发布者类
    class SimplePublisher implements Flow.Publisher<String> {
        private List<Flow.Subscriber<? super String>> subscribers = new ArrayList<>();
        private List<String> data = List.of("Data 1", "Data 2", "Data 3", "Data 4"); // 模拟数据

        @Override
        public void subscribe(Flow.Subscriber<? super String> subscriber) {
            SimpleSubscription subscription = new SimpleSubscription(subscriber, data);
            subscribers.add(subscriber);
            subscriber.onSubscribe(subscription); // 调用订阅者的 onSubscribe 方法传递 Subscription
        }
    }

    // 实现 Subscription 类
    class SimpleSubscription implements Flow.Subscription {
        private final Flow.Subscriber<? super String> subscriber;
        private final List<String> data;
        private int index = 0;

        public SimpleSubscription(Flow.Subscriber<? super String> subscriber, List<String> data) {
            this.subscriber = subscriber;
            this.data = data;
        }

        @Override
        public void request(long n) {
            int count = 0;
            while (count < n && index < data.size()) {
                subscriber.onNext(data.get(index++)); // 将数据逐一发送给订阅者
                count++;
            }

            if (index >= data.size()) {
                subscriber.onComplete(); // 所有数据发送完毕，通知订阅者
            }
        }

        @Override
        public void cancel() {
            System.out.println("Subscription canceled.");
        }
    }

    // 实现一个简单的订阅者类
    class SimpleSubscriber implements Flow.Subscriber<String> {
        private final String name;
        private Flow.Subscription subscription;

        public SimpleSubscriber(String name) {
            this.name = name;
        }

        @Override
        public void onSubscribe(Flow.Subscription subscription) {
            this.subscription = subscription;
            System.out.println(name + " subscribed.");
            subscription.request(2); // 请求 2 个数据
        }

        @Override
        public void onNext(String item) {
            System.out.println(name + " received: " + item);
            subscription.request(1); // 请求下一个数据
        }

        @Override
        public void onError(Throwable throwable) {
            System.out.println(name + " encountered an error: " + throwable.getMessage());
        }

        @Override
        public void onComplete() {
            System.out.println(name + " received all data.");
        }
    }


    // 测试类
    public class PubSubExample {
        public static void main(String[] args) {
/*            SimplePublisher publisher = new SimplePublisher();
            SimpleSubscriber subscriber = new SimpleSubscriber("Subscriber 1");

            publisher.subscribe(subscriber);  // 向发布者订阅数据*/
        }
    }
}
