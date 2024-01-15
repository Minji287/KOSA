// header.js 파일

export default function useHeader() {
  const header = document.querySelector(".header");
  const headerMainFront = document.querySelectorAll(".menu_text");
  const dropDownContainer = document.querySelector(".drop_down_container");
  const dropDownMenuText = document.querySelectorAll(".drop_down_menu_text");
  const dropDownMenuContainers = document.querySelectorAll(
    ".drop_down_menu_container"
  );

  header.addEventListener("mouseover", () => {
    header.classList.add("background_white");
    dropDownContainer.id = "height_l";

    dropDownMenuText.forEach((el) => {
      el.classList.remove("display_none");
    });

    headerMainFront.forEach((el) => {
      el.classList.add("color_black");
    });
  });

  header.addEventListener("mouseout", () => {
    header.classList.remove("background_white");
    dropDownContainer.removeAttribute("id");

    dropDownMenuText.forEach((el) => {
      el.classList.add("display_none");
    });

    headerMainFront.forEach((el) => {
      el.classList.remove("color_black");
    });
  });

  let lastScrollTop = 0;
  window.onscroll = function () {
    if (window.pageYOffset > 300) {
      header.id = "height_none";
    } else {
      header.removeAttribute("id");
    }

    const currentScrollTop =
      window.pageYOffset || document.documentElement.scrollTop;

    if (currentScrollTop < lastScrollTop) {
      header.removeAttribute("id");
    }
    lastScrollTop = currentScrollTop;
  };

  window.addEventListener("DOMContentLoaded", () => {
    let resizeTimer;
    const delay = 400;
    clearTimeout(resizeTimer);
    resizeTimer = setTimeout(function () {
      dropDownMenuPosition();
    }, delay);
  });

  window.addEventListener("resize", () => {
    let resizeTimer;
    const delay = 400;
    clearTimeout(resizeTimer);
    resizeTimer = setTimeout(function () {
      dropDownMenuPosition();
    }, delay);
  });

  const dropDownMenuPosition = () => {
    let mainMenuArray = [
      headerMainFront[0].getBoundingClientRect().left,
      headerMainFront[1].getBoundingClientRect().left,
      headerMainFront[2].getBoundingClientRect().left,
    ];

    dropDownMenuContainers.forEach((el, i) => {
      if (i === 0) el.style.left = mainMenuArray[i] - 8 + "px";
      else el.style.left = mainMenuArray[i] - 20 + "px";
    });
  };
}
