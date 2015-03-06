#!/bin/sh
usage() {
cat <<EOF
$0 javascript/pom.xml
EOF
}

if [ $# -lt 1 ]; then
    usage
    exit 1
fi
#exec mvn -Prun-its invoker:run -Dinvoker.test="$1"
exec mvn -Prun-its -Dinvoker.test="$1" verify
