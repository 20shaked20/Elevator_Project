package ex0.algo;

import ex0.algo.Elevator;
import java.util.ArrayList;

public class Building implements  ex0.Building{
    private String name;
    private int minFloor;
    private int maxFloor;
    private ArrayList<Elevator> elevators;

    public Building (String name, int minFloor, int maxFloor, ArrayList<Elevator> elevators){
        this.name = name;
        this.minFloor = minFloor;
        this.maxFloor = maxFloor;
        this.elevators = new ArrayList<Elevator>(elevators);
    }


    public String getBuildingName(){ return this.name; }

    public int minFloor(){ return this.minFloor; }

    public int maxFloor(){ return this.maxFloor; }

    public int numberOfElevetors(){ return this.elevators.size(); }

    public ex0.Elevator getElevetor(int i) { return this.elevators.get(i); }

}
