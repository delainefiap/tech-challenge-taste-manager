#!/bin/sh
set -e

# Default DB connection params (can be overridden via env)
: ${SPRING_DATASOURCE_URL:=jdbc:mysql://mysql:3306/tastemanager}
: ${SPRING_DATASOURCE_USERNAME:=tastemanager}
: ${SPRING_DATASOURCE_PASSWORD:=tastemanager}

# Extract host and port from JDBC URL (simple parsing)
# Expected formats: jdbc:mysql://host:port/dbname?params or jdbc:mysql://host/dbname
JDBC=${SPRING_DATASOURCE_URL#jdbc:mysql://}
HOSTPORT=${JDBC%%/*}
HOST=${HOSTPORT%%:*}
PORT=${HOSTPORT#*:}
if [ "$PORT" = "$HOST" ]; then
  PORT=3306
fi

echo "Waiting for MySQL at $HOST:$PORT..."
# Wait for mysql to be available
until mysqladmin ping -h "$HOST" -P "$PORT" --silent; do
  echo "MySQL is unavailable - sleeping"
  sleep 2
done

echo "MySQL is up - importing SQL (if present)"
if [ -f /docker-entrypoint/import.sql ]; then
  mysql -h "$HOST" -P "$PORT" -u"$SPRING_DATASOURCE_USERNAME" -p"$SPRING_DATASOURCE_PASSWORD" tastemanager < /docker-entrypoint/import.sql || true
fi

# Start the app (pass all envs through)
exec java -jar /app.jar

