version=0.0.1-SNAPSHOT
options=-Drevision=$(version) -Pfix

.PHONY: *

# The first command will be invoked with `make` only and should be `build`
build:
	docker info && ./mvnw clean verify $(options) && docker compose build

run:
	docker compose up

run-deps:
	docker compose up users notes

stop:
	docker compose down

clean:
	./mvnw clean $(options)
