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

LOGFILE=$0.vm.log

switch_jdk 1.6

pushd ../
mvn clean package > $LOGFILE 2>&1
tail $LOGFILE

switch_jdk 1.7
mvn clean package > $LOGFILE 2>&1
tail $LOGFILE

switch_jdk 1.8
mvn clean package > $LOGFILE 2>&1
tail $LOGFILE

mvn clean
rm -rf $LOGFILE

popd
