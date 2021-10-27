# Smart Elevators - Assignment[0]

> Made by Shaked Levi And Yonatan Ratner.

>Shaked's github https://github.com/20shaked20
>
>Yonatan's github: https://github.com/Teklar223

### Introduction
This repository is part of an assigment in an object oriented programming course at Ariel University,
Specifically the first assignment, and it is about an online algorithm for smart elevators.

## The difference between offline and online algorithm:

Firstly, we must discuss what is an online algorithm, it is an algorithm where we can expect a certain type of input (in our case it is elevator calls),
but what we cant know for certain is how many inputs there will be, the amount will vary by nature and an online algorithm is made with this fact in mind.

On the other hand, an offline algorithm is an algorithm in which we know what input we receive and how much of it, and we can then proceed with that knowledge in mind,
Allowing us the luxury of making a simple, if naive, algorithm that almost always works.

For some problems an offline algorithm is plenty, such as complex mathematical models, or an online store where the owner can set his stock manually (even if this is bad practice), but for our needs we require a dynamic approach since we cannot know beforehand how many people will make elevator calls, or when.



## Offline algorithm for elevator callings:
Let’s consider a building with k tenants, and assume that this number is constant, thus we can learn the schedule of each tenant and assign them elevators based on their needs, assuming these are also static and constant, for example we could ask a tenant how his day looks like and order an elevator to the floors he needs based on that, and then do so for each tenant until we have an optimized elevator routine based off their supposedly static schedule.

>Pros:

  1. simple
  
  2. comfortable          
           
>Cons:

 1. limiting approach
 
 2. static and requires much maintenanc
 
 3. not human (humans aren’t robotic to a schedule)
 


## An online algorithm for elevator callings:

Each time the elevator system gets a call our algorithm will decide which elevator to send to the caller,
But there are several scenarios to consider:

A. No elevator or only some are in motion, in this case we will assign an elevator based on the fastest one to arrive, preferring the ones that are idle.

B. All the elevators are in motion therefore we will assign the closest elevator that is (or eventually is) on its way to him based on it’s route.

C. we have a single idle elevator on a certain floor, but there is an elevator in the same direction to our caller, thus we will send that elevator to him after it’s finished - based off which one will arrive faster and by how much, based off current difference in floors and scheduled tasks (an elevator may arrive faster from floor 1 to 40 than from 50 to 40 but stopping at 49,48...41 along the way).

After the caller got inside the elevator, it has some possible options –

1.Picking up another user during the transport to the designated floor our first caller wanted to go.
2.if the elevator crossed a 'middle' floor, it would skip the other floors and go straight to its current destination.

Simplifying the algorithm –
0. elevators wait for a call (semantic step)
1. elevators get a call (call floor and where to go)
2. elevator gets to the floor (doors open)
3. people get in or out based on need, then elevator becomes idle or goes to a default floor if there are no more calls for it to complete, else it repeats from step (2)
4. repeat from step (0)
 
 
 
 


## SmartElevatorAlgo class
This class represents a smart algorithm for modern elevators. it attempts to load-balance the elevators while sending the best elevator to a caller.
The algorithm uses a route system for each elevator and calculates the time it will take to reach to a caller while considering speed,route and time.

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


### A note on public/private methods 
With the exception of ``` allocateAnElevator ``` and ``` cmdElevator ```, all of the methods listed are tagged private.



## How to use?

As part of the assignment, a simulator was provided that takes the task of implementing some of the interfaces off of our hands. </br>
Thus our entire work is done through 2 classes, SmartElevatorAlgo and Ex0_main.

#### *Ex0_main*
there are 2 important lines that change which test case is run, and on what algorithm: </br>
this line (line 17) decides which test case to run from 10 possible cases. </br>
```
int stage = 7;  // any case in [0,9]. 
```
and this line (line 26) which declares exactly which algorithm is getting executed by creating it as an ElevatorAlgo object. </br>
```
ElevatorAlgo ex0_alg = new SmartElevatorAlgo(Simulator_A.getBuilding());
```

the rest is plug and play - part of the simulation given in the assignment.

#### *SmartElevatorAlgo*

If you wish to change our algorithm there are 2 main functions to consider these are: </br>
``` allocateAnElevator ``` assigns a given call to what our algorithm dictates is the best elevator </br>
``` cmdElevator ``` is called every 'Tick' and dictates the movement of an elevator

Do note however that ``` allocateAnElevator ``` calls helper functions, and some of those also call simpler helper function.

## External info:
- More about online offline algorithms : https://en.wikipedia.org/wiki/Online_algorithm
- More about Elevator Scheduling : https://github.com/00111000/Elevator-Scheduling-Simulator/blob/master/research-papers/Context-Aware-Elevator-Scheduling.pdf
- More about Smart Elevators : https://www.geeksforgeeks.org/smart-elevator-pro-geek-cup/
- See Also                   : https://www.npr.org/templates/story/story.php?storyId=6799860
