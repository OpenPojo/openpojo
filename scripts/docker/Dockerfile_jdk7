FROM ubuntu

RUN dpkg --add-architecture i386 \
  && apt-get update \
  && apt-get install -y libc6:i386 libncurses5:i386 libstdc++6:i386 expect git

ADD ./jdk-7u80-linux-x64.tar.gz /opt/

RUN ln -sf /opt/jdk1.7.0_80 /java

RUN update-alternatives --install "/usr/bin/java" "java" "/java/bin/java" 1 \
  && update-alternatives --install "/usr/bin/javac" "javac" "/java/bin/javac" 1 \
  && update-alternatives --install "/usr/bin/javaws" "javaws" "/java/bin/javaws" 1

ADD http://mirrors.ocf.berkeley.edu/apache/maven/maven-3/3.2.5/binaries/apache-maven-3.2.5-bin.tar.gz /maven.tar.gz

RUN cd /opt \
  && tar -xzvf /maven.tar.gz \
  && rm -rf /maven.tar.gz \
  && update-alternatives --install "/usr/bin/mvn" "mvn" "`find /opt/ -name mvn`" 1

ENV MORE_OPTS -XX:MaxPermSize=512m \
  -XX:NewSize=256m \
  -XX:MaxNewSize=356m \
  -XX:PermSize=256m \
  -XX:+UseConcMarkSweepGC \
  -XX:+CMSPermGenSweepingEnabled \
  -XX:+CMSClassUnloadingEnabled