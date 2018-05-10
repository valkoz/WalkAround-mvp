package ru.walkaround.walkaround;

import android.app.Activity;

import com.google.android.gms.maps.model.LatLng;

import java.util.Arrays;
import java.util.List;

import ru.walkaround.walkaround.model.Place;
import ru.walkaround.walkaround.model.PlaceType;
import ru.walkaround.walkaround.model.Route;

/**
 * This used only for debugging/demo TODO: Remove in prod
 */

public class StubDataUtils {

    private static int getPictureId(Activity activity, Integer id) {
        return activity.getResources().getIdentifier("stub_" + String.valueOf(id),
                "drawable",
                activity.getPackageName());
    }

    public static void generateDemoRoutes(Activity activity, List<Route> routes) {

        Integer id = 0;

        Place bolshoyTheatre = new Place("Большой театр",
                getPictureId(activity, id++),
                new LatLng(55.7601335, 37.6164599),
                PlaceType.ENTERTAINMENT);
        Place gorkiyPark = new Place("Парк Горького",
                getPictureId(activity, id++),
                new LatLng(55.7283678, 37.5991021),
                PlaceType.PARK);
        Place kremlin = new Place("Кремль",
                getPictureId(activity, id++),
                new LatLng(55.7520263, 37.6153107),
                PlaceType.MUSEUM);
        Place tsaritsuno = new Place("Царицыно Музей-заповедник ",
                getPictureId(activity, id++),
                new LatLng(55.6116904, 37.6839599),
                PlaceType.MUSEUM);
        Place tretiakovGallery = new Place("Третьяковская галерея",
                getPictureId(activity, id++),
                new LatLng(55.741392, 37.6186752),
                PlaceType.MUSEUM);
        Place historyMuseum = new Place("Исторический музей",
                getPictureId(activity, id++),
                new LatLng(55.755337, 37.6156587),
                PlaceType.MUSEUM);
        Place gmii = new Place("ГМИИ имени А.С. Пушкина",
                getPictureId(activity, id++),
                new LatLng(55.7473084, 37.6029238),
                PlaceType.MUSEUM);
        Place arbat = new Place("ул. Старый Арбат",
                getPictureId(activity, id++),
                new LatLng(55.7489474, 37.587484),
                PlaceType.ENTERTAINMENT);
        Place museon = new Place("Парк исскусств Музеон",
                getPictureId(activity, id++),
                new LatLng(55.7350683, 37.6052744),
                PlaceType.PARK);


        List<Place> firstPlaces = Arrays.asList(bolshoyTheatre, gorkiyPark, kremlin);
        List<Place> secondPlaces = Arrays.asList(tsaritsuno, tretiakovGallery, historyMuseum);
        List<Place> thirdPlaces = Arrays.asList(gmii, arbat, museon);

        routes.add(new Route("3:50", "10 км", "1000 руб", firstPlaces));
        routes.add(new Route("1:30", "5.6 км", "1500 руб", secondPlaces));
        routes.add(new Route("2:20", "6.2 км", "700 руб", thirdPlaces));

    }

}
