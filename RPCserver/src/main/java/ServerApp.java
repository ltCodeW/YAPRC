import RPC.RpcSrverProxy;
import service.CalcuService;

public class ServerApp  {
    public static void main(String[] args) {
        RpcSrverProxy proxy = new  RpcSrverProxy();
        proxy.publish(8080);
    }

}
