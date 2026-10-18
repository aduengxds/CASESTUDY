<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="vi">

<head>

    <meta charset="UTF-8">

    <title>Xem ghi chú - iNotes</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style.css">

</head>

<body>

<div class="container small">

    <div class="header">

        <h1>${note.title}</h1>

        <p>
            Phân loại:
            <strong>${note.typeName}</strong>
        </p>

    </div>

    <div class="card">

        <div class="note-content">
            ${note.content}
        </div>

        <div class="actions">

            <a href="${pageContext.request.contextPath}/notes/edit?id=${note.id}"
               class="btn">
                Sửa
            </a>

            <a href="${pageContext.request.contextPath}/notes"
               class="btn">
                Quay lại
            </a>

        </div>

    </div>

</div>

</body>
</html>
