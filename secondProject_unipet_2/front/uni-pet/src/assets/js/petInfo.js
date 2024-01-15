import HOSPITAL_IMAGE_URL from "../images/petInfo/hospital_photo.jpg";
import TRAIL_IMAGE_URL from "../images/petInfo/trail_img.jpg";
export default function usePetInfo() {
  const hospitalDataListWarpper = document.querySelector(
    ".hospital_dataList_warpper"
  );
  const trailDataListWarpper = document.querySelector(
    ".trail_dataList_warpper"
  );
  const titleName = document.querySelector(
    ".hospital_dataList_head_text"
  ).innerText;

  const API_KEY = import.meta.env.VITE_API_KEY;
  const seoulGilWalkCourse = "SeoulGilWalkCourse";
  const animalHospital = "LOCALDATA_020301";

  let isScrolling = false;
  let warpper_Y = 0;
  let startGetData = 1;
  let endGetData = 15;
  let trailendGetData = 150;

  const getData = async (startGetData, endGetData, target) => {
    const URL = `http://openapi.seoul.go.kr:8088/${API_KEY}/json/${target}/${startGetData}/${endGetData}/`;

    try {
      const response = await fetch(URL);
      if (!response.ok) throw new Error("Network response was not ok");

      const data = await response.json();
      if (target == "LOCALDATA_020301")
        return hospFilter(data.LOCALDATA_020301);
      else if (target == "SeoulGilWalkCourse")
        return tarilFilter(data.SeoulGilWalkCourse);
    } catch (error) {
      console.error("Fetch error:", error);
      throw error;
    }
  };

  window.addEventListener("load", async () => {
    try {
      if (titleName == "동물 병원 리스트") {
        let data = await getData(startGetData, endGetData, animalHospital);
        addDataToList(data);
      } else {
        let data = await getData(
          startGetData,
          trailendGetData,
          seoulGilWalkCourse
        );
        addTrailDataToList(data);
      }
    } catch (error) {
      console.error("Error:", error);
    }
  });

  window.addEventListener("scroll", async () => {
    getContainerY();

    if (warpper_Y <= window.scrollY) {
      if (!isScrolling) {
        isScrolling = true;
        try {
          startGetData = endGetData + 1;
          endGetData += 10;
          if (titleName == "동물 병원 리스트") {
            let data = await getData(startGetData, endGetData, animalHospital);
            addDataToList(data);
          } else {
            let data = await getData(
              startGetData,
              trailendGetData,
              seoulGilWalkCourse
            );
            addTrailDataToList(data);
          }
          setTimeout(() => {
            isScrolling = false;
          }, 500);
          getContainerY();
        } catch (error) {
          console.error("Error:", error);
        }
      }
    }
  });

  window.addEventListener("resize", () => {
    getContainerY();
  });

  const getContainerY = () => {
    if (titleName == "동물 병원 리스트") {
      warpper_Y =
        hospitalDataListWarpper.getBoundingClientRect().bottom +
        window.pageYOffset -
        800;
    } else {
      warpper_Y =
        trailDataListWarpper.getBoundingClientRect().bottom +
        window.pageYOffset -
        800;
    }
  };

  const addDataToList = (data) => {
    data.forEach((el) => {
      hospitalDataListWarpper.innerHTML += `
          <div class="hospital_dataList_card">
            <img src="${HOSPITAL_IMAGE_URL}" alt="" />
            <div class="hospital_dataList_text">
              <p class="hospital_dataList_maintext">${el.hospitalName}</p>
              <p>${el.phoneNumber}</p>
              <p>${el.detailedAddress}</p>
              <p class="hospital_dataList_btn">${el.isOpen}</p>
            </div>
          </div>`;
    });
  };

  const addTrailDataToList = (data) => {
    data.forEach((el) => {
      trailDataListWarpper.innerHTML += `<div class="trail_dataList_card">
    <div class="trail_dataList_img"></div>
    <div class="trail_text_container">
      <p class="trail_title_text">${el.name}</p>
      <p>소요시간 : ${el.leadTime}</p>
      <p>거리 : ${el.distance}</p>
      <p class="trail_address">${el.address}</p>
      <p class="trail_address">코스 : ${el.course}</p>
    </div>
  </div>`;
    });
  };

  const hospFilter = (dataList) => {
    if (dataList.RESULT.CODE === "INFO-000") {
      const filtedData = dataList.row
        .map((el) => {
          return {
            address: el.SITEWHLADDR,
            detailedAddress: el.RDNWHLADDR,
            hospitalName: el.BPLCNM,
            phoneNumber: el.SITETEL,
            isOpen: el.TRDSTATENM,
          };
        })
        .filter((el) => {
          return el.isOpen === "영업/정상";
        });
      return filtedData;
    } else {
      return [];
    }
  };

  const tarilFilter = (dataList) => {
    if (dataList.RESULT.CODE === "INFO-000") {
      let courseName = "";
      const filtedData = dataList.row
        .filter((el) => {
          if (courseName == el.COURSE_NAME) return false;
          else {
            courseName = el.COURSE_NAME;
            return true;
          }
        })
        .map((el) => {
          return {
            name: el.COURSE_CATEGORY_NM + `/ ${el.COURSE_NAME}`,
            leadTime: el.LEAD_TIME,
            distance: el.DISTANCE,
            address: el.TRAFFIC_INFO,
            course: el.DETAIL_COURSE,
          };
        });
      return filtedData;
    } else {
      return [];
    }
  };
}
