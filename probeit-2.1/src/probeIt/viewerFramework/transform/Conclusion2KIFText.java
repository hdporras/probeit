package probeIt.viewerFramework.transform;

/*
import java.text.Format;
import jtp.fol.CNFSentence;
import jtp.fol.kif.KIF2CNF;
import jtp.fol.parser.FirstOrderLogicParser;
import jtp.fol.parser.ParserBasedTranslator;
import jtp.ui.rs.KIFSentenceFormat;
import jtp.ui.rs.KIFSentenceFormatPlain;
import java.util.ArrayList;
*/

import pml.PMLQuery;
import pml.impl.serializable.PMLConclusion;
public class Conclusion2KIFText implements ConclusionTransformer,QueryTransformer
{
	/*
	public static Format _KIFFormat = new KIFSentenceFormat();
	public static Format _PlainKIFFormat = new KIFSentenceFormatPlain();
	//public static Format _EnglishFormat = new iw.model.rendering.trans.CompleteEnglishSentenceFormat();
	protected static FirstOrderLogicParser _KIFparser = new KIF2CNF();
	protected static ParserBasedTranslator _translator = new ParserBasedTranslator();
	*/
	
	public String getName()
	{return "KIF-Transformer";}

	public Object transform(PMLConclusion conclusion)
	{
		return conclusion.getStringConclusion();
		/*
		String stringConclusion = conclusion.getStringConclusion();
		String htmlKif = renderToHTML(stringConclusion, IWBase.FORMAT_KIF);
		KifIndentation indentedKif = new KifIndentation(htmlKif);
		return indentedKif.getIndentedKif();*/
	}
	
	public String transformQuery(PMLQuery query)
	{
		return query.getContent();
		/*
		String stringConclusion = conclusion.getStringConclusion();
		String htmlKif = renderToHTML(stringConclusion, IWBase.FORMAT_KIF);
		KifIndentation indentedKif = new KifIndentation(htmlKif);
		return indentedKif.getIndentedKif();*/
	}
	
	
	/*
	private String renderToHTML(String sentence, String toFormat)
	{
		String result = "";
		Format resultFormat = null; // format for toFormat
		// System.out.println("KIFRenderer.renderString: sentence="+sentence+",
		// format="+toFormat);
		if (sentence != null)
		{
			if (toFormat.equalsIgnoreCase(IWBase.FORMAT_RAW))
			{

				result = sentence;
				// System.out.println("KIFRenderer.renderString: format raw.
				// result="+result);
			} else
			{
				// set format, anything other than kif, set to english,
				// including for pretty names
				resultFormat = _EnglishFormat;
				if (toFormat.equalsIgnoreCase(IWBase.FORMAT_KIF))
				{
					resultFormat = _KIFFormat;
				}

				sentence = ProofUtils.wrapSpecialChars(sentence); // "("
				// and
				// ")"
				// with
				// space
				// System.out.println("KIFRenderer: after specialchars, sentence
				// = "+sentence);

				if (toFormat.equalsIgnoreCase(IWBase.FORMAT_ENGLISH))
				{
					if (HasTimeSliceh(sentence))
					{
						sentence = TimeSliceToAllanh(sentence);
					}
					if (HasTimeSlicea(sentence))
					{
						sentence = TimeSliceToAllana(sentence);
					}
					if (HasTimeSlicehStar(sentence))
					{
						sentence = TimeSliceToAllanhStar(sentence);
					}

					if (HasCotemporal(sentence))
					{
						sentence = FormatCotemporal(sentence);
					}
				}
				// System.out.println("KIFRenderer: after timeslice, sentence =
				// "+sentence);

				CNFSentence _cnf = null;

				StringBuffer _sb = new StringBuffer();
				_translator.setParser(_KIFparser);

				try
				{
					_cnf = _translator.translate(sentence);
				} catch (Exception _e)
				{
					System.out
							.print("KIFRenderer.renderToHTMLSegment: sentence translation exception.\n"
									+ _e.getMessage());
					// return sentence;
					result = sentence;
				}

				if (_cnf != null)
				{
					// System.out.println("KIFRenderer: after translator,
					// sentence = "+sentence);
					try
					{
						resultFormat.format(_cnf, _sb, null);
						if (resultFormat == _EnglishFormat)
						{
							while ((_sb.indexOf("(") > -1)
									&& (_sb.indexOf(")") > -1))
							{
								StringBuffer tmp = new StringBuffer()
										.append(TranslationHelper(_sb,
												resultFormat));
								_sb = tmp;
							}

							_sb = new StringBuffer(_sb.toString().replaceAll(
									"\\.", "")
									+ ".");
						}

						// return _sb.toString();
						result = _sb.toString();
						// System.out.println("KIFRenderer: after format,
						// sentence = "+_sb.toString());
					}

					catch (Exception _e)
					{
						if ((sentence.startsWith("(not (|Ab|"))
								|| (sentence.startsWith("( not (|Ab|")))
						{
							StringBuffer aBKIFSentenceStringBuffer = new StringBuffer()
									.append(sentence.substring(11, sentence
											.indexOf(")") + 1));
							// System.out.println("Second call to Xhelper");
							sentence = "Typically, "
									+ TranslationHelper(
											aBKIFSentenceStringBuffer,
											resultFormat) + " on ?when.";
						}

						else if ((sentence.startsWith("(or (not"))
								|| (sentence.startsWith("( or ( not")))
						{
							StringBuffer holdsStarFragment = GetHoldsStarFragment(sentence);
							// System.out.println("holdsStarFragment: " +
							// holdsStarFragment);

							StringBuffer notPropagatedAbFragment = GetNotPropagatedAbFragment(sentence);
							// System.out.println("notPropagatedAbFragment:
							// " + notPropagatedAbFragment);

							StringBuffer holdsFragment = GetHoldsFragment(sentence);
							// System.out.println("holdsFragment: " +
							// holdsFragment);

			
							sentence = "When "
									+ TranslationHelper(holdsStarFragment,
											resultFormat)
									+ " and Typically "
									+ TranslationHelper(
											notPropagatedAbFragment,
											resultFormat)
									+ ", then "
									+ TranslationHelper(holdsFragment,
											resultFormat);
						}

						sentence = sentence.replaceAll("\\.", "") + ".";
						result = sentence;
					}
				}
			}
		} else
		{
			result = "Sentence null.";
		}
		result = RenderingUtils.StripNamespace(result);
		return result;
	}*/

