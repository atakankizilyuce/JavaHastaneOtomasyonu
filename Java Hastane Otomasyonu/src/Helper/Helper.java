package Helper;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class Helper {
	public static void optionPaneChangeButtonText() {
		UIManager.put("OptionPane.cancelButtonText", "�ptal");
		UIManager.put("OptionPane.noButtonText", "Hay�r");
		UIManager.put("OptionPane.okButtonText", "Tamam");
		UIManager.put("OptionPane.yesButtonText","Evet");
		//kod tekrar�na d��memek i�in hata mesajlar�n� burada listeleniyor, ihtiya� duyulan yerlerde nesne �retilerek kullan�l�r.
	}
	public static void showMsg(String str) {
		String msg;
		optionPaneChangeButtonText();
		switch(str) {
		case "fill":
			msg = "L�tfen t�m alanlar� doldurunuz !";
			break;
		case "success":
			msg = "��lem Ba�ar�l� !!";
			break;
		case "error":
			msg = "Bir hata ger�ekle�ti !";
			break;
		default:
			msg = str;
		}
		JOptionPane.showMessageDialog(null, msg,"Mesaj",JOptionPane.INFORMATION_MESSAGE);
	}
	public static boolean confirm(String str) {
		String msg;
		optionPaneChangeButtonText();
			switch(str) {
			case "sure":
				msg = "Bu i�lemi ger�ekle�tirmek istiyor musun ?";
				break;
			default:
				msg = str;
				break;
		}
			int res = JOptionPane.showConfirmDialog(null, msg,"Dikkat !",JOptionPane.YES_NO_OPTION);
			if(res==0) {
				return true;
			}else {
				return false;
			}
	}
}
