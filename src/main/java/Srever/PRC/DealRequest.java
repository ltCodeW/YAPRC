package Srever.PRC;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.Socket;

public class DealRequest implements Runnable {
    Socket socket;
    public DealRequest(Socket socket) {
        socket=socket;
    }

    public void run() {
        ObjectInputStream objectInputStream = null;
        ObjectOutputStream objectOutputStream = null;
        Request requst = null;

        try {
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            requst = (Request)objectInputStream.readObject();
            Object result = invoke(requst);
            objectOutputStream =new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(result);
            objectOutputStream.flush();
        } catch (ClassNotFoundException e) {
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
