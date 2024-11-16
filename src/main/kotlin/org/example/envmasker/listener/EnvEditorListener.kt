FROM gradle:8.5-jdk17

WORKDIR /app
COPY . .

# Build the plugin
RUN ./gradlew buildPlugin

# The plugin will be in /app/build/distributions/EnvMasker-1.0-SNAPSHOT.zip 