#!/usr/bin/env bash
mvn package install
java -jar target/elevator-1.0.0-SNAPSHOT-fat.jar
