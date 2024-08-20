package top.mxzero.security.utils;

/**
 * 手机号码检查以及脱敏
 *
 * @author Zhang Peng
 * @email qianmeng6879@163.com
 * @since 2023/9/1
 */
public class PhoneUtil {
    private PhoneUtil() {
    }


    public static boolean isPhone(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            return false;
        }
        return phoneNumber.matches("(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}");
    }

    public static String maskPhoneNumber(String phoneNumber) {
        if (!isPhone(phoneNumber)) {
            return phoneNumber;
        }

        return phoneNumber.substring(0, 2) +
                "*".repeat(7) +
                phoneNumber.substring(9, 11);
    }
}
