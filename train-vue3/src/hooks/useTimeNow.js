import { ref } from "vue";

/**
 * @description 获取本地时间
 */
export default function useTimeNow() {
  const year = ref(0); // 年份
  const month = ref(0); // 月份
  const day = ref(0); // 天数
  const nowTime = ref(""); // 当前时间
  const nextDay = ref(0);  // 第二天天数
  const nextTime = ref(""); // 第二天时间

  // 更新时间
  function updateTime() {
    const date = new Date();
    year.value = date.getFullYear();
    month.value = date.getMonth() + 1;
    day.value = date.getDate();

    nowTime.value = `${year.value}-${month.value < 10 ? '0' + month.value : month.value}-${day.value < 10 ? '0' + day.value : day.value}`;
    nextDay.value = day.value + 1;
    nextTime.value = `${year.value}-${month.value < 10 ? '0' + month.value : month.value}-${nextDay.value < 10 ? '0' + nextDay.value : nextDay.value}`;
  }

  updateTime();

  return { year, month, day, nowTime, nextTime };
}

