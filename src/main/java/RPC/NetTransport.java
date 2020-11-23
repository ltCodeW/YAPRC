package RPC;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.TimeoutException;

public class NetTransport {
    String host;
    int port;

    public NetTransport(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public Object  send(Request request) {
        Socket socket = null;
        Object result = null;
        ObjectOutputStream objectOutputStream = null;
        ObjectInputStream objectInputStream = null;
        try{
            socket = new Socket();
            socket.connect(new InetSocketAddress(host, port), 1000);

            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(request);
            objectOutputStream.flush();
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            result = objectInputStream.readObject();
            //支持最少一次语义
            if(result !=null){
                objectOutputStream.writeObject("ack");
                objectOutputStream.flush();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
}
}
