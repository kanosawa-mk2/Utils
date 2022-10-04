@echo off
cd /d %~dp0

set JAVA_V="D:\dev\pleiades-2022-09\java\11\bin\java"
setCLASSPATH=.\

goto main

:setpath
setCLASSPATH=%CLASSPATH%;%1
exit /b

:main
for %%i in (.\lib\*.jar) do call :setpath %%i

%JAVA_V% -cp %CLASSPATH% my.bat.base.service.impl.Main %*
