package Client.PRC;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class NetTransport {
    String host;
    int port;

    public NetTransport(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public Object send(Request request) {
        Socket socket = null;
        Object result = null;
        ObjectOutputStream objectOutputStream = null;
        ObjectInputStream objectInputStream = null;
        try{
            socket = new Socket(host,port);
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(request);
            objectOutputStream.flush();
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            result = objectInputStream.readObject();

        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
}
}
