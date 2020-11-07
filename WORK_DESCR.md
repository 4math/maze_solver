# Grupas projekta pārskats
 - **Grupas nosaukums**: 
 - **Grupas locekļi**:
    - KT
    - AS
 - **Grupas vadītājs**:
    
    - AS
 - **Problēmu risināšanas metodes**:
    - Grupas vadītajam piemīt veto tiesība.
    - Ja grupas dalībnieks nepilda viņam piešķirto uzdevumu – grupas projekts tiek atcelts.
 - **Komunikācijas veidi**: 
    - Github - koda versiju kontrolei, uzglabāšanai un atskaitei par izstrādāto darbu.
    - Discord, Telegram - attālinātai komunikācijai reizei nedēļā.
 - **Algoritmi**:
    - Prima algoritms - labirinta ģenerācijai. [[Link](https://www.programiz.com/dsa/prim-algorithm#:~:text=a%20spanning%20tree-,Prim's%20Algorithm%20pseudocode,connecting%20the%20least%20weight%20edge.)]
    ```pseudocode
    T = ∅;
    U = { 1 };
    while (U ≠ V)
        let (u, v) be the lowest cost edge such that u ∈ U and v ∈ V - U;
        T = T ∪ {(u, v)}
        U = U ∪ {v}
    ```
   - DFS(Depth First Search) - atrast ceļu labirintā. [[Link](https://en.wikipedia.org/wiki/Depth-first_search)]
   ```pseudocode
    procedure DFS_iterative(G, v) is
        let S be a stack
        S.push(iterator of G.adjacentEdges(v))
        while S is not empty do
            if S.peek().hasNext() then
                w = S.peek().next()
                if w is not labeled as discovered then
                    label w as discovered
                    S.push(iterator of G.adjacentEdges(w))
            else
                S.pop() 
   ```
   - BFS(Breadth First Search) - atrast ceļu labirintā. [[Link](https://en.wikipedia.org/wiki/Breadth-first_search)]
   ```pseudocode
      procedure BFS(G, root) is
          let Q be a queue
          label root as discovered
          Q.enqueue(root)
          while Q is not empty do
              v := Q.dequeue()
              if v is the goal then
                  return v
              for all edges from v to w in G.adjacentEdges(v) do
                  if w is not labeled as discovered then
                      label w as discovered
                      Q.enqueue(w)
    ```
   - A*(A Star) - atrast ceļu labrintā. [[Link](https://en.wikipedia.org/wiki/A*_search_algorithm)]
   ```pseudocode
   function reconstruct_path(cameFrom, current)
       total_path := {current}
       while current in cameFrom.Keys:
           current := cameFrom[current]
           total_path.prepend(current)
       return total_path
   
   function A_Star(start, goal, h)
       openSet := {start}
   
       cameFrom := an empty map
   
       gScore := map with default value of Infinity
       gScore[start] := 0
   
       fScore := map with default value of Infinity
       fScore[start] := h(start)
   
       while openSet is not empty
           current := the node in openSet having the lowest fScore[] value
           if current = goal
               return reconstruct_path(cameFrom, current)
   
           openSet.Remove(current)
           for each neighbor of current
               tentative_gScore := gScore[current] + d(current, neighbor)
               if tentative_gScore < gScore[neighbor]
                   cameFrom[neighbor] := current
                   gScore[neighbor] := tentative_gScore
                   fScore[neighbor] := gScore[neighbor] + h(neighbor)
                   if neighbor not in openSet
                       openSet.add(neighbor)
   
       return failure
    ```
 - **Programmas struktūra**:
    - class *Labyrinth*:
        - method *create* - Inicializē un, ja prasīts - aizpilda labirinta masīvu izmantojot Prima algoritmu.
        - method *solveLabyrinthDFS* - Atgriezt masīvu ar koordinātēm, kuri pieder pareizajām ceļam caur labirintu izmantojot DFS algoritmu.
        - method *solveLabyrinthBFS* - Atgriezt masīvu ar koordinātēm, kuri pieder pareizajām ceļam caur labirintu izmantojot BFS algoritmu
        - method *solveLabyrinthAStar* - Atgriezt masīvu ar koordinātēm, kuri pieder pareizajām ceļam caur labirintu izmantojot A* algoritmu	
    
 - **Pienākumu sadale**:

| #   |      Uzdevums                | Darba apjoms |  KT  |  AS  |
| --- | ---------------------------- | :----------: | ---- | ---- |
| 1   | Labyrinth.create             | 10           | 10   |      |
| 2   | Labyrinth.solveLabyrinthDFS  | 10           | 10   |      |
| 3   | Labyrinth.solveLabyrinthBFS  | 10           | 10   |      |
| 4   | Labrinth.solveLabyrinthAStar | 10           |      | 10   |
| 5   | Prezentācijas sagatavošana   | 10           |      | 10   |
| 6   | Testa piemēru sagatavošana   | 10           | 5    | 5    |
|     | Kopā                         |              | 35   | 25
