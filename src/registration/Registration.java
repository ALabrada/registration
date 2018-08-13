package registration;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

enum CourseType {
    Business, Accounting, Computing
}

enum StudentType {
    Foreign, Home
}

/**
 *
 * @author Samir
 */
public class Registration {

    static Scanner input = new Scanner(System.in);
    private static byte option;
    private static ArrayList students;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        students = new ArrayList();

        do {

            // Main menu
            System.out.println("*********************************");
            System.out.println("*     BRIGHT-FUTURE COLLEGE     *");
            System.out.println("*********************************");
            System.out.println("1. Enroll Students");
            System.out.println("2. Pay Fees");
            System.out.println("3. List course students");
            System.out.println("4. Change course");
            System.out.println("5. View student profile");
            System.out.println("6. Remove student from course");
            System.out.println("7. Reporting");
            System.out.println("8. Exit");
            System.out.println("*********************************");
            System.out.println("Please select your option: ");

            option = input.nextByte();
            input.nextLine();

            switch (option) {
                case 1:
                    registerStudentMenu();
                    break;
                case 2:
                    payFees();
                    break;
                case 3:
                    listCourseStudents();
                    break;
                case 4:
                    changeCourse();
                    break;
                case 5:
                    viewProfile();
                    break;
                case 6:
                    removeFromCourse();
                    break;
                case 7:
                    reporting();
                    break;
                case 8:
                    return;
                default:
                    System.out.println("Please select a valid option number. Press ENTER to continue.");
                    input.nextLine();
            }
        } while (option != 8);
    }

    /**
     * Prompts the user for a student ID and the fee, then looks it up in the
     * students array. Upon successful match, adds the new fee to the fee the
     * student had.
     */
    public static void payFees() {

        System.out.println("*********************************");
        System.out.println("*  PAY STUDENT FEES             *");
        System.out.println("*********************************");
        System.out.println("");

        System.out.println("Please enter student ID: ");
        long id = input.nextLong();

        System.out.println("Please enter fee: ");
        double fee = input.nextDouble();

        Student s = getStudent(id);

        if (s != null) {

            double currentFee = s.getFees();
            double newFee = currentFee + fee;

            if (s.getStudentType() == StudentType.Foreign && newFee > 6000) {
                System.out.println("Foregn students pay a maximum of 6000");
                return;
            }

            if (s.getStudentType() == StudentType.Home && newFee > 3000) {
                System.out.println("Home students pay a maximum of 3000");
                return;
            }

            s.setFees(newFee);
            System.out.println("Fees updated");
            return;

        }

        System.out.println("Student not found.");

    }

    /**
     * Prompts the user for the desired course type, then iterates over the
     * students array printing those that match de course.
     */
    public static void listCourseStudents() {
        System.out.println("*********************************");
        System.out.println("*  VIEW STUDENTS BY COURSE       *");
        System.out.println("**********************************");
        System.out.println("");

        CourseType course = getCourseType();
        boolean found = false;

        for (int i = 0; i < students.size(); i++) {

            Student s = (Student) students.get(i);

            if (s.getCourse() == course) {

                found = true;

                System.out.println("First Name: " + s.getFirstName());
                System.out.println("Last Name: " + s.getLastName());
                System.out.println("Email: " + s.getEmail());
                System.out.println("");

            }
        }

        if (!found) {
            System.out.println("No students for that course.");
        }
    }

    /**
     * Prompts the user for the student ID and looks it up in the students
     * array. Upon successful match, prints out the students data.
     */
    public static void viewProfile() {
        System.out.println("*********************************");
        System.out.println("*  VIEW STUDENT PROFILE          *");
        System.out.println("**********************************");
        System.out.println("");

        System.out.println("Please enter student ID: ");
        long id = input.nextLong();

        for (int i = 0; i < students.size(); i++) {

            Student s = (Student) students.get(i);

            if (s.getId() == id) {

                System.out.println("First Name: " + s.getFirstName());
                System.out.println("Last Name: " + s.getLastName());
                System.out.println("Address: " + s.getAddress());
                System.out.println("Phone Number: " + s.getPhoneNumber());
                System.out.println("Email: " + s.getEmail());
                System.out.println("Paid fees: " + s.getFees());
                System.out.println("Birth date: " + s.getDate());
                System.out.println("Current course: " + s.getCourse());
                System.out.println("Type: " + s.getStudentType());
                System.out.println("ID: " + s.getId());

                return;

            }
        }

        System.out.println("Student not found.");
    }

    /**
     * Prompts the user for the student ID and looks it up in the students
     * array. Upon successful match, it prompts the user for the new course and
     * then updates the student with it.
     */
    public static void changeCourse() {
        System.out.println("*********************************");
        System.out.println("*  CHANGE STUDENT COURSE        *");
        System.out.println("*********************************");
        System.out.println("");

        System.out.println("Please enter student ID: ");
        long id = input.nextLong();

        Student s = getStudent(id);

        if (s != null) {
            CourseType course = getCourseType();
            s.setCourse(course);
            System.out.println("The student's course has been updated.");

        } else {
            System.out.println("The student does not exist");
        }

    }

    /**
     * Prompts the user for the student ID and looks it up in the students
     * array. Upon successful match, it removes the course the student has by
     * setting it to null.
     */
    public static void removeFromCourse() {
        System.out.println("*********************************");
        System.out.println("*  REMOVE STUDENT FROM COURSE   *");
        System.out.println("*********************************");
        System.out.println("");

        System.out.println("Please enter student ID: ");
        long id = input.nextLong();

        Student s = getStudent(id);

        if (s != null) {
            s.setCourse(null);
            System.out.println("The student has been removed from his course.");
        } else {
            System.out.println("The student does not exist");
        }
    }

    /**
     *
     * Prompts the user for the student fields and creates the object. Then it
     * adds it to the students array. The Id is generated automatically.
     *
     */
    public static void registerStudentMenu() {

        System.out.println("*********************************");
        System.out.println("*  REGISTER STUDENT             *");
        System.out.println("*********************************");
        System.out.println("");

        System.out.println("Please type the first name: ");
        String fn = input.nextLine();

        System.out.println("Please type the last name: ");
        String ln = input.nextLine();

        System.out.println("Please type the address");
        String address = input.nextLine();

        System.out.println("Please type the phone number: ");
        String phone = input.nextLine();

        System.out.println("Please type the email: ");
        String email = input.nextLine();

        System.out.println("Please type the fees the student pays upon registration: ");
        double fees = input.nextDouble();

        System.out.println("Please type whether is foreign (Y/N): ");
        StudentType st = getStudentType();

        System.out.println("Please type course: ");
        CourseType course = getCourseType();

        System.out.println("Please enter date of birth: ");
        Date dob = getDate();

        Student s = new Student(fn, ln, address, phone, email, course, fees, dob, st, generateID());
        students.add(s);

        System.out.println("Student successfuly registered");

    }

    /**
     * Shows the management menu for reporting
     */
    public static void reporting() {
        do {
            System.out.println("*********************************");
            System.out.println("*     MANAGEMENT REPORTS        *");
            System.out.println("*********************************");
            System.out.println("1. Most popular course");
            System.out.println("2. Least popular course ");
            System.out.println("3. Total money paid to date");
            System.out.println("4. Total fees yet to be paid");
            System.out.println("5. Go back to main menu");
            System.out.println("*********************************");
            System.out.println("Please select your option: ");

            option = input.nextByte();
            input.nextLine();

            switch (option) {
                case 1:
                    mostPopularCourse();
                    break;
                case 2:
                    leastPopularCourse();
                    break;
                case 3:
                    totalPaid();
                    break;
                case 4:
                    totalToBePaid();
                    break;
                case 5:
                    return;

                default:
                    System.out.println("Please select a valid option number. Press ENTER to continue.");
                    input.nextLine();
            }
        } while (option != 5);
    }

    /**
     * Prints out the the course with more students enrolled. For brevity it is
     * assumed the numbers are different from one course to another.
     */
    public static void mostPopularCourse() {

        int cantBusiness = 0;
        int cantAccounting = 0;
        int cantComputing = 0;

        Student s;

        for (int i = 0; i < students.size(); i++) {

            s = (Student) students.get(i);

            if (s.getCourse() == CourseType.Accounting) {
                cantAccounting += 1;
            }
            if (s.getCourse() == CourseType.Business) {
                cantBusiness += 1;
            }
            if (s.getCourse() == CourseType.Computing) {
                cantComputing += 1;
            }

        }

        if (cantAccounting > cantBusiness && cantAccounting > cantComputing) {
            System.out.println("Accouting is the most popular course");
            return;
        }

        if (cantBusiness > cantAccounting && cantBusiness > cantComputing) {
            System.out.println("Business is the most popular course");
            return;
        }

        if (cantComputing > cantAccounting && cantComputing > cantBusiness) {
            System.out.println("Computing is the most popular course");
            return;
        }
    }

    /**
     * Prints out the the course with more students enrolled. For brevity it is
     * assumed the numbers are different from one course to another.
     */
    public static void leastPopularCourse() {
        int cantBusiness = 0;
        int cantAccounting = 0;
        int cantComputing = 0;

        Student s;

        for (int i = 0; i < students.size(); i++) {

            s = (Student) students.get(i);

            if (s.getCourse() == CourseType.Accounting) {
                cantAccounting += 1;
            }
            if (s.getCourse() == CourseType.Business) {
                cantBusiness += 1;
            }
            if (s.getCourse() == CourseType.Computing) {
                cantComputing += 1;
            }

        }

        if (cantAccounting < cantBusiness && cantAccounting < cantComputing) {
            System.out.println("Accouting is the least popular course");
            return;
        }

        if (cantBusiness < cantAccounting && cantBusiness < cantComputing) {
            System.out.println("Business is the least popular course");
            return;
        }

        if (cantComputing < cantAccounting && cantComputing < cantBusiness) {
            System.out.println("Computing is the least popular course");
            return;
        }
    }

    /**
     * Prints out the total fee paid by adding all students' current paid fee.
     */
    public static void totalPaid() {

        Student s;
        double total = 0;

        for (int i = 0; i < students.size(); i++) {

            s = (Student) students.get(i);

            total += s.getFees();

        }

        System.out.println("Total fees paid to date: " + total);
    }

    /**
     * Prints out the total fee that has yet to be paid. It takes into account
     * the student type in order to determine the pending amount.
     */
    public static void totalToBePaid() {

        Student s;
        double total = 0;
        double cost = 0;

        for (int i = 0; i < students.size(); i++) {

            s = (Student) students.get(i);

            if (s.getStudentType() == StudentType.Foreign) {
                cost = 6000;
            } else {
                cost = 3000;
            }

            total += (cost - s.getFees());

        }

        System.out.println("Total fees yet to be paid add up to: " + total);
    }

    /**
     * Prompts the user for a course type. This method is reused in several
     * parts of the program.
     *
     * @return
     */
    private static CourseType getCourseType() {

        while (true) {
            System.out.println("Please select course type");

            System.out.println("1. Accounting");
            System.out.println("2. Business");
            System.out.println("3. Computing");

            byte option = input.nextByte();

            switch (option) {
                case 1:
                    return CourseType.Accounting;
                case 2:
                    return CourseType.Business;
                case 3:
                    return CourseType.Computing;
                default:
                    System.out.println("Wrong option. Please try again.");
            }
        }

    }

    /**
     * Prompts the user for the student type. This method is reused in several
     * parts of the program.
     *
     * @return
     */
    private static StudentType getStudentType() {

        while (true) {

            System.out.println("1. Foreign");
            System.out.println("2. Home");

            byte option = input.nextByte();

            switch (option) {
                case 1:
                    return StudentType.Foreign;
                case 2:
                    return StudentType.Home;

                default:
                    System.out.println("Wrong option. Please try again.");
            }
        }

    }

    /**
     * Utility method to prompt the user for a Date value.
     *
     * @return
     */
    private static Date getDate() {

        input.nextLine();
        
        while (true) {

            String strDate = input.nextLine();
            DateFormat df = new SimpleDateFormat("dd/mm/yyyy");

            try {

                Date date = df.parse(strDate);
                return date;

            } catch (Exception e) {
                System.out.println("Wrong date format. Please try again.");
            }
        }

    }

    /**
     * Generates an integer to be used as ID for students. It is based on the
     * number of students already registered.
     *
     * @return
     */
    private static int generateID() {
        return students.size() + 1;
    }

    /**
     * Returns a student by looking it up with the given Id. If not found,
     * 'null' is returned.
     *
     * @param id
     * @return
     */
    private static Student getStudent(long id) {

        for (int i = 0; i < students.size(); i++) {

            Student s = (Student) students.get(i);

            if (s.getId() == id) {
                return s;
            }
        }

        return null;
    }

}
