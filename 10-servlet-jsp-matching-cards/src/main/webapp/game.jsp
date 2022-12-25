<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<!DOCTYPE html PUBLIC
"-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd" >

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8"
          content="text/html"
          http-equiv="Content-Type">
    <style type="">
        <%@include file="/style.css" %>
    </style>
    <title>Игра</title>
</head>
<body>

<div class="game-status">
    <span class="moves-counter">Ходы: </span>
    <span class="moves-counter" id="movesCounter"></span>
    <span class="time-counter">Время: </span>
    <span class="time-counter" id="timeCounter">0 мин. 0 сек.</span>
</div>

<table class="game-board" id="gameBoard">
    <tbody class="game-grid">
    </tbody>
</table>

<script type="">
    let cardElements = document.getElementsByClassName('game-card');
    let cardElementsArray = [...cardElements];

    let imgElements = document.getElementsByClassName('game-card-img');
    let imgElementsArray = [...imgElements];

    let movesCounter = document.getElementById('movesCounter');
    let timeCounter = document.getElementById('timeCounter');

    let openedCards = [];
    let matchedCards = [];

    let moves;
    let seconds = 0;
    let interval;

    function createGameBoard() {
        let colsNum = <%= request.getAttribute("horizontal") %>;
        let rowsNum = <%= request.getAttribute("vertical") %>;

        const tableRef = document.getElementById('gameBoard').getElementsByTagName('tbody')[0];

        let imageNum = 0;
        for (let rowIndex = 0; rowIndex < rowsNum; rowIndex++) {
            for (let colIndex = 0; colIndex < colsNum; colIndex++) {
                let newRow = tableRef.insertRow();
                newRow.className = "game-grid-row";

                let newCell = newRow.insertCell(colIndex);
                newCell.className = "game-card";

                let img = document.createElement('img');
                img.className = "game-card-img";
                imageNum++;
                img.src = "images/" + imageNum + ".jpg";

                newCell.append(img);
            }
        }
    }

    function shuffle(array) {
        let currIndex = array.length;

        let randomIndex;
        let tempVal;
        while (currIndex !== 0) {
            randomIndex = Math.floor(Math.random() * currIndex);
            currIndex -= 1;

            tempVal = array[currIndex];
            array[currIndex] = array[randomIndex];
            array[randomIndex] = tempVal;
        }

        return array;
    }

    function startGame() {
        shuffleImages();
        addDisplayCardListener();
        displayAllCards();
        resetMoves();
        resetTimer();
    }

    function shuffleImages() {
        let shuffledImages = shuffle(imgElementsArray);

        for (let i = 0; i < shuffledImages.length; i++) {
            cardElements[i].innerHTML = "";

            cardElements[i].appendChild(shuffledImages[i]);
            cardElements[i].type = `${shuffledImages[i].alt}`;

            cardElements[i].classList.remove("show", "open", "match", "disabled");
            cardElements[i].children[0].classList.remove("show-img");
        }
    }

    function addDisplayCardListener() {
        for (let i = 0; i < cardElementsArray.length; i++) {
            cardElementsArray[i].addEventListener("click", displayCard)
        }
    }

    function displayAllCards() {
        for (let i = 0; i < cardElements.length; i++) {
            cardElements[i].children[0].classList.add("show-img")
        }

        setTimeout(function () {
            for (let i = 0; i < cardElements.length; i++) {
                cardElements[i].children[0].classList.remove("show-img")
            }
        }, 1000)
    }

    function resetMoves() {
        moves = 0;
        movesCounter.innerText = moves;
    }

    function resetTimer() {
        timeCounter.innerHTML = '0 мин. 0 сек.';
        clearInterval(interval);
    }

    function displayCard() {
        this.children[0].classList.toggle('show-img');
        this.classList.toggle("open");
        this.classList.toggle("show");
        this.classList.toggle("disabled");

        openCard(this);
    }

    function openCard(card) {
        openedCards.push(card);

        let len = openedCards.length;
        if (len === 2) {
            countMoves();

            if (openedCards[0].type === openedCards[1].type) {
                matched();
            } else {
                unmatched();
            }
        }
    }

    function countMoves() {
        moves++;
        movesCounter.innerHTML = moves;

        if (moves === 1) {
            seconds = 0;
            startTimer();
        }
    }

    function startTimer() {
        interval = setInterval(function () {
            timeCounter.innerHTML = `${seconds} сек.`;
            seconds++;
        }, 1000)
    }

    function matched() {
        openedCards[0].classList.add("match");
        openedCards[1].classList.add("match");

        openedCards[0].classList.remove("show", "open");
        openedCards[1].classList.remove("show", "open");

        matchedCards.push(openedCards[0]);
        matchedCards.push(openedCards[1]);

        openedCards = [];

        if (matchedCards.length === 16) {
            endGame();
        }
    }

    function unmatched() {
        openedCards[0].classList.add("unmatched");
        openedCards[1].classList.add("unmatched");
        disable();

        setTimeout(function () {
            openedCards[0].classList.remove("show", "open", "unmatched");
            openedCards[1].classList.remove("show", "open", "unmatched");

            openedCards[0].children[0].classList.remove('show-img');
            openedCards[1].children[0].classList.remove('show-img');
            enable();

            openedCards = [];
        }, 1100)
    }

    function enable() {
        cardElementsArray.filter((card) => {
            card.classList.remove('disabled');

            for (let i = 0; i < matchedCards.length; i++) {
                matchedCards[i].classList.add('disabled');
            }
        })
    }

    function disable() {
        cardElementsArray.filter((card) => {
            card.classList.add('disabled');
        })
    }

    function endGame() {
        let form = document.createElement('form');
        form.action = '${pageContext.request.contextPath}/gameover';
        form.method = 'POST';
        form.innerHTML = '<input id="score" type="hidden" name="score">';
        document.body.append(form);
        document.getElementById("score").value = seconds;
        form.submit();
    }

    window.onload = function () {
        setTimeout(function () {
            startGame()
        }, 1200);
    };
</script>

</body>
</html>