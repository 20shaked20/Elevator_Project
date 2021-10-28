package ex0;

import ex0.algo.ElevatorAlgo;
import ex0.algo.ShabatElev3Algo;
import ex0.algo.SmartElevatorAlgo;
import ex0.algo.SmartElevatorAlgoTest;
import ex0.simulator.Simulator_A;

import java.net.URL;

/**
 * This is the main file of Ex0 (OOP), Do play with it and make sure you know how to operate the simulator before
 * starting to implement the algorithm.
 */
public class Ex0_main {
    public static Long ID0=318985165L, ID1 = null, ID2 = null;
    public static void main(String[] ar) {
        String codeOwner = codeOwner();
        Simulator_A.setCodeOwner(codeOwner);
        int stage =7;  // any case in [0,9].

        String stageTemp = ""+stage; //added such that the print message can relay whether it's running a test case or not.
        String callFile = null; // use the predefined cases [1-9]

        /* TESTING ONLY SEGMENT! */
        boolean Test = false; //change to true for testing or false for the algorithm.
        if (Test) {
            stage = 7; //currently, running testCase for this stage
            stageTemp = "TEST"; //stage name for print message
            URL fileLocation = SmartElevatorAlgoTest.class.getResource("TestCalls"); //retrieves the TestCalls.txt relative location
            callFile = fileLocation.getPath(); // sets the call file to this one, the buildings still change from case to case
        }
        /* TESTING ONLY SEGMENT!*/

        System.out.println("Ex0 Simulator: isStarting, stage="+stageTemp+") ... =  ");
        // String callFile = "data/Ex0_stage_2__.csv"; //
        Simulator_A.initData(stage, callFile);  // init the simulator data: {building, calls}.

        // ElevatorAlgo ex0_alg = new ShabatElevAlgo(Simulator_A.getBuilding());  // The simplest algo ever (Shabat Elev).
        // ElevatorAlgo ex0_alg = new ShabatElev2Algo(Simulator_A.getBuilding()); // Shabat Elev with a minor twist
        //ElevatorAlgo ex0_alg = new ShabatElev3Algo(Simulator_A.getBuilding());    // Shabat Elev with two trick - replace with your code;
        ElevatorAlgo ex0_alg = new SmartElevatorAlgo(Simulator_A.getBuilding());
        Simulator_A.initAlgo(ex0_alg); // init the algorithm to be used by the simulator

        Simulator_A.runSim(); // run the simulation - should NOT take more than few seconds.

        long time = System.currentTimeMillis();
        String report_name = "out/Ex0_report_case_"+stage+"_"+time+"_ID_.log";
        Simulator_A.report(report_name); // print the algorithm results in the given case, and save the log to a file.
        //Simulator_A.report(); // if now file  - simple prints just the results.
        Simulator_A.writeAllCalls("out/Ex0_Calls_case_"+stage+"_.csv"); // time,src,dest,state,elevInd, dt.
    }

    private static String codeOwner() {
        String owners = "none";
        if(ID0!=null) {owners = ""+ID0;}
        if(ID1!=null) {owners += ","+ID1;}
        if(ID2!=null) {owners += ","+ID2;}
        return owners;
    }
}
