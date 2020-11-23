import  RPC.*;
import service.*;

public class ClientApp {
    public static void main(String[] args) {
        CalcuService calcuService = (CalcuService) new RpcClientProxy().clientProxy(CalcuService.class, "localhost", 8080);

        System.out.println(calcuService.sum(22,33));

        StringService stringService = (StringService) new RpcClientProxy().clientProxy(StringService.class, "localhost", 8080);
        System.out.println(stringService.uppercase("xixihaha"));

    }
}
