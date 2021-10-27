import ex0.Building;
import ex0.simulator.Simulator_A;
import org.junit.jupiter.api.Test;

class SmartElevatorAlgo {


    Building b1;
    Building b9;
    SmartElevatorAlgo algoB1;
    SmartElevatorAlgo algoB9;

    public SmartElevatorAlgo() {

        Simulator_A.initData(1, null);
        b1 = Simulator_A.getBuilding();
        Simulator_A.initData(9, null);
        b9 = Simulator_A.getBuilding();

        //algoB1 = new SmartElevatorAlgo(b1);
        //algoB9 = new SmartElevatorAlgo(b9);

    }

    @Test
    void allocateAnElevator() {


    }

    @Test
    void cmdElevator() {


    }
}