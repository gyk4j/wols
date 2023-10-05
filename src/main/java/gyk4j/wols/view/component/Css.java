package gyk4j.wols.view.component;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

public class Css {
	
	public static final Border DEBUG_BORDER_GREEN = BorderFactory.createLineBorder(Color.GREEN, 1);
	public static final Border DEBUG_BORDER_MAGENTA = BorderFactory.createLineBorder(Color.MAGENTA, 1);
	public static final Border DEBUG_BORDER_YELLOW = BorderFactory.createLineBorder(Color.YELLOW, 1);

	public static final String MENU_ICON = "menu-icon.png";
	public static final String ICON = "wifi.png";
	public static final String LOGO = "logo.png";
	public static final String APP_TITLE = "WOLS";
	public static final Dimension WINDOW_SIZE = new Dimension(854, 640);
	public static final Dimension FIXED_BAR_SIZE = new Dimension(100, 640);

//	public static final Color BACKGROUND_SELECTED = new Color(51, 153, 255);
//	public static final Color BACKGROUND_SELECTED = new Color(0xfc, 0xfc, 0xfc, 0xff);
	public static final Color BACKGROUND_SELECTED = Color.WHITE;
	public static final Color BACKGROUND_UNSELECTED = new Color(0xee, 0xee, 0xee, 0xff);
	
	public static final String FONT_NAME = "Liberation Sans"; //"Liberation Sans"; //Font.SANS_SERIF
	public static final Font FONT_10PT = new Font(FONT_NAME, Font.PLAIN, 10);
	public static final Font FONT_12PT = new Font(FONT_NAME, Font.PLAIN, 12);
	public static final Font FONT_14PT = new Font(FONT_NAME, Font.PLAIN, 14);
	public static final Font FONT_ISP = new Font(FONT_NAME, Font.BOLD | Font.ITALIC, 14);
	public static final Font FONT_LINE = new Font(FONT_NAME, Font.PLAIN, 16);
	public static final Font FONT_PAGE = new Font(FONT_NAME, Font.PLAIN, 20);
	public static final Font FONT_PIN = new Font(Font.MONOSPACED, Font.BOLD, 20);
	public static final Font FONT_COUNTDOWN = new Font(FONT_NAME, Font.BOLD, 20);
	public static final Font FONT_UPDATE_NOW = new Font(FONT_NAME, Font.BOLD, 20);
	public static final Font FONT_LOGO = new Font(Font.MONOSPACED, Font.BOLD, 20);
	public static final Font FONT_BANNER = new Font(FONT_NAME, Font.BOLD, 20);
	public static final Font FONT_CLOSE = new Font(FONT_NAME, Font.PLAIN, 20);
	public static final Color COLOR_PAGE = new Color(71, 110, 130);
	public static final Color COLOR_PANE_HEADER = new Color(128, 147, 169);
	
	public static final Color PAGE_BACKGROUND = Color.WHITE;
	public static final Color PAGE_FOREGROUND = Color.BLACK;
	public static final Color PAGE_DISABLED = Color.LIGHT_GRAY;
	
	public static final Color TOP_START = new Color(58, 107, 169);
	public static final Color TOP_END = new Color(118, 215, 203);
	
	public static final Color PROGRESSBAR_ACTIVE_BACKGROUND = TOP_START;
	public static final Color PROGRESSBAR_ACTIVE_FOREGROUND = Color.BLACK;
	public static final Color PROGRESSBAR_INACTIVE_BACKGROUND = Color.DARK_GRAY;
	public static final Color PROGRESSBAR_INACTIVE_FOREGROUND = Color.LIGHT_GRAY;
	
	public static final Color[] STEPPER_STEP = { 
			new Color(71, 110, 130), // Registration/Setup
			new Color(236, 123, 89), // Verification
			new Color(26, 129, 187), // Configuration
	};
	
