# SiteCoreCodeChallenge
This repository was created as a part of application process to SiteCore Dublin

## Problem :

You run an airline that has planes that fly to different destinations around the world. Given a
task to fly between two airports you must ensure that each airplane takes the shortest time
to reach its destination.


Given that the following routes are available:
![image](https://user-images.githubusercontent.com/25200547/126197479-22831bf7-301f-4d9b-9092-92ed45caee6e.png)

a) Write a program where two airports can be entered at the command line, eg. DUB -
SYD


b) The program returns the shortest route (duration) between the two airports in the
following format:
DUB -- LHR ( 1 )
LHR -- BKK ( 9 )
BKK -- SYD ( 11 )
time: 21

## Approach

This problem was approached as a single source shortest path problem. 

The airports would be represented as vertices in a directed graphs. It is assumed as a directed graph because no some vertices are only destinations not sources eg: sydney.

As the time taken to reach from one airport to another canont be negative and the data can be easily modeled as a graph , Dijkstras algorithm will be used to find the shortest path from each source. Printing the output in the required format would need a way of storing the path not just the final distance that Dijkstra's provides. Hence a vertex class would be created to store the previous member and the graph would be a collection of vertices. For this problem , the graph is made final but the code would work for any valid graph.

## Working
When the Solution.java file is run , The user is prompted to enter 2 airports seperated by '-'. The source and destination airports are identified. The dijkstras method is called on the graph. This would populate the graph with vertices that have distance and previous values such that they represent the shortest path to that vertex from the source mentioned. The printPath method would use the navigable set generated by the dijkstras algorithm to print the output as required. 
