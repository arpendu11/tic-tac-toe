# tic-tac-toe
A multi-player NXN board size tic-tac-toe game that can be played with another human or computer. 

This project is implemented in Spring Boot Framework.

## Explanation
This game is designed in a way that it can be easily hosted in a cloud or any highly available ecosystem. It basically exposes certain rest APIs.
All the APIs have been documented as part of Swagger and that can be accessed through http://localhost:8080/swagger-ui.html.
The API structure looks something like below:

| url                | method   | consumes                | produces   | description                           | response content
|:------------------:|:--------:|:-----------------------:|:----------:|:-------------------------------------:|:------------------------------------------------------------------------------------------------------:
| /game/new          | POST   | JSON: userName,type       | JSON     | Creates a new game with given player| (success, gameId, message, next step)
| /game/status       | GET    | param: userName           | JSON     | Current State of the game           | (gameId, state(started/Waiting for next move/Ended), message, Print board structure, next step)
| /game/setBoard     | POST   | JSON: userName,size,marker| JSON     | Set a board to start play           | (success, gameId, message, Display board structure, next step)
| /player/turn       | GET    | param: userName           | JSON     | Whose turn is it?                   | (message, gameId, Print board structure, next step)
| /player/move       | POST   | JSON: userName,row,column | JSON     | Play next move                      | (success, gameId, state, message(is valid/won), next step, print board structure)
| /game/exit         | POST   | JSON: userName            | JSON     | Exits the present session of game   | (success, gameId, message, next step)


## Running the application

You can run your application using:
```
./mvnw clean install
```

## Packaging and running the application

The application can be packaged using `./mvnw clean package`.
It produces the `tic-tac-toe-0.0.1-SNAPSHOT.jar` file in the `/target` directory.

The application is now runnable using `java -jar target/tic-tac-toe-0.0.1-SNAPSHOT.jar`.
