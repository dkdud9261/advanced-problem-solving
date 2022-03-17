public class Week1 {

    public static void main(String[] args) {
        System.out.println(reverse(205));
        System.out.println(reverse(-386));

        System.out.println(is_palindrome_number(262L));
        System.out.println(is_palindrome_number(123456654321L));
        System.out.println(is_palindrome_number(200L));

        System.out.println(is_palindrome("civic"));
        System.out.println(is_palindrome("city"));
        System.out.println(is_palindrome("Mr. Owl ate my metal worm"));
        System.out.println(is_palindrome("Do geese see god?"));
        System.out.println(is_palindrome("기러기"));
    }

    // 입력 정수를 문자열로 치환한 후,
    // StringBuffer.reverse()를 이용하여 뒤집기
    public static int reverse(int x) {
        int result = 0;
        String str_x = "";                      // 정수 x를 문자열로 치환할 변수
        String str_result = "";                 // x를 뒤집은 문자열 (결과문자열)
        StringBuffer sb = null;                 // reverse 함수를 사용하기 위한 StringBuffer

        str_x = String.valueOf(x);              // 정수 x를 문자열로 치환
        if(str_x.startsWith("-")) {             // x가 음수일 경우 결과문자열 맨 앞에 '-'를 추가
            str_result += "-";
            str_x = str_x.substring(1);
        }
        sb = new StringBuffer(str_x);
        str_result += sb.reverse().toString();  // x 문자열을 뒤집어서 결과문자열에 추가
        result = Integer.parseInt(str_result);  // 결과문자열을 정수로 치환

        return result;
    }

    // 입력 정수를 문자열로 치환한 후,
    // StringBuffer.reverse()를 이용하여 뒤집은 후 입력 정수와 비교
    public static boolean is_palindrome_number(Long x) {
        String str_x = "";                          // 정수 x를 문자열로 치환할 변수
        String reverse_x = "";                      // x를 뒤집은 문자열
        StringBuffer sb = null;                     // reverse 함수를 사용하기 위한 StringBuffer

        str_x = String.valueOf(x);                  // 정수 x를 문자열로 치환
        sb = new StringBuffer(str_x);
        reverse_x = sb.reverse().toString();        // x를 뒤집기
        if(!reverse_x.equals(str_x)) return false;  // 뒤집은 결과가 x와 다를 경우 return false

        return true;                                // 뒤집은 결과가 x와 같을 경우 return true
    }

    // StringBuffer.reverse()를 이용하여 뒤집은 후 입력 정수와 비교
    public static boolean is_palindrome(String s) {
        StringBuffer sb = null;

        s = s.toLowerCase();                                    // 문자열 s를 모두 소문자로 치환
        s = s.replaceAll("[^a-z]", "");       // 알파벳이 아닌 문자를 모두 제거
        sb = new StringBuffer(s);
        if(!sb.reverse().toString().equals(s)) return false;    // 뒤집은 결과가 s와 다를 경우 return false

        return true;                                            // 뒤집은 결과가 s와 같을 경우 return true
    }
}
