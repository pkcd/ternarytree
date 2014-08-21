#!/usr/bin/env bash
EXPECTED_ARGS=4

if [ $# -ne $EXPECTED_ARGS ]
then
  echo "Usage: `basename $0` spot-file perfect-min-hash-file bin-spot-file elias-fano-index"
  exit $E_BADARGS
fi

TMP=/tmp/spots
TTMP=/tmp/spots1
export MAVEN_OPTS="-Xmx3000m"

#mvn exec:java -Dexec.mainClass="de.mpii.mph.cli.GenerateMinimalPerfectHashCLI" -Dexec.classpathScope=runtime -Dexec.args="-input $1 -h $2 -output $TMP"

echo "sort spots by hash value"
sort -t'	' -nk2 $TMP > $TTMP
mv $TTMP $TMP

mvn exec:java -Dexec.mainClass="de.mpii.mph.cli.GenerateSpotFileCLI" -Dexec.classpathScope=runtime -Dexec.args="-input $TMP -output $3 -e $4"
