package ex0.algo;

import ex0.Building;
import ex0.CallForElevator;
import ex0.Elevator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;


/**
 * This class represents a smart algorithm for modern elevators. it attempts to load-balance the elevators while sending the best elevator to a caller.
 * The algorithm uses a route system for each elevator and calculates the time it will take to reach to a caller while considering:
 * Speed,route and time.
 * <p>
 * It is also of note that the time complexity is O(n) and space complexity is O(n*k) where n is the amount of elevator's and k is the amount of floors
 */

public class SmartElevatorAlgo implements ElevatorAlgo {

    private Building _building;

    private final int maxRouteSize = 16;
    private HashSet<Integer>[] routeMembers; //allows access to route elements in O(1) time rather than O(n).
    private ArrayList<Integer>[] routeList;

    /*
     * Works like graph theory route, for example (3,5) would be - go to floor 3 and then 5 (pickup and deliver)
     * and (3,5,6,7) will stop at floor 3 then 5 then 6 then 7 and will be represented as elements in the list such that:
     * 3 -> 5 -> 6 -> 7
     * Also, we don't allow an elevator to be in between her routes, such that it is *ALWAYS* in a state before arriving to the first element,
     * at which point it gets deleted from the route
     * routeList and routeMembers work the same in the sense that the i'th element represents the i'th elevator like in _building's elevator list
     */


    /**
     * IMPORTANT: this is a TEST-ONLY method and should be treated as an assignment function that you wouldn't see in production,
     * but there is no point in removing it since this method is very important for testing.
     * @return a pointer to the routeList array
     */
    public ArrayList<Integer>[] getRouteList(){
        return this.routeList;
    }

    /**
     * initiates algorithm as an object
     *
     * @param b a building - please see the Building Interface for detail.
     */
    public SmartElevatorAlgo(Building b) { //O(n)
        _building = b;

        routeList = new ArrayList[b.numberOfElevetors()];
        for (int i = 0; i < routeList.length; i++) {
            routeList[i] = new ArrayList<>();
        }

        routeMembers = new HashSet[_building.numberOfElevetors()];
        for (int i = 0; i < _building.numberOfElevetors(); i++) {
            routeMembers[i] = new HashSet();
        }

    }

    /**
     * return the building that uses this algorithm.
     *
     * @return a Building object - please see the Building Interface for detail.
     */
    public Building getBuilding() {
        return this._building;
    }


    /**
     * returns the algorithm name
     *
     * @return algorithm name - includes authors.
     */
    public String algoName() {
        return "Shaked and Yonatan's Smart Elevator Algorithm";
    }


    @Override
    /**
     * The main function for allocating an elevator.
     * this function is heavily reliant on the allocateHelper Method
     * @param c - a call for an elevator, contains call source and destination - please see allocateAnElevator Interface for detail.
     * @return the best elevator for the call according to out algorithm
     */
    public int allocateAnElevator(CallForElevator c) {
        int ans;
        int S = c.getSrc(), D = c.getDest();

        ans = allocateHelper(S, D);

        boolean sourceInRoute = isInRoute(S, ans);
        boolean destinationInRoute = isInRoute(D, ans);
        if (!(sourceInRoute && destinationInRoute)) {
            if (sourceInRoute) {
                addToRouteSimple(D, ans);
                Collections.sort(routeList[ans]);
            } else {
                addToRouteSimple(S, D, ans);
            }
        }
        return ans;
    }


    /**
     * finds the best elevator according to our algorithm
     *
     * @param S - an integer representing source call
     * @param D - an integer destination
     * @return the elevator that the algorithm decides is best to send to a call, represented by its id.
     */

