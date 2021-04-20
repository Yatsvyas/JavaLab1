import java.util.Scanner;
import java.util.ArrayList;
import java.io.*;

interface Pupil {

	void printSecondName();

	void changeSecondName();

	void printSubjectsAndMarks();

	void addSubjectsAndMarks();

	void getElementByIndex();

	void getCountOfSubjects();

	void getAverageMark();

	public String getSecondName();

	public String[] getArrayOfSubjects();

	public String[] getArrayOfMarks();

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

	Student(InputStream fileSurname, InputStream fileSubjects, InputStream fileMarks) throws IOException {
		String dataSubjects = new String(fileSubjects.readAllBytes());
		String dataMarks = new String(fileMarks.readAllBytes());
		String[] arraySubjects = dataSubjects.split("\r\n");
		String[] arrayMarks = dataMarks.split("\r\n");

		secondName = new String(fileSurname.readAllBytes());

		for (int i = 0; i < arraySubjects.length; i++) {
			subjects.add(arraySubjects[i]);
			marks.add(Integer.parseInt(arrayMarks[i]));
		}
	}

	Student(Reader fileSurname, Reader fileSubjects, Reader fileMarks) throws IOException {
		BufferedReader bufferSurname = new BufferedReader(fileSurname);
		BufferedReader bufferSubjects = new BufferedReader(fileSubjects);
		BufferedReader bufferMarks = new BufferedReader(fileMarks);

		secondName = bufferSurname.readLine();

		while (bufferSubjects.ready()) {
			subjects.add(bufferSubjects.readLine());
			marks.add(Integer.parseInt(bufferMarks.readLine()));
		}

		bufferSurname.close();
		bufferSubjects.close();
		bufferMarks.close();
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

	public String getSecondName() {
		return secondName;
	}

	public String[] getArrayOfSubjects() {
		String[] arrayOfSubjects = new String[subjects.size()];
		subjects.toArray(arrayOfSubjects);
		return arrayOfSubjects;
	}

	public String[] getArrayOfMarks() {
		String[] arrayOfMarks = new String[marks.size()];

		for (int i = 0; i < marks.size(); i++) {
			arrayOfMarks[i] = String.valueOf(marks.get(i));
		}

		return arrayOfMarks;
	}

}

class SchoolBoy {

	ArrayList<Student> listRegister = new ArrayList<Student>();

	SchoolBoy() throws IOException {
		String fileNameSurname;
		String fileNameSubjects;
		String fileNameMarks;
		boolean flag = true;
		int i = 1;

		do {
			fileNameSurname = "surname" + i + ".txt";
			fileNameSubjects = "subjects" + i + ".txt";
			fileNameMarks = "marks" + i + ".txt";
			i++;

			try {
				FileReader fileSurname = new FileReader(fileNameSurname);
				FileReader fileSubjects = new FileReader(fileNameSubjects);
				FileReader fileMarks = new FileReader(fileNameMarks);

				listRegister.add(new Student(fileSurname, fileSubjects, fileMarks));

				fileSurname.close();
				fileSubjects.close();
				fileMarks.close();
			} catch (FileNotFoundException err) {
				System.out.println("Information was loaded");
				flag = false;
			}
		} while (flag);
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

	Student choiceSchoolBoy() {
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

	static void outputPupil(Pupil student, String nameFileSurname, String nameFileSubjects, String nameFileMarks)
			throws IOException {
		byte[] surnameBytes = student.getSecondName().getBytes();
		byte[][] subjectsBytes = new byte[student.getArrayOfSubjects().length][];
		byte[][] marksBytes = new byte[student.getArrayOfMarks().length][];
		FileOutputStream surname = new FileOutputStream(nameFileSurname);
		FileOutputStream subjects = new FileOutputStream(nameFileSubjects);
		FileOutputStream marks = new FileOutputStream(nameFileMarks);

		for (int i = 0; i < student.getArrayOfSubjects().length; i++) {
			subjectsBytes[i] = student.getArrayOfSubjects()[i].getBytes();
			marksBytes[i] = student.getArrayOfMarks()[i].getBytes();
		}

		surname.write(surnameBytes);

		for (int i = 0; i < student.getArrayOfSubjects().length; i++) {
			subjects.write(subjectsBytes[i]);
			subjects.write("\n".getBytes());
			marks.write(marksBytes[i]);
			marks.write("\n".getBytes());
		}

		surname.close();
		subjects.close();
		marks.close();

	}

	static Pupil inputPupil(String nameFileSurname, String nameFileSubjects, String nameFileMarks) throws IOException {
		FileInputStream fileSurname = new FileInputStream(nameFileSurname);
		FileInputStream fileSubjects = new FileInputStream(nameFileSubjects);
		FileInputStream fileMarks = new FileInputStream(nameFileMarks);
		Pupil student = new Student(fileSurname, fileSubjects, fileMarks);
		fileSurname.close();
		fileSubjects.close();
		fileMarks.close();
		return student;
	}

	static Pupil readPupil(String nameFileSurname, String nameFileSubjects, String nameFileMarks) throws IOException {
		FileReader fileSurname = new FileReader(nameFileSurname);
		FileReader fileSubjects = new FileReader(nameFileSubjects);
		FileReader fileMarks = new FileReader(nameFileMarks);
		Pupil student = new Student(fileSurname, fileSubjects, fileMarks);
		fileSurname.close();
		fileSubjects.close();
		fileMarks.close();
		return student;
	}

	static void writePupil(Pupil student, String nameFileSurname, String nameFileSubjects, String nameFileMarks)
			throws IOException {
		FileWriter fileSurname = new FileWriter(nameFileSurname);
		FileWriter fileSubjects = new FileWriter(nameFileSubjects);
		FileWriter fileMarks = new FileWriter(nameFileMarks);

		fileSurname.write(student.getSecondName());

		for (int i = 0; i < student.getArrayOfSubjects().length; i++) {
			fileSubjects.write(student.getArrayOfSubjects()[i]);
			fileSubjects.write('\n');
			fileMarks.write(student.getArrayOfMarks()[i]);
			fileMarks.write('\n');
		}

		fileSurname.close();
		fileSubjects.close();
		fileMarks.close();
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

		return --index;
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

	public static void main(String[] args) throws IOException {
		Pupil student = Pupils.readPupil("surname1.txt", "subjects1.txt", "marks1.txt");
		Pupils.printSubjectsAndMarks(student);
		Pupils.writePupil(student, "surname.txt", "subjects.txt", "marks.txt");

		SchoolBoy schoolBoy = new SchoolBoy();
		schoolBoy.printInfoAboutAllSchoolBoys();

	}

}
