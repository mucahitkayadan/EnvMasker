# Changelog

All notable changes to the Env File Masker plugin will be documented in this file.

## [1.0.4] - 2025-05-19
### Changed
- Extended compatibility to support IntelliJ 2025.1 (build 251) and future versions
- Migrated to IntelliJ Platform Gradle Plugin 2.5.0
- Enhanced build system for better compatibility
- Removed upper IDE version limit for unlimited compatibility

### Notes
- Tested and verified working on WebStorm 2025.1
- Compatible with all future IDE versions without requiring updates

## [1.0.3] - 2025-02-27
### Changed
- Masks real-time changes to .env files

### Notes
- Tested and verified working on Intellij IDEA Community 2024.2.4

## [1.0.2] - 2024-11-27
### Fixed
- Fixed issue with first line being unmasked on file open
- Fixed state persistence when switching between files
- Improved masking behavior when reopening files

### Changed
- Migrated to Java
- Updated to support IntelliJ IDEA 2024.3
- Improved file type detection for .env files
- Enhanced click-to-toggle functionality
- Plugin icon changed, dark mode icon added

### Notes
- Tested and verified working on IntelliJ IDEA 2024.3
- Support for additional JetBrains IDEs (PyCharm, WebStorm, etc.) coming in future releases

## [1.0.1] - 2024-11-13
### Added
- Initial release with basic masking functionality
- Click-to-toggle feature for masked values
- Support for .env file type

### Fixed
- Basic folding implementation for environment variables