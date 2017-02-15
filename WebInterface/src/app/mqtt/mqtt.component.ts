import {Component, OnInit, Output, EventEmitter} from '@angular/core';
import {Paho} from "ng2-mqtt/mqttws31";

@Component({
  selector: 'app-mqtt',
  templateUrl: './mqtt.component.html',
  styleUrls: ['./mqtt.component.css']
})
export class MqttComponent implements OnInit {

  @Output() messageToForward: EventEmitter<string> = new EventEmitter<string>();

  private _client: Paho.MQTT.Client;




  public status = '';
  private server = "13.69.75.14";
  private port = Number(1884);
  private clientid = "client"+Math.random();
  private topic = "sensors/temperatures";
  public mqttMessage = "";
  private mqttTopic = "";

  ngOnInit(){
    //this._client = new Paho.MQTT.Client("test.mosquitto.org", 8080, "clientIdsss");

  }

  public constructor() {

    this._client = new Paho.MQTT.Client(this.server, this.port,"/", this.clientid);

    this._client.onConnectionLost = (responseObject: Object) => {
      console.log('Connection lost.');
      this.status = 'Disconnected';
    };

    this._client.onMessageArrived = (message: Paho.MQTT.Message) => {
      console.log('Message arrived.');
      console.log(message.payloadString);
      var json = message.payloadString,
        obj = JSON.parse(json);
      console.log(obj["value"]);
      this.mqttMessage = message.payloadString;
      this.mqttTopic = message.destinationName;
      this.messageToForward.next(obj["value"]);

    };

    this._client.connect({ onSuccess: this.onConnected.bind(this)
    });
  }

  private onConnected():void {
    console.log('Connected to broker.');
    console.log('new user connected: '+this.clientid)
    this.status = 'Connected';
    this._client.subscribe(this.topic,{qos:2})
  }

}
