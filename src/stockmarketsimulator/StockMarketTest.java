
import java.net.*;
import java.io.*;
import java.text.*;
import java.lang.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.imageio.*;
import javax.swing.*;


//import hsa.Console;



public class StockMarketTest
{ 
    public static String Stock;
    public static String companyName;
    public static double price;
    public static String usernameGLBL;
    public static int userNum;
    public static double money;
    public static double initialamount;
    public static boolean repeatmoney;
    public static int gcounter;
    public static int[] shareNum = new int[10];
    public static double[] value = new double[10]; 
    
    public static ArrayList usernames1 = new ArrayList();
    public static ArrayList passwords1 = new ArrayList();
    public static ArrayList cash = new ArrayList();
    
    public static ArrayList portStock1 = new ArrayList();
    public static ArrayList portStock2 = new ArrayList();
    public static ArrayList portStock3 = new ArrayList();
    
//    static Console c;
    
    public static double stockfinder () throws IOException
    {
	BufferedReader myInput = new BufferedReader (new InputStreamReader(System.in));

	price = 0;
	boolean z1;
	boolean z2 = false;
	String inputLine = "";
	String found_line = "";
	String stockPrice = "";

        
        
                
	Matcher m; 
//	String a = "<p class=\"data bgLast\">";
//        String a = "<span class=\"Trsdu(0.3s) Fw(b) Fz(36px) Mb(-4px) D(ib)\" data-reactid=\"35\"><!-- react-text: 36 -->153.39<!-- /react-text --></span>";
        String a = "<span id=\"ref_";
	Pattern p = Pattern.compile(a);
	
        

        
	Matcher m1;
//	String a1 = "<h1>Symbol Lookup</h1>";
        String a1 = "<div style=\"margin-top:1em;margin-bottom:2em;font-size:larger\">Your search - <b>";
	Pattern p1 = Pattern.compile(a1);
	
	String count = "good";
	
	System.out.println("\nSEARCH STOCKS:");
	System.out.println("==============");
	System.out.println("Enter company stock ticker:");
	Stock = myInput.readLine();
	
	System.out.println("\n[Accessing Database...]");
        
//        Date date = new Date();
//        StockQuote facebook = new StockQuote("AAPL", date, 2.21);
//        
//        facebook.getQuote("AAPL");
   
	
	do
	{ 
	    if (count  == "error")
	    {
		System.out.println("\nEnter company stock ticker:");
		Stock = myInput.readLine();
		
		System.out.println("\n[Accessing Database...]");
	    }
	    
	    try
	    {
		URL marketwatch = new URL ("https://finance.google.ca/finance?q=" + Stock);
		BufferedReader in = new BufferedReader (new InputStreamReader (marketwatch.openStream ()));
		
		while ((inputLine = in.readLine())!= null)
		{
		    //System.out.println(inputLine);

		    m = p.matcher(inputLine);
		    z1 = m.find();
		    
		    if (z1 == true)
		    {
			found_line = inputLine;
			
			//System.out.println(found_line);
			
			stockPrice = found_line.substring (found_line.indexOf (">") + 1, found_line.indexOf ("</"));
			
			//System.out.println(stockPrice);
			
			count = "good";
			
			break;    
		    } 
			
		    //price = Double.parseDouble(stockPrice);
		    
//		    System.out.println(stockPrice);
		    
		    m1 = p1.matcher(inputLine);
		    z2 = m1.find();
		    
		    if (z2 == true)
		    {
			System.out.println("\n[ERROR] Please enter a valid stock ticker.");
			count = "error";
		    }    
		}
		
		in.close ();

	    }
	    catch (StringIndexOutOfBoundsException error)
	    {
		System.out.println("\n[ERROR] Please enter a valid stock ticker.");
		count = "error";
	    }
	    catch (MalformedURLException error)
	    {
		System.out.println("\n[ERROR] Please enter a valid stock ticker."); 
		count = "error";   
	    }
	    catch (IOException error)
	    {
		System.out.println("\n[ERROR] Connect to the internet.");
		count = "error";
	    }
	    catch (NumberFormatException error)
	    {
		System.out.println("\n[ERROR] Please enter a valid stock ticker.");
		count = "error";
	    }
	    
	}
	while(count == "error");
	
	//stockPrice = found_line.substring (found_line.indexOf (">") + 1, found_line.indexOf ("</"));
	
	price = Double.parseDouble(stockPrice);
	
	companyName = companyName(Stock);
	
	return price;
    
    }
    
