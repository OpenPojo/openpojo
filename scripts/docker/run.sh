#!/bin/sh

set -x

CMD=$1
VERSION=$2
MVN_GOALS=$3

usage() {
  echo "Usage:"
  echo "\t $0 cleanup"
  echo "\t $0 rebuild [5|6|7|8|9]"
  echo "\t $0 test [5|6|7|8|9] \<mvn goals (i.e. test, package, ...etc)\>"
}

cleanup() {
  docker ps -aq |xargs docker rm -f
  docker images -q |xargs docker rmi
}

rebuild_docker() {
  cp Dockerfile_jdk${VERSION} Dockerfile
  docker build ./ -t jdk${VERSION}_openpojo
  rm -rf Dockerfile
}

test() {
  if [ -z ${MVN_GOALS} ]; then
    MVN_GOALS="clean test"
  fi

  JVM_MEM=2048m
  export CMD="export MAVEN_OPTS=\"-Xmx${JVM_MEM} -Xms${JVM_MEM} \${MORE_OPTS} \" && cd /openpojo && mvn ${MVN_GOALS} 2>&1"
  docker run --rm -it -v /Users/oshoukry/GitHub/openpojo:/openpojo jdk${VERSION}_openpojo sh -c "$CMD"
}

verify_version() {
  case ${VERSION#[-+]} in
    (*[!0-9]*|'')
      usage
      exit 1
      ;;
  esac

  if [ ${VERSION} -lt 5 -o ${VERSION} -gt 9 ]; then
    usage
    exit 1
  fi
}

case ${CMD} in
  rebuild)
    verify_version
    rebuild_docker
    ;;
  test)
    verify_version
    test
    ;;
  cleanup)
    cleanup
    ;;
  *)
    echo usage
    ;;
esac
