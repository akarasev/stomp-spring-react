# Motivation

The goal of the repository is to learn how to enable interaction between backend and frontend applications
using STOMP messaging protocol over WebSockets.

# Backend app

Backend app was bootstrapped with [Spring Initializr](https://start.spring.io/).

To run the app you need Java 8 and use the command:

    $ ./mvnw spring-boot:run

# Frontend app

Frontend app was bootstrapped with [Create React App](https://github.com/facebook/create-react-app).

To run the app you need Node.js 8.12.0 and use the command:

    $ npm start

# Working with STOMP client

Working with [STOMP client library](https://www.npmjs.com/package/@stomp/stompjs) implemented into [src/App.js](./src/App.js)
