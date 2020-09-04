import { Component, AfterViewInit, ViewChild, ElementRef} from 
  '@angular/core';
import { ClinicService } from 'src/app/services/clinic.service';


@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.scss']
})
export class MapComponent implements AfterViewInit {

  @ViewChild('mapContainer') gmap: ElementRef;

  map: google.maps.Map;
  lat = 45.05167;
  lng = 19.83694;
  coordinates = new google.maps.LatLng(this.lat, this.lng);
  mapOptions: google.maps.MapOptions = {
    center: this.coordinates,
    zoom: 8,
  };
  // marker = new google.maps.Marker({
  //   position: this.coordinates,
  //   map: this.map,
  // });
  marker = [];

  constructor(private clincService: ClinicService) { }

  ngAfterViewInit() {
    this.mapInitializer();
    this.setupClinics();
  }

  mapInitializer() {
    this.map = new google.maps.Map(this.gmap.nativeElement, 
    this.mapOptions);
    // this.marker.setMap(this.map);
   }


   setupClinics(): void {
    this.clincService.getAllClinics().subscribe(data => {
      for(let i = 0;i < data.length;i++){
        this.coordinates = new google.maps.LatLng(Number(data[i].lat), Number(data[i].lon));
        this.marker[i] = new google.maps.Marker({
          position: this.coordinates,
          map: this.map,
          title: 'Naziv: ' + data[i].name
    });
      }
    })
  }
}
