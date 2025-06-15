#!/bin/bash

# Script to build and run the Relog Airline Check-in System

echo "Building the application..."
mvn clean install

if [ $? -eq 0 ]; then
    echo "Build successful! Starting the application..."
    mvn spring-boot:run
else
    echo "Build failed. Please check the error messages above."
    exit 1
fi