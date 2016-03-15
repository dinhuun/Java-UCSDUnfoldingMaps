package demos;

public class Temp {

	public static String replace(String word, char a, char b) {
		System.out.println("parameter \t" + word.hashCode());
		char[] ca = word.toCharArray();
		System.out.println("to array \t" + ca.hashCode());
		for (int i = 0; i < ca.length; i++) {
			if (ca[i] == a) {
				ca[i] = b;
			}
		}
		word = new String(ca);
		System.out.println("parameter returned \t" + word.hashCode());
		return word;
	}

	public static void main(String[] args) {
		String received = replace("a happy", 'a', 'i');
		System.out.println("received \t" + received.hashCode());
		System.out.println(received);
		
		String text = "hello  hello?";
		String[] words = text.split(" ");
		System.out.println(words.length);
		System.out.println(words[0] + words[1] + words[2]);
		
	}

}
