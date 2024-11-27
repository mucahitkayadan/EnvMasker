# Changelog

All notable changes to the Env File Masker plugin will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

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