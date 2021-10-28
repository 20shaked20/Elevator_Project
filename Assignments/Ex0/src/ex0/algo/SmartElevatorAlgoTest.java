package ex0.algo;

//project imports
import ex0.Building;
import ex0.Elevator;
import ex0.CallForElevator;
import ex0.simulator.Call_A; //implements callForElevator
import ex0.simulator.ElevetorCallList; //extend ArrayList<CallForElevator>
import ex0.simulator.Simulator_A;

import java.net.URL;
import java.util.ArrayList;


public class SmartElevatorAlgoTest implements ElevatorAlgo {
    private Building building;
    private SmartElevatorAlgo algo;
    private ArrayList<Integer>[] routeListPointer;

   /* private URL fileLocation = getClass().getResource("TestCalls");
    private String callFile = fileLocation.getPath(); */


    /* this class essentially mimics the actual algorithm and is only here to provide a 'safe' way to test */

    public SmartElevatorAlgoTest(Building b){
        building = b;
        algo = new SmartElevatorAlgo(b);
        routeListPointer = algo.getRouteList();
    }


    public Building getBuilding(){
        return this.building;
    }


    public String algoName(){
        return "Tester class - NOT ACTUAL ALGORITHM!";
    }

    @Override
    public int allocateAnElevator(CallForElevator c) {
        final int assignedElev = algo.allocateAnElevator(c); //man in the middle

        try {
            exceptionThrowAllocate(c,assignedElev); //checks if assigned elevator is correct and throws exception accordingly
        } catch (Exception e) {
            //e.printStackTrace();
        }


        System.out.println("test is working");
        return assignedElev;
    }

    /**
     * throws exception if there was an unexpected result
     * @param c call for elevator
     * @param assignedElev the elevator assigned to the call
     */
    private void exceptionThrowAllocate(CallForElevator c,int assignedElev) throws Exception {
        if(c.getDest()==20){
            if (assignedElev!=building.getElevetor(8).getID()){
                throw new Exception("Expected: Elevator 9 (1 based), Received: "+assignedElev+" (0 based)");
                //System.out.println("Expected: Elevator 9 (1 based), Received: "+assignedElev+" (0 based)");
            }
        }

        if(c.getDest()==3){
            if (assignedElev!=building.getElevetor(8).getID()){
                System.out.println("Expected: Elevator 9 (1 based), Received: "+assignedElev+" (0 based)");
            }
        }
    }

    @Override
    public void cmdElevator(int elev){
        algo.cmdElevator(elev); //man in the middle

        int elevState = building.getElevetor(elev).getState();

        if (routeListPointer[elev].size()!=0) { //if the size is 0 we allow the elevator to be either LEVEL or in motion, otherwise we must check it.
            if (elevState == Elevator.LEVEL)
                System.out.println("Expected: Moving, Potentially: Level and un/loading passangers");
        }

    }
}
