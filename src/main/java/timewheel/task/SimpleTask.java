package timewheel.task;

import org.checkerframework.checker.units.qual.A;

/**
 * @Description
 * @Author veritas
 * @Data 2024/11/30 14:13
 */
public abstract class SimpleTask<A> implements Task<A> {
    Long id;
    String name;
    Integer delayInSeconds;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDelayInSeconds() {
        return delayInSeconds;
    }

    public void setDelayInSeconds(Integer delayInSeconds) {
        this.delayInSeconds = delayInSeconds;
    }
}