    public static String companyName(String Stock)
    {
	boolean z2;
	String found_line = "";
	companyName = "";
	String inputLine = "";
	
	Matcher m2;
	String a2 = "<h1 id=\"instrumentname\">";
	Pattern p2 = Pattern.compile(a2);

	try
	{
	    URL marketwatch = new URL ("http://www.marketwatch.com/investing/stock/" + Stock);
	    BufferedReader in = new BufferedReader (new InputStreamReader (marketwatch.openStream()));
	    
	    while ((inputLine = in.readLine())!= null)
	    {
		m2 = p2.matcher(inputLine);
		z2 = m2.find();
	    
		if (z2 == true)
		{
		    found_line = inputLine;
		    
		    companyName = found_line.substring((found_line.indexOf(">") + 1), found_line.indexOf ("</"));
		}
	    }
	    
	}
	catch (StringIndexOutOfBoundsException error)
	{
	    System.out.println("\n[ERROR] Please enter a valid stock ticker.");
	}
	catch (MalformedURLException error)
	{
	    System.out.println("\n[ERROR] Please enter a valid stock ticker.");   
	}
	catch (IOException error)
	{
	    System.out.println("\n[ERROR] Please enter a valid stock ticker.");
	}
	catch (NumberFormatException error)
	{
	    System.out.println("\n[ERROR] Please enter a valid stock ticker.");
	} 
	
	try
	{
	    stockOutput(price, companyName, Stock);
	}
	catch (IOException error)
	{
	    System.out.println(error);
	}
       
	return companyName;
  
    }
    
    public static String stockOutput (double stockPrice, String companyName, String Stock) throws IOException
    {
	BufferedReader myInput = new BufferedReader (new InputStreamReader (System.in));
	DecimalFormat twoDigit = new DecimalFormat ("#,##0.00");
	
	int select = 0;
	
	System.out.println("\nStock: " + companyName + "\nPrice: $" + twoDigit.format(stockPrice));

	do
	{
	    System.out.println("\nSelect an option:");
	    System.out.println("1 - Purchase stock\t\t2 - View stock performance\n3 - Search for another stock\t4 - Return to main menu");

	    try
	    {
		select = Integer.parseInt(myInput.readLine());

		if (select > 4 || select <= 0)
		{
		    System.out.println("[ERROR] Please select a valid option."); 
		}
	    }
	    catch (NumberFormatException error)
	    {
		System.out.println("[ERROR] Please select a valid option.");
	    }    
	}
	while (select > 4 || select <= 0);
	
	if (select == 1)
	{
	    Purchase();
	}
	else if (select == 2)
	{
	    checkstockPerformance(Stock);    
	}
	else if (select == 3)
	{
	    stockfinder();   
	}
	else if (select == 4)
	{
	    mainMenu();
	}

	return companyName;
    }
    
    public static String checkstockPerformance(String Stock) throws IOException
    {
	String inputLine = "";
	boolean h, repeat;
	Matcher m; 
	String a = "<title>sc(305�176)</title>";
	Pattern p = Pattern.compile(a); 
	
	    try
	    {
		URL stockgraph = new URL ("http://stockcharts.com/c-sc/sc?s=" + Stock + "&p=D&b=5&g=0&i=t50254414921&r=1434157391837");
		BufferedReader in = new BufferedReader (new InputStreamReader (stockgraph.openStream()));
		
		while ((inputLine = in.readLine())!= null)
		{
		    //System.out.println(inputLine);

		    m = p.matcher(inputLine);   
		    h = m.find();
			    
		    if (h == false)
		    {
			stockPerformance(Stock);        
		    }
		    else
		    {
			System.out.println("\nUnable to locate stock performance.");
			stockOutput2();
		    }
		}
	    }
	    catch (StringIndexOutOfBoundsException error)
	    {
		System.out.println("\n[ERROR] Please enter a valid stock ticker.");
	    }
	    catch (MalformedURLException error)
	    {
		System.out.println("\n[ERROR] Please enter a valid stock ticker.");   
	    }
	    catch (IOException error)
	    {
	    System.out.println("\n[ERROR] Please enter a valid stock ticker.");
	    }
	    catch (NumberFormatException error)
	    {
		System.out.println("\n[ERROR] Please enter a valid stock ticker.");
	    }
	    
	    return Stock;               
    }
    
    public static String stockPerformance(String Stock) throws IOException
    {
	c = new Console ();    
	
	URL url = new URL("http://stockcharts.com/c-sc/sc?s=" + Stock + "&p=D&b=5&g=0&i=t50254414921&r=1434157391837");
	Image image = ImageIO.read(url);
	
	c.drawImage (image, 10, 0, null);
	
	c.setColor(Color.WHITE);
	c.fillRect(0, 375, 2000, 1000);
	
	
	c.setColor(Color.black);
	c.drawString("Move window to the left.",50, 425); 
	
	stockOutput2();
	
	return Stock;
    }
    
