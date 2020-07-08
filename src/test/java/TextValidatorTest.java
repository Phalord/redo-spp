import java.util.Scanner;

import static com.spp.utils.TextValidator.*;

public class TextValidatorTest {
    private final static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        testStudentEnrollmentValidation();
        testProfessorEmployeeNumberValidation();
        testCoordinatorEmployeeNumberValidation();
        testEmailValidation();
        testTelephoneNumberValidation();
    }

    private static void testStudentEnrollmentValidation() {
        while(true) {
            System.out.println("Ingrese una Matrícula de Practicante");
            String studentEnrollment = scanner.nextLine();
            System.out.println(validatePractitionerEnrollment(studentEnrollment));
            System.out.println("Wanna continue? Y/N");
            if (scanner.nextLine().equalsIgnoreCase("N")) {
                break;
            }
        }
    }

    private static void testProfessorEmployeeNumberValidation() {
        while(true) {

            System.out.println("Ingrese un Numero de Empleado del Profesor");
            String professorEmployeeNumber = scanner.nextLine();
            System.out.println(validateProfessorEmployeeNumber(professorEmployeeNumber));
            System.out.println("Wanna continue? Y/N");
            if (scanner.nextLine().equalsIgnoreCase("N")) {
                break;
            }
        }
    }

    private static void testCoordinatorEmployeeNumberValidation() {
        while(true) {
            System.out.println("Ingrese un Numero de Empleado del Coordinador");
            String coordinatorEmployeeNumber = scanner.nextLine();
            System.out.println(validateCoordinatorEmployeeNumber(coordinatorEmployeeNumber));
            System.out.println("Wanna continue? Y/N");
            if (scanner.nextLine().equalsIgnoreCase("N")) {
                break;
            }
        }
    }

    private static void testEmailValidation() {
        while (true) {
            System.out.println("Ingrese un email");
            String email = scanner.nextLine();
            System.out.println(validateEmail(email.trim()));
            System.out.println("Wanna continue? Y/N");
            if (scanner.nextLine().equalsIgnoreCase("N")) {
                break;
            }
        }
    }

    private static void testTelephoneNumberValidation() {
        while (true) {
            System.out.println("Ingrese un numero de teléfono");
            String telephoneNumber = scanner.nextLine();
            System.out.println(validateTelephoneNumber(telephoneNumber));
            System.out.println("Wanna continue? Y/N");
            if (scanner.nextLine().equalsIgnoreCase("N")) {
                break;
            }
        }
    }
}
