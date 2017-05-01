#!/bin/bash

dir=/c/dev/docker/aiblockchain-hana/docker/turing/Main-1.0/lib
for i in `ls $dir`
do
  echo mvn deploy:deploy-file -Durl=file:///c/dev/git/aiblockchain/aiblockchain-websocket/repo/ -Dfile=$dir/$i -DgroupId=com.aiblockchain -DartifactId=local-${i::-4} -Dpackaging=jar -Dversion=1.0
  mvn deploy:deploy-file -Durl=file:///c/dev/git/aiblockchain/aiblockchain-websocket/repo/ -Dfile=$dir/$i -DgroupId=com.aiblockchain -DartifactId=local-${i::-4} -Dpackaging=jar -Dversion=1.0
done

