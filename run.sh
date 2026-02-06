#!/bin/sh
echo "Current directory: $(pwd)"
echo "Listing /app directory:"
ls -la /app
echo "Environment PORT is: $PORT"
echo "Starting application..."
java -jar /app/app.jar
