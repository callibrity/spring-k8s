#!/bin/bash

./mvnw clean spring-boot:build-image k8s:push k8s:resource k8s:apply