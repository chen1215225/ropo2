#!/usr/bin/env bash
PRG="$0"
JAVA_OPT="-Xms1g -Xmx2g"

# Get standard environment variables
PRGDIR=`dirname "$PRG"`
APPLICATION_HOME=`cd "$PRGDIR/.." >/dev/null; pwd`

#MBG生成指令
cd $APPLICATION_HOME


mvn clean package install -DskipTests -e -X