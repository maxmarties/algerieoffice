package com.rinitec.algerieoffice.web.form;

import java.util.Random;

public class ConstraintesForm {
	public static final int SOCIALFOOTER_FORMULE = 1;
	public static final int PRICEPREMIUM_FORMULE = 2;
	
	public static final int LIMIT_MAPSITE = 1000;
	public static final int LIMIT_NEWSLETTER = 2000;
	public static final int LIMIT_EMAILING = 1000;
	
	public static final int COUNT_SECTOR_ACTIITY = 31;
	public static final int COUNT_WILAYA = 48;
	public static final int COUNT_DOMAINES = 11;
	public static final int COUNT_FAMILY_TOPIC = 10;
	
	public static final int MIN_LENGTH_FIELD = 3;
	public static final int MIN_LENGTH_PASSWORD = 6;
	public static final int MIN_LENGTH_DESCRIPTION = 10;
	public static final int MIN_LENGTH_ADRRESS = 5;
	
	public static final int MAX_LENGTH_FIELD = 30;
	public static final int MAX_LENGTH_DENOMINATION = 60;
	public static final int MAX_LENGTH_MINADRRESS = 90;
	public static final int MAX_LENGTH_ADRRESS = 150;
	public static final int MAX_LENGTH_DESCRIPTION = 250;
	public static final int MAX_LENGTH_NOTE = 512;
	public static final int MAX_LENGTH_MESSAGE = 1024;
	
	public static final int MAX_KEYS_BLOG = 20;
	public static final int MIN_COUNT_ANALYSE = 20;
	
	public static final Integer[] PREMIUM_FORMULE = {560, 1120, 2400, 4000};
	
	public static final Integer[] ORDERS_CREDIT = {1000, 2500, 5000, 10000};
	public static final String[] ORDERS_FORMULE = {"2400", "5200", "9600", "16800"};
	public static final Integer[] CAMPAIGNS_CREDIT = {100, 250, 500, 1000};
	public static final String[] CAMPAIGNS_FORMULE = {"2600", "5800", "10400", "18200"};
	public static final Integer[] BUDGET_EMAILS = {10000, 50000, 200000, 1000000};
	public static final Integer[] BUDGET_SENDING = {100, 300, 1000, 5000};
	public static final String[] BUDGET_FORMULE = {"3990", "9220", "18690", "41790"};
	
	public static final int MAX_COUNT_DATA = 1000;
	public static final int[] MAX_KEYSWORD = {4, 8, 12, 16, 20};
	public static final int[] MAX_COUNT_POST = {3, 10, 50, 500, 1000};
	public static final int[] MAX_COUNT_AGENT = {3, 10, 200, 1000, 2500};
	public static final long[] MAX_COUNT_USER = {3L, 10L, 50L, 250L, 500L};
	public static final Long[] MAX_COUNT_SPACE = {30L, 200L, 500L, 2000L, 5000L};
	
	public static final long MAX_NEWSLETTER_CHOSER = 10000L;
	public static final long MAX_EASYLIST = 2000L;
	
	public static final String[] SECTOR_ANALYTICS = {"Type,Build,Capital,Agent", "Type,Month,Briefcase,Region", "Type,Build,Warehouse,Agent", 
			"Type,Month,Capital,Region", "Type,Build,Briefcase,Agent", "Strict,Month,Capital,Effectif", "Warehouse,Briefcase,Build,Region", 
			"Strict,Build,Capital,Effectif"};
	
	public static final String[] WILAYA_ANALYTICS = {"Type,Build,Capital,Agent", "Type,Month,Briefcase,Agent", "Type,Build,Warehouse,Agent", 
			"Type,Month,Capital,Effectif", "Type,Build,Briefcase,Agent", "Strict,Month,Capital,Agent", "Warehouse,Briefcase,Build,Agent", 
			"Strict,Build,Capital,Effectif"};
	
