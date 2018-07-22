import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * created on sunyang 2018/7/9 16:11
 * Are you different!"jia you" for me
 */
public class getDoubleCheckSingleton {

    Map<String, Object> objectMap = new ConcurrentHashMap<>(16);

    protected Object getSingleton(String beanName){
        Object object = this.objectMap.get(beanName);
        if (object == null){
            synchronized (this.objectMap){
                if (object == null){
                    object = this.objectMap.get(beanName);
                }
            }
        }
        return object;
    }
}
