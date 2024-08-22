package heima_data_structure.test.other;

import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class TestLambda {
    /*public static void main(String[] args) {
        Map<Type, Bean> collect = new Types().getTypeList().keySet().stream()
                .collect(Collectors.toMap(k->k, k->appcliation.getBean(k)));
        Map<Type, Bean> granterPool = new HashMap<>();
        granterPool.putAll(collect);
    }

    static class Types {
        Map<Type,Object> typeList = Map.of(
                new Type(1), new Object(),
                new Type(2), new Object(),
                new Type(3), new Object()
        );

        public Map<Type, Object> getTypeList() {
            return typeList;
        }
    }
    static class Type {
        int type;

        @Override
        public String toString() {
            return "Type{" +
                    "type=" + type +
                    '}';
        }

        public Type(int type) {
            this.type = type;
        }
    }*/
}
