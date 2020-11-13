package Srever.service;

public class CalcuServiceImp implements CalcuService {

    public float sum(float a, float b) {
        System.out.println("调用sum（"+a+b+"）");
        return (a+b);
    }
}
