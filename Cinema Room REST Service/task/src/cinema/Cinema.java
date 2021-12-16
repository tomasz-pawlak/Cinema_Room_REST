package cinema;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Cinema {
    @JsonProperty("total_rows")
    int totalRows;
    @JsonProperty("total_columns")
    int totalColumns;
    @JsonProperty("available_seats")
    List<Seat> availableSeats;

    public Cinema(int totalRows, int totalColumns) {
        this.totalRows = totalRows;
        this.totalColumns = totalColumns;
        this.availableSeats = generateList();
    }

    private List<Seat> generateList() {
        List<Seat> seats = new ArrayList<>();
        for (int i = 1; i <= totalRows; i++) {
            for (int j = 1; j <= totalColumns; j++) {
                seats.add(new Seat(i, j).purchaseTicket(i, j));
            }
        }
        return seats;
    }

}
