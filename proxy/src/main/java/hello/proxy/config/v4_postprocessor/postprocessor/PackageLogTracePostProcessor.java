package hello.proxy.config.v4_postprocessor.postprocessor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

@Slf4j
@RequiredArgsConstructor
public class PackageLogTracePostProcessor implements BeanPostProcessor {

    private final String basicPackage; // 모든 곳에 프록시 등록필요 x
    private final Advisor advisor;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        log.info("beanName = {}, bean = {}", beanName, bean.getClass());

        //프록시 대상이 아닌 경우
        String packageName = bean.getClass().getPackageName();
        if (!packageName.startsWith(basicPackage)) {
            return bean;
        }
        //프록시 대상인 경우
        ProxyFactory proxyFactory = new ProxyFactory(bean);
        proxyFactory.addAdvisor(advisor);

        Object proxy = proxyFactory.getProxy();
        log.info("beanName = {}, proxy clasName = {}", bean.getClass(), proxy.getClass());
        //프록시 객체
        return proxy;
    }
}
