#!/usr/bin/env bash
EXPECTED_ARGS=2

if [ $# -ne $EXPECTED_ARGS ]
then
  echo "Usage: `basename $0` spot-file mph-dir"
  exit $E_BADARGS
fi

TMP=/tmp/spots
TTMP=/tmp/spots1
export MAVEN_OPTS="-Xmx3000m"

mvn exec:java -Dexec.mainClass="de.mpii.mph.cli.GenerateMinimalPerfectHashCLI" -Dexec.classpathScope=runtime -Dexec.args="-input $1 -output $2"

echo "sort spots by hash value"
sort -t'	' -nk2 $2/spot-mph.tsv > $TTMP
mv $TTMP $2/spot-mph.tsv

mvn exec:java -Dexec.mainClass="de.mpii.mph.cli.GenerateSpotFileCLI" -Dexec.classpathScope=runtime -Dexec.args="-output $2"
