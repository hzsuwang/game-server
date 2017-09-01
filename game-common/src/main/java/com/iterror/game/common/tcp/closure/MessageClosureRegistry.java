package com.iterror.game.common.tcp.closure;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.iterror.game.common.exception.InvalidMessageException;
import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@SuppressWarnings("rawtypes")
public class MessageClosureRegistry {

    private static final Logger LOGGER     = LoggerFactory.getLogger(MessageClosureRegistry.class);

    private Map<String, HandlerTuple> handlerMap = new HashMap<String, HandlerTuple>();

    public void setHandlers(List<MessageCourse<?>> handlers) {
        if (handlers != null) {
            for (MessageCourse<?> handler : handlers) {
                Method[] methods = getAllMethodOf(handler.getClass());
                for (Method method : methods) {
                    if (method.getName().equals("execute")) {
                        Class<?>[] params = method.getParameterTypes();
                        if (params.length < 1) {
                            continue;
                        }
                        Class<?> requestType = params[0];
                        registerHandler(requestType, handler);
                    }
                }
            }
        }
    }
    
    private Method[] getAllMethodOf(final Class<?> clazz) {
        Method[] methods = null;

        Class<?> itr = clazz;
        while (!itr.equals(Object.class) && !itr.isInterface()) {
            methods = (Method[]) ArrayUtils.addAll(itr.getDeclaredMethods(), methods);
            itr = itr.getSuperclass();
        }

        return methods;
    }
    
    public void registerHandler(Class<?> requestType,MessageCourse handler) {
        if (requestType == null || handler == null) {
            throw new IllegalArgumentException("requestType is null or handler is null.");
        }

        handlerMap.put(getComponentName(requestType), new HandlerTuple(requestType, handler));
    }

    public boolean updateFilter(String requestType, boolean canceled) {
        boolean oldValue = false;

        HandlerTuple result = handlerMap.get(requestType);
        if (result != null) {
            oldValue = result.isCanceled();
            result.setCanceled(canceled);
            handlerMap.put(requestType, result);
        } else {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn("Invalid requestType " + requestType + " canceled " + canceled);
            }
        }

        return oldValue;
    }

    public boolean messageRegistered(Class<?> requestType) {
        String name = getComponentName(requestType);
        HandlerTuple result = handlerMap.get(name);
        return (result != null && !result.isCanceled());
    }

    public MessageCourse getHandlerFor(Object requestMessage) {
        if (requestMessage == null) {
            throw new IllegalArgumentException("requestMessage is null");
        }

        return getHandlerTuple(requestMessage.getClass()).getHandler();
    }

    private String getComponentName(Class<?> requestType) {
        return requestType.getName();
    }

    private HandlerTuple getHandlerTuple(Class<?> requestType) {
        String name = getComponentName(requestType);
        if (handlerMap.containsKey(name)) {
            return handlerMap.get(name);
        } else {
            throw new InvalidMessageException("No such message handler of type " + name + " registered");
        }
    }

    class HandlerTuple {

        private Class<?>  requestType;
        private MessageCourse handler;
        private boolean   canceled;

        public HandlerTuple(Class<?> requestType, MessageCourse handler){
            this.requestType = requestType;
            this.handler = handler;
        }

        public Class<?> getRequestType() {
            return requestType;
        }

        public MessageCourse getHandler() {
            return handler;
        }

        public boolean isCanceled() {
            return canceled;
        }

        public void setCanceled(boolean canceled) {
            this.canceled = canceled;
        }

    }
}