    public static String stockOutput2() throws IOException
    {
	BufferedReader myInput = new BufferedReader (new InputStreamReader (System.in));
	
	int select = 0;
	boolean repeat = false;
	
	System.out.println("\n\nPlease select an option:");
	System.out.println("1 - Purchase Stock\t\t2 - Search Stocks\n3 - Main Menu");
	
	do
	{
	    try
	    {
		select = Integer.parseInt(myInput.readLine());
		
		if (select > 3 || select <= 0)
		{
		    repeat = true;
		    System.out.println("\n[ERROR] Invalid Selection.");
		}
	    }
	    catch (NumberFormatException error)
	    {
		System.out.println("\n[ERROR] Invalid Selection.");
		repeat = true;
	    }
	}
	while(select > 3 || select <= 0);
	
	if (select == 1)
	{
	    Purchase();
	}
	else if (select == 2)
	{
	    stockfinder();
	}
	else if (select == 3)
	{
	    mainMenu();
	}
	
	return Stock;
		   
    }
    
    public static int sell() throws IOException
    {
	BufferedReader myInput = new BufferedReader (new InputStreamReader (System.in));
	
	BufferedReader portfolio = new BufferedReader(new FileReader("Portfolio.txt"));
	BufferedReader Balance = new BufferedReader(new FileReader("Balance.txt"));
	BufferedReader ShareNum = new BufferedReader(new FileReader("ShareNum.txt"));
	
	DecimalFormat twoDigit = new DecimalFormat("#,##0.00");
	
	String[] portRead = new String[10];
	String[] stockPrice1 = new String[portRead.length];
	
	String portRead1;
	
	portStock1.clear();
	
	for (int i = 0; i <= portRead.length; i++)
	{
	    portRead1 = portfolio.readLine();
	    
	    if (portRead1 == null || portRead1.equals(""))
	    {
		break;
	    }
	    else
	    {
		portStock1.add(portRead1);
	    }
	}

	for (int i9 = 0; i9 < portRead.length; i9++)
	{
	    try
	    {
		if (ShareNum.readLine() == null || ShareNum.readLine() == "0")
		{
		    ShareNum.close();
		    break;
		}
		else
		{
		    BufferedReader ShareNum3 = new BufferedReader(new FileReader("ShareNum.txt"));
		    shareNum[i9] = Integer.parseInt(ShareNum3.readLine());
		    //System.out.println(shareNum[i9]);
		}
	    }
	    catch (NumberFormatException error)
	    {
		break;
	    }
	}
	
	money = Double.parseDouble(Balance.readLine());
	
	int select = 0;
	int count = 0;
	String stockPrice = "";
	String found_line = "";
	String inputLine = "";
	boolean h, repeat;
	Matcher m; 
	String a = "<p class=\"data bgLast\">";
	Pattern p = Pattern.compile(a);       
	
	
	
	if (portStock1.size() > 0)
	{
		for (int x = 0; x < portStock1.size(); x++)
		{
		    try
		    {
			URL marketwatch = new URL ("http://www.marketwatch.com/investing/stock/" + portStock1.get(x));
			BufferedReader in = new BufferedReader (new InputStreamReader (marketwatch.openStream()));
		
			while ((inputLine = in.readLine())!= null)
			{
			    //System.out.println(inputLine);

			    m = p.matcher(inputLine);   
			    h = m.find();
			    
			    if (h == true)
			    {
				found_line = inputLine;
			
				//System.out.println(found_line);
			
				stockPrice1[x] = found_line.substring (found_line.indexOf (">") + 1, found_line.indexOf ("</"));
			
				//System.out.println(stockPrice1[x]);
				
				if (x > portStock1.size())
				{
				    break;
				}
			    
			    }
			}
		    }
		    catch (StringIndexOutOfBoundsException error)
		    {
			System.out.println("\n[ERROR] Please enter a valid stock ticker.");
		    }
		    catch (MalformedURLException error)
		    {
			System.out.println("\n[ERROR] Please enter a valid stock ticker.");   
		    }
		    catch (IOException error)
		    {
			System.out.println("\n[ERROR] Please enter a valid stock ticker.");
		    }
		    catch (NumberFormatException error)
		    {
			System.out.println("\n[ERROR] Please enter a valid stock ticker.");
		    } 
		
		}
		
		System.out.println("\nPORTFOLIO");
		System.out.println("=========");
		
		//System.out.println(shareNum[0]);
		
		if (money == 0)
		{
		    System.out.println("Balance: $" + twoDigit.format(initialamount));
		}
		else
		{
		    System.out.println("Balance: $" + twoDigit.format(money));    
		}
		System.out.println("\nStock:\t\tPrice:\t\tValue:");
  
		for (int loop = 0; loop < portStock1.size(); loop++)
		{
		    price = Double.parseDouble(stockPrice1[loop]);
		    value[loop] = price*shareNum[loop];
		    System.out.println(portStock1.get(loop) + "\t\t$" + stockPrice1[loop] + "\t\t$" + twoDigit.format(value[loop]));
		    
		    
		}
		
		double totalvalue = 0;
		
		for (int i = 0; i < portStock1.size(); i++)
		{
		    totalvalue = totalvalue + value[i];
		}

		totalvalue = money + totalvalue;
		
		if (totalvalue == 0)
		{
		    System.out.println("\nTotal Value: $" + twoDigit.format(initialamount));
		}
		else
		{
		    System.out.println("\nTotal Value: $" + twoDigit.format(totalvalue));   
		}

	}
	
	if (portStock1.size() == 0)
	{
	    System.out.println("\nBalance: $" + twoDigit.format(money));
	}
	
	System.out.println("\nPlease select an option:");
	System.out.println("1 - Sell Stocks\t\t2 - Purchase Stocks\n3 - Main Menu\t\t4 - Refresh");
	
	do
	{
	    repeat = false;
	    
	    if (count > 0)
	    {
		System.out.println("\nPlease select an option:");
		System.out.println("1 - Sell Stocks\t\t2 - Purchase Stocks\n3 - Main Menu");
	    }   
	    
	    try
	    {
		select = Integer.parseInt(myInput.readLine());
		
		if (select > 4 || select <= 0)
		{
		    System.out.println("[ERROR] Please select a valid option.");
		    repeat = true;
		    count++;
		}
		else
		{
		    break;
		}
	    }
	    catch(NumberFormatException error)
	    {
		System.out.println("\n[ERROR] Please select a valid option.");
		repeat = true;
		count++;
	    } 
	}
	while(repeat == true);
	
	if (select == 1)
	{
	    if (portStock1.size() == 0)
	    {
		System.out.println("[ERROR] No Stocks Purchased.");
		sell();
	    }
	    else
	    {
		sellstock();
	    }
	}
	else if (select == 2)
	{
	    stockfinder();
	}
	else if (select == 3)
	{
	    mainMenu();
	}
	else if (select == 4)
	{
	    sell();
	}
	
	return 1;  
	  
    }
    
    
    public static double sellstock() throws IOException
    {
	BufferedReader myInput = new BufferedReader (new InputStreamReader (System.in));
	
	DecimalFormat twoDigit = new DecimalFormat ("#,##0.00");

	BufferedReader portfolio = new BufferedReader(new FileReader("Portfolio.txt"));
	BufferedReader Balance = new BufferedReader(new FileReader("Balance.txt"));
	BufferedReader ShareNum = new BufferedReader(new FileReader("ShareNum.txt"));


	int i = 0;
	int pnshare = 0;
	int select = 0;
	boolean repeat;
	String stock;
	double pPrice = 0;
	
	System.out.println("\nSelect a stock to sell:");
	System.out.println(" \tStock:\t\tValue:");
	for (i = 0; i < portStock1.size(); i++)
	{
	    System.out.println((i + 1) + "\t" + portStock1.get(i) + "\t\t$" + twoDigit.format(value[i]));
	}
	
	do
	{
	    repeat = false;
	    
	    try
	    {
		select = Integer.parseInt(myInput.readLine());
		
		if (select > portStock1.size() || select <= 0)
		{
		    System.out.println("[ERROR] Invalid selection.");
		    repeat = true;
		}
		else
		{
		    select = select - 1;

		    pPrice = value[select]/shareNum[select]; 
		    
		    break;   
		}
	    }
	    catch (NumberFormatException error)
	    {
		System.out.println("[ERROR] Invalid selection.");
		repeat = true;
	    }
	}
	while(repeat = true);
	
	System.out.println("\nCurrent Number of Shares: " + shareNum[select]);
	System.out.println("Number of Shares to Sell:");
	do
	{
	    repeat = false;
	    try
	    {
		pnshare = Integer.parseInt(myInput.readLine());
		
		if (pnshare > shareNum[select] || pnshare <= 0)
		{
		    System.out.println("\n[ERROR] Invalid entry.");
		    repeat = true;
		}
		else
		{
		    break;
		}   
	    }
	    catch (NumberFormatException error)
	    {
		    System.out.println("\n[ERROR] Invalid entry.");
		    repeat = true;    
	    }    
	}
	while(repeat = true);
	
	shareNum[select] = shareNum[select] - pnshare;
	
	PrintWriter ShareNum1 = new PrintWriter(new FileWriter("ShareNum.txt"));
	PrintWriter Balance1 = new PrintWriter(new FileWriter("Balance.txt")); 

	for (int i4 = 0; i4 < shareNum.length; i4++)
	{
	    ShareNum1.println(shareNum[i4]);
	}
	ShareNum1.close();
	
	if (shareNum[select] == 0)
	{
	    portStock1.remove(select);
	    
	    PrintWriter portfolio1 = new PrintWriter(new FileWriter("Portfolio.txt"));
	    for (int i3 = 0; i3 < portStock1.size(); i3++)
	    {
		portfolio1.println(portStock1.get(i3));
	    }
	    portfolio1.close();
	    
	    for (int i2 = 1; i2 < shareNum.length; i2++)
	    {
		value[i2 - 1] = value[i2];
		shareNum[i2 - 1] = shareNum[i2];
		
		ShareNum1.println(shareNum[i2]);
	    }
	    ShareNum1.close();
	     
	}
	
	money = money + (pnshare*pPrice); 
	
	Balance1.println(money);
	Balance1.close();
	
	System.out.println("\nSale Confirmed.");
	
	sell();
	
	return 5;
    }
    
