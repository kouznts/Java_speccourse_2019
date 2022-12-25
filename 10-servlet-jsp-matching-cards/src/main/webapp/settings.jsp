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
    <title>Настройки</title>
</head>
<body>
<div class="canvas-like-div">
    <div class="centered-group">

        <h1>Размер игрового поля</h1>

        <form action="${pageContext.request.contextPath}/game"
              method="POST">

            <label>
                По горизонтали
                <select name="sizes">
                    <option value="2">2</option>
                    <option value="4">4</option>
                    <option value="6">6</option>
                </select>
            </label>

            <label>
                По вертикали
                <select name="sizes">
                    <option value="2">2</option>
                    <option value="4">4</option>
                    <option value="6">6</option>
                </select>
            </label>

            <input type="submit"
                   value="Начать"
                   class="menu-button bottom-button"/>

        </form>
    </div>
</div>
</body>
</html>