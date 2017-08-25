package sample;

import javafx.scene.control.Label;

import java.util.Timer;
import java.util.TimerTask;

public class Clock extends Label {
    private short hours;
    private short minutes;
    private short seconds;
    private static final int REPEATTIME = 1000;
    private boolean pause;
    private Timer timer;
    private TimerTask task = new TimerTask() {
        @Override
        public void run() {
            if(!pause){
                update();
                updateLabels();
            }
        }
    };
    /**
     * Creates new Clock
     */
    public Clock() {
        pause = false;
        this.setMaxSize(150, 50);
        timer = new Timer();
    }
    /**
     * Starts Clock.
     */
    public void start(){
        timer.schedule(task, 0, REPEATTIME);
    }
    /**
     * Update Clock Seconds, Minutes and Hours on timer Tick.
     */
    private void update(){
        seconds++;
        seconds%=60;
        if(seconds==0){
            minutes++;
            minutes%=60;
            if(minutes==0){
                hours++;
            }
        }
    }
    /**
     * Update Seconds Label, Minutes Label and Hours Label with the new values after calling the update() function
     */
    private void updateLabels(){
        this.setText(String.format("%02d : %02d : %02d", hours, minutes, seconds));
    }
    /**
     * Reset Clock.
     */
    public void reset(){
        hours=minutes=seconds=0;
    }
    /**
     * Pause Clock.
     */
    public void pause(){
        pause = !pause;
    }
    /**
     * Stop the Clock.
     */
    public void stop(){
        timer.cancel();
    }
    /**
     * Returns private value of clock Hours.
     * @return hours.
     */
    public short getHours(){
        return hours;
    }
    /**
     * Returns private value of clock Minutes.
     * @return minutes.
     */
    public short getMinuts(){
        return minutes;
    }
    /**
     * Returns private value of clock Seconds.
     * @return seconds.
     */
    public short getSeconds(){
        return seconds;
    }
    /**
     * Sets private value of clock hours.
     * @param hours short Integer to set the value of hours to it.
     */
    public void setHours(short hours){
        this.hours = hours;
    }
    /**
     * Sets private value of clock minutes.
     * @param mins short Integer to set the value of minutes to it.
     */
    public void setMinuts(short mins){
        this.minutes = mins;
    }
    /**
     * Sets private value of clock seconds.
     * @param seconds short Integer to set the value of seconds to it.
     */
    public void setSeconds(short seconds){
        this.seconds = seconds;
    }
    /**
     * Represents the Clock in the form of a String.
     * @return String Representing the Clock Time at the moment the function was called.
     */
    @Override
    public String toString(){
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}
