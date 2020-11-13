package Srever.service;

public class StringServiceImp implements StringService {
    public String uppercase(String str) {
        System.out.println("调用uppercase( "+str+" )");
        return str.toUpperCase();
    }
}
