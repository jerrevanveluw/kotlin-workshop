options=-Pformat

.PHONY: *

# The first command will be invoked with `make` only and should be `build`
build:
	docker info && ./mvnw verify $(options) && docker compose build

clean:
	./mvnw clean $(options)

compile:
	./mvnw test-compile $(options)

docker:
	docker info

run:
	docker compose up

run-deps:
	docker compose up users notes

stop:
	docker compose down

update:
	./mvnw versions:update-parent versions:update-properties versions:use-latest-versions
