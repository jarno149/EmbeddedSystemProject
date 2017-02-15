import {Component, OnInit, ViewChild} from '@angular/core';
import { jqxGaugeComponent } from 'jqwidgets-framework/jqwidgets-ts/angular_jqxgauge';

@Component({
  selector: 'app-gauge',
  template: `
        <app-mqtt (messageToForward)="mqttmessage($event)"></app-mqtt>
        <jqxGauge  #gaugeReference (onValueChanging)="gaugeOnValueChanging($event)"
            [ranges]='ranges' [ticksMinor]='ticksMinor' [ticksMajor]='ticksMajor'
            [value]='0' [colorScheme]='"scheme05"' [animationDuration]='1200'>
        </jqxGauge>
        <div id="gaugeValue"
            style="position: absolute; top: 235px; left: 132px; font-family: Sans-Serif; text-align: center; font-size: 17px; width: 70px;">
        </div>
`


})

export class GaugeComponent {
  @ViewChild('gaugeReference') myGauge: jqxGaugeComponent;

  mqttmessage(messu: string) { //tassa kuunnellaa eventtia mqtt komponentilta
    this.myGauge.value(+messu);

  }





  ngAfterViewInit(): void
  {
    setTimeout(() =>
    {
      this.myGauge.max(40);
      this.myGauge.min(-40);


      // koko this.myGauge.radius(50);
      var asetukset =  	{ distance: '38%', position: 'none', interval: 5, offset: [0, -10], visible: true, formatValue: function (value) { return value; }};

      this.myGauge.labels(asetukset);



    });
  }
  gaugeOnValueChanging(event: any): void
  {
    let gaugeValueDom = <HTMLElement>document.getElementById('gaugeValue');
    gaugeValueDom.innerText = Math.round(event.args.value) +"C "+" " + '\u00B0';


  }


  ticksMinor: any = { interval: 1, size: '5%' };
  ticksMajor: any = { interval: 5, size: '9%' };
  ranges: any[] =
    [
      { startValue: -40, endValue: -20, style: { fill: '#0067c8', stroke: '#0067c8' }, endWidth: 16, startWidth: 16},
      { startValue: -20, endValue: 0, style: { fill: '#00b2ff', stroke: '#00b2ff' }, endWidth: 16, startWidth: 16 },
      { startValue: 0, endValue: 20, style: { fill: '#ff8000', stroke: '#ff8000' }, endWidth: 16, startWidth: 16 },
      { startValue: 20, endValue: 40, style: { fill: '#e02629', stroke: '#e02629' }, endWidth: 16, startWidth: 16 }
    ];




}
