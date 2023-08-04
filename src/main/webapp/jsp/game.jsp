<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Game - Guessing Game</title>
</head>
<body>
    <h1>Guessing Game</h1>
    <h2>Game</h2>
    <a href="/guessinggame/home">Back to home</a>
    <h3>Player: ${name} | Lowest guess count: ${guessCount}</h3>
    <h4>Enter a number from 1 to 1000 to guess the secret number:</h4>
    <form action="/guessinggame/game" method="POST">
        <input type="number" min="1" max="1000" name="guessNum"
            onkeydown="if(event.key==='.'){event.preventDefault();}"
            oninput="if(value<1) value=1; if(value>1000) value=1000;"
            required />
        <button ${win ? "disabled" : ""}>Guess</button>
        <h5>${message}</h5>
    </form>
    <form action="/guessinggame/game" method="GET">
        <c:if test="${win}">
            <button style="color: green;">Play again</button>
        </c:if>
    </form>
</body>
</html>