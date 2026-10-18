<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="vi">

<head>
    <meta charset="UTF-8">
    <title>iNotes - Dashboard</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style.css">
</head>

<body>

<div class="container">

    <header>
        <h1>iNotes</h1>
        <p>Ứng dụng quản lý ghi chú</p>
    </header>

    <div class="toolbar">

        <form action="${pageContext.request.contextPath}/notes"
              method="get"
              class="search-form">

            <input type="text"
                   name="keyword"
                   value="${keyword}"
                   placeholder="Nhập từ khóa tìm kiếm...">

            <button type="submit">Tìm kiếm</button>

        </form>

        <a class="button"
           href="${pageContext.request.contextPath}/notes/add">
            + Thêm mới
        </a>

    </div>

    <table>

        <thead>
        <tr>
            <th>#</th>
            <th>Tiêu đề</th>
            <th>Phân loại</th>
            <th>Thao tác</th>
        </tr>
        </thead>

        <tbody>

        <c:choose>

            <c:when test="${empty notes}">

                <tr>
                    <td colspan="4" class="empty">
                        Không có ghi chú nào.
                    </td>
                </tr>

            </c:when>

            <c:otherwise>

                <c:forEach var="note" items="${notes}">

                    <tr>

                        <td>
                            ${note.id}
                        </td>

                        <td>
                            <strong>${note.title}</strong>
                        </td>

                        <td>
                            ${note.typeName}
                        </td>

                        <td>

                            <a href="${pageContext.request.contextPath}/notes/view?id=${note.id}">
                                Xem
                            </a>

                            |

                            <a href="${pageContext.request.contextPath}/notes/edit?id=${note.id}">
                                Sửa
                            </a>

                            |

                            <a class="delete"
                               href="${pageContext.request.contextPath}/notes/delete?id=${note.id}"
                               onclick="return confirm('Bạn có chắc muốn xóa ghi chú này?')">
                                Xóa
                            </a>

                        </td>

                    </tr>

                </c:forEach>

            </c:otherwise>

        </c:choose>

        </tbody>

    </table>

</div>

</body>
</html>