	public static final String[] EXPLORER_PRIMARY_COLORS = {"#2C3F50", "#003961", "#333333", "#2A445D", "#003772", "#212121", "#153F4D", "#5F3A26", "#191F22", 
			"#212E24", "#2C2D42", "#142340", "#2E3F59", "#7E909A", "#051022", "#961819"};
	public static final String[] EXPLORER_SEGOND_COLORS = {"#3DAC4E", "#F27219", "#406285", "#022E5D", "#32A32B", "#AC0D08", "#F1C40F", "#FF8427", "#344144", 
			"#357A2A", "#366776", "#2A9CBF", "#596A7C", "#E9911E", "#E5452B", "#BF211E"};
	public static final String[] EXPLORER_TREEN_COLORS = {"#F0F0F0", "#E1EDF4", "#F0F0F0", "#E6EEF7", "#EBEFF4", "#878787", "rgba(20,62,76,.2)", "rgba(70,26,3,.5)", 
			"rgba(37,48,52,.3)", "rgba(33,46,36,.1)", "rgba(44,45,66,.25)", "rgba(0,0,0,.1)", "rgba(30,45,69,.1)", "rgba(19,33,46,.75)", "#E6EAF6", "rgba(0,0,0,.1)"};
	public static final String[] EXPLORER_MENUBACK_COLORS = {"#FFFFFF", "#FFFFFF", "#FFFFFF", "#DDE4EC", "#FFFFFF", "#212121", "#153F4D", "#5F3A26", "#242F33", 
			"#FFFFFF", "#F0F0F1", "#E6E6E8", "#FFFFFF", "#13212E", "#E6EAF6", "#FFFFFF"};
	public static final String[] EXPLORER_MENU_COLORS = {"#2C3F50", "#0D4B88", "#15355C", "#022E5D", "#002B62", "#D4D4D4", "#FFFFFF", "#FFFFFF", "#DBDCDC", 
			"#212E24", "#191A29", "#142340", "#1E2D45", "#D0D0D0", "#040D1C", "#010101"};
	public static final String[] EXPLORER_MENUHOVER_COLORS = {"#3DAC4E", "#F27219", "#3695D7", "#6C98E1", "#169919", "#FFFFFF", "#F1C40F", "#FF8427", "#FFD900", 
			"#357A2A", "#366776", "#2A9CBF", "#C34745", "#E9911E", "#E5452B", "#BF211E"};
	public static final String[] EXPLORER_POPUPBACK_COLORS = {"#FFFFFF", "#FFFFFF", "#406285", "#DDE4EC", "#EBEFF4", "#FFFFFF", "#FFFFFF", "#5F3A26", "#191F22", 
			"#FFFFFF", "#FFFFFF", "#E6E6E8", "#FFFFFF", "#13212E", "#E6EAF6", "#FFFFFF"};
	public static final String[] EXPLORER_POPUP_COLORS = {"#2C3F50", "#0D4B88", "#F0F0F0", "#022E5D", "#002B62", "#212121", "#100C0B", "#FFFFFF", "#DBDCDC", 
			"#212E24", "#191A29", "#142340", "#1E2D45", "#D0D0D0", "#040D1C", "#010101"};
	public static final String[] EXPLORER_POPUPHOVER_COLORS = {"#3DAC4E", "#F27219", "#FFB600", "#6C98E1", "#32A32B", "#AC0D08", "#153F4D", "#FF8427", "#FFD900", 
			"#357A2A", "#366776", "#2A9CBF", "#C34745", "#E9911E", "#0087EC", "#BF211E"};
	public static final String[] EXPLORER_SHAREDBACK_COLORS = {"#F0F0F0", "#205C96", "#333333", "#2A445D", "#169919", "#5C5C5C", "#BFC3C6", "#461A03", "#191F22", 
			"#212E24", "#CE9A47", "#FFFFFF", "#FFFFFF", "#7E909A", "#0087EC", "#BF211E"};
	public static final String[] EXPLORER_SHARED_COLORS = {"#353C45", "#D2EEFE", "#F0F0F0", "#F0F0F0", "#FFFFFF", "#FFFFFF", "#153F4D", "#E5E5E5", "#DBDCDC", 
			"#E0E0E0", "#2C2D42", "#142340", "#2E3F59", "#13212E", "#E6EAF6", "#FFFFFF"};
	public static final String[] EXPLORER_SHAREDHOVER_COLORS = {"#E5E5E5", "#1C4E80", "#333333", "#546C85", "#43A627", "#AC0D08", "#F1C40F", "#461A03", "#0E0E0E", 
			"#357A2A", "#18353E", "#2A9CBF", "#596A7C", "#9CB0BC", "#026FC0", "#961819"};
	public static final String[] EXPLORER_SHAREDFOCUS_COLORS = {"#2C3F50", "#FFFFFF", "#FFB600", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#153F4D", "#FF8427", "#FFFFFF", 
			"#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#13212E", "#FFFFFF", "#FFFFFF"};
	public static final String[] EXPLORER_TITLE_COLORS = {"#181F1F", "#3D4147", "#171717", "#022E5D", "#003472", "#121212", "#100C0B", "#020202", "#0E0E0E", 
			"#000000", "#191A29", "#142340", "#1E2D45", "#242A38", "#040D1C", "#292929"};
	public static final String[] EXPLORER_TITLEAFTER_COLORS = {"#3DAC4E", "#F27219", "#FFB600", "#6C98E1", "#2CA22B", "#E11C23", "#F1C40F", "#FF8427", "#FFD900", 
			"#357A2A", "#CE9A47", "#2A9CBF", "#F56765", "#242A38", "#E5452B", "#DD2928"};
	public static final String[] EXPLORER_TITLEPRODUCT_COLORS = {"#205C96", "#2B4B88", "#0A2C4E", "#222222", "#2D6EB4", "#002B48", "#153E4D", "#5F3A26", "#1C4E80", 
			"#314A49", "#366776", "#2A9CBF", "#F56765", "#496E83", "#0087EC", "#BF211E"};
	public static final String[] EXPLORER_TITLEHOVER_COLORS = {"#29AC60", "#F27219", "#FFB600", "#6C98E1", "#003472", "#EA3838", "#F1A516", "#DB5E00", "#438AF4", 
			"#357A2A", "#18353E", "#4AC8EE", "#C34745", "#E9911E", "#026FC0", "#961819"};
	public static final String[] EXPLORER_TITLEFROALA_COLORS = {"#17486D", "#2B4B88", "#0A2C4E", "#022E5D", "#1C4E80", "#222222", "#0C2A34", "#461A03", "#1C4E80", 
			"#212E24", "#366776", "#293C61", "#2E3F59", "#7E909A", "#0087EC", "#292929"};
	public static final String[] EXPLORER_PAGE_COLORS = {"#C4DCDF", "#D1E2EF", "#A6A6A6", "#CFD7E2", "#CDD4DD", "#A3A3A3", "#ADC3CA", "#BAAF93", "#7C8183", 
			"#ABABAB", "#90A3A9", "#C0C0C0", "#AFC0C7", "#BEC3C7", "#A2ACBD", "#A9A9A9"};
	public static final String[] EXPLORER_TOP_COLORS = {"#E5E5E5", "#E5E5E5", "#E5E5E5", "#E6E6E6", "#E6E6E6", "#E2E2E2", "#F8F8F8", "#E8E8E8", "#EFE5E5", 
			"#EFE5E5", "#E1E1E2", "#E1E1E2", "#E1E1E2", "#E8E8F0", "#E2E2E0", "#F0F0F0"};
	public static final String[] EXPLORER_TEXTE_COLORS = {"#353C45", "#202020", "#171717", "#343434", "#262626", "#424242", "#242121", "#373737", "#2E383A", 
			"#202020", "#353C45", "#262626", "#383843", "#202020", "#202020", "#4F4F52"};
	public static final String[] EXPLORER_SELECT_COLORS = {"#205C96", "#205C96", "#3695D7", "#3497DB", "#0091D5", "#15355C", "#205C96", "#20283E", "#40A2D9", 
			"#205C96", "#1C4E80", "#1F72E6", "#34495E", "#2C63A2", "#0087EC", "#2C63A2"};
	public static final String[] EXPLORER_BUTTON_COLORS = {"#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF", 
			"#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#13212E", "#E6EAF6", "#E6EAF6"};
	public static final String[] EXPLORER_HELP_COLORS = {"#545E74", "#4E596F", "#4B4B52", "#525252", "#545E74", "#565656", "#646E82", "#656565", "#545E74", 
			"#545E74", "#545E74", "#465461", "#465461", "#686C73", "#434A57", "#686C73"};
	public static final String[] EXPLORER_INPUT_COLORS = {"#182640", "#182640", "#111111", "#121212", "#242A38", "#060F18", "#060F18", "#272727", "#2D6EB4", 
			"#242A38", "#242A38", "#22242A", "#121212", "#244163", "#000313", "#2E353d"};
	public static final String[] EXPLORER_ICON_COLORS = {"#20283E", "#353C45", "#242A38", "#242A38", "#314A49", "#212121", "#212121", "#322C1E", "#292729", 
			"#292729", "#292729", "#302E30", "#302E30", "#7F848D", "#3C4C5B", "#202430"};
	public static final String[] EXPLORER_BORDER_COLORS = {"#C3C7CA", "#BEC3C7", "#DADADA", "#DADADA", "#577CA3", "#7E909A", "#A2ACB4", "#CFCCCC", "#C3C7CA", 
			"#C3C7CA", "#C3C7CA", "#C3C7CA", "#C3C7CA", "#C3C7CA", "#C3C7CA", "#C3C7CA"};
	public static final String[] EXPLORER_ALERTE_COLORS = {"#F3F4F8", "#C8DCEA", "#EAEAEA", "#B4CEEE", "#C2C8CF", "#BEC3C7", "#C2D6DC", "#EEE1C1", "#F3F4F8", 
			"#C2C8CF", "#C2C8CF", "#DDE5EB", "#DDE5EB", "#494D54", "#BCC4DB", "#C2C8CF"};
	public static final String[] EXPLORER_FOOTER_COLORS = {"#E5E5E5", "#C8C8C8", "#D4D4D4", "#E5E5E5", "#E5E5E5", "#B0B0B0", "#D4D4D4", "#E0E0E0", "#CBCBCB", 
			"#C8C8C8", "#D0D0D0", "#BBCAD4", "#BBCAD4", "#202020", "#C4C5CA", "#E9C7D5"};
	public static final String[] EXPLORER_FAVORITE_COLORS = {"#C3C7CA", "#C4C4C4", "#C8C8C8", "#C8C8C8", "#C8C8C8", "#B8B8B8", "#D0D0D0", "#C8C8C8", "#C3C7CA", 
			"#A0A0A0", "#B0A0A0", "#D3DDE3", "#D8D8E1", "#4E596F", "#BCC4DB", "#CDC7C9"};
	public static final String[] EXPLORER_RED_COLORS = {"#9B2402", "#CD2721", "#C3402F", "#C3402F", "#D32D41", "#D2321E", "#E16070", "#A02F34", "#9B2402", 
			"#EA3838", "#A02F34", "#C3402F", "#C3402F", "#F05455", "#C93720", "#EA3838"};
	public static final String[] EXPLORER_BLUE_COLORS = {"#0D326A", "#163466", "#294690", "#294690", "#4DBCD7", "#40A2D9", "#4DBCD7", "#0091D5", "#0D326A", 
			"#0091D5", "#2D44C5", "#1C4E80", "#1C4E80", "#57ACDF", "#026FC0", "#4CB5F5"};
	public static final String[] EXPLORER_YELLOW_COLORS = {"#F6BD25", "#F1C40F", "#E9911E", "#FFE72A", "#FFD400", "#AC0D08", "#F1C40F", "#E9911E", "#F6BD25", 
			"#F6BD25", "#E9911E", "#B3C100", "#F6BD25", "#1F1E2E", "#F6BD25", "#FFFFFF"};
	public static final String[] EXPLORER_SHADOW1_COLORS = {"#1E2E26", "#1E2E26", "#1E2E26", "#4E4E46", "#1E2E26", "#221E1E", "#061318", "#000000", "#000000", 
			"#000000", "#000000", "#1E2E26", "#1E2E26", "#000000", "#000000", "#000000"};
	public static final String[] EXPLORER_SHADOW2_COLORS = {"rgba(30,46,38,0)", "rgba(30,46,38,0)", "rgba(30,46,38,0)", "rgba(30,46,38,0)", "rgba(30,46,38,0)", 
			"rgba(34,30,30,0)", "rgba(6,19,29,0)", "rgba(0,0,0,0)", "rgba(0,0,0,0)", "rgba(0,0,0,0)", "rgba(0,0,0,0)", "rgba(30,46,38,0)", "rgba(30,46,38,0)", 
			"rgba(0,0,0,0)", "rgba(0,0,0,0)", "rgba(0,0,0,0)"};
	public static final String[] EXPLORER_SHADOW3_COLORS = {"rgba(30,46,38,0.14)", "rgba(30,46,38,0.14)", "rgba(30,46,38,0.14)", "rgba(30,46,38,0.14)", "rgba(30,46,38,0.14)", 
			"rgba(34,30,30,0.14)", "rgba(6,19,29,0.14)", "rgba(0,0,0,0.14)", "rgba(0,0,0,0.14)", "rgba(0,0,0,0.14)", "rgba(0,0,0,0.14)", "rgba(30,46,38,0.14)", 
			"rgba(30,46,38,0.14)", "rgba(0,0,0,0.14)", "rgba(0,0,0,0.14)", "rgba(0,0,0,0.14)"};
	public static final String[] EXPLORER_SEPARATOR1_COLORS = {"rgba(0,0,0,.05)", "rgba(0,0,0,.05)", "rgba(0,0,0,.05)", "rgba(0,0,0,.05)", "rgba(0,0,0,.05)", 
			"rgba(0,0,0,.07)", "rgba(0,0,0,.05)", "rgba(0,0,0,.05)", "rgba(0,0,0,.05)", "rgba(0,0,0,.05)", "rgba(0,0,0,.05)", "rgba(0,0,0,.05)", "rgba(0,0,0,.05)", 
			"rgba(0,0,0,.05)", "rgba(0,0,0,.04)", "rgba(0,0,0,.04)"};
	public static final String[] EXPLORER_SEPARATOR2_COLORS = {"rgba(0,0,0,.08)", "rgba(0,0,0,.08)", "rgba(0,0,0,.08)", "rgba(0,0,0,.08)", "rgba(0,0,0,.08)", 
			"rgba(0,0,0,.1)", "rgba(0,0,0,.08)", "rgba(0,0,0,.08)", "rgba(0,0,0,.08)", "rgba(0,0,0,.08)", "rgba(0,0,0,.08)", "rgba(0,0,0,.08)", "rgba(0,0,0,.08)", 
			"rgba(0,0,0,.08)", "rgba(0,0,0,.06)", "rgba(0,0,0,.06)"};
	public static final String[] EXPLORER_SEPARATOR3_COLORS = {"rgba(0,0,0,.12)", "rgba(0,0,0,.12)", "rgba(0,0,0,.12)", "rgba(0,0,0,.12)", "rgba(0,0,0,.12)", 
			"rgba(0,0,0,.14)", "rgba(0,0,0,.12)", "rgba(0,0,0,.12)", "rgba(0,0,0,.12)", "rgba(0,0,0,.12)", "rgba(0,0,0,.12)", "rgba(0,0,0,.12)", "rgba(0,0,0,.12)", 
			"rgba(0,0,0,.12)", "rgba(0,0,0,.1)", "rgba(0,0,0,.1)"};
	public static final String[] EXPLORER_SEPARATOR4_COLORS = {"rgba(38,64,76,.24)", "rgba(38,64,76,.24)", "rgba(38,64,76,.24)", "rgba(38,64,76,.24)", "rgba(38,64,76,.24)", 
			"rgba(38,64,76,.26)", "rgba(38,64,76,.24)", "rgba(38,64,76,.24)", "rgba(38,64,76,.24)", "rgba(38,64,76,.24)", "rgba(38,64,76,.24)", "rgba(38,64,76,.24)", 
			"rgba(38,64,76,.24)", "rgba(38,64,76,.24)", "rgba(38,64,76,.2)", "rgba(38,64,76,.2)"};
	public static final String[] EXPLORER_OVERLAY_COLORS = {"rgba(36,42,56,.43)", "rgba(24,38,64,.43)", "rgba(24,38,64,.53)", "rgba(24,38,64,.53)", "rgba(10,25,43,.53)", 
			"rgba(25,8,8,.53)", "rgba(12,40,50,.53)", "rgba(70,26,3,.53)", "rgba(38,52,55,.43)", "rgba(28,90,18,.43)", "rgba(24,53,62,.43)", "rgba(13,135,172,.43)", 
			"rgba(23,43,65,.43)", "rgba(0,0,0,.43)", "rgba(4,13,28,.43)", "rgba(0,0,0,.6)"};
	public static final String[] EXPLORER_OVERLAY9_COLORS = {"rgba(36,42,56,.9)", "rgba(24,38,64,.9)", "rgba(24,38,64,.9)", "rgba(24,38,64,.9)", "rgba(10,25,43,.9)", 
			"rgba(25,8,8,.9)", "rgba(12,40,50,.9)", "rgba(70,26,3,.9)", "rgba(38,52,55,.9)", "rgba(28,90,18,.9)", "rgba(24,53,62,.9)", "rgba(13,135,172,.9)", 
			"rgba(23,43,65,.9)", "rgba(0,0,0,.9)", "rgba(4,13,28,.9)", "rgba(0,0,0,.9)"};
	public static final String[] EXPLORER_DARK_COLORS = {"rgba(0,0,0,.25)", "rgba(0,0,0,.25)", "rgba(0,0,0,.25)", "rgba(0,0,0,.25)", "rgba(0,0,0,.25)", 
			"rgba(0,0,0,.25)", "rgba(0,0,0,.25)", "rgba(0,0,0,.25)", "rgba(0,0,0,.25)", "rgba(0,0,0,.25)", "rgba(0,0,0,.25)", "rgba(0,0,0,.25)", 
			"rgba(0,0,0,.25)", "rgba(0,0,0,.25)", "rgba(0,0,0,.25)", "rgba(0,0,0,.25)"};
	public static final String[] EXPLORER_LIGHT1_COLORS = {"rgba(255,255,255,.08)", "rgba(255,255,255,.08)", "rgba(255,255,255,.08)", "rgba(255,255,255,.08)", "rgba(255,255,255,.08)", 
			"rgba(255,255,255,.08)", "rgba(255,255,255,.1)", "rgba(255,255,255,.1)", "rgba(255,255,255,.08)", "rgba(255,255,255,.08)", "rgba(255,255,255,.04)", 
			"rgba(255,255,255,.08)", "rgba(255,255,255,.08)", "rgba(255,255,255,.08)", "rgba(255,255,255,.05)", "rgba(255,255,255,.05)"};
	public static final String[] EXPLORER_LIGHT2_COLORS = {"rgba(255,255,255,.25)", "rgba(255,255,255,.25)", "rgba(255,255,255,.25)", "rgba(255,255,255,.25)", "rgba(255,255,255,.25)", 
			"rgba(255,255,255,.25)", "rgba(255,255,255,.27)", "rgba(255,255,255,.27)", "rgba(255,255,255,.25)", "rgba(255,255,255,.25)", "rgba(255,255,255,.15)", 
			"rgba(255,255,255,.25)", "rgba(255,255,255,.25)", "rgba(255,255,255,.25)", "rgba(255,255,255,.2)", "rgba(255,255,255,.2)"};
	public static final String[] EXPLORER_LIGHT3_COLORS = {"rgba(255,255,255,.5)", "rgba(255,255,255,.5)", "rgba(255,255,255,.5)", "rgba(255,255,255,.5)", "rgba(255,255,255,.5)", 
			"rgba(255,255,255,.5)", "rgba(255,255,255,.52)", "rgba(255,255,255,.52)", "rgba(255,255,255,.5)", "rgba(255,255,255,.5)", "rgba(255,255,255,.34)", 
			"rgba(255,255,255,.5)", "rgba(255,255,255,.5)", "rgba(255,255,255,.5)", "rgba(255,255,255,.4)", "rgba(255,255,255,.4)"};
	public static final String[] EXPLORER_PRIMARY1_COLORS = {"#34495E", "#0F5194", "#5A5A5A", "#6C98E1", "#005092", "#4A4141", "#153F4D", "#5F3A26", "#242F33", 
			"#212E24", "#2C2D42", "#293C61", "#2E3F59", "#9CB0BC", "#0087EC", "#372D2D"};
	public static final String[] EXPLORER_PRIMARY2_COLORS = {"#242A38", "#0C437A", "#1A1A1A", "#567AB5", "#012A5F", "#222222", "#0C2A34", "#461A03", "#0E0E0E", 
			"#07170A", "#191A29", "#142340", "#1E2D45", "#7E909A", "#026FC0", "#030104"};
	public static final String[] EXPLORER_SEGOND1_COLORS = {"#3DAC4E", "#FF8C1A", "#3695d7", "#1C4E80", "#6DB121", "#E11B22", "#F1C40F", "#FF8427", "#FFD900", 
			"#357A2A", "#366776", "#4AC8EE", "#F56765", "#F6BD25", "#E5452B", "#372D2D"};
	public static final String[] EXPLORER_SEGOND2_COLORS = {"#178143", "#E65C17", "#406285", "#0A2C4E", "#2CA32C", "#AC0D08", "#F1A516", "#DB5E00", "#F6BD25", 
			"#136107", "#18353E", "#2A9CBF", "#C34745", "#E9911E", "#C93720", "#030104"};
	
