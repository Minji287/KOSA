
// 값 받아오기
document.addEventListener("DOMContentLoaded", function() {
    var currentPage = 1;
    var limit = 10;

    function loadPageData(currentPage) {
        const xhr = new XMLHttpRequest();
        xhr.onload = function() {
            if (xhr.status === 200) {
                displayPageData(currentPage);
            }
        };

        xhr.open('GET', '/items?limit=' + limit + '&page=' + currentPage, true);
        xhr.send();
    }
    window.loadPageData = loadPageData;

    function displayPageData(currentPage) {
        var limit = 10;
        // 페이지 번호가 변경될 때만 데이터를 가져와서 업데이트
        const tableData = {
            method: "POST"
        };

        fetch('/items?limit=' + limit + '&page=' + currentPage, tableData)
            .then(function(response) {
                return response.json();
            })
            .then(function(jsonData) {
                var totalCount = jsonData.count;
                var itemsDiv = document.getElementById('items');

                if (totalCount === 0) {
                    itemsDiv.innerHTML = '<div class="no_tb">등록된 글이 없습니다.</div>';
                } else {
                    var totalPages = Math.ceil(totalCount / limit);
                    var writings = jsonData.writings;
                    console.log(writings);
                    // 글 목록을 생성하고 표시하는 코드
                    itemsDiv.innerHTML = '<table class="table table-hover">' +
                        '<thead class="table-light">' +
                        '<tr>' +
                        '<th scope="col">번호</th>' +
                        '<th scope="col">제목</th>' +
                        '<th scope="col">날짜</th>' +
                        '</tr>' +
                        '</thead>' +
                        '<tbody class="table-group-divider">';

                    for (var i = 0; i < writings.length; i++) {
                        var my_writings = writings[i];
                        var board_no = my_writings.board_no;
                        var title = my_writings.title;
                        var posting_date = my_writings.posting_date;
                        var row_num = my_writings.num;
                        loadItems(itemsDiv, board_no, title, posting_date, row_num);
                    }

                    itemsDiv.innerHTML += '</tbody></table>';
                    loadPagination(currentPage, totalPages);
                }
            })
            .catch(function(error) {
                console.log(error);
            });

        }

// 페이지당 게시글 가져오기
    function loadItems(itemsDiv, board_no, title, posting_date, row_num) {
        var itemHTML =
            '<tr>' +
            '<input type="hidden" value="' + board_no + '">' +
            '<td class="tb_num">' + row_num + '</td>' +
            '<td class="tb_title">' +
            '<a href="/board/content?board_no=' + board_no + '">' + title + '</a>' +
            '</td>' +
            '<td>' + posting_date + '</td>' +
            '</tr>';

        var tableBody = itemsDiv.querySelector('tbody');
        tableBody.innerHTML += itemHTML;
    }

    function loadPagination(currentPage, totalPages) {
        var paginationDiv = document.getElementById('pagination');
        var paginationHTML = '<ul class="pagination">';
        var previousPage = currentPage > 1 ? currentPage - 1 : 1;
        var nextPage = currentPage < totalPages ? currentPage + 1 : totalPages;

        // 이전 페이지로 이동
        paginationHTML += '<li class="page-item">' +
                    '<a class="page-link" href="javascript:void(0);" onclick="loadPageData(' + previousPage + ')">이전</a></li>';

        // 페이지 번호
        for (var i = 1; i <= totalPages; i++) {
            if (i === currentPage) {
                paginationHTML += '<li class="page-item active">' +
                    '<a class="page-link" href="javascript:void(0);">' + i + '</a></li>';
            } else {
                paginationHTML += '<li class="page-item">' +
                    '<a class="page-link" href="javascript:void(0);" onclick="loadPageData(' + i + ')">' + i + '</a></li>';
            }
        }

        // 다음 페이지로 이동
        paginationHTML += '<li class="page-item">' +
                    '<a class="page-link" href="javascript:void(0);" onclick="loadPageData(' + nextPage + ')">다음</a></li>';

        paginationHTML += '</ul>';

        paginationDiv.innerHTML = paginationHTML;
    }

    loadPageData(currentPage);

});