#!/bin/bash

echo "set term png;
set output \"mandel.png\";
set grid;
set pm3d map;
set size square;
splot \"mandel.data\"" | gnuplot
