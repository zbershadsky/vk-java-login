#!/bin/sh

# Computes the absolute path of eXo
cd `dirname "$0"`

# Sets some variables
VK_OPTS="-Xshare:auto -Xms256m -Xmx512m"

#REMOTE_DEBUG="-Xdebug -Xrunjdwp:transport=dt_socket,address=8000,server=y,suspend=n"

JAVA_OPTS="$JAVA_OPTS $VK_OPTS $REMOTE_DEBUG"
export JAVA_OPTS

# Launches the server
exec "$PRGDIR"./catalina.sh "$@"
