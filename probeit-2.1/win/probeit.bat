set path=%GMTHOME%/win;%GS_BIN%
classpath.bat
java -Xms256m -Xmx512m probeIt.ProbeIt %1 %2 %3
