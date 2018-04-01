package org.futuroblanquiazul.futuroblaquiazul.Entity;

import java.util.List;

/**
 * Created by Jesus Inca on 01/04/2018.
 */

public class Partido {

    List<String> Linea_Tiempo;
    int posesion;
    int gole_rival;
    int ZPG;
    int ZF;
    int ZR;
    int ZPG2;

    int IZQ_DR;
    int IZQ_PG;

    int ZF_OG;
    int ZF_R;

    int CEN_OG;
    int CEN_R;

    int DER_DR;
    int DER_PG;

 public Partido(){

 }

    public List<String> getLinea_Tiempo() {
        return Linea_Tiempo;
    }

    public void setLinea_Tiempo(List<String> linea_Tiempo) {
        Linea_Tiempo = linea_Tiempo;
    }

    public int getPosesion() {
        return posesion;
    }

    public void setPosesion(int posesion) {
        this.posesion = posesion;
    }

    public int getGole_rival() {
        return gole_rival;
    }

    public void setGole_rival(int gole_rival) {
        this.gole_rival = gole_rival;
    }

    public int getZPG() {
        return ZPG;
    }

    public void setZPG(int ZPG) {
        this.ZPG = ZPG;
    }

    public int getZF() {
        return ZF;
    }

    public void setZF(int ZF) {
        this.ZF = ZF;
    }

    public int getZR() {
        return ZR;
    }

    public void setZR(int ZR) {
        this.ZR = ZR;
    }

    public int getZPG2() {
        return ZPG2;
    }

    public void setZPG2(int ZPG2) {
        this.ZPG2 = ZPG2;
    }

    public int getIZQ_DR() {
        return IZQ_DR;
    }

    public void setIZQ_DR(int IZQ_DR) {
        this.IZQ_DR = IZQ_DR;
    }

    public int getIZQ_PG() {
        return IZQ_PG;
    }

    public void setIZQ_PG(int IZQ_PG) {
        this.IZQ_PG = IZQ_PG;
    }

    public int getZF_OG() {
        return ZF_OG;
    }

    public void setZF_OG(int ZF_OG) {
        this.ZF_OG = ZF_OG;
    }

    public int getZF_R() {
        return ZF_R;
    }

    public void setZF_R(int ZF_R) {
        this.ZF_R = ZF_R;
    }

    public int getCEN_OG() {
        return CEN_OG;
    }

    public void setCEN_OG(int CEN_OG) {
        this.CEN_OG = CEN_OG;
    }

    public int getCEN_R() {
        return CEN_R;
    }

    public void setCEN_R(int CEN_R) {
        this.CEN_R = CEN_R;
    }

    public int getDER_DR() {
        return DER_DR;
    }

    public void setDER_DR(int DER_DR) {
        this.DER_DR = DER_DR;
    }

    public int getDER_PG() {
        return DER_PG;
    }

    public void setDER_PG(int DER_PG) {
        this.DER_PG = DER_PG;
    }
}
