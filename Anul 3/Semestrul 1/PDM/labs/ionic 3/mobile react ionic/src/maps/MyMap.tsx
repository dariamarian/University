import { GoogleMap } from '@capacitor/google-maps';
import { useEffect, useRef } from 'react';
import { mapsApiKey } from './mapsApiKey';


interface MyMapProps {
  lat: number;
  lng: number;
  /*onMapClick: () => void;
  onMarkerClick: (e: any) => void;*/
  onUpdateLocation?: (lat: number, lng: number) => void
  size?: { width: string; height: string };
}

const MyMap: React.FC<MyMapProps> = ({ lat, lng,  onUpdateLocation, size = { width: '300px', height: '300px' }  }) => {
  const mapRef = useRef<HTMLDivElement | null>(null);
  useEffect(myMapEffect, [mapRef.current])

  return <div ref={mapRef} style={{ ...size }} />;

  function myMapEffect() {
    let canceled = false;
    let googleMap: GoogleMap | null = null;
    createMap();
    return () => {
      canceled = true;
      googleMap?.removeAllMapListeners();
    }

    async function createMap() {
      if (!mapRef.current) {
        return;
      }
      googleMap = await GoogleMap.create({
        id: 'my-cool-map',
        element: mapRef.current,
        apiKey: mapsApiKey,
        config: {
          center: { lat, lng },
          zoom: 8
        }
      })
      console.log('gm created');
      const myLocationMarkerId = await googleMap.addMarker({ coordinate: { lat, lng }, title: 'My location2' });

      await googleMap.setOnMapClickListener(({ latitude, longitude }) => {
        if (googleMap) {
          googleMap.removeMarker(myLocationMarkerId);
          googleMap.addMarker({ coordinate: { lat: latitude, lng: longitude }, title: 'My location2' });
          if (onUpdateLocation) {
            onUpdateLocation(latitude, longitude);
          }
        }
      });
    }
  }

}

export default MyMap;
