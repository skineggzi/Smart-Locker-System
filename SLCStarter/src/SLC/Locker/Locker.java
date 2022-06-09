package SLC.Locker;

public class Locker {
    private int lockerID;
    private String lockerStatus;
    private String lockerSize;
    private String isDoorOpen;
    private String pw;

    public Locker(int lockerID, String lockerStatus, String lockerSize, String isDoorOpen, String pw){
        this.lockerID = lockerID;
        this.lockerStatus = lockerStatus;
        this.lockerSize = lockerSize;
        this.isDoorOpen = isDoorOpen;
        this.pw = pw;
    }

    public int getLockerId() {
        return lockerID;
    }

    public String getLockerStatus() {
        return lockerStatus;
    }

    public String getLockerSize() {
        return lockerSize;
    }

    public String getIsDoorOpen() {
        return isDoorOpen;
    }

    public void setId(int lockerID) {
        this.lockerID = lockerID;
    }

    public void setLockerStatus(String lockerStatus) {
        this.lockerStatus = lockerStatus;
    }

    public void setLockerSize(String lockerSize) {
        this.lockerSize = lockerSize;
    }

    public void setIsDoorOpen(String doorOpen) {
        isDoorOpen = doorOpen;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    @Override
    public String toString() {
        return ("ID: "+ this.getLockerId()+
                " Status: "+ this.getLockerStatus() +
                " Size: "+ this.getLockerSize() +
                " DoorIsOpen: " + this.getIsDoorOpen());
    }

}
