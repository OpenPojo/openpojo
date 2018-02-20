FROM ubuntu

RUN dpkg --add-architecture i386 \
  && apt-get update \
  && apt-get install -y libc6:i386 libncurses5:i386 libstdc++6:i386 expect git

ADD ./jdk-9.0.4_linux-x64_bin.tar.gz /opt/

RUN ln -sf /opt/jdk-9.0.4 /java

RUN update-alternatives --install "/usr/bin/java" "java" "/java/bin/java" 1 \
  && update-alternatives --install "/usr/bin/javac" "javac" "/java/bin/javac" 1 \
  && update-alternatives --install "/usr/bin/javaws" "javaws" "/java/bin/javaws" 1

ADD http://mirrors.ocf.berkeley.edu/apache/maven/maven-3/3.5.2/binaries/apache-maven-3.5.2-bin.tar.gz /maven.tar.gz

RUN cd /opt \
  && tar -xzvf /maven.tar.gz \
  && rm -rf /maven.tar.gz \
  && update-alternatives --install "/usr/bin/mvn" "mvn" "`find /opt/ -name mvn`" 1

ENV MORE_OPTS -Dmaven.compiler.source=1.6 \
  -Dmaven.compiler.target=1.6
