import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import java.io.FileOutputStream;

//importing the time-slots data from the exam-scheduling class;
//importing the data of core and elective students to print the datesheet

/*
--------------OUTPUT-1 (EXAM SCHEDULE/DATESHEET)-------------------------
---------------------------------------
subject | date | time-slot (clock-time)
--------|------|------------------------
DAA     | 22/3 |   12 PM- 2PM
--------|------|------------------------
JAVA    | 23/3 |   12 PM- 2PM
--------|------|------------------------
CLOUD   | 24/3 |   12 PM- 2PM
--------|------|------------------------
CYBER   | 24/3 |   12 PM- 2PM 
        |      |

-------------------OUTPUT-2 (SEATING PLAN)----------------------------------

ROOM NAME= LT-1       COURSE-B.TECH         YEAR- 2026-27
-----------------------------------------------------------------------------
ROW1(roll_no.)       ROW2(roll_no)     ROW3(roll_no)     ROW4(roll_no)
    2021                 2025             2030               2035
    2022                 2026             2031               2036
    2023                 2027             2032               2037
    2024                 2028             2033               2038
    2024                 2029             2034               2039
-----------------------------------------------------------------------------
*/

public class datesheet {
    public static void generateDatesheetPDF(
            Map<Integer, List<String>> map,
            int startDay, int month,
            String time, String type) throws Exception {

        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("datesheet.pdf"));
        document.open();

