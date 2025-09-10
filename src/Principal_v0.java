/**
 * Autores: Camila Costa Durante
 * 			Gabriel Candelaria Wiltgen Barbosa
 * 
 * LAB1: Sistemas Distribuidos - 09/09/2025
 */

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Principal_v0 {

	public final static Path path = Paths			
			.get("src\\fortune-br.txt");
	private int NUM_FORTUNES = 0;

	public class FileReader {

		public int countFortunes() throws FileNotFoundException {

			int lineCount = 0;

			InputStream is = new BufferedInputStream(new FileInputStream(
					path.toString()));
			try (BufferedReader br = new BufferedReader(new InputStreamReader(
					is))) {

				String line = "";
				while (!(line == null)) {

					if (line.equals("%"))
						lineCount++;

					line = br.readLine();

				}// fim while

				System.out.println(lineCount);
			} catch (IOException e) {
				System.out.println("SHOW: Excecao na leitura do arquivo.");
			}
			return lineCount;
		}

		public void parser(HashMap<Integer, String> hm)
				throws FileNotFoundException {

			InputStream is = new BufferedInputStream(new FileInputStream(
					path.toString()));
			try (BufferedReader br = new BufferedReader(new InputStreamReader(
					is))) {

				int lineCount = 0;

				String line = "";
				while (!(line == null)) {

					if (line.equals("%"))
						lineCount++;

					line = br.readLine();
					StringBuffer fortune = new StringBuffer();
					while (!(line == null) && !line.equals("%")) {
						fortune.append(line + "\n");
						line = br.readLine();
						// System.out.print(lineCount + ".");
					}

					hm.put(lineCount, fortune.toString());
					System.out.println(fortune.toString());

					System.out.println(lineCount);
				}// fim while

			} catch (IOException e) {
				System.out.println("SHOW: Excecao na leitura do arquivo.");
			}
		}

		public void read(HashMap<Integer, String> hm)
				throws FileNotFoundException {
			if(Principal_v0.this.NUM_FORTUNES == 0){
				System.out.println("O arquivo nao possui fortunas!");
				return;
			}
			SecureRandom random = new SecureRandom();
			int index_fortune = random.nextInt(Principal_v0.this.NUM_FORTUNES);
			String fortune = hm.get(index_fortune);
			System.out.println("\nFortuna encontrada: " + fortune);
		}

		public void write(HashMap<Integer, String> hm)
				throws FileNotFoundException {
			Scanner scanner = new Scanner(System.in,"UTF-8");
			System.out.println("\nDigite a fortuna que seja adicionar e aperte Enter duas vezes: ");
			StringBuilder new_fortune = new StringBuilder();
			String scam;

			while(scanner.hasNextLine()){
				scam = scanner.nextLine();
				if(scam.isEmpty()){
					break;
				}
				new_fortune.append(scam).append("\n");
			}

			try(BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path.toFile(), true), StandardCharsets.UTF_8))){
				writer.write("\n" + new_fortune.toString().trim() + "\n\n%");
				System.out.println("Fortuna salva!");
			
			}catch(IOException e){
				System.err.println("Erro ao escrever fortuna: " + e.getMessage());
			}finally{
				scanner.close();
			}
			
		}
	}

	public void iniciar() {

		FileReader fr = new FileReader();
		try {
			NUM_FORTUNES = fr.countFortunes();
			HashMap hm = new HashMap<Integer, String>();
			fr.parser(hm);
			fr.read(hm);
			fr.write(hm);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		new Principal_v0().iniciar();
	}

}
