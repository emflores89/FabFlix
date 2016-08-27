#!/bin/bash
from sys import argv

# store the values in lists
#TJ = Time Spent on JBDC
TJ = []
#TS - Time Spent on Servlet
TS = []
# store the command parameters in variable
script, logFileName_ = argv
# open a file reader
logFile = open(logFileName_, 'r')
# read the file line by line
count = 0;
for line in logFile:
	# Maybe you can decide to somehow 'mark' those log records that you care about?
        if not '####' in line:
                continue
	# Clean the string, remove the mark, and tokenize it
        #times = line.strip()[4:].split(' ')
	times = line.strip().split();
	# times is an array that contains the sample values ...
	# print them? use them to calculate averages? etc.
	TJ.append(int(times[-2]))
	TS.append(int(times[-1]))
        print "JDBC TIME: " + times[-2] + " " + ", SEARCH SERVLET TIME: " + times[-1]
        count = count + 1

if(count > 0):
        print ""
        print "TJ (AVG JDBC): " + str(sum(TJ) / float(len(TJ)))
        print "TS (AVG SERVLET): " + str(sum(TS) / float(len(TS)))

else:
        print "No logs were found in the file"
