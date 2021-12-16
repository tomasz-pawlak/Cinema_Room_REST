package cinema;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Seat {
    int row;
    int column;
    int price;


    public Seat(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public Seat(int row, int column, int price) {
        this.row = row;
        this.column = column;
        this.price = price;
    }

    public Seat() {
    }

    public Seat purchaseTicket(int row, int column) {

        if (row <= 4) {
            price = 10;
        } else {
            price = 8;
        }
        Seat seat = new Seat(row, column, price);

        return seat;
    }

    @Override
    public boolean equals(Object object) {
        Seat seat = (Seat) object;
        if (seat.row == row && seat.column == column) return true;
        return false;
    }

    @Override
    public String toString() {
        return "Seat{" +
                "row=" + row +
                ", column=" + column +
                ", price=" + price +
                '}';
    }
}