    public static int Purchase() throws IOException
    {
	BufferedReader myInput = new BufferedReader (new InputStreamReader (System.in));
	
	BufferedReader portfolio = new BufferedReader(new FileReader("Portfolio.txt"));
	BufferedReader Balance = new BufferedReader(new FileReader("Balance.txt"));
	BufferedReader ShareNum = new BufferedReader(new FileReader("ShareNum.txt"));
	
	
	DecimalFormat twoDigit = new DecimalFormat ("#,##0.00");
	
	int select = 0;
	int i = 0;
	boolean repeat = false;
	double pvalue;
	String convert, count, portRead1;
	String check;
	try
	{
	    if (Balance.readLine() != null)
	    {
		Balance.close();
		
		BufferedReader Balance2 = new BufferedReader(new FileReader("Balance.txt"));
		
		money = Double.parseDouble(Balance2.readLine()); 
	    }
	    
	    else
	    {
		convert =(cash.get(userNum)).toString();
		money = Double.parseDouble(convert);
	    }    
	}
	catch (NullPointerException error)
	{
	    System.out.println("[ERROR] Invalid balance.");
	}
	
	portStock1.clear();
	
	for (int i5 = 0; i5 <= 10; i5++)
	{
	    portRead1 = portfolio.readLine();
	    
	    if (portRead1 == null || portRead1.equals(""))
	    {
		break;
	    }
	    else
	    {
		portStock1.add(portRead1);
	    }
	}
	
	for (int i11 = 0; i11 < 10; i11++)
	{
	    if (ShareNum.readLine() == null || ShareNum.readLine() == "0")
	    {
		ShareNum.close();
		break;
	    }
	    else
	    {
		BufferedReader ShareNum2 = new BufferedReader(new FileReader("ShareNum.txt"));
		shareNum[i11] = Integer.parseInt(ShareNum2.readLine());
		
		//System.out.println(shareNum[i11]);
	    }
	}
	
	System.out.println("\nPURCHASE");
	System.out.println("========");
	
	count = "good";
	
	do
	{ 
	    if (repeat == true)
	    {
		do
		{
		    System.out.println("\nPlease select an option:");
		    System.out.println("1 - Purchase shares in " + companyName + "\t2 - Search Stocks\n3 - Main Menu");
		    
		    try
		    {
			select = Integer.parseInt(myInput.readLine());
			
			if (select == 1)
			{
			    break;
			}
			if (select == 2)
			{
			    stockfinder();
			    count = "good";   
			}
			else if (select == 3)
			{
			    mainMenu();
			    count = "good";
			}
			else if (select > 3 || select <= 0)
			{
			    System.out.println("[ERROR] Please select a valid option.");
			    count = "error";
			}
		    }
		    catch (NumberFormatException error)
		    {
			System.out.println("[ERROR] Please select a valid option.");
			count = "error"; 
		    }
		}
		while (count == "error");
	    }
	    
	    repeat = false;



	    try
	    {
		System.out.println("\nStock: " + companyName + "\nPrice: $" + price);
		
		System.out.println("\nBalance: $" + twoDigit.format(money));
		
		System.out.println("\nNumber of Shares to Purchase:");
		
		for (i = 0; i < 9; i++)
		{
		    if (shareNum[i] == 0)
		    {
			shareNum[i] = Integer.parseInt(myInput.readLine());
			
			break;
		    }
		}

		if (shareNum[i] * price > money || shareNum[i] * price < 0)
		{
		    shareNum[i] = 0;
		    System.out.println("\n[ERROR] Insufficient Funds");
		    repeat = true;
		}
	    }
	    catch(NumberFormatException error)
	    {
		System.out.println("\n[ERROR] Please enter a valid number.");
		repeat = true;
	    }   
	}
	while (repeat == true);
	
	pvalue = shareNum[i]*price;

	
	System.out.println("\nPlease select an option:");
	
	do
	{
	    repeat = false;
	    
	    System.out.println("1 - Confirm purchase: " + shareNum[i] + " shares at $" + price + " in " + companyName + ".\n2 - Return to main menu.");
	    
	    try
	    {
		
		select = Integer.parseInt(myInput.readLine());
		
		if (select > 2 || select <= 0)
		{
		    System.out.println("[ERROR] Please select a valid option.");
		    repeat = true;
		}
		else 
		{
		    break;
		}
	    }
	    catch (NumberFormatException error)
	    {
		System.out.println("[ERROR] Please select a valid option.");
		repeat = true;
	    }
	}
	while(repeat == true);

	//System.out.println(userNum);
	
	
	PrintWriter Balance1 = new PrintWriter(new FileWriter("Balance.txt"));
	PrintWriter ShareNum1 = new PrintWriter(new FileWriter("ShareNum.txt"));
	
	if (select == 1)
	{
	    gcounter++;
	    
	    money = money - pvalue;
	    
	    Balance1.println(money);
	    Balance1.close();
	    
	    repeatmoney = true;


	    Stock = Stock.toLowerCase();
	    //System.out.println(Stock);
	    
	    for (int i1 = 0; i1 < portStock1.size(); i1++)
	    {
		if (Stock.equals(portStock1.get(i1)))
		{
		    value[i1] = value[i1] + pvalue;
		    shareNum[i1] = shareNum[i1] + shareNum[i];
		    
		    //System.out.println(shareNum[i1]);
		    //System.out.println(shareNum[1]);
		    //System.out.println(i1);
		    
		    for (int i4 = 0; i < shareNum.length; i++)
		    {
			if (shareNum[i4] == 0)
			{
			    //System.out.println("error");
			    break;
			}
			else
			{
			    ShareNum1.println(shareNum[i4]);
			
			    //System.out.println(shareNum[i4]);
			    //System.out.println(portStock1.get(i4));
			}
		    }
		    ShareNum1.close();
		    
		    System.out.println("\nPurchase Confirmed.");
		    
		    mainMenu();
		}
	    }
	    
	    for (int i5 = 0; i5 < shareNum.length; i5++)
	    {
		ShareNum1.println(shareNum[i5]);
	    }
	    ShareNum1.close();
	    
	    PrintWriter portfolio1 = new PrintWriter(new FileWriter("Portfolio.txt"));
	    
	    portStock1.add(Stock);
	    for (int fr = 0; fr < portStock1.size(); fr++)
	    {
		portfolio1.println (portStock1.get(fr));
	    }
	    portfolio1.close();
	    
	    System.out.println("\nPurchase Confirmed.");
	    
	    mainMenu();
	}
	else if (select == 2)
	{
	    mainMenu();
	}
	return shareNum[i];
	
    }
    
    
    public static String portfolio() throws IOException
    {
	BufferedReader myInput = new BufferedReader (new InputStreamReader (System.in));        
	BufferedReader portfolio = new BufferedReader(new FileReader("Portfolio1.txt"));
	PrintWriter portfolio1 = new PrintWriter(new FileWriter("Portfolio1.txt"));
    
	int select;
	boolean repeat = false;
	
	System.out.println("\nPORTFOLIO MENU");
	System.out.println("==============");
	
	do
	{
	    System.out.println("Please select an option:");
	    System.out.println("1 - View Portfolio\t2 - Search Stocks\n3 - Main Menu");
	    select = Integer.parseInt(myInput.readLine());
	    
	    try
	    {
		if (select > 3 || select <= 0)
		{
		    System.out.println("\n[ERROR] Please select a valid option.");
		    repeat = true;
		}
		else
		{
		    break;
		}
	    }
	    catch (NumberFormatException error)
	    {
		System.out.println("\n[ERROR] Please select a valid option.");
		repeat = true;
	    }
	}
	while(repeat == true);
	
	if (select == 1)
	{
	    sell();
	}
	else if (select == 2)
	{
	    stockfinder();
	}
	else if (select == 3)
	{
	    mainMenu();
	} 
	
	String p = "pizza";
	return p;
    }
    
    
    
