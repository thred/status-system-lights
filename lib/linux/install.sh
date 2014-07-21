#!/bin/sh

mvn install:install-file -DgroupId=com.mfizz -DartifactId=rxtx -Dpackaging=jar -Dversion=2.2 -Dfile=RXTXcomm.jar -DgeneratePom=true
