#!/usr/bin/env bash
EXPECTED_ARGS=2

if [ $# -ne $EXPECTED_ARGS ]
then
  echo "Usage: `basename $0` mph-dir query"
  exit $E_BADARGS
fi
export MAVEN_OPTS="-Xmx3000m"


mvn exec:java -Dexec.mainClass="de.mpii.mph.cli.GetSpotKeyCLI" -Dexec.classpathScope=runtime -Dexec.args="-i $1 -q $2" 