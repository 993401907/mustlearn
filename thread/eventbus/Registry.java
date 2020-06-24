package thread.eventbus;

import list.ArrayList;
import list.List;

import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author wulizi
 * 注册中心
 */
public class Registry {
    private ConcurrentHashMap<String, ConcurrentLinkedQueue<Subscriber>> subMap =
            new ConcurrentHashMap<>();

    public void bind(Object subObj) {
        List<Method> methods = getSubscribeMethods(subObj);
        for (int i = 0; i < methods.size(); i++) {
            tierSubscirber(subObj, methods.get(i));
        }
    }

    public void unbind(Object subObj) {
        subMap.forEach((key, queue) -> {
            queue.forEach(item -> {
                if (item.getSubsObj() == subObj) {
                    item.setDisabled(true);
                }
            });
        });
    }

    private void tierSubscirber(Object subObj, Method method) {
        final Subscribe subscribe = method.getDeclaredAnnotation(Subscribe.class);
        String topic = subscribe.topic();
        subMap.computeIfAbsent(topic, key -> new ConcurrentLinkedQueue<>());
        subMap.get(topic).add(new Subscriber(subObj, method));
    }

    private List<Method> getSubscribeMethods(Object subObj) {
        final List<Method> methodList = new ArrayList<>();
        Class<?> clazz = subObj.getClass();
        while (clazz != null) {
            Method[] methods = clazz.getDeclaredMethods();
            Arrays.stream(methods).filter(m ->
                    m.isAnnotationPresent(Subscribe.class) &&
                            m.getParameterCount() == 1 &&
                            m.getModifiers() == Member.PUBLIC
            ).forEach(methodList::add);
            clazz = clazz.getSuperclass();
        }
        return methodList;
    }

    public ConcurrentLinkedQueue<Subscriber> scanSubsciber(String topic) {
        return subMap.get(topic);
    }
}
