#!/usr/bin/env bash
mvn clean install

java -XX:+UnlockDiagnosticVMOptions -XX:+WhiteBoxAPI -XX:-RestrictContended  -Xbootclasspath/a:../../../libs/jcstress-core-1.0-SNAPSHOT.jar;./target/jcstress-stack.jar -jar target/jcstress-stack.jar -time 5000
