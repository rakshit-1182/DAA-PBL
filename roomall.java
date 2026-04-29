import java.util.*;
import java.io.*;

class Room {

    String roomName;
    int capacity;

    Room(String name, int cap) {
        roomName = name;
        capacity = cap;
    }
}
class SeatingResult {
    int[][] seating;
    int nextIndex;

    public SeatingResult(int[][] seating, int nextIndex) {
        this.seating = seating;
        this.nextIndex = nextIndex;
    }
}

public class roomall {

    public static List<Integer> readRollNumbers(String fileName) throws Exception {

        List<Integer> rolls = new ArrayList<>();

        BufferedReader br = new BufferedReader(new FileReader(fileName));

        String line;

        while ((line = br.readLine()) != null) {
            rolls.add(Integer.parseInt(line.trim()));
        }

        br.close();
        return rolls;
    }

    public static SeatingResult generateSeatingPlan(List<Integer> students, Room room, int startidx) {

        int columns = 4;
        int rows = room.capacity / columns + (room.capacity % columns != 0 ? 1 : 0);

        int index = startidx;

        int[][] seating = new int[rows][columns];

        int filled_seats = 0;

        for (int c = 0; c < columns; c++) {
            for (int r = 0; r < rows; r++) {

                if ((r * columns + c) < room.capacity && index < students.size()) {
                    seating[r][c] = students.get(index);
                    index++;
                    filled_seats++;
                } else if ((r * columns + c) < room.capacity) {
                    seating[r][c] = 0;
                }
            }
        }
         /* 
        System.out.println("\nSeating Plan for Room: " + room.roomName);
        System.out.println("Room Capacity: " + room.capacity);

        System.out.println("ROW-1" + "\t" + "ROW-2" + "\t" + "ROW-3" + "\t" + "ROW-4");

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                System.out.print(seating[r][c] + "\t");
            }
            System.out.println();
        }
        */
        /* 
        int emptySeats = room.capacity - filled_seats;

        System.out.println("Students seated: " + filled_seats);
        System.out.println("Empty seats: " + emptySeats);

        if (filled_seats == 0) {
            System.out.println("Room Status: EMPTY");
        } else if (emptySeats == 0) {
            System.out.println("Room Status: FULL");
        } else {
            System.out.println("Room Status: PARTIALLY FILLED");
        }
            */

        return new SeatingResult(seating, index);
    }

    public static void main(String[] args) throws Exception {
        /* 
        List<Integer> students = readRollNumbers("rollnumbers.txt");
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of rooms: ");
        int r = sc.nextInt();

        Room[] rooms = new Room[r];

        for (int i = 0; i < r; i++) {
            System.out.print("Enter room name: ");
            String name = sc.next();

            System.out.print("Enter capacity: ");
            int cap = sc.nextInt();

            rooms[i] = new Room(name, cap);
        }

        int index = 0;

        for (Room room : rooms) {

            if (index >= students.size()) {
                break;
            }

            index = generateSeatingPlan(students, room, index);

            int remaining = students.size() - index;

            System.out.println("\nRemaining students: " + remaining);
        }

        if (index >= students.size()) {
            System.out.println("\nAll students have been seated successfully!");
        } else {
            System.out.println("\nWarning: Not enough rooms for all students!");
        }*/
    }
}