#!/usr/bin/env bash
PRG="$0"
JAVA_OPT="-Xms1g -Xmx2g"

# Get standard environment variables
PRGDIR=`dirname "$PRG"`
APPLICATION_HOME=`cd "$PRGDIR/.." >/dev/null; pwd`
cd $APPLICATION_HOME

# 执行指令

if [ $# != 2 ] ; then
    echo "请输入正确的 标签 备注"
    exit 1;
fi

echo "开始打包项目"
mvn clean package -DskipTests
echo "推送Git标签"
mkdir -p target/$1 && pwd && cp target/*.zip target/$1/ && \
    cd target/$1 \
    && git init \
    && git add * \
    && git commit -am "$2" \
    && git remote add origin git@git.ipukr.cn:delai/bees-tops.git \
    && git tag $1 \
    && git push --tag origin $1