    private int allocateHelper(int S, int D) {
        int ans = 0;
        int direction = D > S ? 1 : -1; // 1 if the call is going up or -1 if it's down.

        int idle = bestIdle(S);
        if (idle != -1 && _building.numberOfElevetors() != 1) {
            return idle;
        }

        for (int i = 0; i < _building.numberOfElevetors(); i++) {
            int i_Elev = _building.getElevetor(i).getID();
            int elev_state = _building.getElevetor(i).getState();
            double elev_speed = _building.getElevetor(i).getSpeed();

            if (isInRoute(S, _building.getElevetor(i).getID()) && isInRoute(D, _building.getElevetor(i).getID())) {
                return i;
            } else {
                if (isInRoute(S, _building.getElevetor(i).getID()) && direction == _building.getElevetor(i).getState()) {
                    if (routeList[i].size() < maxRouteSize)
                        ans = i;
                }
            }

            /* ^ if it's in the route or on the way as is then we just return this elevator. ^ */

            if (routeList[i].size() < maxRouteSize) {
                if (_building.getElevetor(i).getSpeed() > _building.getElevetor(ans).getSpeed() && direction == _building.getElevetor(i).getState()) {
                    if (routeList[i].size() < routeList[ans].size())
                        ans = i;
                } else if (_building.getElevetor(i).getSpeed() > _building.getElevetor(ans).getSpeed()) {
                    if (routeList[i].size() < routeList[ans].size())
                        ans = i;
                } else if (direction == _building.getElevetor(i).getState()) {
                    if (_building.getElevetor(i).getPos() < S && direction == Elevator.UP) { //case where direction is up
                        if (routeList[_building.getElevetor(i).getID()].size() > 1) {
                            if (routeList[_building.getElevetor(i).getID()].get(1) > S)
                                ans = i;
                        } else {
                            return i; //found a near empty elevator that is already in the same direction...
                        }
                    }
                    if (_building.getElevetor(i).getPos() > S && direction == Elevator.DOWN) { //case where direction is down
                        if (routeList[_building.getElevetor(i).getID()].size() > 1) {
                            if (routeList[_building.getElevetor(i).getID()].get(1) < S)
                                ans = i;
                        } else {
                            return i; //found a near empty elevator that is already in the same direction...
                        }
                    }
                } else if (getRouteTime(i) < getRouteTime(ans)) {
                    if (routeList[i].size() < routeList[ans].size()) {
                        ans = i;
                    }
                }
            }
            if ((isOnRoute(S, _building.getElevetor(i).getID()) && isOnRoute(D, _building.getElevetor(i).getID())) && _building.getElevetor(i).getSpeed() >= _building.getElevetor(ans).getSpeed()) {
                if (routeList[i].size() < maxRouteSize)
                    return i;
                if (routeList[i].size() < routeList[ans].size())
                    ans = i;
            }
        }

        return ans;
    }


    /**
     * this function finds the closest idle elevator to a certain floor.
     *
     * @param floor - an integer representing a floor
     * @return the fastest idle elevator to arrive at a given floor.
     */
    private int bestIdle(int floor) {
        int ans = -1;
        boolean foundIdle = false;

        for (int i = 0; i < _building.numberOfElevetors(); i++) {
            if (_building.getElevetor(i).getState() == Elevator.LEVEL) {
                if (!foundIdle) {
                    foundIdle = true;
                    ans = i;
                } else {
                    if (distanceFromIdleElevToFloor(floor, i) < distanceFromIdleElevToFloor(floor, ans)) {
                        ans = i;
                    }
                }
            }
        }

        return ans;
    }


    /**
     * a non-dynamic naive implementation, simply adds S and D to end of the route - like a queue would.
     *
     * @param S    - an integer representing source call
     * @param D    - an integer destination
     * @param elev - the elevator ID for which we add the source call and destination to
     */
    private void addToRouteSimple(int S, int D, int elev) {
        routeList[elev].add(routeList[elev].size(), S);
        routeList[elev].add(routeList[elev].size(), D);
        routeMembers[elev].add(S);
        routeMembers[elev].add(D);
    }


    /**
     * a non-dynamic naive implementation, simply adds floor to end of the route - like a queue would.
     *
     * @param floor - an integer representing a floor
     * @param elev  - the elevator ID for which we add the floor to
     */
    private void addToRouteSimple(int floor, int elev) {
        routeList[elev].add(routeList[elev].size(), floor);
        routeMembers[elev].add(floor);
    }


    /**
     * a function that checks if a certain floor is between two other floors.
     * including if isFloorBetween=floor1 or isFloorBetween=floor2
     *
     * @param floor1         an integer representing a floor
     * @param floor2         an integer representing a floor
     * @param isFloorBetween an integer representing a floor, to be checked against floor1 and floor2
     * @return returns true iff isFloorBetween is between floor1 and floor2.
     */
    private boolean isBetween(int floor1, int floor2, int isFloorBetween) {
        return (floor1 <= isFloorBetween && isFloorBetween <= floor2 || floor2 <= isFloorBetween && isFloorBetween <= floor1);
    }


    /**
     * checks if a floor is a stop of an elevator's route
     *
     * @param floor - an integer representing a floor
     * @param elev  - the ID of the elevator we want to check for
     * @return - true or false if the floor is in the route.
     */
    private boolean isInRoute(int floor, int elev) {
        return routeMembers[elev].contains(floor);
    }


    /**
     * returns the time it will take to complete an elevator's current route.
     *
     * @param elev the elevator ID for which we check the route time.
     * @return total route time in seconds.
     */
    private double getRouteTime(int elev) {
        ArrayList<Integer> route = routeList[elev];
        Elevator thisElev = this._building.getElevetor(elev);
        double routeTime = 0;
        double speed = thisElev.getSpeed(); //speed
        double floorTime = thisElev.getStopTime() + thisElev.getStartTime() + thisElev.getTimeForOpen() + thisElev.getTimeForClose();

        if (route != null && route.size() != 0) {
            int floorsInRoute = getFloorDiff(thisElev.getPos(), route.get(0));
            int floorsToStopAt = route.size();
            for (int i = 0; i < route.size() - 1; i++) {
                floorsInRoute += getFloorDiff(route.get(i), route.get(i + 1));
            }
            routeTime = floorsInRoute * (1 / speed) + floorsToStopAt * floorTime;
        }
        return routeTime;
    }


