FROM ubuntu

RUN dpkg --add-architecture i386 \
  && apt-get update \
  && apt-get install -y libc6:i386 libncurses5:i386 libstdc++6:i386 expect git

ADD ./jdk-1_5_0_22-linux-amd64.bin /
ADD ./installer_jdk5.sh /installer.sh

RUN chmod 755 /jdk-1_5_0_22-linux-amd64.bin /installer.sh

RUN  /installer.sh \
  && mv /jdk1.5.0_22 /opt/ \
  && ln -sf /opt/jdk1.5.0_22 /java \
  && rm -rf /jdk-1_5_0_22-linux-amd64.bin /installer.sh

RUN update-alternatives --install "/usr/bin/java" "java" "/java/bin/java" 1 \
  && update-alternatives --install "/usr/bin/javac" "javac" "/java/bin/javac" 1

ADD http://mirrors.ocf.berkeley.edu/apache/maven/maven-3/3.1.1/binaries/apache-maven-3.1.1-bin.tar.gz /maven.tar.gz

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
