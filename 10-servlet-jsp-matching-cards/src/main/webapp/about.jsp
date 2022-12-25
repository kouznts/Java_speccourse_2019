<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<!DOCTYPE html PUBLIC
"-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd" >

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta http-equiv="Content-Type"
          content="text/html; charset=ISO-8859-1">
    <style type="">
        <%@include file="/style.css" %>
    </style>
    <title>Справка</title>
</head>
<body>
<div class="canvas-like-div">
    <div class="about-div">
        <h1>Справка</h1>

        <p class="about-p">ИНФОРМАЦИЯ О СИСТЕМЕ</p>
        <p class="about-p">Распределённое клиент-серверное приложение,
            использующее технологию Servlets для распределённой объектной обработки.</p>
        <p class="about-p">Приложение реализует игру ПАЗЗЛ со следующими правилами:</p>
        <ul>
            <li class="about-li">в игре присутствует один игрок,
                который попарно открывает ячейки;
            </li>
            <li class="about-li">игрового поля, если ячейки имеют одинаковый цвет,
                то они освобождаются,
                если ячейки разные, то они закрываются обратно;
            </li>
            <li class="about-li">размер поля можно задавать,
                количество ячеек на поле должно быть чётным;
            </li>
            <li class="about-li">игра заканчивается, когда поле полностью освобождено.</li>
        </ul>

        <form action="${pageContext.request.contextPath}/">
            <div class="centered">
                <input type="submit"
                       value="Вернуться в меню"
                       class="menu-button"/>
            </div>
        </form>
    </div>
</div>
</body>
</html>