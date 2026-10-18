<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="vi">

<head>

    <meta charset="UTF-8">

    <title>iNotes - Thêm ghi chú</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style.css">

</head>

<body>

<div class="container form-container">

    <h1>Thêm mới ghi chú</h1>

    <form method="post"
          action="${pageContext.request.contextPath}/notes/add">

        <label>Tiêu đề</label>

        <input type="text"
               name="title"
               required
               maxlength="255"
               placeholder="Nhập tiêu đề">

        <label>Phân loại</label>

        <select name="typeId" required>

            <c:forEach var="type" items="${types}">

                <option value="${type.id}">
                    ${type.name}
                </option>

            </c:forEach>

        </select>

        <label>Nội dung</label>

        <textarea name="content"
                  rows="10"
                  placeholder="Nhập nội dung ghi chú"></textarea>

        <div class="actions">

            <button type="submit">
                Lưu ghi chú
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
