set path=%GMTHOME%/win;%GS_BIN%
classpath.bat
java -Xms256m -Xmx512m scenarioGeneration.experimentBuilder.PSWClient %1 %2 %3 %4 %5
