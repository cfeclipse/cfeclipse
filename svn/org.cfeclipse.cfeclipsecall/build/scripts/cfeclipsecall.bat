@echo off
java -jar cfeclipsecall.jar %1 %2
IF %ERRORLEVEL% NEQ 0 PAUSE