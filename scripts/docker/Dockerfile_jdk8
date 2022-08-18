FROM ubuntu

RUN dpkg --add-architecture i386 \
  && apt-get update \
  && apt-get install -y libc6:i386 libncurses5:i386 libstdc++6:i386 expect git

ADD ./jdk-8u152-linux-x64.tar.gz /opt/

RUN ln -sf /opt/jdk1.8.0_152 /java

RUN update-alternatives --install "/usr/bin/java" "java" "/java/bin/java" 1 \
  && update-alternatives --install "/usr/bin/javac" "javac" "/java/bin/javac" 1 \
  && update-alternatives --install "/usr/bin/javaws" "javaws" "/java/bin/javaws" 1

ADD http://mirrors.ocf.berkeley.edu/apache/maven/maven-3/3.5.2/binaries/apache-maven-3.5.2-bin.tar.gz /maven.tar.gz

RUN cd /opt \
  && tar -xzvf /maven.tar.gz \
  && rm -rf /maven.tar.gz \
  && update-alternatives --install "/usr/bin/mvn" "mvn" "`find /opt/ -name mvn`" 1
