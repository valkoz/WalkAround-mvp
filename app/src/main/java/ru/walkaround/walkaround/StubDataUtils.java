package ru.walkaround.walkaround;

import android.app.Activity;

import com.google.android.gms.maps.model.LatLng;

import java.util.Arrays;
import java.util.List;

import ru.walkaround.walkaround.model.Place;
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
                "Театральная пл., 1",
                getPictureId(activity, id++),
                new LatLng(55.759725, 37.618823),
                R.drawable.ic_entertainment, 3.5f);
        Place gorkiyPark = new Place("Парк Горького",
                "ул. Крымский Вал, 9",
                getPictureId(activity, id++),
                new LatLng(55.7283678, 37.5991021),
                R.drawable.ic_park, 4.5f);
        Place kremlin = new Place("Кремль",
                "Москва",
                getPictureId(activity, id++),
                new LatLng(55.7520263, 37.6153107),
                R.drawable.ic_museum, 5f);
        Place tsaritsuno = new Place("Царицыно Музей-заповедник",
                "Дольская ул., 1",
                getPictureId(activity, id++),
                new LatLng(55.6116904, 37.6839599),
                R.drawable.ic_museum, 4f);
        Place tretiakovGallery = new Place("Третьяковская галерея",
                "Лаврушинский пер., 10",
                getPictureId(activity, id++),
                new LatLng(55.741392, 37.6186752),
                R.drawable.ic_museum, 4.5f);
        Place historyMuseum = new Place("Исторический музей",
                "Красная пл., 1",
                getPictureId(activity, id++),
                new LatLng(55.755481, 37.618128),
                R.drawable.ic_museum, 3.5f);
        Place gmii = new Place("ГМИИ имени А.С. Пушкина",
                "ул. Волхонка, 12",
                getPictureId(activity, id++),
                new LatLng(55.7473084, 37.6029238),
                R.drawable.ic_museum, 5f);
        Place arbat = new Place("ул. Старый Арбат",
                "ул. Арбат",
                getPictureId(activity, id++),
                new LatLng(55.7489474, 37.587484),
                R.drawable.ic_entertainment, 4.5f);
        Place museon = new Place("Парк исскусств Музеон",
                "Крымский вал, вл. 2",
                getPictureId(activity, id++),
                new LatLng(55.7350683, 37.6052744),
                R.drawable.ic_park, 4f);

        //55.756221, 37.617069
        //55.755507, 37.618059 Исторический музей
        //55.754260, 37.619586 Красная площадь
        //55.754267, 37.620739 ГУМ
        //55.755586, 37.619868
        //55.758993, 37.625046 Фарш
        //55.759348, 37.625468
        //55.7598306, 37.6257178 Музей детства
        Place redSquare = new Place("Красная площадь",
                "Москва",
                getPictureId(activity, id++),
                new LatLng(55.754267, 37.620739),
                R.drawable.ic_museum, 5f);
        Place gum = new Place("ГУМ",
                "Красная пл., 3",
                getPictureId(activity, id++),
                new LatLng(55.7548273, 37.6209261),
                R.drawable.ic_shop, 5f);
        Place farsh = new Place("Бургерная #FARШ",
                "Никольская ул., 12",
                getPictureId(activity, id++),
                new LatLng(55.758993, 37.625046),
                R.drawable.ic_food, 4.5f);
        Place detMir = new Place("Музей детства",
                "Театральный пр-д, 5",
                getPictureId(activity, id++),
                new LatLng(55.7598306, 37.6257178),
                R.drawable.ic_museum, 4.5f);

        // 55.756358, 37.615704 Охотный ряд
        // 55.757464, 37.616638 Starbucks
        // 55.759725, 37.618823 Большой театр
        // 55.760956, 37.619083 ЦУМ
        // 55.7603913,37.6151306 Вареничная
        Place varienik = new Place("Вареничная №1",
                "ул. Большая Дмитровка, 5/6, с. 5",
                getPictureId(activity, id++),
                new LatLng(55.7603913, 37.6151306),
                R.drawable.ic_food, 4.5f);
        Place tsum = new Place("ЦУМ",
                "Петровка ул., 2",
                getPictureId(activity, id++),
                new LatLng(55.760956, 37.619083),
                R.drawable.ic_shop, 4.5f);
        Place ohotniy = new Place("Охотный Ряд",
                "Манежная пл., 1, стр. 2",
                getPictureId(activity, id++),
                new LatLng(55.756358, 37.615704),
                R.drawable.ic_shop, 4f);
        Place starbucks = new Place("Starbucks",
                "ул. Охотный Ряд, 2",
                getPictureId(activity, id++),
                new LatLng(55.757464, 37.616638),
                R.drawable.ic_shop, 4.5f);
        //List<Place> firstPlaces = Arrays.asList(bolshoyTheatre, gorkiyPark, kremlin);
        List<Place> firstPlaces = Arrays.asList(historyMuseum, redSquare, gum, farsh, detMir);
        List<Place> secondPlaces = Arrays.asList(ohotniy, starbucks, bolshoyTheatre, tsum, varienik);
        List<Place> thirdPlaces = Arrays.asList(gmii, arbat, museon);

        routes.add(new Route("3:50", "2 км", "2500 руб", firstPlaces));
        routes.add(new Route("4:30", "3.1 км", "4000 руб", secondPlaces));
        routes.add(new Route("2:20", "6.2 км", "700 руб", thirdPlaces));

    }

}
