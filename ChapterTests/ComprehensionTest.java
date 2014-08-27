import java.util.Random;
import java.util.Scanner;

/**
 * Tests user's comprehension of given text.
 */
public class ComprehensionTest {

  public static void main (String [] args) {
    Scanner istream = new Scanner(System.in);

    Random random = new Random();

    String text = "Alice is playing with a white kitten (whom she calls 'Snowdrop') and a black kitten (whom she calls 'Kitty')—the offspring of Dinah, Alice's cat in Alice's Adventures in Wonderland—when she ponders what the world is like on the other side of a mirror's reflection. Climbing up on the fireplace mantel, she pokes at the wall-hung mirror behind the fireplace and discovers, to her surprise, that she is able to step through it to an alternative world. In this reflected version of her own house, she finds a book with looking-glass poetry, 'Jabberwocky', whose reversed printing she can read only by holding it up to the mirror. She also observes that the chess pieces have come to life, though they remain small enough for her to pick up. Upon leaving the house (where it had been a cold, snowy night), she enters a sunny spring garden where the flowers have the power of human speech; they perceive Alice as being a 'flower that can move about.' Elsewhere in the garden, Alice meets the Red Queen, who is now human-sized, and who impresses Alice with her ability to run at breathtaking speeds. This is a reference to the chess rule that queens are able to move any number of vacant squares at once, in any direction, which makes them the most 'agile' of pieces.";

    // Split into sentences by period.
    String[] sentences = text.split("\\.");
    int length = sentences.length;

    int choice = 1;
    while (choice == 1) {
      // Select a sentence randomly that contains at least one article.
      int idx = Math.abs(random.nextInt())%length;
      String sentence = sentences[idx];

      // Create a copy of the sentence and delete an article.
      String[] words = sentence.split("\\s+");
      idx = Math.abs(random.nextInt()) % words.length;
      while (isStopWord(words[idx])) {
        idx = Math.abs(random.nextInt()) % words.length;
      }
      String answer = words[idx];
      words[idx] = new String("_____");

      String question = "";
      for (int i=0; i<words.length; i++) {
        question += words[i];
        if (i == words.length - 1) {
          question += ".\n";
        } else {
          question += " ";
        }
      }

      System.out.println("\n\nFill in the blank with correct word.\n");
      System.out.println(question);
      System.out.print("Your answer: ");
      String input = istream.nextLine();
      if (input.equals(answer)) {
        System.out.println("Correct Answer!");
      } else {
        System.out.println("Wrong Answer! Correct answer is: '" + answer + "'.");
      }

      System.out.print("\n\nDo you want to continue (Enter '1' for Yes, '0' for No): ");
      choice = istream.nextInt();
      istream.nextLine();
    }
  }

  /**
   * Returns true if given word is a stopword.
   */
  protected static boolean isStopWord(String word) {
    String[] stopwords = {"a", "able", "about", "above", "abst", "accordance", "according", "accordingly", "across", "act", "actually", "added", "adj", "affected", "affecting", "affects", "after", "afterwards", "again", "against", "ah", "all", "almost", "alone", "along", "already", "also", "although", "always", "am", "among", "amongst", "an", "and", "announce", "another", "any", "anybody", "anyhow", "anymore", "anyone", "anything", "anyway", "anyways", "anywhere", "apparently", "approximately", "are", "aren", "arent", "arise", "around", "as", "aside", "ask", "asking", "at", "auth", "available", "away", "awfully", "b", "back", "be", "became", "because", "become", "becomes", "becoming", "been", "before", "beforehand", "begin", "beginning", "beginnings", "begins", "behind", "being", "believe", "below", "beside", "besides", "between", "beyond", "biol", "both", "brief", "briefly", "but", "by", "c", "ca", "came", "can", "cannot", "can't", "cause", "causes", "certain", "certainly", "co", "com", "come", "comes", "contain", "containing", "contains", "could", "couldn't", "d", "date", "did", "didn't", "different", "do", "does", "doesn't", "doing", "done", "don't", "down", "downwards", "due", "during", "e", "each", "ed", "edu", "effect", "eg", "eight", "eighty", "either", "else", "elsewhere", "end", "ending", "enough", "especially", "et", "et-al", "etc", "even", "ever", "every", "everybody", "everyone", "everything", "everywhere", "ex", "except", "f", "far", "few", "ff", "fifth", "first", "five", "fix", "followed", "following", "follows", "for", "former", "formerly", "forth", "found", "four", "from", "further", "furthermore", "g", "gave", "get", "gets", "getting", "give", "given", "gives", "giving", "go", "goes", "gone", "got", "gotten", "h", "had", "happens", "hardly", "has", "hasn't", "have", "haven't", "having", "he", "hed", "hence", "her", "here", "hereafter", "hereby", "herein", "heres", "hereupon", "hers", "herself", "hes", "hi", "hid", "him", "himself", "his", "hither", "home", "how", "howbeit", "however", "hundred", "i", "id", "ie", "if", "i'll", "im", "immediate", "immediately", "importance", "important", "in", "inc", "indeed", "index", "information", "instead", "into", "invention", "inward", "is", "isn't", "it", "itd", "it'll", "its", "itself", "i've", "j", "just", "k", "keep"};

    for (int i=0; i<stopwords.length; i++) {
      if (stopwords[i].equals(word)) {
        return false;
      }
    }
    return true;
  }

}
