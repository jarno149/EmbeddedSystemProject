import {Component, OnInit, Injectable, Output, EventEmitter} from '@angular/core';
import {Paho} from 'ng2-mqtt/mqttws31';



@Injectable()

@Component({
  selector:'app-mqtt',
  templateUrl:'./mqtt.component.html',




//  template: `<h1>Hello {{message}}</h1> <br/>
//  <app-gauge [gaugemessage]="test"></app-gauge>
//  `,


})


export class MqttComponent implements OnInit{

  @Output() messageToForward: EventEmitter<string> = new EventEmitter<string>();

  private _client: Paho.MQTT.Client;



  public status = '';
  private server = "test.mosquitto.org";
  private port = Number(8080);
  private clientid = "Testclient1212"
  private topic = "testtopic/1212"
  public mqttMessage = "";
  private mqttTopic = "";

  ngOnInit(){
    //this._client = new Paho.MQTT.Client("test.mosquitto.org", 8080, "clientIdsss");

  }

  public constructor() {

    this._client = new Paho.MQTT.Client(this.server, this.port, this.clientid);

    this._client.onConnectionLost = (responseObject: Object) => {
      console.log('Connection lost.');
      this.status = 'Disconnected';
    };

    this._client.onMessageArrived = (message: Paho.MQTT.Message) => {
      console.log('Message arrived.');
      console.log(message.payloadString);
      this.mqttMessage = message.payloadString;
      this.mqttTopic = message.destinationName;
      this.messageToForward.next(message.payloadString);

    };

    this._client.connect({ onSuccess: this.onConnected.bind(this)
    });
  }

  private onConnected():void {
    console.log('Connected to broker.');

    this.status = 'Connected';
    this._client.subscribe(this.topic,{qos:2})
  }





}
