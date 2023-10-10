package com.sprung.core.container;

public interface DisposableBean {

    /**
     * Invoked by the containing {@code BeanFactory} on destruction of a bean.
     * @throws Exception in case of shutdown errors. Exceptions will get logged
     * but not rethrown to allow other beans to release their resources as well.
     */
    void destroy() throws Exception;

}
