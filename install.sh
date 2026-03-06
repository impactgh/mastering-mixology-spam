#!/bin/bash

echo "Building Mastering Mixology Helper Plugin..."
./gradlew build

if [ $? -eq 0 ]; then
    echo ""
    echo "Build successful!"
    echo ""
    echo "Plugin JAR location:"
    echo "  $(pwd)/build/libs/mastering-mixology-plugin-1.0-SNAPSHOT.jar"
    echo ""
    echo "To install in RuneLite:"
    echo "  1. Copy the JAR file to your RuneLite plugins folder"
    echo "  2. Restart RuneLite"
    echo "  3. Enable the plugin in the Plugin Hub"
    echo ""
    echo "Or run in development mode:"
    echo "  ./gradlew run"
else
    echo "Build failed. Please check the error messages above."
    exit 1
fi