    //MAIN METHOD
    public static void main (String[] args) throws IOException
    {
	BufferedReader myInput = new BufferedReader (new InputStreamReader (System.in));    
    
	DecimalFormat twoDigit = new DecimalFormat ("#,##0.00");
	
	repeatmoney = false;
	String companyName;
	int select;
	
	System.out.println("STOCK MARKET SIMULATOR 2015");
	System.out.println("===========================");
	
	System.out.println("\nManage your portfolio and grow your wealth!");
	
	intromenu();
	mainMenu();
	//companyName = companyName(Stock);
	//stockOutput(price, companyName);
	
    }
    //END MAIN METHOD

    public static int mainMenu() throws IOException
    {
	BufferedReader myInput = new BufferedReader (new InputStreamReader (System.in));
	int select = 0;
	
	System.out.println("\nMAIN MENU");
	System.out.println("=========");
	System.out.println("Please select an option:");
	
	do
	{
	    System.out.println("1 - Stocks\t\t2 - Portfolio\n3 - Log Out");
	    
	    try
	    {
		select = Integer.parseInt(myInput.readLine());
		
		if (select > 3 || select <= 0)
		{
		    System.out.println("[ERROR] Please select a valid option.");
		}
	    }
	    catch (NumberFormatException error)
	    {
		System.out.println("[ERROR] Please select a valid option.");   
	    }
	}
	while(select > 3 || select <= 0);
	
	if (select == 1)
	{
	    stockfinder();
	}
	else if (select == 2)
	{
	    //portfolio method
	    portfolio();
	}
	else if (select == 3)
	{
	    //intromenu method
	    intromenu();
	}
	
	return select;
	
    }
    
