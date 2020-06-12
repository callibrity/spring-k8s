#!/bin/bash
./mvnw build-helper:parse-version \
       versions:set -DnewVersion=\${parsedVersion.majorVersion}.\${parsedVersion.minorVersion}.\${parsedVersion.incrementalVersion} \
       versions:commit
./mvnw clean \
       spring-boot:build-image \
       k8s:push \
       k8s:resource \
       k8s:apply
./mvnw build-helper:parse-version \
       versions:set -DnewVersion=\${parsedVersion.majorVersion}.\${parsedVersion.minorVersion}.\${parsedVersion.nextIncrementalVersion}-SNAPSHOT \
       versions:commit