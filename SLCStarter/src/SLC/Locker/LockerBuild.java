package SLC.Locker;
import java.util.ArrayList;
import java.util.logging.Logger;


public class LockerBuild{
    //static 30 lockers
    public static int lockerNum = 30;
    public ArrayList<Locker> locker;

    public void buildLocker(){
        locker = new ArrayList<Locker>();
        for(int i=1; i<=lockerNum; i++){
            if(i<=10){
                //locker 1-10 is S
               // locker.add(new Locker(i, Locker.LockerStatus.EMPTY, Locker.LockerSize.S, false));
            }else if(i<=20){
                //locker 11-20 is M
               // locker.add(new Locker(i, Locker.LockerStatus.EMPTY, Locker.LockerSize.M, false));
            }else{
                //locker 21-30 is L
           //     locker.add(new Locker(i, Locker.LockerStatus.EMPTY, Locker.LockerSize.L, false));
            }
        }
        //testing output
        /*
            for (Locker l : locker)
                System.out.println(l);
         */

    }


}
