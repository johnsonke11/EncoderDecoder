import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileReader;
import java.io.FileOutputStream;
import java.io.PrintWriter;

public class Decoder {

	final static boolean EVEN = true;
	final static boolean ODD = false;

	public static void main(String[] args) {
		String inputname;
		String outputname;
		String parity;
		boolean pair = false;

		String text = null;

		System.out.println("Please enter an input file name: ");
		Scanner scan = new Scanner(System.in);
		inputname = scan.nextLine();
		System.out.println("Please enter an output file name: ");
		outputname = scan.nextLine();
		System.out.println("Please enter a parity type (even or odd): ");
		parity = scan.nextLine().trim().toLowerCase();
		if (parity.charAt(0) != 'e' && parity.charAt(0) != 'o') {
			System.out.println("Please enter even or odd");
			System.exit(0);
		} else if (parity.charAt(0) == 'e') {
			pair = EVEN;
		} else {
			pair = ODD;
		}

		try {
			Scanner input = null;
			try {
				input = new Scanner(new FileReader(inputname));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				System.err.println("Please enter a correct input file name");
				scan.close();

			}

			Scanner outputcheck = null;
			try {
				outputcheck = new Scanner(new FileReader(outputname));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				System.err.println("Please enter a correct output file name");
				scan.close();

			}
			PrintWriter output = new PrintWriter(new FileOutputStream(
					outputname));
			outputcheck.close();
			
			while (input.hasNextLine()) {
				int errorbit=-1;
				boolean bit;
				//int powercount = 0;
				text = input.nextLine();
				/*for (int i = 1; i <= text.length(); i++) {
					if ((i & (i - 1)) == 0) {
						powercount++;
					}
				}*/
				int size = text.length() + /*powercount*/ + 1;
				ArrayList Numblist = new ArrayList(size);
				for (int count = 0; count < text.length(); count++) {
					Numblist.add("" + text.charAt(count));

				}
				
				for (int i = 1; i <= text.length(); i++) {
					if ((i & (i - 1)) == 0) {
						bit=checkvaluefinder(i,Numblist,pair);
						if(bit == false)
						{
							errorbit+=i;
						}
					}
				}
				if(errorbit != -1)
				{
					
					if(Numblist.get(errorbit).equals("1"))
					{
						Numblist.set(errorbit, "0");
					}
					else if(Numblist.get(errorbit).equals("0"))
					{
						Numblist.set(errorbit, "1");
					}
				
				}
				for (int i = 1; i <= text.length(); i++) {
					if ((i & (i - 1)) == 0) {
						Numblist.set(i-1, "?");
					}
				}
				
				String codeword=Numblist.toString();
				codeword=codeword.replace(",","");
				codeword=codeword.replace("[","");
				codeword=codeword.replace("]","");
				codeword=codeword.replace(" ","");
				codeword=codeword.replace("?", "");
				//System.out.println(codeword);
				output.println(codeword);
				
			}
			output.close();
			
			

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

	}
	public static int onecounter(int position, ArrayList e) {
		int onecount = 0;
		for (int count = position - 1; count < e.size(); count += position * 2) {
			for (int countinner = count; countinner < count + position; countinner++) {
				if (countinner == e.size()) {
					return onecount;
				}
				if (e.get(countinner).equals("1")) {
					onecount++;
				}
			}
		}
		return onecount;

	}

public static boolean checkvaluefinder(int position, ArrayList e,
			boolean pair) {
		boolean bit=true;
		int onecount = onecounter(position, e);
		if (pair == EVEN) {
			if (onecount % 2 == 1) {
				if (e.get(position) == "1")
				{
					bit=false;
				} 
			}
		} 
		else if (pair == ODD) 
		{
			if (onecount % 2 == 1) 
			{
				if(e.get(position) == "0")
				{
					bit = false;
				}
			}
		}
		return bit;
}
}