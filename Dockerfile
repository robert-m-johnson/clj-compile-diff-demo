FROM clojure:temurin-17-tools-deps-1.11.1.1273-bullseye as app-build

COPY ./deps.edn ./
RUN clj -A:test:build -P
COPY ./. ./.

CMD sh ./test.sh
