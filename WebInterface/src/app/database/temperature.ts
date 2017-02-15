export class Temperature {

  id: string;
  sensorname: string;
  value: number;
  timestamp: number;



  constructor(id: string, sensorname: string, value: number, timestamp: number) {
    this.id = id;
    this.sensorname = sensorname;
    this.value = value;
    this.timestamp = timestamp;
  }

}
