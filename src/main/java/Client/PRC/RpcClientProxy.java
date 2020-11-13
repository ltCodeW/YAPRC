package Client.PRC;


import Client.PRC.RemoteInvocationHandler;

import java.lang.reflect.Proxy;

/**
 * 远程调用，返回对象*/
public class RpcClientProxy {
    public Object clientProxy(final Class<?> interfaceCls, final String host, final int port){
        return Proxy.newProxyInstance(interfaceCls.getClassLoader(),
                                      new Class<?>[]{interfaceCls},
                                      new RemoteInvocationHandler(host, port)
                );
    }
}
