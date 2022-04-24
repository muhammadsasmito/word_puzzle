import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Scanner;


public class Main {

  public static ArrayList getPuzzleApi(String word) throws IOException, InterruptedException, ParseException {
    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create("https://www.dictionary.com/e/wp-json/dictionary/v1/word-finder?letters=" + word))
        .GET()
        .build();

    HttpResponse<String> response = client.send(request,
        HttpResponse.BodyHandlers.ofString());

    //System.out.println(response.body());
    JSONParser parser = new JSONParser();
    JSONObject json = (JSONObject) parser.parse(response.body());
    JSONArray dataResult = (JSONArray) json.get("data");
    ArrayList dataArray = new ArrayList<>();
    dataResult.forEach((o) -> {
      JSONObject value = (JSONObject) o;
      //System.out.println(value.get("word"));
      dataArray.add(value.get("word"));
    });

    return dataArray;
  }

  public static void main(String[] args) {
    int score = 0;
    System.out.println("Coepoe Word Puzzle");
    System.out.println("==================");
    System.out.println("\nRules :");
    System.out.println("1. Create a word using given characters, min 3 characters & max 6 characters.");
    System.out.println("2. Each level, You have 10 chances to answers correctly.");
    System.out.println("3. To get through to next level, you have to score 70 points each level");
    System.out.println("Level 1");
    System.out.println("-------");
    try {
      System.out.println("    d   e   t    t   l   i");
      ArrayList<String> ans = new ArrayList<>();
      ArrayList bow = getPuzzleApi("d,e,t,t,l,i");
      //System.out.println(bow);
      String guess;
      for (int i = 1; i <= 10; i++) {
        do {
          Scanner input = new Scanner(System.in);
          System.out.print(i + "> Your Answer : ");
          guess = input.next();
          if (ans.contains(guess)) {
            System.out.println("You had already type this word before..");
          } else {
            ans.add(guess);
            if (bow.contains(guess)) {
              score += 10;
              System.out.println("#Right. Score : " + score);
            }
            break;
          }
        } while (true);
      }

    } catch (IOException | InterruptedException | ParseException e) {
      e.printStackTrace();
    }

  }

}