    public static int intromenu() throws IOException
    {
	BufferedReader myInput = new BufferedReader (new InputStreamReader (System.in));
	int select = 0;
	
	System.out.println("\nWELCOME");
	System.out.println("=======");
	
	do
	{
	    System.out.println("Select an option:");
	    System.out.println("1 - Login\t2 - Register");
	    try
	    {
		select = Integer.parseInt(myInput.readLine());
		
		if (select > 2 || select <= 0)
		{
		    System.out.println("\n[ERROR] Please select a valid option.");   
		}
		
	    }
	    catch (NumberFormatException error)
	    {
		System.out.println("\n[ERROR] Please select a valid option.");
	    }  
	}
	while (select > 2 || select <= 0);
	
	if (select == 1)
	{
	    login();
	}
	else if (select == 2)
	{
	    register();
	}
	
	return select;   
    }
    
    public static String login () throws IOException
    {
	BufferedReader myInput = new BufferedReader (new InputStreamReader (System.in));  
	
	BufferedReader Username = new BufferedReader(new FileReader("Username.txt"));
	BufferedReader Password = new BufferedReader(new FileReader("Password.txt"));
	
	String username, password, count, count3, usernameRead, passwordRead;
	int count2, select;
	System.out.println("\nLOGIN");
	System.out.println("=====");
	
	count3 = "good";
	count2 = 0;
	count = "good";
	
	usernameRead = Username.readLine();
	
	do
	{
	    if (count2 > 0)
	    {
	     
		System.out.println("\n[ERROR] INVALID USERNAME");
		   
		do
		{
		    System.out.println("\nPlease select an option:");
		    System.out.println("1 - Return to Menu\t\t2 - Re-enter Username");
		    
		    try
		    {
			select = Integer.parseInt(myInput.readLine());
		
			if (select == 1)
			{
			    intromenu();
			}
			else if(select == 2)
			{
			    break;
			}
			else if (select > 2 || select <= 0)
			{
			    System.out.println("\n[ERROR] Please select a valid option.");
			    count3 = "error";   
			}
		    }
		    catch(NumberFormatException error)
		    {
			System.out.println("\n[ERROR] Please select a valid option.");
			count3 = "error";
		    }
		}
		while(count3 == "error"); 
	    }

	    System.out.print("\nUsername:");
	    username = myInput.readLine();
 
	    try
	    {
		//usernames1.add("gallo");
		//String test = myInput.readLine();
		
		if (usernames1.size() == 0)
		{
		    count = "error";
		    count2++;
		}
		
		for (int i = 0; i <= usernames1.size(); i++)
		{                
		    if (username.equals(usernameRead))
		    {
			count = "good";
			
			usernameGLBL = username;
			i = userNum;
			
			break;
		    }
		    else if (username != usernameRead)
		    {
			count = "error";
			count2 ++;
		    }
		}
	    }
	    catch (NullPointerException error)
	    {
		System.out.println("[ERROR] INVALID USERNAME");
		count = "error";
		count2 ++;
	    }
	}
	while (count == "error");
	
	count3 = "good";
	count2 = 0;
	count = "good";
 
	//password
	
	passwordRead = Password.readLine();
	
	do
	{
	    if (count2 > 0)
	    {
		
		do
		{
		    System.out.println("[ERROR] INVALID PASSWORD");
		    System.out.println("\nSelect an option:");
		    System.out.println("1 - Return to Menu\t\t2 - Re-enter Password");
		    
		    try
		    {
			select = Integer.parseInt(myInput.readLine());
		
			if (select == 1)
			{
			    intromenu();
			}
			else if(select == 2)
			{
			    break;
			}
			else if (select > 2 || select <= 0)
			{
			    System.out.println("[ERROR] Please select a valid option.");
			    count3 = "error";   
			}
		    }
		    catch(NumberFormatException error)
		    {
			System.out.println("[ERROR] Please select a valid option.");
			count3 = "error";
		    }
		}
		while(count3 == "error"); 
	    }

	    System.out.print("\nPassword:");
	    password = myInput.readLine();

	    try
	    {
		//usernames1.add("gallo");
		//String test = myInput.readLine();
		for (int i = 0; i <= passwords1.size(); i++)
		{
		    if (password.equals(passwordRead))
		    {
			count = "good";
		    }
		    else if (password != passwordRead)
		    {
			count = "error";
			count2 ++;
		    }
		}
	    }
	    catch (NullPointerException error)
	    {
		System.out.println("[ERROR] INVALID PASSWORD");
		count = "error";
		count2 ++;
	    }
	}
	while (count == "error");
	
	mainMenu();

	return username;  
    }
    
