import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class DataGenerator {

    public static String generateRandomString() {
        int length = 8;
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder randomString = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int index = new Random().nextInt(characters.length());
            randomString.append(characters.charAt(index));
        }

        return randomString.toString();
    }

    public static int generateRandomInt(int maxValue) {
        return new Random().nextInt(maxValue) + 1;
    }

    public static String generateRandomPhoneNumber() {
        StringBuilder phoneNumber = new StringBuilder("+7 ");

        for (int i = 0; i < 10; i++) {
            phoneNumber.append(new Random().nextInt(10));
        }

        return phoneNumber.toString();
    }


    public static String generateRandomColor() {
        String[] availableColors = {"BLACK", "GREY", "RED", "BLUE", "GREEN"};
        Random random = new Random();
        int index = random.nextInt(availableColors.length);
        return availableColors[index];
    }

    public static String generateDeliveryDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Date tomorrow = calendar.getTime();
        return sdf.format(tomorrow);
    }

}
