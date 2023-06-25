package pl.pjatk.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.pjatk.Model.NbpModel;
import pl.pjatk.Service.NbpService;

@RestController
@RequestMapping("/NBP")
@Tag(name = "Opis API projektu wyliczajacego mediane z danych NBP")
public class NbpController {

    private final NbpService nbpService;

    public NbpController(NbpService nbpService) {
        this.nbpService = nbpService;
    }

    @Operation(description = "Całe zapytanie zwracajace mediane wyglada nastepująco: http://localhost:8083/NBP/kursy/{waluta}/{Data_rozpoczecia}/{Data_zakonczenia}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sukces", content = {
                    @Content(
                            mediaType = "text/html",
                            schema = @Schema(implementation = Double.class))
            }),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "400", description = "Złe zapytanie"),
            @ApiResponse(responseCode = "500", description = "Problem z serwerem")
    })
    @GetMapping("/kursy/{currency}/{startDate}/{endDate}")
    public ResponseEntity<Double> saveToDb(@PathVariable String currency, @PathVariable String startDate, @PathVariable String endDate){
        System.out.println("Hello from saveToDb");
        return ResponseEntity.status(HttpStatus.OK).body(nbpService.getInfoFromApiAndSendToDb(currency, startDate, endDate));
    }
}
