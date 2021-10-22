package ex0.algo;

public class Elevator implements ex0.Elevator{
    public static final int UP = 1, LEVEL = 0, DOWN = -1, ERROR = -2;

    private int minFloor;
    private int maxFloor;
    private double timeForOpen;
    private double timeForClose;
    private int state;
    private int position;
    private double speed;
    private double startTime;
    private double stopTime;
    private int id;
    private int dest;

    /**
     * note that the Elevator constructor *DOES NOT* take care of the id field
     * instead Building as a class has the Elevator.setIDs(ArrayList<Elevator>) function
     * that updates the id's of each elevator in respect to it's building.
     * */

    public Elevator (int minFloor, int maxFloor, int position, int id, double timeForOpen,
                     double timeForClose, double speed, double startTime, double stopTime){
        this.minFloor = minFloor;
        this.maxFloor = maxFloor;
        this.timeForClose = timeForClose;
        this.timeForOpen = timeForOpen;
        this.state = 0; //Elevator created in 'Idle' state
        this.position = minFloor; //Elevator 'created at' minFloor
        this.speed = speed;
        this.startTime = startTime;
        this.stopTime = stopTime;
        id = 0; //simply 0, make sure to run Elevator.setIDs(ArrayList<Elevator>)!!!
        dest = Integer.MAX_VALUE;
    }

    public int getMinFloor(){return minFloor;}

    public int getMaxFloor(){return maxFloor;}

    public double getTimeForOpen(){return timeForOpen;}

    public double getTimeForClose(){return timeForClose;}

    public int getState(){return state;} // UP, DOWN, LEVEL, ERROR

    public int getPos(){return position;}

    public boolean goTo(int floor){
        position = floor;
        return position==floor;
    }

    public boolean stop(int floor){return false;}

    public double getSpeed(){return speed;}

    public double getStartTime(){return startTime;}

    public double getStopTime(){return stopTime;}

    public int getID(){return id;}
}
