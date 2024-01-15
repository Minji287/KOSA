
function showImagePreview(input) {
    var preview = document.getElementById('preview');
    if (input.files && input.files[0]) {
        var reader = new FileReader();
        reader.onload = function(e) {
            preview.src = e.target.result;
            preview.style.display = 'block'; // 이미지가 선택되면 표시
        };
        reader.readAsDataURL(input.files[0]);
    } else {
        preview.style.display = 'none'; // 이미지 선택이 취소되면 숨김
    }
}

    // 생일 선택 달력
    flatpickr("#petBirthPicker", {
        dateFormat: "Y-m-d", // 원하는 날짜 형식으로 설정 (예: 년-월-일)
        enableTime: false, // 시간을 사용하지 않는 경우 false로 설정
    });
