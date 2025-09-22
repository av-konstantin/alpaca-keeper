@ECHO OFF
SETLOCAL

SET WRAPPER_JAR=.mvn\wrapper\maven-wrapper.jar

IF NOT EXIST "%WRAPPER_JAR%" (
  ECHO Downloading Maven Wrapper...
  IF EXIST "%ProgramFiles%\Git\usr\bin\curl.exe" (
    "%ProgramFiles%\Git\usr\bin\curl.exe" -fsSL -o "%WRAPPER_JAR%" https://repo.maven.apache.org/maven2/org/apache/maven/wrapper/maven-wrapper/3.3.2/maven-wrapper-3.3.2.jar
  ) ELSE (
    powershell -Command "(New-Object Net.WebClient).DownloadFile('https://repo.maven.apache.org/maven2/org/apache/maven/wrapper/maven-wrapper/3.3.2/maven-wrapper-3.3.2.jar','%WRAPPER_JAR%')"
  )
)

SET JAVA_CMD=java
IF NOT "%JAVA_HOME%"=="" SET JAVA_CMD="%JAVA_HOME%\bin\java.exe"

%JAVA_CMD% -version >NUL 2>&1
IF %ERRORLEVEL% NEQ 0 (
  ECHO Java not found.
  EXIT /B 1
)

%JAVA_CMD% -cp "%WRAPPER_JAR%" org.apache.maven.wrapper.MavenWrapperMain %*
