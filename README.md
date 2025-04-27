# Env File Masker - IntelliJ Plugin

![ss](img/ss.png)


![Build Status](https://img.shields.io/badge/build-passing-brightgreen)
![Version](https://img.shields.io/badge/version-1.0.4-blue)
![License](https://img.shields.io/badge/license-MIT-green)
[![JetBrains Plugins](https://img.shields.io/jetbrains/plugin/d/25857-env-file-masker.svg)](https://plugins.jetbrains.com/plugin/25857-env-file-masker)


A security-focused IntelliJ plugin that automatically masks sensitive values in `.env` files to prevent accidental exposure of sensitive data.

## Features

- 🔒 **Automatic Value Masking**: Automatically masks values in `.env` files
- 👆 **Click to Reveal**: Simply click on masked values to reveal them
- 🔄 **Auto-Hide**: Values automatically re-mask when clicking elsewhere
- 🎯 **Smart Detection**: Works with any `.env` file format
- 🛠 **IDE Support**: Compatible with all IntelliJ-based IDEs

## Installation

### From JetBrains Marketplace
1. Open IntelliJ IDEA
2. Go to `Settings/Preferences` → `Plugins`
3. Search for "Env File Masker"
4. Click `Install`
5. Restart IDE when prompted

### Manual Installation
1. Download the latest release `.zip` file
2. In IntelliJ IDEA, go to `Settings/Preferences` → `Plugins`
3. Click the gear icon and select `Install Plugin from Disk`
4. Select the downloaded `.zip` file
5. Restart IDE when prompted

## Usage

1. Open any `.env` file in your project
2. Values will automatically be masked with asterisks (`********`)
3. Click on a masked value to reveal it
4. Click anywhere else to re-mask the value

## For Developers

### Development Requirements
- Java 17 or newer (Java 21 recommended)
- Gradle 8.5 or newer
- IntelliJ IDEA (Community or Ultimate)

### Building the Plugin
The project uses IntelliJ Platform Gradle Plugin 2.5.0. To build the plugin:

```bash
# Clone the repository
git clone https://github.com/mucahitkayadan/EnvMasker.git
cd EnvMasker

# Build the plugin
./gradlew cleanBuildPlugin
```

The built plugin will be available in the `build/distributions` directory.

### IDE Compatibility
- Minimum supported IDE version: 2023.2 (build 232)
- No upper compatibility limit (all future IDE versions supported)
- Tested on WebStorm 2025.1, IntelliJ IDEA 2024.1, and other JetBrains IDEs

### Contributing
Contributions are welcome! Please feel free to submit a Pull Request.

## Changelog

See [CHANGELOG.md](CHANGELOG.md) for a list of changes and version history.


