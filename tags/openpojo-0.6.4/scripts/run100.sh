#!/bin/sh

function switch_jdk() {
         local wanted_java_version=$1
         export JAVA_HOME=`/usr/libexec/java_home -F -v $wanted_java_version -a x86_64 -d64`
        
         # cleaned PATH
         export PATH=$(echo $PATH | sed -E "s,(/System)?/Library/Java/JavaVirtualMachines/[a-zA-Z0-9._]+/Contents/Home/bin:,,g")
        
         # prepend wanted JAVA_HOME
         export PATH=$JAVA_HOME/bin:$PATH
        
         echo "Now using : "
         java -version
        }

switch_jdk 1.6

COUNTER=1;

pushd ../

while [ $COUNTER -gt 0 ]; do
  COUNTER=`expr $COUNTER - 1`
  mvn clean package > run.$COUNTER

  ISSUCCESSFUL=`cat run.$COUNTER |grep "BUILD SUCCESS" |wc -l`

  if [ $ISSUCCESSFUL -gt 0 ]; then
    rm -rf run.$COUNTER
  else
    echo "ERROR: Failure detected in run.$COUNTER, file not removed."
  fi

done

popd