import java.util.Random;
import java.util.Scanner;

/**
 * Tests user's ability to fill correct Articles in sentences.
 *
 * Articels: 'a', 'an', 'the'.
 * Senteces are chosen at random from the given text.
 */
public class ArticleTest {

  public static void main (String [] args) {
    Scanner istream = new Scanner(System.in);

    Random random = new Random();

    String text = "Alice is playing with a white kitten (whom she calls 'Snowdrop') and a black kitten (whom she calls 'Kitty')—the offspring of Dinah, Alice's cat in Alice's Adventures in Wonderland—when she ponders what the world is like on the other side of a mirror's reflection. Climbing up on the fireplace mantel, she pokes at the wall-hung mirror behind the fireplace and discovers, to her surprise, that she is able to step through it to an alternative world. In this reflected version of her own house, she finds a book with looking-glass poetry, 'Jabberwocky', whose reversed printing she can read only by holding it up to the mirror. She also observes that the chess pieces have come to life, though they remain small enough for her to pick up. Upon leaving the house (where it had been a cold, snowy night), she enters a sunny spring garden where the flowers have the power of human speech; they perceive Alice as being a 'flower that can move about.' Elsewhere in the garden, Alice meets the Red Queen, who is now human-sized, and who impresses Alice with her ability to run at breathtaking speeds. This is a reference to the chess rule that queens are able to move any number of vacant squares at once, in any direction, which makes them the most 'agile' of pieces.";

    // Split into sentences by period.
    String[] sentences = text.split("\\.");
    int length = sentences.length;

    // Select a sentence randomly that contains at least one article.
    int idx = Math.abs(random.nextInt())%length;
    while (!checkArticles(sentences[idx])) {
      idx = Math.abs(random.nextInt())%length;
    }
    String sentence = sentences[idx];

    // Create a copy of the sentence and delete an article.
    String[] words = sentence.split("\\s+");
    int i;
    for (i=0; i<words.length; i++) {
      String word = words[i];
      if (word.equals("a") || word.equals("an") || word.equals("the")) {
        break;
      }
    }
    String answer = words[i];
    words[i] = new String("_____");

    String question = "";
    for (i=0; i<words.length; i++) {
      question += words[i];
      if (i == words.length - 1) {
        question += ".\n";
      } else {
        question += " ";
      }
    }

    System.out.println("Fill in the blank with correct article: 'a', 'an' or 'the':\n");
    System.out.println(question);
    System.out.print("Your answer (Enter 'a', 'an' or 'the'): ");
    String input = istream.nextLine();
    if (input.equals(answer)) {
      System.out.println("Correct Answer!");
    } else {
      System.out.println("Wrong Answer! Correct answer is: '" + answer + "'.");
    }
  }

  /**
   * Returns true if given sentence contains at least one article.
   */
  protected static Boolean checkArticles(String sentence) {
    String[] words = sentence.split("\\s+");
    for (int i=0; i<words.length; i++) {
      String word = words[i];
      if (word.equals("a") || word.equals("an") || word.equals("the")) {
        return true;
      }
    }
    return false;
  }

}