    public static String register () throws IOException
    {
	BufferedReader myInput = new BufferedReader (new InputStreamReader (System.in));
	
	BufferedReader Username = new BufferedReader(new FileReader("Username.txt"));
	BufferedReader Password = new BufferedReader(new FileReader("Password.txt"));
	PrintWriter Username1 = new PrintWriter(new FileWriter("Username.txt"));
	PrintWriter Password1 = new PrintWriter(new FileWriter("Password.txt"));
	BufferedReader portfolio = new BufferedReader(new FileReader("Portfolio.txt"));
	PrintWriter portfolio1 = new PrintWriter(new FileWriter("Portfolio.txt"));
	BufferedReader Balance = new BufferedReader(new FileReader("Balance.txt"));
	PrintWriter Balance1 = new PrintWriter(new FileWriter("Balance.txt"));
	BufferedReader ShareNum = new BufferedReader(new FileReader("ShareNum.txt"));
	PrintWriter ShareNum1 = new PrintWriter(new FileWriter("ShareNum.txt"));
	
	
	String username, password, count;
	boolean repeat;
	
	count = "good";
	
	if (Username.readLine() != null)
	{
	    Username1.println("");
	    Username1.close();
	}
	
	System.out.println("\nREGISTER");
	System.out.println("========");
	
	//System.out.print("Enter new username:");
	//username = myInput.readLine();
	
	//search username array for username, if not there, continue to password.

	    repeat = false;
	    
	    System.out.print("Enter new username:");
	    username = myInput.readLine();
	    
	    /*
	    if (usernames1.size() == 0)
	    {
	    */
		usernames1.add(username);
		
		for (int i = 0; i < usernames1.size(); i++)
		{
		    Username1.println(usernames1.get(i));
		}
		
		Username1.close();

	System.out.print("Enter new password:");
	password = myInput.readLine();
	    
	passwords1.add(password);
	
	for (int i = 0; i < passwords1.size(); i++)
	{
	    Password1.println(passwords1.get(i));
	}
	
	Password1.close();
	
	portfolio1.println("");
	portfolio1.close();
	ShareNum1.println(0);
	ShareNum1.close();
	Balance1.println("");
	Balance1.close();
	
	initialPortfolioamount();

	return username;

    }
    
