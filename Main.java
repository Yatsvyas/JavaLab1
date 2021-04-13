import java.util.Scanner;
import java.util.ArrayList;

interface Pupil {

	void printSecondName();

	void changeSecondName();

	void printSubjectsAndMarks();

	void addSubjectsAndMarks();

	void getElementByIndex();

	void getCountOfSubjects();

	void getAverageMark();

}

class Student implements Pupil {
	private String secondName;
	private ArrayList<String> subjects = new ArrayList<String>();
	private ArrayList<Integer> marks = new ArrayList<Integer>();

	Student() {
		Scanner input = new Scanner(System.in);
		System.out.print("Enter the surname of the student:  ");
		secondName = input.nextLine();
	}

	public void printSecondName() {
		System.out.println("Surname: " + secondName);
	}

	public void changeSecondName() {
		Scanner input = new Scanner(System.in);
		System.out.print("Enter the new surname: ");
		secondName = input.nextLine();

		printSecondName();
	}

	public void printSubjectsAndMarks() {

		if (subjects.size() == 0) {
			System.out.println("List of subjects and marks is empty!");
		} else {
			System.out.println("List of " + secondName + " subjects: ");
			for (int i = 0; i < subjects.size(); i++)
				System.out.println(subjects.get(i) + " " + marks.get(i));
		}
	}

	public void addSubjectsAndMarks() {
		int N;

		Scanner input = new Scanner(System.in);
		System.out.print("How many subjects and marks to add: ");
		N = input.nextInt();

		for (int i = 0; i < N; i++) {
			input = new Scanner(System.in);

			System.out.print("Enter " + (i + 1) + " subject: ");
			subjects.add(input.nextLine());

			try {
				if (checkSameSubjects()) {
					throw new DuplicateSubjectException();
				}
			} catch (DuplicateSubjectException error) {
				error.getSubject(subjects);
			}

			System.out.print("Enter of the mark of the subject: ");
			marks.add(input.nextInt());
		}

		printSubjectsAndMarks();
	}

	public void getElementByIndex() {
		Scanner input = new Scanner(System.in);
		int index;
		int size = subjects.size();

		try {
			System.out.print("Number of subject:");
			index = input.nextInt();

			if (index < 1 || index > size)
				throw new MarkOutOfBoundsException();

		} catch (MarkOutOfBoundsException err) {
			index = err.getIndex(size);
		}

		System.out.println(subjects.get(index) + " " + marks.get(index));
	}

	public void getCountOfSubjects() {
		System.out.println("Count of subjects: " + subjects.size());
	}

	public void getAverageMark() {
		int sum = 0;
		double averageMarks;
		for (int mark : marks) {
			sum += mark;
		}
		averageMarks = (double) sum / marks.size();
		averageMarks = (double) Math.round(averageMarks * 100) / 100;
		System.out.println("Average mark of " + secondName + " is equal to: " + averageMarks);
	}

	private boolean checkSameSubjects() {
		int lastIndex = subjects.size() - 1;
		String lastSubject = subjects.get(lastIndex);

		for (int i = 0; i < lastIndex; i++) {
			if (lastSubject.equals(subjects.get(i))) {
				subjects.remove(lastIndex);
				return true;
			}
		}
		return false;
	}
}

class SchoolBoy {
	ArrayList<Register> listRegister = new ArrayList<Register>();

	SchoolBoy() {
		int N;
		Scanner input = new Scanner(System.in);

		System.out.print("Enter the count of student: ");
		N = input.nextInt();

		for (int i = 0; i < N; i++) {
			System.out.println((i + 1) + " student.");
			listRegister.add(new Register());

			listRegister.get(i).addSubjectsAndMarks();
		}
	}

	class Register extends Student {
	}

	void printAllSchoolBoys() {
		System.out.println("List of all students: ");
		int size = listRegister.size();
		for (int i = 0; i < size; i++) {
			listRegister.get(i).printSecondName();
		}
	}

	Register choiceSchoolBoy() {
		Scanner input = new Scanner(System.in);
		int index;
		printAllSchoolBoys();
		System.out.print("Which student you need (enter the number): ");

		try {
			index = input.nextInt();
			if (index < 1 || index > listRegister.size())
				throw new MarkOutOfBoundsException();
			index--;
		} catch (MarkOutOfBoundsException error) {
			index = error.getIndex(listRegister.size());
		}

		return listRegister.get(index);
	}

	void printInfoAboutAllSchoolBoys() {
		for (int i = 0; i < listRegister.size(); i++) {
			System.out.println((i + 1) + " student:");
			listRegister.get(i).printSecondName();

			System.out.println("Info about subjects and marks:");
			listRegister.get(i).printSubjectsAndMarks();
		}
	}
}

class Pupils {

	Pupils() {

	}

	static void printSubjectsAndMarks(Pupil student) {
		student.printSubjectsAndMarks();
	}

	static void getAverageMark(Pupil student) {
		student.getAverageMark();
	}

}

class MarkOutOfBoundsException extends RuntimeException {

	void printMessage() {
		System.out.print("The number is not exist. Re-enter: ");
	}

	int getIndex(int N) {
		int index;

		do {
			Scanner input = new Scanner(System.in);
			printMessage();
			index = input.nextInt();

		} while (index < 1 || index > N);

		return index--;
	}
}

class DuplicateSubjectException extends Exception {

	void printMessage() {
		System.out.println("You entered two identical subjects.");
		System.out.println("Re-enter.");
	}

	void getSubject(ArrayList<String> subjects) {
		Scanner input = new Scanner(System.in);

		do {
			printMessage();
			System.out.print("Enter the subject: ");
			subjects.add(input.nextLine());

		} while (checkSameSubjects(subjects));
	}

	boolean checkSameSubjects(ArrayList<String> subjects) {
		int lastIndex = subjects.size() - 1;
		String lastSubject = subjects.get(lastIndex);

		for (int i = 0; i < lastIndex; i++) {

			if (lastSubject.equals(subjects.get(i))) {
				subjects.remove(lastIndex);
				return true;
			}
		}
		return false;
	}
}

public class Main {

	public static void main(String[] args) {
		SchoolBoy list = new SchoolBoy();
		Pupil student = list.choiceSchoolBoy();
		Pupils.getAverageMark(student);
		Pupils.printSubjectsAndMarks(student);
	}

}
