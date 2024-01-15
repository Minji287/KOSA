const alertBtn = document.querySelector(".alert_btn");
const alertContainer = document.querySelector(".alert");
const blackBg = document.querySelector(".black_bg");

if(alertBtn !=null && blackBg!= null){
alertBtn.addEventListener("click", () => {
  blackBg.classList.add("display_none");
  alertContainer.classList.add("display_none");
});

blackBg.addEventListener("click", () => {
  blackBg.classList.add("display_none");
  alertContainer.classList.add("display_none");
});
}