# Env File Masker - IntelliJ Plugin

![ss](img/ss.png)


![Build Status](https://img.shields.io/badge/build-passing-brightgreen)
![Version](https://img.shields.io/badge/version-1.0.2-blue)
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

## Changelog

See [CHANGELOG.md](CHANGELOG.md) for a list of changes and version history.


