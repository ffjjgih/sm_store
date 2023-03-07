package fpoly.edu.datn.vibee.utilities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.CollectionType;
import org.apache.logging.log4j.ThreadContext;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Pattern;

/**
 * @author haidv
 * @version 1.0
 */
public class CommonUtil {
	private static String localIp = null;
	private static ModelMapper mapper = null;
	public static final String DATE_FORMAT = "dd/MM/yyyy";
	public static final String DATE_FORMAT_YYYYMMDD = "yyyy-MM-dd";
	public static final String DATE_FORMAT_YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_FORMAT_YYYYMMDDHHMMSSSSS = "yyyy-MM-dd HH:mm:ss.SSS";
	public static final String DATE_FORMAT_YYYYMMDDHHMMSSSSS_2 = "yyyyMMddHHmmssSSS";
	public static final String DATE_FORMAT_YYYYMMDDHHMMSSSSS_3 = "yyyyMMddHHmmss";

	public static <S, T> T toObject(S s, Class<T> targetClass) {
		getMapper();
		if (s == null) {
			return null;
		}
		return mapper.map(s, targetClass);
	}

//	public static <T> T toObject(String s, Class<T> targetClass) {
//		getMapper();
//		return mapper.map(s, targetClass);
//	}

	public static <S, T> List<T> toListObject(List<S> list, Class<T> targetClass) {
		if (list == null || list.isEmpty()) {
			return null;
		}
		List<T> result = new ArrayList<>();
		for (S item : list) {
			result.add(toObject(item, targetClass));
		}

		return result;
	}

	private static void getMapper() {
		if (mapper == null) {
			mapper = new ModelMapper();
			mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		}
	}

	public static boolean isNullOrEmpty(String str) {
		return str == null || str.trim().isEmpty();
	}

	public static <T> String beanToString(T value) {
		if (value == null) {
			return null;
		}
		Class<?> clazz = value.getClass();
		if (clazz == int.class || clazz == Integer.class) {
			return "" + value;
		} else if (clazz == String.class) {
			return (String) value;
		} else if (clazz == long.class || clazz == Long.class) {
			return "" + value;
		} else {
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
			String jsonString = "";
			try {
				jsonString = mapper.writeValueAsString(value);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
				jsonString = "Can't build json from object";
			}
			return jsonString;
		}
	}

	public static <T> String listToJson(List<T> list) {
		if (list == null || list.isEmpty()) {
			// return "[]";
			return null;
		}
		String json = "[";
		for (T item : list) {
			json = json + beanToString(item) + ",";
		}
		json = json.substring(0, json.length() - 1) + "]";
		return json;
	}

	public static <T> List<T> jsonToList(String json, Class<T> clazz) throws IOException {
		if (CommonUtil.isNullOrEmpty(json)) {
			return new ArrayList<>();
		}
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		CollectionType listType = mapper.getTypeFactory().constructCollectionType(ArrayList.class, clazz);
		return mapper.readValue(json, listType);
	}

	@SuppressWarnings("unchecked")
    public static <T> T stringToBean(String str, Class<T> clazz) {
		if (str == null || str.length() <= 0 || clazz == null) {
			return null;
		}
		if (clazz == int.class || clazz == Integer.class) {
			return (T) Integer.valueOf(str);
		} else if (clazz == String.class) {
			return (T) str;
		} else if (clazz == long.class || clazz == Long.class) {
			return (T) Long.valueOf(str);
		} else {
			ObjectMapper mapper = new ObjectMapper();
			try {
				return mapper.readValue(str, clazz);
			} catch (IOException e) {
				return null;
			}
		}
	}

	public static String getLocalIp() {
		if (!isNullOrEmpty(localIp)) {
			return localIp;
		}
		InetAddress result = null;
		try {
			int lowest = Integer.MAX_VALUE;
			for (Enumeration<NetworkInterface> nics = NetworkInterface.getNetworkInterfaces(); nics
					.hasMoreElements();) {
				NetworkInterface ifc = nics.nextElement();
				if (ifc.isUp()) {
					if (ifc.getIndex() < lowest || result == null) {
						lowest = ifc.getIndex();
					} else if (result != null) {
						continue;
					}

					// @formatter:off
                    //if (!ignoreInterface(ifc.getDisplayName())) {
                    for (Enumeration<InetAddress> addrs = ifc
                            .getInetAddresses(); addrs.hasMoreElements(); ) {
                        InetAddress address = addrs.nextElement();
                        if (address instanceof Inet4Address
                                && !address.isLoopbackAddress()
                        ) {
                            result = address;
                        }
                    }
                    //}
                    // @formatter:on
				}
			}
		} catch (IOException ex) {

		}

		if (result != null) {
			return result.getHostAddress();
		}

		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {

		}

		return null;
	}

//	public static String randomString(int length) {
//		int leftLimit = 48; // numeral '0'
//		int rightLimit = 122; // letter 'z'
//		Random random = new Random();
//
//		String generatedString = random.ints(leftLimit, rightLimit + 1)
//				.filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97)).limit(length)
//				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
//		return generatedString.toUpperCase();
//	}

