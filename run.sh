#!/bin/sh
echo "Current directory: $(pwd)"
echo "Listing /app directory:"
ls -la /app
echo "Environment PORT is: $PORT"
echo "Starting application..."
exec java -Xmx384m -Xms256m -jar /app/app.jar
