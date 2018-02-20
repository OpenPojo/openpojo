#!/bin/sh

check_jdk() {
  VERSION=${1}
  DOCKER_CONTAINER="jdk${VERSION}_openpojo"
  CREATED=`docker images -q ${DOCKER_CONTAINER}`
    if [ -z "${CREATED}" ]; then
      echo "docker container for jdk${VERSION} not created, creating..."
      pushd docker
      ./run.sh rebuild ${VERSION}
      popd
    fi
    rm -rf jdk${VERSION}.results.txt
    docker/run.sh test ${VERSION} > jdk${VERSION}.results.out
}

if [ -z "${1}" ]; then
  for COUNTER in {5..9}; do
    check_jdk ${COUNTER}
  done
else
  check_jdk ${1}
fi
