import java.util.stream.Collectors;

/**
 * Created by Sikang on 2019-09-23. This is a class which can help toggle the case of each character
 * in a string, and then reverse them.
 */
public class ToggleService implements StringService {

  @Override
  public String convert(String str) {
    return reverse(toggle(str));
  }

  private String reverse(String str) {
    return new StringBuilder(str).reverse().toString();
  }

  private String toggle(String str) {
    return str.chars().mapToObj(c -> {
      if (Character.isUpperCase(c)) {
        c = Character.toLowerCase(c);
      } else if (Character.isLowerCase(c)) {
        c = Character.toUpperCase(c);
      }
      return String.valueOf((char) c);
    }).collect(Collectors.joining());
  }
}
