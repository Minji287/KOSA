
        document.getElementById("confirm_change").addEventListener("submit", submitEventHandler);

        var email = document.getElementById('customEmailField');
        var select = document.getElementById('emailSelect');
        var c_input = document.getElementsByClassName('customInput')[0];
        var cancelButton = document.getElementById('cancelButton');

        function submitEventHandler(e) {
            e.preventDefault(); // 기본 제출 동작 방지
            var formData = new FormData(document.getElementById("confirm_change")); // FormData

            var selectOption = document.getElementById("emailSelect");
            selectOption = selectOption.options[selectOption.selectedIndex].value;
            console.log(selectOption == 'custom');

            formData.set("email", email.value);
            if (select.value === 'custom') {
                formData.set("domain", c_input.value); // custom 도메인 값을 FormData에 추가
            } else {
                formData.set("domain", select.value);
            }
            console.log("Email: " + formData.get("email"));
            console.log("Domain: " + formData.get("domain"));

            // Fetch API를 사용하여 서버로 POST 요청을 보냅니다.
            fetch("/changeemail", {
              method: "POST",
              // FormData를 요청 본문에 포함시킵니다.
              body: formData,
            })
              .then(function(response) {
          if (response.ok) {
            window.location.href = "/myprofile";
          } else {
            throw new Error("서버 응답이 실패했습니다.");
          }
        })
        .catch(function(error) {
          console.error(error);
        });

        }


        select.addEventListener('change', function (e) {
            if (select.value === 'custom') {
                select.style.display = 'none';  // select 숨기기
                c_input.style.display = 'block';
                cancelButton.style.display = 'block';
            }
        });

        cancelButton.addEventListener('click', function (e) {
            e.preventDefault();
            select.style.display = 'block';
            select.value = 'gmail.com'; // 기본 선택 옵션으로 변경 (원하는 기본값으로 설정)
            c_input.style.display = 'none';
            cancelButton.style.display = 'none';
        });
