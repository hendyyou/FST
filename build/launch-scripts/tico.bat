@echo off

set JAR="tico.jar"

java -Xmx256m -classpath %JAR% -jar %JAR% %1 %2