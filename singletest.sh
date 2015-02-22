#!/bin/sh
if [ $# -lt 1 ]; then
    exit 1
fi
exec mvn -Prun-its invoker:run -Dinvoker.test="$1"
