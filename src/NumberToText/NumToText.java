package NumberToText;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class NumToText {
	private int num;
	private String NumToText[] = {"không", "một", "hai", "ba", "bốn", "năm", "sáu", "bảy", "tám", "chín", "mười"};
	private int l_devider[] = {10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000, 1000000000, Integer.MAX_VALUE};
	private String text_devider[] = {"mươi", "trăm", "ngàn", "mươi ngàn", "trăm ngàn", "triệu", "mươi triệu","trăm triệu", "tỷ"};
	
	public NumToText() {}
	public NumToText(int num)
	{
		this.setNum(num);
	}
	public int getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public String removeDuplicateBetween(String input) {
	    String[] keywords = {"ngàn", "triệu", "tỷ"}; // Các từ cần kiểm tra
	    for (String keyword : keywords) {
	    	
	        // Tìm tất cả các từ trùng nhau
	        String regex = "\\b" + keyword + "\\b.*?\\b" + keyword + "\\b"; 
	        while (true) {
	        	
	            // Lưu giá trị đầu vào tạm thời
	            String temp = input;

	            // Tìm vị trí của từ trùng nhau
	            int firstIndex = input.indexOf(keyword);
	            if (firstIndex != -1) {
	                // Tìm từ thứ hai
	                int secondIndex = input.indexOf(keyword, firstIndex + keyword.length());
	                if (secondIndex != -1) {
	                    // Thay thế từ đầu tiên gặp
	                    input = input.substring(0, firstIndex) + input.substring(firstIndex + keyword.length());
	                } else {
	                    break; // Không tìm thấy từ thứ hai, thoát khỏi vòng lặp
	                }
	            } else {
	                break; // Không tìm thấy từ trùng nào, thoát khỏi vòng lặp
	            }
	            if (input.equals(temp)) {
	                break; 
	            }
	        }
	    }
	    return input.trim(); // Trả về kết quả đã xử lý
	}

	public String getTextFromNum()
	{
		int index = -1;
		String result = "";
		String isNegavite = "";
		
		if (num < 0)
		{
			num = num * -1;
			isNegavite = "âm ";
			result = isNegavite +result;
		}
		
		for (int i = 0; i < l_devider.length; i++)
		{
			if (num < l_devider[i])
			{
				index = i - 1;
				break;
			}
		}
		
		if (index == -1) {
			return result + NumToText[num];
		}
		else
		{		
			while (num != 0 && index != -1)
			{
				int devider = l_devider[index];
				int quotient = num / devider;
				int remainder = num % devider;
				result += NumToText[quotient] + " " + text_devider[index] + " ";
				num = remainder;
				index--;				
//				System.out.println(result);
//				System.out.println(num);
			}
			
			if (num != 0 ) {
				result += NumToText[num];
			}
			
			result = removeDuplicateBetween(result);
//			System.out.println(result);
			Map<String, String> exceptions = new HashMap<String, String>();
			
			// Trường hợp logic
			 
			exceptions.put("không trăm\\s*không mươi\\s*không (tỷ|triệu|ngàn)", "");
//			exceptions.put("không mươi tỷ\\s+không tỷ", "");
//			exceptions.put("không mươi triệu\\s+không triệu", "");
			exceptions.put("không mươi ngàn\\s+không ngàn", "");
			exceptions.put("không trăm\\s+(triệu|ngàn)", "không trăm");
			exceptions.put("\\s+", " "); // Replace multiple spaces with single space
			exceptions.put("không mươi", "linh"); 
			
			// Trường hợp giọng đọc
			
			exceptions.put("một\\s+mươi", "mười");
			exceptions.put("mười\\s+năm", "mười lăm");
			exceptions.put("mươi\\s+năm", "mươi lăm");
			exceptions.put("không triệu", "triệu");
			exceptions.put("không ngàn", "ngàn");
			exceptions.put("mươi một", "mươi mốt");

			for (Entry<String, String> entry : exceptions.entrySet()) {
			    if (entry.getKey().contains("(") || entry.getKey().contains("\\") || entry.getKey().contains("|")) {
			        // Use replaceAll only for regex patterns
			        result = result.replaceAll(entry.getKey(), entry.getValue());
			    } else {
			        // Use replace for simple text replacements
			        result = result.replace(entry.getKey(), entry.getValue());
			    }
			}
			
			return result.trim();


		}
	}
}
