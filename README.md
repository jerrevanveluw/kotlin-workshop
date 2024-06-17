# Workshop Software Engineering

Workshop in Kotlin with DDD, ArrowKt, Value Classes, and API Specifications

## Dependencies

* JDK (recommend through [SDKMAN](https://sdkman.io/))
* [Docker](https://www.docker.com/get-started/)

## Quick Start

Build everything you need:

```shell
make build
```

Then run it:

```shell
make run
```

Use [requests.http](.test/requests.http) to check it out in IntelliJ! Or check:

* [localhost:8080/api/users](http://localhost:8080/api/users)
* [localhost:3000/api/notes](http://localhost:3000/api/notes)

### Dev Dependencies

* node.js through [Node Version Manager](https://formulae.brew.sh/formula/nvm) Homebrew Formulae for Mac

```shell
make dev
```

To install JDK and Node.js versions through SDKMAN and Node Version Manager and build the dist/bundle.js
