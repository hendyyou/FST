#!/bin/bash

JAR="tico.jar"

java -Xmx256m -Djava.library.path=libs -jar $JAR $*
