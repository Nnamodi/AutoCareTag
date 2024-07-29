
# AutoCareTag App

## Overview
AutoCareTag is designed to help vehicle service technicians steamline the process of logging, tracking and managing vehicle maintenance using NFC technology.

## Table of Contents
- [Installation](#installation)
- [Usage](#usage)
- [Contribution Guidelines](#contribution-guidelines)
- [License](#license)

## Installation
1. Set up SSH access for this repository. Instructions [here](https://docs.github.com/en/authentication/connecting-to-github-with-ssh)
2. Clone the repository:
   ```bash
   git clone git@github.com:slowburn-404/AutoCareTag.git
   ```
3. Navigate to the project directory:
   ```bash
   cd AutoCareTag/
   ```
4. Open in Android Studio, build and run.

## Contribution Guidelines
Please follow these guidelines:

1. **Create a Branch**: Use a descriptive branch name for your feature or bug fix.
   ```bash
   git checkout -b FEAT/your-feature-name
   ```
2. **Make Changes**: Implement your changes and write clear and concise commit messages.
3. **Build Project**
```bash
./gradlew clean build
```
4. **Linting**: Run code quality checks and linting.
```bash
./gradlew ktlintCheck
./gradlew detekt
```
6. **Submit a Pull Request**: Once you're done, push your branch to the remote repo and create a pull request to the main branch.
7. **Write Tests**: If applicable, include tests for your changes to ensure functionality.
8. **Documentation**: Update any relevant documentation to reflect your changes.

