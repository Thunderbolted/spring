package com.luting.spring.ioc02;

/**
 * Author:luting
 * Date:2018-09-28 9:37
 * Description:基本类型Bean
 */
public class SimpleDataBean {

    private int a;
    private double b;
    private float c;
    private char d;
    private boolean e;
    private long f;
    private byte g;
    private short h;

    public SimpleDataBean() {

    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public double getB() {
        return b;
    }

    public void setB(double b) {
        this.b = b;
    }

    public float getC() {
        return c;
    }

    public void setC(float c) {
        this.c = c;
    }

    public char getD() {
        return d;
    }

    public void setD(char d) {
        this.d = d;
    }

    public boolean isE() {
        return e;
    }

    public void setE(boolean e) {
        this.e = e;
    }

    public long getF() {
        return f;
    }

    public void setF(long f) {
        this.f = f;
    }

    public byte getG() {
        return g;
    }

    public void setG(byte g) {
        this.g = g;
    }

    public short getH() {
        return h;
    }

    public void setH(short h) {
        this.h = h;
    }

    public SimpleDataBean(int a, double b, float c, char d, boolean e, long f, byte g, short h) {

        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.e = e;
        this.f = f;
        this.g = g;
        this.h = h;
    }

    @Override
    public String toString() {
        return "SimpleDataBean{" +
                "a=" + a +
                ", b=" + b +
                ", c=" + c +
                ", d=" + d +
                ", e=" + e +
                ", f=" + f +
                ", g=" + g +
                ", h=" + h +
                '}';
    }
}
