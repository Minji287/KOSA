function toggleInputs() {
      var havePetTrue = document.getElementById("havePetTrue");
      var havePetFalse = document.getElementById("havePetFalse");
      var inputFields = document.querySelectorAll(".disable_toggle");

      for (var i = 0; i < inputFields.length; i++) {
        inputFields[i].disabled = havePetFalse.checked;
      }
    }

document.getElementById("havePetTrue").addEventListener("change", toggleInputs);
document.getElementById("havePetFalse").addEventListener("change", toggleInputs);

toggleInputs();