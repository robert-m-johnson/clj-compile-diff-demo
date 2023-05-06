#!/bin/sh
set -e
docker build --network host -t compile-test . && docker run --rm compile-test