	public static final String[] DASHBOARD_BLACK_COLORS = {"#000000", "#000000", "#FFFFFF"};
	public static final String[] DASHBOARD_WHITE_COLORS = {"#FFFFFF", "#FFFFFF", "#060F18"};
	public static final String[] DASHBOARD_PRIMARY_COLORS = {"#2C3F50", "#158526", "#01070E"};
	public static final String[] DASHBOARD_SEGOND_COLORS = {"#3DAC4E", "#212E3A", "#248432"};
	public static final String[] DASHBOARD_PAGE_COLORS = {"#F1F1F1", "#FFFFFF", "#0A192B"};
	public static final String[] DASHBOARD_SCREEN_COLORS = {"#C4DCDF", "#DEE0E7", "#0A192B"};
	public static final String[] DASHBOARD_MENU_COLORS = {"#FFFFFF", "#FFFFFF", "#0F2136"};
	public static final String[] DASHBOARD_SIDEBAR_COLORS = {"#2C3F50", "#2D8F3C", "#030D19"};
	public static final String[] DASHBOARD_ASIDE_COLORS = {"#C4DCDF", "#DAE8EA", "#4E596F"};
	public static final String[] DASHBOARD_TRIGGER_COLORS = {"#EEEEEE", "#F6F0F0", "#DADADA"};
	public static final String[] DASHBOARD_TOP_COLORS = {"#E5E5E5", "#DADADA", "rgba(240,240,240,.5)"};
	public static final String[] DASHBOARD_LIGHT_COLORS = {"#F8F8F8", "#F4F4F4", "#071322"};
	public static final String[] DASHBOARD_TITLE_COLORS = {"#181F1F", "#17486D", "#B7CBD8"};
	public static final String[] DASHBOARD_HEADING_COLORS = {"#17486D", "#15355C", "#79A1BC"};
	public static final String[] DASHBOARD_HEADER_COLORS = {"#205C96", "#2C3F50", "#57ACDF"};
	public static final String[] DASHBOARD_TEXT_COLORS = {"#353C45", "#242A38", "#A5A8AA"};
	public static final String[] DASHBOARD_LABEL_COLORS = {"#182640", "#0A192B", "#BEC3C7"};
	public static final String[] DASHBOARD_HELP_COLORS = {"#545E74", "#4A596F", "#4B5468"};
	public static final String[] DASHBOARD_INPUT_COLORS = {"#222222", "#050C16", "#E6E8E3"};
	public static final String[] DASHBOARD_BORDER_COLORS = {"#C3C7CA", "#B5B9BD", "#233240"};
	public static final String[] DASHBOARD_SELECT_COLORS = {"#1757B8", "#172A5C", "#7488A0"};
	public static final String[] DASHBOARD_POPUP_COLORS = {"#205C96", "#052C53", "#2C63A2"};
	public static final String[] DASHBOARD_DISBALED_COLORS = {"#DADADA", "#DDDEDF", "#24282D"};
	public static final String[] DASHBOARD_BUTTON_COLORS = {"#FFFFFF", "#FFFFFF", "#FFFFFF"};
	public static final String[] DASHBOARD_ICON_COLORS = {"#20283E", "#181F1F", "#717884"};
	public static final String[] DASHBOARD_TABLE_COLORS = {"#F8F8F8", "#F8F8F8", "#C2C8CF"};
	public static final String[] DASHBOARD_TRICK_COLORS = {"#555A62", "#0A527A", "#10858A"};
	public static final String[] DASHBOARD_LINK_COLORS = {"#2C3F50", "#0C364E", "#BEC3C7"};
	public static final String[] DASHBOARD_HLINK_COLORS = {"#2C3F50", "#163466", "#248432"};
	public static final String[] DASHBOARD_WIZARD_COLORS = {"#7E909A", "#97A5AD", "#0C3247"};
	public static final String[] DASHBOARD_ALERT_COLORS = {"#C2C8CF", "#D6DBE0", "#1F3244"};
	public static final String[] DASHBOARD_NOTIF_COLORS = {"#F0F0F0", "#E6E7EC", "#282F35"};
	public static final String[] DASHBOARD_ERROR_COLORS = {"#E74C3C", "#C93223", "#CC3E4A"};
	public static final String[] DASHBOARD_SUPPORT_COLORS = {"#E8E8E8", "#DCE6E7", "#050C16"};
	public static final String[] DASHBOARD_RED_COLORS = {"#C1392B", "#AE4237", "#A22A22"};
	public static final String[] DASHBOARD_GREEN_COLORS = {"#29AC60", "#319d5E", "#065828"};
	public static final String[] DASHBOARD_YELLOW_COLORS = {"#F6BD25", "#E6B430", "#E7B121"};
	public static final String[] DASHBOARD_BLUE_COLORS = {"#2D6EB4", "#215EA0", "#104FA6"};
	public static final String[] DASHBOARD_INFO_COLORS = {"#E9F7F8", "#AEDDF9", "#04284A"};
	public static final String[] DASHBOARD_IMPORT_COLORS = {"#FDF7E1", "#F6EDCE", "rgba(152,79,8,.2)"};
	public static final String[] DASHBOARD_PRIMARY1_COLORS = {"#34495E", "#243A51", "#2F4158"};
	public static final String[] DASHBOARD_PRIMARY2_COLORS = {"#242A38", "#0B1B2C", "#1D2834"};
	public static final String[] DASHBOARD_SEGOND1_COLORS = {"#3DAC4E", "#369D45", "#21802F"};
	public static final String[] DASHBOARD_SEGOND2_COLORS = {"#178143", "#1D6828", "#064E10"};
	public static final String[] DASHBOARD_SUCCESS1_COLORS = {"#29AC60", "#20A056", "#205737"};
	public static final String[] DASHBOARD_SUCCESS2_COLORS = {"#188144", "#126F39", "#173A25"};
	public static final String[] DASHBOARD_DANGER1_COLORS = {"#D32D41", "#D62C2C", "#D2321E"};
	public static final String[] DASHBOARD_DANGER2_COLORS = {"#A02F34", "#9F1616", "#7C1003"};
	public static final String[] DASHBOARD_ACTION1_COLORS = {"#3377C9", "#195CAC", "#3377C9"};
	public static final String[] DASHBOARD_ACTION2_COLORS = {"#225695", "#062D5C", "#154783"};
	public static final String[] DASHBOARD_WARNING1_COLORS = {"#F6BD25", "#E4AD1B", "#F1961E"};
	public static final String[] DASHBOARD_WARNING2_COLORS = {"#E9911E", "#D9800C", "#B96F0F"};
	public static final String[] DASHBOARD_LIGHT06_COLORS = {"rgba(255,255,255,.06)", "rgba(255,255,255,.1)", "rgba(76,106,136,.06)"};
	public static final String[] DASHBOARD_LIGHT1_COLORS = {"rgba(255,255,255,.1)", "rgba(255,255,255,.14)", "rgba(78,89,111,.14)"};
	public static final String[] DASHBOARD_LIGHT25_COLORS = {"rgba(255,255,255,.25)", "rgba(255,255,255,.3)", "rgba(0,0,0,.25)"};
	public static final String[] DASHBOARD_LIGHT75_COLORS = {"rgba(255,255,255,.75)", "rgba(255,255,255,.8)", "rgba(0,0,0,.75)"};
	public static final String[] DASHBOARD_DARK04_COLORS = {"rgba(0,0,0,.04)", "rgba(0,0,0,.06)", "rgba(207,216,224,.04)"};
	public static final String[] DASHBOARD_DARK06_COLORS = {"rgba(0,0,0,.08)", "rgba(0,0,0,.12)", "rgba(207,216,224,.08)"};
	public static final String[] DASHBOARD_DARK1_COLORS = {"rgba(0,0,0,.1)", "rgba(0,0,0,.14)", "rgba(161,192,223,.1)"};
	public static final String[] DASHBOARD_DARK14_COLORS = {"rgba(0,0,0,.14)", "rgba(0,0,0,.2)", "rgba(161,192,223,.14)"};
	public static final String[] DASHBOARD_DARK25_COLORS = {"rgba(0,0,0,.25)", "rgba(0,0,0,.3)", "rgba(161,192,223,.25)"};
	public static final String[] DASHBOARD_DARK5_COLORS = {"rgba(0,0,0,.5)", "rgba(0,0,0,.6)", "rgba(161,192,223,.5)"};
	public static final String[] DASHBOARD_DARK8_COLORS = {"rgba(0,0,0,.8)", "rgba(0,0,0,.85)", "rgba(161,192,223,.8)"};
	public static final String[] DASHBOARD_SHADOW1_COLORS = {"#1E2E26", "#121C17", "rgba(255,255,255,.4)"};
	public static final String[] DASHBOARD_SHADOW2_COLORS = {"rgba(30,46,38,0)", "rgba(30,46,38,0)", "rgba(255,255,255,0)"};
	public static final String[] DASHBOARD_SHADOW3_COLORS = {"rgba(36,42,56,.43)", "rgba(36,42,56,.43)", "rgba(36,42,56,.43)"};
	public static final String[] DASHBOARD_SHADOW4_COLORS = {"rgba(36,42,56,.9)", "rgba(36,42,56,.9)", "rgba(36,42,56,.9)"};
	public static final String[] DASHBOARD_FOOTER_COLORS = {"#C0C0C0", "#B8B8B8", "rgba(240,240,240,.5)"};
	public static final String[] DASHBOARD_BACKFOOTER_COLORS = {"#2C3F50", "#212E3A", "#0F2136"};
	
