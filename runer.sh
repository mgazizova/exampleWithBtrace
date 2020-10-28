#!/bin/bash

WORKPLACE=$(pwd)
PATH_TO_BTRACE="/Users/mariagazizova"

btracec $WORKPLACE/src/main/java/TracingScript.java

java -javaagent:$PATH_TO_BTRACE/.btrace/libs/btrace-agent.jar=script=$WORKPLACE/TracingScript.class,trusted=true,scriptOutputFile=$WORKPLACE/out/out.txt $WORKPLACE/src/main/java/example/Main.java