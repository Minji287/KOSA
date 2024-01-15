
document.addEventListener("DOMContentLoaded", function() {
    const cards = document.getElementsByClassName('sharecard');
    const loadMoreButton = document.getElementById('load-more');
    const itemsPerPage = 8;
    let currentItems = itemsPerPage;

    loadMoreButton.addEventListener('click', function() {
        for (let i = currentItems; i < currentItems + itemsPerPage; i++) {
            if (cards[i]) {
                cards[i].style.display = 'flex';
            }
        }
        currentItems += itemsPerPage;

        if (currentItems >= cards.length) {
            loadMoreButton.style.display = 'none';
        }
    });

    // 초기에는 처음부터 일부 항목만 보여주고 나머지 숨김
    for (let i = 0; i < currentItems; i++) {
        if (cards[i]) {
            cards[i].style.display = 'flex';
        }
    }

    // 모든 항목이 보여질 때 더보기 버튼 숨김
    if (cards.length <= itemsPerPage) {
        loadMoreButton.style.display = 'none';
    }
});