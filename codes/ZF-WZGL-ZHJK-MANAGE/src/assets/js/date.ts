const DT = {
  getDate(type: number, date = new Date(), split: string = "-") {
    let startTime: number = 0;
    let y: number = date.getFullYear(),
      m: number = date.getMonth() + 1,
      d: number = date.getDate();
    /*
      dt new Date
      dm 计算前移得月份
      dy 计算前移得年
      */
    let dt: Date, dm: number=0, dy: number=0;
    switch (type) {
      case 0: //最近一周
        startTime = date.getTime() - 7 * 24 * 60 * 60 * 1000;
        break;
      case 1: //最近一个月
        dy = m - 1 < 1 ? y - 1 : y;
        dm = m - 1 < 1 ? 12 : m - 1;
        break;
      case 2: //最近一个季度
        dy = m - 3 < 1 ? y - 1 : y;
        dm = m - 3 < 1 ? m + 9 : m - 3;
        break;
      case 3: //最近半年
        dy = m - 6 < 1 ? y - 1 : y;
        dm = m - 6 < 1 ? m + 6 : m - 6;
        break;
      case 4: //最近一年
        dy=y-1;
        dm=m;
        break;
    }
    if(type){
        dt = new Date(dy + "-" + dm + "-" + d);
        if (dt.getMonth() + 1 != dm) {
          //月份和期望月份不一样，说明上个月不存在那一天，取dt.getMonth() + 1得第一天
          startTime = new Date(dy + "-" + (dt.getMonth() + 1) + "-1").getTime();
        } else {
          startTime = new Date(dt).getTime();
        }
    }
    return [
      this.computedDate(startTime, split),
      this.computedDate(date.getTime(), split),
    ];
  },
  computedDate(time: number|string|Date=new Date(), split: string='-') {
    const date = new Date(time);
    const y: number = date.getFullYear(),
      m: number = date.getMonth() + 1,
      d: number = date.getDate();
    return y + split + (m > 9 ? m : "0" + m) + split + (d > 9 ? d : "0" + d);
  },
};
export default DT;
