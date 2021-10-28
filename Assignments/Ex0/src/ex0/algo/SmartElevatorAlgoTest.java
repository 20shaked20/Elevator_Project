package ex0.algo;

//project imports
import ex0.Building;
import ex0.Elevator;
import ex0.CallForElevator;
import ex0.simulator.Call_A; //implements callForElevator
import ex0.simulator.ElevetorCallList; //extend ArrayList<CallForElevator>
import ex0.simulator.Simulator_A;
import java.util.ArrayList;

//Junit imports
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SmartElevatorAlgoTest {
    /*Builings            Algorithm cases                 Calls for testing            */
    Building building0;   SmartElevatorAlgo algorithm0;   Call_A call1; Call_A call11;
    Building building1;   SmartElevatorAlgo algorithm1;   Call_A call2; Call_A call12;
    Building building2;   SmartElevatorAlgo algorithm2;   Call_A call3; Call_A call13;
    Building building3;   SmartElevatorAlgo algorithm3;   Call_A call4; Call_A call14;
    Building building4;   SmartElevatorAlgo algorithm4;   Call_A call5; Call_A call15;
    Building building5;   SmartElevatorAlgo algorithm5;   Call_A call6; Call_A call16;
    Building building6;   SmartElevatorAlgo algorithm6;   Call_A call7; Call_A call17;
    Building building7;   SmartElevatorAlgo algorithm7;   Call_A call8; Call_A call18;
    Building building8;   SmartElevatorAlgo algorithm8;   Call_A call9; Call_A call19;
    Building building9;   SmartElevatorAlgo algorithm9;   Call_A call10; Call_A call20;
    /***********************************************************************************/

    ArrayList<CallForElevator> calls = new ArrayList<>();
    ElevetorCallList callList = new ElevetorCallList(calls);

    //initializes all building, 10 cases of our algorithm, and several calls for testing.
    public SmartElevatorAlgoTest(){
        Simulator_A.initData(0,null);
        building0 = Simulator_A.getBuilding();

        Simulator_A.initData(1,null);
        building1 = Simulator_A.getBuilding();

        Simulator_A.initData(7,null);
        building2 = Simulator_A.getBuilding();

        Simulator_A.initData(3,null);
        building3 = Simulator_A.getBuilding();

        Simulator_A.initData(4,null);
        building4 = Simulator_A.getBuilding();

        Simulator_A.initData(5,null);
        building5 = Simulator_A.getBuilding();

        Simulator_A.initData(6,null);
        building6 = Simulator_A.getBuilding();

        Simulator_A.initData(7,null);
        building7 = Simulator_A.getBuilding();

        Simulator_A.initData(8,null);
        building8 = Simulator_A.getBuilding();

        Simulator_A.initData(9,null);
        building9 = Simulator_A.getBuilding();

        algorithm0 = new SmartElevatorAlgo(building0);
        algorithm1 = new SmartElevatorAlgo(building1);
        algorithm2 = new SmartElevatorAlgo(building2);
        algorithm3 = new SmartElevatorAlgo(building3);
        algorithm4 = new SmartElevatorAlgo(building4);
        algorithm5 = new SmartElevatorAlgo(building5);
        algorithm6 = new SmartElevatorAlgo(building6);
        algorithm7 = new SmartElevatorAlgo(building7);
        algorithm8 = new SmartElevatorAlgo(building8);
        algorithm9 = new SmartElevatorAlgo(building9);

        //13 calls to test with:
        Call_A call1 = new Call_A(0.0,0,20);
        Call_A call2 = new Call_A(0.0,20,3);
        Call_A call3 = new Call_A(0.0,0,-1);
        Call_A call4 = new Call_A(0.0,0,-1);
        Call_A call5 = new Call_A(0.0,-5,-10);
        Call_A call6 = new Call_A(0.0,-1,-3);
        Call_A call7 = new Call_A(0.0,10,25);
        Call_A call8 = new Call_A(0.0,10,30);
        Call_A call9 = new Call_A(0.0,5,30);
        Call_A call10 = new Call_A(0.0,0,3);
        Call_A call11 = new Call_A(0.0,0,3);
        Call_A call12 = new Call_A(0.0,0,3);
        Call_A call13 = new Call_A(0.0,0,3);


    }

    private boolean isLegalFloor(Building b, int floor){
        if (b.minFloor() <= floor && floor <=b.maxFloor())
            return true;
        return false;
    }

    @Test
    void allocateAnElevator(CallForElevator c){
        Call_A call = new Call_A(0.0,0,3);

        int elevator = algorithm0.allocateAnElevator(c);

    }

    @Test
    void cmdElevator(int elev){

    }
}
