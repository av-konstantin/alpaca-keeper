#!/bin/sh
# ----------------------------------------------------------------------------
# Maven Wrapper Startup Script (compatible, simplified)
# ----------------------------------------------------------------------------

WRAPPER_JAR=".mvn/wrapper/maven-wrapper.jar"
MVNW_REPOURL=""

# resolve script dir
PRG="$0"
while [ -h "$PRG" ]; do
  ls=`ls -ld "$PRG"`
  link=`expr "$ls" : '.*-> \(.*\)$'`
  if expr "$link" : '/.*' > /dev/null; then
    PRG="$link"
  else
    PRG=`dirname "$PRG"`/"$link"
  fi
done
BASEDIR=`dirname "$PRG"`
[ -z "$BASEDIR" ] && BASEDIR='.'

cd "$BASEDIR"

# Download wrapper jar if not present
if [ ! -f "$WRAPPER_JAR" ]; then
  echo "Downloading Maven Wrapper..."
  mkdir -p .mvn/wrapper
  WRAPPER_URL="https://repo.maven.apache.org/maven2/org/apache/maven/wrapper/maven-wrapper/3.3.2/maven-wrapper-3.3.2.jar"
  if command -v curl >/dev/null 2>&1; then
    curl -fsSL -o "$WRAPPER_JAR" "$WRAPPER_URL"
  elif command -v wget >/dev/null 2>&1; then
    wget -q -O "$WRAPPER_JAR" "$WRAPPER_URL"
  else
    echo "Error: curl or wget is required to download the Maven Wrapper." >&2
    exit 1
  fi
fi

# Locate Java
if [ -n "$JAVA_HOME" ] ; then
  JAVA_CMD="$JAVA_HOME/bin/java"
else
  JAVA_CMD="java"
fi

# Verify Java
$JAVA_CMD -version >/dev/null 2>&1 || { echo "Java not found." >&2; exit 1; }

# Execute Maven Wrapper
exec "$JAVA_CMD" -cp "$WRAPPER_JAR" org.apache.maven.wrapper.MavenWrapperMain "$@"
