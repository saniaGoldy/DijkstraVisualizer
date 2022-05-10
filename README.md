# A-Star Visualizer

Програма використовує відстань до місця призначення як евристику h(n) і вагу ребра як g(n), тому результат залежить від розміщення вершин графу одна відносно одної


The A* Algorithm in Java
Starting conditions:

We have a starting node (called start) and a target node (called target).
We have a weighted directed graph of n nodes.
The goal:

Find the shortest path from start to finish
Cost Function - f(n)
We want to determine which node to move into at every step. To do that, we'll design a mathematical function f(n) which will measure how good of a candidate a node is for being included in our shortest path.

This is the cost function, and we'll want to minimize it to produce an optimal outcome.

The cost function is the sum of a move function and a heuristic function.

Move Function - g(n)
Because we're at node n, we know the cost it took us to get there from the start node. We'll call that move function - g(n).

If we say that f(n)=g(n) we'll create Dijkstra's algorithm. At each step, we'd be picking the node with the lowest cost to get to from start - the node with the smallest value for g(n). This means our function is lacking a "guiding component" so to speak.

Heuristic Function - h(n)
We'll call this guiding component a heuristic and label it h(n). We'll use this component to estimate how close the node we're looking at is to the target.

This estimation is the heart and soul of A* and it will make or break any particular implementation of it, but theoretically speaking you can use any function you'd like. If we knew the exact distance in terms of nodes, we'd already have the optimal solution.

Though, if we know the position of the target node, we can for example, calculate the Euclidean Distance between the target node and our current node. The shorter it is, the closer we are to the target node - roughly.