	/*
	private static String TranslationHelper(StringBuffer sb, Format resultFormat)
	{
		String innerKIFSentence = sb.toString().substring(
				sb.toString().indexOf("("), sb.toString().indexOf(")") + 1);
		String pre = sb.toString().substring(0, sb.toString().indexOf("("));
		String post = sb.toString().substring(sb.toString().indexOf(")") + 1);
		StringBuffer innersb = new StringBuffer();

		CNFSentence innercnf = null;

		_translator.setParser(_KIFparser);

		// innerKIFSentence = ProofUtils.wrapSpecialChars(innerKIFSentence); //
		// "("
		// and ")" with spaces.

		try
		{
			innercnf = _translator.translate(innerKIFSentence);
		} catch (Exception _e)
		{
		}

		if (innercnf != null)
		{
			try
			{
				resultFormat.format(innercnf, innersb, null);
			} catch (Exception e)
			{
			}
		}

		return pre + " " + innersb.toString() + " " + post;
	}

	private static StringBuffer GetHoldsFragment(String sentence)
	{
		StringBuffer buf = new StringBuffer();
		int startIndex = sentence.indexOf("(|Holds| ");
		int endIndex = sentence.indexOf("?t)", startIndex) + 2;
		return buf.append(sentence.substring(startIndex, endIndex + 1));
	}

	private static StringBuffer GetNotPropagatedAbFragment(String sentence)
	{
		StringBuffer buf = new StringBuffer();
		int startIndex = sentence.indexOf("(|Ab| ") + 6;
		int endIndex = sentence.indexOf("?t)", startIndex) + 2;
		return buf.append("(|Holds| "
				+ sentence.substring(startIndex, endIndex + 1));
	}

	private static StringBuffer GetHoldsStarFragment(String sentence)
	{
		StringBuffer buf = new StringBuffer();
		int startIndex = -1;
		int endIndex = -1;
		int paranCount = 0;

		startIndex = sentence.indexOf("(|Holds*| ");

		for (int i = startIndex + 1; i < sentence.length(); i++)
		{
			if (sentence.charAt(i) == '(')
				paranCount++;
			if (sentence.charAt(i) == ')')
			{
				if (paranCount == 0)
				{
					endIndex = i;
					break;
				} else
					paranCount--;
			}
		}
		return buf.append(sentence.substring(startIndex, endIndex + 1));
	}

	private static boolean HasTimeSliceh(String sentence)
	{
		if (sentence.matches(".*\\( h .*"))
			return true;
		else
			return false;
	}

	private static String TimeSliceToAllanh(String sentence)
	{
		int h1Index = -1;
		int h2Index = -1;
		int fooIndex = -1;

		String mid = "";
		String pre = "";
		String post = "";

		String foo = "";
		String a = "";
		String b = "";

		for (int i = 0; i < sentence.length(); i++)
		{
			if ((sentence.charAt(i) == '(') && (sentence.charAt(i + 1) == ' ')
					&& (sentence.charAt(i + 2) == 'h')
					&& (sentence.charAt(i + 3) == ' '))
			{
				h1Index = i + 2;
				break;
			}
		}

		for (int i = h1Index + 1; i < sentence.length(); i++)
		{
			if ((sentence.charAt(i) == '(') && (sentence.charAt(i + 1) == ' ')
					&& (sentence.charAt(i + 2) == 'h')
					&& (sentence.charAt(i + 3) == ' '))
			{
				h2Index = i + 2;
				break;
			}
		}

		for (int i = h1Index + 2; sentence.charAt(i) != ' '; i++)
		{
			a += sentence.charAt(i);
		}

		for (int i = h2Index + 2; sentence.charAt(i) != ' '; i++)
		{
			b += sentence.charAt(i);
		}

		for (int i = h1Index - 3; i >= 0; i--)
		{
			if (sentence.charAt(i) == '(')
			{
				fooIndex = i + 2;
				foo = sentence.substring(fooIndex, h1Index - 3);
				pre = sentence.substring(0, fooIndex - 2);
				break;
			}
		}

		for (int i = h1Index; i < sentence.length(); i++)
		{
			// this condition will likely depend on # of predicates
			if ((sentence.charAt(i) == ')') && (sentence.charAt(i + 1) == ' ')
					&& (sentence.charAt(i + 2) == ')'))
			{
				post = sentence.substring(i + 3);
				break;
			}
		}

		// may need to add more
		if (foo.matches(".*owner.*"))
			foo = "owner";

		// last part may not be t all the time !
		mid = "(|Holds| (" + foo + " " + a + " " + b + ") t)";

		// return sentence;
		return pre + mid + post;
	}

	private static boolean HasTimeSlicea(String sentence)
	{
		if (sentence.matches(".*\\( a .*"))
			return true;
		else
			return false;
	}

	private static String TimeSliceToAllana(String sentence)
	{
		int a1Index = -1;
		int a2Index = -1;
		int fooIndex = -1;

		String mid = "";
		String pre = "";
		String post = "";

		String foo = "";
		String a = "";
		String b = "";

		for (int i = 0; i < sentence.length(); i++)
		{
			if ((sentence.charAt(i) == '(') && (sentence.charAt(i + 1) == ' ')
					&& (sentence.charAt(i + 2) == 'a')
					&& (sentence.charAt(i + 3) == ' '))
			{
				a1Index = i + 2;
				break;
			}
		}

		for (int i = a1Index + 1; i < sentence.length(); i++)
		{
			if ((sentence.charAt(i) == '(') && (sentence.charAt(i + 1) == ' ')
					&& (sentence.charAt(i + 2) == 'a')
					&& (sentence.charAt(i + 3) == ' '))
			{
				a2Index = i + 2;
				break;
			}
		}

		for (int i = a1Index + 2; sentence.charAt(i) != ' '; i++)
		{
			a += sentence.charAt(i);
		}

		for (int i = a2Index + 2; sentence.charAt(i) != ' '; i++)
		{
			b += sentence.charAt(i);
		}

		for (int i = a1Index - 3; i >= 0; i--)
		{
			if (sentence.charAt(i) == '(')
			{
				fooIndex = i + 2;
				foo = sentence.substring(fooIndex, a1Index - 3);
				pre = sentence.substring(0, fooIndex - 2);
				break;
			}
		}

		for (int i = a1Index; i < sentence.length(); i++)
		{
			// this condition will likely depend on # of predicates
			if ((sentence.charAt(i) == ')') && (sentence.charAt(i + 1) == ' ')
					&& (sentence.charAt(i + 2) == ')'))
			{
				post = sentence.substring(i + 3);
				break;
			}
		}

		// may need to add more
		if (foo.matches(".*owner.*"))
			foo = "owner";

		// last part may not be t all the time !
		mid = "(|Ab| (" + foo + " " + a + " " + b + ") t)";

		// return sentence;
		return pre + mid + post;
	}

	private static boolean HasTimeSlicehStar(String sentence)
	{
		if (sentence.matches(".*\\( h\\* .*"))
			return true;
		else
			return false;
	}

	private static String TimeSliceToAllanhStar(String sentence)
	{
		int hStar1Index = -1;
		int hStar2Index = -1;
		int fooIndex = -1;

		String mid = "";
		String pre = "";
		String post = "";

		String foo = "";
		String a = "";
		String b = "";

		for (int i = 0; i < sentence.length(); i++)
		{
			if ((sentence.charAt(i) == '(') && (sentence.charAt(i + 1) == ' ')
					&& (sentence.charAt(i + 2) == 'h')
					&& (sentence.charAt(i + 3) == '*')
					&& (sentence.charAt(i + 4) == ' '))
			{
				hStar1Index = i + 2;
				break;
			}
		}

		for (int i = hStar1Index + 2; i < sentence.length(); i++)
		{
			if ((sentence.charAt(i) == '(') && (sentence.charAt(i + 1) == ' ')
					&& (sentence.charAt(i + 2) == 'h')
					&& (sentence.charAt(i + 3) == '*')
					&& (sentence.charAt(i + 4) == ' '))
			{
				hStar2Index = i + 2;
				break;
			}
		}

		if (hStar2Index == -1)
			return sentence; // only one h* - returning original sentence for
		// now, to be resilient

		for (int i = hStar1Index + 3; sentence.charAt(i) != ' '; i++)
		{
			a += sentence.charAt(i);
		}

		for (int i = hStar2Index + 3; sentence.charAt(i) != ' '; i++)
		{
			b += sentence.charAt(i);
		}

		for (int i = hStar1Index - 3; i >= 0; i--)
		{
			if (sentence.charAt(i) == '(')
			{
				fooIndex = i + 2;
				foo = sentence.substring(fooIndex, hStar1Index - 3);
				pre = sentence.substring(0, fooIndex - 2);
				break;
			}
		}

		for (int i = hStar1Index; i < sentence.length(); i++)
		{
			// this condition will likely depend on # of predicates
			if ((sentence.charAt(i) == ')') && (sentence.charAt(i + 1) == ' ')
					&& (sentence.charAt(i + 2) == ')'))
			{
				post = sentence.substring(i + 3);
				break;
			}
		}

		// may need to add more
		if (foo.matches(".*owner.*"))
			foo = "owner";

		// last part may not be t all the time !
		mid = "(|Holds*| (" + foo + " " + a + " " + b + ") t)";

		// return sentence;
		return pre + mid + post;
	}

	// assuming Cotemporal has fixed structure - may need to validate
	static boolean HasCotemporal(String sentence)
	{
		if (sentence.startsWith("(cotemporal"))
			return true;
		else
			return false;
	}

	static String FormatCotemporal(String sentence)
	{
		String p1 = "";
		String p2 = "";
		String t1 = "";
		String t2 = "";

		String y1 = "";
		String m1 = "";
		String d1 = "";
		String h1 = "";

		String y2 = "";
		String m2 = "";
		String d2 = "";
		String h2 = "";

		// quite a few assumptions on sentence structure in the code below
		for (int i = 12; sentence.charAt(i) != ' '; i++)
		{
			p1 += sentence.charAt(i);
		}

		for (int i = sentence.indexOf("year") + 5; sentence.charAt(i) != ')'; i++)
		{
			y1 += sentence.charAt(i);
		}
		for (int i = sentence.indexOf("month") + 6; sentence.charAt(i) != ')'; i++)
		{
			m1 += sentence.charAt(i);
		}
		for (int i = sentence.indexOf("day") + 4; sentence.charAt(i) != ')'; i++)
		{
			d1 += sentence.charAt(i);
		}
		for (int i = sentence.indexOf("hour") + 5; sentence.charAt(i) != ')'; i++)
		{
			h1 += sentence.charAt(i);
		}
		t1 = "year" + y1 + "-month" + m1 + "-day" + d1 + "-hour" + h1;

		for (int i = sentence.indexOf("year", sentence.indexOf("year") + 1) + 5; sentence
				.charAt(i) != ')'; i++)
		{
			y2 += sentence.charAt(i);
		}
		for (int i = sentence.indexOf("month", sentence.indexOf("month") + 1) + 6; sentence
				.charAt(i) != ')'; i++)
		{
			m2 += sentence.charAt(i);
		}
		for (int i = sentence.indexOf("day", sentence.indexOf("day") + 1) + 4; sentence
				.charAt(i) != ')'; i++)
		{
			d2 += sentence.charAt(i);
		}
		for (int i = sentence.indexOf("hour", sentence.indexOf("hour") + 1) + 5; sentence
				.charAt(i) != ')'; i++)
		{
			h2 += sentence.charAt(i);
		}
		t2 = "year" + y2 + "-month" + m2 + "-day" + d2 + "-hour" + h2;

		p2 = "(time-interval-fn " + t1 + " " + t2 + ")";


		return "(cotemporal " + p1 + " " + p2 + ")";
		// return sentence;
	}

	public static class KifIndentation
	{
		private String _kif;
		private static final String AND = "(and";
		private static final String OR = "(or";
		private static final String NOT = "(not";

		public KifIndentation(String kif)
		{
			_kif = kif;
		}

		public String getIndentedKif()
		{
			String kif = removeHTMLTags(_kif);
			return indent(kif, 0);
		}

		private static String removeHTMLTags(String kif)
		{
			kif = kif.replaceAll("<font[^0-9]*[0-9]*\">", "");
			kif = kif.replaceAll("</font>", "");
			return kif.replaceAll("&lt;=", "<=");
		}

		private static String indent(String kif, int indentLevel)
		{
			if (isRule(kif))
			{
				String[] elements = getTopLevelElements(kif);
				String ruleOperator = "(<= ";
				int indentationLength = ruleOperator.length();
				String indentation = makeIndentation(indentationLength);
				String indentedKif = ruleOperator + elements[0] + "\n";
				indentedKif = indentedKif + indentation
						+ indent(elements[1], indentationLength) + ")";
				return indentedKif;
			} else if (isClause(kif))
			{
				String operator = getClausalOperator(kif) + " ";
				String[] elements = getTopLevelElements(kif);
				int indentationLength = indentLevel + operator.length() + 1;
				String indentation = makeIndentation(indentationLength);
				String indentedKif = operator + " ";

				for (int i = 0; i < elements.length; i++)
				{
					if (i == 0)
						indentedKif = indentedKif
								+ indent(elements[i], indentationLength);
					else
					{
						indentedKif = indentedKif + "\n" + indentation
								+ indent(elements[i], indentationLength);
					}
				}
				return indentedKif + ")";
			} else
			{
				return kif;
			}
		}

		private static String makeIndentation(int length)
		{
			String indentation = "";
			for (int i = 0; i < length; i++)
				indentation = indentation + " ";
			return indentation;
		}

		private static boolean isClause(String kif)
		{
			if (kif.startsWith(AND) || kif.startsWith(OR)
					|| kif.startsWith(NOT))
				return true;
			return false;
		}

		private static String getClausalOperator(String kif)
		{
			if (kif.startsWith(AND))
				return AND;
			else if (kif.startsWith(OR))
				return OR;
			else if (kif.startsWith(NOT))
				return NOT;

			return null;
		}

		private static boolean isRule(String kif)
		{
			if (kif.startsWith("(<="))
				return true;
			return false;
		}

		private static String[] getTopLevelElements(String kif)
		{
			// remove opening and closing parens
			kif = kif.substring(1, kif.length() - 1);

			// extract top level elements denoted by ( 'some sequence' )
			int stackCounter = 0;
			char currentChar;
			String anElement = "";
			ArrayList elements = new ArrayList();
			for (int i = 0; i < kif.length(); i++)
			{
				currentChar = kif.charAt(i);
				if (currentChar == '(')
				{
					stackCounter++;
				} else if (currentChar == ')')
				{
					stackCounter--;
				}

				if (stackCounter > 0 || currentChar == ')')
				{
					anElement = anElement + currentChar;
				}

				if (stackCounter == 0 && currentChar == ')')
				{
					if (anElement != "")
						elements.add(anElement);
					anElement = "";
				}
			}
			return toStringArray(elements);
		}

		private static String[] toStringArray(ArrayList list)
		{
			String[] array = new String[list.size()];
			for (int i = 0; i < list.size(); i++)
			{
				array[i] = (String) list.get(i);
			}
			return array;
		}
	}*/
}