    public static double initialPortfolioamount() throws IOException
    {
	BufferedReader myInput = new BufferedReader (new InputStreamReader (System.in));
	
	BufferedReader Balance = new BufferedReader(new FileReader("Balance.txt"));
	PrintWriter Balance1 = new PrintWriter(new FileWriter("Balance.txt"));
	
	initialamount = 0;
	String cashconvert;
	
	System.out.println("\nPORTFOLIO CREATION");
	System.out.println("==================");
	
	while(initialamount == 0)
	{
	    do
	    {
		System.out.print("\nInitial Portfolio Amount (maximum $50,000): $");
	    
		try
		{
		    initialamount = Double.parseDouble(myInput.readLine());
		
		    if (initialamount > 50000 || initialamount < 0)
		    {
			System.out.println("[ERROR] Amount must be less than $50,000 and greater than $0."); 
		    }
		    else 
		    {
			break;
		    }   
		
		}
		catch (NumberFormatException error)
		{
		    System.out.println("[ERROR] Please enter a valid amount.");    
		}
	    }
	    while(initialamount > 50000 || initialamount < 0);
	
	}
	
	Balance1.println(initialamount);        
	Balance1.close();
	
	for (int i = 0; i < usernames1.size(); i++)
	{
	    if (cash.size() == 0)
	    {
		cashconvert = String.valueOf(initialamount);
		
		cash.add(cashconvert);
		
		//System.out.println(cash.get(i));
		
		i = userNum;
	    }
	    else if (cash.size() > 0)
	    {
		for (int a = 0; a < (usernames1.size() + 1); a++)
		{
		    cashconvert = String.valueOf(initialamount);
		
		    cash.add(cashconvert);
		    
		    a = userNum;
		
		    //System.out.println(cash.get(a));
		}  
	    }
	}
	
	mainMenu();
	
	return initialamount;
    }

}


