import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * processFilters(0)
 * │
 * ├── AuthenticationFilter.preProcess()
 * │   │
 * │   └── processFilters(1)
 * │       │
 * │       ├── LoggingFilter.preProcess()
 * │       │   │
 * │       │   └── processFilters(2)
 * │       │       │
 * │       │       └── Target.execute() (目标方法)
 * │       │
 * │       └── LoggingFilter.postProcess()
 * │
 * └── AuthenticationFilter.postProcess()
 * @Description
 * @Author veritas
 * @Data 2024/10/10 14:57
 */
public class FilterManager {

    class Target {
        public void execute() {
            System.out.println("Executing target method...");
        }
    }

    private List<Filter> filters = new ArrayList<>();

    // 添加过滤器到链中
    public void addFilter(Filter filter) {
        filters.add(filter);
    }

    // 递归调用过滤器链
    public void processFilters(int index, Target target) {
        if (index < filters.size()) {
            // 执行前置处理
            filters.get(index).preProcess();

            // 递归调用下一个过滤器
            processFilters(index + 1, target);

            // 执行后置处理
            filters.get(index).postProcess();
        } else {
            // 到达链末端，执行目标类的方法
            target.execute();
        }
    }

    // 启动过滤器链
    public void execute(Target target) {
        processFilters(0, target);
    }

    interface Filter {
        void preProcess();

        void postProcess();
    }

    public class AuthenticationFilter implements Filter {
        @Override
        public void preProcess() {
            System.out.println("Authentication pre-process");
        }

        @Override
        public void postProcess() {
            System.out.println("Authentication post-process");
        }
    }

    public class LoggingFilter implements Filter {
        @Override
        public void preProcess() {
            System.out.println("Logging pre-process");
        }

        @Override
        public void postProcess() {
            System.out.println("Logging post-process");
        }
    }
}
