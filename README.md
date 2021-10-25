# Smart Elevators - Assignment[0]

> Made by Shaked Levi And Johnatan Ratner.

>GitHub pages:
> https://github.com/20shaked20
> https://github.com/Teklar223

### Introduction
This project is an assigment in an object - oriented course at Ariel University.\
The project is about online algorithm for smart elevators.\

# The difference between offline to online algorithm:

We did some reading on Wikipedia to find out that the difference between both algorithms is whether the input is available from the start, or it changes dynamically to the user's needs.
Furthermore, some of the times offline algorithms are more effective in time run complexity. However, they can’t solve all the problems, therefore the use of online algorithms.


# Offline algorithm for elevator callings:
*We need to know beforehand how many people are going to call the elevator.

For instance: let’s consider a building with 50 tenants, and assume that this number is constant, thus we can learn the schedule of each tenant and assign them elevators based on their needs, assuming these are also static and constant, for example we could ask a tenant how his day looks like and order an elevator to the floors he needs based on that, and then do so for each tenant until we have an optimized elevator routine based off their supposedly static schedule.

Pros:
            -simple
            -comfortable
Cons:
            -limiting approach
            -static and requires much maintenance
            -not human (humans aren’t robotic to a schedule)
 



# An online algorithm for elevator callings:

*We can’t tell how many people will use the elevator during the day.

Each time the elevator system gets a call our system will need to decide which elevator to send to our user.
We’ll give an illustration - the elevator controller gets a call to a certain floor, we have several scenarios to consider:

A. No elevator or only some are in motion, in this case we will assign an elevator based on the fastest one to arrive, preferring the ones that are idle.

B. All the elevators are in motion therefore we will assign the closest elevator that is (or eventually is) on its way to him based on it’s route.

C. we have a single idle elevator on a certain floor, but there is an elevator in the same direction to our caller, thus we will send that elevator to him after it’s finished - based off which one will arrive faster and by how much, based off current difference in floors and scheduled tasks (an elevator may arrive faster from floor 1 to 40 than from 50 to 40 but stopping at 49,48...41 along the way).

After our user got inside the elevator, it has some possible options –

1.Picking up another user during the transport to the designated floor our first user wanted to go.
2.if the elevator crossed the middle floor, it would skip the other floors.

Simplifying the algorithm –
0. elevators wait for a call (semantic step)
1. elevators get a call (insert a certain floor destination and source)
2. elevator gets to the floor (doors open)
3. people get in or out based on need, then elevator becomes idle or goes to a default floor if there are no more calls for her to complete, else it repeats from step (2)
4. repeat from step (0)
 
 
 
 


## SmartElevatorAlgo class
This class represents a smart algorithm for modern elevators. it attempts to load-balance the elevators while sending the best elevator to a caller.
The algorithm uses a route system for each elevator and calculates the time it will take to reach to a caller while considering:
Speed,route and time.

| **Methods**      |    **Details**        |
|-----------------|-----------------------|
| `SmartElevatorAlgo()` | Constructor |
| `getBuilding()` | Returns the building that uses this algorithm |
| `algoName()` | Returns the algorithm name |
| `allocateAnElevator()` | Main function for elevator allocation |
| `allocateHelper()` | Returns the best elevator |
| `bestIdle()` | Returns the best idle elevator |
| `addToRouteSimple()` | Adds to a certain route Source and Destination |
| `addToRouteSimple()` | Adds to a certain route a single floor |
| `isBetween()` | Checks if a certain floor is between two floors |
| `isInRoute()` | Checks if a floor is a stop of an elevator's route |
| `getRouteTime()` | Returns the total time of current route |
| `getFloorDiff()` | Returns the amount of floors between two floors |
| `distanceFromIdleElevToFloor()` | Returns the time between an idle elevator and a given floor |
| `cmdElevator()` | Operator function for elevator |


###### Private Methods 
//TODO : Explains more maybe?



# How to use?




## External info:
- More about online offline algorithms : https://en.wikipedia.org/wiki/Online_algorithm
- More about Elevator Scheduling : https://github.com/00111000/Elevator-Scheduling-Simulator/blob/master/research-papers/Context-Aware-Elevator-Scheduling.pdf
- More about Smart Elevators : https://www.geeksforgeeks.org/smart-elevator-pro-geek-cup/
- More about Smart Elevators_2 : https://www.npr.org/templates/story/story.php?storyId=6799860
