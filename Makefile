.PHONY: clean build

clean:
	./gradlew clean

build: clean
	./gradlew build

cb: clean build