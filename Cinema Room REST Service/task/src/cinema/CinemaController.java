package cinema;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class CinemaController {

    List<Seat> takenSeats = new ArrayList<>();
    int income = 0;
    Map<Ticket, Seat> tickets = new HashMap<>();


    @GetMapping("/seats")
    public Cinema getCinema() {
        return new Cinema(9, 9);
    }

    @PostMapping("/purchase")
    public ResponseEntity purchaseTicket(@RequestBody Seat seat) {

        Cinema cinema = new Cinema(9, 9);
        Seat newSeat = new Seat(seat.row, seat.column);
        Ticket ticket = new Ticket(UUID.randomUUID());

        if (takenSeats.contains(newSeat)) {
            return ResponseEntity.badRequest().body("{\n \"error\": \"The ticket has been already purchased!\" \n}");
        }

        if (seat.row > cinema.totalRows || seat.column > cinema.getTotalColumns()
                || seat.row <= 0 || seat.column <= 0) {
            return ResponseEntity.badRequest().body
                    ("{\n \"error\": \"The number of a row or a column is out of bounds!\" \n}");
        }

        newSeat.purchaseTicket(newSeat.getRow(), newSeat.getColumn());
        takenSeats.add(newSeat);
        tickets.put(ticket, newSeat);
        income += newSeat.getPrice();

        return ResponseEntity.ok().body(String.format
                ("{\n  \"token\": \"%s\", \n\"ticket\": {\n  \"row\": %s, \n  \"column\": %s, \n  \"price\": %s \n} \n}",
                        ticket.getToken(), newSeat.getRow(), newSeat.getColumn(), newSeat.getPrice()));

    }

    @PostMapping("/return")
    public ResponseEntity returnTicket(@RequestBody Ticket ticket) {

        Ticket purchasedTicket = new Ticket(ticket.getToken());

        if (!tickets.containsKey(purchasedTicket)) {
            return ResponseEntity.badRequest().body("{\n \"error\": \"Wrong token!\" \n}");
        }
        Seat seat = tickets.get(purchasedTicket);

        tickets.remove(purchasedTicket);
        takenSeats.remove(seat);
        income -= seat.getPrice();

        return ResponseEntity.ok().body
                (String.format("{\n \"returned_ticket\": { \n  \"row\": %s, \n  \"column\": %s, \n  \"price\": %s \n} \n}",
                        seat.getRow(), seat.getColumn(), seat.getPrice()));
    }

    @PostMapping("/stats")
    public ResponseEntity showStats(@RequestParam(required = false) String password) {
        String pass = "super_secret";
        Cinema cinema = new Cinema(9, 9);
        int availableSeats = cinema.getAvailableSeats().size() - tickets.size();

        if (password == null) {
            return ResponseEntity.status(401).body("{\n \"error\": \"The password is wrong!\" \n}");
        }
        if (!password.equals(pass)) {
            return ResponseEntity.status(401).body("{\n \"error\": \"The password is wrong!\" \n}");
        }

        return ResponseEntity.ok().body
                (String.format(" { \n  \"current_income\": %s, \n  \"number_of_available_seats\": %s, \n  \"number_of_purchased_tickets\": %s \n}",
                        income, availableSeats, tickets.size()));
    }


}
