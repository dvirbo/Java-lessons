# Bounded NxM puzzle tile puzzle
In the game there is a board of size NxM containing  NxM-1 blocks numbered from 1 to NxM-1 and an empty block.
Some of the blocks are painted in white and part painted in red.
In addition, on each white block is written how many moves can be made with it. The blocks are arranged in a given starting order
any, and the goal is to find the cheapest number of operations from the initial arrangement to the final state. 
In the final state all the blocks arranged from 1 to NxM-1 from left to right and from top to bottom (regardless of their color), 
with the empty block in the right corner


the algorithem that we use in the search:
DFID,A*, IDA*, DFBnB