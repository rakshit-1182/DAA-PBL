import java.util.*;

import javax.security.auth.Subject;

class subject {
    String sub_code;
    String sub_name;
    String sub_type;

    subject(String code, String name, String type) {
        this.sub_code = code;
        this.sub_name = name;
        this.sub_type = type;
    }

    void display() {
        System.out.println(sub_code + " - " + sub_name + " - " + sub_type);
    }
}

class Room {
    String room_name;
    int capacity;

    Room(String name, int cap) {
        this.room_name = name;
        this.capacity = cap;
    }

    void show_rooms() {
        System.out.println("Room - " + room_name + " - " + capacity);
    }
}

public class input {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the number of subjects: ");
        int n = input.nextInt();
        input.nextLine();

        subject[] subjects = new subject[n];
        for (int i = 0; i < n; i++) {

            System.out.println("\nEnter details for subject " + (i + 1));

            System.out.print("Enter Subject Code: ");
            String code = input.nextLine();

            System.out.print("Enter Subject Name: ");
            String name = input.nextLine();

            System.out.println("Enter (CORE/ELECTIVE)");
            String type = input.nextLine().toUpperCase();

            subjects[i] = new subject(code, name, type);
        }

        System.out.println("\nSubjects Entered:");

        for (subject s : subjects) {
            s.display();
        }

        // inputting data for rooms and their available capacity--

        System.out.println("Enter the number of rooms available: ");
        int m = input.nextInt();
        input.nextLine();

        Room[] rooms = new Room[m];

        for (int i = 0; i < m; i++) {

            System.out.println("\nEnter details for Rooms " + (i + 1));

            System.out.print("Enter Room Name: ");
            String room_name = input.nextLine();

            System.out.print("Enter Room capacity: ");
            int cap = input.nextInt();
            input.nextLine();

            rooms[i] = new Room(room_name, cap);
        }

        System.out.println("\nRooms Data:");

        for (Room s : rooms) {
            s.show_rooms();
        }

        input.close();
    }
}
