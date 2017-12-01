# mini-http-server

A small, Java based HTTP/HTTPS web server. ( First I created it for myself. :) ) It's very useful for local Javascript (Angular JS, Progressive Web Apps, etc.) web development.

| Build | Code coverage |
| ------------- | ------------- |
| ![Build status](https://travis-ci.org/szrnka-peter/mini-http-server.svg?branch=master) | [![codecov](https://codecov.io/gh/szrnka-peter/mini-http-server/branch/master/graph/badge.svg)](https://codecov.io/gh/szrnka-peter/mini-http-server)

## Used technologies
- Java 7
- Maven
- Jetty webserver

## Features
- configurable
- small
- portable (no installation required)

## Install & configuration
- Download the JAR file
- Create a keystore file (for HTTPS mode)
- Create a batch file to run it, or you can download it from here.
- Download the sample config.properties file
- Open the config.properties file and customize the parameters.

## Usage
- Start the batch file or run it from command line with
```
java -jar mini-https-server.jar
```

## Settings

| Key | Description |
| ------------- | ------------- |
| server.type |[HTTP,HTTPS] |
| server.port | The port type, where the server'll run |
| www.dir | WWW directory |
| keystore.location | Keystore file location |
| keystore.password | Base64 encoded keystore password |
| truststore.location | Truststore file location (same as keystore file) |
| truststore.password | Base64 encoded truststore password |
| password.encrypttype | Encryption type (currently only base64 allowed) |
