#!/bin/sh

if [ "x$1" = "x" ]; then
  MVN_GOALS="clean test"
else
  MVN_GOALS="$1" 
fi

JVM_MEM=512m
export CMD="export MAVEN_OPTS=\"-Xmx${JVM_MEM} -Xms${JVM_MEM}\" && cd /openpojo && mvn ${MVN_GOALS} 2>&1"
docker run --rm -it -v /Users/oshoukry/GitHub/openpojo:/openpojo jdk5_openpojo sh -c "$CMD"
