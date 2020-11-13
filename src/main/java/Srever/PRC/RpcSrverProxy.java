package Srever.PRC;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * see:主要向外提供服务*/
public class RpcSrverProxy {
    //线程池成员用来处理接受的请求
    ExecutorService executorService = Executors.newCachedThreadPool();
    //发布服务
    // 使用socket等待请求，有请求就执行
    public void publish(int port) {
        ServerSocket serversocker ;
        try {
            serversocker =new ServerSocket(port);
            while (true){
                Socket socket = serversocker.accept();
                executorService.execute(new DealRequest(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