	public static final Font FONT_STEPPER = new Font(FONT_NAME, Font.BOLD, 10);
	public static final int STEPPER_BORDER_MARGIN = 16;
	public static final Border STEPPER_BORDER = BorderFactory.createEmptyBorder(
			STEPPER_BORDER_MARGIN, 
			STEPPER_BORDER_MARGIN, 
			STEPPER_BORDER_MARGIN, 
			STEPPER_BORDER_MARGIN);
	
	public static final int HTMLVIEW_BORDER_MARGIN = 48;
	public static final Border HTMLVIEW_BORDER = BorderFactory.createEmptyBorder(
			HTMLVIEW_BORDER_MARGIN, 
			HTMLVIEW_BORDER_MARGIN, 
			HTMLVIEW_BORDER_MARGIN, 
			HTMLVIEW_BORDER_MARGIN);
	
	public static final Color LINK_TEXT = new Color(0, 132, 222);
	
	public static final int BUTTON_VERTICAL_MARGIN = 8;
	public static final int BUTTON_HORIZONTAL_MARGIN = 32;
	public static final Border BUTTON_BORDER = BorderFactory.createEmptyBorder(
			BUTTON_VERTICAL_MARGIN, 
			BUTTON_HORIZONTAL_MARGIN, 
			BUTTON_VERTICAL_MARGIN, 
			BUTTON_HORIZONTAL_MARGIN);
	
	public static final int BUTTON_TRANSPARENT_MARGIN = 8;
	public static final Border TRANSPARENT_BORDER = BorderFactory.createEmptyBorder(
			BUTTON_TRANSPARENT_MARGIN, BUTTON_TRANSPARENT_MARGIN,
			BUTTON_TRANSPARENT_MARGIN, BUTTON_TRANSPARENT_MARGIN);
	
	public static final Color BUTTON_NEXT = new Color(74, 74, 74);
	public static final Color BUTTON_CONFIGURATION_PROCEED = new Color(6, 60, 130);
	
	public static final Color NEWS_GRAY_LIGHT = new Color(242, 242, 242);
	public static final Color NEWS_GRAY_DARK = new Color(204, 203, 203);
	
	public static final int NEWS_MARGIN_Y = 0;
	public static final int NEWS_MARGIN_X = 32;
	public static final Border NEWS_BORDER = BorderFactory.createEmptyBorder(
			NEWS_MARGIN_Y, 
			NEWS_MARGIN_X, 
			NEWS_MARGIN_Y, 
			NEWS_MARGIN_X);
	public static final Color COLOR_NEWS_NEW_OVERLAY_BACKGROUND = new Color(0xff, 0x31, 0x31);
	public static final Color COLOR_NEWS_NEW_OVERLAY_FOREGROUND = Color.WHITE;
	
	public static final Color COLOR_DEFAULT_FOREGROUND = Color.BLACK;
	public static final Color COLOR_DEFAULT_BACKGROUND = Color.WHITE;
	public static final Color COLOR_PASS_FOREGROUND = new Color(0x00, 0x64, 0x00);
	public static final Color COLOR_PASS_BACKGROUND = new Color(0xCC, 0xFF, 0xCC);
	public static final Color COLOR_FAIL_FOREGROUND = new Color(0x8B, 0x00, 0x00);
	public static final Color COLOR_FAIL_BACKGROUND = new Color(0xFF, 0xD5, 0xFF);
	
	public static final int COLLAPSIBLE_PAD_X = 8;
	public static final int COLLAPSIBLE_PAD_Y = 8;
	public static final int COLLAPSIBLE_BORDER_HORIZONTAL_MARGIN = 32;
	public static final int COLLAPSIBLE_BORDER_VERTICAL_MARGIN = 16;
	public static final Border COLLAPSIBLE_PANE_BORDER = BorderFactory.createEmptyBorder(
			COLLAPSIBLE_BORDER_VERTICAL_MARGIN, 
			COLLAPSIBLE_BORDER_HORIZONTAL_MARGIN, 
			COLLAPSIBLE_BORDER_VERTICAL_MARGIN, 
			COLLAPSIBLE_BORDER_HORIZONTAL_MARGIN);
	
}
