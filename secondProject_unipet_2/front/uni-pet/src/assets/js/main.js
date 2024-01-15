export default function useMain() {
  window.scrollTo(0, 0);
  const mainTextBgbox = document.querySelector(".main_text_bgbox");
  const serviceContainer = document.querySelector(".service_container");
  const textUpDownContainer = document.querySelector(".text_up_down_container");

  let yRelativeToPageTop = 0;
  let currentWidth = 75;
  const targetWidth = 100;
  const scrollIncrement = 1;
  const scrolldiscrement = 0.2;
  let lastScrollY = window.scrollY;

  window.addEventListener("load", () => {
    getBgY();
    updateWidth();
  });

  window.addEventListener("scroll", () => {
    updateWidth();
    if (getServiceContainerY() <= window.scrollY) {
      serviceContainer.classList.remove("hidden");
      textUpDownContainer.classList.remove("hidden");
    } else if (getServiceContainerY() > window.scrollY - 200) {
      serviceContainer.classList.add("hidden");
      textUpDownContainer.classList.add("hidden");
    }
  });

  window.addEventListener("resize", () => {
    getBgY();
    updateWidth();
  });

  const getBgY = () => {
    const rectY = mainTextBgbox.getBoundingClientRect().top;
    yRelativeToPageTop = rectY + window.scrollY - 500;
    return rectY + window.scrollY;
  };

  const getServiceContainerY = () => {
    const rectY = serviceContainer.getBoundingClientRect().top;
    return rectY + window.scrollY - 400;
  };

  const updateWidth = () => {
    const scrollY = window.scrollY;

    if (yRelativeToPageTop <= scrollY && scrollY > lastScrollY) {
      currentWidth += scrollIncrement;
    } else {
      currentWidth -= scrolldiscrement;
    }

    if (currentWidth > targetWidth) {
      currentWidth = targetWidth;
    } else if (currentWidth < 75) {
      currentWidth = 75;
    }

    mainTextBgbox.style.width = currentWidth + "%";
    lastScrollY = scrollY;
  };
}
