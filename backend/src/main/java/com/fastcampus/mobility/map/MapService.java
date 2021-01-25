package com.fastcampus.mobility.map;

import com.fastcampus.mobility.dto.DrivingRouteDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MapService {

  private final NaverClient naverClient;
  private final ObjectMapper objectMapper;

  @Autowired
  public MapService(final NaverClient naverClient,
      final ObjectMapper objectMapper) {
    this.naverClient = naverClient;
    this.objectMapper = objectMapper;
  }

  public RouteResponse addPath(String startCoordinates, String boardingCoordinates,
      String destinationCoordinates) throws Exception {
    String route = naverClient.getRoute(
        startCoordinates,
        boardingCoordinates,
        destinationCoordinates);
    List paths = JsonPath.read(route, "$.route.traoptimal[0].path");
    int passengerPathIndex = JsonPath
        .read(route, "$.route.traoptimal[0].summary.waypoints[0].pointIndex");
    RouteResponse routeResponse = new RouteResponse();
    routeResponse.setStartCoordinates(startCoordinates);
    routeResponse.setBoardingCoordinates(boardingCoordinates);
    routeResponse.setDestinationCoordinates(destinationCoordinates);
    routeResponse.setPaths(objectMapper.writeValueAsString(paths));
    routeResponse.setBoardingIndex(passengerPathIndex);
    return routeResponse;
  }

  public CoordinatesResponse getDrivingCoordinates(
      DrivingRouteDto drivingRouteDto, Long elapsedTimeSeconds) throws IOException {

    double kmPerHour = 100;
    double kmPerSecond = kmPerHour / (60 * 60);
    double kmEstimated = kmPerSecond * elapsedTimeSeconds; // 예상 주행 KM
    CoordinatesResponse coordinatesResponse = new CoordinatesResponse();

    List paths = objectMapper.readValue(drivingRouteDto.getPaths(), List.class);
    int passengerPathIndex = drivingRouteDto.getBoardingIndex();
    AtomicInteger currentPathIndex = new AtomicInteger(0);
    double cumulativeDistance = 0;
    while (true) {
      if (currentPathIndex.get() == passengerPathIndex) {
        coordinatesResponse.setPassengerPassed(true);
      }

      List currentCoordinates = (List) paths.get(currentPathIndex.get());
      double lon1 = (double) currentCoordinates.get(0);
      double lat1 = (double) currentCoordinates.get(1);
      if (currentPathIndex.get() == (paths.size() - 1)) {
        coordinatesResponse.setCoordinates(lon1 + "," + lat1);
        coordinatesResponse.setDestinationPassed(true);
        break;
      }
      List nextCoordinates = (List) paths.get(currentPathIndex.get() + 1);
      double lon2 = (double) nextCoordinates.get(0);
      double lat2 = (double) nextCoordinates.get(1);

      double distanceOfPath = this.getKmUnitDistance(lon1, lat1, lon2, lat2);
      double nextCumulativeDistance = cumulativeDistance + distanceOfPath;

      if (cumulativeDistance <= kmEstimated && kmEstimated < nextCumulativeDistance) {
        double remainKm = kmEstimated - cumulativeDistance;
        double remainRate = remainKm / distanceOfPath;
        coordinatesResponse.setCoordinates(
            this.format(lon1 + ((lon2 - lon1) * remainRate)) + "," +
                this.format(lat1 + ((lat2 - lat1) * remainRate))
        );
        break;
      }
      cumulativeDistance = cumulativeDistance + distanceOfPath;
      currentPathIndex.incrementAndGet();
    }
    return coordinatesResponse;
  }

  private double format(double number) {
    return Double.parseDouble(String.format("%.5f", number));
  }

  private double getKmUnitDistance(double lon1, double lat1, double lon2, double lat2) {
    if ((lat1 == lat2) && (lon1 == lon2)) {
      return 0;
    } else {
      double theta = lon1 - lon2;
      double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2))
          + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math
          .cos(Math.toRadians(theta));
      dist = Math.acos(dist);
      dist = Math.toDegrees(dist);
      return dist * 60 * 1.1515 * 1.609344;
    }
  }
}