        document.add(new Paragraph("EXAM DATE SHEET"));
        document.add(new Paragraph(" "));

        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100);

        table.addCell("Subject");
        table.addCell("Date");
        table.addCell("Time");

        int s_day = startDay;

        for (int slot : map.keySet()) {
            for (String sub : map.get(slot)) {

                table.addCell(sub.toUpperCase());
                table.addCell(s_day + "/" + month);
                table.addCell(time);
            }

            if (type.equalsIgnoreCase("end"))
                s_day += 2;
            else
                s_day++;

            if (s_day >= 31) {
                s_day = 1;
                month++;
            }
        }

        document.add(table);
        document.close();
    }

    public static void generateSeatingPDF(List<int[][]> allSeating, Room[] rooms) throws Exception {

        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("seating.pdf"));
        document.open();

        Font titleFont = new Font(Font.HELVETICA, 16, Font.BOLD);
        Paragraph title = new Paragraph("SEATING PLAN\n\n", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        for (int i = 0; i < allSeating.size(); i++) {

            document.add(new Paragraph("\nRoom: " + rooms[i].roomName));

            int[][] seating = allSeating.get(i);

            PdfPTable table = new PdfPTable(seating[0].length);
            table.setWidthPercentage(100);

            for (int c = 0; c < seating[0].length; c++) {
                table.addCell("COL " + (c + 1));
            }

            for (int r = 0; r < seating.length; r++) {
                for (int c = 0; c < seating[r].length; c++) {
                    table.addCell(String.valueOf(seating[r][c]));
                }
            }

            document.add(table);
        }

        document.close();
    }

    public static void printdatesheet() throws Exception {

        Object[] result = ExamScheduler.getTimeSlots("students.txt");
        Scanner sc = new Scanner(System.in);

        int[] color = (int[]) result[0];
        String[] subjects = (String[]) result[1];
        System.out.println("Enter type of examination(end/mid): ");
        String type = sc.nextLine();
        System.out.println("Enter starting date of exam(DD/MM): ");
        String date = sc.nextLine();
        System.out.println("Enter the time of examination: ");
        String time = sc.nextLine();

        /*
         * System.out.
         * println("--------------OUTPUT-1 (EXAM SCHEDULE/DATESHEET)-------------------------"
         * );
         * System.out.println("---------------------------------------");
         * System.out.println("subject | date | time-slot (clock-time)");
         * System.out.println("--------|------|------------------------");
         * 
         */
        Map<Integer, List<String>> map = new TreeMap<>();

        for (int i = 0; i < subjects.length; i++) {
            map.putIfAbsent(color[i], new ArrayList<>());
            map.get(color[i]).add(subjects[i]);
        }

        String parts[] = date.split("/");
        int s_day = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        generateDatesheetPDF(map, s_day, month, time, type);

        /*
         * for (int slot : map.keySet()) {
         * 
         * List<String> subList = map.get(slot);
         * 
         * for (String sub : subList) {
         * 
         * System.out.printf("%-8s| %-5s|   %s\n",
         * sub.toUpperCase(),
         * s_day + "/"+ month,
         * time);
         * 
         * System.out.println("--------|------|------------------------");
         * }
         * 
         * if (type.equalsIgnoreCase("end")) {
         * s_day += 2;
         * } else {
         * s_day++;
         * }
         * if(s_day>=31){
         * s_day=1;
         * month++;
         * }
         * }
         * System.out.println(
         * "------------------------------------------------------------------------");
         */
    }

    public static void printShiftWiseSeating(List<Integer> students, Room[] rooms) {

        int totalCapacity = 0;

        for (Room room : rooms) {
            totalCapacity += room.capacity;
        }

        int index = 0;
        int shift = 1;

        while (index < students.size()) {

            System.out.println("\n================ SHIFT " + shift + " ================\n");

            int remainingStudents = students.size() - index;

            // decide how many rooms needed in this shift
            int capacityUsed = 0;

            for (int i = 0; i < rooms.length; i++) {

                if (index >= students.size())
                    break;

                if (shift > 1 && capacityUsed >= remainingStudents)
                    break;

                Room room = rooms[i];

                SeatingResult result = roomall.generateSeatingPlan(students, room, index);

                int[][] seating = result.seating;

                index = result.nextIndex;

                capacityUsed += room.capacity;

                System.out.println("Room: " + room.roomName);
                System.out.println("ROW-1\tROW-2\tROW-3\tROW-4");

                for (int r = 0; r < seating.length; r++) {
                    for (int c = 0; c < seating[r].length; c++) {

                        if (seating[r][c] == 0)
                            System.out.print("0\t");
                        else
                            System.out.print(seating[r][c] + "\t");
                    }
                    System.out.println();
                }

                System.out.println();
            }

            shift++;
        }

        System.out.println(" All students allocated across shifts.");
    }

    public static void seating_plan() throws Exception {

        List<Integer> students = roomall.readRollNumbers("rollnumbers.txt");
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of rooms: ");
        int r = Integer.parseInt(sc.nextLine());

        Room[] rooms = new Room[r];

        for (int i = 0; i < r; i++) {
            System.out.print("Enter room name: ");
            String name = sc.nextLine();

            System.out.print("Enter capacity: ");
            int cap = Integer.parseInt(sc.nextLine());

            rooms[i] = new Room(name, cap);
        }

        int index = 0;

        List<int[][]> allSeating = new ArrayList<>();

        for (Room room : rooms) {

            if (index >= students.size()) {
                break; // stop if all students seated
            }

            SeatingResult result = roomall.generateSeatingPlan(students, room, index);

            allSeating.add(result.seating);

            index = result.nextIndex;

            int remaining = students.size() - index;
        }

        if (index >= students.size()) {

            System.out.println("\n All students have been seated successfully!");

            for (int i = 0; i < allSeating.size(); i++) {

                System.out.println("\nRoom: " + rooms[i].roomName);

                int[][] seating = allSeating.get(i);

                System.out.println("ROW-1\tROW-2\tROW-3\tROW-4");

                for (int r1 = 0; r1 < seating.length; r1++) {
                    for (int c = 0; c < seating[r1].length; c++) {
                        System.out.print(seating[r1][c] + "\t");
                    }
                    System.out.println();
                }
            }

        } else {

            int remaining = students.size() - index;

            System.out.println("\n Not enough rooms!");
            System.out.println("Remaining students: " + remaining);

            System.out.println("\nChoose option:");
            System.out.println("1. Add more rooms");
            System.out.println("2. Create new time slot");

            int choice = Integer.parseInt(sc.nextLine());

            if (choice == 1) {
                System.out.print("Enter number of rooms: ");
                int nw = Integer.parseInt(sc.nextLine());

                Room newrooms[] = new Room[r + nw];

                for (int i = 0; i < r; i++) {
                    newrooms[i] = rooms[i];
                }

                for (int i = 0; i < nw; i++) {
                    System.out.print("Enter room name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter capacity: ");
                    int cap = Integer.parseInt(sc.nextLine());

                    newrooms[r + i] = new Room(name, cap);
                }
                rooms = newrooms;
                r = rooms.length;
                index = 0;
                allSeating.clear();
                for (Room room : rooms) {

                    if (index >= students.size()) {
                        break;
                    }

                    SeatingResult result = roomall.generateSeatingPlan(students, room, index);

                    allSeating.add(result.seating);

                    index = result.nextIndex;

                    remaining = students.size() - index;
                }
                System.out.println("Remaining Students: " + remaining);
            } else if (choice == 2) {
                System.out.println(" Create another shift for remaining students.");
                datesheet.printShiftWiseSeating(students, rooms);

            } else {
                System.out.println("Invalid choice.");
            }

        }
        generateSeatingPDF(allSeating, rooms);
    }

    public static void main(String[] args) throws Exception {
        datesheet.printdatesheet();
        datesheet.seating_plan();
    }
}
