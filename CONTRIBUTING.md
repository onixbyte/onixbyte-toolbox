# Contributing to OnixByte Toolbox

We appreciate your interest in contributing to our Java Enterprise Utility Library. Contributions
are welcome in various forms such as code, documentation, bug reports, and test cases. To ensure a
smooth collaboration, please follow the guidelines outlined below.

## Getting Started

The OnixByte Toolbox is an open-source Java library designed to ease Java enterprise
application development. It is built with Java 17, and we require contributors to use OpenJDK 17.

## Development Setup

There is no need for manual setup beyond ensuring you have JDK 17 and a stable internet connection.
Our project uses Gradle Wrapper for building, simplifying the setup process.

```shell
./gradlew build
```

## Branching Strategy

We follow the `git-flow` branching model. Contributors should fork this repository and create their
work on separate branches prefixed with `feature/` or `hotfix/` as appropriate.

## Code Style

Please adhere to the coding standards specified in our `.editorconfig` file in the root of
the repository. Consistent style helps in maintaining readability and uniformity across the codebase.

## Commit Messages

We require that commit messages follow this structure:

```text
type[(scope)]: subject

[body]

[BREAKING CHANGE: breaking changes]
```

This format helps in auto-generating changelogs and understanding the purpose behind changes.

## Submitting Contributions

1. Fork the repository.
2. Create a branch: `git checkout -b feature/my-new-feature` or `git checkout -b hotfix/my-fix`.
3. Make your changes and commit them.
4. Push to the branch: `git push origin feature/my-new-feature`.
5. Create a Pull Request with a succinct subject and a detailed body.

## Testing

We encourage using JUnit Jupiter for unit and integration tests. Pull Requests with accompanying
test reports are prioritised for review and merging.

## License

By contributing to the OnixByte Toolbox, you agree that your contributions will be licensed under the
MIT license. If you do not agree to this, please refrain from contributing.
