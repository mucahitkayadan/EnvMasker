FROM gradle:8.5-jdk17

WORKDIR /app

# Copy only the build files first to leverage Docker caching
COPY build.gradle.kts settings.gradle.kts ./
COPY gradle gradle
COPY gradlew gradlew

# Download dependencies
RUN ./gradlew --no-daemon dependencies

# Now copy the source code
COPY src src

# Build the plugin
RUN ./gradlew --no-daemon buildPlugin

# The plugin will be in /app/build/distributions/EnvMasker-1.0-SNAPSHOT.zip

