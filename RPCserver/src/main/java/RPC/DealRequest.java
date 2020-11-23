package RPC;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;


public class DealRequest implements Runnable {
    Socket socket;


    public DealRequest(Socket socket) {
        this.socket=socket;
    }

    public void run() {

        try {
            socket.setSoTimeout(1000);
        } catch (SocketException e) {
            e.printStackTrace();
        }

        System.out.println("开始处理客户端请求...");
        ObjectInputStream objectInputStream = null;
        ObjectOutputStream objectOutputStream = null;
        Request request = null;
        String ack = null;
        Object result =null;


        try {
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            objectOutputStream =new ObjectOutputStream(socket.getOutputStream());
            request = (Request)objectInputStream.readObject();
            result = invoke(request);
            objectOutputStream.writeObject(result);
            objectOutputStream.flush();
            while(!ack.equals("ack")) {
                try {
                    ack = (String) objectInputStream.readObject();
                } catch (SocketTimeoutException e) {
                    //timeout 后重新发送
                    objectOutputStream.writeObject(result);
                    objectOutputStream.flush();
                    ack = (String) objectInputStream.readObject();
                }
            }
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try{
                if(objectInputStream != null )objectInputStream.close();;
                if(objectOutputStream != null ) objectOutputStream.close();
            }catch (IOException e ){
                e.printStackTrace();
            }

        }
    }


//反射调用
    Object invoke(Request request) throws Exception  {
        Class<?> clazz = Class.forName(request.getClassName());
        Object obj = clazz.newInstance();


        Object[] params =request.getParams();
        Class<?>[] types = new Class[params.length];
        for(int i =0;i<params.length;i++){
            types[i] = params[i].getClass();
        }

        Method method = clazz.getMethod(request.getMethodName(),types);
        return method.invoke(obj, params);
    }
}