	public static Long safeToLong(Object obj) {
		return safeToLong(obj, null);
	}

	public static Long safeToLong(Object obj, Long defaultValue) {
		if (obj != null) {
			return Long.parseLong(obj.toString());
		}
		return defaultValue;
	}

	private static String safeToString(Object obj, String defaultValue) {
		if (obj != null && obj != "") {
			return obj.toString();
		}
		return defaultValue;
	}

	public static String safeToString(Object obj) {
		return safeToString(obj, null);
	}

//	public static Double safeToDouble(Object obj, Double defaultValue) {
//		if (obj != null) {
//			return Double.parseDouble(obj.toString());
//		}
//		return defaultValue;
//	}

//	public static List<FieldChange> checkChange(Object s1, Object s2, Map<String, String> ignoreFields)
//			throws IllegalAccessException {
//		List<FieldChange> changedProperties = new ArrayList<>();
//		if (s1 == null || s2 == null) {
//			return changedProperties;
//		}
//		for (Field field : s1.getClass().getDeclaredFields()) {
//			if (ignoreFields != null && ignoreFields.containsKey(field.getName())) {
//				continue;
//			}
//			field.setAccessible(true);
//			Object value1 = field.get(s1);
//			Object value2 = null;
//			try {
//				Field field1 = s2.getClass().getDeclaredField(field.getName());
//				field1.setAccessible(true);
//				value2 = field1.get(s2);
//			} catch (NoSuchFieldException ex) {
//				continue;
//			}
//			if (value1 == null && value2 == null) {
//				continue;
//			}
//			boolean isDifference = false;
//			if (value1 == null || value2 == null) {
//				isDifference = true;
//
//			} else {
//				if (isBaseType(value1.getClass())) {
//					if (!Objects.equals(value1, value2)) {
//						isDifference = true;
//					}
//				}
//			}
//			if (isDifference) {
//				FieldChange fieldChange = new FieldChange();
//				fieldChange.setFieldName(field.getName());
//				if (value1 != null) {
//					fieldChange.setOldValue(String.valueOf(value1));
//				} else {
//					fieldChange.setOldValue("");
//				}
//				if (value2 != null) {
//					fieldChange.setNewValue(String.valueOf(value2));
//				} else {
//					fieldChange.setNewValue("");
//				}
//				changedProperties.add(fieldChange);
//			}
//		}
//		return changedProperties;
//	}

	private static final Set<Class<?>> BASE_TYPES = new HashSet<Class<?>>(
			Arrays.asList(String.class, Boolean.class, Character.class, Byte.class, Short.class, Integer.class,
					Long.class, Float.class, Double.class, Void.class, Date.class, Timestamp.class));

	private static boolean isBaseType(Class<? extends Object> clazz) {
		return BASE_TYPES.contains(clazz);
	}

	public static boolean listIsEmptyOrNull(Collection<?> collection) {
		return (collection == null || collection.isEmpty());
	}

