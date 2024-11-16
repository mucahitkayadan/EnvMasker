FROM gradle:8.5-jdk17

WORKDIR /app

# Install dos2unix
RUN apt-get update && apt-get install -y dos2unix

# Copy the entire project
COPY . .

# Fix line endings and permissions
RUN chmod +x ./gradlew && \
    dos2unix ./gradlew && \
    dos2unix ./gradle/wrapper/gradle-wrapper.properties

# Show the content of the problematic file for debugging
RUN cat src/main/kotlin/org/example/envmasker/listener/EnvEditorListener.kt

# Build with more verbose output
RUN ./gradlew buildPlugin --stacktrace --info

# The plugin will be in /app/build/distributions/EnvMasker-1.0-SNAPSHOT.zip

