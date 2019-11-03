import java.util.Scanner;

public class HammingCode4 {
	
	
	public static boolean binary(String input) {
		
		for (int i = 0; i < input.length(); i++) {
			if (!Character.isDigit(input.charAt(i)) || (input.charAt(i) != '0' && input.charAt(i) != '1')) {
				return false;
			}
		}
		return true;
	}

	public static void parity(String input, int step, String[] code) {
		
		String S = "";
		int count = 0;

		for (int i = step - 1; i < input.length();) {
			if ((i + step) > input.length())
				S += input.substring(i, input.length());
			else
				S += input.substring(i, i + step);

			if (step == 1 || step == 2)
				i += Math.pow(2, step);
			else if (step == 4 || step == 8)
				i += Math.pow(2, step - 1);
		}
		for (int j = 0; j < S.length(); j++) {
			if (S.charAt(j) == '1')
				count++;
		}

		if (count % 2 == 0) {
			if (step == 1)
				code[0] = "0";
			else if (step == 2)
				code[1] = "0";
			else if (step == 4)
				code[2] = "0";
			else if (step == 8)
				code[3] = "0";
		} else {
			if (step == 1)
				code[0] = "1";
			else if (step == 2)
				code[1] = "1";
			else if (step == 4)
				code[2] = "1";
			else if (step == 8)
				code[3] = "1";
		}
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		System.out.print("Ievadiet datus: ");

		String input;
		input = in.nextLine();

		while (true) {
			try {
				if (input.length() != 8 || !binary(input))
					throw new Exception();
				else
					break;
			} catch (Exception ex) {
				System.out.println("Nav ievadîti dati 8 bitu garumâ");
				input = in.nextLine();
			}
		}
		input = "XX" + input.charAt(0) + "X" + input.substring(1, 4) + "X" + input.substring(4, 8);

		String encoded = "";
		String code[] = new String[4];

		parity(input, 1, code);
		parity(input, 2, code);
		parity(input, 4, code);
		parity(input, 8, code);

		System.out.print("Kodeðanas vçrtîba: ");
		for (int i = 0; i < code.length; i++) {
			System.out.print(code[i]);
		}
		int k = 0;
		
		for (int j = 0; j < input.length(); j++) {
			if (input.charAt(j) == 'X') {
				encoded += code[k++];
			} else {
				encoded += input.charAt(j);
			}
		}
		System.out.println("\nKodçtie dati : " + encoded);

		System.out.println("Ievadiet saòemtos datus, pa vienam bitam:");
		String received = "";

		while (true) {
			for (int i = 0; i < 12; i++) {
				received += in.next();
			}
			if (!binary(received)) {
				System.out.println("ievadîti nepareizi dati!");
			} else
				break;
		}

		String check[] = new String[4];
		parity(received, 1, check);
		parity(received, 2, check);
		parity(received, 4, check);
		parity(received, 8, check);

		int pos = 0;
		for (int i = 0; i < 4; i++) {
			if (check[i] == "1") {
				pos += Math.pow(2, i);
			}
		}
		int t = 0;
		for (int i = 0; i < encoded.length(); i++) {
			if (encoded.charAt(i) != received.charAt(i)) {
				t++;
			}
		}
		if (t > 1) {
			System.out.println("Pieïauta vairâk kâ viena kïûda datu sûtîðanas rezultâtâ!");
		} else if (pos == 0) {
			System.out.println("Dati korekti");
		} else {
			System.out.println("Kïûda datos ir bitâ nr.: " + pos);
		}
		in.close();
	}
}