    /**
     * calculates the amount of floors between two floors
     *
     * @param floor1 a floor for measuring distance from
     * @param floor2 a floor for measuring distance to
     * @return amount of floors between floor1 and floor2 in absolute value
     */
    private int getFloorDiff(int floor1, int floor2) {
        return Math.abs(floor1 - floor2);
    }


    /**
     * calculates time between an idle elevator and a given floor
     *
     * @param floor a floor represented as an integer
     * @param elev  an elevator represented as an integer
     * @return time for elevator with ID elev to get to floor
     */
    private double distanceFromIdleElevToFloor(int floor, int elev) {
        Elevator thisElev = _building.getElevetor(elev);
        double speed = thisElev.getSpeed(); //speed
        double floorTime = thisElev.getStopTime() + thisElev.getStartTime() + thisElev.getTimeForOpen() + thisElev.getTimeForClose();
        int floors = Math.abs(thisElev.getPos() - floor);
        return floors * (speed + floorTime);
    }

    private boolean isOnRoute(int floor, int elev) {
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

    @Override
    /** command line for elevator, it tells the elevator what to do next.
     * @param elev the current Elevator ID on which the operation is performed.
     */
    public void cmdElevator(int elev) {
        Elevator curr = _building.getElevetor(elev);
        ArrayList<Integer> route = routeList[elev];

        if (route != null) {
            if (route.size() != 0) {
                if (curr.getPos() != route.get(0)) {
                    curr.goTo(route.get(0));
                    curr.stop(route.get(0));
                } else {
                    if (routeMembers[elev].size() != 0) {
                        routeMembers[elev].remove(route.get(0));
                    }
                    route.remove(0);
                }
            } else {
                curr.goTo(0); //we assume 0 is the first floor
            }
        }
    }


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /*
     * There are some methods that we didn't use eventually, either because we didn't need them or couldn't implement them well enough.
     * We wanted to keep these just in case better ideas would pop to mind on how to use it, or they may come in handy later.
     *
     *
     *
     *
     * And because we're proud of them.
     */

    /*
     * (A)
     * add  S,D to the elevator route in the proper location.
     *
     * @param S    an integer representing source call
     * @param D    an integer destination
     * @param elev Elevator id to be added to its route
     */
    private void addToRoute(int S, int D, int elev) {
        boolean addedSrcFlag = false;
        boolean addedDestFlag = false;
        ArrayList<Integer> route = routeList[elev];

        for (int i = 0; i < route.size() - 1 && !addedDestFlag; i++) { // 1,2,4,8 src=-1 dest =10
            if (!addedSrcFlag) {//adding the call Source
                if (isBetween(route.get(i), route.get(i + 1), S)) {
                    if (route.get(i) != S && route.get(i + 1) != S) {
                        route.add(i, S);
                        routeMembers[elev].add(S);
                    }
                    addedSrcFlag = true; //now we know to move on to add D (call Destination).
                }
            } else { //adding the call Destination
                if (isBetween(route.get(i), route.get(i + 1), D)) {
                    if (route.get(i) != D && route.get(i + 1) != D) {
                        route.add(i, D);
                        routeMembers[elev].add(D);
                    }
                    addedDestFlag = true; //such that we know it doesn't need to be added as last in route.
                }
            }
        }

        if ((!addedDestFlag) && (!addedSrcFlag)) { //the case where both S and D must be added at the end of the route.
            addToRouteSimple(S, D, elev);
        }

        if (!addedDestFlag) { //if it wasn't added above then it must be added in the last position.
            addToRouteSimple(D, elev);
        }
    }

    /**
     * (B)
     * adds a single floor to the route dynamically, O(n).
     *
     * @param floor - single floor that should be added to the route of elev
     * @param elev  - the id of the elevator to which floor will be added to its route.
     */
    private void addToRoute(int floor, int elev) { /////////////// 2,4,5,6 : SRC = -1. DES = 10
        boolean addedFloorFlag = false;
        ArrayList<Integer> route = routeList[elev];

        for (int i = 0; i < route.size() - 1 && !addedFloorFlag; i++) {
            if (isBetween(route.get(i), route.get(i + 1), floor)) {
                if (route.get(i) != floor && route.get(i + 1) != floor) {
                    route.add(i, floor);
                    routeMembers[elev].add(floor);
                }
                addedFloorFlag = true;
            }
        }
        if (!addedFloorFlag) { //if it wasn't added above then it must be added in the last position.
            route.add(route.size(), floor);
            routeMembers[elev].add(floor);
        }
    }

    /**
     * (C)
     * O(n), checks if a request's floor is on an elevator's route
     *
     * @param floor a floor represented as an integer
     * @param elev  the id of the elevator to check if the floor is on its route.
     * @return true or false in case the floor is on the route
     */

    /**
     * (D)
     *
     * @return how many floors are in the building.
     */
    private int getFloors() {
        return Math.abs(_building.maxFloor() - _building.minFloor());
    }
}
