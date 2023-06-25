package pl.pjatk.Service;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import pl.pjatk.Model.NbpModel;
import pl.pjatk.Model.NbpResponse;
import pl.pjatk.Repository.NbpRepository;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class NbpService {

    private final RestTemplate restTemplate;
    private final NbpRepository nbpRepository;

    public NbpService(RestTemplate restTemplate, NbpRepository nbpRepository1) {
        this.restTemplate = restTemplate;
        this.nbpRepository = nbpRepository1;
    }

    public Double getInfoFromApiAndSendToDb(String currency, String startDate, String endDate) {
        //ArrayList<NbpResponse> responseEntities = new ArrayList<>();
        System.out.println("Hello from getInfoFromApiAndSendToDb");
        ResponseEntity<NbpResponse> response = restTemplate.getForEntity("http://api.nbp.pl/api/exchangerates/rates/a/" + currency + "/" + startDate + "/" + endDate + "/", NbpResponse.class);
        if (response.getStatusCode() != HttpStatus.OK) {
            statusHandler(response.getStatusCode());
        } else {
            NbpResponse nbpResponse = response.getBody();
            System.out.println(nbpResponse);

            String fromDataCurrency = nbpResponse.getCode();
            String fromDataStartDate = nbpResponse.getRates().get(0).getEffectiveDate();
            String fromDataEndDate = nbpResponse.getRates().get(nbpResponse.getRates().size() - 1).getEffectiveDate();

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime currTime = LocalDateTime.now();

            //also called median
            double average/*median*/ = getMedianValue(new ArrayList<>(nbpResponse.getRates()));

            nbpRepository.addRecord(fromDataStartDate, fromDataEndDate, dtf.format(currTime), fromDataCurrency, average);
            return average;
        }
        return 0.0;
    }

    //Tu stary kod
   /* public double getAverageValue(NbpResponse nbpResponse) {
        ArrayList<NbpResponse.Rate> rateArrayList = (ArrayList<NbpResponse.Rate>) nbpResponse.getRates();

        double average = 0.0;

        for (NbpResponse.Rate rate : rateArrayList) {
            average += rate.getMid();
        }

        average /= rateArrayList.size();

        return average;
    } */

    public double getMedianValue(ArrayList<NbpResponse.Rate> list) {

        double median;
        ArrayList<Double> listOfValues = new ArrayList<>();

        list.forEach(rate -> {
            listOfValues.add(rate.getMid());
        });

        Collections.sort(listOfValues);

        if (list.size() % 2 == 0) {
            median = (listOfValues.get(listOfValues.size() / 2) + listOfValues.get(listOfValues.size() / 2 - 1)) / 2;
        } else {
            median = listOfValues.get(listOfValues.size() / 2);
        }

        return median;
    }

    private void statusHandler(HttpStatusCode statusCode) {
        if (statusCode == HttpStatus.NOT_FOUND) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else if (statusCode == HttpStatus.BAD_REQUEST) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