	public static final String[] getSectorAnalytics() {
		final Random random = new Random();
		return SECTOR_ANALYTICS[random.nextInt(SECTOR_ANALYTICS.length)].split(",");
	}
	
	public static final String[] getWilayaAnalytics() {
		final Random random = new Random();
		return WILAYA_ANALYTICS[random.nextInt(WILAYA_ANALYTICS.length)].split(",");
	}
	
	public static final String getDashboardColor(final int color, final int index) {
		switch(color) {
		case 1: return DASHBOARD_BLACK_COLORS[index];
		case 2: return DASHBOARD_WHITE_COLORS[index];
		case 3: return DASHBOARD_PRIMARY_COLORS[index];
		case 4: return DASHBOARD_SEGOND_COLORS[index];
		case 5: return DASHBOARD_PAGE_COLORS[index];
		case 6: return DASHBOARD_SCREEN_COLORS[index];
		case 7: return DASHBOARD_MENU_COLORS[index];
		case 8: return DASHBOARD_SIDEBAR_COLORS[index];
		case 9: return DASHBOARD_ASIDE_COLORS[index];
		case 10: return DASHBOARD_TRIGGER_COLORS[index];
		case 11: return DASHBOARD_TOP_COLORS[index];
		case 12: return DASHBOARD_LIGHT_COLORS[index];
		case 13: return DASHBOARD_TITLE_COLORS[index];
		case 14: return DASHBOARD_HEADING_COLORS[index];
		case 15: return DASHBOARD_HEADER_COLORS[index];
		case 16: return DASHBOARD_TEXT_COLORS[index];
		case 17: return DASHBOARD_LABEL_COLORS[index];
		case 18: return DASHBOARD_HELP_COLORS[index];
		case 19: return DASHBOARD_INPUT_COLORS[index];
		case 20: return DASHBOARD_BORDER_COLORS[index];
		case 21: return DASHBOARD_SELECT_COLORS[index];
		case 22: return DASHBOARD_POPUP_COLORS[index];
		case 23: return DASHBOARD_DISBALED_COLORS[index];
		case 24: return DASHBOARD_BUTTON_COLORS[index];
		case 25: return DASHBOARD_ICON_COLORS[index];
		case 26: return DASHBOARD_TABLE_COLORS[index];
		case 27: return DASHBOARD_TRICK_COLORS[index];
		case 28: return DASHBOARD_LINK_COLORS[index];
		case 29: return DASHBOARD_HLINK_COLORS[index];
		case 30: return DASHBOARD_WIZARD_COLORS[index];
		case 31: return DASHBOARD_ALERT_COLORS[index];
		case 32: return DASHBOARD_NOTIF_COLORS[index];
		case 33: return DASHBOARD_ERROR_COLORS[index];
		case 34: return DASHBOARD_SUPPORT_COLORS[index];
		case 35: return DASHBOARD_RED_COLORS[index];
		case 36: return DASHBOARD_GREEN_COLORS[index];
		case 37: return DASHBOARD_YELLOW_COLORS[index];
		case 38: return DASHBOARD_BLUE_COLORS[index];
		case 39: return DASHBOARD_INFO_COLORS[index];
		case 40: return DASHBOARD_IMPORT_COLORS[index];
		case 41: return DASHBOARD_PRIMARY1_COLORS[index];
		case 42: return DASHBOARD_PRIMARY2_COLORS[index];
		case 43: return DASHBOARD_SEGOND1_COLORS[index];
		case 44: return DASHBOARD_SEGOND2_COLORS[index];
		case 45: return DASHBOARD_SUCCESS1_COLORS[index];
		case 46: return DASHBOARD_SUCCESS2_COLORS[index];
		case 47: return DASHBOARD_DANGER1_COLORS[index];
		case 48: return DASHBOARD_DANGER2_COLORS[index];
		case 49: return DASHBOARD_ACTION1_COLORS[index];
		case 50: return DASHBOARD_ACTION2_COLORS[index];
		case 51: return DASHBOARD_WARNING1_COLORS[index];
		case 52: return DASHBOARD_WARNING2_COLORS[index];
		case 53: return DASHBOARD_LIGHT06_COLORS[index];
		case 54: return DASHBOARD_LIGHT1_COLORS[index];
		case 55: return DASHBOARD_LIGHT25_COLORS[index];
		case 56: return DASHBOARD_LIGHT75_COLORS[index];
		case 57: return DASHBOARD_DARK04_COLORS[index];
		case 58: return DASHBOARD_DARK06_COLORS[index];
		case 59: return DASHBOARD_DARK1_COLORS[index];
		case 60: return DASHBOARD_DARK14_COLORS[index];
		case 61: return DASHBOARD_DARK25_COLORS[index];
		case 62: return DASHBOARD_DARK5_COLORS[index];
		case 63: return DASHBOARD_DARK8_COLORS[index];
		case 64: return DASHBOARD_SHADOW1_COLORS[index];
		case 65: return DASHBOARD_SHADOW2_COLORS[index];
		case 66: return DASHBOARD_SHADOW3_COLORS[index];
		case 67: return DASHBOARD_SHADOW4_COLORS[index];
		case 68: return DASHBOARD_FOOTER_COLORS[index];
		case 69: return DASHBOARD_BACKFOOTER_COLORS[index];
		}
		return "#FFFFFF";
	}
	
}
