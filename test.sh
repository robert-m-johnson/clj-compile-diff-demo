#!/bin/sh
set -e

rm -rf target1 target2

clj -T:build build
mv target target1

clj -T:build build
mv target target2
diff -qr target1 target2 | echo "$(wc -l) class files differ"
