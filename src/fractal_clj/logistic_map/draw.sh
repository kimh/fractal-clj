#!/bin/bash

echo "set term aqua;
plot \"../../../output.data\" with points pointtype 9 pointsize 0.1" | gnuplot
