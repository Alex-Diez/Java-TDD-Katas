#!/usr/bin/env bash
mvn clean install
java -jar target/jmh-stack.jar
