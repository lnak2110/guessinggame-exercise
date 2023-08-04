<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Home - Guessing Game</title>
</head>
<body>
    <h1>Guessing Game</h1>
    <h2>Home</h2>
    <form action="/guessinggame/home" method="POST">
        <input type="text" name="name" placeholder="Enter your name"
            required />
        <button>Play</button>
    </form>
    <h3>Leaderboard</h3>
    <c:forEach items="${leaderboardFilter}" var="player"
        varStatus="loop">
        <h4>
            ${loop.index + 1}. Player <span style="color: blue;">${player.key}</span>
            won with <span style="color: green;">${player.value}</span>
            ${player.value > 1 ? "guesses" : "guess"}
        </h4>
    </c:forEach>
</body>
</html>