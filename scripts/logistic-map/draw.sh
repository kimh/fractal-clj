#!/bin/bash

echo "set term aqua;
plot \"data/logistic-map.data\" with points pointtype 9 pointsize 0.1" | gnuplot
