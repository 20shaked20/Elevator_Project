package ex0.algo;

import ex0.Building;
import ex0.CallForElevator;
import ex0.Elevator;

import java.util.ArrayList;
import java.util.Collections;



/**
 * This class represent a sophisticated algorithm for elevator calling. it uses the concept of sending the best elevator to a caller.
 * The algorithm checks the routes for each elevator and calculates the time it will take to reach to a caller.
 */

public class SmartElevatorAlgo implements ElevatorAlgo {

    private static final int UP = 1, DOWN = -1;
    private Building _building;

    private ArrayList<Integer>[] routeList;
    /*Works like graph theory route, for example (3,5) would be go to floor 3 and then 5 (pickup and deliver)
    and (3,5,6,7) will stop at floor 3 then 5 then 6 then 7 and will be represented as elements in the list such that:
    3 -> 5 -> 6 -> 7
    Also, we don't allow an elevator to be in between her routes, such that it is *ALWAYS* in a state before arriving to the first element,
    at which point it gets deleted from the route
    routeList and routeTimes work the same in the sense that the i'th element represents the i'th elevator like in _building's elevator list
    */

    public SmartElevatorAlgo(Building b) { //O(n^2)
        _building = b;

        routeList = new ArrayList[b.numberOfElevetors()];
        for (int i = 0; i < routeList.length; i++) {
            routeList[i] = new ArrayList<Integer>();
        }
    }

    public int getFloors() {
        return _building.maxFloor() - _building.minFloor();
    }

    public Building getBuilding() {
        return this._building;
    }

    public String algoName() {
        return "Shaked and Yonatan's Smart Elevator Algorithm";
    }

    @Override
    public int allocateAnElevator(CallForElevator c) {

        int ans; //TODO: check rigorously if ans=0 is actually a problem throughout the project.
        int S = c.getSrc(), D = c.getDest();

        ans = allocateElevator(S, D);
        //}

        //addToRoute(S, D, ans);
        addToRouteSimple(S, D, ans); //currently, better than addToRoute() in cases 0-9 (as of 20/10/21) and in O(1) instead of O(n).

        Collections.sort(routeList[ans]); //route optimization, TODO: make route one way and switch direction based on specific conditions (to be set later)

        return ans;

    }

    private int allocateElevator(int S, int D) { //case 3 - all active, compares the shortest route
        int ans = 0;

        int idle = closestIdle(D);

        if(idle != -1 && _building.numberOfElevetors()!=1){
            return idle;
        }

        for (int i = 1; i < _building.numberOfElevetors(); i++) {
            if (getRouteTime(i) < getRouteTime(ans)) {
                ans = i;
            }
        }
        return ans;
    }

    private int closestIdle(int floor){
        int ans= -1;
        boolean foundIdle=false;

        for (int i = 0; i<_building.numberOfElevetors();i++){
            if (_building.getElevetor(i).getState()==0){
                if(!foundIdle){
                    foundIdle=true;
                    ans=i;
                }
                else{
                    if (dist(floor,i) < dist(floor,ans)){
                        ans=i;
                    }
                }
            }
        }

        return ans;
    }

    private void addToRouteSimple(int S, int D, int elev) { //a non-dynamic naive implementation, simply adds S and D to end of the route - like a queue would.
        routeList[elev].add(routeList[elev].size(), S);
        routeList[elev].add(routeList[elev].size(), D);
    }

