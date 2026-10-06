@echo off
setlocal
set "MAVEN_VERSION=3.9.16"
set "MAVEN_HOME=%USERPROFILE%\.m2\wrapper\dists\apache-maven-%MAVEN_VERSION%"
set "MAVEN_BIN=%MAVEN_HOME%\bin\mvn.cmd"
if exist "%MAVEN_BIN%" goto run
set "DIST=%USERPROFILE%\.m2\wrapper\dists\apache-maven-%MAVEN_VERSION%-bin.zip"
if not exist "%DIST%" (
  echo Downloading Apache Maven %MAVEN_VERSION%...
  powershell -NoProfile -ExecutionPolicy Bypass -Command "$u='https://repo.maven.apache.org/maven2/org/apache/maven/apache-maven/%MAVEN_VERSION%/apache-maven-%MAVEN_VERSION%-bin.zip'; New-Item -ItemType Directory -Force -Path (Split-Path '%DIST%') | Out-Null; Invoke-WebRequest -Uri $u -OutFile '%DIST%'"
  if errorlevel 1 exit /b 1
)
powershell -NoProfile -ExecutionPolicy Bypass -Command "$d='%MAVEN_HOME%'; New-Item -ItemType Directory -Force -Path $d | Out-Null; Expand-Archive -Force '%DIST%' '%USERPROFILE%\.m2\wrapper\dists'; $inner=Join-Path '%USERPROFILE%\.m2\wrapper\dists' 'apache-maven-%MAVEN_VERSION%'; if(Test-Path $inner){Move-Item -Force (Join-Path $inner '*') $d}" 
if errorlevel 1 exit /b 1
:run
call "%MAVEN_BIN%" %*
exit /b %ERRORLEVEL%
