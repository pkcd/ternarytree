#!/usr/bin/env bash
EXPECTED_ARGS=4

if [ $# -ne $EXPECTED_ARGS ]
then
  echo "Usage: `basename $0` perfect-min-hash-file bin-spot-file elias-fano-index query"
  exit $E_BADARGS
fi
export MAVEN_OPTS="-Xmx3000m"


mvn exec:java -Dexec.mainClass="de.mpii.mph.cli.GetSpotKeyCLI" -Dexec.classpathScope=runtime -Dexec.args="-h $1 -e $3 -s $2 -q $4" 