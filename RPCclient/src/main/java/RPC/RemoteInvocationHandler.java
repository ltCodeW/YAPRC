package RPC;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class RemoteInvocationHandler implements InvocationHandler {
    String host;
    int port;
    public RemoteInvocationHandler(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Request request = new Request();
        request.setClassName(method.getDeclaringClass().getName());
        request.setMethodName(method.getName());
        request.setParams(args);
        NetTransport netTransport = new NetTransport(host,port);
        return netTransport.send(request);

    }
}
