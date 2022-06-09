package SLC.Locker;
import SLC.BarcodeReaderDriver.BarcodeDB;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.locks.Lock;


public class LockerDB {
    //static 30 lockers
    private static int lockerNum = 30;
    private static ArrayList<Locker> locker;
    private static LockerDB lockerDB;

    //constructor
    private LockerDB(){
        locker = new ArrayList<Locker>();
    }

    //simple generator
    public static void generateLocker(){
        for(int i=1; i<=lockerNum; i++){
                locker.add(new Locker(1,"USED", "S", "CLOSE", "123456"));
                locker.add(new Locker(2,"RESERVED", "S", "CLOSE", "NULL"));
            if(i>2&&i<=10){
                locker.add(new Locker(i,"EMPTY", "S", "CLOSE", "NULL"));
            }else if(i<=20){
                locker.add(new Locker(i,"EMPTY", "M", "CLOSE", "NULL"));
            }else{
                locker.add(new Locker(i,"EMPTY", "L", "CLOSE", "NULL"));
            }
        }
    }

    //return LockerDB class
    public static LockerDB getLockerDB() {
        if(lockerDB == null){
            lockerDB = new LockerDB();
        }
        return lockerDB;
    }

    //return Locker arraylist
    public ArrayList<Locker> getLockerList(){
        return this.locker;
    }

    //return is there any locker can use, condition (only status=EMPTY)
    public Boolean isLockerCanUse(String lockerSize){
        for(int i=0; i<getLockerList().size(); i++){
            Locker l = getLockerList().get(i);
            if(Objects.equals(lockerSize, l.getLockerSize())){
                if(!Objects.equals("EMPTY", l.getLockerStatus())){
                    return true;
                }
            }
        }
        return false;
    }

    //update status
    public void UdLockStatus(int lockerID, String udStatus){
        for(int i=0; i<getLockerList().size(); i++){
            Locker l = getLockerList().get(i);
            if(lockerID==l.getLockerId()){
                l.setLockerStatus(udStatus);
            }
        }
    }

    //update isDoorOpen
    public void UdIsDoorOpen(int lockerID, String udIsDoorOpen){
        for(int i=0; i<getLockerList().size(); i++){
            Locker l = getLockerList().get(i);
            if(lockerID==l.getLockerId()){
                l.setIsDoorOpen(udIsDoorOpen);
            }
        }
    }

    //update pw
    public void UdLockerPw(int lockerID, String udLockerPw){
        for(int i=0; i<getLockerList().size(); i++){
            Locker l = getLockerList().get(i);
            if(lockerID==l.getLockerId()){
                l.setIsDoorOpen(udLockerPw);
            }
        }
    }

    //check pw, and return Locker id
    public int checkLockerPw(String lockerPw){
        int lockerId = -1;
        for(int i=0; i<getLockerList().size(); i++){
            Locker l = getLockerList().get(i);
            if(Objects.equals(lockerPw, l.getPw())){
                lockerId = l.getLockerId();
            }
        }
        return lockerId;
    }

}
