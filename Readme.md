# Clojure compilation diff demo

This is a project to demonstrate an oddity that I have observed - that **the
Clojure compiler can produce different output for the same input**. Though
otherwise harmless in terms of the functionality of the resulting Java program,
this is an issue for any attempts to cache the compiled classes based on simple
byte equality, such as with Docker layer caching.

## Test scenario

This project is an extremely minimal deps.edn Clojure project; it has a core
namespace, a `-main` function and imports some arbitrary Clojure libraries, but
nothing else.

The script `docker-test.sh` builds and runs the test container. In turn, the
container runs `test.sh`; this does the following:
- Compiles the code (via a simple `build.clj` file)
- Compiles the output again (to a different location)
- Runs a diff on the two build output directories

### Expected output

Most of the time, the test run yields output indicating that the resulting class
files differ, e.g.:

```shell
$ ./docker-test.sh

(docker build output...)

Copying source...
Compiling compile-test.core...
Copying source...
Compiling compile-test.core...
176 class files differ
```

However, sometimes the build output yields no differences:
```shell
$ ./docker-test.sh
...
0 class files differ
```


## Usage

Simply run `./docker-test.sh` from the project root directory.

## Environment

My test runs were run using:
- Linux; Pop OS 22.04 LTS
- Kernel version 6.2.6-76060206
- Docker version 23.0.5, build bc4487a

## Motivation

This difference in output is an issue for docker layer caching. Ideally, it
would be possible to compile third-party clojure libraries and store these in a
separate Docker layer in containers for Clojure apps. If such a layer resulted
in the same byte output given a particular fixed set of dependencies, then the
layer could be recognised as already existing when pushing to a repository. This
would avoid the storage & network costs that result from creating a whole new
uberjar for every single source change within your application code.

Such caching is possible when not using AOT compilation, but this comes at the
cost of slower application startup.

## License

Copyright © 2023 Robert Johnson

Distributed under the Eclipse Public License version 1.0.