	@SuppressWarnings("unchecked")
    public static <T> T convertStringToObject(String str, TypeReference<T> typeReference) {
		if (isNullOrEmpty(str) || typeReference == null) {
			return null;
		}
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		try {
			return typeReference.getType().equals(String.class) ? (T) str : mapper.readValue(str, typeReference);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static List<String> convertJsonString(String json) {
		List<String> listNationality = new ArrayList<>();
		String nationality = json;
		if (!CommonUtil.isNullOrEmpty(json)) {
			nationality = nationality.replace("]", "");
			nationality = nationality.replace("[", "");
			String[] list = nationality.split(",");

			for (String s : list) {
				if (!CommonUtil.isNullOrEmpty(s)) {
					listNationality.add(s);
				}
			}
		}
		return listNationality;
	}

	public static boolean compareString(String s1, String s2) {
		if (isNullOrEmpty(s1)) {
			if (isNullOrEmpty(s2)) {
				return true;
			} else {
				return false;
			}
		} else {
			return s1.equals(s2);
		}
	}

//	public static String convertMobileNumberTo84(String mobileNumber) {
//		if (!CommonUtil.isNullOrEmpty(mobileNumber) && mobileNumber.startsWith("0")) {
//			return Constant.MOBILE_NUMBER_PREFIX_84 + mobileNumber.substring(1);
//		}
//		return mobileNumber;
//	}

	public static String convertDateFormat(String date, String oldFormat, String newFormat) {
		if (isNullOrEmpty(date)) {
			return null;
		}
		try {
			return LocalDate.parse(date, DateTimeFormatter.ofPattern(oldFormat))
					.format(DateTimeFormatter.ofPattern(newFormat));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @content Get uuid into ThreadContext
	 */
	public static String getClientMessageId() {
		return ThreadContext.get("uuid");
	}

	public static <T> List<T> mapAsList(Iterable<?> fromList, Class<T> instanceClass) {
		getMapper();
		return mapper.map(fromList, new TypeToken<List<T>>() {
		}.getType());
	}

	public static List<String> stringToList(String str) {
		if (str == null) {
			return null;
		}
		if (str.charAt(str.length() - 1) == ']') {
			str = str.replace(str.substring(str.length() - 1), "");
		}
		if (str.charAt(0) == '[') {
			str = str.substring(1);
		}
		if (str.split(",").length == 0) {
			return Arrays.asList(str);
		}
		return Arrays.asList(str.split(","));
	}

	public static <T> String listToString(List<T> str) {
		String response = "";
		if (str == null || str.size() == 0) {
			return null;
		}
		for (T item : str) {
			response += item + ",";
		}
		if (isNullOrEmpty(response)) {
			return null;
		}

		return response.substring(0, (response.length() - 1));
	}

	public static Date convertStringToDateTime(String date) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		try {
			return format.parse(date);
		} catch (Exception e) {
			return null;
		}
	}

	public static String convertStringToDate(String date) {
		if (date == null) {
			return null;
		}
		if (date.length()<=8){
			return date;
		}
		date=date.replaceAll("-","");
		date=date.replaceAll("/","");
		date=date.replaceAll(":","");
		date = date.replace(date.substring(8), "");
		return date;
	}

	public static String convertLanguage(String value){
		if (value==null){
			return null;
		}
		value=value.replaceAll("â|á|ạ|à|ã|ả|ấ|ẩ|ậ|ầ|ắ|ă|ẳ|ằ|ẵ|ặ|ẫ","a");
		value=value.replaceAll("ê|é|ẽ|ẻ|ẹ|è|ễ|ệ|ế|ề|ể","e");
		value=value.replaceAll("ư|ự|ữ|ử|ừ|ụ|ủ|ù|ú|ũ","u");
		value=value.replaceAll("ý|ỷ|ỹ|ỵ|ỹ","y");
		value=value.replaceAll("ỉ|ĩ|ị|ì|í","i");
		value=removeAccents(value);
		value=value.replaceAll("Á|À|Ạ|Ả|Ã|Â|Ấ|Ẩ|Ầ|Ẫ|Ậ|Ă|Ẳ|Ắ|Ằ|Ẵ|Ặ","A");
		value=value.replaceAll("Ê|Ế|Ề|Ể|Ệ|Ễ|Ẹ|È|Ẽ|Ẻ|É","E");
		value=value.replaceAll("Ư|Ự|Ữ|Ử|Ừ|Ụ|Ủ|Ú|Ù|Ũ","U");
		value=value.replaceAll("Ý|Ỷ|Ỹ|Ỵ|Ỳ","Y");
		value=value.replaceAll("Ỉ|Ĩ|Ị|Ì|Í","I");
		value=value.replaceAll("Đ","D");
		value=value.replaceAll("ó|ỏ|ọ|õ|ò|ô|ố|ỗ|ồ|ộ|ổ|ở|ơ|ờ|ỡ|ớ|ợ","o");
		value=value.replaceAll("!|@|#|$|&|~|`|<|>|_|,"," ");
		return value;
	}

	public static String removeAccents(String value) {
		if (value == null) {
			return null;
		}
		value = value.replace("\u0110", "D").replace("\u0111", "d");
		String temp = Normalizer.normalize(value, Normalizer.Form.NFD);
		Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
		return pattern.matcher(temp).replaceAll("");
	}

	public static boolean uploadFile(MultipartFile file){
		try {
			FileCopyUtils.copy(file.getBytes(), new File(Constant.UPLOAD_DIR + file.getOriginalFilename()));
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}