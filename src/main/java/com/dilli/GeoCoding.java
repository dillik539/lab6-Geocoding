package com.dilli;
/*
This program calculates the elevation of user-entered
places
 */
import java.io.*;
import java.util.*;

import com.google.maps.ElevationApi;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.ElevationResult;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;

public class GeoCoding {

    public static void main(String[] args) throws  Exception {
        Scanner addr = new Scanner(System.in);

        String key = null;
        int name;
        try {
            //Create a file and read it
            BufferedReader reader = new BufferedReader(new FileReader("key.txt"));
            key = reader.readLine();
            System.out.println(key);
        }catch (IOException ioe){
            System.out.println("No key file found, or could not read key. Please verify key.txt present");
            System.exit(-1);
        }
        //ask user to enter address
        System.out.println("Please enter an address");

        GeoApiContext context = new GeoApiContext().setApiKey(key);
        String myaddr = addr.nextLine();
        GeocodingResult results[] = GeocodingApi.geocode(context, myaddr).await();
        for(int x = 0; x<results.length;x++){
            System.out.println(x+": "+results[x].formattedAddress);
        }
        name = addr.nextInt();
        LatLng r = (results[0].geometry.location);

        ElevationResult[] t = ElevationApi.getByPoints(context,r).await();

        if (results.length >= 1){
            ElevationResult Elevation = t[0];

            System.out.println(String.format("The elevation of " + results[name].formattedAddress +" above sea level is %.2f meters.", Elevation.elevation));
        }
    }
}
