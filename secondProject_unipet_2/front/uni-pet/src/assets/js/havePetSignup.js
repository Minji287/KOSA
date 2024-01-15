export default function useHavePet() {
  window.scrollTo(0, 0);
  function toggleInputs() {
    const havePetFalse = document.getElementById("havePetFalse");
    const inputFields = document.querySelectorAll(".disable_toggle");

    for (let i = 0; i < inputFields.length; i++) {
      inputFields[i].disabled = havePetFalse.checked;
    }
  }

  document
    .getElementById("havePetTrue")
    .addEventListener("change", toggleInputs);
  document
    .getElementById("havePetFalse")
    .addEventListener("change", toggleInputs);

  toggleInputs();
}
