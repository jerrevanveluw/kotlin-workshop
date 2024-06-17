.PHONY: *

# The first command will be invoked with `make` only and should be `build`
build: docker
	./mvnw verify -Pformat && docker compose build

all: run-deps clean build run

clean:
	./mvnw clean

docker:
	docker info

format:
	./mvnw test-compile -Pformat

run:
	docker compose up

run-deps:
	docker compose up users notes -d

stop:
	docker compose down

update:
	./mvnw versions:update-parent versions:update-properties versions:use-latest-versions
