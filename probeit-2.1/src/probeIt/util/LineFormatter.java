package probeIt.util;

public class LineFormatter
{
	private static String formatMultiLineText(String kif, int width, int height)
	{
		String[] lines = kif.split("\n");
		String cleanedKif = "";
		int i;
		for (i = 0; i < lines.length && i < height ; i++)
		{cleanedKif+=formatSingleLineText(lines[i], width) + "\n";}
		
		if(i == height)
			cleanedKif += "more...";
		return cleanedKif;
	}

	public static String formatText(String text, int widthByChars, int heightByLines)
	{
		if(text == null)
		{
			System.out.println("null null nulasdfdddddddddddddddddddddddddddddsfasdf");
			return "NULL TEXT";
		}
		
		String[] lines = text.split("\n");

		if (lines.length > 1)
			return formatMultiLineText(text, widthByChars, heightByLines);

		return formatSingleLineText(text, widthByChars);
	}

	public static String formatTextforHTML(String text, int widthByChars, int heightByLines)
	{
		String formattedText = formatText(text, widthByChars, heightByLines);
		return formattedText.replaceAll("\n", "<br>");
	}
	
	
	public static String formatSingleLineText(String string, int width)
	{
		String formattedText = "";
		if (string.length() > width)
			string = string.substring(0, width);

		for (int i = 0; i < string.length() && i < (width); i++)
		{
			if (i == (width) - 3)
			{
				formattedText = formattedText + " ...";
				break;
			}

			formattedText = formattedText + string.charAt(i);
		}
		return formattedText;
	}
}
