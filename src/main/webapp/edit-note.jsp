<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="vi">

<head>

    <meta charset="UTF-8">

    <title>iNotes - Sửa ghi chú</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style.css">

</head>

<body>

<div class="container form-container">

    <h1>Sửa ghi chú</h1>

    <form method="post"
          action="${pageContext.request.contextPath}/notes/edit">

        <input type="hidden"
               name="id"
               value="${note.id}">

        <label>Tiêu đề</label>

        <input type="text"
               name="title"
               value="${note.title}"
               required
               maxlength="255">

        <label>Phân loại</label>

        <select name="typeId" required>

            <c:forEach var="type" items="${types}">

                <option value="${type.id}"
                    <c:if test="${type.id == note.typeId}">
                        selected
                    </c:if>>
                    ${type.name}
                </option>

            </c:forEach>

        </select>

        <label>Nội dung</label>

        <textarea name="content"
                  rows="10">${note.content}</textarea>

        <div class="actions">

            <button type="submit">
                Cập nhật
            </button>

            <a class="button secondary"
               href="${pageContext.request.contextPath}/notes">
                Hủy bỏ
            </a>

        </div>

    </form>

</div>

</body>
</html>
