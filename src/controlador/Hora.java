/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.JLabel;

/**
 *
 * @author Holi
 */
public class Hora implements Runnable{
    
    String hora,minutos,segundos,ampm;
    Calendar calendario;
    public Thread hilo;
    JLabel lblHora;
    
    public Hora(JLabel lblHora)
    {
        this.lblHora = lblHora;
        hilo = new Thread(this);
        hilo.start();   
    }
    @Override
    public void run() {
        Thread ct = Thread.currentThread();
        while(ct == hilo) {
            calcula();
        lblHora.setText(hora + ":" + minutos + ":" + segundos + " "+ampm);
            try {
                Thread.sleep(1000);
            }
            catch(InterruptedException e) {}
        }
    }
    
    public void calcula () {
        calendario = new GregorianCalendar();
        Date fechaHoraActual = new Date();

        calendario.setTime(fechaHoraActual);
        ampm = calendario.get(Calendar.AM_PM)==Calendar.AM?"AM":"PM";
        int h = calendario.get(Calendar.HOUR_OF_DAY);
        int m = calendario.get(Calendar.MINUTE);
        int s = calendario.get(Calendar.SECOND);
        
        if(ampm.equals("PM")){
            h = h-12;
        }
        hora = (h>9) ? (""+h) : ("0"+h); 
        minutos = (m>9) ? (""+m) : ("0"+m);
        segundos = (s>9) ? (""+s) : ("0"+s);
    }
    
}