    private void addToRoute(int S, int D, int elev) { //assigns the route dynamically, O(n)
        boolean addedSrcFlag = false;
        boolean addedDestFlag = false;
        boolean betweenTwoFloors; //works like a temp
        ArrayList<Integer> route = routeList[elev];

        for (int i = 0; i < route.size() - 1 && !addedDestFlag; i++) {
            // i < route.size() - 1 && (!addedDestFlag || !addedSrcFlag) is a small improvement for better case scenario,
            // such that we won't keep 'looking' after we found what we wanted

            if (!addedSrcFlag) {//adding the call Source
                betweenTwoFloors = (route.get(i) <= S && S <= route.get(i + 1)) || (route.get(i + 1) <= S && S <= route.get(i));
                //^ is a nasty if that simply checks if S (call source) is between two consecutive floors on the route, and accounts for both relative positions.
                if (betweenTwoFloors) {
                    if (route.get(i) != S && route.get(i + 1) != S) {
                        route.add(i, S);
                    }
                    addedSrcFlag = true; //now that it's false we will move on to add D (call Destination).
                }
            } else {             //adding the D, call Destination
                betweenTwoFloors = (route.get(i) <= D && D <= route.get(i + 1)) || (route.get(i + 1) <= D && D <= route.get(i)); //same as before but with D.
                if (betweenTwoFloors) {
                    if (route.get(i) != D && route.get(i + 1) != D) {
                        route.add(i, D);
                    }
                    addedDestFlag = true; //such that we know it doesn't need to be added as last in route.
                }
            }
        }

        if ((!addedDestFlag) && (!addedSrcFlag)) { //the case where both S and D must be added at the end of the route.
            route.add(route.size(), S);
            route.add(route.size(), D);
        }

        if (!addedDestFlag) { //if it wasn't added above then it must be added in the last position.
            route.add(route.size(), D);
        }
    }

    private boolean isOnRoute(int floor, int elev) { //O(n), checks if a request's floor is on an elevator's route
        ArrayList<Integer> route = routeList[elev];
        Elevator elevator = _building.getElevetor(elev);

        if (route.size() != 0) {
            if ((elevator.getPos() <= floor && floor <= route.get(0)) || (route.get(0) <= floor && floor <= elevator.getPos())) {
                return true;
            }
        }
        for (int i = 0; i < route.size() - 1; i++) {
            if ((route.get(i) <= floor && floor <= route.get(i + 1)) || (route.get(i + 1) <= floor && floor <= route.get(i))) {
                //if src is between the 2 consecutive elements of the route array - accounts for cases where one is above the next or vice-versa
                return true;
            }
        }
        return false;
    }

    private boolean isInRoute(int floor, int elev){ //checks if a floor is part of the elevator's route array

        for (int i = 0; i< routeList[elev].size(); i++){
            if (routeList[elev].get(i) == floor)
                return true;
        }
        return false;
    }

    //TODO: implement better calculation.
    private double getRouteTime(int elev) {
        Elevator curr = _building.getElevetor(elev);
        ArrayList<Integer> route = routeList[elev];

        // int floorsInRoute = Math.abs(curr.getPos() - route.get(0)); //?
        double routeTime = 0;
        int floorsInRoute = 0;
        for (int i = 0; i < route.size(); i++) {
            //floorsInRoute += Math.abs((route.get(i) - route.get(i + 1)));
            routeTime += dist(i, elev);
        }

        return routeTime;

    }

    // destination method will calculate the time of elevator reaching to SRC, Taken from boaz's code
    private double dist(int src, int elev) {
        double ans;
        Elevator thisElev = this._building.getElevetor(elev);
        int pos = thisElev.getPos();
        double speed = thisElev.getSpeed();
        int min = this._building.minFloor(), max = this._building.maxFloor();
        double up2down = (max - min) * speed;
        double floorTime = speed + thisElev.getStopTime() + thisElev.getStartTime() + thisElev.getTimeForOpen() + thisElev.getTimeForClose();
        if (pos <= src) {
            ans = (src - pos) * floorTime;
        } else { //pos>src
            ans = ((max - pos) + (pos - min)) * floorTime + up2down;
        }
        return ans;
    }


    @Override
    public void cmdElevator(int elev) {
        Elevator curr = _building.getElevetor(elev);
        ArrayList<Integer> route = routeList[elev];
        if (route != null) {
            if (route.size() != 0) {
                if (curr.getPos() != route.get(0)) {
                    curr.goTo(route.get(0));
                    curr.stop(route.get(0));
                } else {
                    curr.stop(route.get(0));
                    route.remove(0);
                }
            }
        }
    }
}

